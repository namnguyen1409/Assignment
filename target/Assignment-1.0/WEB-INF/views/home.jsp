<%-- 
    Document   : index1
    Created on : Sep 24, 2024, 10:40:38 PM
    Author     : namnguyen
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="h-100">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <base href="<c:url value="/" />">
        <title>Trang chủ</title>
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/home.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-icons.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/animate.min.css" />">
    </head>
    <body class="d-flex h-100 text-center text-white bg-dark">

        <div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
            <header class="mb-auto">
                <div class="animate__animated animate__fadeInDown" style="--animate-duration: 2s;">
                    <h3 class="float-md-start mb-0">${compName}</h3>
                    <nav class="nav nav-masthead justify-content-center float-md-end " >
                        <a class="nav-link active" aria-current="page" href="#">Trang chủ</a>
                        <a class="nav-link" href="<c:url value="/logout" />">Đăng xuất</a>
                    </nav>
                </div>
            </header>

            <main class="px-3">
                <h1 class="animate__animated animate__fadeInUp">Xin chào ${name}!</h1>
                <p class="lead animate__animated animate__fadeInDown" style="--animate-duration: 2s;">
                    Chào mừng bạn đến với trang chủ của chúng tôi. Chúng tôi hy vọng bạn sẽ có những trải nghiệm tuyệt vời tại đây. Nếu bạn cần bất kỳ sự trợ giúp nào, đừng ngần ngại liên hệ với chúng tôi.
                </p>
                <p class="lead animate__animated animate__heartBeat" style="--animate-duration: 2s;">
                    <a href="#" class="btn btn-lg btn-secondary fw-bold border-white bg-white">
                        Tiếp tục <i class="bi bi-arrow-right"></i>
                    </a>
                </p>
            </main>

            <footer class="mt-auto text-white-50">
                <p>
                    &copy; 2024 <a href="#" class="text-white">${compName}</a>. Bảo lưu mọi quyền.
                </p>
            </footer>
        </div>
    </body>
</html>
