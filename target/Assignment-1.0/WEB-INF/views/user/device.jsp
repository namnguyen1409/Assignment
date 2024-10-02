<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        Thu thập thông tin thiết bị
    </title>
    <base href="<c:url value="/" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/device.css" />">
    <script src="<c:url value="/resources/js/fingerprint.min.js" />"></script>
</head>
<body>
    <%-- using javaScript and form to get device info --%>

    <%-- ensure js on --%>
    <noscript>
        <h1>
            Bạn cần bật JavaScript để tiếp tục
        </h1>
    </noscript>

    <h1>Kiểm tra bảo mật</h1>
    <h3 class="countDown"></h3>
    <h3>Đang thu thập thông tin thiết bị...</h3>
    <br>
    <div class="container">
        <div class="dot"></div>
        <div class="dot"></div>
        <div class="dot"></div>
        <div class="dot"></div>
    </div>

    <div class="hiddenForm" style="display:none;">
        <form:form modelAttribute="userDevice" method="POST" action="">
        <form:input path="screenSize" id="screenSize" />
        <form:input path="canvasFingerprint" id="canvasFingerprint" />
        <%-- submit --%>
        <button type="submit" class="btn btn-primary">Submit</button>
        </form:form>
    </div>
    

    <script>
        // get device info
        var userDevice = {
            screenSize: window.screen.width + "x" + window.screen.height,
            canvasFingerprint: ""
        };
        var fingerprint = new Fingerprint();
        var uniqueId = fingerprint.get();
        userDevice.canvasFingerprint = uniqueId;

        // submit form
        var form = document.querySelector(".hiddenForm form");
        form.screenSize.value = userDevice.screenSize;
        form.canvasFingerprint.value = userDevice.canvasFingerprint;

        // countdown then submit form
        var countDown = 5;
        var countDownElement = document.querySelector(".countDown");
        var interval = setInterval(() => {
            countDownElement.innerHTML = countDown;
            countDown--;
            if (countDown < 0) {
                clearInterval(interval);
                form.submit();
            }
        }, 1000);

    </script>

</body>
</html>