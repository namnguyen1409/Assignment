<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/component/defaultHeader.jsp" %>

<div class="container">
    <h1 class="text-center">Chọn tính năng bạn muốn dùng</h1>
    <%-- private String code;
    private String name;
    private String description;
    private Boolean selfRegister;
    private String image;
    private Boolean isActive;
    private Boolean hasPermission;
    private String disabledReason; --%>
    <div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
        <c:forEach items="${permissions}" var="permission">
            <div class="col-sm-12 col-md-6 col-lg-4">
                <div class="card mb-3">
                    <img src="<c:url value="/resources/images/" />${permission.image}" class="card-img-top" alt="${permission.name}">
                    <div class="card-body">
                        <h5 class="card-title">${permission.name}</h5>
                        <p class="card-text">${permission.description}</p>
                        <c:if test="${!permission.isActive}">
                            <p class="card-text text-danger">${permission.disabledReason}</p>
                        </c:if>
                        <c:choose>
                            <c:when test="${permission.isActive}">
                                <a href="<c:url value="/user/" />${permission.code.toLowerCase()}" class="btn btn-primary">Truy cập dịch vụ</a>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${permission.selfRegister}">
                                    <a href="user/register/${permission.code}" class="btn btn-secondary">Đăng ký dịch vụ</a>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>


</div>


<%@ include file="/WEB-INF/views/component/defaultFooter.jsp" %>