<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="col-lg-12 col-md-12 col-xl-8 bg-light p-3">
    <c:choose>
        <c:when test="${tab eq 'general'}">
            <%-- header --%>
            <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
                <span class="fs-4">Thông tin cá nhân</span>
            </a>
            <hr>
            <form:form modelAttribute="general" action="" method="POST">
                <%-- avatar --%>
                <div class="mb-3">
                    <label for="avatar" class="form-label h6">Ảnh đại diện</label>
                    <p class="text-muted">
                        Click vào nút bên dưới để thêm / thay đổi ảnh đại diện. Kích thước ảnh tối đa 2MB.
                    </p>
                    <button title="click để thêm hình ảnh" type="button" class="btn-image" id="uploadAvtBtn" for="avatar">
                        <c:if test="${empty general.avatar}">
                            <i class="bi bi-image"></i>
                        </c:if>
                        <c:if test="${not empty general.avatar}">
                            <img src="<c:url value="/resources/images/avatar/" />${general.avatar}" alt="avatar" class="rounded-circle" width="100" height="100">
                        </c:if>
                    </button>
                    <input type="file" class="d-none" id="uploadAvtFile" accept="image/*">
                    <form:hidden path="avatar"></form:hidden>
                    </div>

                <%-- user name --%>
                <div class="mb-3">
                    <label for="username" class="form-label h6">Tên người dùng</label>
                    <div class="input-group">
                        <span class="input-group-text">
                            @
                        </span>
                        <form:input path="username" id="username" class="form-control" required="true" readonly="true" disabled="true" />
                    </div>
                    <form:errors path="username" cssClass="text-danger" />
                </div>

                <%-- full name --%>

                <div class="row gx-3">
                    <label class="form-label h6">Họ và tên</label>
                    <div class="col-md-6 mb-3">
                        <div class="input-group">
                            <span class="input-group-text">Họ</span>
                            <form:input path="firstName" id="firstName" class="form-control" required="true" readonly="true" disabled="true" />
                        </div>
                        <form:errors path="firstName" cssClass="text-danger" />
                    </div>
                    <div class="col-md-6 mb-3">
                        <div class="input-group">
                            <span class="input-group-text">Tên</span>
                            <form:input path="lastName" id="lastName" class="form-control" required="true" readonly="true" disabled="true" />
                        </div>
                        <form:errors path="lastName" cssClass="text-danger" />
                    </div>
                </div>

                <%-- gender/birthday --%>
                <div class="row gx-3 mb-3">
                    <div class="col-md-6">
                        <label for="gender" class="form-label h6">Giới tính</label>
                        <div class="input-group">
                            <span class="input-group-text"> <i class="bi bi-gender-ambiguous"></i> </span>
                            <form:input path="gender" class="form-control" required="true" readonly="true" disabled="true" />
                        </div>
                        <form:errors path="gender" cssClass="text-danger" />
                    </div>
                    <div class="col-md-6">
                        <label for="birthday" class="form-label h6">Ngày sinh</label>
                        <div class="input-group">
                            <span class="input-group-text"> <i class="bi bi-cake-fill"></i> </span>
                            <form:input path="birthday" class="form-control" required="true" readonly="true" disabled="true" />
                        </div>
                        <form:errors path="birthday" cssClass="text-danger" />
                    </div>
                </div>

                <%-- create At --%>
                <div class="mb-3">
                    <label for="createdAt" class="form-label h6">Ngày tạo</label>
                    <div class="input-group">
                        <span class="input-group-text"> <i class="bi bi-calendar"></i> </span>
                        <form:input path="createdAt" class="form-control" required="true" readonly="true" disabled="true" />
                    </div>
                    <form:errors path="createdAt" cssClass="text-danger" />
                </div>
                <%-- submit btn --%>
                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                </div>

            </form:form>
        </c:when>
        <c:when test="${tab eq 'contact'}">
            <%-- header --%>
            <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
                <span class="fs-4">Thông tin liên hệ</span>
            </a>
            <hr>
            <form:form modelAttribute="contact">
                <%-- email --%>
                <div class="mb-3">
                    <label for="email" class="form-label h6">Email</label>
                    <div class="input-group">
                        <span class="input-group-text"> <i class="bi bi-envelope-fill"></i> </span>
                        <form:input path="email" id="email" class="form-control is-valid" required="true" readonly="true" disabled="true" />

                    </div>
                    <form:errors path="email" cssClass="text-danger" />
                </div>
                <%-- phone --%>
                <div class="mb-3">
                    <label for="phone" class="form-label h6">Số điện thoại</label>
                    <div class="input-group">
                        <span class="input-group-text"> <i class="bi bi-telephone-fill"></i> </span>
                        <form:input path="phone" id="phone" class="form-control" required="true" readonly="true" disabled="true" />
                    </div>
                    <form:errors path="phone" cssClass="text-danger" />
                </div>
            </form:form>
        </c:when>

        <c:when test="${tab eq 'address'}">
            <%-- header --%>
            <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
                <span class="fs-4">Địa chỉ</span>
            </a>
            <hr>
            <%-- List location as table--%>
            <div class="table-responsive">
                <c:if test="${not empty locations}">
                    <table class="table table-hover table-bordered">
                        <thead>
                            <tr>
                                <th>Thành phố</th>
                                <th>Quận/Huyện</th>
                                <th>Phường/Xã</th>
                                <th>Địa chỉ</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${locations}" var="location">
                                <tr>
                                    <td>${location.province.name}</td>
                                    <td>${location.district.name}</td>
                                    <td>${location.ward.name}</td>
                                    <td>${location.detailAddress}</td>
                                    <td>
                                        <a href="<c:url value="/setting/address/edit/" />${location.id}" class="btn btn-warning">Sửa</a>
                                        <a href="<c:url value="/setting/address/delete/" />${location.id}" class="btn btn-danger">Xóa</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty locations}">
                    <p class="text-muted">Chưa có địa chỉ nào</p>
                </c:if>
            </div>
            <%-- add new address btn --%>
            <a href="<c:url value="/setting/address/add" />" class="btn btn-primary">Thêm địa chỉ mới</a>

        </c:when>

        <c:when test="${tab eq 'addAddress'}">
            <%-- header --%>
            <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
                <span class="fs-4">Thêm địa chỉ mới</span>
            </a>
            <hr>
            <%-- form --%>
            <form:form modelAttribute="location" action="" method="POST">

                <%-- province --%>
                <div class="mb-3">
                    <label for="province" class="form-label h6">Tỉnh/Thành phố</label>
                    <form:select path="provinceID" class="form-select" required="true">
                        <form:option value="">Chọn tỉnh/thành phố</form:option>
                        <form:options items="${provinces}" itemValue="id" itemLabel="name" />
                    </form:select>
                    <form:errors path="provinceID" cssClass="text-danger" />
                </div>

                <%-- district --%>
                <div class="mb-3">
                    <label for="district" class="form-label h6">Quận/Huyện</label>
                    <form:select path="districtID" class="form-select" required="true">
                        <form:option value="">Chọn quận/huyện</form:option>

                    </form:select>
                    <form:errors path="districtID" cssClass="text-danger" />
                </div>

                <%-- ward --%>
                <div class="mb-3">
                    <label for="ward" class="form-label h6">Phường/Xã</label>
                    <form:select path="wardID" class="form-select" required="true">
                        <form:option value="">Chọn phường/xã</form:option>

                    </form:select>
                    <form:errors path="wardID" cssClass="text-danger" />
                </div>

                <%-- detail address --%>
                <div class="mb-3">
                    <label for="detailAddress" class="form-label h6">Địa chỉ chi tiết</label>
                    <form:input path="detailAddress" class="form-control" required="true"></form:input>
                    <form:errors path="detailAddress" cssClass="text-danger" />
                </div>

                <%-- full address --%>
                <div class="mb-3">
                    <label for="fullAddress" class="form-label h6">Địa chỉ đầy đủ</label>
                    <input type="text" class="form-control" id="fullAddress" readonly="true" disabled="true">
                </div>

                <%-- submit btn --%>
                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </div>

            </form:form>

        </c:when>

        <c:when test="${tab eq 'editAddress'}">
            <%-- header --%>
            <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
                <span class="fs-4">Sửa địa chỉ</span>
            </a>
            <hr>

            <c:forEach items="${listProvince}" var="lp">
                <c:out value="${lp.name}" />
                <c:out value="${lp.id}" />
            </c:forEach>
            <%-- form --%>
            <form:form modelAttribute="location" action="" method="POST">
                <%-- province --%>
                <div class="mb-3">
                    <label for="province" class="form-label h6">Tỉnh/Thành phố</label>
                    <form:select path="provinceID" class="form-select" required="true">
                        <form:option value="">Chọn tỉnh/thành phố</form:option>
                        <form:options items="${provinces}" itemValue="id" itemLabel="name" />
                    </form:select>
                    <form:errors path="provinceID" cssClass="text-danger" />
                </div>

                <%-- district --%>
                <div class="mb-3">
                    <label for="district" class="form-label h6">Quận/Huyện</label>
                    <form:select path="districtID" class="form-select" required="true">
                        <form:option value="">Chọn quận/huyện</form:option>
                        <form:options items="${districts}" itemValue="id" itemLabel="name" />
                    </form:select>
                    <form:errors path="districtID" cssClass="text-danger" />
                </div>

                <%-- ward --%>
                <div class="mb-3">
                    <label for="ward" class="form-label h6">Phường/Xã</label>
                    <form:select path="wardID" class="form-select" required="true">
                        <form:option value="">Chọn phường/xã</form:option>
                        <form:options items="${wards}" itemValue="id" itemLabel="name" />
                    </form:select>
                    <form:errors path="wardID" cssClass="text-danger" />
                </div>

                <%-- detail address --%>
                <div class="mb-3">
                    <label for="detailAddress" class="form-label h6">Địa chỉ chi tiết</label>
                    <form:input path="detailAddress" class="form-control" required="true"></form:input>
                    <form:errors path="detailAddress" cssClass="text-danger" />
                </div>

                <%-- full address --%>
                <div class="mb-3">
                    <label for="fullAddress" class="form-label h6">Địa chỉ đầy đủ</label>
                    <input type="text" class="form-control" id="fullAddress" readonly="true" disabled="true" value="${fullAddress}">
                </div>

                <%-- submit btn --%>
                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </div>

            </form:form>

        </c:when>


        <c:when test="${tab eq 'security'}">
            <%-- header --%>
            <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
                <span class="fs-4">Mật khẩu và bảo mật</span>
            </a>

            <hr>
            <%-- change password --%>
            <%-- title --%>
            <h5 class="mb-3">Thay đổi mật khẩu</h5>

            <form:form modelAttribute="changePass" action="" method="POST">
                <%-- current password --%>
                <div class="mb-3">
                    <label for="oldPassword" class="form-label h6">Mật khẩu hiện tại</label>
                    <form:input type="password" path="oldPassword" id="oldPassword" class="form-control" required="true" />
                    <form:errors path="oldPassword" cssClass="text-danger" />
                </div>
                <%-- new password --%>
                <div class="mb-3">
                    <label for="newPassword" class="form-label h6">Mật khẩu mới</label>
                    <form:input type="password" path="newPassword" id="newPassword" class="form-control" required="true" />
                    <form:errors path="newPassword" cssClass="text-danger" />
                </div>
                <%-- confirm password --%>
                <div class="mb-3">
                    <label for="reNewPassword" class="form-label h6">Xác nhận mật khẩu</label>
                    <form:input type="password" path="reNewPassword" id="reNewPassword" class="form-control" required="true" />
                    <form:errors path="reNewPassword" cssClass="text-danger" />
                </div>
                <c:if test="${changePass.enableTOTP}">
                    <%-- TOTP --%>
                    <div class="mb-3">
                        <label for="TOTP" class="form-label h6">Mã xác thực TOTP</label>
                        <div class="input-group">
                            <span class="input-group-text"> <i class="bi bi-shield-lock-fill"></i> </span>
                            <form:input path="TOTP" id="TOTP" class="form-control" required="true" />
                        </div>
                        <form:errors path="TOTP" cssClass="text-danger" />
                    </div>
                </c:if>
                <%-- submit btn --%>
                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                </div>
            </form:form>

            <hr>
            <h5 class="mb-3">Danh sách thiết bị đã đăng nhập</h5>
            <%-- list device --%>
            <div class="table-responsive">
                <%-- title --%>

                <table class="table table-hover table-bordered">
                    <thead>
                        <tr>
                            <th>Tên thiết bị</th>
                            <th>Hệ điều hành</th>
                            <th>Trình duyệt</th>
                            <th>Lần đăng nhập cuối</th>
                            <th>Trạng thái</th>
                            <th>ghi chú</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${devices}" var="device">
                            <tr>
                                <td>${device.deviceName}</td>
                                <td>${device.platform}</td>
                                <td>${device.browser}</td>
                                <td>${device.lastLogin}</td>
                                <c:if test="${device.isRevoked}">
                                    <td class="text-danger">Đã đăng xuất</td>
                                </c:if>
                                <c:if test="${not device.isRevoked}">
                                    <td class="text-success">Đang hoạt động</td>
                                </c:if>
                                <c:if test="${device.isCurrentDevice}">
                                    <td class="text-primary">Thiết bị hiện tại</td>
                                </c:if>
                                <c:if test="${not device.isCurrentDevice}">
                                    <td class="text-muted">Thiết bị khác</td>
                                </c:if>
                                <td>
                                    <a href="revoke?id=${device.id}" class="btn btn-danger">Đăng xuất</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <hr>
            <h5 class="mb-3">Trạng thái kích hoạt TOTP</h5>
            <c:if test="${changePass.enableTOTP}">
                <p class="text-success fw-bold mb-3">Đã kích hoạt xác thực TOTP</p>
                <%-- disable TOTP --%>                
                <div class="mb-3">
                    <a href="<c:url value="/setting/security/disableTOTP" />" class="btn btn-danger">Tắt xác thực TOTP</a>
                </div>
            </c:if>
            <c:if test="${not changePass.enableTOTP}">
                <%-- enable TOTP --%>
                <p class="text-danger fw-bold mb-3">Chưa kích hoạt xác thực TOTP</p>
                <div class="mb-3">
                    <a href="<c:url value="/setting/security/enableTOTP" />" class="btn btn-primary">Bật xác thực TOTP</a>
                </div>
            </c:if>

        </c:when>

        <c:when test="${tab eq 'enableTOTP'}">
            <%-- header --%>
            <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
                <span class="fs-4">Kích hoạt xác thực TOTP</span>
            </a>
            <hr>
            <%-- form --%>
            <form:form modelAttribute="totp" action="" method="POST">
                <div class="row mt-3">
                    <%-- QR code --%>
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
                        <%-- code --%>
                        <div class="form-floating mb-3">
                            <form:input path="totpCode" cssClass="form-control" id="totpCode" placeholder="Nhập mã xác thực" required="true" />
                            <label for="totpCode">Mã xác thực<span class="text-danger">*</span></label>
                            <form:errors path="totpCode" cssClass="text-danger roboto-serif-medium p-1" />
                        </div>
                        <%-- password --%>
                        <div class="form-floating mb-3">
                            <form:input type="password" path="password" cssClass="form-control" id="password" placeholder="Mật khẩu" required="true" />
                            <label for="password">Mật khẩu<span class="text-danger">*</span></label>
                            <form:errors path="password" cssClass="text-danger roboto-serif-medium p-1" />
                        </div>
                        <div class="text-center pt-1 mb-5 pb-1">
                            <button type="submit" class="btn btn-primary">Kích hoạt</button>
                        </div>
                    </div>
                </div>
            </form:form>
        </c:when>


    </c:choose>
</div>