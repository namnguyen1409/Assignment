<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi" class="h-100">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <base href="<c:url value="/" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-docs.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-icons.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/animate.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/component/header.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/component/settingBody.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/component/role.css" />">
    <script>
      const baseHref = document.getElementsByTagName("base")[0].href;
    </script>
    <title>Cài đặt</title>
</head>
<body class="h-100">
<header class="py-3 mb-3 container border-bottom">
    <div class="container-fluid d-grid gap-3 align-items-center" style="grid-template-columns: 4fr 1fr;">
        <div class="d-flex align-items-center me-3">
            <a href="home" class="d-flex align-items-center mb-3 mb-lg-0 me-lg-auto link-body-emphasis text-decoration-none me-2">
                <img src="<c:url value="/resources/images/" />${compLogo}" alt="logo" width="40" height="40" class="d-inline-block align-top rounded-circle me-2">
                <span class="fs-2 fw-bold">${compName}</span>
            </a>
            <div class="d-none d-lg-block flex-grow-1">
                <div class="d-flex">
                    <form class="ms-3 me-3 flex-grow-1">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Tìm kiếm sản phẩm" aria-label="Tìm kiếm sản phẩm" aria-describedby="button-addon2">
                            <button class="btn btn-outline-secondary" type="button" id="button-addon2"><i class="bi bi-search"></i></button>
                        </div>
                    </form>
                    <a href="<c:url value='/user/purchase/cart' />" class="text-body position-relative">
                        <i class="bi bi-cart" style="font-size: 1.5rem;"></i>
                    </a>
                </div>
            </div>
        </div>
        <div class="d-flex align-items-center">
            <!-- Người dùng -->
            <div class="flex-shrink-0 dropdown">
                <a class="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                    <span class="d-none d-sm-inline me-2">${simpUser.username}</span>
                    <img src="<c:url value="/resources/images/avatar/" />${simpUser.avatar}" alt="mdo" width="32" height="32" class="rounded-circle">
                </a>
                <ul class="dropdown-menu text-small shadow">
                    <!-- Đơn hàng -->
                    <li><a class="dropdown-item" href="<c:url value="/purchase" />">Đơn mua</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="<c:url value="/home" />">Trang chủ</a></li>
                    <li><a class="dropdown-item" href="<c:url value="/setting" />">Cài đặt</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="<c:url value="/logout" />">Đăng xuất</a></li>
                </ul>
            </div>
        </div>
    </div>
    <!-- Hàng thứ hai với thanh tìm  kiếm và giỏ hàng -->
    <div class="container-fluid mt-3 d-lg-none">
        <div class="container d-flex justify-content-between align-items-center">
            <!-- Thanh tìm kiếm -->
            <form class="d-flex flex-grow-1 me-3" action="<c:url value='/search' />" method="get">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Tìm kiếm sản phẩm" aria-label="Tìm kiếm sản phẩm" aria-describedby="button-addon2">
                    <button class="btn btn-outline-secondary" type="button" id="button-addon2"><i class="bi bi-search"></i></button>
                </div>
            </form>
            <!-- Giỏ hàng -->
            <div>
                <a href="<c:url value='/user/purchase/cart' />" class="text-body position-relative">
                    <i class="bi bi-cart" style="font-size: 1.5rem;"></i>
                </a>
            </div>
        </div>
    </div>
</header>
