<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');
    .btn-image-small {
        width: 4rem;
        height: 4rem;
        border: 1px dashed blue;
        background-color: #f8f9fa;
        border-radius: 0.25rem;
        padding: 0.375rem 0.75rem;
        cursor: pointer;
    }

    .p-sticky {
        position: -webkit-sticky;
        position: sticky;
        top: 0;
        z-index: 1000;
    }

    .btn-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }

    .btn-is-valid {
        border-color: green;
    }

    .btn-is-invalid {
        border-color: red;
    }


    .tox-promotion {
        display: none !important;
    }
    .tox-statusbar__branding {
        display: none !important;
    }

    .guaranteeTime {
        display: none;
    }

    .guaranteeTime.show {
        display: block;
    }

</style>

<form action="<c:url value="/api/user/sell/product/add" />" method="post" id="addNewProduct">

    <!-- thông tin cơ bản -->
    <div class="mt-3 row border rounded p-4">
        <h4 class="mb-4">Thông tin cơ bản</h4>
        <!-- ảnh thu nhỏ -->
        <div class="mb-3">
            <label for="thumbnailFile" class="form-label h6">Ảnh thu nhỏ <span class="text-danger">*</span></label>
            
            <p class="text-muted">Ảnh thu nhỏ giúp khách hàng nhận biết sản phẩm của bạn.</p>
            <%-- input nhận hình ảnh --%>
            <input type="file" class="d-none" id="thumbnailFile" accept="image/*">
            <%-- input nhận url --%>
            <input type="hidden" id="thumbnail" name="thumbnail" >
            <button title="click để thêm hình ảnh" type="button" class="btn-image" id="thumbnailBtn">
                <i class="bi bi-image fs-1 text-primary"></i>
            </button>
        </div>
        <%-- ảnh sản phẩm (tối đa 9 ảnh)--%>
        <div class="mb-3">
            <label for="imagesFile" class="form-label h6">Ảnh sản phẩm<span class="text-danger">*</span></label>
            <p class="text-muted">Tối đa 9 ảnh.</p>
            <input type="file" class="form-control d-none" id="imagesFile" multiple accept="image/*"/>
            <input type="file" class="form-control d-none" id="imageFile" accept="image/*"/>
            <div class="d-flex flex-wrap">
                <button title="click để thêm hình ảnh" type="button" class="btn-image" id="imageBtn">
                    <i class="bi bi-image fs-1 text-primary"></i>
                </button>
            </div>
        </div>

        <%-- video sản phẩm --%>
        <div class="mb-3">
            <label for="videoFile" class="form-label h6">Video sản phẩm <span class="text-danger">*</span></label>
            <p class="text-muted">Hãy chọn video mô tả sản phẩm của bạn</p>
                <ul class="text-muted small">
                    <li>Chỉ chấp nhận video có định dạng mp4</li>
                    <li>Chỉ chấp nhận video có dung lượng nhỏ hơn 30MB</li>
                    <li>Chỉ chấp nhận video có thời lượng từ 3 giây đến 1 phút</li>
                </ul>
            <input type="file" class="form-control d-none" id="videoFile" accept="video/*"/>
            <input type="hidden" name="video" id="video" value=""/>
            <button title="click để thêm video" type="button" class="btn-image" id="videoBtn">
                <i class="bi bi-camera-video fs-1 text-primary"></i>
            </button>
        </div>

        <!-- tên sản phẩm -->
        <div class="mb-3">
            <label for="name" class="form-label h6">Tên sản phẩm <span class="text-danger">*</span></label>
            <p class="text-muted">Gợi ý đặt tên: Tên sản phẩm + thương hiệu + model + thông số kỹ thuật</p>
            <input type="text" class="form-control" id="name" name="name"  placeholder="Nhập tên sản phẩm">
        </div>

        <!-- danh mục -->

        <div class="mb-3">
            <label for="category" class="form-label h6">Danh mục <span class="text-danger">*</span></label>
            <input type="hidden" name="category" id="category" value=""/>
            <input id="catShowModel" type="button" class="form-control" data-bs-toggle="modal" data-bs-target="#categoryModal" value="Chọn danh mục">
        </div>

        <%-- mô tả sản phẩm --%>

        <div class="mb-3">
            <label id="lbDesc" for="description" class="form-label h6">Mô tả sản phẩm <span class="text-danger">*</span></label>
            <p class="text-muted">Mô tả chi tiết sản phẩm của bạn, giúp khách hàng hiểu rõ hơn về sản phẩm</p>
            <p id='descInfo' class="d-none text-danger">Nhâp ít nhất 100 ký tự, tối đa 5000 ký tự. </p>
            <textarea class="form-control" id="description" name="description"></textarea>
        </div>
    </div>

    <%-- Thông tin chi tiết --%>
    <div class="mt-3 row border rounded p-4">
        <h4 class="mb-4">Thông tin chi tiết</h4>

        <%-- thương hiệu --%>
        <div class="mb-3">
            <label for="brand" class="form-label h6">Thương hiệu <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="brand" name="brand"  placeholder="Nhập thương hiệu">
        </div>

        <%-- xuất xứ thương hiệu --%>
        <div class="mb-3">
            <label for="brandOrigin" class="form-label h6">Xuất xứ thương hiệu <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="brandOrigin" name="brandOrigin"  placeholder="Nhập xuất xứ thương hiệu">
        </div>

        <%-- Xuất xứ sản phẩm --%>
        <div class="mb-3">
            <label for="madeIn" class="form-label h6">Xuất xứ sản phẩm <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="madeIn" name="madeIn"  placeholder="Nhập xuất xứ sản phẩm">
        </div>

        <%-- bảo hành --%>
        <div class="mb-3">
            <label for="guarantee" class="form-label h6">Bảo hành <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="guarantee" name="guarantee"  placeholder="Nhập thời gian bảo hành">
        </div>

    </div>


    <%-- thông tin bán hàng --%>
    <div class="mt-3 row border rounded p-4">
        <div class="container mt-3">
        <div class="row">
            <div class="col-12">
                <div class="mb-3">
                    <label for="style" class="form-label h6">Thêm kiểu sản phẩm</label>
                    <p class="text-muted">Thêm kiểu sản phẩm như kiểu dáng, màu sắc...</p>
                    <input id="addStyle" type="button" class="form-control" value="Thêm kiểu sản phẩm"/>
                    <input type="file" class="form-control d-none" id="styleFile" accept="image/*"/>
                </div>
            </div>
            <div class="col-12">
                <div class="mb-3">
                    <label for="size" class="form-label h6">Thêm kích thước sản phẩm</label>
                    <p class="text-muted">Thêm kích thước sản phẩm như kích thước, trọng lượng...</p>
                    <input id="addSize" type="button" class="form-control" value="Thêm kích thước sản phẩm"/>
                </div>
            </div>
            <div class="col-12">
                <div class="mb-3">
                    <label for="list" class="form-label h6">Danh sách phân loại hàng</label>
                    <p class="text-muted">Danh sách dựa theo phân loại hàng (nếu có), mặc định không có phân loại</p>
                    <div class="col-12" id="autoFill">
                            <div class="mb-3">
                                <div class="input-group mb-3">
                                    <input type="text" class="form-control" id="autoPriceOrg" placeholder="Giá gốc"/>
                                    <input type="text" class="form-control" id="autoPrice" placeholder="Giá bán"/>
                                    <input type="text" class="form-control" id="autoQuantity" placeholder="Tồn kho"/>
                                    <button class="btn btn-outline-primary" type="button" id="autoFillBtn">Áp dụng</button>
                                </div>
                            </div>
                        </div>
                    <table class="table table-bordered" id="tableList">
                        <thead>
                            <tr>
                                <th>Giá Niêm Yết</th>
                                <th>Giá Bán</th>
                                <th>Tồn kho</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><input type="number" class="form-control" name="priceOrg[]" placeholder="Giá Niêm Yết"/></td>
                                <td><input type="number" class="form-control" name="price[]" placeholder="Giá Bán"/></td>
                                <td><input type="number" class="form-control" name="quantity[]" placeholder="Tồn kho"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        </div>
    

    </div>

    <%-- thông tin vận chuyển --%>
    <div class="mt-3 row border rounded p-4">
        <h4 class="mb-4">Thông tin vận chuyển</h4>

        <div class="container mt-3">
            <div class="row">
                <div class="col-12">
                    <div class="mb-3">
                        <label for="productWeight" class="form-label h6">Trọng lượng sản phẩm <span class="text-danger">*</span></label>
                        <p class="text-muted">Nhập trọng lượng sản phẩm (đơn vị g)</p>
                        <input type="number" class="form-control" id="productWeight" name="productWeight" placeholder="Trọng lượng sản phẩm"/>
                    </div>
                    <div class="mb-3">
                        <label id="productSize" class="form-label h6">Kích thước sản phẩm <span class="text-danger">*</span></label>
                        <p class="text-muted">Nhập kích thước sản phẩm (dài x rộng x cao) (đơn vị cm)</p>
                        <div class="input-group mb-3">
                            <input type="number" id="productLength" name="productLength" class="form-control mb-3" placeholder="Dài"/>
                            <input type="number" id="productWidth" name="productWidth" class="form-control mb-3" placeholder="Rộng"/>
                            <input type="number" id="productHeight" name="productHeight" class="form-control mb-3" placeholder="Cao"/>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="shipping" class="form-label h6">Phí vận chuyển ước tính</label>
                        <p class="text-muted">Phí vận chuyển ước tính cho sản phẩm này</p>
                        <input type="text" class="form-control" id="shipping" name="shipping" readonly/>
                        <p id="overSize" class="text-danger d-none"> Hàng cồng kềnh </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%-- nộp form --%>
    <div class="mt-3 row border rounded p-4">
        <div class="col-12 d-flex flex-wrap justify-content-between align-items-center mb-4">
            <h4 class="mb-0">Nộp form</h4>
            <p class="text-muted">Nhấn nút "Nộp form" để hoàn tất</p>
                <select class="form-select me-2 mt-3" name="action" aria-label="Chọn trạng thái">
                    <option value="add_publish">Thêm và Đăng</option>
                    <option value="add_hidden">Thêm và Ẩn</option>
                </select>
            <button type="submit" class="btn btn-primary mt-3">Nộp form</button>
        </div>
    </div>

</form>

<%@ include file="/WEB-INF/views/component/user/sell/product/category.jsp" %>

<%-- nhúng thư viện tinymce --%>
<script src="<c:url value="/resources/tinymce/tinymce.min.js" />"></script>
<script src="<c:url value="/resources/js/user/sell/product/add.js" />"></script>
