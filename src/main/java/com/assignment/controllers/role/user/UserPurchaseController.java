package com.assignment.controllers.role.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assignment.config.PropertiesConfig;
import com.assignment.models.dto.setting.SimpleUserDTO;
import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.shop.cart.Cart;
import com.assignment.models.entities.shop.cart.CartItem;
import com.assignment.models.entities.shop.product.Product;
import com.assignment.models.entities.shop.product.ProductStatus;
import com.assignment.models.entities.shop.product.ProductVariant;
import com.assignment.models.entities.shop.product.ProductView;
import com.assignment.models.entities.shop.store.Store;
import com.assignment.models.repositories.auth.UserLocationRepo;
import com.assignment.models.repositories.shop.cart.CartItemRepo;
import com.assignment.models.repositories.shop.cart.CartRepo;
import com.assignment.models.repositories.shop.order.OrderRepo;
import com.assignment.models.repositories.shop.product.CategoryRepo;
import com.assignment.models.repositories.shop.product.ProductImageRepo;
import com.assignment.models.repositories.shop.product.ProductRatingRepo;
import com.assignment.models.repositories.shop.product.ProductRepo;
import com.assignment.models.repositories.shop.product.ProductVariantRepo;
import com.assignment.models.repositories.shop.product.ProductViewRepo;
import com.assignment.security.CustomUserDetails;
import com.assignment.security.XSSProtected;


@Controller
@Transactional
@RequestMapping("/user/purchase")
public class UserPurchaseController {

    @Autowired
    private PropertiesConfig prop;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserLocationRepo userLocationRepo;
    
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductViewRepo productViewRepo;

    @Autowired
    private ProductRatingRepo productRatingRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CartItemRepo cartItemRepo;


    @Autowired
    private ProductImageRepo productImageRepo;    
    
    @Autowired
    private ProductVariantRepo productVariantRepo;
    

    private User getUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }

    private void addProperties(Model model) {
        User user = getUser();
        SimpleUserDTO simpleUserDTO = new SimpleUserDTO(user.getUsername(), user.getAvatar());
        model.addAttribute("simpUser", simpleUserDTO);
        model.addAttribute("compName", prop.COMPANY_NAME);
        model.addAttribute("compLogo", prop.APP_LOGO);
    }


    // trang chủ
    @GetMapping(
        value = {"/", "home", ""}
    )
    public String home(
        Model model
    ) {
        model.addAttribute("categories", categoryRepo.findAllParenCategory());
        model.addAttribute("tab", "home");
        // các sản phẩm mới nhất
        model.addAttribute("newestProducts", productRepo.listNewestProduct(1, 24));
        addProperties(model);
        return "role/user/purchase/home";
    }

    // trang chi tiết sản phẩm
    @GetMapping("/product/{productId}")
    public String productDetail(
        Model model,
        @PathVariable("productId") Long productId
    ) throws UnsupportedEncodingException {
        Product product = productRepo.findByIdWithImagesAndStore(productId);
        if (product == null) {
            return "redirect:/user/purchase";
        }
        if (product.getStatus() != ProductStatus.ACTIVE) {
            return redirectToError("Bạn không thể xem sản phẩm này");
        }
        product.setVariants(productVariantRepo.findByProductId(productId));
        product.setCategory(categoryRepo.findById(product.getCategory().getId()));
        model.addAttribute("product", product);
        // đánh giá trung bình của sản phẩm
        model.addAttribute("avgRating", productRatingRepo.getAverageRating(productId));
        model.addAttribute("tab", "productDetail");
        addProperties(model);
        
        if (!productViewRepo.isViewed(productId, getUser().getId())) {
            // tăng số lượt xem
            productRepo.increaseViewCount(productId);
            ProductView productView = new ProductView();
            var pd = new Product();
            pd.setId(productId);
            productView.setProduct(pd);
            var user = new User();
            user.setId(getUser().getId());
            productView.setUser(user);
            productViewRepo.save(productView);
        }
        // thông tin cửa hàng
        model.addAttribute("store", product.getStore());

        model.addAttribute("locations", userLocationRepo.findByUserIdWithFetch(getUser().getId()));
        return "role/user/purchase/home";
    }

    
    // Helper method to redirect to the error page with an encoded reason
    private String redirectToError(String message) throws UnsupportedEncodingException {
        return "redirect:/error/403?reason=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
    }


    @GetMapping("/cart")
    public String cart(
        Model model
    ) {
        model.addAttribute("tab", "cart");
        addProperties(model);
        // kiểm tra người dùng có giỏ hàng chưa

        if (cartRepo.findByUserId(getUser().getId()) == null) {
            // nếu chưa có thì tạo mới
            Cart cart = new Cart();
            cart.setUser(getUser());
            cartRepo.save(cart);
        }

        // lấy giỏ hàng
        Cart cart = cartRepo.findByUserId(getUser().getId());
        // lấy danh sách sản phẩm trong giỏ hàng
        /*
         * cấu trúc dữ liệu:
         *  Map<Store, List<Variant>>
         */
        model.addAttribute("tab", "cart");
        List<CartItem> cartItems = cartItemRepo.findAllByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            model.addAttribute("cartData", Collections.emptyMap());
            return "role/user/purchase/cart";
        }
        
        Map<Store, List<CartItem>> cartData = cartItems.stream()
        .filter(cartItem -> cartItem.getProductVariant() != null && 
                            cartItem.getProductVariant().getProduct() != null &&
                            cartItem.getProductVariant().getProduct().getStore() != null)
        .collect(Collectors.groupingBy(
            cartItem -> cartItem.getProductVariant().getProduct().getStore(),
            Collectors.toList()
        ));
        model.addAttribute("cartData", cartData);
        return "role/user/purchase/home";
    }
}
