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
<header class="py-3 mb-3 container border-bottom ">
    <div class="container-fluid d-grid gap-3 align-items-center" style="grid-template-columns: 4fr 1fr;">
        <div class="d-flex align-items-center">
            <a href="home" class="d-flex align-items-center mb-3 mb-lg-0 me-lg-auto link-body-emphasis text-decoration-none">
                <img src="<c:url value="/resources/images/" />${compLogo}" alt="logo" width="40" height="40" class="d-inline-block align-top rounded-circle me-2">
                <span class="fs-2 fw-bold">${compName}</span>
            </a>
        </div>
      <div class="d-flex align-items-center">
        <div class="flex-shrink-0 dropdown">
          <a href="#" class="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
            <span class="d-none d-sm-inline me-2">${simpUser.username}</span>
            <img src="<c:url value="/resources/images/avatar/" />${simpUser.avatar}" alt="mdo" width="32" height="32" class="rounded-circle">
          </a>
          <ul class="dropdown-menu text-small shadow">
            <li><a class="dropdown-item" href="<c:url value="/home" />">Trang chủ</a></li>
            <li><a class="dropdown-item" href="<c:url value="/setting" />">Cài đặt</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="<c:url value="/setting" />">Đăng xuất</a></li>
          </ul>
        </div>
      </div>
    </div>
  </header>