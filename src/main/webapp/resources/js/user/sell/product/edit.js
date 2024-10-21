
function uploadImage(file) {
    // check file type
    if (!file.type.match('image.*')) {
        showInfo("Cảnh báo", "Bạn phải chọn file hình ảnh.", context.warning);
        return;
    }
    // check file size
    if (file.size > 2 * 1024 * 1024) {
        showInfo("Cảnh báo", "Kích thước file phải nhỏ hơn 2MB.", context.warning);
        return;
    }
    const fileName = file.name;
    const fileExtension = fileName.split('.').pop();
    if (!["jpg", "jpeg", "png", "gif", "bmp", "webp"].includes(fileExtension)) {
        showInfo("Cảnh báo", "File phải có định dạng ảnh (jpg, jpeg, png, gif, bmp, webp).", context.warning);
        return;
    }
    const formData = new FormData();
    formData.append('file', file);
    const url = baseHref + "api/upload/image/product";
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    console.log(csrfToken);
    return fetch(url, {
        method: 'POST',
        body: formData,
        headers: {
            [csrfHeader]: csrfToken
        }
    })
    .then(response => response.json())
    .then(data => data.fileName);
}

// upload video
function uploadVideo(file) {
    // check file type
    if (!file.type.match('video/mp4')) {
        showInfo("Cảnh báo", "Bạn phải chọn file video.", context.warning);
        return;
    }
    // check file size
    if (file.size > 30 * 1024 * 1024) {
        showInfo("Cảnh báo", "Kích thước file phải nhỏ hơn 30MB.", context.warning);
        return;
    }
    const formData = new FormData();
    formData.append('file', file);
    const url = baseHref + "api/upload/video/product";
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    console.log(csrfToken);
    return fetch(url, {
        method: 'POST',
        body: formData,
        headers: {
            [csrfHeader]: csrfToken
        }
    })
    .then(response => response.json())
    .then(data => data.fileName);
}




// upload thumbnail
document.getElementById('thumbnailBtn').addEventListener('click', function() {
    document.getElementById('thumbnailFile').click();
});

document.getElementById('thumbnailFile').addEventListener('change', async (e) => {
    const file = e.target.files[0];
    const url = await uploadImage(file);
    if (!url) {
        return;
    }
    document.getElementById('thumbnail').value = url;
    let imageUrl = baseHref + "resources/images/products/" + url;
    //show image
    document.getElementById('thumbnailBtn').innerHTML = '<img src="' + imageUrl + '"/>';
    document.getElementById('thumbnailBtn').title = 'Click để thay đổi hình ảnh';
});

// upload images
document.getElementById('imageBtn').addEventListener('click', function() {
    document.getElementById('imagesFile').click();
});

function countImage() {
    return document.querySelectorAll('.changeImage').length;
}

document.getElementById('imagesFile').addEventListener('change', async (e) => {
    const files = e.target.files;
    let count = countImage();
    if (count + files.length > 9) {
        showInfo("Cảnh báo", "Số lượng ảnh tối đa là 9.", context.warning);
        return;
    }
    const uploadFiles = await Promise.all(Array.from(files).map(file => uploadImage(file)));
    if (!uploadFiles) {
        return;
    }
    uploadFiles.forEach((uploadFile, index) => {
        // thêm độ trễ cho hiệu ứng
        setTimeout(() => {
            addNewBtnImage(uploadFile);
        }, (index - 1) * 200);
    });
    if (countImage() >= 9) {
        document.getElementById('imageBtn').classList.add('d-none');
    }
});

function isExistImage(uploadFile) {
    const images = document.querySelectorAll('input[name="images[]"]');
    for (let i = 0; i < images.length; i++) {
        if (images[i].value === uploadFile) {
            return true;
        }
    }
    return false;
}

