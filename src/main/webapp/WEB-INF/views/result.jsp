<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Kết Quả Phân Tích Dữ Liệu Ứng Viên</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f0f2f5;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            min-height: 100vh;
            margin: 0;
            padding: 40px 20px;
            box-sizing: border-box;
        }

        .result-container {
            width: 100%;
            max-width: 900px;
            background: #ffffff;
            padding: 35px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            border-top: 5px solid #ff4757;
        }

        .result-header {
            border-left: 5px solid #1e3799;
            padding-left: 12px;
            margin-bottom: 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .result-header h3 {
            margin: 0;
            color: #2c3e50;
            font-size: 22px;
        }

        .result-header p {
            margin: 5px 0 0 0;
            color: #7f8c8d;
            font-size: 13px;
        }

        .btn-back {
            background: #1e3799;
            color: white;
            text-decoration: none;
            padding: 10px 18px;
            font-size: 14px;
            font-weight: bold;
            border-radius: 5px;
            transition: background 0.2s;
        }

        .btn-back:hover {
            background: #0c2461;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            border-radius: 8px;
            overflow: hidden;
            margin-top: 15px;
        }

        th {
            background-color: #2c3e50;
            color: #ffffff;
            text-align: center;
            padding: 12px 8px;
            font-size: 13px;
            border: 1px solid #dcdde1;
            text-transform: uppercase;
        }

        td {
            padding: 12px 8px;
            border: 1px solid #dcdde1;
            font-size: 14px;
            text-align: center;
            color: #333;
        }

        tr:nth-child(even) {
            background-color: #f8f9fa;
        }

        tr:hover {
            background-color: #fff2f2;
            transition: background 0.15s ease;
        }

        .badge-line {
            background-color: #ff4757;
            color: white;
            padding: 3px 9px;
            border-radius: 12px;
            font-weight: bold;
            font-size: 12px;
        }

        .danger-marker {
            background-color: #ff7675;
            color: #d63031;
            padding: 3px 12px;
            border-radius: 4px;
            font-weight: bold;
            font-size: 13px;
            display: inline-block;
        }

        .success-box {
            background: #dff9fb;
            border: 1px solid #c7ecee;
            padding: 30px;
            text-align: center;
            border-radius: 8px;
            color: #009432;
            font-weight: bold;
            font-size: 16px;
        }
    </style>
</head>
<body>

<div class="result-container">
    <div class="result-header">
        <div>
            <h3>DANH SÁCH CÁC DÒNG LỖI</h3>
            <p>Các dòng dữ liệu đúng quy tắc đã được đồng bộ tự động vào database <strong>employee</strong>.</p>
        </div>
        <a href="${pageContext.request.contextPath}/" class="btn-back">⬅ Tải File Khác</a>
    </div>

    <c:choose>
        <c:when test="${not empty errors}">
            <table>
                <thead>
                <tr>
                    <th>Dòng</th>
                    <th>Sai định dạng<br>Ngày sinh</th>
                    <th>Sai định dạng<br>Số điện thoại</th>
                    <th>Sai định dạng<br>Địa chỉ email</th>
                    <th>Sai định dạng<br>Số năm kinh nghiệm</th>
                    <th>Thừa dữ liệu</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="err" items="${errors}">
                    <tr>
                        <td><span class="badge-line">${err.lineNum}</span></td>
                        <td><c:if test="${err.birthDateErr == 'Yes'}"><span class="danger-marker">Yes</span></c:if></td>
                        <td><c:if test="${err.phoneErr == 'Yes'}"><span class="danger-marker">Yes</span></c:if></td>
                        <td><c:if test="${err.emailErr == 'Yes'}"><span class="danger-marker">Yes</span></c:if></td>
                        <td><c:if test="${err.expErr == 'Yes'}"><span class="danger-marker">Yes</span></c:if></td>
                        <td><c:if test="${err.thuaDuLieuErr == 'Yes'}"><span class="danger-marker">Yes</span></c:if></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="success-box">
                🎉 Xuất sắc! Tập tin không chứa bất kỳ dòng lỗi nào. Toàn bộ dữ liệu ứng viên đã được ghi nhận vào cơ sở dữ liệu!
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>