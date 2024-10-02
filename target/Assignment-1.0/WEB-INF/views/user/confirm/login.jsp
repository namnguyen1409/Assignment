<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác nhận truy cập trang đăng nhập</title>
</head>
<body>
    <div class="confirm">
        <form method="post">
            <h1>Xác nhận hành động của bạn</h1>
            <p>Bạn có <strong>chắc chắn</strong> rằng bạn muốn truy cập vào trang đăng nhập?</p>
            <p>Bạn sẽ bị đăng xuất khỏi tài khoản hiện tại nếu bạn đồng ý.</p>
            <button class="cancel">Quay lại</button>
            <button type="submit" autofocus>Đồng ý</button>
        </form>
    </div>
</body>

<script>
    document.querySelector('.cancel').addEventListener('click', () => {
        window.history.back();
    });
</script>

</html>

