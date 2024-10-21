package com.assignment.api.user.sell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.auth.UserLocation;
import com.assignment.models.entities.shop.product.Category;
import com.assignment.models.entities.shop.product.Product;
import com.assignment.models.entities.shop.product.ProductImage;
import com.assignment.models.entities.shop.product.ProductStatus;
import com.assignment.models.entities.shop.product.ProductVariant;
import com.assignment.models.entities.shop.product.Size;
import com.assignment.models.entities.shop.product.Style;
import com.assignment.models.entities.shop.store.Store;
import com.assignment.models.repositories.auth.UserLocationRepo;
import com.assignment.models.repositories.auth.UserRepo;
import com.assignment.models.repositories.shop.product.CategoryRepo;
import com.assignment.models.repositories.shop.product.ProductRepo;
import com.assignment.models.repositories.shop.product.ProductVariantRepo;
import com.assignment.models.repositories.shop.product.SizeRepo;
import com.assignment.models.repositories.shop.product.StyleRepo;
import com.assignment.security.CustomUserDetails;
import com.assignment.security.XSSProtected;

@RestController
@Transactional
@RequestMapping("/api/user/sell/product")
public class ProductAPI {

    @Autowired
    private XSSProtected xssProtected;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductVariantRepo productVariantRepo;

    @Autowired
    private UserLocationRepo userLocationRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private SizeRepo sizeRepo;

    @Autowired
    private StyleRepo styleRepo;

