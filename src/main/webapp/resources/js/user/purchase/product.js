document.addEventListener("DOMContentLoaded", function () {
    const url = `${baseHref}api/user/sell/product/variant/${productId}`;

    fetch(url)
        .then(response => response.json())
        .then(data => data.variants)
        .then(variants => {
            const styleContainer = document.getElementById('style-container');
            const sizeContainer = document.getElementById('size-container');

            const uniqueStyles = [...new Map(variants.map(v => [v.style?.id, v.style])).values()].filter(Boolean);
            const uniqueSizes = [...new Map(variants.map(v => [v.size?.id, v.size])).values()].filter(Boolean);

            // Hiển thị tiêu đề nếu có kiểu dáng hoặc kích cỡ
            renderSectionTitle(styleContainer, 'Kiểu dáng', uniqueStyles.length > 0);
            renderSectionTitle(sizeContainer, 'Kích cỡ', uniqueSizes.length > 0);

            uniqueStyles.forEach(style => renderStyleOption(style, styleContainer));
            uniqueSizes.forEach(size => renderSizeOption(size, sizeContainer));

            function renderSectionTitle(container, title, condition) {
                if (condition) {
                    const sectionTitle = document.createElement('h6');
                    sectionTitle.innerText = title;
                    container.appendChild(sectionTitle);
                }
            }

            function renderStyleOption(style, container) {
                const styleDiv = createVariantElement(style.id, style.name, style.image, true);
                styleDiv.addEventListener('click', () => handleSelection(styleDiv, 'active-style'));
                container.appendChild(styleDiv);
            }

            function renderSizeOption(size, container) {
                const sizeDiv = createVariantElement(size.id, size.name, null, false);
                sizeDiv.addEventListener('click', () => handleSelection(sizeDiv, 'active-size'));
                container.appendChild(sizeDiv);
            }

            function createVariantElement(id, name, image, isStyle) {
                const div = document.createElement('div');
                div.setAttribute(isStyle ? 'data-style-id' : 'data-size-id', id);
                div.classList.add('d-inline-block', 'text-center', 'mb-2', 'me-2', 'p-2', 'border', 'border-secondary', 'rounded');
                div.style.minWidth = '50px';
                div.style.cursor = 'pointer';
                div.innerHTML = isStyle
                    ? `<img src="${baseHref}resources/images/products/${image}" alt="${name}" class="img-variant">
                       <p class="text-muted small">${name}</p>`
                    : `<span>${name}</span>`;
                return div;
            }

            function handleSelection(selectedDiv, activeClass) {
                const previousActive = document.querySelector(`.${activeClass}`);
                if (previousActive) {
                    previousActive.classList.remove(activeClass, 'border-primary', 'border-2');
                    previousActive.classList.add('border-secondary');
                }
                selectedDiv.classList.add(activeClass, 'border-primary', 'border-2');
                selectedDiv.classList.remove('border-secondary');
                updateData();
            }

            function updateData() {
                try {
                    const selectedStyleID = document.querySelector('.active-style')?.getAttribute('data-style-id');
                    const selectedSizeID = document.querySelector('.active-size')?.getAttribute('data-size-id');
                    let selectedVariant;
                    if (selectedStyleID == null) {
                        selectedVariant = variants.find(variant =>
                            variant.style == null && variant.size.id == selectedSizeID
                        );
                    }
                    else if (selectedSizeID == null) {
                        selectedVariant = variants.find(variant =>
                            variant.style.id == selectedStyleID && variant.size == null
                        );
                    }
                    else {
                        selectedVariant = variants.find(variant =>
                            variant.style.id == selectedStyleID && variant.size.id == selectedSizeID
                        );
                    }

                    if (selectedVariant) {
                        document.getElementById("oldPrice").innerHTML = `
                            <span class="text-muted text-decoration-line-through fs-4 fw-bold currency">
                                ${formatter.format(selectedVariant.price)}
                            </span>`;
                        document.getElementById("salePrice").innerHTML = `
                            <span class="text-danger fs-4 fw-bold currency">${formatter.format(selectedVariant.salePrice)}</span>`;

                        document.getElementById("discount").innerHTML =  "-" + Math.round((1 - selectedVariant.salePrice / selectedVariant.price) * 100) + "%";
                        document.getElementById("variantId").value = selectedVariant.id;
                        
                    } else {
                        console.warn("Không tìm thấy biến thể phù hợp.");
                    }
                } catch (error) {
                    console.error("Lỗi khi cập nhật dữ liệu biến thể:", error);
                }
            }
        })
        .catch(error => {
            console.error("Lỗi khi lấy dữ liệu biến thể:", error);
        });
});
