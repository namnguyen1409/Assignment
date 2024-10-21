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
        pediting: 0.375rem 0.75rem;
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
    <!-- thông tin cơ bản -->
    <div class="mt-3 row border rounded p-4">
        <form action="<c:url value="/api/user/sell/product/edit/thumbnail/${product.id}" />" method="post" id="updateThumbnail" class="mb-3">
            <h4 class="mb-4">Thông tin cơ bản</h4>
            <!-- ảnh thu nhỏ -->
            <div class="mb-3">
                <label for="thumbnailFile" class="form-label h6">Ảnh thu nhỏ <span class="text-danger">*</span></label>
                <p class="text-muted">Ảnh thu nhỏ giúp khách hàng nhận biết sản phẩm của bạn.</p>
                <input type="file" class="d-none" id="thumbnailFile" accept="image/*">
                <input type="hidden" id="thumbnail" name="thumbnail" value="${product.thumbnail}">
                <button title="Click để thay đổi hình ảnh" type="button" class="btn-image" id="thumbnailBtn"><img src="<c:url value="/resources/images/products/${product.thumbnail}" />"></button>
            </div>
            <%-- nút cập nhật --%>
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-arrow-repeat"></i> Cập nhật ảnh thu nhỏ
            </button>
        </form>
        <%-- ảnh sản phẩm (tối đa 9 ảnh)--%>
        <form action="<c:url value="/api/user/sell/product/edit/images/${product.id}" />" method="post" id="updateImages" class="mb-3">
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
            <%-- nút cập nhật --%>
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-arrow-repeat"></i> Cập nhật ảnh sản phẩm
            </button>
        </form>

        <%-- video sản phẩm --%>
        <form action="<c:url value="/api/user/sell/product/edit/video/${product.id}" />" method="post" id="updateVideo" class="mb-3">
            <div class="mb-3">
                <label for="videoFile" class="form-label h6">Video sản phẩm <span class="text-danger">*</span></label>
                <p class="text-muted">Hãy chọn video mô tả sản phẩm của bạn</p>
                    <ul class="text-muted small">
                        <li>Chỉ chấp nhận video có định dạng mp4</li>
                        <li>Chỉ chấp nhận video có dung lượng nhỏ hơn 30MB</li>
                        <li>Chỉ chấp nhận video có thời lượng từ 3 giây đến 1 phút</li>
                    </ul>
                <input type="file" class="form-control d-none" id="videoFile" accept="video/*"/>
                <input type="hidden" name="video" id="video" value="${product.video}"/>
                <button title="Click để thay đổi video" type="button" class="btn-image" id="videoBtn">
                    <video width="100%" height="100%" controls="">
                        <source src="<c:url value="/resources/videos/products/${product.video}" />" type="video/mp4">
                    </video>
                </button>
            </div>
            <%-- nút cập nhật --%>
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-arrow-repeat"></i> Cập nhật video sản phẩm
            </button>
        </form>

        
            <!-- tên sản phẩm -->
        
        <form action="<c:url value="/api/user/sell/product/edit/name/${product.id}" />" method="post" id="updateNameProduct" class="mb-3">
            <div class="mb-3">
                <label for="name" class="form-label h6">Tên sản phẩm <span class="text-danger">*</span></label>
                <p class="text-muted">Gợi ý đặt tên: Tên sản phẩm + thương hiệu + model + thông số kỹ thuật</p>
                <div class="input-group">
                    <input type="text" class="form-control" id="name" name="name"  placeholder="Nhập tên sản phẩm" value="${product.name}">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-arrow-repeat"></i> Cập nhật tên
                    </button>
                </div>
            </div>
        </form>

            <!-- danh mục -->
        <div class="mb-3">
            <label for="category" class="form-label h6">Danh mục <span class="text-danger">*</span></label>
            <input type="hidden" name="category" id="category" value="${product.category.id}"/>
            <input id="catShowModel" type="button" class="form-control" data-bs-toggle="modal" data-bs-target="#categoryModal" value="${product.category.parent.name} > ${product.category.name}">
        </div>

            <%-- mô tả sản phẩm --%>
        <form action="<c:url value="/api/user/sell/product/edit/description/${product.id}" />" method="post" id="updateDescription" class="mb-3">
            <div class="mb-3">
                <label id="lbDesc" for="description" class="form-label h6">Mô tả sản phẩm <span class="text-danger">*</span></label>
                <p class="text-muted">Mô tả chi tiết sản phẩm của bạn, giúp khách hàng hiểu rõ hơn về sản phẩm</p>
                <p id='descInfo' class="d-none text-danger">Nhâp ít nhất 100 ký tự, tối đa 5000 ký tự. </p>
                <textarea class="form-control" id="description" name="description">
                    ${product.description}
                </textarea>
            </div>
            <%-- nút cập nhật --%>
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-arrow-repeat"></i> Cập nhật mô tả
            </button>
        </form>
    </div>

    <%-- danh sách phân loại --%>