    private User getUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestParam(value = "thumbnail", required = true) String thumbnail,
            @RequestParam(value = "images[]", required = true) String[] images,
            @RequestParam(value = "video", required = true) String video,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "category", required = true) Long categoryId,
            @RequestParam(value = "description", required = true) String description,
            @RequestParam(value = "brand", required = true) String brand,
            @RequestParam(value = "brandOrigin", required = true) String brandOrigin,
            @RequestParam(value = "madeIn", required = true) String madeIn,
            @RequestParam(value = "guarantee", required = true) String guarantee,
            @RequestParam(value = "style[]", required = false) String[] style,
            @RequestParam(value = "styleImages[]", required = false) String[] styleImages,
            @RequestParam(value = "size[]", required = false) String[] size,
            @RequestParam(value = "priceOrg[]", required = true) Double[] priceOrg,
            @RequestParam(value = "price[]", required = true) Double[] price,
            @RequestParam(value = "quantity[]", required = true) Integer[] quantity,
            @RequestParam(value = "productWeight", required = true) Integer productWeight,
            @RequestParam(value = "productLength", required = true) Integer productLength,
            @RequestParam(value = "productWidth", required = true) Integer productWidth,
            @RequestParam(value = "productHeight", required = true) Integer productHeight,
            @RequestParam(value = "action", required = true) String action
        ) {
        Map<String, String> response = new HashMap<>();
        thumbnail = xssProtected.encodeAllHTMLElement(thumbnail);
        if (thumbnail.isBlank()) {
            response.put("message", "Vui lòng chọn ảnh thu nhỏ cho sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        if (images.length == 0) {
            response.put("message", "Vui lòng chọn ít nhất 1 ảnh cho sản phẩm");
            return ResponseEntity.badRequest().body(response);
        }
        for (int i = 0; i < images.length; i++) {
            images[i] = xssProtected.encodeAllHTMLElement(images[i]);
            if (images[i].isBlank()) {
                response.put("message", "Vui lòng chọn ảnh cho sản phẩm hợp lệ");
                return ResponseEntity.badRequest().body(response);
            }
        }
        video = xssProtected.encodeAllHTMLElement(video);
        if (video.isBlank()) {
            response.put("message", "Vui lòng chọn video cho sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        name = xssProtected.encodeAllHTMLElement(name);
        if (name.isBlank()) {
            response.put("message", "Vui lòng nhập tên sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        Category category = categoryRepo.findById(categoryId);
        if (category == null) {
            response.put("message", "Vui lòng chọn danh mục sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        description = xssProtected.sanitize(description);
        if (description.length() < 100 || description.length() > 50000) {
            response.put("message", "Mô tả sản phẩm phải từ 100 đến 5000 ký tự");
            return ResponseEntity.badRequest().body(response);
        }
        brand = xssProtected.encodeAllHTMLElement(brand);
        if (brand.isBlank()) {
            response.put("message", "Vui lòng nhập thương hiệu sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        brandOrigin = xssProtected.encodeAllHTMLElement(brandOrigin);
        if (brandOrigin.isBlank()) {
            response.put("message", "Vui lòng nhập xuất xứ thương hiệu hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        madeIn = xssProtected.encodeAllHTMLElement(madeIn);
        if (madeIn.isBlank()) {
            response.put("message", "Vui lòng nhập xuất xứ sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        guarantee = xssProtected.encodeAllHTMLElement(guarantee);
        if (guarantee.isBlank()) {
            response.put("message", "Vui lòng nhập thông tin bảo hành hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        if (style != null && style.length > 0) {
            for (int i = 0; i < style.length; i++) {
                style[i] = xssProtected.encodeAllHTMLElement(style[i]);
                if (style[i].isBlank()) {
                    response.put("message", "Vui lòng nhập thông tin kiểu dáng hợp lệ");
                    return ResponseEntity.badRequest().body(response);
                }
            }
        }

        if (styleImages != null && styleImages.length > 0) {
            for (int i = 0; i < styleImages.length; i++) {
                styleImages[i] = xssProtected.encodeAllHTMLElement(styleImages[i]);
                if (styleImages[i].isBlank()) {
                    response.put("message", "Vui lòng chọn ảnh kiểu dáng hợp lệ");
                    return ResponseEntity.badRequest().body(response);
                }
            }
        }
        if (style != null && styleImages != null) {
            if (styleImages.length != style.length) {
                response.put("message", "Số ảnh kiểu dáng không khớp với số kiểu dáng");
                return ResponseEntity.badRequest().body(response);
            }
        }

        if (size != null && size.length > 0) {
            for (int i = 0; i < size.length; i++) {
                size[i] = xssProtected.encodeAllHTMLElement(size[i]);
                if (size[i].isBlank()) {
                    response.put("message", "Vui lòng nhập thông tin kích thước hợp lệ");
                    return ResponseEntity.badRequest().body(response);
                }
            }
        }
        if (priceOrg.length == 0) {
            response.put("message", "Vui lòng nhập giá gốc sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        if (price.length == 0) {
            response.put("message", "Vui lòng nhập giá sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        if (quantity.length == 0) {
            response.put("message", "Vui lòng nhập số lượng sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        if (productWeight <= 0) {
            response.put("message", "Vui lòng nhập trọng lượng sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        if (productLength <= 0) {
            response.put("message", "Vui lòng nhập chiều dài sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        if (productWidth <= 0) {
            response.put("message", "Vui lòng nhập chiều rộng sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        if (productHeight <= 0) {
            response.put("message", "Vui lòng nhập chiều cao sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }

        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();

        Product product = new Product();
        product.setThumbnail(thumbnail);
        for (String image : images) {
            ProductImage productImage = new ProductImage();
            productImage.setImage(image);
            product.addImage(productImage);
        }
        product.setVideo(video);
        product.setName(name);
        product.setCategory(category);
        product.setDescription(description);
        product.setBrand(brand);
        product.setBrandOrigin(brandOrigin);
        product.setMadeIn(madeIn);
        product.setGuarantee(guarantee);

        // nếu không có biến thể (không có kiểu và kích thước)
        if ((style == null || style.length == 0) && (size == null || size.length == 0)) {
            ProductVariant productVariant = new ProductVariant();
            productVariant.setSize(null);
            productVariant.setStyle(null);
            productVariant.setPrice(priceOrg[0]);
            productVariant.setSalePrice(price[0]);
            productVariant.setQuantity(quantity[0]);
            product.addVariant(productVariant);
        }
        // nếu chỉ có kiểu mà không có kích thước
        if ((style != null && style.length > 0) && (size == null || size.length == 0)) {
            for (int i = 0; i < style.length; i++) {
                ProductVariant productVariant = new ProductVariant();
                productVariant.setSize(null);
                Style producStyle = new Style();
                producStyle.setName(style[i]);
                if (styleImages != null) {
                    producStyle.setImage(styleImages[i]);
                }
                styleRepo.save(producStyle);
                productVariant.setStyle(producStyle);
                productVariant.setPrice(priceOrg[i]);
                productVariant.setSalePrice(price[i]);
                productVariant.setQuantity(quantity[i]);
                product.addVariant(productVariant);
            }
        }
        // nếu chỉ có kích thước mà không có kiểu
        if ((style == null || style.length == 0) && (size != null && size.length > 0)) {
            for (int i = 0; i < size.length; i++) {
                ProductVariant productVariant = new ProductVariant();
                productVariant.setStyle(null);
                Size productSize = new Size();
                productSize.setName(size[i]);
                sizeRepo.save(productSize);
                productVariant.setSize(productSize);
                productVariant.setPrice(priceOrg[i]);
                productVariant.setSalePrice(price[i]);
                productVariant.setQuantity(quantity[i]);
                product.addVariant(productVariant);
            }
        }
        // nếu có cả kiểu và kích thước
        if ((style != null && style.length > 0) && (size != null && size.length > 0)) {
            List<Style> styles = new ArrayList<>();
            for (int i = 0; i < style.length; i++) {
                Style producStyle = new Style();
                producStyle.setName(style[i]);
                if (styleImages != null) {
                    producStyle.setImage(styleImages[i]);
                }
                styleRepo.save(producStyle);
                styles.add(producStyle);
            }
            List<Size> sizes = new ArrayList<>();
            for (String s : size) {
                Size productSize = new Size();
                productSize.setName(s);
                sizeRepo.save(productSize);
                sizes.add(productSize);
            }
            int count = 0;
            for (Style s : styles) {
                for (Size si : sizes) {
                    ProductVariant productVariant = new ProductVariant();
                    productVariant.setStyle(s);
                    productVariant.setSize(si);
                    productVariant.setPrice(priceOrg[count]);
                    productVariant.setSalePrice(price[count]);
                    productVariant.setQuantity(quantity[count]);
                    product.addVariant(productVariant);
                    count++;
                }
            }

        }
        
        product.updatePrice();
        product.setWeight(productWeight);
        product.setLength(productLength);
        product.setWidth(productWidth);
        product.setHeight(productHeight);
        product.setStore(store);
        if (action.equals("add_hidden")) {
            product.setStatus(ProductStatus.HIDDEN);
        }
        if (action.equals("add_publish")) {
            product.setStatus(ProductStatus.ACTIVE);
        }
        product.setQuantity(product.getVariants().stream().mapToInt(ProductVariant::getQuantity).sum());
        product.updateDiscount();
        productRepo.save(product);
        response.put("message", "Thêm sản phẩm thành công");
        return ResponseEntity.ok(response);
    }



    @GetMapping("/variant/{productId}")
    public ResponseEntity<?> getVariant(
        @PathVariable(value = "productId", required = true) Long productId
    ) {
        Map<String, Object> response = new HashMap<>();
        Product product = productRepo.findByIdWithVariants(productId);
        if (product == null) {
            response.put("message", "Không tìm thấy sản phẩm");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("variants", product.getVariants().stream().map(v -> v.toDTO()).toList());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/editVariant/{variantId}")
    public ResponseEntity<?> editVariant(
        @PathVariable(value = "variantId", required = true) Long variantId,
        @RequestParam(value = "price", required = true) Double price,
        @RequestParam(value = "salePrice", required = true) Double salePrice,
        @RequestParam(value = "quantity", required = true) Integer quantity
    ) {
        Map<String, String> response = new HashMap<>();
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        ProductVariant variant = productVariantRepo.findByIdWithProduct(variantId);
        if (variant == null) {
            response.put("message", "Không tìm thấy biến thể sản phẩm");
            return ResponseEntity.badRequest().body(response);
        }
        Product product = variant.getProduct();
        if (product == null || !Objects.equals(product.getStore().getId(), store.getId())) {
            response.put("message", "Không tìm thấy sản phẩm");
            return ResponseEntity.badRequest().body(response);
        }
        if (price <= 0) {
            response.put("message", "Vui lòng nhập giá sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        if (salePrice <= 0) {
            response.put("message", "Vui lòng nhập giá khuyến mãi sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        if (quantity <= 0) {
            response.put("message", "Vui lòng nhập số lượng sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        variant.setPrice(price);
        variant.setSalePrice(salePrice);
        int quantityOld = variant.getQuantity();
        variant.setQuantity(quantity);
        productVariantRepo.update(variant);
        product.setQuantity(product.getQuantity() - quantityOld + quantity);
        product.updatePrice();
        product.updateDiscount();
        productRepo.update(product);
        response.put("message", "Cập nhật biến thể sản phẩm thành công");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/edit/thumbnail/{productId}")
    public ResponseEntity<?> editThumbnail(
        @PathVariable(value = "productId", required = true) Long productId,
        @RequestParam(value = "thumbnail", required = true) String thumbnail
    ) {
        Map<String, String> response = new HashMap<>();
        thumbnail = xssProtected.encodeAllHTMLElement(thumbnail);
        if (thumbnail.isBlank()) {
            response.put("message", "Vui lòng chọn ảnh thu nhỏ cho sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        Product product = productRepo.findById(productId);
        if (product == null || !Objects.equals(product.getStore().getId(), store.getId())) {
            response.put("message", "Không tìm thấy sản phẩm");
            return ResponseEntity.badRequest().body(response);
        }
        product.setThumbnail(thumbnail);
        productRepo.update(product);
        response.put("message", "Cập nhật ảnh thu nhỏ sản phẩm thành công");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/edit/images/{productId}")
    public ResponseEntity<?> editImages(
        @PathVariable(value = "productId", required = true) Long productId,
        @RequestParam(value = "images[]", required = true) String[] images
    ) {
        Map<String, String> response = new HashMap<>();

        if (images.length == 0) {
            response.put("message", "Vui lòng chọn ít nhất 1 ảnh cho sản phẩm");
            return ResponseEntity.badRequest().body(response);
        }

        for (String image : images) {
            image = xssProtected.encodeAllHTMLElement(image);
            if (image.isBlank()) {
                response.put("message", "Vui lòng chọn ảnh cho sản phẩm hợp lệ");
                return ResponseEntity.badRequest().body(response);
            }
        }
    
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
    
        Product product = productRepo.findById(productId);
        if (product == null || !Objects.equals(product.getStore().getId(), store.getId())) {
            response.put("message", "Không tìm thấy sản phẩm");
            return ResponseEntity.badRequest().body(response);
        }
    
        List<ProductImage> oldImages = new ArrayList<>(product.getImages());
        for (ProductImage oldImage : oldImages) {
            product.removeImage(oldImage);
        }
    
        for (String image : images) {
            ProductImage productImage = new ProductImage();
            productImage.setImage(image);
            product.addImage(productImage);
        }
    
        productRepo.update(product);
    
        response.put("message", "Cập nhật ảnh sản phẩm thành công");
        return ResponseEntity.ok(response);
    }
    

    @PostMapping("/edit/video/{productId}")
    public ResponseEntity<?> editVideo(
        @PathVariable(value = "productId", required = true) Long productId,
        @RequestParam(value = "video", required = true) String video
    ) {
        Map<String, String> response = new HashMap<>();
        video = xssProtected.encodeAllHTMLElement(video);
        if (video.isBlank()) {
            response.put("message", "Vui lòng chọn video cho sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        Product product = productRepo.findById(productId);
        if (product == null || !Objects.equals(product.getStore().getId(), store.getId())) {
            response.put("message", "Không tìm thấy sản phẩm");
            return ResponseEntity.badRequest().body(response);
        }
        product.setVideo(video);
        productRepo.update(product);
        response.put("message", "Cập nhật video sản phẩm thành công");
        return ResponseEntity.ok(response);
    }

    // Tính toán chi phí vận chuyển
    @GetMapping("/calculate-ship-fee/{productId}/{locationId}")
    public ResponseEntity<?> calculateShipFee(
        @PathVariable("productId") Long productId,
        @PathVariable("locationId") Long locationId
    ) {
        Map<String, Object> response = new HashMap<>();

        Product product = productRepo.findByIdWithStore(productId);
        UserLocation location = userLocationRepo.findById(locationId);

        // Kiểm tra xem sản phẩm và vị trí có tồn tại không
        if (product == null || location == null) {
            response.put("message", "Sản phẩm hoặc vị trí không tồn tại");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Store store = product.getStore();
        if (store == null || store.getDistrict() == null || location.getDistrict() == null) {
            response.put("message", "Thông tin cửa hàng hoặc khu vực không hợp lệ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        double distance = location.getDistrict().calculateDistance(store.getDistrict());

        double convertedWeight = Math.ceil((product.getLength() * product.getWidth() * product.getHeight()) / 6000.0);
        double weight = Math.max(convertedWeight, product.getWeight());

        double shipFee;
        if (weight <= 1000) {
            shipFee = 15000;
        } else if (weight <= 1500) {
            shipFee = 20000;
        } else if (weight <= 2000) {
            shipFee = 30000;
        } else if (weight <= 3000) {
            shipFee = 40000;
        } else if (weight <= 5000) {
            shipFee = 50000;
        } else {
            shipFee = 50000 + Math.ceil((weight - 5000) / 1000.0) * 10000;
        }

        if (Objects.equals(store.getDistrict().getId(), location.getDistrict().getId())) {
            response.put("shipFee", shipFee);
            response.put("message", "Tính toán phí vận chuyển thành công");
            return ResponseEntity.ok(response);
        }
        double distanceFactor = (distance <= 100) ? 1.0 
        : (distance <= 300) ? 1.2 
        : (distance <= 500) ? 1.5
        : (distance <= 1000) ? 2.0
        : (distance <= 1500) ? 2.5
        : 3.0;
        shipFee *= distanceFactor;

        response.put("shipFee", shipFee);
        response.put("message", "Tính toán phí vận chuyển thành công");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/edit/name/{productId}")
    public ResponseEntity<?> editName(
        @PathVariable(value = "productId", required = true) Long productId,
        @RequestParam(value = "name", required = true) String name
    ) {
        Map<String, String> response = new HashMap<>();
        name = xssProtected.encodeAllHTMLElement(name);
        if (name.isBlank()) {
            response.put("message", "Vui lòng nhập tên sản phẩm hợp lệ");
            return ResponseEntity.badRequest().body(response);
        }
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        Product product = productRepo.findById(productId);
        if (product == null || !Objects.equals(product.getStore().getId(), store.getId())) {
            response.put("message", "Không tìm thấy sản phẩm");
            return ResponseEntity.badRequest().body(response);
        }
        product.setName(name);
        productRepo.update(product);
        response.put("message", "Cập nhật tên sản phẩm thành công");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/edit/description/{productId}")
    public ResponseEntity<?> editDescription(
        @PathVariable(value = "productId", required = true) Long productId,
        @RequestParam(value = "description", required = true) String description
    ) {
        Map<String, String> response = new HashMap<>();
        description = xssProtected.sanitize(description);
        if (description.length() < 100 || description.length() > 50000) {
            response.put("message", "Mô tả sản phẩm phải từ 100 đến 5000 ký tự");
            return ResponseEntity.badRequest().body(response);
        }
        User user = userRepo.getUserWithStore(getUser().getId());
        Store store = user.getStore();
        Product product = productRepo.findById(productId);
        if (product == null || !Objects.equals(product.getStore().getId(), store.getId())) {
            response.put("message", "Không tìm thấy sản phẩm");
            return ResponseEntity.badRequest().body(response);
        }
        product.setDescription(description);
        productRepo.update(product);
        response.put("message", "Cập nhật mô tả sản phẩm thành công");
        return ResponseEntity.ok(response);
    }

}
