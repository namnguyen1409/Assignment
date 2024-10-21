<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/component/user/sell/sellHeader.jsp" %>

<div class="container bd-navbar-toggle d-flex justify-content-between d-xl-none">
    <button class="btn btn-outline-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#bdSidebar" aria-controls="bdSidebar" aria-label="Toggle docs navigation">
        <i class="bi-list fs-4"></i>
        <span class="d-none fs-6 pe-1">Browse</span>
    </button>
</div>
<div class="container bd-gutter mt-3 my-md-4 bd-layout">
    <%@ include file="/WEB-INF/views/component/user/sell/aside.jsp" %>
    <main>
        <c:choose>
            <c:when test="${tab eq 'home'}">
                <%@ include file="/WEB-INF/views/component/user/sell/homeBody.jsp" %>
            </c:when>
            <c:when test="${tab eq 'addProduct'}">
                <%@ include file="/WEB-INF/views/component/user/sell/product/add.jsp" %>
            </c:when>
            <c:when test="${tab eq 'editProduct'}">
                <%@ include file="/WEB-INF/views/component/user/sell/product/edit.jsp" %>
            </c:when>
            <c:when test="${tab eq 'listAllProduct' || tab eq 'listActiveProduct' || tab eq 'listHideProduct' || tab eq 'listInActiveProduct' || tab eq 'listDeletedProduct'}">
                <%@ include file="/WEB-INF/views/component/user/sell/product/list.jsp" %>
            </c:when>
            <c:when test="${tab eq 'listDiscount'}">
                <%@ include file="/WEB-INF/views/component/user/sell/discount/list.jsp" %>
            </c:when>



        </c:choose>
    </main>


</div>

<%@ include file="/WEB-INF/views/component/modelInfo.jsp" %>

<%@ include file="/WEB-INF/views/component/defaultFooter.jsp" %>