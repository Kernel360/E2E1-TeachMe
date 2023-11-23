document.getElementById("confirmPassword").addEventListener("input", function() {
    var password = document.getElementById("password").value;
    var confirmPassword = this.value;
    var passwordMatchMessage = document.getElementById("passwordMatchMessage");
    var submitButton = document.querySelector("button[type='submit']");

    if (password === confirmPassword) {
        passwordMatchMessage.textContent = "비밀번호가 일치합니다.";
        passwordMatchMessage.classList.remove("text-danger");
        passwordMatchMessage.classList.add("text-success");
        submitButton.removeAttribute("disabled");
    } else {
        passwordMatchMessage.textContent = "비밀번호가 일치하지 않습니다.";
        passwordMatchMessage.classList.remove("text-success");
        passwordMatchMessage.classList.add("text-danger");
        submitButton.setAttribute("disabled", "disabled");
    }
});

document.getElementById("username").addEventListener("input", function() {
    let username = this.value;
    let usernameMatchMessage = document.getElementById("usernameMatchMessage");
    let submit = document.querySelector("button[type='submit']");

    let regex = /^[a-zA-Z0-9@_-]+$/;

    if(!regex.test(username)) {
        usernameMatchMessage.textContent = "ID는 영문, 숫자, @, _, - 만 포함할 수 있습니다.";
        usernameMatchMessage.classList.remove("text-success");
        usernameMatchMessage.classList.add("text-danger");
        submit.setAttribute("disabled", "disabled");
    } else {
        usernameMatchMessage.textContent = "ID가 조건을 만족합니다";
        usernameMatchMessage.classList.remove("text-danger");
        usernameMatchMessage.classList.add("text-success");
        submit.removeAttribute("disabled");
    }
})

const submitButton = document.getElementById("submit_button");

document.getElementById("join-form").addEventListener("submit", function() {
    submitButton.disabled = true;
});