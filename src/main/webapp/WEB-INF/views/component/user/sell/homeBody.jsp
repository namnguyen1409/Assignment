
<%-- 

    Danh sách việc cần làm
    - chờ xác nhận
    - chờ lấy hàng
    - đã xử lý
    - trả hàng / hoàn tiền chờ xử lý
    - đơn hủy
    - sản phẩm bị khóa
    - sản phẩm hết hàng

--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="mt-3 row border rounded p-4">
    <h4 class="mb-4">Danh sách cần làm</h4>
    <p class="text-muted">Những việc bạn sẽ phải làm</p>
    <div class="col-6 col-md-3 mb-4">
        <div class="border p-2 d-flex flex-wrap justify-content-center">
            <strong class="d-block w-100 text-center">Chờ Xác Nhận</strong>
            <h2>${userSellHomeDTO.orderPending}</h2>
        </div>
    </div>
    <div class="col-6 col-md-3 mb-4">
        <div class="border p-2 d-flex flex-wrap justify-content-center">
            <strong class="d-block w-100 text-center">Chờ Lấy Hàng</strong>
            <h2>${userSellHomeDTO.orderPendingDelivery}</h2>
        </div>
    </div>
    <div class="col-6 col-md-3 mb-4">
        <div class="border p-2 d-flex flex-wrap justify-content-center">
            <strong class="d-block w-100 text-center">Đã Xử Lý</strong>
            <h2>${userSellHomeDTO.orderDelivering}</h2>
        </div>
    </div>
    <div class="col-6 col-md-3 mb-4">
        <div class="border p-2 d-flex flex-wrap justify-content-center">
            <strong class="d-block w-100 text-center">Đơn Hủy</strong>
            <h2>${userSellHomeDTO.orderCancelled}</h2>
        </div>
    </div>
    <div class="col-6 col-md-3 mb-4">
        <div class="border p-2 d-flex flex-wrap justify-content-center">
            <strong class="d-block w-100 text-center">Sản Phẩm Bị Khóa</strong>
            <h2>${userSellHomeDTO.productInactive}</h2>
        </div>
    </div>
    <div class="col-6 col-md-3 mb-4">
        <div class="border p-2 d-flex flex-wrap justify-content-center">
            <strong class="d-block w-100 text-center">Sản Phẩm Hết Hàng</strong>
            <h2>${userSellHomeDTO.productOutOfStock}</h2>
        </div>
    </div>
    <div class="col-6 col-md-3 mb-4">
        <div class="border p-2 d-flex flex-wrap justify-content-center">
            <strong class="d-block w-100 text-center">Trả Hàng / Hoàn Tiền</strong>
            <h2>${userSellHomeDTO.orderReturnPending}</h2>
        </div>
    </div>
</div>


<%-- 
    biểu đồ doanh số

--%>

<div class="mt-3 row border rounded p-4">
    <h4 class="mb-4">Phân Tích Bán Hàng</h4>
    <div class="row">
        <div class="col-12">
            <p class="text-mute fw-bold">Hôm nay 00:00 GMT+7</p>
            <p class="text-mute">Tổng quan dữ liệu của shop đối với đơn hàng đã xác nhận</p>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col-md-6">
            <canvas id="salesChart" width="400" height="200"></canvas>
        </div>
        <div class="col-md-6 row">
            <div class="col-6 mb-4">
                <div class="card-body border p-2 d-flex flex-wrap justify-content-center">
                    <strong class="card-title w-100 text-center">Lượt truy shop</strong>
                    <h5 class="w-100 text-center" id="trafficCount">0</h5>
                    <p class="text-muted">so với hôm qua 
                        <span class="text-success">+0%</span>
                    </p>
                </div>
            </div>
            <div class="col-6 mb-4">
                <div class="card-body border p-2 d-flex flex-wrap justify-content-center">
                    <strong class="card-title w-100 text-center">Lượt xem sản phẩm</strong>
                    <h5 class="w-100 text-center" id="viewsCount">0</h5>
                    <p class="text-muted">so với hôm qua 
                        <span class="text-success">+0%</span>
                    </p>
                </div>
            </div>
            <div class="col-6 mb-4">
                <div class="card-body border p-2 d-flex flex-wrap justify-content-center">
                    <strong class="card-title w-100 text-center">Đơn hàng</strong>
                    <h5 class="w-100 text-center" id="orderCount">0</h5>
                    <p class="text-muted">so với hôm qua 
                        <span class="text-success">+0%</span>
                    </p>
                </div>
            </div>
            <div class="col-6 mb-4">
                <div class="card-body border p-2 d-flex flex-wrap justify-content-center">
                    <strong class="card-title w-100 text-center">Tỷ lệ chuyển đổi</strong>
                    <h5 class="w-100 text-center" id="conversionRate">0.00%</h5>
                    <p class="text-muted">so với hôm qua 
                        <span class="text-success">+0%</span>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    // Dữ liệu biểu đồ
    const ctx = document.getElementById('salesChart').getContext('2d');
    const salesChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['00:00', '06:00', '12:00', '18:00', '24:00'],
            datasets: [{
                    label: 'Doanh số',
                    data: [10, 0, 0, 0, 0],
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 2,
                    fill: true
                }]
        },
        options: {
            responsive: true,
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Thời gian'
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Doanh số',
                        min: 0
                    }
                }
            }
        }
    });
</script>