function addNewBtnImage(uploadFile) {
    if (!uploadFile) {
        return;
    }
    if (isExistImage(uploadFile)) {
        return;
    }
    const imageUrl = baseHref + "resources/images/products/" + uploadFile;
    const btnImage = document.createElement('button');
    btnImage.type = 'button';
    btnImage.title = 'Click để thay đổi hình ảnh';
    btnImage.className = 'btn-image changeImage me-2 mb-2 position-relative animate__animated animate__zoomIn';
    btnImage.innerHTML = '<img src="' + imageUrl + '"/> <input type="hidden" name="images[]" value="' + uploadFile + '"/>';
    // add delete button
    const deleteBtn = document.createElement('button');
    deleteBtn.type = 'button';
    deleteBtn.className = 'btn-close position-absolute top-0 end-0 z-index-1 bg-danger text-white';
    deleteBtn.addEventListener('click', function(e) {
        e.stopPropagation(); // dùng để ngăn chặn sự kiện click của cha
        btnImage.remove();
        const imageBtn = document.getElementById('imageBtn');
        if (countImage() < 9) {
            imageBtn.classList.remove('d-none');
        }
    });
    btnImage.appendChild(deleteBtn);
    document.getElementById('imageBtn').before(btnImage);
    btnImage.addEventListener('click', function() {
        document.getElementById('imageFile').click();
        document.getElementById('imageFile').addEventListener('change', async (e) => {
            const file = e.target.files[0];
            const url = await uploadImage(file);
            if (!url) {
                return;
            }
            const imageUrl = baseHref + "resources/images/products/" + url;
            btnImage.innerHTML = '<img src="' + imageUrl + '"/> <input type="hidden" name="images[]" value="' + url + '"/>';
            btnImage.title = 'Click để thay đổi hình ảnh';
            btnImage.appendChild(deleteBtn);
        });
    });
}

// upload video
document.getElementById('videoBtn').addEventListener('click', function() {
    document.getElementById('videoFile').click();
});

document.getElementById('videoFile').addEventListener('change', async (e) => {
    const file = e.target.files[0];
    const url = await uploadVideo(file);
    if (!url) {
        return;
    }
    document.getElementById('video').value = url;
    let videoUrl = baseHref + "resources/videos/products/" + url;
    //show video
    document.getElementById('videoBtn').innerHTML = '<video width="100%" height="100%" controls><source src="' + videoUrl + '" type="video/mp4"></video>';
    document.getElementById('videoBtn').title = 'Click để thay đổi video';
});

// tinymce
const image_upload_handler_callback = (blobInfo, progress) => new Promise((resolve, reject) => {
    const url = baseHref + "api/upload/image/product";
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const formData = new FormData();
    formData.append('file', blobInfo.blob(), blobInfo.filename());

    fetch(url, {
        method: 'POST',
        body: formData,
        headers: {
            [csrfHeader]: csrfToken
        },
        credentials: 'same-origin'
    }).then(response => {
        if (!response.ok) {
            return reject(`Upload failed with status ${response.status}: ${response.statusText}`);

        }
        return response.json();
    }).then(data => {
        if (!data || !data.fileName) {
            return reject('Invalid response format');
        }
        resolve(baseHref + "resources/images/products/" + data.fileName);
    }).catch(error => {
        reject(`Upload failed: ${error.message}`);
    });
});



tinyMCE.baseURL = baseHref + 'resources/tinymce';
tinymce.init({
    selector: '#description',
    language: 'vi',
    license_key: 'gpl',
    content_style: `
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');
        body { font-family: 'Roboto'; font-size: 16px; }
    `,
    font_formats: `Andale Mono=andale mono,times; Arial=arial,helvetica,sans-serif; Arial Black=arial black,avant garde; 
    Book Antiqua=book antiqua,palatino; Comic Sans MS=comic sans ms,sans-serif; Courier New=courier new,courier; 
    Georgia=georgia,palatino; Helvetica=helvetica; Impact=impact,chicago; Symbol=symbol; 
    Tahoma=tahoma,arial,helvetica,sans-serif; Terminal=terminal,monaco; 
    Times New Roman=times new roman,times; Trebuchet MS=trebuchet ms,geneva; Verdana=verdana,geneva; 
    Webdings=webdings; Wingdings=wingdings,zapf dingbats; Roboto=roboto,arial,helvetica,sans-serif`,
    plugins: 'preview importcss searchreplace autolink autosave save directionality code fullscreen image link media codesample table charmap anchor advlist lists wordcount help quickbars emoticons',
    relative_urls: false,
    remove_script_host: false,
    convert_urls: true,
    images_upload_url: baseHref + 'api/upload/image/product',
    images_upload_handler: image_upload_handler_callback,
    menubar: 'file edit view insert format tools table help',
    toolbar: 'undo redo | bold italic underline | fontfamily fontsize blocks | alignleft aligncenter alignright | numlist bullist | forecolor backcolor | fullscreen preview save | link image media | charmap emoticons',
    height: 400,
    image_advtab: true,
    autosave_ask_before_unload: true,
    autosave_interval: '30s',
    autosave_retention: '2m',
    image_caption: true,
    quickbars_selection_toolbar: 'bold italic | quicklink h2 h3 blockquote quickimage quicktable',
    noneditable_class: 'mceNonEditable',
    toolbar_mode: 'sliding',
    contextmenu: 'link image table',
    toolbar_sticky: true,
    importcss_append: true,
});



