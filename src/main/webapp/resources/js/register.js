"use strict";
const invalidChars = /[!@#$%^&*()_+=\[\]{}|,;:'"<>?/\\~`]/;
const emailPattern = /^(?=.{1,64}@)[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]{2,})$/;
const phonePattern = /([+84|84|0]+(3|5|7|8|9|1[2|6|8|9]))+([0-9]{8})\b/;


function showInfoMessages(inputId, errorMessage, context) {
    // check error field of spring validation then remove it
    let springErrorField = document.getElementById(`${inputId}.errors`);
    if (springErrorField !== null) {
        springErrorField.remove();
    }
    // check if error field already exists then add error message, then create error field
    let infoField = document.getElementById(`${inputId}Info`);
    if (infoField === null) {
        infoField = document.createElement("div");
        infoField.id = `${inputId}Info`;
        document.getElementById(inputId).after(infoField);
    }

    let inputField = document.getElementById(inputId);
    if (context === "success") {
        inputField.classList.remove("is-invalid");
        inputField.classList.add("is-valid");
    } else {
        inputField.classList.remove("is-valid");
        inputField.classList.add("is-invalid");
    }

    // remove all classes of infoField
    infoField.className = "roboto-serif-medium p-1";
    if (context === undefined) {
        context = "danger";
    }
    infoField.classList.add("text-" + context);
    infoField.innerHTML = errorMessage;
}



// 1.th group: validate name

function validateFirstName() {
    let firstName = document.getElementById("firstName");
    if (firstName === null) {
        return true;
    }
    if (firstName.value.trim() === "") {
        showInfoMessages("firstName", "Họ không được để trống");
        return false;
    }
    if (firstName.value.trim().length < 2) {
        showInfoMessages("firstName", "Họ phải có ít nhất 2 ký tự");
        return false;
    }
    if (invalidChars.test(firstName.value)) {
        showInfoMessages("firstName", "Họ không được chứa ký tự đặc biệt");
        return false;
    }
    showInfoMessages("firstName", "Họ hơp lệ", "success");

    return true;
}


function validateLastName() {
    let lastName = document.getElementById("lastName");
    if (lastName === null) {
        return true;
    }
    if (lastName.value.trim() === "") {
        showInfoMessages("lastName", "Tên không được để trống");
        return false;
    }
    if (lastName.value.trim().length < 2) {
        showInfoMessages("lastName", "Tên phải có ít nhất 2 ký tự");
        return false;
    }
    if (invalidChars.test(lastName.value)) {
        showInfoMessages("lastName", "Tên không được chứa ký tự đặc biệt");
        return false;
    }
    showInfoMessages("lastName", "Tên hợp lệ", "success");

    return true;
}

try {
    document.getElementById("firstName").addEventListener("blur", validateFirstName);
    document.getElementById("firstName").addEventListener("input", validateFirstName);
    document.getElementById("lastName").addEventListener("blur", validateLastName);
    document.getElementById("lastName").addEventListener("input", validateLastName);
} catch (e) {
}


// 2.th group: validate birthday gender

function validateBirthday() {
    let birthday = document.getElementById("birthday");
    if (birthday === null) {
        return true;
    }
    let birthdayValue = new Date(birthday.value);
    let currentDate = new Date();
    let age = currentDate.getFullYear() - birthdayValue.getFullYear();
    if(currentDate.getMonth() < birthdayValue.getMonth() || (currentDate.getMonth() === birthdayValue.getMonth() && currentDate.getDate() < birthdayValue.getDate())) {
        age--;
    }
    if (age < 12) {
        showInfoMessages("birthday", "Tuổi phải lớn hơn hoặc bằng 12");
        return false;
    }
    if (age > 125) {
        showInfoMessages("birthday", `${age} tuổi? bạn chắc chắn?`);
        return false;
    }
    if (isNaN(age)) {
        showInfoMessages("birthday", "Ngày sinh không hợp lệ");
        return false;
    }
    showInfoMessages("birthday", "Ngày sinh hợp lệ", "success");
    return true;
}

