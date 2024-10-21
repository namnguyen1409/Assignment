<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/component/defaultHeader.jsp" %>
<div class="container mt-5">
    <div class="row">
        <c:if test="${tab eq 'purchase'}">
            <%@ include file="/WEB-INF/views/component/user/purchase.jsp" %>
        </c:if>
        <c:if test="${tab eq 'sell'}">
            <%@ include file="/WEB-INF/views/component/user/sell.jsp" %>
        </c:if>
        <c:if test="${tab eq 'ewallet'}">
            <%@ include file="/WEB-INF/views/component/user/ewallet.jsp" %>
        </c:if>
    </div>
</div>


<%@ include file="/WEB-INF/views/component/defaultFooter.jsp" %>