<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <base href="<c:url value="/" />">
        <title>Đăng ký tài khoản mới</title>
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
                                                Tạo tài khoản ${compName}
                                            </h3>
                                        </div>

                                        <c:choose>
                                            <c:when test="${tab == 'fullname'}">
                                                <form:form modelAttribute="userFullname" method="POST" action="" id="regForm">
                                                    <p class="roboto-slab-medium">Nhập tên của bạn</p>
                                                    <div class="form-floating mb-3">
                                                        <form:input path="firstName" cssClass="form-control" id="firstName" placeholder="Nhập họ của bạn" required="true" />
                                                        <label for="firstName">Họ<span class="text-danger">*</span></label>
                                                        <form:errors path="firstName" cssClass="text-danger roboto-serif-medium p-1" />
                                                    </div>
                                                    <div class="form-floating mb-3">
                                                        <form:input path="lastName" cssClass="form-control" id="lastName" placeholder="Nhập tên của bạn" />
                                                        <label for="lastName">Tên<span class="text-danger">*</span></label>
                                                        <form:errors path="lastName" cssClass="text-danger roboto-serif-medium p-1" />
                                                    </div>
                                                    <div class="text-center pt-1 mb-5 pb-1">
                                                        <button data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" id="submitBtn" type="submit">
                                                            Tiếp theo    
                                                        </button>
                                                    </div>
                                                    <div class="d-flex align-items-center justify-content-center pb-4">
                                                        <p class="mb-0 me-2">Đã có tài khoản? </p>
                                                        <button type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-outline-danger">Đăng nhập</button>
                                                    </div>
                                                </form:form>
                                            </c:when>
                                            <c:when test="${tab == 'birthdaygender'}">
                                                <form:form modelAttribute="userBirthdayGender" method="POST" action="" id="regForm">
                                                    <p class="roboto-slab-medium">Nhập ngày sinh và giới tính của bạn</p>
                                                    <div class="form-floating mb-3">
                                                        <form:input type="date" path="birthday" cssClass="form-control" id="birthday" placeholder="dd-MM-yyyy" required="true" />
                                                        <label for="birthday">Ngày sinh<span class="text-danger">*</span></label>
                                                        <form:errors path="birthday" cssClass="text-danger roboto-serif-medium p-1" />
                                                    </div>
                                                    <div class="form-floating mb-3">
                                                        <form:select path="gender" cssClass="form-select" id="gender" required="true">
                                                            <form:option value="" label="Chọn giới tính" />
                                                            <form:option value="Nam" label="Nam" />
                                                            <form:option value="Nữ" label="Nữ" />
                                                            <form:option value="Không muốn trả lời" label="Không muốn trả lời" />
                                                            <form:option value="Khác" label="Khác" />
                                                        </form:select>
                                                        <label for="gender">Giới tính<span class="text-danger">*</span></label>
                                                        <form:errors path="gender" cssClass="text-danger roboto-serif-medium p-1" />
                                                    </div>

                                                    <div id="otherGenderDiv" class="form-floating mb-3 ${userBirthdayGender.gender == 'Khác' ? '' : 'd-none'}">
                                                        <form:input path="otherGender" cssClass="form-control" id="otherGender" placeholder="Nhập giới tính của bạn" />
                                                        <label for="otherGender">Giới tính khác<span class="text-danger">*</span></label>
                                                        <form:errors path="otherGender" cssClass="text-danger roboto-serif-medium p-1" />
                                                    </div>

                                                    <div class="text-center pt-1 mb-5 pb-1">
                                                        <button data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" id="submitBtn" type="submit">
                                                            Tiếp theo    
                                                        </button>
                                                    </div>

                                                </form:form>
                                            </c:when>
                                            <c:when test="${tab == 'namepass'}">
                                                <form:form modelAttribute="userNamePass" method="POST" action="" id="regForm">
                                                    <p class="roboto-slab-medium">Nhập tên đăng nhập và mật khẩu</p>
                                                    <div class="form-floating mb-3">
                                                        <form:input path="username" cssClass="form-control" id="username" placeholder="Nhập tên đăng nhập" required="true" autocomplete="off" />
                                                        <label for="username">Tên đăng nhập<span class="text-danger">*</span></label>
                                                        <form:errors path="username" cssClass="text-danger roboto-serif-medium p-1" />
                                                    </div>
                                                    <div class="form-floating mb-3">
                                                        <form:input type="password" path="password" cssClass="form-control" id="password" placeholder="Nhập mật khẩu" required="true" autocomplete="off" />
                                                        <label for="password">Mật khẩu<span class="text-danger">*</span></label>
                                                        <form:errors path="password" cssClass="text-danger roboto-serif-medium p-1" />
                                                        <div class="password-meter mt-3">
                                                            <div class="meter-section rounded me-2"></div>
                                                            <div class="meter-section rounded me-2"></div>
                                                            <div class="meter-section rounded me-2"></div>
                                                            <div class="meter-section rounded"></div>
                                                        </div>
                                                        <div class="password-contain mt-3"> 
                                                            <span id="charCount" class="text-danger bg-light badge"> 0 kí tự </span>
                                                            <span id="lowercase" class="text-danger bg-light badge"> Chữ thường </span>
                                                            <span id="uppercase" class="text-danger bg-light badge"> Chữ hoa </span>
                                                            <span id="number" class="text-danger bg-light badge"> Số </span>
                                                            <span id="symbol" class="text-danger bg-light badge"> Ký tự đặc biệt </span>
                                                        </div>
                                                    </div>
                                                    <div class="form-floating mb-3">
                                                        <form:input type="password" path="rePassword" cssClass="form-control" id="rePassword" placeholder="Nhập lại mật khẩu" required="true" />
                                                        <label for="rePassword">Nhập lại mật khẩu<span class="text-danger">*</span></label>
                                                        <form:errors path="rePassword" cssClass="text-danger roboto-serif-medium p-1" />
                                                    </div>
                                                    <div class="text-center pt-1 mb-5 pb-1">
                                                        <button data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" id="submitBtn" type="submit">
                                                            Tiếp theo    
                                                        </button>
                                                    </div>
                                                </form:form>
                                            </c:when>
                                            <c:when test="${tab == 'phoneemail'}">
                                                <form:form modelAttribute="userPhoneEmail" method="POST" action="" id="regForm">
                                                    <p class="roboto-slab-medium">Số điện thoại và email</p>
                                                    <div class="form-floating mb-3">
                                                        <form:input path="phone" cssClass="form-control" id="phone" placeholder="Nhập số điện thoại của bạn" required="true" type="tel" />
                                                        <label for="phone">Số điện thoại<span class="text-danger">*</span></label>
                                                        <form:errors path="phone" cssClass="text-danger roboto-serif-medium p-1" />
                                                    </div>
                                                    <div class="form-floating mb-3">
                                                        <form:input path="email" cssClass="form-control" id="email" placeholder="Nhập email của bạn" required="true" type="email" />
                                                        <label for="email">Email<span class="text-danger">*</span></label>
                                                        <form:errors path="email" cssClass="text-danger roboto-serif-medium p-1" />
                                                    </div>
                                                    <div class="text-center pt-1 mb-5 pb-1">
                                                        <button data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" id="submitBtn" type="submit">
                                                            Tiếp theo    
                                                        </button>
                                                    </div>
                                                </form:form>
                                            </c:when>

                                            <c:when test="${tab == 'enable2fa'}">
                                                <form:form modelAttribute="userEnable2FA" method="POST" action="" id="regForm">
                                                    <p class="roboto-slab-medium">Đăng ký xác thực 2 yếu tố TOTP</p>
                                                    <p class="text-center">
                                                        Xác thực 2 lớp giúp bảo vệ tài khoản của bạn khỏi việc truy cập trái phép với một ứng dụng xác thực TOTP như Google Authenticator. Khi bạn đăng nhập, bạn sẽ cần nhập mã xác thực được tạo bởi ứng dụng xác thực của bạn.
                                                    </p>
                                                    <div class="text-center pt-1 mb-5 pb-1">
                                                        <button data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" name="enable2fa" type="submit" value="false" type="submit">
                                                            Bỏ qua  
                                                        </button>
                                                        <button data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" name="enable2fa" type="submit" value="true" type="submit">
                                                            Đăng ký
                                                        </button>
                                                    </div>
                                                </form:form>
                                            </c:when>

                                            <c:when test="${tab == 'verify2fa'}">
                                                <form:form modelAttribute="totp" method="POST" action="" id="regForm">
                                                    <p class="roboto-slab-medium">Xác thực 2 yếu tố TOTP</p>
                                                    <div class="row mt-3">
                                                        <div class="col-7">
                                                            <figure class="figure text-center">
                                                                <img src="${totp.QRCode}" class="figure-img img-fluid img-thumbnail w-100"  alt="QR Code">
                                                                <div class="input-group mt-1" id="key">
                                                                    <form:input path="secretKey" cssClass="form-control" value="${totp.secretKey}" readonly="true" />
                                                                    <button class="btn btn-outline-secondary" type="button"><i class="bi bi-clipboard"></i></button>
                                                                </div>
                                                                <figcaption class="figure-caption">Quét mã QR bằng ứng dụng xác thực TOTP của bạn hoặc nhập mã</figcaption>
                                                            </figure>
                                                        </div>

                                                        <div class="col-5"> 
                                                            <div class="form-floating mb-3">
                                                                <form:input path="totpCode" cssClass="form-control" id="totpCode" placeholder="Nhập mã xác thực" required="true" />
                                                                <label for="totpCode">Mã xác thực<span class="text-danger">*</span></label>
                                                                <form:errors path="totpCode" cssClass="text-danger roboto-serif-medium p-1" />
                                                            </div>
                                                            <div class="text-center pt-1 mb-5 pb-1">
                                                                <button class="w-100 btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" id="submitBtn" type="submit">
                                                                    Xác thực    
                                                                </button>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </form:form>

                                            </c:when>

                                        </c:choose>





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
    
    <script src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>
    <script src="<c:url value="/resources/js/register.js" />"></script>

</html>