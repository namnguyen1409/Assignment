<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/animate.min.css" />">
    <base href="<c:url value="/" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/confirm-login.css" />">
    <title>Xác nhận truy cập trang đăng nhập</title>
</head>
<body class="d-flex h-100 text-center bg-dark">
    <div class="confirm animate__animated animate__zoomInUp">
        <form method="post">
            <h1 class="text-dark">Xác nhận hành động của bạn</h1>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <p>Bạn có <strong>chắc chắn</strong> rằng bạn muốn tiếp tục?<br>
            Bạn sẽ bị đăng xuất khỏi tài khoản hiện tại nếu bạn đồng ý.</p>
            <button class="cancel text-primary">Quay lại</button>
            <button type="submit text-danger">Đồng ý</button>
        </form>
    </div>
</body>

<script>
    document.querySelector('.cancel').addEventListener('click', function() {
        // prevent form submission
        event.preventDefault();
        window.location.href = '<c:url value="/" />';
    });
</script>

</html>

