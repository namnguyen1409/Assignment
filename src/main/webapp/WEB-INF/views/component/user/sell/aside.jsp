<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url value="/user/sell" var="optionUrl"/>
<aside class="bd-sidebar">
    <div class="offcanvas-lg offcanvas-start" tabindex="-1" id="bdSidebar" aria-labelledby="bdSidebarOffcanvasLabel">
        <div class="offcanvas-header border-bottom">
            <h5 class="offcanvas-title" id="bdSidebarOffcanvasLabel">Danh sách chức năng</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close" data-bs-target="#bdSidebar"></button>
        </div>
        <div class="offcanvas-body">
            <nav class="bd-links w-100" id="bd-docs-nav" aria-label="Docs navigation">
                <ul class="bd-links-nav list-unstyled mb-0 pb-3 pb-md-2 pe-lg-2">
                    <li class="bd-links-group py-2">
                        <strong class="bd-links-heading d-flex w-100 align-items-center fw-semibold">
                            <i class="bi bi-clipboard-data-fill me-2 d-flex align-items-center" style="color: var(--bs-indigo);"></i>
                            Quản lý đơn hàng
                        </strong>
                        <ul class="list-unstyled fw-normal pb-2 small">
                            <li>
                                <a href="${optionUrl}/order" class="bd-links-link d-inline-block rounded">Danh sách đơn hàng</a>
                            </li>
                            <li>
                                <%-- giao hàng loạt --%>
                                <a href="${optionUrl}/order/batch" class="bd-links-link d-inline-block rounded">Giao hàng loạt</a>
                            </li>
                        </ul>
                    </li>
                    <li class="bd-links-group py-2">
                        <strong class="bd-links-heading d-flex w-100 align-items-center fw-semibold">
                            <%-- quản lý sản phẩm --%>
                            <i class="bi bi-box-seam me-2 d-flex align-items-center" style="color: var(--bs-blue);"></i>
                            Quản lý sản phẩm
                        </strong>
                        <ul class="list-unstyled fw-normal pb-2 small">
                            <li>
                                <a href="${optionUrl}/product" class="bd-links-link d-inline-block rounded">Danh sách sản phẩm</a>
                            </li>
                            <li>
                                <a href="${optionUrl}/product/add" class="bd-links-link d-inline-block rounded">Thêm sản phẩm</a>
                            </li>
                        </ul>
                    </li>
                    <li class="bd-links-group py-2">
                        <strong class="bd-links-heading d-flex w-100 align-items-center fw-semibold">
                            <%-- chiến dịch maketing --%>
                            <i class="bi bi-megaphone-fill me-2 d-flex align-items-center" style="color: var(--bs-green);"></i>
                            Chiến dịch maketing
                        </strong>
                        <ul class="list-unstyled fw-normal pb-2 small">
                            <%-- mã giảm giá của shop --%>
                            <li>
                                <a href="${optionUrl}/discount" class="bd-links-link d-inline-block rounded">Mã giảm giá của shop</a>
                            </li>
                            <li>
                                <a href="${optionUrl}/discount/add" class="bd-links-link d-inline-block rounded">Tạo mã giảm giá</a>
                            </li>
                            <%-- Quảng cáo --%>
                            <li>
                                <a href="${optionUrl}/ads" class="bd-links-link d-inline-block rounded">Quảng cáo</a>
                            </li>
                            <%-- tiếp thị liên kết --%>
                            <li>
                                <a href="${optionUrl}/affiliate" class="bd-links-link d-inline-block rounded">Tiếp thị liên kết</a>
                            </li>
                        </ul>
                    </li>
                    <li class="bd-links-group py-2">
                        <strong class="bd-links-heading d-flex w-100 align-items-center fw-semibold">
                            <%-- chăm sóc khách hàng --%>
                            <i class="bi bi-chat me-2 d-flex align-items-center" style="color: var(--bs-red);"></i>
                            Chăm sóc khách hàng
                        </strong>
                        <ul class="list-unstyled fw-normal pb-2 small">
                            <%-- quản lý chat --%>
                            <li>
                                <a href="${optionUrl}/chat" class="bd-links-link d-inline-block rounded">Quản lý chat</a>
                            </li>
                            <%-- quản lý đánh giá --%>
                            <li>
                                <a href="${optionUrl}/review" class="bd-links-link d-inline-block rounded">Quản lý đánh giá</a>
                            </li>
                        </ul>
                    </li>
                    <li class="bd-links-group py-2">
                        <strong class="bd-links-heading d-flex w-100 align-items-center fw-semibold">
                            <%-- tài chính --%>
                            <i class="bi bi-cash me-2 d-flex align-items-center" style="color: var(--bs-yellow);"></i>
                            Tài chính
                        </strong>
                        <ul class="list-unstyled fw-normal pb-2 small">
                            <%-- quản lý doanh thu --%>
                            <li>
                                <a href="${optionUrl}/revenue" class="bd-links-link d-inline-block rounded">Quản lý doanh thu</a>
                            </li>
                            <%-- quản lý chi phí --%>
                            <li>
                                <a href="${optionUrl}/cost" class="bd-links-link d-inline-block rounded">Quản lý chi phí</a>
                            </li>
                        </ul>
                    </li>
                    <li class="bd-links-group py-2">
                        <strong class="bd-links-heading d-flex w-100 align-items-center fw-semibold">
                            <%-- dữ liệu --%>
                            <i class="bi bi-database me-2 d-flex align-items-center" style="color: var(--bs-purple);"></i>
                            Dữ liệu
                        </strong>
                        <ul class="list-unstyled fw-normal pb-2 small">
                            <%-- thống kê --%>
                            <li>
                                <a href="${optionUrl}/statistic" class="bd-links-link d-inline-block rounded">Thống kê</a>
                            </li>
                            <%-- báo cáo --%>
                            <li>
                                <a href="${optionUrl}/report" class="bd-links-link d-inline-block rounded">Báo cáo</a>
                            </li>
                        </ul>
                    </li>
                    <li class="bd-links-group py-2">
                        <strong class="bd-links-heading d-flex w-100 align-items-center fw-semibold">
                            <%-- quản lý shop --%>
                            <i class="bi bi-shop me-2 d-flex align-items-center" style="color: var(--bs-orange);"></i>
                            Quản lý shop
                        </strong>

                        <ul class="list-unstyled fw-normal pb-2 small">
                            <%-- thông tin shop --%>
                            <li>
                                <a href="${optionUrl}/shop" class="bd-links-link d-inline-block rounded">Thông tin shop</a>
                            </li>
                            <%-- trang trí shop --%>
                            <li>
                                <a href="${optionUrl}/shop/decorate" class="bd-links-link d-inline-block rounded">Trang trí shop</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</aside>