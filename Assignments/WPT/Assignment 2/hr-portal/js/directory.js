/**
 * TechNova HR Portal - Directory Script
 * Assignment 2: Employee directory with async fetch, computed search, shorthand properties, spread operator, and remove()
 */

// In-memory employee records
let employees = [];

/**
 * Loads employee records from jsonplaceholder API using fetch, async/await, and try/catch.
 * Logs response.status in the console. Shows an error on the page if the request fails.
 */
async function loadEmployees() {
    const errorDisplay = document.getElementById("directory-error");
    const statusDisplay = document.getElementById("directory-status");

    errorDisplay.textContent = "";
    statusDisplay.textContent = "Loading employees from server...";

    try {
        const response = await fetch("https://jsonplaceholder.typicode.com/users");
        // Print response.status in the console
        console.log("Response status:", response.status);

        if (!response.ok) {
            throw new Error(`Server returned HTTP ${response.status}`);
        }

        const data = await response.json();
        statusDisplay.textContent = "";

        // Standard company departments assigned across loaded users
        const departments = ["Engineering", "Human Resources", "Design", "Finance", "Marketing"];

        // Extend records using spread operator
        employees = data.map((user, idx) => {
            const department = departments[idx % departments.length];
            return {
                ...user,
                department
            };
        });

        renderEmployees(employees);

    } catch (err) {
        statusDisplay.textContent = "";
        errorDisplay.textContent = `Failed to load employees: ${err.message}`;
        console.error("Error fetching employees:", err);
    }
}

/**
 * Updates the total employees label using a template literal.
 * Must update whenever a row is added or removed.
 * @param {number} count
 */
function updateTotalCount(count = employees.length) {
    const totalEl = document.getElementById("total-count");
    if (totalEl) {
        totalEl.textContent = `Total employees: ${count}`;
    }
}

/**
 * Builds the employee table with a loop, createElement, and destructuring.
 * Attaches a Remove button to each row that calls remove().
 * @param {Array} list
 */
function renderEmployees(list) {
    const tbody = document.querySelector("#employee-table tbody");
    if (!tbody) return;

    tbody.innerHTML = "";

    list.forEach(emp => {
        // Destructuring assignment (id, name, username, email, phone)
        const { id, name, username, email, phone, department } = emp;

        const tr = document.createElement("tr");

        const tdId = document.createElement("td");
        tdId.textContent = `TN${String(id).padStart(3, "0")}`;
        tr.appendChild(tdId);

        const tdName = document.createElement("td");
        tdName.textContent = name;
        tr.appendChild(tdName);

        const tdDept = document.createElement("td");
        tdDept.textContent = department || "Engineering";
        tr.appendChild(tdDept);

        const tdUsername = document.createElement("td");
        tdUsername.textContent = username;
        tr.appendChild(tdUsername);

        const tdEmail = document.createElement("td");
        tdEmail.textContent = email;
        tr.appendChild(tdEmail);

        const tdPhone = document.createElement("td");
        tdPhone.textContent = phone;
        tr.appendChild(tdPhone);

        // Action cell with Remove button
        const tdAction = document.createElement("td");
        const removeBtn = document.createElement("button");
        removeBtn.type = "button";
        removeBtn.className = "remove-button";
        removeBtn.textContent = "Remove";

        // Remove row using remove() and update count
        removeBtn.onclick = function () {
            tr.remove(); // Deletes the row from the DOM
            employees = employees.filter(e => e.id !== id); // Remove from array
            updateTotalCount(employees.length); // Update total with template literal
        };

        tdAction.appendChild(removeBtn);
        tr.appendChild(tdAction);

        tbody.appendChild(tr);
    });

    updateTotalCount(list.length);
}

/**
 * Searches employees with choice of Name, Email or Username.
 * Keeps current search in an object built with a computed key: {[field]: text}.
 * Uses filter to show matching employees.
 */
function searchEmployees() {
    const fieldSelect = document.getElementById("search-field");
    const searchInput = document.getElementById("search-text");

    const field = fieldSelect.value; // "name", "email", or "username"
    const text = searchInput.value.toLowerCase().trim();

    // Computed key object: {[field]: text}
    const currentSearch = { [field]: text };

    const matchingEmployees = employees.filter(emp => {
        const valueToMatch = emp[field];
        if (!valueToMatch) return false;
        return String(valueToMatch).toLowerCase().includes(currentSearch[field]);
    });

    renderEmployees(matchingEmployees);
}

/**
 * Adds an employee to the directory.
 * Uses shorthand properties to create object and extends with spread operator {...employee, department}.
 * @param {Event} event
 */
function handleAddEmployee(event) {
    if (event) {
        event.preventDefault();
    }

    const nameInput = document.getElementById("new-emp-name");
    const deptSelect = document.getElementById("new-emp-dept");

    const name = nameInput.value.trim();
    const department = deptSelect.value;

    if (!name || !department) {
        alert("Please provide both name and department.");
        return false;
    }

    // Auto-generate attributes
    const nextId = employees.length > 0 ? Math.max(...employees.map(e => Number(e.id) || 0)) + 1 : 1;
    const username = name.toLowerCase().replace(/\s+/g, ".");
    const email = `${username}@technova.example`;
    const phone = "000-000-0000";

    // Shorthand properties object creation:
    const employee = { id: nextId, name, username, email, phone };

    // Extend loaded record with the spread operator: {...employee, department}
    const newEmployee = { ...employee, department };

    // Append to employees array
    employees.push(newEmployee);

    // Re-render directory
    renderEmployees(employees);

    // Reset inputs
    nameInput.value = "";
    deptSelect.value = "";

    return false;
}

// Expose symbols on window for browser scripts and automated testing
if (typeof window !== "undefined") {
    window.loadEmployees = loadEmployees;
    window.renderEmployees = renderEmployees;
    window.updateTotalCount = updateTotalCount;
    window.searchEmployees = searchEmployees;
    window.handleAddEmployee = handleAddEmployee;
    // Getter/setter for employees array to allow external access
    Object.defineProperty(window, "employees", {
        get: () => employees,
        set: (val) => { employees = val; },
        configurable: true
    });
}