function changeTableStructure() {
    const styles = document.querySelectorAll('.product-style');
    const sizes = document.querySelectorAll('.product-size');

    if (styles.length > 0 && sizes.length == 0) {
        document.getElementById('tableList').querySelector('thead tr').innerHTML = `
            <th>Kiểu sản phẩm</th>
            <th>Giá niêm yết</th>
            <th>Giá</th>
            <th>Tồn kho</th>
        `;
        const tbody = document.getElementById('tableList').querySelector('tbody');
        tbody.innerHTML = '';
        styles.forEach(style => {
            tbody.innerHTML += `
                <tr>
                    <td><input type="text" class="form-control" name="style[]" value="${style.value}" placeholder="Kiểu sản phẩm" readonly/></td>
                    <td><input type="number" class="form-control" name="priceOrg[]" placeholder="Giá Niêm Yết"/></td>
                    <td><input type="number" class="form-control" name="price[]" placeholder="Giá Bán"/></td>
                    <td><input type="number" class="form-control" name="quantity[]" placeholder="Tồn kho"/></td>
                </tr>
            `;
        });
    } else if (sizes.length > 0 && styles.length == 0) {
        document.getElementById('tableList').querySelector('thead tr').innerHTML = `
            <th>Kích thước sản phẩm</th>
            <th>Giá niêm yết</th>
            <th>Giá bán</th>
            <th>Tồn kho</th>
        `;
        const tbody = document.getElementById('tableList').querySelector('tbody');
        tbody.innerHTML = '';
        sizes.forEach(size => {
            tbody.innerHTML += `
                <tr>
                    <td><input type="text" class="form-control" value="${size.value}" placeholder="Kích thước sản phẩm" readonly/></td>
                    <td><input type="number" class="form-control" name="priceOrg[]" placeholder="Giá Niêm Yết"/></td>
                    <td><input type="number" class="form-control" name="price[]" placeholder="Giá Bán"/></td>
                    <td><input type="number" class="form-control" name="quantity[]" placeholder="Tồn kho"/></td>
                </tr>
            `;
        });
    } else if (styles.length > 0 && sizes.length > 0) {
        document.getElementById('tableList').querySelector('thead tr').innerHTML = `
            <th>Kiểu sản phẩm</th>
            <th>Kích thước sản phẩm</th>
            <th>Giá niêm yết</th>
            <th>Giá bán</th>
            <th>Tồn kho</th>
        `;
        const tbody = document.getElementById('tableList').querySelector('tbody');
        tbody.innerHTML = '';
        styles.forEach(style => {
            sizes.forEach((size, index) => {
                if (index == 0) {
                    tbody.innerHTML += `
                        <tr>
                            <td rowspan="${sizes.length}"><input type="text" class="form-control" name="style[]" value="${style.value}" placeholder="Kiểu sản phẩm" readonly/></td>
                            <td><input type="text" class="form-control" value="${size.value}" placeholder="Kích thước sản phẩm" readonly/></td>
                            <td><input type="number" class="form-control" name="priceOrg[]" placeholder="Giá Niêm Yết"/></td>
                            <td><input type="number" class="form-control" name="price[]" placeholder="Giá bán"/></td>
                            <td><input type="number" class="form-control" name="quantity[]" placeholder="Tồn kho"/></td>
                        </tr>
                    `;
                } else {
                    tbody.innerHTML += `
                        <tr>
                            <td><input type="text" class="form-control" value="${size.value}" placeholder="Kích thước sản phẩm" readonly/></td>
                            <td><input type="number" class="form-control" name="priceOrg[]" placeholder="Giá Niêm Yết"/></td>
                            <td><input type="number" class="form-control" name="price[]" placeholder="Giá"/></td>
                            <td><input type="number" class="form-control" name="quantity[]" placeholder="Tồn kho"/></td>
                        </tr>
                    `;
                }
            });
        });
    } else {
        document.getElementById('tableList').querySelector('thead tr').innerHTML = `
            <th>Giá niêm yết</th>
            <th>Giá bán</th>
            <th>Tồn kho</th>
        `;
        const tbody = document.getElementById('tableList').querySelector('tbody');
        tbody.innerHTML = `
            <tr>
                <td><input type="number" class="form-control" name="priceOrg[]" placeholder="Giá Niêm Yết"/></td>
                <td><input type="number" class="form-control" name="price[]" placeholder="Giá Bán"/></td>
                <td><input type="number" class="form-control" name="quantity[]" placeholder="Tồn kho"/></td>
            </tr>
        `;
    }
}