function validateGender() {
    let gender = document.getElementById("gender");
    if (gender === null) {
        return true;
    }
    if (!gender.value) {
        showInfoMessages("gender", "Vui lòng chọn giới tính");
        return false;
    }
    showInfoMessages("gender", "Giới tính hợp lệ", "success");
    if(gender.value === "Khác") {
        let otherGenderDiv = document.getElementById("otherGenderDiv");
        otherGenderDiv.classList.remove("d-none");
    } else {
        let otherGenderDiv = document.getElementById("otherGenderDiv");
        otherGenderDiv.classList.add("d-none");
    }
    return true;
}

function validateOtherGender() {
    let otherGender = document.getElementById("otherGender");
    let gender = document.getElementById("gender");
    if (gender.value !== "Khác") {
        return true;
    }
    if (otherGender === null) {
        return true;
    }
    if (otherGender.value.trim() === "") {
        showInfoMessages("otherGender", "Vui lòng nhập giới tính khác");
        return false;
    }
    if (otherGender.value.trim().length > 20) {
        showInfoMessages("otherGender", "Giới tính không được quá 20 ký tự");
        return false;
    }

    if (invalidChars.test(otherGender.value)) {
        showInfoMessages("otherGender", "Giới tính không được chứa ký tự đặc biệt");
        return false;
    }
    showInfoMessages("otherGender", "Giới tính hợp lệ", "success");
    return true;
}



try {
    document.getElementById("birthday").addEventListener("blur", validateBirthday);
    document.getElementById("birthday").addEventListener("input", validateBirthday);
    document.getElementById("gender").addEventListener("blur", validateGender);
    document.getElementById("gender").addEventListener("change", validateGender);
    document.getElementById("otherGender").addEventListener("blur", validateOtherGender);
    document.getElementById("otherGender").addEventListener("input", validateOtherGender);
} catch (e) {
}

const baseHref = document.getElementsByTagName("base")[0].href;

// 3.th group: validate username password
function isExistUsername(username) {
    return $.ajax({
        type: "GET",
        Credentials: "same-origin",
        url: `${baseHref}api/user/check-username?username=${username}`,
    });
}

function debounce(func, delay) {
    let timeOut;
    return function (...args) {
        clearTimeout(timeOut);
        timeOut = setTimeout(() => {
            func.apply(this, args);
        }, delay);
    }
}


function checkUsernameExist() {
    let isExist = isExistUsername(document.getElementById("username").value);
    isExist.done(function (data) {
        if (data) {
            showInfoMessages("username", "Tên đăng nhập đã tồn tại");
            return false;
        } else {
            showInfoMessages("username", "Tên đăng nhập hợp lệ", "success");
            return true;
        }
    });
}

const debouncedCheckUsernameExist = debounce(checkUsernameExist, 500);


function validateUsername() {
    let username = document.getElementById("username");
    if (username === null) {
        return true;
    }
    if (username.value.trim() === "") {
        showInfoMessages("username", "Tên đăng nhập không được để trống");
        return false;
    }
    if (username.value.trim().length < 6) {
        showInfoMessages("username", "Tên đăng nhập phải có ít nhất 6 ký tự");
        return false;
    }
    if (invalidChars.test(username.value)) {
        showInfoMessages("username", "Tên đăng nhập không được chứa ký tự đặc biệt");
        return false;
    }
    return true;
}

