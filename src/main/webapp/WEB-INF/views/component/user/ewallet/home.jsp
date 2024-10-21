<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- phần thân
    - hiển thị số dư
    - nút nạp tiền
    - nút rút tiền
    - nút chuyển tiền
    - nút lịch sử giao dịch
    - nút quản lý ngân hàng (thẻ, tài khoản)
    - nút quản lý ví
 --%>

<div class="container mt-5">
    <div class="row d-flex ">
        <div class="col-md-4 mb-3">
            <div class="card shadow-sm w-100">
                <div class="card-body d-flex align-items-start">
                    <i class="bi bi-wallet2 fs-1 text-primary me-3"></i>
                    <div>
                        <h4 class="fw-bold mb-0">Số dư</h4>
                        <strong class="currency fs-5 text-success">${ewallet.balance}</strong>
                        <p class="text-muted">Số dư hiện tại của bạn</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-3">
            <a href="<c:url value="/user/ewallet/deposit" />" class="text-decoration-none w-100 d-block h-100">
                <div class="card shadow-sm w-100 h-100">
                    <div class="card-body d-flex align-items-start">
                        <i class="bi bi-cash-coin fs-1 text-primary me-3"></i>
                        <div>
                            <h4 class="fw-bold mb-0">Nạp tiền</h4>
                            <p class="text-muted">Nạp tiền vào ví điện tử</p>
                        </div>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-md-4 mb-3">
            <a href="<c:url value="/user/ewallet/withdraw" />" class="text-decoration-none w-100 d-block h-100">
                <div class="card shadow-sm w-100 h-100">
                    <div class="card-body d-flex align-items-start">
                        <i class="bi bi-cash-stack fs-1 text-primary me-3"></i>
                        <div>
                            <h4 class="fw-bold mb-0">Rút tiền</h4>
                            <p class="text-muted">Rút tiền từ ví điện tử</p>
                        </div>
                    </div>
                </div>
            </a>
        </div>

        <div class="col-md-4 mb-3">
            <a href="<c:url value="/user/ewallet/transfer" />" class="text-decoration-none w-100 d-block h-100">
                <div class="card shadow-sm w-100 h-100">
                    <div class="card-body d-flex align-items-start">
                        <i class="bi bi-arrow-right-circle fs-1 text-primary me-3"></i>
                        <div>
                            <h4 class="fw-bold mb-0">Chuyển tiền</h4>
                            <p class="text-muted">Chuyển tiền từ ví sang tài khoản khác</p>
                        </div>
                    </div>
                </div>
            </a>
        </div>

        <div class="col-md-4 mb-3">
            <a href="<c:url value="/user/ewallet/transaction" />" class="text-decoration-none w-100 d-block h-100">
                <div class="card shadow-sm w-100 h-100">
                    <div class="card-body d-flex align-items-start">
                        <i class="bi bi-journal fs-1 text-primary me-3"></i>
                        <div>
                            <h4 class="fw-bold mb-0">Lịch sử giao dịch</h4>
                            <p class="text-muted">Xem lịch sử giao dịch của bạn</p>
                        </div>
                    </div>
                </div>
            </a>
        </div>

        <div class="col-md-4 mb-3">
            <a href="<c:url value="/user/ewallet/bank" />" class="text-decoration-none w-100 d-block h-100">
                <div class="card shadow-sm w-100 h-100">
                    <div class="card-body d-flex align-items-start">
                        <i class="bi bi-credit-card-2-front fs-1 text-primary me-3"></i>
                        <div>
                            <h4 class="fw-bold mb-0">Quản lý ngân hàng</h4>
                            <p class="text-muted">Quản lý thẻ và tài khoản ngân hàng</p>
                        </div>
                    </div>
                </div>
            </a>
        </div>

    </div>

</div>



<script>
    var formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });

    var currency = document.querySelectorAll('.currency');
    currency.forEach((element) => {
        element.innerHTML = formatter.format(element.innerHTML);
    });

</script>