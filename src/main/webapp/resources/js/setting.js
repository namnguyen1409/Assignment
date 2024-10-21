

const context = Object.freeze({
    primary: "primary",
    secondary: "secondary",
    success: "success",
    danger: "danger",
    warning: "warning",
    info: "info",
    light: "light",
    dark: "dark"
});


function showInfo(title, content, context) {
    const infoTitle = document.getElementById('infoModalLabel');
    infoTitle.textContent = title;
    const infoContent = document.getElementById('infoContent');
    infoContent.textContent = content;
    infoContent.className = `text-${context}`;
    document.getElementById('infoShowModel').click();
}




function uploadAvatar(file) {
    // check file type
    if (!file.type.match('image.*')) {
        showInfo("Cảnh báo", "Bạn phải chọn file hình ảnh.", context.warning);
        return;
    }
    // check file size: max 2MB
    if (file.size > 2 * 1024 * 1024) {
        showInfo("Cảnh báo", "Kích thước file phải nhỏ hơn 2MB.", context.warning);
        return;
    }
    // check file extension
    const fileName = file.name;
    const fileExtension = fileName.split('.').pop();
    if (!["jpg", "jpeg", "png", "gif", "bmp", "webp"].includes(fileExtension)) {
        showInfo("Cảnh báo", "File phải có định dạng ảnh (jpg, jpeg, png, gif, bmp, webp).", context.warning);
        return;
    }
    // upload file
    const formData = new FormData();
    formData.append("file", file);
    const url = baseHref + "api/upload/image/avatar";
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    // get url of uploaded file (text response)
    return fetch(url, {
        method: "POST",
        body: formData,
        headers: {
            [csrfHeader]: csrfToken
        }
    }).then(response => response.json())
    .then(data => data.fileName);
}

document.getElementById("uploadAvtBtn").addEventListener("click", function () {
    document.getElementById("uploadAvtFile").click();
});

document.getElementById("uploadAvtFile").addEventListener("change", async (event) => {
    const file = event.target.files[0];
    const url = await uploadAvatar(file);
    console.log(url);
    const uploadAvtBtn = document.getElementById("uploadAvtBtn");
    const avatar = document.getElementById("avatar");
    if (url != null) {
        showInfo("Thông báo", "Tải ảnh đại diện thành công.", context.success);
        uploadAvtBtn.innerHTML = `<img src="${baseHref}resources/images/avatar/${url}" class="rounded-circle" style="width: 100px; height: 100px;">`;
        avatar.value = url;
    } else {
        showInfo("Cảnh báo", "Tải ảnh đại diện thất bại.", context.danger);
        uploadAvtBtn.innerHTML = `<i class="bi bi-image fs-1"></i>`;
        avatar.value = "";
    }
});

