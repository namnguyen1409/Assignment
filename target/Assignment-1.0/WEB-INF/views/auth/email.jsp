<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<c:url value="/" />">
    <title>Xác thực email</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/2fa.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
</head>
<body>
    <div class="container height-100 d-flex justify-content-center align-items-center">
    <div class="position-relative">
        <div class="card p-5 text-center">
            <form:form modelAttribute="otp" method="POST" action="">
                <h6>Vui lòng nhập mã xác thực <br> để xác minh tài khoản của bạn</h6>
                <div>Mã xác thực đã được gửi đến email <strong>${blurEmail}</strong>. Vui lòng kiểm tra hòm thư của bạn, bao gồm cả hòm thư rác.</div>
                <div class="text-danger text-center mt-2"> ${error} </div>
                <div id="otpDiv" class="inputs d-flex flex-row justify-content-center mt-2"> 
                    <form:input type="number" path="first" maxlength="1" class="m-2 text-center form-control rounded" />
                    <form:input type="number" path="second" maxlength="1" class="m-2 text-center form-control rounded" />
                    <form:input type="number" path="third" maxlength="1" class="m-2 text-center form-control rounded" />
                    <form:input type="number" path="fourth" maxlength="1" class="m-2 text-center form-control rounded" />
                    <form:input type="number" path="fifth" maxlength="1" class="m-2 text-center form-control rounded" />
                    <form:input type="number" path="sixth" maxlength="1" class="m-2 text-center form-control rounded" />
                </div>
                <div class="mt-4">
                    <button data-time="${timeRemaining}" type="button" class="btn btn-danger px-4" id="timeRemainingBtn"></button>
                    <button type="submit" class="btn btn-primary px-4 validate">Xác thực</button>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
<script src="<c:url value="/resources/js/2fa.js" />"></script>
<script src="<c:url value="/resources/js/email.js" />"></script>

</html>