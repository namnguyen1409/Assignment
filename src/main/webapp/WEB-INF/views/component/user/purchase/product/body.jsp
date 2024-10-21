<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<style>
    .preview img {
        width: 100%;
        height: 100%;
        aspect-ratio: 1/1;
        object-fit: cover;
    }

    .preview video {
        width: 100%;
        height: 100%;
        aspect-ratio: 1 / 1;
        object-fit: cover;
    }


    .small-img img{
        aspect-ratio: 1/1;
        object-fit: cover;
    }

    #scrollRight {
        width: 20px;
        background-color: rgba(0, 0, 0, 0.5);
        color: white;
        position: absolute;
        right: 0;
        top: 50%;
        transform: translateY(-50%);
        cursor: pointer;
    }

    #scrollLeft {
        width: 20px;
        background-color: rgba(0, 0, 0, 0.5);
        color: white;
        position: absolute;
        left: 0;
        top: 50%;
        transform: translateY(-50%);
        cursor: pointer;
    }


    .active-image {
        border: 2px solid red;
    }
    .star {
        font-size: 24px;
        color: gray;
        position: relative;
        display: inline-block;
    }
    .star.filled::before {
        color: gold; /* Ngôi sao đầy màu vàng */
    }

    .star.partial::before {
        content: '\2605';
        color: gold;
        position: absolute;
        top: 0;
        left: 0;
        width: var(--fill-width);
        overflow: hidden;
    }


    .img-variant {
        width: 50px;
        height: 50px;
        object-fit: cover;
        aspect-ratio: 1/1;
        border-radius: 10px;
    }

</style>

