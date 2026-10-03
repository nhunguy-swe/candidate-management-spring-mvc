# HỆ THỐNG QUẢN LÝ ỨNG VIÊN TUYỂN DỤNG

## 1. Giới thiệu

Hệ thống được xây dựng nhằm quản lý thông tin ứng viên tuyển dụng của công ty. Dữ liệu ứng viên được nhập từ giao diện Web và lưu vào cơ sở dữ liệu.

Ứng viên được chia thành 3 loại:

* Experience Candidate (Ứng viên có kinh nghiệm)
* Fresher Candidate (Ứng viên mới tốt nghiệp)
* Intern Candidate (Sinh viên thực tập)

Hệ thống hỗ trợ:

* Quản lý ứng viên
* Kiểm tra dữ liệu đầu vào
* Tìm kiếm ứng viên
* Thống kê ứng viên theo loại

---

## 2. Công nghệ sử dụng

* Java 17+
* Spring MVC
* Hibernate ORM
* MySQL
* Apache Tomcat 10
* JSP/JSTL
* Maven
* Bootstrap 5

---

## 3. Thiết kế cơ sở dữ liệu

### Bảng CANDIDATE

| Tên cột       | Kiểu dữ liệu             | Mô tả         |
| ------------- | ------------------------ | ------------- |
| candidateId   | INT (PK, AUTO_INCREMENT) | Mã ứng viên   |
| candidateType | INT                      | 1, 2, 3       |
| fullName      | VARCHAR(100)             | Họ tên        |
| birthDay      | DATE                     | Ngày sinh     |
| address       | VARCHAR(255)             | Địa chỉ       |
| homeTown      | VARCHAR(255)             | Quê quán      |
| phone         | VARCHAR(20)              | Số điện thoại |
| email         | VARCHAR(100)             | Email         |

---

### Bảng EXPERIENCE

| Tên cột         | Kiểu dữ liệu             |
| --------------- | ------------------------ |
| experienceId    | INT (PK, AUTO_INCREMENT) |
| candidateId     | INT (FK)                 |
| expInYear       | DECIMAL(4,1)             |
| proSkill        | VARCHAR(100)             |
| recentWorkPlace | VARCHAR(255)             |

---

### Bảng FRESHER

| Tên cột        | Kiểu dữ liệu             |
| -------------- | ------------------------ |
| fresherId      | INT (PK, AUTO_INCREMENT) |
| candidateId    | INT (FK)                 |
| graduationDate | DATE                     |
| graduationRank | VARCHAR(50)              |
| education      | VARCHAR(255)             |

---

### Bảng INTERN

| Tên cột                | Kiểu dữ liệu             |
| ---------------------- | ------------------------ |
| internId               | INT (PK, AUTO_INCREMENT) |
| candidateId            | INT (FK)                 |
| major                  | VARCHAR(100)             |
| semester               | VARCHAR(50)              |
| universityName         | VARCHAR(255)             |
| expectedGraduationDate | DATE                     |

---

## 4. Chức năng hệ thống

### 4.1 Quản lý Ứng viên

Cho phép thêm mới ứng viên:

Thông tin chung:

* Họ tên
* Ngày sinh
* Địa chỉ thường trú
* Quê quán
* Số điện thoại
* Email
* Loại ứng viên

---

### 4.2 Ứng viên có kinh nghiệm

Khi chọn:

```text
Experience Candidate
```

Hiển thị thêm:

* Số năm kinh nghiệm
* Kỹ năng chuyên môn
* Nơi làm việc gần nhất

#### Validation

**Số năm kinh nghiệm**

* > 0
* < 100
* Làm tròn 1 chữ số thập phân

Ví dụ:

```java
@DecimalMin("0.1")
@DecimalMax("99.9")
private Double expInYear;
```

---

### 4.3 Ứng viên Fresher

Khi chọn:

```text
Fresher Candidate
```

Hiển thị:

* Ngày tốt nghiệp
* Xếp loại
* Trường tốt nghiệp

---

### 4.4 Ứng viên Intern

Khi chọn:

```text
Intern Candidate
```

Hiển thị:

* Chuyên ngành
* Học kỳ
* Trường đang học
* Ngày dự kiến tốt nghiệp

---

## 5. Validation dữ liệu

### Họ tên

```java
@NotBlank
private String fullName;
```

---

### Ngày sinh

```java
@Past
private LocalDate birthDay;
```

---

### Số điện thoại

* Tối thiểu 7 số

```java
@Pattern(
 regexp = "^\\d{7,}$",
 message = "Số điện thoại không hợp lệ"
)
private String phone;
```

---

### Email

```java
@Email
private String email;
```

---

### Loại ứng viên

```java
@NotNull
private Integer candidateType;
```

Giá trị:

| Mã | Loại       |
| -- | ---------- |
| 1  | Experience |
| 2  | Fresher    |
| 3  | Intern     |

---

## 6. Chức năng tìm kiếm

### Form 1: Tìm kiếm ứng viên

Cho phép tìm theo:

* Họ tên
* Email
* Số điện thoại

Kết quả hiển thị:

| Candidate ID |
| Full Name |
| Birthday |
| Phone |
| Email |
| Candidate Type |

---

### Form 2: Tìm kiếm chi tiết hồ sơ

Hiển thị:

* Họ tên
* Loại ứng viên

Nếu Experience:

* Số năm kinh nghiệm
* Kỹ năng
* Công ty gần nhất

Nếu Fresher:

* Ngày tốt nghiệp
* Xếp loại
* Trường tốt nghiệp

Nếu Intern:

* Chuyên ngành
* Học kỳ
* Trường học
* Ngày dự kiến tốt nghiệp

---

## 7. Mapping Hibernate

### Candidate → Experience

```java
@OneToOne(mappedBy = "candidate")
private Experience experience;
```

---

### Candidate → Fresher

```java
@OneToOne(mappedBy = "candidate")
private Fresher fresher;
```

---

### Candidate → Intern

```java
@OneToOne(mappedBy = "candidate")
private Intern intern;
```

---

### Experience

```java
@OneToOne
@JoinColumn(name = "candidate_id")
private Candidate candidate;
```

---

## 8. Kiến trúc dự án

```text
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
│   ├── CandidateDAO
│
├── service
│   ├── CandidateService
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

Các trang chính:

### Trang chủ

* Dashboard

### Quản lý ứng viên

* Danh sách ứng viên
* Thêm mới ứng viên

### Tìm kiếm

* Tìm kiếm cơ bản
* Tìm kiếm chi tiết

Khuyến khích sử dụng:

* Bootstrap 5
* Responsive Layout
* DataTables
* Form Validation

---

## 10. Yêu cầu kỹ thuật

### Framework

* Spring MVC

### ORM

* Hibernate

### Database

* MySQL

### Server

* Apache Tomcat 10

### Coding Convention

* PascalCase cho class
* camelCase cho biến
* Controller → Service → DAO → Entity
* Code rõ ràng, dễ bảo trì

---

## 11. Kết luận

Hệ thống đáp ứng đầy đủ các yêu cầu:

✔ Quản lý Experience Candidate

✔ Quản lý Fresher Candidate

✔ Quản lý Intern Candidate

✔ Validation dữ liệu đầu vào

✔ Tìm kiếm ứng viên

✔ Hiển thị hồ sơ chi tiết

✔ Áp dụng Spring MVC

✔ Áp dụng Hibernate ORM

✔ Lưu trữ MySQL

✔ Chạy trên Tomcat 10

✔ Tuân thủ Java Coding Convention
