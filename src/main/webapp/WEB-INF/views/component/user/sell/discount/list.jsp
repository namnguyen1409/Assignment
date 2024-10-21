<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.assignment.models.entities.shop.voucher.VoucherType" %>
<c:choose>
    <c:when test="${vouchers.size() == 0}">

        <div class="text-center">
            <h4 class="font-size-18">Không có mã giảm giá nào</h4>
            <p class="text-muted">
                hãy tạo mã giảm giá cho cửa hàng của bạn để thu hút khách hàng
            </p>
        </div>
    </c:when>
    <c:otherwise>
        <div class="row">
            <c:forEach items="${vouchers}" var="voucher">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex align-items-center">
                                <div class="flex-1">
                                    <h5 class="font-size-18">${voucher.code}</h5>
                                    <p class="text-muted mb-0">Mã giảm giá</p>
                                </div>
                                <div class="flex-shrink-0">
                                    <span class="badge bg-primary">${voucher.discount}%</span>
                                </div>
                            </div>
                            <hr>
                            <div class="d-flex align-items-center">
                                <div class="flex-1">
                                    <p class="text-muted mb-0">Hạn sử dụng: ${voucher.expiredDate}</p>
                                </div>
                            </div>
                            <hr>
                            <div class="d-flex justify-content-between align-items-center">
                                <button class="btn btn-danger">Xóa</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>