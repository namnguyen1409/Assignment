<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<style>
    body{
        margin-top:20px;
        background-color: #f1f3f7;
    }

    .avatar-lg {
        height: 5rem;
        width: 5rem;
    }

    .font-size-18 {
        font-size: 18px!important;
    }

    .text-truncate {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    a {
        text-decoration: none!important;
    }

    .w-xl {
        min-width: 160px;
    }

    .card {
        margin-bottom: 24px;
        -webkit-box-shadow: 0 2px 3px #e4e8f0;
        box-shadow: 0 2px 3px #e4e8f0;
    }

    .card {
        position: relative;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        -ms-flex-direction: column;
        flex-direction: column;
        min-width: 0;
        word-wrap: break-word;
        background-color: #fff;
        background-clip: border-box;
        border: 1px solid #eff0f2;
        border-radius: 1rem;
    }

</style>


<section class="container">
    <div class="row">
        <div class="col-xl-8">
            <c:forEach var="entry" items="${cartData}">
                <div class="store-section bg-light p-3 mb-4">
                    <h5>${entry.key.name}</h5>
                    <hr>
                    <c:forEach var="cartItem" items="${entry.value}">

                        <div class="card border shadow-none">
                            <div class="card-body">
                                <div class="d-flex align-items-start border-bottom pb-3">
                                    <div class="me-4">
                                        <c:if test="${cartItem.productVariant.style == null}">
                                            <img src="<c:url value="/resources/images/products/${cartItem.productVariant.product.thumbnail}" />" alt="product-image" class="avatar-lg rounded">
                                        </c:if>
                                        <c:if test="${cartItem.productVariant.style != null}">
                                            <img src="<c:url value="/resources/images/products/${cartItem.productVariant.style.image}" />" alt="product-image" class="avatar-lg rounded">
                                        </c:if>
                                    </div>
                                    <div class="flex-grow-1 align-self-center overflow-hidden">
                                        <div>
                                            <h5 class="text-truncate font-size-18"><a href="#" class="text-dark">${cartItem.productVariant.product.name}</a></h5>
                                            <c:if test="${cartItem.productVariant.style != null}">
                                                <p class="mb-0 mt-1">Kiểu dáng: <span class="fw-medium">${cartItem.productVariant.style.name}</span></p>
                                            </c:if>
                                            <c:if test="${cartItem.productVariant.size != null}">
                                                <p class="mb-0 mt-1">Kích thước: <span class="fw-medium">${cartItem.productVariant.size.name}</span></p>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="flex-shrink-0 ms-2">
                                        <ul class="d-flex align-items-center mb-0 font-size-16">
                                            <li class="list-inline-item">
                                                <button type="button" class="btn btn-link text-danger removed">
                                                    <i class="bi bi-trash"></i>
                                                </button>
                                            </li>
                                            <%-- nút chọn --%>
                                            <li class="list-inline-item">
                                                <div class="form-check form-switch">
                                                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault">
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>

                                <div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="mt-3">
                                                <p class="text-muted mb-2">Giá</p>
                                                <%-- <h5 class="mb-0 mt-2"><span class="text-muted me-2"><del class="font-size-16 fw-normal">$500</del></span>$450</h5> --%>
                                                <h5 class="mb-0 mt-2"> <span class="text-muted me-2"><del class="font-size-16 fw-normal currency">${cartItem.productVariant.price}</del></span>
                                                    <span class="currency">${cartItem.productVariant.salePrice}</span>
                                                </h5>

                                            </div>
                                        </div>
                                        <div class="col-md-5">
                                            <div class="mt-3">
                                                <p class="text-muted mb-2">Số lượng</p>
                                                <div class="d-inline-flex">
                                                    <input type="number" class="form-control" value="${cartItem.quantity}" min="1">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="mt-3">
                                                <p class="text-muted mb-2">Tổng</p>
                                                <h5 class="mb-0 mt-2"><span class="currency">${cartItem.productVariant.salePrice * cartItem.quantity}</span></h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
        

        <div class="col-xl-4">
            <div class="mt-5 mt-lg-0">
                <div class="card border shadow-none">
                    <div class="card-header bg-transparent border-bottom py-3 px-4">
                        <h5 class="font-size-16 mb-0">Order Summary <span class="float-end">#MN0124</span></h5>
                    </div>
                    <div class="card-body p-4 pt-2">

                        <div class="table-responsive">
                            <table class="table mb-0">
                                <tbody>
                                    <tr>
                                        <td>Sub Total :</td>
                                        <td class="text-end">$ 780</td>
                                    </tr>
                                    <tr>
                                        <td>Discount : </td>
                                        <td class="text-end">- $ 78</td>
                                    </tr>
                                    <tr>
                                        <td>Shipping Charge :</td>
                                        <td class="text-end">$ 25</td>
                                    </tr>
                                    <tr>
                                        <td>Estimated Tax : </td>
                                        <td class="text-end">$ 18.20</td>
                                    </tr>
                                    <tr class="bg-light">
                                        <th>Total :</th>
                                        <td class="text-end">
                                            <span class="fw-bold">
                                                $ 745.2
                                            </span>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <!-- end table-responsive -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- end row -->





    </div>
</section>


<script>
    // format currency
    const currency = document.querySelectorAll('.currency');
    currency.forEach((element) => {
        element.textContent = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(element.textContent);
    });





</script>