<%-- phần sản phẩm --%>
<section class="container mt-3">
    <div class="row p-3 bg-light">
        <%-- phần ảnh sản phẩm --%>
        <div class="mt-5 col-sm-12 col-md-6 col-lg-4">
            <%-- ảnh lớn --%>
            <div class="d-flex flex-column">
                <div class="position-relative preview">
                    <img id="main-preview" src="<c:url value='/resources/images/products/${product.thumbnail}'/>" class="card-img-top" alt="${product.name}">

                    <video class="d-none position-absolute top-0 start-0" id="video">
                        <source src="<c:url value='/resources/videos/products/${product.video}'/>" type="video/mp4">
                    </video>
                </div>
            </div>
            <%-- list ảnh nhỏ --%>
            <div class="d-flex mt-1 overflow-hidden position-relative">
                <div class="d-flex flex-nowarp" id="scrollAble">
                    <!-- Ảnh nhỏ thứ 1 -->
                    <div class="col-3 d-inline-block pe-1 small-img">
                        <img src="<c:url value='/resources/images/products/${product.thumbnail}'/>" class="card-img-top active-image preview-image" alt="${product.name}" data-preview-src="<c:url value='/resources/images/products/${product.thumbnail}'/>">
                    </div>
                    <!-- Ảnh nhỏ thứ 2 -->
                    <div id="video-thumbnail" class="col-3 d-inline-block me-1 position-relative bg-dark">
                        <img class="preview-image">
                        <i class="bi bi-play-circle-fill fs-1 text-white position-absolute top-50 start-50 translate-middle"></i>
                    </div>
                    <%-- danh sách ảnh sản phẩm --%>
                    <c:forEach var="image" items="${product.images}">
                        <div class="col-3 d-inline-block pe-1 small-img">
                            <img id="image-${image.id}" src="<c:url value='/resources/images/products/${image.image}'/>" class="card-img-top preview-image" alt="${product.name}" data-preview-src="<c:url value='/resources/images/products/${image.image}'/>">
                        </div>
                    </c:forEach>
                </div>
                <div id="scrollRight" class="d-flex justify-content-center align-items-center scroll h-50">
                    <i class="bi bi-chevron-right fs-3"></i>
                </div>

                <div id="scrollLeft" class="d-flex justify-content-center align-items-center scroll h-50">
                    <i class="bi bi-chevron-left fs-3"></i>
                </div>
            </div>

        </div>
        <%-- thông tin sản phẩm --%>

        <div class="mt-5 col-sm-12 col-md-6 col-lg-8 ">
            <h5 class="fw-bold">${product.name}</h5>
            <div class="d-flex align-items-center">
                <span class="text-danger fs-5">${avgRating}</span>
                <div class="ratings me-2 rating" value="${avgRating}"></div>
                <span class="mx-2 text-muted">|</span>
                <span class="text-muted">${product.buyCount} đã bán</span>
                <span class="mx-2 text-muted">|</span>
                <span class="text-muted">${product.viewCount} lượt xem</span>
                <%-- tố cáo sản phẩm; float right --%>
                <a href="<c:url value='/report/product/${product.id}'/>" class="text-danger ms-auto">
                    <i class="bi bi-flag"></i> Báo cáo sản phẩm
                </a>
            </div>

            <div class="d-flex align-items-center bg-light p-3">
                <div id = "oldPrice">
                    <c:if test="${product.priceOrgFrom != product.priceOrgTo}">
                        <span class="text-muted text-decoration-line-through fs-4 fw-bold currency">${product.priceOrgFrom}</span>
                        <span class="text-muted fs-4 fw-bold">-</span>
                        <span class="text-muted text-decoration-line-through fs-4 fw-bold currency">${product.priceOrgTo}</span>
                    </c:if>
                    <c:if test="${product.priceOrgFrom == product.priceOrgTo}">
                        <span class="text-muted text-decoration-line-through fs-4 fw-bold currency">${product.priceOrgFrom}</span>
                    </c:if>
                </div>
                <span class="me-3"></span>
                <div id="salePrice">
                    <c:if test="${product.priceFrom != product.priceTo}">
                        <span class="text-danger fs-4 fw-bold currency">${product.priceFrom}</span>
                        <span class="text-danger fs-4 fw-bold">-</span>
                        <span class="text-danger fs-4 fw-bold currency">${product.priceTo}</span>
                    </c:if>
                    <c:if test="${product.priceFrom == product.priceTo}">
                        <span class="text-danger fs-4 fw-bold currency">${product.priceFrom}</span>
                    </c:if>
                </div>
                <span id="discount" class="badge bg-danger text-white m-2">-${product.discount}%</span>
            </div>

            <%-- vận chuyển --%>
            <div class="mb-3">
                <label for="location" class="form-label h6">Vận chuyển tới</label>
                <select class="form-select" id="location">
                    <option>Chọn địa chỉ</option>
                    <c:forEach var="location" items="${locations}">
                        <option value="${location.id}">${location.detailAddress}, ${location.ward.name} - ${location.district.name} - ${location.province.name}</option>
                    </c:forEach>
                </select>
                <p class="text-muted pt-3">chi phí vận chuyển ước tính: <span id="shipping" class="fw-bold">--</span></p>
            </div>
            <c:if test="${product.variants.size() > 1}">
                <%-- biến thể sản phẩm --%>
                <div class="mt-3">
                    <h6 class="fw-bold">Biến thể sản phẩm</h6>
                    <div id="style-container"></div>
                    <div id="size-container"></div>
                    <div id="variant-info"></div>
                </div>
            </c:if>


            <%-- chọn mua/thêm vào giỏ hàng --%>
            <div class="mt-4">
                <div class="d-flex align-items-center mb-3">
                    <label for="quantityInput" class="form-label h6 me-3">Số lượng</label>
                    <div class="input-group" style="max-width: 150px;">
                        <button class="btn btn-outline-secondary" type="button" id="minus">
                            <i class="bi bi-dash"></i>
                        </button>
                        <input 
                            type="number" 
                            class="form-control text-center" 
                            id="quantityInput" 
                            name="quantityInput" 
                            value="1" 
                            min="1"
                            style="max-width: 60px;"
                        />
                        <button class="btn btn-outline-secondary" type="button" id="plus">
                            <i class="bi bi-plus"></i>
                        </button>
                    </div>
                </div>

                <div class="d-flex" style="max-width: 400px">
                    <button id="addToCart" class="btn btn-outline-danger me-3 flex-grow-1">
                        <i class="bi bi-cart-plus me-1"></i> Thêm vào giỏ hàng
                    </button>
                    <button id="buyNow" class="btn btn-danger flex-grow-1">
                        <i class="bi bi-cash-coin me-1"></i> Mua ngay
                    </button>
                </div>
            </div>

            <%-- Form ẩn --%>
            <form id="variantForm" action="<c:url value='/user/purchase/buy' />" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="hidden" name="variantId" id="variantId" value="${product.variants.get(0).id}">
                <input type="hidden" name="quantity" id="quantity" value="1">
            </form>

        </div>
    </div>

    <%-- Thông tin cửa hàng --%>
    <div class="mt-5 p-4 bg-light rounded">
        <h5 class="fw-bold mb-3">Thông tin cửa hàng</h5>
        
        <div class="d-flex align-items-center mb-3">
            <img 
                src="<c:url value='/resources/images/avatar/${store.avatar}'/>" 
                class="rounded-circle me-3" 
                alt="${store.name}" 
                width="50" 
                height="50"
            />
            <div class="flex-grow-1">
                <h6 class="fw-bold mb-1">${store.name}</h6>
                <p class="text-muted mb-0">${store.description}</p>
            </div>
            <div class="ms-3 d-flex">
                <%-- Xem cửa hàng --%>
                <a href="<c:url value='/user/purchase/store/${store.id}'/>" 
                class="btn btn-outline-danger me-2 d-flex align-items-center">
                    <i class="bi bi-shop me-1"></i> Xem cửa hàng
                </a>
                <%-- Chat với cửa hàng --%>
                <a href="<c:url value='/user/purchase/chat/${store.id}'/>" 
                class="btn btn-outline-danger d-flex align-items-center">
                    <i class="bi bi-chat-left-text me-1"></i> Chat
                </a>
            </div>
        </div>

        <p id="storeJoinDate" class="text-muted mt-3"></p>

    </div>
    <%-- mô tả sản phẩm --%>
    <div class="mt-5 p-3 bg-light">
        <h5 class="fw-bold">Mô tả sản phẩm</h5>
        ${product.description}
    </div>

