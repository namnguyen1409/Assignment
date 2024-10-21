<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
    .list-cat {
        width: 10rem;
        height: 12rem;
        border: 1px solid #ccc;
        background-color: rgb(255, 255, 255);
        transition: all 0.3s;
    }

    .list-cat:hover {
        /* zoom in */
        box-shadow: inset 0 0 0 1000px rgba(0, 0, 0, 0.1);
    }

    .list-cat:hover .item-img {
        transform: scale(1.1);
    }

    .list-cat:hover .cat-name {
        color: #f60;
    }

    .add-border {
        border: 1px solid #ccc;
    }

    .category-container {
        position: relative;
    }

    .item-img {
        flex-shrink: 1;
        width: 70%;
        height: 70%;
        margin-top: 10%;
        transition: all 0.3s;
    }

    .cat-name {
        font-size: 1rem;
        color: #3f3f3f;
        transition: all 0.3s;
    }

    .over-x-hidden {
        overflow-x:hidden;
    }


    #scrollLeft {
        position: absolute;
        left: 0;
        top: 50%;
        transform: translateY(-50%) translateX(-50%);
    }

    #scrollRight {
        position: absolute;
        right: 0;
        top: 50%;
        transform: translateY(-50%) translateX(50%);
    }

    #scrollRight:hover {
        transform: translateY(-50%) translateX(50%) scale(1.2);
        box-shadow: inset 0 0 0 1000px rgba(0, 0, 0, 0.1);
    }

    #scrollLeft:hover {
        transform: translateY(-50%) translateX(-50%) scale(1.2);
        box-shadow: inset 0 0 0 1000px rgba(0, 0, 0, 0.1);
    }
    .scroll {
        cursor: pointer;
        width: 3rem;
        height: 3rem;
        background-color: rgb(255, 243, 226);
        box-shadow: 0 0 5px 0 rgba(0, 0, 0, 0.1);
        opacity: 0;
        transition: all 0.3s;
    }

    .show-scroll {
        opacity: 1;
    }

    .sub-cat a {
        color: #3f3f3f;
    }

    .sub-cat a:hover {
        color: orange;
    }

    .sub-cat a:not(.sub-cat a:last-child)::after {
        content: " | ";
        color: #3f3f3f;
    }

    .product-image {
        width: 100%;
        height: 100%;
        aspect-ratio: 1/1;
        object-fit: cover;
    }

    .line-clamp-2 {
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }

    .center {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -70%);

    }

  .product-card {
        transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
    }

    .product-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
    }

</style>


<nav class="mt-3 p-4 container">
    <div class="row">
        <div class="col-12 col-lg-8 mb-2">
            <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
                </div>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="https://via.placeholder.com/1920x600" class="d-block w-100" alt="...">
                    </div>
                    <div class="carousel-item">
                        <img src="https://via.placeholder.com/1920x600" class="d-block w-100" alt="...">
                    </div>
                    <div class="carousel-item">
                        <img src="https://via.placeholder.com/1920x600" class="d-block w-100" alt="...">
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <div class="col-12 col-lg-4">
            <div class="row">
                <div class="col-12 col-md-6 col-lg-12 mb-2">
                <img src="https://via.placeholder.com/1920x600" class="img-fluid" alt="Image 1">
                </div>
                <div class="col-12 col-md-6 col-lg-12">
                <img src="https://via.placeholder.com/1920x600" class="img-fluid" alt="Image 2">
                </div>
            </div>
        </div>
    </div>

</nav>

<main class="container">

