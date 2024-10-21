<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%-- danh sách các thẻ ngân hàng --%>

<div class="container mt-5">
<div class="row">



    <div class="col-md-6 col-lg-4">
        <div class="card card-cover h-100 overflow-hidden text-white bg-dark rounded-5 shadow-lg position-relative border"
             style="background-image: url('https://raw.githubusercontent.com/muhammederdem/credit-card-form/master/src/assets/images/2.jpeg');">
            <div class="overlay position-absolute top-0 start-0 w-100 h-100 bg-dark opacity-50"></div>
            <div class="d-flex flex-column h-100 p-4 text-white position-relative">
                <%-- Thông tin thẻ --%>
                <div class="d-flex align-items-center justify-content-between mb-4 pt-3">
                    <img src="<c:url value='/resources/images/ewallet/bank/ABB.png' />" alt="Logo" height="40" class="me-2">
                    <img src="<c:url value='/resources/images/ewallet/cardtype/jcb.png' />" alt="Card Type" height="30">
                </div>

                <%-- Số thẻ --%>
                <div class="d-flex align-items-center justify-content-center mb-4">
                    <span class="fw-bold fs-4">1234 **** **** 1234</span>
                </div>

                <%-- Thông tin chủ thẻ --%>
                <div class="d-flex justify-content-between mb-3">
                    <div>
                        <span class="fw-bold">Chủ thẻ</span>
                        <span class="d-block fs-5">NGUYEN VAN A</span>
                    </div>
                    <div class="text-end">
                        <span class="fw-bold">Ngày hết hạn</span>
                        <span class="d-block fs-5">12/24</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</div>