
document.addEventListener("DOMContentLoaded", function () {
    const provinceId = document.getElementById("provinceId");
    console.log(provinceId);
    if (provinceId != null) {
        provinceId.addEventListener("change", function () {
            const districtId = document.getElementById("districtId");
            const wardId = document.getElementById("wardId");
            if (provinceId.value != "") {
                console.log(provinceId.value);
                const districtURL = baseHref + "api/location/districts/" + provinceId.value;
                fetch(districtURL)
                    .then(response => response.json())
                    .then(data => {
                        districtId.innerHTML = "<option value=''>Chọn quận/huyện</option>";
                        wardId.innerHTML = "<option value=''>Chọn phường/xã</option>";
                        data.forEach(district => {
                            districtId.innerHTML += `<option value="${district.id}">${district.name}</option>`;
                        });
                        
                    });
                districtId.addEventListener("change", function () {
                    if (districtId.value != "") {
                        const wardURL = baseHref + "api/location/wards/" + districtId.value;
                        fetch(wardURL)
                            .then(response => response.json())
                            .then(data => {
                                wardId.innerHTML = "<option value=''>Chọn phường/xã</option>";
                                data.forEach(ward => {
                                    wardId.innerHTML += `<option value="${ward.id}">${ward.name}</option>`;
                                });
                            });
                    } else {
                        wardId.innerHTML = "<option value=''>Chọn phường/xã</option>";
                    }
                });
                
            } else {
                districtId.innerHTML = "<option value=''>Chọn quận/huyện</option>";
                wardId.innerHTML = "<option value=''>Chọn phường/xã</option>";
            }
        });
    }
});

function updateFullAddress() {
    const provinceId = document.getElementById("provinceId");
    const districtId = document.getElementById("districtId");
    const wardId = document.getElementById("wardId");
    const detailAddress = document.getElementById("detailAddress");
    const fullAddress = document.getElementById("fullAddress");
    fullAddress.value = `${detailAddress.value}, ${wardId.options[wardId.selectedIndex].text}, ${districtId.options[districtId.selectedIndex].text}, ${provinceId.options[provinceId.selectedIndex].text}`;
}

document.addEventListener("DOMContentLoaded", function () {
    const detailAddress = document.getElementById("detailAddress");
    if (detailAddress != null) {
        detailAddress.addEventListener("input", updateFullAddress);
    }
    const wardId = document.getElementById("wardId");
    if (wardId != null) {
        wardId.addEventListener("change", updateFullAddress);
    }
    const districtId = document.getElementById("districtId");
    if (districtId != null) {
        districtId.addEventListener("change", updateFullAddress);
    }
    const provinceId = document.getElementById("provinceId");
    if (provinceId != null) {
        provinceId.addEventListener("change", updateFullAddress);
    }
});