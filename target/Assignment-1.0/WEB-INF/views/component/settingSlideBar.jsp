<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="d-none d-xl-block col-md-5 col-xl-4 d-flex flex-column flex-shrink-0 p-3 bg-light">
    <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
        <i class="bi bi-gear ms-2 me-2 fs-4"></i>
        <span class="fs-4">Cài đặt</span>
    </a>
    <hr>
    <ul class="nav nav-pills flex-column mb-auto">
        <li class="nav-item">
            <a href="<c:url value="/setting/general" />" class="nav-link ${tab eq 'general'?'active':''}">
            <i class="bi bi-person-circle me-2 fs-5"></i>
                Chung
            </a>
        </li>
        <li class="nav-item">
            <a href="<c:url value="/setting/contact" />" class="nav-link ${tab eq 'contact'?'active':''}">
                <i class="bi bi-info-circle me-2 fs-5"></i>
                Thông tin liên hệ
            </a>
        </li>
        <li class="nav-item">
            <a href="<c:url value="/setting/address" />" class="nav-link ${tab eq 'address'?'active':''}">
                <i class="bi bi-geo-alt me-2 fs-5"></i>
                Địa chỉ
            </a>
        </li>
        <li class="nav-item">
            <a href="<c:url value="/setting/security" />" class="nav-link ${tab eq 'security'?'active':''}">
                <i class="bi bi-shield-lock-fill me-2 fs-5"></i>
                Mật khẩu và bảo mật
            </a>
        </li>
    </ul>
</div>

<div class="d-md-block d-lg-block d-xl-none col-12 d-flex flex-wrap flex-shrink-0 p-3 bg-light">
    <ul class="nav nav-pills row mb-auto w-100">
        <li class="nav-item col-3">
            <a href="<c:url value="/setting/general" />" class="nav-link text-center p-2 ${tab eq 'general'?'active':''}">
                <i class="bi bi-person-circle fs-3 d-block mx-auto"></i>
            </a>
        </li>
        <li class="nav-item col-3">
            <a href="<c:url value="/setting/contact" />" class="nav-link text-center p-2 ${tab eq 'contact'?'active':''}">
                <i class="bi bi-info-circle fs-3 d-block mx-auto"></i>
            </a>
        </li>
        <li class="nav-item col-3">
            <a href="<c:url value="/setting/address" />" class="nav-link text-center p-2 ${tab eq 'address'?'active':''}">
                <i class="bi bi-geo-alt fs-3 d-block mx-auto"></i>
            </a>
        </li>
        <li class="nav-item col-3">
            <a href="<c:url value="/setting/security" />" class="nav-link text-center p-2 ${tab eq 'security'?'active':''}">
                <i class="bi bi-shield-lock-fill fs-3 d-block mx-auto"></i>
            </a>
        </li>
    </ul>
    <hr class="w-100">
</div>