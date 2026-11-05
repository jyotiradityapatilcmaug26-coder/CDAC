/**
 * Jyotiraditya Patil - Portfolio Contact Form Validation
 * Assignment 2: Contact form validation with regex, onkeyup, and onsubmit
 */

// Regular expressions
const EMAIL_REGEX = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const PHONE_REGEX = /^\d{10}$/; // Exactly 10 digits

/**
 * Validates the visitor's name.
 * Name is required.
 * @returns {boolean}
 */
function validateName() {
    const nameInput = document.getElementById("name");
    const errorSpan = document.getElementById("name-error");
    const value = nameInput.value.trim();

    if (!value) {
        errorSpan.textContent = "Full name is required.";
        return false;
    } else {
        errorSpan.textContent = "";
        return true;
    }
}

/**
 * Validates the visitor's email using a regular expression.
 * @returns {boolean}
 */
function validateEmail() {
    const emailInput = document.getElementById("email");
    const errorSpan = document.getElementById("email-error");
    const value = emailInput.value.trim();

    if (!value) {
        errorSpan.textContent = "Email address is required.";
        return false;
    } else if (!EMAIL_REGEX.test(value)) {
        errorSpan.textContent = "Please enter a valid email address (e.g., you@example.com).";
        return false;
    } else {
        errorSpan.textContent = "";
        return true;
    }
}

/**
 * Validates the phone number.
 * Must be exactly 10 digits.
 * @returns {boolean}
 */
function validatePhone() {
    const phoneInput = document.getElementById("phone");
    const errorSpan = document.getElementById("phone-error");
    const value = phoneInput.value.trim();

    if (!value) {
        errorSpan.textContent = "Phone number is required.";
        return false;
    } else if (!PHONE_REGEX.test(value)) {
        errorSpan.textContent = "Phone number must be exactly 10 digits.";
        return false;
    } else {
        errorSpan.textContent = "";
        return true;
    }
}

/**
 * Validates the message.
 * Must be at least 20 characters long.
 * @returns {boolean}
 */
function validateMessage() {
    const messageInput = document.getElementById("message");
    const errorSpan = document.getElementById("message-error");
    const value = messageInput.value.trim();

    if (!value) {
        errorSpan.textContent = "Message cannot be empty.";
        return false;
    } else if (value.length < 20) {
        errorSpan.textContent = `Message must be at least 20 characters (current: ${value.length}).`;
        return false;
    } else {
        errorSpan.textContent = "";
        return true;
    }
}

/**
 * Main validation function invoked on form submit: onsubmit="return validate()"
 * Checks all fields; if any field is invalid, prevents submission.
 * If all valid, displays a personalized thank-you message with visitor's name.
 * @returns {boolean}
 */
function validate() {
    const isNameValid = validateName();
    const isEmailValid = validateEmail();
    const isPhoneValid = validatePhone();
    const isMessageValid = validateMessage();

    // Check all fields
    const allValid = isNameValid && isEmailValid && isPhoneValid && isMessageValid;

    if (!allValid) {
        return false; // Prevents form submission
    }

    // Success handling: show thank-you message with visitor's name
    const visitorName = document.getElementById("name").value.trim();
    const formElement = document.getElementById("contact-form");
    const successBanner = document.getElementById("thank-you-box");

    if (successBanner) {
        successBanner.style.display = "block";
        successBanner.innerHTML = `
            <h3>Thank you, ${visitorName}!</h3>
            <p>Your message has been received successfully. I will get back to you shortly at the provided email.</p>
        `;
    }

    // Reset form fields
    formElement.reset();

    // Clear error spans
    document.querySelectorAll(".error-span").forEach(el => el.textContent = "");

    // Return false to prevent page reload and keep the thank-you message visible
    return false;
}