<%-- danh mục sản phẩm --%>
    <section class="mt-3">
        <h2 class="h4">Danh mục sản phẩm</h2>
        <div class="category-container">
            <div class="over-x-hidden add-border" id="cat">
                <ul id="catList" class="d-flex list-unstyled flex-wrap m-0" style="width: ${((categories.size()/2)+1)*10}rem">
                    <c:forEach var="cat" items="${categories}">
                        <li class="list-cat">
                            <a href="purchase/category?catId=${cat.id}" class="text-decoration-none">
                                <div class="d-flex flex-column align-items-center">
                                    <div class="item-img p-3">
                                        <img src="<c:url value="/resources/images/category/${cat.image}"/>" class="img-fluid rounded-circle" style="aspect-ratio: 1/1; object-fit: cover; width: 100%;" alt="${cat.name}">
                                    </div>
                                    <div class="text-center" style="height: 3rem; width: 90%;">
                                        <strong class="cat-name">${cat.name}</strong>
                                    </div>
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div id="scrollRight" class="d-flex justify-content-center align-items-center rounded-circle scroll">
                <i class="bi bi-chevron-right text-dark center fs-3"></i>
            </div>
            <div id="scrollLeft" class="d-flex justify-content-center align-items-center rounded-circle scroll">
                <i class="bi bi-chevron-left text-dark center fs-3"></i>
            </div>
        </div>
    </section>

    <%-- sản phẩm mới cập nhật --%>
    <section class="mt-3">
        <h2 class="h4">Sản phẩm mới cập nhật</h2>
        <div class="row">
            <c:forEach var="product" items="${newestProducts}">
                <div class="col-6 col-md-3 col-lg-2 mb-3">
                    
                    <a href="<c:url value="/user/purchase/product/${product.id}"/>" class="text-decoration-none">
                        <div class="card h-100 product-card">
                            <!-- Ảnh sản phẩm + Phần trăm giảm giá -->
                            <div class="position-relative">
                                <img src="<c:url value='/resources/images/products/${product.thumbnail}'/>" 
                                    class="card-img-top product-image" 
                                    alt="${product.name}">
                                <c:if test="${product.discount > 0}">
                                    <span class="badge bg-danger text-white position-absolute top-0 start-0 m-2">
                                        Sale up to ${product.discount}%
                                    </span>
                                </c:if>
                            </div>

                            <div class="card-body d-flex flex-column">
                                <!-- Tiêu đề sản phẩm -->
                                <h6 class="card-title text-truncate mb-2" title="${product.name}">
                                    ${product.name}
                                </h6>

                                <!-- Giá sản phẩm -->
                                <p class="text-danger fw-bold mb-2 currency">
                                    ${product.priceFrom}
                                </p>

                                <!-- Thông tin bổ sung: Đã bán -->
                                <div class="mt-auto d-flex justify-content-between align-items-center">
                                    <span class="text-muted small">Đã bán: ${product.buyCount}</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </section>

</main>

<script>
    document.getElementById("cat").addEventListener("mouseenter", function() {
        updateScrollIcon();
    });

    document.getElementById("cat").addEventListener("scroll", function() {
        updateScrollIcon();
    });

    function updateScrollIcon() {
        let cat = document.getElementById("cat");
        let catList = document.getElementById("catList");
        let sL = document.getElementById("scrollLeft");
        let sR = document.getElementById("scrollRight");
        let catListWidth = catList.offsetWidth;
        let catWidth = cat.offsetWidth;
        
        // Kiểm tra phần cuộn bên trái
        if (cat.scrollLeft > 0) {
            sL.classList.add("show-scroll");
        } else {
            sL.classList.remove("show-scroll");
        }

        // Kiểm tra phần cuộn bên phải
        if (cat.scrollLeft + catWidth < catListWidth) {
            sR.classList.add("show-scroll");
        } else {
            sR.classList.remove("show-scroll");
        }
    }


    document.getElementById("scrollRight").addEventListener("click", function() {
        let cat = document.getElementById("cat");
        let catWidth = cat.offsetWidth;
        cat.scrollBy({
            left: catWidth,
            behavior: 'smooth'
        });
    });

    document.getElementById("scrollLeft").addEventListener("click", function() {
        let cat = document.getElementById("cat");
        let catWidth = cat.offsetWidth;
        cat.scrollBy({
            left: -catWidth,
            behavior: 'smooth'
        });
    });


    var formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    });

    var currency = document.querySelectorAll('.currency');
    currency.forEach((element) => {
        element.innerHTML = formatter.format(element.innerHTML);
    });

    
</script>