function calculatePasswordStrength(password) {
    const lengthWeight = 0.2;
    const uppercaseWeight = 0.5;
    const lowercaseWeight = 0.5;
    const numberWeight = 0.7;
    const symbolWeight = 1;

    let strength = 0;

    strength += password.length * lengthWeight;

    const charCount = document.getElementById('charCount');
    charCount.textContent = password.length + ' kí tự';

    const uppercase = document.getElementById('uppercase');
    if (/[A-Z]/.test(password)) {
        uppercase.classList.add('text-success');
        uppercase.classList.remove('text-danger');
        strength += uppercaseWeight;
    } else {
        uppercase.classList.remove('text-success');
        uppercase.classList.add('text-danger');
        strength -= uppercaseWeight;
    }

    const lowercase = document.getElementById('lowercase');
    if (/[a-z]/.test(password)) {
        lowercase.classList.add('text-success');
        lowercase.classList.remove('text-danger');
        strength += lowercaseWeight;
    } else {
        lowercase.classList.remove('text-success');
        lowercase.classList.add('text-danger');
        strength -= lowercaseWeight;
    }

    const number = document.getElementById('number');
    if (/\d/.test(password)) {
        strength += numberWeight;
        number.classList.add('text-success');
        number.classList.remove('text-danger');
    } else {
        number.classList.remove('text-success');
        number.classList.add('text-danger');
        strength -= numberWeight;
    }

    const symbol = document.getElementById('symbol');
    if (/[^A-Za-z0-9]/.test(password)) {
        symbol.classList.add('text-success');
        symbol.classList.remove('text-danger');
        strength += symbolWeight;
    } else {
        symbol.classList.remove('text-success');
        symbol.classList.add('text-danger');
        strength -= symbolWeight;
    }

    if (password.length < 8) {
        strength = 0;
        charCount.classList.add('text-danger');
        charCount.classList.remove('text-success');
    } else {
        charCount.classList.remove('text-danger');
        charCount.classList.add('text-success');
    }
    return strength;
}

function updateMeter() {
    const passwordInput = document.getElementById('password');
    const meterSections = document.querySelectorAll('.password-meter .meter-section');
    let strength = calculatePasswordStrength(passwordInput.value);

    // Remove all strength classes
    meterSections.forEach((section) => {
        section.classList.remove('weak', 'medium', 'strong', 'very-strong');
    });

    // Add the appropriate strength class based on the strength value
    if (strength >= 1) {
        meterSections[0].classList.add('weak');
    }
    if (strength >= 2) {
        meterSections[1].classList.add('medium');
    }
    if (strength >= 3) {
        meterSections[2].classList.add('strong');
    }
    if (strength >= 4) {
        meterSections[3].classList.add('very-strong');
    }
    return strength;
}


function validatePassword() {
    let password = document.getElementById("password");
    if (password === null) {
        return true;
    }
    let strength = updateMeter();
    if (strength < 1) {
        showInfoMessages("password", "Mật khẩu quá yếu");
        return false;
    }
    if (strength < 2) {
        showInfoMessages("password", "Mật khẩu yếu");
        return false;
    }
    if (strength < 3) {
        showInfoMessages("password", "Mật khẩu trung bình", "info");
        return true;
    }
    if (strength < 4) {
        showInfoMessages("password", "Mật khẩu mạnh", "success");
        return true;
    }
    else {
        showInfoMessages("password", "Mật khẩu rất mạnh", "success");
        return true;
    }
}

function validateRePassword() {
    let password = document.getElementById("password");
    let rePassword = document.getElementById("rePassword");
    if (rePassword === null) {
        return true;
    }
    if (rePassword.value !== password.value) {
        showInfoMessages("rePassword", "Mật khẩu không khớp");
        return false;
    }
    showInfoMessages("rePassword", "Mật khẩu khớp", "success");
    return true;
}


try {
    document.getElementById("username").addEventListener("blur", function () {
        if (validateUsername()) {
            debouncedCheckUsernameExist();
        }
    });
    document.getElementById("username").addEventListener("input", function () {
        if (validateUsername()) {
            debouncedCheckUsernameExist();
        }
    });
    document.getElementById("password").addEventListener("blur", validatePassword);
    document.getElementById("password").addEventListener("input", validatePassword);
    document.getElementById("rePassword").addEventListener("blur", validateRePassword);
    document.getElementById("rePassword").addEventListener("input", validateRePassword);
} catch (e) {

}


// 4.th group: validate phone email

function isExistPhone(phone) {
    return $.ajax({
        type: "GET",
        Credentials: "same-origin",
        url: `${baseHref}api/user/check-phone?phone=${phone}`,
    });
}