<%-- danh sách phân loại --%>

    <%-- Thông tin chi tiết --%>
    <div class="mt-3 row border rounded p-4">
        <h4 class="mb-4">Thông tin chi tiết</h4>

        <%-- thương hiệu --%>
        <div class="mb-3">
            <label for="brand" class="form-label h6">Thương hiệu <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="brand" name="brand"  placeholder="Nhập thương hiệu" value="${product.brand}">
        </div>

        <%-- xuất xứ thương hiệu --%>
        <div class="mb-3">
            <label for="brandOrigin" class="form-label h6">Xuất xứ thương hiệu <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="brandOrigin" name="brandOrigin"  placeholder="Nhập xuất xứ thương hiệu" value="${product.brandOrigin}">
        </div>

        <%-- Xuất xứ sản phẩm --%>
        <div class="mb-3">
            <label for="madeIn" class="form-label h6">Xuất xứ sản phẩm <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="madeIn" name="madeIn"  placeholder="Nhập xuất xứ sản phẩm" value="${product.madeIn}">
        </div>

        <%-- bảo hành --%>
        <div class="mb-3">
            <label for="guarantee" class="form-label h6">Bảo hành <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="guarantee" name="guarantee"  placeholder="Nhập thời gian bảo hành" value="${product.guarantee}">
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
                        <input type="number" class="form-control" id="productWeight" name="productWeight" placeholder="Trọng lượng sản phẩm" value="${product.weight}">
                    </div>
                    <div class="mb-3">
                        <label id="productSize" class="form-label h6">Kích thước sản phẩm <span class="text-danger">*</span></label>
                        <p class="text-muted">Nhập kích thước sản phẩm (dài x rộng x cao) (đơn vị cm)</p>
                        <div class="input-group mb-3">
                            <input type="number" id="productLength" name="productLength" class="form-control mb-3" placeholder="Dài" value="${product.length}"/>
                            <input type="number" id="productWidth" name="productWidth" class="form-control mb-3" placeholder="Rộng" value="${product.width}"/>
                            <input type="number" id="productHeight" name="productHeight" class="form-control mb-3" placeholder="Cao" value="${product.height}"/>
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
<div class="mt-3 row border rounded p-4">
    <h4 class="mb-4">Danh sách phân loại</h4>
    <div class="mb-3">
        <label for="variant" class="form-label h6">Danh sách phân loại <span class="text-danger">*</span></label>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <c:if test="${product.variants.get(0).style != null}">
                        <th scope="col">Kiểu</th>
                        <th scope="col">Ảnh</th>
                    </c:if>
                    <c:if test="${product.variants.get(0).size != null}">
                        <th scope="col">Kích thước</th>
                    </c:if>
                    <th scope="col">Giá Niêm Yết</th>
                    <th scope="col">Giá Bán</th>
                    <th scope="col">Số lượng</th>
                    <th scope="col">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="variant" items="${product.variants}">
                    <form class="updateVariant" action="<c:url value='/api/user/sell/product/editVariant/${variant.id}' />" method="post">
                        <tr>
                            <c:if test="${variant.style != null}">
                                <td>
                                    ${variant.style.name}
                                </td>
                                <td>
                                    <img src="<c:url value='/resources/images/products/${variant.style.image}' />" alt="thumbnail" class="img-thumbnail rounded shadow-sm" style="width: 100px; height: 100px;">
                                </td>
                            </c:if>
                            <c:if test="${variant.size != null}">
                                <td>
                                    ${variant.size.name}
                                </td>
                            </c:if>
                            <td>
                                <input type="number" class="form-control" name="price" value="${variant.price}">
                            </td>
                            <td>
                                <input type="number" class="form-control" name="salePrice" value="${variant.salePrice}">
                            </td>
                            <td>
                                <input type="number" class="form-control" name="quantity" value="${variant.quantity}">
                            </td>
                            <td>
                                <input type="hidden" name="id" value="${variant.id}">
                                <button type="submit" class="btn btn-primary">Cập nhật</button>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>






<%@ include file="/WEB-INF/views/component/user/sell/product/category.jsp" %>

<%-- nhúng thư viện tinymce --%>
<script src="<c:url value="/resources/tinymce/tinymce.min.js" />"></script>
<script src="<c:url value="/resources/js/user/sell/product/edit.js" />"></script>

<c:forEach var="image" items="${product.images}">
    <script>
        addNewBtnImage("${image.image}");
    </script>
</c:forEach>