function updateShipPrice() {
    const productWeight = document.getElementById('productWeight').value;
    const productLength = document.getElementById('productLength').value;
    const productWidth = document.getElementById('productWidth').value;
    const productHeight = document.getElementById('productHeight').value;
    if (!productWeight || !productLength || !productWidth || !productHeight) {
        document.getElementById('shipping').value = "Vui lòng nhập đủ thông tin";
        return;
    }
    const convertedWeight = Math.ceil((productLength * productWidth * productHeight) / 6);
    let weight = productWeight;
    let price;
    if(convertedWeight > productWeight) {
        weight = convertedWeight;
        document.getElementById('overSize').classList.remove('d-none');
    } else {
        document.getElementById('overSize').classList.add('d-none');
    }
    if (weight <= 1000) {
        price = 15000;
    } else if (weight <= 1500) {
        price = 20000;
    } else if (weight <= 2000) {
        price = 30000;
    } else if (weight <= 3000) {
        price = 40000;
    } else if (weight <= 5000) {
        price = 50000;
    } else {
        price = 50000 + Math.ceil((weight - 5000) / 1000) * 10000;
    }
    document.getElementById('shipping').value = price + " VND";
}

document.getElementById('productWeight').addEventListener('input', function() {
    updateShipPrice();
});

document.getElementById('productLength').addEventListener('input', function() {
    updateShipPrice();
});

document.getElementById('productWidth').addEventListener('input', function() {
    updateShipPrice();
});

document.getElementById('productHeight').addEventListener('input', function() {
    updateShipPrice();
});


function validatedStyle() {
    // Get all style input
    const styles = document.querySelectorAll('.product-style');
    let isValid = true;
    for (i = 0; i < styles.length; i++) {
        if (styles[i].value == '') {
            styles[i].classList.remove('is-valid');
            styles[i].classList.add('is-invalid');
            isValid = false;
        } else {
            styles[i].classList.remove('is-invalid');
            styles[i].classList.add('is-valid');
        }
        if (styles[i].nextElementSibling.value == '') {
            styles[i].nextElementSibling.nextElementSibling.classList.remove('btn-is-valid');
            styles[i].nextElementSibling.nextElementSibling.classList.add('btn-is-invalid');
            isValid = false;
        } else {
            styles[i].nextElementSibling.nextElementSibling.classList.remove('btn-is-invalid');
            styles[i].nextElementSibling.nextElementSibling.classList.add('btn-is-valid');
        }
    }
    return isValid;
}

function validatedSize() {
    // Get all size input
    const sizes = document.querySelectorAll('.product-size');
    let isValid = true;
    for (i = 0; i < sizes.length; i++) {
        if (sizes[i].value == '') {
            sizes[i].classList.remove('is-valid');
            sizes[i].classList.add('is-invalid');
            isValid = false;
        } else {
            sizes[i].classList.remove('is-invalid');
            sizes[i].classList.add('is-valid');
        }
    }
    return isValid;
}

