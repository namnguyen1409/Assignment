<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/component/user/purchase/purchaseHeader.jsp" %>


<c:choose>
    <c:when test="${tab eq 'home'}">
        <%@ include file="/WEB-INF/views/component/user/purchase/home/body.jsp" %>
    </c:when>
    <c:when test="${tab eq 'productDetail'}">
        <%@ include file="/WEB-INF/views/component/user/purchase/product/body.jsp" %>
    </c:when>
    <c:when test="${tab eq 'cart'}">
        <%@ include file="/WEB-INF/views/component/user/purchase/cart/body.jsp" %>
    </c:when>



</c:choose>

<%@ include file="/WEB-INF/views/component/defaultFooter.jsp" %>