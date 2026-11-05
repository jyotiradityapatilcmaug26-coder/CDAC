/**
 * TechNova HR Portal - Onboarding Script
 * Assignment 2: Form validation & asynchronous onboarding promise chain with .then() and .catch()
 */

// Email regex validation
const OFFICIAL_EMAIL_REGEX = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

/**
 * Validates the full name field.
 * Name is required and must not be empty.
 * @returns {boolean}
 */
function validateFullName() {
    const nameInput = document.getElementById("full-name");
    const errorSpan = document.getElementById("name-error");
    const val = nameInput.value.trim();

    if (!val) {
        errorSpan.textContent = "Full name is required.";
        return false;
    } else {
        errorSpan.textContent = "";
        return true;
    }
}

/**
 * Validates the official email using regex.
 * @returns {boolean}
 */
function validateOfficialEmail() {
    const emailInput = document.getElementById("official-email");
    const errorSpan = document.getElementById("email-error");
    const val = emailInput.value.trim();

    if (!val) {
        errorSpan.textContent = "Official email is required.";
        return false;
    } else if (!OFFICIAL_EMAIL_REGEX.test(val)) {
        errorSpan.textContent = "Please enter a valid official email address.";
        return false;
    } else {
        errorSpan.textContent = "";
        return true;
    }
}

/**
 * Validates the qualification dropdown.
 * Must be chosen, not left on the empty option.
 * @returns {boolean}
 */
function validateQualification() {
    const qualSelect = document.getElementById("qualification");
    const errorSpan = document.getElementById("qual-error");
    const val = qualSelect.value;

    if (!val) {
        errorSpan.textContent = "Highest qualification must be chosen.";
        return false;
    } else {
        errorSpan.textContent = "";
        return true;
    }
}

/**
 * Validates the department dropdown.
 * Must be chosen, not left on the empty option.
 * @returns {boolean}
 */
function validateDepartment() {
    const deptSelect = document.getElementById("department");
    const errorSpan = document.getElementById("dept-error");
    const val = deptSelect.value;

    if (!val) {
        errorSpan.textContent = "Department must be chosen.";
        return false;
    } else {
        errorSpan.textContent = "";
        return true;
    }
}

/**
 * Validates all required form inputs.
 * @returns {boolean}
 */
function validateForm() {
    const isNameOk = validateFullName();
    const isEmailOk = validateOfficialEmail();
    const isQualOk = validateQualification();
    const isDeptOk = validateDepartment();

    return isNameOk && isEmailOk && isQualOk && isDeptOk;
}

/**
 * Updates the user-facing status message banner.
 * @param {string} msg
 * @param {boolean} isSuccess
 */
function updateStatus(msg, isSuccess = false) {
    const statusBox = document.getElementById("onboarding-status");
    if (statusBox) {
        statusBox.style.display = "block";
        statusBox.textContent = msg;
        if (isSuccess) {
            statusBox.style.backgroundColor = "#ecfdf5";
            statusBox.style.color = "#047857";
            statusBox.style.borderColor = "#10b981";
        } else {
            statusBox.style.backgroundColor = "#eff6ff";
            statusBox.style.color = "#1d4ed8";
            statusBox.style.borderColor = "#93c5fd";
        }
    }
}

// ====================================================
// Onboarding steps as Promise-returning functions
// ====================================================

/**
 * Step 1: Creates account.
 * Rejects if name is shorter than 3 letters.
 * @param {string} name
 * @returns {Promise<string>}
 */
function createAccount(name) {
    return new Promise((resolve, reject) => {
        updateStatus("Creating your account...");
        setTimeout(() => {
            if (name.length < 3) {
                reject("Account creation rejected: Name must be at least 3 letters long.");
            } else {
                resolve(name);
            }
        }, 1000); // 1 second
    });
}

/**
 * Step 2: Sends welcome email (2 seconds)
 * @param {string} name
 * @returns {Promise<string>}
 */
function sendWelcomeEmail(name) {
    return new Promise((resolve) => {
        updateStatus("Sending welcome email...");
        setTimeout(() => {
            resolve(name);
        }, 2000); // 2 seconds
    });
}

/**
 * Step 3: Assigns laptop (1.5 seconds)
 * @param {string} name
 * @returns {Promise<string>}
 */
function assignLaptop(name) {
    return new Promise((resolve) => {
        updateStatus("Assigning your laptop...");
        setTimeout(() => {
            resolve(name);
        }, 1500); // 1.5 seconds
    });
}

/**
 * Step 4: Welcomes new hire aboard
 * @param {string} name
 * @returns {Promise<string>}
 */
function welcomeAboard(name) {
    return new Promise((resolve) => {
        const message = `Welcome aboard, ${name}!`;
        updateStatus(message, true);
        resolve(message);
    });
}

/**
 * Handles the onboarding submission.
 * Validates fields, then chains steps with .then() and catches errors with .catch().
 * Returns false so the page does not reload.
 * @param {Event} event
 * @returns {boolean}
 */
function handleOnboardingSubmit(event) {
    if (event) {
        event.preventDefault();
    }

    const errorBox = document.getElementById("onboarding-error");
    if (errorBox) {
        errorBox.textContent = "";
    }

    // Check all fields on submit
    if (!validateForm()) {
        return false;
    }

    const name = document.getElementById("full-name").value.trim();

    // Disable submit button during processing
    const submitBtn = document.getElementById("onboarding-submit-btn");
    if (submitBtn) submitBtn.disabled = true;

    // Chain onboarding promises with .then()
    createAccount(name)
        .then(validatedName => {
            return sendWelcomeEmail(validatedName);
        })
        .then(validatedName => {
            return assignLaptop(validatedName);
        })
        .then(validatedName => {
            return welcomeAboard(validatedName);
        })
        .catch(err => {
            // Rejection handler
            const statusBox = document.getElementById("onboarding-status");
            if (statusBox) statusBox.style.display = "none";

            if (errorBox) {
                errorBox.style.display = "block";
                errorBox.textContent = `Onboarding Failed: ${err}`;
            }
            console.error("Onboarding step failed:", err);
        })
        .finally(() => {
            if (submitBtn) submitBtn.disabled = false;
        });

    // Return false from the submit handler so that the page does not reload
    return false;
}