function validatedTable() {
    const table = document.getElementById('tableList');
    const rows = table.querySelectorAll('tbody tr');
    let isValid = true;
    for (i = 0; i < rows.length; i++) {
        const inputs = rows[i].querySelectorAll('input');
        for (j = 0; j < inputs.length; j++) {
            if (inputs[j].value == '') {
                inputs[j].classList.remove('is-valid');
                inputs[j].classList.add('is-invalid');
                isValid = false;
            } else {
                inputs[j].classList.remove('is-invalid');
                inputs[j].classList.add('is-valid');
            }
        }
    }
    return isValid;
}

function validatedThumbnail() {
    const thumbnail = document.getElementById('thumbnail');
    let isValidate = true;
    if (!thumbnail.value) {
        thumbnail.nextElementSibling.classList.add('btn-is-invalid');
        thumbnail.nextElementSibling.classList.remove('btn-is-valid');
        isValidate = false;
    } else {
        thumbnail.nextElementSibling.classList.add('btn-is-valid');
        thumbnail.nextElementSibling.classList.remove('btn-is-invalid');
    }
    return isValidate;
}

function validatedImage() {
    const images = document.querySelectorAll('input[name="images[]"]');

    if(images.length == 0) {
        document.getElementById('imageBtn').classList.add('btn-is-invalid');
        return false;
    } else {
        document.getElementById('imageBtn').classList.remove('btn-is-invalid');
    }

    let isValidate = true;
    for (i = 0; i < images.length; i++) {
        if (!images[i].value) {
            images[i].parentElement.classList.add('btn-is-invalid');
            images[i].parentElement.classList.remove('btn-is-valid');
        } else {
            images[i].parentElement.classList.add('btn-is-valid');
            images[i].parentElement.classList.remove('btn-is-invalid');
        }
    }
    return isValidate;
}

function validatedVideo() {
    const video = document.getElementById('video');
    let isValidate = true;
    if (!video.value) {
        video.nextElementSibling.classList.add('btn-is-invalid');
        video.nextElementSibling.classList.remove('btn-is-valid');
        isValidate = false;
    } else {
        video.nextElementSibling.classList.add('btn-is-valid');
        video.nextElementSibling.classList.remove('btn-is-invalid');
    }
    return isValidate;
}



function validatedNameProduct() {
    const nameProduct = document.getElementById('name');
    let isValidate = true;
    if (!nameProduct.value) {
        nameProduct.classList.add('is-invalid');
        nameProduct.classList.remove('is-valid');
        isValidate = false;
    } else {
        nameProduct.classList.add('is-valid');
        nameProduct.classList.remove('is-invalid');
    }
    return isValidate;
}

function validatedCategory() {
    const category = document.getElementById('category');
    let isValidate = true;
    if (!category.value) {
        category.nextElementSibling.classList.add('is-invalid');
        category.nextElementSibling.classList.remove('is-valid');
        isValidate = false;
    } else {
        category.nextElementSibling.classList.add('is-valid');
        category.nextElementSibling.classList.remove('is-invalid');
    }
    return isValidate;
}

function validatedDescription() {
    // load tinymce content to textarea
    const content = tinymce.activeEditor.getContent('description');
    const charCount = tinymce.activeEditor.plugins.wordcount.body.getCharacterCount();
    const description = document.getElementById('description');
    const descInfo = document.getElementById('descInfo');
    let isValidate = true;
    if (charCount < 100 || charCount > 5000) {
        descInfo.classList.remove('d-none');
        isValidate = false;
    } else {
        descInfo.classList.add('d-none');
        description.value = content;
    }
    return isValidate;
}

function validatedBrand() {
    const brand = document.getElementById('brand');
    let isValidate = true;
    if (!brand.value) {
        brand.classList.add('is-invalid');
        brand.classList.remove('is-valid');
        isValidate = false;
    } else {
        brand.classList.add('is-valid');
        brand.classList.remove('is-invalid');
    }
    return isValidate;
}

function validatedBrandOrigin() {
    const brandOrigin = document.getElementById('brandOrigin');
    let isValidate = true;
    if (!brandOrigin.value) {
        brandOrigin.classList.add('is-invalid');
        brandOrigin.classList.remove('is-valid');
        isValidate = false;
    } else {
        brandOrigin.classList.add('is-valid');
        brandOrigin.classList.remove('is-invalid');
    }
    return isValidate;
}

