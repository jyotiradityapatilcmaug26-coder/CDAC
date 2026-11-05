/**
 * TechNova HR Portal - Login Script
 * Assignment 2: Show/hide password and login validation
 */

// Password regular expression:
// At least 8 characters, at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character
const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,}$/;

/**
 * Toggles password visibility using setAttribute between type="password" and type="text".
 * Switches button text between "Show" and "Hide".
 */
function togglePasswordVisibility() {
    const passwordInput = document.getElementById("password");
    const toggleBtn = document.getElementById("toggle-password-btn");
    const currentType = passwordInput.getAttribute("type");

    if (currentType === "password") {
        passwordInput.setAttribute("type", "text");
        toggleBtn.textContent = "Hide";
    } else {
        passwordInput.setAttribute("type", "password");
        toggleBtn.textContent = "Show";
    }
}

/**
 * Validates the username.
 * Username is required.
 * @returns {boolean}
 */
function validateUsername() {
    const usernameInput = document.getElementById("username");
    const errorSpan = document.getElementById("username-error");
    const value = usernameInput.value.trim();

    if (!value) {
        errorSpan.textContent = "Username is required.";
        return false;
    } else {
        errorSpan.textContent = "";
        return true;
    }
}

/**
 * Validates password complexity against the regular expression.
 * Checked onkeyup while the user types and on submit.
 * @returns {boolean}
 */
function validatePassword() {
    const passwordInput = document.getElementById("password");
    const errorSpan = document.getElementById("password-error");
    const value = passwordInput.value;

    if (!value) {
        errorSpan.textContent = "Password is required.";
        return false;
    } else if (!PASSWORD_REGEX.test(value)) {
        errorSpan.textContent = "Must be at least 8 chars with 1 uppercase, 1 lowercase, 1 number, and 1 special character.";
        return false;
    } else {
        errorSpan.textContent = "";
        return true;
    }
}

/**
 * Validates all fields on form submission: onsubmit="return validate()"
 * @returns {boolean}
 */
function validate() {
    const isUserValid = validateUsername();
    const isPassValid = validatePassword();
    const successBox = document.getElementById("login-success");

    if (isUserValid && isPassValid) {
        if (successBox) {
            successBox.style.display = "block";
            successBox.textContent = `Welcome back, ${document.getElementById("username").value.trim()}! Login successful.`;
        }
        return false; // Prevent actual navigation for demo testing
    }

    if (successBox) {
        successBox.style.display = "none";
    }
    return false;
}
