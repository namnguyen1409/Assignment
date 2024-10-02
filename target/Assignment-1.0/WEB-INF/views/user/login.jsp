<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <base href="<c:url value="/" />">
        <title>Đăng nhập</title>
        <meta name="google-signin-client_id" content="283494544149-b0jrhort23nmgcon51er5b104r8epeti.apps.googleusercontent.com">
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-icons.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/animate.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/roboto.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/register.css" />">
        <script src="<c:url value="/resources/js/jquery-3.7.1.min.js" />"></script>
    </head>
    <body style="background-color: #eee;">
        <section class="h-100 gradient-form" style="background-color: #eee;" id="main">
            <div class="container py-5">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-xl-10">
                        <div class="card rounded-3 text-black">
                            <div class="row g-0">
                                <div class="col-lg-6">
                                    <div class="card-body p-md-5 mx-md-4">
                                        <div class="text-center">
                                            <img src="<c:url value="/resources/images/" />${compLogo}"
                                                 style="width: 185px;" alt="logo">
                                            <h3 class="mt-1 mb-5 pb-1 roboto-black">
                                                Đăng nhập tài khoản ${compName}
                                            </h3>
                                            ${error}
                                        </div>
                                        <form:form modelAttribute="loginData" method="POST" action="login/check" id="regForm">
                                            <div class="form-floating mb-3">
                                                <form:input path="loginInfo" type="text" id="loginInfo" class="form-control form-control-lg" placeholder="Email" required="required" />
                                                <label for="loginInfo">Username, email hoặc số điện thoại</label>
                                                <form:errors path="loginInfo" cssClass="text-danger" />
                                            </div>
                                            <div class="form-floating mb-3">
                                                <form:input path="password" type="password" id="password" class="form-control form-control-lg" placeholder="Password" required="required" />
                                                <label for="password">Mật khẩu</label>
                                                <form:errors path="password" cssClass="text-danger" />
                                            </div>

                                            <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                                <div class="form-check">
                                                    <form:checkbox path="rememberMe" id="rememberMe" class="form-check-input" />
                                                    <label class="form-check label" for="rememberMe">
                                                        Nhớ mật khẩu
                                                    </label>
                                                </div>
                                                <a href="<c:url value="/forgot-password" />" class="small text-right">Quên mật khẩu?</a>
                                            </div>
                                            <div class="d-grid">
                                                <button id="submitBtn" type="submit" class="btn btn-primary btn-lg">Đăng nhập</button>
                                            </div>

                                            <div class="d-grid mt-3">
                                                <div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>
                                <div class="col-lg-6 d-flex align-items-center gradient-custom-2">
                                    <div class="text-white px-3 py-4 p-md-5 mx-md-4">
                                        <h4 class="mb-4">We are more than just a company</h4>
                                        <p class="small mb-0">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                            tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
                                            exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </body>
    <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
    <script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
    <script src="<c:url value="/resources/js/register.js" />"></script>
  <script>
    function onSuccess(googleUser) {
      console.log('Logged in as: ' + googleUser.getBasicProfile().getName());
    }
    function onFailure(error) {
      console.log(error);
    }
    function renderButton() {
      gapi.signin2.render('my-signin2', {
        'scope': 'profile email',
        'width': 240,
        'height': 50,
        'longtitle': true,
        'theme': 'dark',
        'onsuccess': onSuccess,
        'onfailure': onFailure
      });
    }
  </script>
</html>