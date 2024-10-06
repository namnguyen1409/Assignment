<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/component/defaultHeader.jsp" %>

<div class="container px-4 py-5">
    <h2 class="pb-2 border-bottom">Chọn vai trò muốn truy cập</h2>
    <div class="row g-4 py-5 row-cols-1 row-cols-lg-3">

        <c:forEach items="${roles}" var="role">
            <div class="profile col">
                <div class="profile-icon d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-2 mb-3">
                <img src="<c:url value="/resources/images/" />${role.image}" alt="icon" width="40" height="40" class="d-inline-block align-top me-2">
                </div>
                <h3 class="fs-2 text-body-emphasis">${role.name}</h3>
                <p>${role.description}</p>
                <a href="<c:url value="/" />${role.code.toLowerCase()}" class="icon-link"> Truy cập <bi class="bi bi-chevron-right"></bi>
                </a>
            </div>
        </c:forEach>
    </div>
  </div>

<%@ include file="/WEB-INF/views/component/defaultFooter.jsp" %>