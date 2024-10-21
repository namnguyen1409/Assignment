<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<c:url value="/" />">
    <title>Xác thực 2 yếu tố</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/2fa.css" />">
</head>
<body>
    <div class="container height-100 d-flex justify-content-center align-items-center">
    <div class="position-relative">
        <div class="card p-2 text-center">
            <form:form modelAttribute="otp" method="POST" action="">
                <h6>Vui lòng nhập mã xác thực <br> để xác minh tài khoản của bạn</h6>
                <div> <span>Mã xác minh được tạo trong ứng dụng xác thực bạn đã thiết lập trước đó</div>
                <div class="text-danger text-center mt-2"> ${error} </div>
                <div id="otpDiv" class="inputs d-flex flex-row justify-content-center mt-2"> 
                    <form:input type="password" path="first" maxlength="1" class="m-2 text-center form-control rounded" />
                    <form:input type="password" path="second" maxlength="1" class="m-2 text-center form-control rounded" />
                    <form:input type="password" path="third" maxlength="1" class="m-2 text-center form-control rounded" />
                    <form:input type="password" path="fourth" maxlength="1" class="m-2 text-center form-control rounded" />
                    <form:input type="password" path="fifth" maxlength="1" class="m-2 text-center form-control rounded" />
                    <form:input type="password" path="sixth" maxlength="1" class="m-2 text-center form-control rounded" />
                </div>
                <div class="mt-4"> <button type="submit" class="btn btn-danger px-4 validate">Tiếp tục</button> </div>
            </form:form>
        </div>
    </div>
</div>
</body>
<script src="<c:url value="/resources/js/2fa.js" />"></script>

</html>