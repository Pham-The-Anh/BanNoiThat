create database QuanLy_HangNoiThat
use QuanLy_HangNoiThat
SELECT * FROM cart 


-- Tạo bảng Customers (khách hàng)
CREATE TABLE Account (      
    Fullname NVARCHAR(100) NOT NULL,         
    Email NVARCHAR(100) NOT NULL primary key,     
    Phone NVARCHAR(20),                       
    Photo VARCHAR(250),                       
    Address NVARCHAR(255),                   
    City NVARCHAR(100),                      
    Country NVARCHAR(100), 
    Password NVARCHAR(255) NOT NULL,         
    Admin BIT,                                -- Vai trò người dùng: 1 = Admin, 0 = User
    Gender NVARCHAR(10),
	Date_Create DATETIME DEFAULT GETDATE()
);
CREATE TABLE Mail (
	ID INT  IDENTITY(1,1)  PRIMARY KEY,
    from_email NVARCHAR(100) ,
    to_email VARCHAR(255) not null,
    subject VARCHAR(255),
    body TEXT,
    Date_Create DATETIME DEFAULT GETDATE(),
	attachments varchar(200),
    status bit,
	FOREIGN KEY (from_email) REFERENCES Account(Email) ON DELETE CASCADE
);
select * from account
INSERT INTO Account (Fullname, Email, Phone, Address, City, Country, Password, Admin, Gender, Date_Create)
VALUES 
('Nguyễn Văn C', 'admin@gmail.com', '1234567890', N'123 Đường Lê Đức Thọ', N'TP HCM', N'Việt Nam', '123', 0, N'Nam', GETDATE()),
(N'Nguyễn Thị A', 'user1@example.com', '0987654321', N'456 Đường Quang Trung', N'TP HCM', N'Việt Nam', '123', 0, N'Nữ', GETDATE()),
(N'Trằn Văn B', 'user2@example.com', '0123456789', N'789 Đường Thống Nhất', N'TP HCM', N'Việt Nam', '123', 0, N'Nam', GETDATE()),
(N'Duy Tân Ps27768', 'tanvndps27768@fpt.edu.vn', '0123456789', N'789 Đường Thống Nhất', N'TP HCM', N'Việt Nam', '123', 0, N'Nam', GETDATE()),
(N'Hoài Như Khùng', 'nhuphpc07930@fpt.edu.vn', '0123456789', N'789 Đường Thống Nhất', N'TP HCM', N'Việt Nam', '123', 0, N'Nữ', GETDATE()),
(N'Phạm Thế Anh', 'anhptps33436@fpt.edu.vn', '0123456789', N'789 Đường Thống Nhất', N'TP HCM', N'Việt Nam', '123', 0, N'Nữ', GETDATE()),
(N'Võ Nguyễn Duy Thịnh', 'vonguyenduythinh@gmail.com', '0123456789', N'789 Đường Thống Nhất', N'TP HCM', N'Việt Nam', '123', 0, N'Nữ', GETDATE()),
(N'Anh Thư', 'doanhoaianhthu0202@gmail.com', '0123456789', N'789 Đường Thống Nhất', N'TP HCM', N'Việt Nam', '123', 0, N'Nữ', GETDATE()),
(N'Võ Nguyễn Duy Tân', 'vonguyenduytan12cb9@gmail.com', '0123456789', N'789 Đường Thống Nhất', N'TP HCM', N'Việt Nam', '123', 1, N'Nữ', GETDATE());
CREATE TABLE Contact (
    ID INT PRIMARY KEY IDENTITY(1,1),
    Email nvarchar(100),  -- Thêm trường AccountID để liên kết với bảng Account
    Phone NVARCHAR(100) NOT NULL,
    Message NVARCHAR(500) NOT NULL,
    Created_At DATETIME DEFAULT GETDATE(),
	Status bit, 
    FOREIGN KEY (Email) REFERENCES Account(Email) ON DELETE CASCADE  -- Khóa ngoại liên kết với bảng Account
);

-- Tạo bảng Categories (danh mục sản phẩm)
CREATE TABLE Categories (
    ID VARCHAR(10) PRIMARY KEY ,
    Name NVARCHAR(100) NOT NULL
);