function validatedMadeIn() {
    const madeIn = document.getElementById('madeIn');
    let isValidate = true;
    if (!madeIn.value) {
        madeIn.classList.add('is-invalid');
        madeIn.classList.remove('is-valid');
        isValidate = false;
    } else {
        madeIn.classList.add('is-valid');
        madeIn.classList.remove('is-invalid');
    }
    return isValidate;
}

function validatedGuarantee() {
    const guarantee = document.getElementById('guarantee');
    let isValidate = true;
    if (!guarantee.value) {
        guarantee.classList.add('is-invalid');
        guarantee.classList.remove('is-valid');
        isValidate = false;
    } else {
        guarantee.classList.add('is-valid');
        guarantee.classList.remove('is-invalid');
    }
    return isValidate;
}

function validatedProductWeight() {
    const productWeight = document.getElementById('productWeight');
    let isValidate = true;
    if (!productWeight.value) {
        productWeight.classList.add('is-invalid');
        productWeight.classList.remove('is-valid');
        isValidate = false;
    } else {
        productWeight.classList.add('is-valid');
        productWeight.classList.remove('is-invalid');
    }
    return isValidate;
}

function validatedProductSize() {
    const productLength = document.getElementById('productLength');
    const productWidth = document.getElementById('productWidth');
    const productHeight = document.getElementById('productHeight');
    let isValidate = true;
    if (!productLength.value || !productWidth.value || !productHeight.value) {
        productLength.classList.add('is-invalid');
        productLength.classList.remove('is-valid');
        productWidth.classList.add('is-invalid');
        productWidth.classList.remove('is-valid');
        productHeight.classList.add('is-invalid');
        productHeight.classList.remove('is-valid');
        isValidate = false;
    } else {
        productLength.classList.add('is-valid');
        productLength.classList.remove('is-invalid');
        productWidth.classList.add('is-valid');
        productWidth.classList.remove('is-invalid');
        productHeight.classList.add('is-valid');
        productHeight.classList.remove('is-invalid');
    }
    return isValidate;
}

function scrollToElement(id) {
    document.getElementById(id).scrollIntoView({ behavior: 'smooth' });
}

function delay(s) {
    let ms = s * 1000;
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function checkAllBeforeSubmit() {
    scrollToElement('thumbnailBtn');
    const isValidateThumbnail = validatedThumbnail();
    await delay(0.5);

    scrollToElement('imageBtn');
    const isValidateImage = validatedImage();
    await delay(0.5);

    scrollToElement('videoBtn');
    const isValidateVideo = validatedVideo();
    await delay(0.5);


    scrollToElement('name');
    const isValidateNameProduct = validatedNameProduct();
    await delay(0.5);


    scrollToElement('category');
    const isValidateCategory = validatedCategory();
    await delay(0.5);

    scrollToElement('lbDesc');
    const isValidateDescription = validatedDescription();
    await delay(0.5);

    scrollToElement('brand');
    const isValidateBrand = validatedBrand();
    await delay(0.5);

    scrollToElement('brandOrigin');
    const isValidateBrandOrigin = validatedBrandOrigin();
    await delay(0.5);

    scrollToElement('madeIn');
    const isValidateMadeIn = validatedMadeIn();
    await delay(0.5);

    scrollToElement('guarantee');
    const isValidateGuarantee = validatedGuarantee();
    await delay(0.5);

    scrollToElement('addSize');
    const isValidatedSize = validatedSize();
    await delay(0.5);

    scrollToElement('tableList');
    const isalidatedTable = validatedTable();
    await delay(0.5);

    scrollToElement('productWeight');
    const isValidateProductWeight = validatedProductWeight();
    await delay(0.5);

    scrollToElement('productSize');
    const isValidateProductSize = validatedProductSize();
    await delay(0.5);

    // return true;
    return isValidateThumbnail && isValidateImage && isValidateVideo 
    && isValidateNameProduct && isValidateCategory && isValidateDescription 
    && isValidateBrand && isValidateBrandOrigin && isValidateMadeIn 
    && isValidateGuarantee && isValidatedStyle && isValidatedSize 
    && isalidatedTable && isValidateProductWeight && isValidateProductSize;
}


// cập nhật variant
const listForm = document.getElementsByClassName("updateVariant");
for (let form of listForm) {
    form.addEventListener("submit", function(e) {
        console.log("submit");
        e.preventDefault();
        const url = this.action;
        const data = new FormData(this);
        const params = new URLSearchParams(data);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        fetch(url, {
            method: "POST",
            body: params,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeader]: csrfToken
            }
        })
        .then(response => response.json())
        .then(data => {
            showInfo("Thông báo", data.message, context.success);
        })
    });
}

