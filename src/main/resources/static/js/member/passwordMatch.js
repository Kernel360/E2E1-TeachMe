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

const submitButton = document.getElementById("submit_button");

document.getElementById("join-form").addEventListener("submit", function() {
    submitButton.disabled = true;
});