<%-- 
    Document   : index1
    Created on : Sep 24, 2024, 10:40:38 PM
    Author     : namnguyen
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World! Đây là trang đầu tiên</h1>
        <h2>${id}</h2>
        <h2>${name}</h2>
        <h2>${firstName}</h2>
        <h2>${lastName}</h2>
        <h2>${email}</h2>
        <h2>${picture}</h2>
        <h2>${emailVerified}</h2>
        <h2>${locale}</h2>
        <h2>${principalJson}</h2>

        <c:forEach var="userRole" items="${userRoles}">
            <h3>${userRole.role.code}</h3>
        </c:forEach>


    </body>
</html>
