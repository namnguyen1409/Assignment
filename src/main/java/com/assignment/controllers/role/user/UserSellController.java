package com.assignment.controllers.role.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment.config.PropertiesConfig;
import com.assignment.models.dto.role.user.sell.UserSellHomeDTO;
import com.assignment.models.dto.setting.SimpleUserDTO;
import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.shop.product.Product;
import com.assignment.models.entities.shop.product.ProductStatus;
import com.assignment.models.entities.shop.store.Store;
import com.assignment.models.entities.shop.voucher.Voucher;
import com.assignment.models.entities.shop.voucher.Voucher.VoucherDTO;
import com.assignment.models.repositories.QueryBuilder;
import com.assignment.models.repositories.auth.UserRepo;
import com.assignment.models.repositories.shop.order.OrderRepo;
import com.assignment.models.repositories.shop.order.OrderReturnRepo;
import com.assignment.models.repositories.shop.product.CategoryRepo;
import com.assignment.models.repositories.shop.product.ProductImageRepo;
import com.assignment.models.repositories.shop.product.ProductRepo;
import com.assignment.models.repositories.shop.product.ProductVariantRepo;
import com.assignment.models.repositories.shop.voucher.VoucherRepo;
import com.assignment.security.CustomUserDetails;
import com.assignment.security.XSSProtected;

@Controller
@RequestMapping("/user/sell")
@Transactional
public class UserSellController {

    @Autowired
    private PropertiesConfig prop;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired 
    private OrderReturnRepo orderReturnRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private XSSProtected xssProtected;


