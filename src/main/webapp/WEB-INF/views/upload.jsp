<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Hệ Thống Đọc Dữ Liệu Ứng Viên</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .upload-card {
            background: #fff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 400px;
        }

        h2 {
            color: #1e3799;
            margin-bottom: 20px;
        }

        input[type="file"] {
            margin: 20px 0;
            padding: 10px;
            width: 100%;
            border: 1px dashed #3498db;
            border-radius: 5px;
            background: #f8f9fa;
        }

        button {
            background: #2ed573;
            color: white;
            border: none;
            padding: 12px 25px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
        }

        button:hover {
            background: #26af5f;
        }

        .alert {
            color: #ff4757;
            margin-bottom: 15px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="upload-card">
    <h2>Nhập Dữ Liệu Đầu Vào</h2>
    <c:if test="${not empty msgError}">
        <div class="alert">${msgError}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/import" method="post" enctype="multipart/form-data">
        <input type="file" name="file" accept=".txt" required />
        <button type="submit">Bắt đầu Import</button>
    </form>
</div>
</body>
</html>