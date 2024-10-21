<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.assignment.models.entities.shop.product.ProductStatus" %>

<style>
.product-name {
    max-height: 3em; /* Giới hạn chiều cao tối đa */
    overflow: hidden; /* Ẩn phần nội dung thừa */
    display: -webkit-box; /* Sử dụng flexbox */
    -webkit-box-orient: vertical; /* Đặt chiều dọc cho flexbox */
    -webkit-line-clamp: 2; /* Giới hạn số dòng hiển thị */
    text-overflow: ellipsis; /* Hiện dấu ... */
}

</style>

<!-- Nav bar nằm ngang -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Quản lý hàng</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item ${tab eq 'listAllProduct'?'border-bottom border-primary border-4': ''}">
                    <a class="nav-link" href="<c:url value="/user/sell/product" />">Tất cả sản phẩm</a>
                </li>
                <li class="nav-item ${tab eq 'listActiveProduct'?'border-bottom border-primary border-4': ''}">
                    <a class="nav-link" href="<c:url value="/user/sell/product/active" />">Sản phẩm đang bán</a>
                </li>
                <li class="nav-item ${tab eq 'listHideProduct'?'border-bottom border-primary border-4': ''}">
                    <a class="nav-link" href="<c:url value="/user/sell/product/hide" />">Sản phẩm đã ẩn</a>
                </li>
                <li class="nav-item ${tab eq 'listInActiveProduct'?'border-bottom border-primary border-4': ''}">
                    <a class="nav-link" href="<c:url value="/user/sell/product/inactive" />">Sản phẩm bị tạm khóa</a>
                </li>
                <li class="nav-item ${tab eq 'listDeletedProduct'?'border-bottom border-primary border-4': ''}">
                    <a class="nav-link" href="<c:url value="/user/sell/product/deleted" />">Sản phẩm bị xóa</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Danh sách sản phẩm -->


