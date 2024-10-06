
document.addEventListener("DOMContentLoaded", function () {
    const provinceID = document.getElementById("provinceID");
    console.log(provinceID);
    if (provinceID != null) {
        provinceID.addEventListener("change", function () {
            const districtID = document.getElementById("districtID");
            const wardID = document.getElementById("wardID");
            if (provinceID.value != "") {
                console.log(provinceID.value);
                const districtURL = baseHref + "api/location/districts/" + provinceID.value;
                fetch(districtURL)
                    .then(response => response.json())
                    .then(data => {
                        districtID.innerHTML = "<option value=''>Chọn quận/huyện</option>";
                        wardID.innerHTML = "<option value=''>Chọn phường/xã</option>";
                        data.forEach(district => {
                            districtID.innerHTML += `<option value="${district.id}">${district.name}</option>`;
                        });
                        
                    });
                districtID.addEventListener("change", function () {
                    if (districtID.value != "") {
                        const wardURL = baseHref + "api/location/wards/" + districtID.value;
                        fetch(wardURL)
                            .then(response => response.json())
                            .then(data => {
                                wardID.innerHTML = "<option value=''>Chọn phường/xã</option>";
                                data.forEach(ward => {
                                    wardID.innerHTML += `<option value="${ward.id}">${ward.name}</option>`;
                                });
                            });
                    } else {
                        wardID.innerHTML = "<option value=''>Chọn phường/xã</option>";
                    }
                });
                
            } else {
                districtID.innerHTML = "<option value=''>Chọn quận/huyện</option>";
                wardID.innerHTML = "<option value=''>Chọn phường/xã</option>";
            }
        });
    }
});

function updateFullAddress() {
    const provinceID = document.getElementById("provinceID");
    const districtID = document.getElementById("districtID");
    const wardID = document.getElementById("wardID");
    const detailAddress = document.getElementById("detailAddress");
    const fullAddress = document.getElementById("fullAddress");
    fullAddress.value = `${detailAddress.value}, ${wardID.options[wardID.selectedIndex].text}, ${districtID.options[districtID.selectedIndex].text}, ${provinceID.options[provinceID.selectedIndex].text}`;
}

document.addEventListener("DOMContentLoaded", function () {
    const detailAddress = document.getElementById("detailAddress");
    if (detailAddress != null) {
        detailAddress.addEventListener("input", updateFullAddress);
    }
    const wardID = document.getElementById("wardID");
    if (wardID != null) {
        wardID.addEventListener("change", updateFullAddress);
    }
    const districtID = document.getElementById("districtID");
    if (districtID != null) {
        districtID.addEventListener("change", updateFullAddress);
    }
    const provinceID = document.getElementById("provinceID");
    if (provinceID != null) {
        provinceID.addEventListener("change", updateFullAddress);
    }
});