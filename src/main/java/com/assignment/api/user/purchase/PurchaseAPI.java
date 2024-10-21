package com.assignment.api.user.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.shop.cart.Cart;
import com.assignment.models.entities.shop.cart.CartItem;
import com.assignment.models.repositories.shop.cart.CartItemRepo;
import com.assignment.models.repositories.shop.cart.CartRepo;
import com.assignment.models.repositories.shop.product.ProductVariantRepo;
import com.assignment.security.CustomUserDetails;


@RestController
@Transactional
@RequestMapping("/api/user/purchase")
public class PurchaseAPI {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductVariantRepo productVariantRepo;

    @Autowired
    private CartItemRepo cartItemRepo;


    private User getUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }


    // thêm vào giỏ hàng
    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(
        @RequestParam("variantId") Long variantId,
        @RequestParam("quantity") Integer quantity
    ) {
        // kiểm tra người dùng có giỏ hàng chưa

        if (cartRepo.findByUserId(getUser().getId()) == null) {
            // nếu chưa có thì tạo mới
            Cart cart = new Cart();
            cart.setUser(getUser());
            cartRepo.save(cart);
        }

        // lấy giỏ hàng
        Cart cart = cartRepo.findByUserId(getUser().getId());
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setQuantity(quantity);
        cartItem.setProductVariant(productVariantRepo.findById(variantId));
        // kiểm tra sản phẩm đã có trong giỏ hàng chưa
        if (cartItemRepo.findByCartIdAndVariantId(cart.getId(), variantId) != null) {
            // nếu có rồi thì cộng thêm số lượng
            CartItem existCartItem = cartItemRepo.findByCartIdAndVariantId(cart.getId(), variantId);
            existCartItem.setQuantity(existCartItem.getQuantity() + quantity);
            cartItemRepo.save(existCartItem);
        } else {
            // nếu chưa có thì thêm mới
            cartItemRepo.save(cartItem);
        }
        return ResponseEntity.ok("Đã thêm vào giỏ hàng");
    }
    
}