<div class="container mt-3 bg-light p-3">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <form method="get" class="row g-3">
            <div class="col-md-4">
                <div class="input-group">
                    <input type="text" class="form-control" name="search" placeholder="Tìm kiếm sản phẩm" value="${search}">
                    <button class="btn btn-outline-secondary" type="submit">Tìm kiếm</button>
                </div>
            </div>

            <div class="col-md-4">
                <div class="input-group">
                    <label class="input-group-text" for="sort">Sắp xếp theo</label>
                    <select class="form-select" id="sort" name="sort">
                        <option value="1" ${sort == 1 ? 'selected' : ''}>Giá: thấp đến cao</option>
                        <option value="2" ${sort == 2 ? 'selected' : ''}>Giá: cao đến thấp</option>
                        <option value="3" ${sort == 3 ? 'selected' : ''}>Ngày tạo: Mới đến cũ</option>
                        <option value="4" ${sort == 4 ? 'selected' : ''}>Ngày tạo: Cũ đến mới</option>
                    </select>
                </div>
            </div>

            <div class="col-md-4">
                <div class="input-group">
                    <label class="input-group-text" for="size">Hiển thị</label>
                    <select class="form-select" id="size" name="size">
                        <option value="5" ${size == 5 ? 'selected' : ''}>5 sản phẩm / trang</option>
                        <option value="10" ${size == 10 ? 'selected' : ''}>10 sản phẩm / trang</option>
                        <option value="20" ${size == 20 ? 'selected' : ''}>20 sản phẩm / trang</option>
                        <option value="50" ${size == 50 ? 'selected' : ''}>50 sản phẩm / trang</option>
                    </select>
                </div>
            </div>
            
            <input type="hidden" name="page" value="${currentPage}">
        </form>

        <script>
            document.getElementById('sort').addEventListener('change', function() {
                this.form.submit();
            });
            document.getElementById('size').addEventListener('change', function() {
                // page = 1 when change size
                this.form.page.value = 1;
                this.form.submit();
            });
        </script>
    </div>

    <c:if test="${tab eq 'listAllProduct'}">
        <div class="table-responsive">
            <div class="mb-3">
                <span class="text-muted">Tìm thấy ${totalItem} sản phẩm</span>
            </div>
            <table class="table table-hover table-bordered align-middle" style="min-width:1000px;">
                <thead class="table-light sticky-top">
                    <tr>
                        <th scope="col">Ảnh</th>
                        <th scope="col">Tên</th>
                        <th scope="col">Giá</th>
                        <th scope="col">Thống kê</th>
                        <th scope="col">Trạng thái</th>
                        <th scope="col">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${products}" var="product">
                        <tr>
                            <td>
                                <img src="<c:url value="/resources/images/products/${product.thumbnail}" />" alt="thumbnail" class="img-thumbnail rounded shadow-sm" style="width: 100px; height: 100px;">
                            </td>
                            <td>
                                <span class="text-truncate d-block" style="max-width: 200px;" title="${product.name}">${product.name}</span>
                            </td>
                            <td>
                                <c:if test="${product.priceFrom != product.priceTo}">
                                    <span class="currency">${product.priceFrom}</span> - <span class="currency">${product.priceTo}</span>
                                </c:if>
                                <c:if test="${product.priceFrom == product.priceTo}">
                                    <span class="currency">${product.priceFrom} </span>
                                </c:if>
                            </td>
                            <td>
                                <ul class="list-group bg-none">
                                    <li class="list-group-item border-0 px-3 text-primary"><i class="bi bi-eye"></i> ${product.viewCount}</li>
                                    <li class="list-group-item border-0 px-3 text-danger"><i class="bi bi-heart"></i> ${product.likeCount}</li>
                                    <li class="list-group-item border-0 px-3 text-success"><i class="bi bi-cart"></i> ${product.buyCount}</li>
                                </ul>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${product.status == ProductStatus.ACTIVE}">
                                        <span class="badge bg-success">Đang bán</span>
                                    </c:when>
                                    <c:when test="${product.status == ProductStatus.HIDDEN}">
                                        <span class="badge bg-warning text-dark">Đã ẩn</span>
                                    </c:when>
                                    <c:when test="${product.status == ProductStatus.INACTIVE}">
                                        <span class="badge bg-danger">Bị tạm khóa</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">Đã xóa</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div class="d-flex gap-2">
                                    <c:if test="${product.status != ProductStatus.DELETED && product.status != ProductStatus.INACTIVE}">
                                        <a href="<c:url value="/user/sell/product/edit/${product.id}" />" class="btn btn-outline-primary btn-sm">
                                            <i class="bi bi-pencil"></i> Sửa
                                        </a>
                                        <a href="<c:url value="/user/sell/product/delete/${product.id}" />" class="btn btn-outline-danger btn-sm">
                                            <i class="bi bi-trash"></i> Xóa
                                        </a>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </c:if>

    <c:if test="${tab eq 'listActiveProduct'}">
    <%-- hiển thị dưới dạng card --%>
        <div class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4">
            <c:forEach items="${products}" var="product">
                <div class="col">
                    <div class="card h-100 shadow rounded border-0">
                        <div class="position-relative">
                            <img src="<c:url value="/resources/images/products/${product.thumbnail}" />" class="card-img-top rounded-top" alt="thumbnail" style="aspect-ratio:1/1;">
                            <div class="card-img-overlay d-flex flex-column justify-content-end p-2" style="background: linear-gradient(to top, rgba(0,0,0,0.5), rgba(0,0,0,0) 50%);">
                                <h5 class="card-title product-name text-white">
                                    ${product.name}
                                </h5>
                                <p class="card-text text-white mb-0">
                                    <c:if test="${product.priceFrom != product.priceTo}">
                                        <span class="currency">${product.priceFrom}</span> - <span class="currency">${product.priceTo}</span>
                                    </c:if>
                                    <c:if test="${product.priceFrom == product.priceTo}">
                                        <span class="currency">${product.priceFrom} </span>
                                    </c:if>
                                </p>
                            </div>
                        </div>
                        <div class="card-body">
                            <ul class="list-group list-group-horizontal justify-content-center">
                                <li class="list-group-item border-0 px-3 text-primary">
                                    <i class="bi bi-eye"></i> ${product.viewCount}
                                </li>
                                <li class="list-group-item border-0 px-3 text-danger">
                                    <i class="bi bi-heart"></i> ${product.likeCount}
                                </li>
                                <li class="list-group-item border-0 px-3 text-success">
                                    <i class="bi bi-cart"></i> ${product.buyCount}
                                </li>
                            </ul>
                        </div>
                        <div class="card-footer d-flex justify-content-around border-0">
                            <a href="<c:url value="/user/sell/product/edit/${product.id}" />" class="btn btn-outline-primary btn-sm">
                                <i class="bi bi-pencil"></i> Sửa
                            </a>
                            <a href="<c:url value="/user/sell/product/hide/${product.id}" />" class="btn btn-outline-warning btn-sm">
                                <i class="bi bi-eye-slash"></i> Ẩn
                            </a>
                            <a href="<c:url value="/user/sell/product/delete/${product.id}" />" class="btn btn-outline-danger btn-sm">
                                <i class="bi bi-trash"></i> Xóa
                            </a>
                        </div>
                    </div>
                </div>

            </c:forEach>
        </div>
    </c:if>

    <c:if test="${tab eq 'listHideProduct'}">
    <%-- hiển thị dưới dạng card --%>
        <div class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4">
            <c:forEach items="${products}" var="product">
                <div class="col">
                    <div class="card h-100 shadow rounded border-0">
                        <div class="position-relative">
                            <img src="<c:url value="/resources/images/products/${product.thumbnail}" />" class="card-img-top rounded-top" alt="thumbnail" style="aspect-ratio:1/1;">
                            <div class="card-img-overlay d-flex flex-column justify-content-end p-2" style="background: linear-gradient(to top, rgba(0,0,0,0.5), rgba(0,0,0,0) 50%);">
                                <h5 class="card-title product-name text-white">
                                    ${product.name}
                                </h5>
                                <p class="card-text text-white mb-0">
                                    <c:if test="${product.priceFrom != product.priceTo}">
                                        <span class="currency">${product.priceFrom}</span> - <span class="currency">${product.priceTo}</span>
                                    </c:if>
                                    <c:if test="${product.priceFrom == product.priceTo}">
                                        <span class="currency">${product.priceFrom} </span>
                                    </c:if>
                                </p>
                            </div>
                        </div>
                        <div class="card-body">
                            <ul class="list-group list-group-horizontal justify-content-center">
                                <li class="list-group-item border-0 px-3 text-primary">
                                    <i class="bi bi-eye"></i> ${product.viewCount}
                                </li>
                                <li class="list-group-item border-0 px-3 text-danger">
                                    <i class="bi bi-heart"></i> ${product.likeCount}
                                </li>
                                <li class="list-group-item border-0 px-3 text-success">
                                    <i class="bi bi-cart"></i> ${product.buyCount}
                                </li>
                            </ul>
                        </div>
                        <div class="card-footer d-flex justify-content-around border-0">
                            <a href="<c:url value="/user/sell/product/edit/${product.id}" />" class="btn btn-outline-primary btn-sm">
                                <i class="bi bi-pencil"></i> Sửa
                            </a>
                            <a href="<c:url value="/user/sell/product/show/${product.id}" />" class="btn btn-outline-warning btn-sm">
                                <i class="bi bi-eye"></i> Hiện
                            </a>
                            <a href="<c:url value="/user/sell/product/delete/${product.id}" />" class="btn btn-outline-danger btn-sm">
                                <i class="bi bi-trash"></i> Xóa
                            </a>
                        </div>
                    </div>
                </div>

            </c:forEach>
        </div>
    </c:if>

    <c:if test="${tab eq 'listInActiveProduct'}">
        <%-- hiện dưới dạng bảng --%>
        <div class="table-responsive">
            <div class="mb-3">
                <span class="text-muted">Tìm thấy ${totalItem} sản phẩm</span>
            </div>
            <table class="table table-hover table-bordered align-middle" style="min-width:1000px;">
                <thead class="table-light sticky-top">
                    <tr>
                        <th scope="col">Ảnh</th>
                        <th scope="col">Tên</th>
                        <th scope="col">Giá</th>
                        <th scope="col">Thống kê</th>
                        <th scope="col">Lý do bị khóa</th>
                        <th scope="col">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${products}" var="product">
                        <tr>
                            <td>
                                <img src="<c:url value="/resources/images/products/${product.thumbnail}" />" alt="thumbnail" class="img-thumbnail rounded shadow-sm" style="width: 100px; height: 100px;">
                            </td>
                            <td>
                                <span class="text-truncate d-block" style="max-width: 200px;" title="${product.name}">${product.name}</span>
                            </td>
                            <td>
                                <c:if test="${product.priceFrom != product.priceTo}">
                                    <span class="currency">${product.priceFrom}</span> - <span class="currency">${product.priceTo}</span>
                                </c:if>
                                <c:if test="${product.priceFrom == product.priceTo}">
                                    <span class="currency">${product.priceFrom} </span>
                                </c:if>
                            </td>
                            <td>
                                <ul class="list-group bg-none">
                                    <li class="list-group-item border-0 px-3 text-primary"><i class="bi bi-eye"></i> ${product.viewCount}</li>
                                    <li class="list-group-item border-0 px-3 text-danger"><i class="bi bi-heart"></i> ${product.likeCount}</li>
                                    <li class="list-group-item border-0 px-3 text-success"><i class="bi bi-cart"></i> ${product.buyCount}</li>
                                </ul>
                            </td>
                            <td>
                                <span class="text-muted">${product.reason}</span>
                            </td>
                            <td>
                                <div class="d-flex gap-2">
                                    <a href="<c:url value="/user/sell/product/delete/${product.id}" />" class="btn btn-outline-danger btn-sm">
                                        <i class="bi bi-trash"></i> Xóa
                                    </a>
                                    <a href="<c:url value="/user/sell/product/report/${product.id}" />" class="btn btn-outline-warning btn-sm">
                                        <i class="bi bi-exclamation-triangle"></i> Báo cáo
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </c:if>

    <c:if test="${tab eq 'listDeletedProduct'}">
        <%-- hiện dưới dạng bảng --%>
        <%-- sản phẩm bị xóa không thể khôi phục --%>
        <div class="table-responsive">
            <div class="mb-3">
                <span class="text-muted">Tìm thấy ${totalItem} sản phẩm</span>
            </div>
            <table class="table table-hover table-bordered align-middle" style="min-width:1000px;">
                <thead class="table-light sticky-top">
                    <tr>
                        <th scope="col">Ảnh</th>
                        <th scope="col">Tên</th>
                        <th scope="col">Giá</th>
                        <th scope="col">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${products}" var="product">
                        <tr>
                            <td>
                                <img src="<c:url value="/resources/images/products/${product.thumbnail}" />" alt="thumbnail" class="img-thumbnail rounded shadow-sm" style="width: 100px; height: 100px;">
                            </td>
                            <td>
                                <span class="text-truncate d-block" style="max-width: 200px;" title="${product.name}">${product.name}</span>
                            </td>
                            <td>
                                <c:if test="${product.priceFrom != product.priceTo}">
                                    <span class="currency">${product.priceFrom}</span> - <span class="currency">${product.priceTo}</span>
                                </c:if>
                                <c:if test="${product.priceFrom == product.priceTo}">
                                    <span class="currency">${product.priceFrom} </span>
                                </c:if>
                            </td>
                            <td>
                                <div class="d-flex gap-2">
                                    <a href="<c:url value="/user/sell/product/show/${product.id}" />" class="btn btn-outline-danger btn-sm">
                                        <i class="bi bi-eye"></i> Xem lại
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>




    <%-- phân trang động --%>