-- Tạo bảng Products (sản phẩm)
CREATE TABLE Products (
    ID VARCHAR(10) PRIMARY KEY ,
    Tiltle NVARCHAR(100) NOT NULL,
    Category VARCHAR(10),
    Price int NOT NULL,
    Description NVARCHAR(150) NOT NULL,
    Image VARCHAR(255),
    FOREIGN KEY (Category) REFERENCES Categories(ID) ON DELETE SET NULL
);
-- Chèn dữ liệu vào bảng Categories (loại hàng)
INSERT INTO Categories (ID, Name)
VALUES 
('C001', N'Bàn ăn'),
('C002', N'Bàn làm việc'),
('C003', N'Ghế Sofa'),
('C004', N'Ghế nhà khách');


-- Chèn dữ liệu vào bảng Products (sản phẩm)
INSERT INTO Products (ID, Tiltle, Category, Price, Description, Image)
VALUES 
('P001', N'Ghế Nordic', 'C004', 500000, N'Bàn làm việc chất liệu gỗ sồi cao cấp', 'product-1.png'),
('P002', N'Ghế Kruzo Aero', 'C004', 120022, N'Ghế xoay văn phòng có đệm mút êm ái', 'product-2.png'),
('P003', N'Ghế Ergonomic', 'C004', 120022, N'Ghế xoay văn phòng có đệm mút êm ái', 'product-3.png'),
('P004', N'Ghế Sofa Nordic', 'C003', 122000, N'Ghế sofa tự nhiên, kích thước 1m8 x 2m', 'sofa.png'),
('P005', N'Sofa Bolero 3 chỗ + Đôn M3 vải xanh MB408', 'C003', 199022000, N'Ghế sofa tự nhiên, kích thước 1m8 x 2m', 'sofa-2.jpg'),
('P006', N'Sofa Coastal 1 chỗ vải VACT7502', 'C003', 23002000, N'Ghế sofa tự nhiên, kích thước 1m8 x 2m', 'sofa-3.jpg'),
('P007', N'Sofa Coastal 2 chỗ M2 VACT7502', 'C003', 3290000, N'Ghế sofa tự nhiên, kích thước 1m8 x 2m', 'sofa-4.jpg'),
('P008', N'Bàn làm việc Fence', 'C002', 120022, N'Ghế xoay văn phòng có đệm mút êm ái', 'banlamviec-1.jpg'),
('P009', N'Bàn làm việc Finn 260011', 'C002', 120022, N'Ghế xoay văn phòng có đệm mút êm ái', 'banlamviec-2.jpg'),
('P0010', N'Bàn làm việc Sakao', 'C002', 3000000, N'Ghế xoay văn phòng có đệm mút êm ái', 'banlamviec-3.jpg'),
('P0011', N'Bàn làm việc Maxine', 'C002', 4900000, N'Ghế xoay văn phòng có đệm mút êm ái', 'banlamviec-4.jpg');
-- Tạo bảng Orders (đơn hàng)
CREATE TABLE Orders (
    ID INT PRIMARY KEY IDENTITY(1,1),
    Account_ID nvarchar(100),
    Order_Date DATE NOT NULL,
    Total_Amount DECIMAL(10, 3) NOT NULL,
    Status VARCHAR(50) NOT NULL,
    FOREIGN KEY (Account_ID) REFERENCES Account(Email) ON DELETE CASCADE
);

-- Tạo bảng OrderDetails (chi tiết đơn hàng)
CREATE TABLE OrderDetails (
    ID INT PRIMARY KEY IDENTITY(1,1),
    Order_id INT,
    Product VARCHAR(10),
    Quantity INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (Order_id) REFERENCES Orders(ID) ON DELETE CASCADE,
    FOREIGN KEY (Product) REFERENCES Products(ID) ON DELETE CASCADE
);
-- Tạo bảng Cart (Giỏ hàng)
CREATE TABLE Cart (
    ID INT PRIMARY KEY IDENTITY(1,1),
    Account_ID NVARCHAR(100),  -- Tài khoản người dùng
    Product_ID VARCHAR(10),    -- Mã sản phẩm
    Quantity INT NOT NULL,     -- Số lượng sản phẩm
    Date_Added DATETIME DEFAULT GETDATE(), -- Ngày thêm vào giỏ
    FOREIGN KEY (Account_ID) REFERENCES Account(Email) ON DELETE CASCADE,  -- Liên kết với bảng Account
    FOREIGN KEY (Product_ID) REFERENCES Products(ID) ON DELETE CASCADE     -- Liên kết với bảng Products
);
select * from cart
	SELECT * FROM Cart WHERE Account_ID = 'vonguyenduytan12cb9@gmail.com';
