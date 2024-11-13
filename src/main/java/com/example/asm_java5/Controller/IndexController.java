package com.example.asm_java5.Controller;


import com.example.asm_java5.Service.Format;
import com.example.asm_java5.Dao.ProductDao;
import com.example.asm_java5.Entity.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    ProductDao productDao;

    @RequestMapping("/index")
    public String index(Model model, @RequestParam(defaultValue = "0") int p , HttpSession session)
    {
        Object fullnameObj = session.getAttribute("fullname");
        if (fullnameObj != null) {
            String fullname = fullnameObj.toString();
            model.addAttribute("fullname", fullname);
        }
        System.out.println("thanh cong"+ fullnameObj);
        Pageable pageable = PageRequest.of(p, 7);  // Kích thước trang là 5 sản phẩm
        Page<Product> page = productDao.findAll(pageable);
        page.getContent().forEach(product -> {
            product.setFormattedPrice(Format.formatCurrency(product.getPrice()));
        });
        model.addAttribute("page", page);  // Đưa đối tượng Page vào model để sử dụng trên view
        model.addAttribute("currentPage", p);  // Thêm thông tin trang hiện tại
        return "index";
    }

}