<c:if test="${totalPage > 1}">
    <div class="d-flex justify-content-center mt-5">
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item ${currentPage eq 1 ? 'disabled' : ''}">
                    <button class="page-link" onclick="goToPage(1)" aria-label="Trang đầu">
                        <span aria-hidden="true">&laquo;</span>
                    </button>
                </li>
                <li class="page-item ${currentPage eq 1 ? 'disabled' : ''}">
                    <button class="page-link" onclick="goToPage(${currentPage - 1})" aria-label="Trang trước">
                        <span aria-hidden="true">&lsaquo;</span>
                    </button>
                </li>

                <c:choose>
                    <c:when test="${totalPage <= 5}">
                        <c:forEach begin="1" end="${totalPage}" var="i">
                            <li class="page-item ${currentPage eq i ? 'active' : ''}">
                                <button class="page-link" onclick="goToPage(${i})">${i}</button>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${currentPage > 3}">
                            <li class="page-item"><button class="page-link" onclick="goToPage(1)">1</button></li>
                            <li class="page-item"><span class="page-link">...</span></li>
                        </c:if>
                        
                        <c:forEach begin="${(currentPage - 2) < 1 ? 1 : (currentPage - 2)}" end="${(currentPage + 2) > totalPage ? totalPage : (currentPage + 2)}" var="i">
                            <li class="page-item ${currentPage eq i ? 'active' : ''}">
                                <button class="page-link" onclick="goToPage(${i})">${i}</button>
                            </li>
                        </c:forEach>

                        <c:if test="${currentPage < totalPage - 2}">
                            <li class="page-item"><span class="page-link">...</span></li>
                            <li class="page-item"><button class="page-link" onclick="goToPage(${totalPage})">${totalPage}</button></li>
                        </c:if>
                    </c:otherwise>
                </c:choose>

                <li class="page-item ${currentPage eq totalPage ? 'disabled' : ''}">
                    <button class="page-link" onclick="goToPage(${currentPage + 1})" aria-label="Trang sau">
                        <span aria-hidden="true">&rsaquo;</span>
                    </button>
                </li>

                <li class="page-item ${currentPage eq totalPage ? 'disabled' : ''}">
                    <button class="page-link" onclick="goToPage(${totalPage})" aria-label="Trang cuối">
                        <span aria-hidden="true">&raquo;</span>
                    </button>
                </li>
            </ul>
        </nav>
    </div>
</c:if>




<script>

    function goToPage(page) {
        document.querySelector('input[name="page"]').value = page;
        document.querySelector('form').submit();
    }

    // format currency

    var formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });

    var currency = document.querySelectorAll('.currency');
    currency.forEach((element) => {
        element.innerHTML = formatter.format(element.innerHTML);
    });

    


</script>

