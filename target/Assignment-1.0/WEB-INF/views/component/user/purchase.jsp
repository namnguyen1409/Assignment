<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="bg-light p-3">
    <h1> Đăng ký dịch vụ mua hàng</h1>
    <h3 class="text-uppercase mt-3">ĐIỀU KHOẢN DỊCH VỤ</h3>
    <h4>1. Giới thiệu</h4>
    <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Illum architecto, commodi iusto ea blanditiis molestias? Laudantium error consectetur esse quibusdam delectus blanditiis corrupti, libero perferendis, ea voluptate eos dolorum suscipit.</p>
    <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Eum, doloribus! Quisquam veniam ullam earum mollitia porro nam quidem dicta dolorum est fugit magnam, suscipit asperiores ad illum a in sint.</p>
    <h4>2. Quyền riêng tư</h4>
    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime id porro quidem animi fugiat, numquam recusandae optio voluptates nam doloremque voluptatum? Repellat voluptates obcaecati ipsam vero laudantium eius omnis ipsa.</p>
    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Culpa veritatis voluptates officia natus nulla perspiciatis, vitae itaque magni delectus necessitatibus explicabo doloribus. Optio tenetur culpa debitis porro. Reiciendis, nam cumque.</p>
    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Aut deleniti pariatur nihil rem, iste fuga quae aliquid molestiae praesentium, quo animi nobis facere labore quaerat! Alias adipisci labore velit tempore.</p>
    <h4>3. Điều khoản sử dụng</h4>
    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit</p>
    <ol>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
    </ol>
    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit</p>

    <ol>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
    </ol>
    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit</p>
    <ol>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
        <li>Lorem ipsum dolor sit amet consectetur adipisicing elit</li>
    </ol>
    <h3 class="text-uppercase">Chính sách bảo mật</h3>
    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Natus cupiditate iusto consequuntur, at laborum nulla maxime quam perspiciatis saepe modi repellendus, eum delectus culpa non aliquid quasi sunt quo. Quaerat.</p>
    <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Molestiae laudantium beatae voluptate nobis dolores officiis magni velit! Incidunt repudiandae laborum inventore assumenda aliquid molestias harum architecto magnam. Hic, facere quasi.</p>
    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Excepturi culpa amet, commodi laboriosam libero ullam quo sit, harum totam quia at, vitae temporibus consequuntur nam tenetur facere doloremque blanditiis quam.</p>
    <p>Inventore nulla sapiente ex adipisci laborum dolore, explicabo pariatur totam officia reprehenderit deserunt distinctio facilis vitae labore impedit autem quae sed molestiae laboriosam voluptates aliquid eaque. Mollitia veritatis officia reiciendis?</p>
    <p>Placeat, dolore distinctio. Voluptatibus veritatis magni deleniti at vitae consequatur praesentium, eum iste dolorem incidunt adipisci laudantium nam possimus est eveniet cupiditate consectetur sint delectus? Quasi necessitatibus maxime suscipit nam!</p>
    <p>Ipsam accusantium amet numquam exercitationem tenetur cum autem, blanditiis temporibus, nam pariatur recusandae molestias! Ut impedit aut quo voluptatem architecto quae quod non, nostrum nam temporibus id, velit consectetur porro?</p>
    <p>Provident, mollitia? Eligendi ipsum provident aliquam sapiente dolorum dolor repellendus vel reprehenderit veniam quos at velit, illum unde similique dolore consequuntur facilis totam corporis voluptas. Beatae magnam earum rerum aut?</p>
    <hr>
    <div class="form-group">
        <form:form modelAttribute="forceAcceptDTO" cssClass="mb-5" action="" method="POST">
            <p class="text-danger">* 
                Bằng việc bạn tick vào ô bên dưới, bạn đã đồng ý với các điều khoản sử dụng dịch vụ mua hàng của chúng tôi
            </p>
            <form:checkbox path="accept" cssClass="form-check-input"/>
            <form:label path="accept" cssClass="form-check" for="accept">Tôi đồng ý với các điều khoản sử dụng dịch vụ mua hàng</form:label>
            <button type="submit" class="btn btn-primary mt-3">Đăng ký</button>
        </form:form>
    </div>


</div>