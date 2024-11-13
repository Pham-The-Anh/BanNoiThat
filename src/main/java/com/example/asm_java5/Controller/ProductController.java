package com.example.asm_java5.Controller;

import com.example.asm_java5.Dao.CategoryDao;
import com.example.asm_java5.Entity.Category;
import com.example.asm_java5.Service.Format;
import com.example.asm_java5.Service.Session;
import com.example.asm_java5.Dao.ProductDao;
import com.example.asm_java5.Entity.Product;
import com.example.asm_java5.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private Session session;

    @RequestMapping("/shop")
    public String shop(Model model,
                       @RequestParam(defaultValue = "0") int p,
                       @RequestParam(name = "category", required = false, defaultValue = "Ghế Sofa") String category) {

        // Hiển thị sản phẩm theo loại
        Page<Product> productPage = productService.getProducts(category, p, 4);  // 4 sản phẩm mỗi trang
        if (p < 0 || p >= productPage.getTotalPages()) {
            return "redirect:/shop?p=0&category=" + category;
        }

        // Định dạng giá cho từng sản phẩm
        productPage.getContent().forEach(product -> {
            product.setFormattedPrice(Format.formatCurrency(product.getPrice()));
        });

        model.addAttribute("page", productPage);
        model.addAttribute("currentPage", p);
        model.addAttribute("category", category);

         //Lấy danh sách sản phẩm cho các loại khác (như Bàn làm việc)
        Page<Product> tablesPage = productService.getProducts("Bàn làm việc", 0, 4); // Bắt đầu từ trang 0 cho bàn làm việc
        tablesPage.getContent().forEach(product -> {
            product.setFormattedPrice(Format.formatCurrency(product.getPrice()));
        });
        model.addAttribute("tablesPage", tablesPage);

        return "shop";
    }
    @GetMapping("/shop1")
    public String shop(Model model) {
        List<Product> allProducts = productDao.findAll(); // Lấy tất cả sản phẩm
        Map<String, List<Product>> productsByCategory = new HashMap<>();

        // Nhóm sản phẩm theo loại
        for (Product product : allProducts) {
            // Kiểm tra nếu product.getCategory() không null trước khi truy xuất tên danh mục
            if (product.getCategory() != null) {
                String categoryName = product.getCategory().getName();
                productsByCategory
                        .computeIfAbsent(categoryName, k -> new ArrayList<>())
                        .add(product);
            } else {
                // Xử lý trường hợp sản phẩm không có danh mục
                String categoryName = "No category"; // Hoặc tên mặc định
                productsByCategory
                        .computeIfAbsent(categoryName, k -> new ArrayList<>())
                        .add(product);
            }
        }

        model.addAttribute("productsByCategory", productsByCategory);

        return "shop1"; // Trả về tên mẫu
    }

    @RequestMapping("/shop/search-and-page")
    public String searchAndPage(
            @RequestParam(defaultValue = "") String keywords,
            @RequestParam(defaultValue = "") String category,
            @RequestParam(defaultValue = "0") int p,
            Model model) {

        Pageable pageable = PageRequest.of(p, 8);  // 8 sản phẩm mỗi trang
        Page<Product> page;

        // Lấy sản phẩm dựa trên từ khóa và loại
        if (keywords.isEmpty() && category.isEmpty()) {
            page = productDao.findAll(pageable);
        } else if (!keywords.isEmpty() && category.isEmpty()) {
            page = productDao.findAllByproductNameLike("%" + keywords + "%", pageable);
        } else if (keywords.isEmpty()) {
            page = productDao.findByCategory_Name(category, pageable);
        } else {
            page = productDao.findAllByProductNameLikeAndCategory("%" + keywords + "%", category, pageable);
        }

        // Định dạng giá cho từng sản phẩm
        page.getContent().forEach(product -> {
            product.setFormattedPrice(Format.formatCurrency(product.getPrice()));
        });

        model.addAttribute("page", page);
        model.addAttribute("keywords", keywords);  // Giữ lại từ khóa tìm kiếm
        model.addAttribute("category", category);  // Giữ lại loại trong dropdown
        return "shop";
    }
    @RequestMapping("/shop/{id}")
    public String getProductDetail(@PathVariable("id") String productId, Model model) {
        Optional<Product> productOptional = productDao.findById(productId); // Sử dụng findById vì tìm theo ID duy nhất
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            // Gọi phương thức formatCurrency từ service
            String formattedPrice = Format.formatCurrency(product.getPrice());
            product.setFormattedPrice(formattedPrice); // Gán giá trị đã định dạng vào product
            model.addAttribute("product", product);
        } else {
            // Xử lý trường hợp sản phẩm không tồn tại
            return "redirect:/index"; // Ví dụ: chuyển hướng về trang shop
        }

        return "product-detail"; // Trả về trang hiển thị chi tiết sản phẩm
    }


        // Hiển thị trang tạo hoặc chỉnh sửa sản phẩm
        @RequestMapping("/admin/product")
        public String showproduct(Model model) {
            Product item = new Product();
            model.addAttribute("product", item);  // Đối tượng sản phẩm mới để hiển thị trong form
            List<Product> products = productDao.findAll();  // Lấy danh sách tất cả sản phẩm
            model.addAttribute("products", products);  // Truyền danh sách sản phẩm vào model
            return "editProduct";
        }

        // Hiển thị trang chỉnh sửa sản phẩm
        @RequestMapping("/admin/product/edit/{id}")
        public String edit(Model model, @PathVariable("id") String id) {
            // Kiểm tra sản phẩm có tồn tại không
            Optional<Product> optionalProduct = productDao.findById(id);
            if (optionalProduct.isPresent()) {
                // Nếu tồn tại, thêm sản phẩm vào model
                Product item = optionalProduct.get();
                model.addAttribute("product", item);
            } else {
                // Nếu không tìm thấy sản phẩm, hiển thị thông báo lỗi
                model.addAttribute("message", "Product không tồn tại");
                return "redirect:/admin/product"; // Chuyển hướng về trang tạo mới
            }
            return "editProduct"; // Quay lại trang chỉnh sửa sản phẩm
        }

        // Tạo mới sản phẩm
        @RequestMapping("/admin/product/create")
        public String create(Product product) {
            productDao.save(product); // Lưu sản phẩm vào database
            return "redirect:/admin/product"; // Sau khi tạo, chuyển hướng về trang tạo mới
        }

        // Cập nhật sản phẩm
        @RequestMapping("/admin/product/update")
        public String update(Product product) {
            productDao.save(product); // Lưu sản phẩm cập nhật vào database
            return "redirect:/admin/product/edit/" + product.getProductID(); // Sau khi cập nhật, chuyển hướng về trang chỉnh sửa
        }

        // Xóa sản phẩm
        @RequestMapping("/admin/product/delete/{id}")
        public String delete(@PathVariable("id") String id) {
            productDao.deleteById(id); // Xóa sản phẩm khỏi database
            return "redirect:/admin/product"; // Sau khi xóa, chuyển hướng về trang tạo mới
        }
    }


