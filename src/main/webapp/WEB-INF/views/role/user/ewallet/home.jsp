<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/component/defaultHeader.jsp" %>

<c:choose>
    <c:when test="${tab eq 'home'}">
        <%@ include file="/WEB-INF/views/component/user/ewallet/home.jsp" %>
    </c:when>
    <c:when test="${tab eq 'bank'}">
        <%@ include file="/WEB-INF/views/component/user/ewallet/bank.jsp" %>
    </c:when>


</c:choose>




<%@ include file="/WEB-INF/views/component/defaultFooter.jsp" %>