    @Autowired
    private VoucherRepo voucherRepo;   
    
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
        value = {"", "/", "home"}
    )
    public String home(
        Model model
    ) {
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        UserSellHomeDTO userSellHomeDTO = new UserSellHomeDTO();
        userSellHomeDTO.setOrderPending(orderRepo.countOrderPending(store.getId()));
        userSellHomeDTO.setOrderPendingDelivery(orderRepo.countOrderPendingDelivery(store.getId()));
        userSellHomeDTO.setOrderDelivering(orderRepo.countOrderDelivering(store.getId()));
        userSellHomeDTO.setOrderCancelled(orderRepo.countOrderCancelled(store.getId()));
        userSellHomeDTO.setProductInactive(productRepo.countProductInactive(store.getId()));
        userSellHomeDTO.setProductOutOfStock(productRepo.countProductOutOfStock(store.getId()));
        userSellHomeDTO.setOrderReturnPending(orderReturnRepo.countOrderReturnPending(store.getId()));
        model.addAttribute("userSellHomeDTO", userSellHomeDTO);
        model.addAttribute("tab", "home");
        addProperties(model);
        return "role/user/sell/home";
    }


    @GetMapping(
        value = {"/product/add"}
    )
    public String addProduct(
        Model model
    ) {
        model.addAttribute("categories", categoryRepo.findAllParenCategory());
        System.out.println("Size of cat" +  categoryRepo.findAllParenCategory().size());
        model.addAttribute("tab", "addProduct");
        addProperties(model);
        return "role/user/sell/home";
    }


    @GetMapping(
        value = {"/product", "/product/list", "/product/list/all"}
    )
    public String listProduct(
        Model model,
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "search", defaultValue = "") String search,
        @RequestParam(name = "category", defaultValue = "0") int category,
        @RequestParam(name = "sort", defaultValue = "0") int sort
    ) {
        model.addAttribute("tab", "listAllProduct");
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        QueryBuilder<Product> queryBuilder = new QueryBuilder<>(Product.class);
        queryBuilder.from("p")
                    .where("p.store", store);

        if (!search.isEmpty()) {
            queryBuilder.andLike("p.name", "%" + search + "%");
        }
        if (category > 0) {
            queryBuilder.and("p.category.id", category);
        }
        switch (sort) {
            case 1 -> queryBuilder.orderBy("p.priceFrom", true);
            case 2 -> queryBuilder.orderBy("p.priceTo", false);
            case 3 -> queryBuilder.orderBy("p.createdAt", true);
            case 4 -> queryBuilder.orderBy("p.createdAt", false);
            default -> {
            }
        }
        model.addAttribute("products", productRepo.getResultList(page, size, queryBuilder).stream().map(e -> e.toSellDTO()).toList());
        model.addAttribute("productStatus", ProductStatus.values());
        queryBuilder = queryBuilder.reset()
        .count("p")
        .where("p.store", store);
        if (!search.isEmpty()) {
            queryBuilder.andLike("p.name", "%" + search + "%");
        }
        if (category > 0) {
            queryBuilder.and("p.category.id", category);
        }
        var totalItemCount = productRepo.count(queryBuilder);
        model.addAttribute("totalItem", totalItemCount);
        model.addAttribute("totalPage", (int) Math.ceil((double) totalItemCount / size));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", xssProtected.encodeAllHTMLElement(search));
        model.addAttribute("sort", sort);
        addProperties(model);
        return "role/user/sell/home";
    }


    @GetMapping(
        value = {"/product/active", "/product/active/"}
    )
    public String activeProduct(
        Model model,
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "search", defaultValue = "") String search,
        @RequestParam(name = "category", defaultValue = "0") int category,
        @RequestParam(name = "sort", defaultValue = "0") int sort
    ) {
        model.addAttribute("tab", "listActiveProduct");
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        QueryBuilder<Product> queryBuilder = new QueryBuilder<>(Product.class);
        queryBuilder.from("p")
                    .where("p.store", store)
                    .where("p.status", ProductStatus.ACTIVE);

        if (!search.isEmpty()) {
            queryBuilder.andLike("p.name", "%" + search + "%");
        }
        if (category > 0) {
            queryBuilder.and("p.category.id", category);
        }
        switch (sort) {
            case 1 -> queryBuilder.orderBy("p.priceFrom", true);
            case 2 -> queryBuilder.orderBy("p.priceTo", false);
            case 3 -> queryBuilder.orderBy("p.createdAt", true);
            case 4 -> queryBuilder.orderBy("p.createdAt", false);
            default -> {
            }
        }
        model.addAttribute("products", productRepo.getResultList(page, size, queryBuilder).stream().map(e -> e.toSellDTO()).toList());
        model.addAttribute("productStatus", ProductStatus.values());
        queryBuilder = queryBuilder.reset()
        .count("p")
        .where("p.store", store)
        .where("p.status", ProductStatus.ACTIVE);
        if (!search.isEmpty()) {
            queryBuilder.andLike("p.name", "%" + search + "%");
        }
        if (category > 0) {
            queryBuilder.and("p.category.id", category);
        }
        var totalItemCount = productRepo.count(queryBuilder);
        model.addAttribute("totalItem", totalItemCount);
        model.addAttribute("totalPage", (int) Math.ceil((double) totalItemCount / size));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", xssProtected.encodeAllHTMLElement(search));
        model.addAttribute("sort", sort);
        addProperties(model);
        return "role/user/sell/home";
    }

    @GetMapping(
        value = {"/product/hide", "/product/hide/"}
    )
    public String hideProduct(
        Model model,
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "search", defaultValue = "") String search,
        @RequestParam(name = "category", defaultValue = "0") int category,
        @RequestParam(name = "sort", defaultValue = "0") int sort
    ) {
        model.addAttribute("tab", "listHideProduct");
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        QueryBuilder<Product> queryBuilder = new QueryBuilder<>(Product.class);
        queryBuilder.from("p")
                    .where("p.store", store)
                    .where("p.status", ProductStatus.HIDDEN);

        if (!search.isEmpty()) {
            queryBuilder.andLike("p.name", "%" + search + "%");
        }
        if (category > 0) {
            queryBuilder.and("p.category.id", category);
        }
        switch (sort) {
            case 1 -> queryBuilder.orderBy("p.priceFrom", true);
            case 2 -> queryBuilder.orderBy("p.priceTo", false);
            case 3 -> queryBuilder.orderBy("p.createdAt", true);
            case 4 -> queryBuilder.orderBy("p.createdAt", false);
            default -> {
            }
        }
        model.addAttribute("products", productRepo.getResultList(page, size, queryBuilder).stream().map(e -> e.toSellDTO()).toList());
        model.addAttribute("productStatus", ProductStatus.values());
        queryBuilder = queryBuilder.reset()
        .count("p")
        .where("p.store", store)
        .where("p.status", ProductStatus.HIDDEN);
        if (!search.isEmpty()) {
            queryBuilder.andLike("p.name", "%" + search + "%");
        }
        if (category > 0) {
            queryBuilder.and("p.category.id", category);
        }
        var totalItemCount = productRepo.count(queryBuilder);
        model.addAttribute("totalItem", totalItemCount);
        model.addAttribute("totalPage", (int) Math.ceil((double) totalItemCount / size));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", xssProtected.encodeAllHTMLElement(search));
        model.addAttribute("sort", sort);
        addProperties(model);
        return "role/user/sell/home";
    }

    @GetMapping(
        value = {"/product/inactive", "/product/inactive/"}
    )
    public String inActiveProduct(
        Model model,
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "search", defaultValue = "") String search,
        @RequestParam(name = "category", defaultValue = "0") int category,
        @RequestParam(name = "sort", defaultValue = "0") int sort
    ) {
        model.addAttribute("tab", "listInActiveProduct");
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        QueryBuilder<Product> queryBuilder = new QueryBuilder<>(Product.class);
        queryBuilder.from("p")
                    .where("p.store", store)
                    .where("p.status", ProductStatus.INACTIVE);

        if (!search.isEmpty()) {
            queryBuilder.andLike("p.name", "%" + search + "%");
        }
        if (category > 0) {
            queryBuilder.and("p.category.id", category);
        }
        switch (sort) {
            case 1 -> queryBuilder.orderBy("p.priceFrom", true);
            case 2 -> queryBuilder.orderBy("p.priceTo", false);
            case 3 -> queryBuilder.orderBy("p.createdAt", true);
            case 4 -> queryBuilder.orderBy("p.createdAt", false);
            default -> {
            }
        }
        model.addAttribute("products", productRepo.getResultList(page, size, queryBuilder).stream().map(e -> e.toSellDTO()).toList());
        model.addAttribute("productStatus", ProductStatus.values());
        queryBuilder = queryBuilder.reset()
        .count("p")
        .where("p.store", store)
        .where("p.status", ProductStatus.INACTIVE);
        if (!search.isEmpty()) {
            queryBuilder.andLike("p.name", "%" + search + "%");
        }
        if (category > 0) {
            queryBuilder.and("p.category.id", category);
        }
        var totalItemCount = productRepo.count(queryBuilder);
        model.addAttribute("totalItem", totalItemCount);
        model.addAttribute("totalPage", (int) Math.ceil((double) totalItemCount / size));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", xssProtected.encodeAllHTMLElement(search));
        model.addAttribute("sort", sort);
        addProperties(model);
        return "role/user/sell/home";
    }


    @GetMapping(
        value = {"/product/deleted", "/product/deleted/"}
    )
    public String deletedProduct(
        Model model,
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "search", defaultValue = "") String search,
        @RequestParam(name = "category", defaultValue = "0") int category,
        @RequestParam(name = "sort", defaultValue = "0") int sort
    ) {
        model.addAttribute("tab", "listDeletedProduct");
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        QueryBuilder<Product> queryBuilder = new QueryBuilder<>(Product.class);
        queryBuilder.from("p")
                    .where("p.store", store)
                    .where("p.status", ProductStatus.DELETED);

        if (!search.isEmpty()) {
            queryBuilder.andLike("p.name", "%" + search + "%");
        }
        if (category > 0) {
            queryBuilder.and("p.category.id", category);
        }
        switch (sort) {
            case 1 -> queryBuilder.orderBy("p.priceFrom", true);
            case 2 -> queryBuilder.orderBy("p.priceTo", false);
            case 3 -> queryBuilder.orderBy("p.createdAt", true);
            case 4 -> queryBuilder.orderBy("p.createdAt", false);
            default -> {
            }
        }
        model.addAttribute("products", productRepo.getResultList(page, size, queryBuilder).stream().map(e -> e.toSellDTO()).toList());
        model.addAttribute("productStatus", ProductStatus.values());
        queryBuilder = queryBuilder.reset()
        .count("p")
        .where("p.store", store)
        .where("p.status", ProductStatus.DELETED);
        if (!search.isEmpty()) {
            queryBuilder.andLike("p.name", "%" + search + "%");
        }
        if (category > 0) {
            queryBuilder.and("p.category.id", category);
        }
        var totalItemCount = productRepo.count(queryBuilder);
        model.addAttribute("totalItem", totalItemCount);
        model.addAttribute("totalPage", (int) Math.ceil((double) totalItemCount / size));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", xssProtected.encodeAllHTMLElement(search));
        model.addAttribute("sort", sort);
        addProperties(model);
        return "role/user/sell/home";
    }


    @GetMapping(
        value = {"/product/edit/{id}"}
    )
    public String editProduct(
        Model model,
        @PathVariable("id") Long id
    ) {
        model.addAttribute("tab", "editProduct");

        Product product = productRepo.findByIdWithImages(id);
        if (product == null) {
            return "redirect:/user/sell/product";
        }
        model.addAttribute("categories", categoryRepo.findAllParenCategory());
        product.setVariants(productVariantRepo.findByProductId(id));
        product.setCategory(categoryRepo.findById(product.getCategory().getId()));
        model.addAttribute("product", product);
        addProperties(model);
        return "role/user/sell/home";
    }

    @GetMapping(
        value = {"/product/hide/{productId}"}
    )
    public String hideProduct(
        @PathVariable("productId") Long productId
    ) throws UnsupportedEncodingException {
        Product product = productRepo.findById(productId);
        // kiểm tra sản phẩm thuộc cửa hàng của user: tránh lỗi bảo mật
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        if (product == null ||  !product.getStore().getId().equals(store.getId())) {
            return redirectToError("Sản phẩm không thuộc về cửa hàng của bạn");
        }
        // chỉ ẩn sản phẩm nếu sản phẩm đang hoạt động
        if (product.getStatus() == ProductStatus.ACTIVE) {
            product.setStatus(ProductStatus.HIDDEN);
            productRepo.update(product);
            return "redirect:/user/sell/product/active";
        }
        else {
            return redirectToError("Sản phẩm không ở trạng thái hoạt động");
        }
    }

    @GetMapping(
        value = {"/product/show/{productId}"}
    )
    public String showProduct(
        @PathVariable("productId") Long productId
    ) throws UnsupportedEncodingException {
        Product product = productRepo.findById(productId);
        // kiểm tra sản phẩm thuộc cửa hàng của user: tránh lỗi bảo mật
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        if (product == null || !product.getStore().getId().equals(store.getId())) {
            return redirectToError("Sản phẩm không thuộc về cửa hàng của bạn");
        }
        // chỉ hiện sản phẩm nếu sản phẩm đang ẩn
        if (product.getStatus() == ProductStatus.HIDDEN) {
            product.setStatus(ProductStatus.ACTIVE);
            productRepo.update(product);
            return "redirect:/user/sell/product/hide";
        }
        else {
            return redirectToError("Sản phẩm không ở trạng thái ẩn");
        }
    }

    @GetMapping(
        value = {"/product/delete/{productId}"}
    )
    public String deleteProduct(
        @PathVariable("productId") Long productId
    ) throws UnsupportedEncodingException {
        Product product = productRepo.findById(productId);
        // kiểm tra sản phẩm thuộc cửa hàng của user: tránh lỗi bảo mật
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        if (product == null || !product.getStore().getId().equals(store.getId())) {
            return redirectToError("Sản phẩm không thuộc về cửa hàng của bạn");
        }
        // chỉ xóa sản phẩm nếu sản phẩm đang hoạt động hoặc ẩn
        if (product.getStatus() == ProductStatus.ACTIVE || product.getStatus() == ProductStatus.HIDDEN) {
            product.setStatus(ProductStatus.DELETED);
            productRepo.update(product);
            return "redirect:/user/sell/product/active";
        }
        else {
            return redirectToError("Sản phẩm không ở trạng thái hoạt động hoặc ẩn");
        }
    }


    // danh sách mã giảm giá của cửa hàng
    @GetMapping(
        value = {"/discount", "/discount/list"}
    )
    public String listDiscount(
        Model model
    ) {
        model.addAttribute("tab", "listDiscount");
        addProperties(model);
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        model.addAttribute("vouchers", voucherRepo.findAllVoucherByStoreId(store.getId()));
        return "role/user/sell/home";
    }

    // thêm mã giảm giá
    @GetMapping(
        value = {"/discount/add"}
    )
    public String addDiscount(
        Model model
    ) {
        model.addAttribute("tab", "addDiscount");
        Voucher.VoucherDTO voucherDTO = new Voucher.VoucherDTO();
        model.addAttribute("voucher", voucherDTO);
        addProperties(model);
        return "role/user/sell/home";
    }


    // thêm mã giảm giá
    @PostMapping(
        value = {"/discount/add"}
    )
    public String addDiscount(
        Model model,
        @ModelAttribute("voucher") @Validated Voucher.VoucherDTO voucherDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tab", "addDiscount");
            model.addAttribute("voucher", voucherDTO);
            addProperties(model);
            return "role/user/sell/home";
        }
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        Voucher voucher = new Voucher();
        voucher.setStore(store);
        voucher.setCode(voucherDTO.getCode());
        voucher.setType(voucherDTO.getType());
        voucher.setDiscount(voucherDTO.getDiscount());
        voucher.setMaxDiscount(voucherDTO.getMaxDiscount());
        voucher.setMinOrderValue(voucherDTO.getMinOrderValue());
        voucher.setStartDate(voucherDTO.getStartDate());
        voucher.setEndDate(voucherDTO.getEndDate());
        voucher.setQuantity(voucherDTO.getQuantity());
        voucher.setStatus(true);
        // kiểm tra người dùng có nhập mô tả hay không
        if (voucherDTO.getDescription() != null) {
            voucher.setDescription(voucherDTO.getDescription());
        } else {
            voucher.defaultDescription();
        }
        voucherRepo.save(voucher);
        return "redirect:/user/sell/discount";
    }

















    // Helper method to redirect to the error page with an encoded reason
    private String redirectToError(String message) throws UnsupportedEncodingException {
        return "redirect:/error/403?reason=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
    }
}
