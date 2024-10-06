<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="container">
<footer class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
    <div class="col-md-4 d-flex align-items-center">
    <img src="<c:url value="/resources/images/" />${compLogo}" alt="logo" width="40" height="40" class="d-inline-block align-top rounded-circle me-2">
    <span class="mb-3 mb-md-0 text-body-secondary">© 2024 ${compName}, Inc</span>
    </div>

    <ul class="nav col-md-4 justify-content-end list-unstyled d-flex">
      <li class="ms-3">
        <a class="fs-1 text-primary" href="#">
        <i class="bi bi-twitter"></i>
      </a>
      </li>
      <li class="ms-3">
      <a class="fs-1 text-primary" href="#">
        <i class="bi bi-facebook"></i>
      </a></li>
      <li class="ms-3"><a class="fs-1 text-danger" href="#">
        <i class="bi bi-instagram"></i>
      </a></li>
    </ul>
  </footer>
</div>
  </body>
  <script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
  <script src="<c:url value="/resources/js/setting.js" />"></script>
  <script src="<c:url value="/resources/js/address.js" />"></script>
</html>