// updateThumbnail
document.getElementById('updateThumbnail').addEventListener('submit', function(e) {
    console.log("submit");
    e.preventDefault();
    const isValid = validatedThumbnail();
    if(isValid) {
        const url = this.action;
        const data = new FormData(this);
        const params = new URLSearchParams(data);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        fetch(url, {
            method: "POST",
            body: params,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeader]: csrfToken
            }
        })
        .then(response => response.json())
        .then(data => {
            showInfo("Thông báo", data.message, context.success);
        })
    } else {
        showInfo("Cảnh báo", "Vui lòng kiểm tra lại thông tin.", context.warning);
    }
});

// updateImage
document.getElementById('updateImages').addEventListener('submit', function(e) {
    e.preventDefault();

    const isValid = validatedImage();
    if(isValid) {
        const url = this.action;
        const data = new FormData(this);
        const params = new URLSearchParams(data);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        fetch(url, {
            method: "POST",
            body: params,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeader]: csrfToken
            }
        })
        .then(response => response.json())
        .then(data => {
            showInfo("Thông báo", data.message, context.success);
        })
    } else {
        showInfo("Cảnh báo", "Vui lòng kiểm tra lại thông tin.", context.warning);
    }
});

// updateVideo
document.getElementById('updateVideo').addEventListener('submit', function(e) {
    e.preventDefault();
    const isValid = validatedVideo();
    if(isValid) {
        const url = this.action;
        const data = new FormData(this);
        const params = new URLSearchParams(data);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        fetch(url, {
            method: "POST",
            body: params,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeader]: csrfToken
            }
        })
        .then(response => response.json())
        .then(data => {
            showInfo("Thông báo", data.message, context.success);
        })
    } else {
        showInfo("Cảnh báo", "Vui lòng kiểm tra lại thông tin.", context.warning);
    }
});

// updateNameProduct
document.getElementById('updateNameProduct').addEventListener('submit', function(e) {
    e.preventDefault();
    const isValid = validatedNameProduct()
    if(isValid) {
        const url = this.action;
        const data = new FormData(this);
        const params = new URLSearchParams(data);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        fetch(url, {
            method: "POST",
            body: params,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeader]: csrfToken
            }
        })
        .then(response => response.json())
        .then(data => {
            showInfo("Thông báo", data.message, context.success);
        })
    } else {
        showInfo("Cảnh báo", "Vui lòng kiểm tra lại thông tin.", context.warning);
    }
});

// // updateCategory
// document.getElementById('updateCategory').addEventListener('submit', function() {
//     e.preventDefault();
//     validatedCategory().then(isValid => {
//         if(isValid) {
//             const url = this.action;
//             const data = new FormData(this);
//             const params = new URLSearchParams(data);
//             const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
//             const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
//             fetch(url, {
//                 method: "POST",
//                 body: params,
//                 headers: {
//                     'Content-Type': 'application/x-www-form-urlencoded',
//                     [csrfHeader]: csrfToken
//                 }
//             })
//             .then(response => response.json())
//             .then(data => {
//                 showInfo("Thông báo", data.message, context.success);
//             })
//         } else {
//             showInfo("Cảnh báo", "Vui lòng kiểm tra lại thông tin.", context.warning);
//         }
//     });
// });

// updateDescription
document.getElementById('updateDescription').addEventListener('submit', function(e) {
    e.preventDefault();
    const isValid = validatedDescription();
    if(isValid) {
        const url = this.action;
        const data = new FormData(this);
        const params = new URLSearchParams(data);
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
        fetch(url, {
            method: "POST",
            body: params,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeader]: csrfToken
            }
        })
        .then(response => response.json())
        .then(data => {
            showInfo("Thông báo", data.message, context.success);
        })
    } else {
        showInfo("Cảnh báo", "Vui lòng kiểm tra lại thông tin.", context.warning);
    }
});
