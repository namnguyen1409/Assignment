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
                                        <a href="deleteAddress?id=${location.id}" class="btn btn-danger">Xóa</a>
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


    </c:choose>
</div>