function checkPhoneExist() {
    let isExist = isExistPhone(document.getElementById("phone").value);
    isExist.done(function (data) {
        if (data) {
            showInfoMessages("phone", "Số điện thoại đã tồn tại");
            return false;
        } else {
            showInfoMessages("phone", "Số điện thoại hợp lệ", "success");
            return true;
        }
    }
    );
}

const debouncedCheckPhoneExist = debounce(checkPhoneExist, 500);


function validatePhone() {
    let phone = document.getElementById("phone");
    if (phone === null) {
        return true;
    }
    if (phone.value.trim() === "") {
        showInfoMessages("phone", "Số điện thoại không được để trống");
        return false;
    }
    if (!phonePattern.test(phone.value)) {
        showInfoMessages("phone", "Số điện thoại không hợp lệ");
        return false;
    }
    showInfoMessages("phone", "Số điện thoại hợp lệ", "success");
    return true;
}


function isExistEmail(email) {
    return $.ajax({
        type: "GET",
        Credentials: "same-origin",
        url: `${baseHref}api/user/check-email?email=${email}`,
    });
}


function checkEmailExist() {
    let isExist = isExistEmail(document.getElementById("email").value);
    isExist.done(function (data) {
        if (data) {
            showInfoMessages("email", "Email đã tồn tại");
            return false;
        } else {
            showInfoMessages("email", "Email hợp lệ", "success");
            return true;
        }
    }
    );
}

const debouncedCheckEmailExist = debounce(checkEmailExist, 500);



function validateEmail() {
    let email = document.getElementById("email");
    if (email === null) {
        return true;
    }
    if (email.value.trim() === "") {
        showInfoMessages("email", "Email không được để trống");
        return false;
    }
    if (!emailPattern.test(email.value)) {
        showInfoMessages("email", "Email không hợp lệ");
        return false;
    }
    showInfoMessages("email", "Email hợp lệ", "success");
    return true;
}


try {
    document.getElementById("phone").addEventListener("blur", function () {
        if (validatePhone()) {
            debouncedCheckPhoneExist();
        }
    }
    );
    document.getElementById("phone").addEventListener("input", function () {
        if (validatePhone()) {
            debouncedCheckPhoneExist();
        }
    }
    );
    document.getElementById("email").addEventListener("blur", function () {
        if (validateEmail()) {
            debouncedCheckEmailExist();
        }
    }
    );
    document.getElementById("email").addEventListener("input", function () {
        if (validateEmail()) {
            debouncedCheckEmailExist();
        }
    }
    );
} catch (e) {

}





function validateAll() {
    return validateFirstName() 
    && validateLastName() 
    && validateBirthday() 
    && validateGender() 
    && validateOtherGender()
    && validateUsername()
    && validatePassword()
    && validateRePassword()
    && validatePhone()
    && validateEmail();

    // return true;
}

const animationList = ["animate__pulse", 
    "animate__rubberBand",
    "animate__tada",
    "animate__heartBeat",
    "animate__backInDown",
    "animate__backInUp",
    "animate__bounceIn",
    "animate__bounceInDown",
    "animate__bounceInUp",
    "animate__fadeIn",
    "animate__fadeInDown",
    "animate__fadeInUp",
    "animate__flipInX",
    "animate__flipInY",
    "animate__zoomIn",
    "animate__zoomInDown",
    "animate__zoomInUp"
]

function randomAnimation() {
    return animationList[Math.floor(Math.random() * animationList.length)];
}


document.addEventListener("DOMContentLoaded", function () {
    addAnimation("main", randomAnimation(), 2);
});

document.getElementById("submitBtn").addEventListener("click", function () {
    let form = document.getElementById("regForm");
    if (!form.checkValidity()) {
        validateAll();
        addAnimation("regForm", "animate__shakeX", 0.5);
    }
});


document.getElementById("regForm").addEventListener("submit", function (event) {
    if (!validateAll()) {
        event.preventDefault();
        addAnimation("regForm", "animate__shakeX", 0.5);
    }
});

// add animation for x seconds
function addAnimation(formId, animation_name, duration) {
    document.getElementById(formId).className = "animate__animated " + animation_name;
    setTimeout(function () {
        document.getElementById(formId).className = "";
    }, duration * 1000);
}