</section>


<%-- đánh giá sản phẩm --%>
<section class="container mt-3">

</section>




<script>

    document.getElementById("buyNow").addEventListener("click", function(){
        const variantForm = document.getElementById('variantForm');
        variantForm.submit();

    });


    document.getElementById("addToCart").addEventListener("click", function () {
        const variantForm = document.getElementById('variantForm');
        const url = baseHref + "api/user/purchase/cart/add";
        const formData = new FormData(variantForm);
        const params = new URLSearchParams(formData);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        fetch(url, {
            method: 'POST',
            body: params,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeader]: csrfToken
            },
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Thêm vào giỏ hàng thành công");
            } else {
                alert("Thêm vào giỏ hàng thất bại");
            }
        });
    });


    document.addEventListener("DOMContentLoaded", function () {
        const joinDateElement = document.getElementById('storeJoinDate');
        const rawDate = "${store.createdAt}";

        if (rawDate) {
            joinDateElement.innerText = "Đã tham gia từ: " + formatDate(rawDate);
        }

        function formatDate(dateString) {
            const date = new Date(dateString);
            const day = String(date.getDate()).padStart(2, '0');
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const year = date.getFullYear();
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');
            return day + "/" + month + "/" + year + " " + hours + ":" + minutes;
        }
    });

    document.addEventListener("DOMContentLoaded", function () {
        // Xử lý số lượng
        const minusButton = document.getElementById('minus');
        const plusButton = document.getElementById('plus');
        const quantityInput = document.getElementById('quantityInput');
        const hiddenQuantityInput = document.getElementById('quantity'); // Input ẩn cho số lượng

        // Giới hạn số lượng hợp lệ
        function validateQuantity() {
            let quantity = parseInt(quantityInput.value);
            if (isNaN(quantity) || quantity < 1) {
                quantityInput.value = 1;
            }
            // Cập nhật giá trị của input ẩn
            hiddenQuantityInput.value = quantityInput.value;
        }

        // Giảm số lượng
        minusButton.addEventListener('click', () => {
            let currentValue = parseInt(quantityInput.value) || 1;
            if (currentValue > 1) {
                quantityInput.value = currentValue - 1;
                hiddenQuantityInput.value = quantityInput.value; // Cập nhật input ẩn
            }
        });

        // Tăng số lượng
        plusButton.addEventListener('click', () => {
            let currentValue = parseInt(quantityInput.value) || 1;
            quantityInput.value = currentValue + 1;
            hiddenQuantityInput.value = quantityInput.value; // Cập nhật input ẩn
        });

        // Kiểm tra số lượng khi người dùng nhập tay
        quantityInput.addEventListener('input', () => {
            validateQuantity();
            hiddenQuantityInput.value = quantityInput.value; // Cập nhật input ẩn
        });
    });



    const productId = ${product.id};

    var formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });

    var currency = document.querySelectorAll('.currency');
    currency.forEach((element) => {
        element.innerHTML = formatter.format(element.innerHTML);
    });

    // tính toán chi phí vận chuyển
    document.getElementById("location").addEventListener("change", function() {
        const locationId = this.value;
        const productId = ${product.id};
        const url = baseHref + "api/user/sell/product/calculate-ship-fee/" + productId + "/" + locationId;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                const shipping = document.getElementById("shipping");
                shipping.innerHTML = formatter.format(data.shipFee);
            });
    })


    document.addEventListener('DOMContentLoaded', function () {
        //load video thumbnail
        const video = document.getElementById('video');
        const mainPreview = document.getElementById('main-preview');
        const previewImages = document.querySelectorAll('.preview-image');


        function clearAllActiveImage() {
            const activeImage = document.querySelector('.active-image');
            if (activeImage) {
                activeImage.classList.remove('active-image');
            }
        }

        // Thêm sự kiện click vào các ảnh nhỏ
        previewImages.forEach(image => {
            image.addEventListener('click', function () {
                clearAllActiveImage();
                this.classList.add('active-image');
                const video = document.getElementById('video');
                video.classList.add('d-none');
                video.pause();
                const previewSrc = this.getAttribute('data-preview-src');
                mainPreview.src = previewSrc;
            });
        });

        // Thêm sự kiện click vào video thumbnail
        const videoThumbnail = document.getElementById('video-thumbnail');
        videoThumbnail.addEventListener('click', function () {
            clearAllActiveImage();
            this.classList.add('active-image');
            const video = document.getElementById('video');
            video.play();
            video.classList.remove('d-none');
        });


        // Scroll left and right
        const scrollRight = document.getElementById('scrollRight');
        const scrollLeft = document.getElementById('scrollLeft');
        let index = 0;
        let maxIndex = previewImages.length - 4;

        const scrollAble = document.getElementById('scrollAble');

        scrollRight.addEventListener('click', function () {
            console.log(index);
            if (index < maxIndex) {
                index++;
                scrollAble.style.transform = `translateX(-` + index * 25 + `%)`;
            }
        });

        scrollLeft.addEventListener('click', function () {
            console.log(index);
            if (index > 0) {
                index--;
                scrollAble.style.transform = `translateX(-` + index * 25 + `%)`;
            }
        });

    });

    document.addEventListener('DOMContentLoaded', (event) => {
        const ratings = document.querySelectorAll('.rating');
        ratings.forEach((rating) => {
            const value = rating.getAttribute('value');
            const interger = parseInt(value);
            const decimal = value - interger;
            for (let i = 0; i < 5; i++) {
                const star = document.createElement('span');
                star.classList.add('star');
                star.innerHTML = '&#9733';
                if (i < interger) {
                    star.classList.add('filled');
                } else if (i === interger && decimal > 0) {
                    star.classList.add('partial');
                    star.style.setProperty('--fill-width', `${decimal * 100}%`);
                }
                rating.appendChild(star);
            }

        });
    });


</script>

<script src="<c:url value='/resources/js/user/purchase/product.js'/>"></script>