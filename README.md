# HỆ THỐNG QUẢN LÝ ỨNG VIÊN TUYỂN DỤNG (Candidate Management)

<p>
  <img src="https://img.shields.io/badge/Java-17%2B-orange" alt="Java">
  <img src="https://img.shields.io/badge/Spring%20MVC-brightgreen" alt="Spring MVC">
  <img src="https://img.shields.io/badge/Hibernate-ORM-blue" alt="Hibernate">
  <img src="https://img.shields.io/badge/MySQL-Database-4479A1" alt="MySQL">
  <img src="https://img.shields.io/badge/Tomcat-10-yellow" alt="Tomcat 10">
</p>

## 1. Giới thiệu

Hệ thống được xây dựng nhằm quản lý thông tin ứng viên tuyển dụng của công ty. Dữ liệu ứng viên được nhập từ giao diện Web và lưu vào cơ sở dữ liệu.

Ứng viên được chia thành 3 loại:

- **Experience Candidate** (Ứng viên có kinh nghiệm)
- **Fresher Candidate** (Ứng viên mới tốt nghiệp)
- **Intern Candidate** (Sinh viên thực tập)

Hệ thống hỗ trợ: Quản lý ứng viên, Kiểm tra dữ liệu đầu vào, Tìm kiếm ứng viên, Thống kê ứng viên theo loại.

---

## 2. Công nghệ sử dụng

- Java 17+
- Spring MVC
- Hibernate ORM
- MySQL
- Apache Tomcat 10
- JSP/JSTL
- Maven
- Bootstrap 5

---

## 3. Thiết kế cơ sở dữ liệu

### Bảng CANDIDATE

| Tên cột       | Kiểu dữ liệu              | Mô tả          |
| ------------- | ------------------------- | ---------------|
| candidateId   | INT (PK, AUTO_INCREMENT)  | Mã ứng viên       |
| candidateType | INT                          | 1, 2, 3              |
| fullName      | VARCHAR(100)                    | Họ tên                   |
| birthDay      | DATE                               | Ngày sinh                     |
| address       | VARCHAR(255)                          | Địa chỉ                          |
| homeTown      | VARCHAR(255)                             | Quê quán                            |
| phone         | VARCHAR(20)                                 | Số điện thoại                          |
| email         | VARCHAR(100)                                   | Email                                     |

### Bảng EXPERIENCE

| Tên cột         | Kiểu dữ liệu              |
| --------------- | ------------------------- |
| experienceId    | INT (PK, AUTO_INCREMENT)  |
| candidateId     | INT (FK)                    |
| expInYear       | DECIMAL(4,1)                   |
| proSkill        | VARCHAR(100)                       |
| recentWorkPlace | VARCHAR(255)                           |

### Bảng FRESHER

| Tên cột        | Kiểu dữ liệu              |
| -------------- | ------------------------- |
| fresherId      | INT (PK, AUTO_INCREMENT)  |
| candidateId    | INT (FK)                    |
| graduationDate | DATE                           |
| graduationRank | VARCHAR(50)                        |
| education      | VARCHAR(255)                           |

### Bảng INTERN

| Tên cột                | Kiểu dữ liệu              |
| ----------------------- | ------------------------- |
| internId                | INT (PK, AUTO_INCREMENT)  |
| candidateId             | INT (FK)                    |
| major                   | VARCHAR(100)                   |
| semester                | VARCHAR(50)                        |
| universityName          | VARCHAR(255)                           |
| expectedGraduationDate  | DATE                                       |

---

## 4. Chức năng hệ thống

### 4.1 Quản lý Ứng viên

Cho phép thêm mới ứng viên với thông tin chung: Họ tên, Ngày sinh, Địa chỉ thường trú, Quê quán, Số điện thoại, Email, Loại ứng viên.

### 4.2 Ứng viên có kinh nghiệm (Experience Candidate)

Khi chọn `Experience Candidate`, hiển thị thêm: Số năm kinh nghiệm, Kỹ năng chuyên môn, Nơi làm việc gần nhất.

**Validation — Số năm kinh nghiệm:** > 0, < 100, làm tròn 1 chữ số thập phân.
```java
@DecimalMin("0.1")
@DecimalMax("99.9")
private Double expInYear;
```

### 4.3 Ứng viên Fresher

Khi chọn `Fresher Candidate`, hiển thị: Ngày tốt nghiệp, Xếp loại, Trường tốt nghiệp.

### 4.4 Ứng viên Intern

Khi chọn `Intern Candidate`, hiển thị: Chuyên ngành, Học kỳ, Trường đang học, Ngày dự kiến tốt nghiệp.

---

## 5. Validation dữ liệu

**Họ tên:**
```java
@NotBlank
private String fullName;
```

**Ngày sinh:**
```java
@Past
private LocalDate birthDay;
```

**Số điện thoại:** tối thiểu 7 số.
```java
@Pattern(regexp = "^\\d{7,}$", message = "Số điện thoại không hợp lệ")
private String phone;
```

**Email:**
```java
@Email
private String email;
```

**Loại ứng viên:**
```java
@NotNull
private Integer candidateType;
```
| Mã | Loại       |
| --- | ---------- |
| 1  | Experience |
| 2  | Fresher    |
| 3  | Intern     |

---

## 6. Chức năng tìm kiếm

**Form 1 — Tìm kiếm ứng viên:** theo Họ tên, Email, Số điện thoại. Kết quả: Candidate ID, Full Name, Birthday, Phone, Email, Candidate Type.

**Form 2 — Tìm kiếm chi tiết hồ sơ:** hiển thị Họ tên, Loại ứng viên, và chi tiết tương ứng theo loại (Experience / Fresher / Intern).

---

## 7. Mapping Hibernate

```java
// Candidate → Experience
@OneToOne(mappedBy = "candidate")
private Experience experience;

// Candidate → Fresher
@OneToOne(mappedBy = "candidate")
private Fresher fresher;

// Candidate → Intern
@OneToOne(mappedBy = "candidate")
private Intern intern;

// Experience → Candidate
@OneToOne
@JoinColumn(name = "candidate_id")
private Candidate candidate;
```

---

## 8. Kiến trúc dự án

```
src/main/java
│
├── controller
│   ├── CandidateController
│   └── SearchController
│
├── entity
│   ├── Candidate
│   ├── Experience
│   ├── Fresher
│   └── Intern
│
├── dao
│   └── CandidateDAO
│
├── service
│   └── CandidateService
│
├── validator
│   └── CandidateValidator
│
└── config
    ├── WebConfig
    ├── HibernateConfig
    └── AppInitializer
```

---

## 9. Giao diện

Các trang chính: Trang chủ (Dashboard), Quản lý ứng viên (Danh sách, Thêm mới), Tìm kiếm (cơ bản + chi tiết).

Khuyến khích sử dụng: Bootstrap 5, Responsive Layout, DataTables, Form Validation.

---

## 10. Yêu cầu kỹ thuật

- **Framework:** Spring MVC
- **ORM:** Hibernate
- **Database:** MySQL
- **Server:** Apache Tomcat 10
- **Coding Convention:** PascalCase cho class, camelCase cho biến, `Controller → Service → DAO → Entity`

---

## Bắt đầu (Getting Started)

### Yêu cầu

- JDK 17+
- MySQL
- Apache Tomcat 10
- IDE: IntelliJ IDEA / Eclipse

### Cài đặt

```bash
git clone https://github.com/nhunguy-swe/candidate-management-spring-mvc.git
cd candidate-management-spring-mvc
```

### Cấu hình Database

1. Tạo các bảng `CANDIDATE`, `EXPERIENCE`, `FRESHER`, `INTERN` theo thiết kế ở trên.
2. Cập nhật thông tin kết nối trong `HibernateConfig.java`.

> ⚠️ Không hard-code mật khẩu database trực tiếp trong code nếu push lên GitHub public — dùng biến môi trường hoặc file cấu hình đã thêm vào `.gitignore`.

> ℹ️ Repo có file `input.txt` ở thư mục gốc — nếu đây là dữ liệu mẫu để test, nên ghi chú rõ mục đích ở đây hoặc chuyển vào thư mục `database/`/`resources/` cho gọn cấu trúc.

### Chạy ứng dụng

```bash
mvn clean install
```

Deploy file `.war` lên **Apache Tomcat 10**, truy cập tại `http://localhost:8080/quan-ly-ung-vien/`.

---

## 11. Kết luận

Hệ thống đáp ứng đầy đủ các yêu cầu: Quản lý Experience Candidate · Quản lý Fresher Candidate · Quản lý Intern Candidate · Validation dữ liệu đầu vào · Tìm kiếm ứng viên · Hiển thị hồ sơ chi tiết · Áp dụng Spring MVC · Áp dụng Hibernate ORM · Lưu trữ MySQL · Chạy trên Tomcat 10 · Tuân thủ Java Coding Convention

---

## Tác giả

- GitHub: [@nhunguy-swe](https://github.com/nhunguy-swe)

---

## Giấy phép

Dự án này được thực hiện cho mục đích học tập/ôn thi cá nhân.
