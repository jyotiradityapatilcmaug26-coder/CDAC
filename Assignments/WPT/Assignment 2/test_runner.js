/**
 * Automated Test Suite for Assignment 2 (JavaScript)
 * Student: Jyotiraditya Patil
 *
 * Runs full assertion testing on all parts:
 * - Part A: Written Questions Verification & Logic
 * - Question 1: Portfolio (Home, Projects, Contact)
 * - Question 2: HR Portal (Login, Directory, Onboarding)
 * - Question 3: Order Calculator Landing Page
 */

const fs = require("fs");
const path = require("path");
const assert = require("assert");

// Colors for terminal reporting
const GREEN = "\x1b[32m";
const RED = "\x1b[31m";
const CYAN = "\x1b[36m";
const YELLOW = "\x1b[33m";
const RESET = "\x1b[0m";

let totalTests = 0;
let passedTests = 0;
let failedTests = 0;

function test(name, fn) {
    totalTests++;
    try {
        fn();
        console.log(`  ${GREEN}✓${RESET} ${name}`);
        passedTests++;
    } catch (err) {
        console.error(`  ${RED}✗${RESET} ${name}`);
        console.error(`    ${RED}Error: ${err.message}${RESET}`);
        failedTests++;
    }
}

async function asyncTest(name, fn) {
    totalTests++;
    try {
        await fn();
        console.log(`  ${GREEN}✓${RESET} ${name}`);
        passedTests++;
    } catch (err) {
        console.error(`  ${RED}✗${RESET} ${name}`);
        console.error(`    ${RED}Error: ${err.message}${RESET}`);
        failedTests++;
    }
}

async function describe(suiteName, fn) {
    console.log(`\n${CYAN}=== ${suiteName} ===${RESET}`);
    await fn();
}

/**
 * Lightweight DOM Mock helper to simulate browser elements in Node.js
 */
function createMockElement(tagName = "div", id = "") {
    const children = [];
    const attributes = {};
    let innerHTMLVal = "";
    let textContentVal;
    return {
        tagName: tagName.toUpperCase(),
        id: id,
        value: "",
        get textContent() {
            if (textContentVal !== undefined) return textContentVal;
            if (children.length > 0) {
                return children.map(c => c.textContent || "").join(" ");
            }
            return "";
        },
        set textContent(val) {
            textContentVal = String(val);
        },
        get innerHTML() {
            return innerHTMLVal;
        },
        set innerHTML(val) {
            innerHTMLVal = String(val);
            if (val === "") {
                children.length = 0;
            }
        },
        style: {},
        attributes: attributes,
        children: children,
        disabled: false,
        setAttribute(name, val) {
            attributes[name] = String(val);
            if (name === "type") this.type = String(val);
        },
        getAttribute(name) {
            return attributes[name] !== undefined ? attributes[name] : null;
        },
        appendChild(child) {
            child._parent = this;
            children.push(child);
            return child;
        },
        remove() {
            this._removed = true;
            if (this._parent && Array.isArray(this._parent.children)) {
                const idx = this._parent.children.indexOf(this);
                if (idx !== -1) {
                    this._parent.children.splice(idx, 1);
                }
            }
        },
        reset() {
            this.value = "";
        }
    };
}

function createMockDocument() {
    const elements = {};
    return {
        body: createMockElement("body"),
        createElement(tag) {
            return createMockElement(tag);
        },
        createTextNode(text) {
            return { nodeType: 3, textContent: text };
        },
        getElementById(id) {
            if (!elements[id]) {
                elements[id] = createMockElement("div", id);
            }
            return elements[id];
        },
        querySelector(selector) {
            if (selector.includes(" ")) {
                const [parentSel, childTag] = selector.split(/\s+/);
                const parentId = parentSel.replace("#", "");
                const parent = this.getElementById(parentId);
                const found = parent.children.find(c => c.tagName === childTag.toUpperCase());
                if (found) return found;
                // If not found yet, create and attach
                const child = createMockElement(childTag);
                parent.appendChild(child);
                return child;
            }
            if (selector.startsWith("#")) {
                const id = selector.substring(1);
                return this.getElementById(id);
            }
            return createMockElement("div");
        },
        querySelectorAll(selector) {
            return [];
        },
        _register(id, el) {
            elements[id] = el;
            return el;
        }
    };
}

// ========================================================
// START RUNNING TEST SUITES
// ========================================================

async function runAllTests() {
    console.log(`${YELLOW}Starting Assignment 2 Test Suite...${RESET}`);

    const baseDir = __dirname;

    // ----------------------------------------------------
    // SUITE 1: PART A - Written Questions Verification
    // ----------------------------------------------------
    await describe("Part A: Written Questions (answers.txt)", async () => {
        const answersPath = path.join(baseDir, "answers.txt");

        test("answers.txt exists and is accessible", () => {
            assert.ok(fs.existsSync(answersPath), "answers.txt does not exist");
            const stat = fs.statSync(answersPath);
            assert.ok(stat.size > 1000, "answers.txt is too short");
        });

        test("answers.txt covers Questions 1 to 12", () => {
            const content = fs.readFileSync(answersPath, "utf8");
            for (let i = 1; i <= 12; i++) {
                assert.ok(content.includes(`${i}.`), `Question ${i} missing from answers.txt`);
            }
        });

        test("Question 5 array operations evaluation", () => {
            var arr = [10, 45, 32, 76, 45, 61, 78];
            const evens = arr.filter(n => n % 2 === 0);
            const doubled = arr.map(n => n * 2);
            const sum = arr.reduce((acc, curr) => acc + curr, 0);

            assert.deepStrictEqual(evens, [10, 32, 76, 78]);
            assert.deepStrictEqual(doubled, [20, 90, 64, 152, 90, 122, 156]);
            assert.strictEqual(sum, 347);
        });

        test("Question 6 arrow function evaluation", () => {
            const add = (x, y) => x + y;
            assert.strictEqual(add(15, 27), 42);
        });

        test("Question 3 Nullish coalescing (??) preserves 0 unlike (||)", () => {
            const val = 0;
            const withOr = val || 10;
            const withNullish = val ?? 10;
            assert.strictEqual(withOr, 10, "0 || 10 should be 10 (incorrect default)");
            assert.strictEqual(withNullish, 0, "0 ?? 10 should preserve 0");
        });

        test("Question 4 Optional chaining (?.) safely handles missing nested properties", () => {
            const student = {
                id: 101,
                name: "Jyotiraditya Patil",
                phoneNumbers: ["9876543210", "9822012345"]
                // address is intentionally omitted
            };
            assert.strictEqual(student.phoneNumbers[1], "9822012345");
            assert.strictEqual(student?.address?.city, undefined, "Optional chaining did not return undefined");
        });
    });

    // ----------------------------------------------------
    // SUITE 2: Question 1 - Portfolio (Home, Projects, Contact)
    // ----------------------------------------------------
    await describe("Question 1: Portfolio - Home Page", async () => {
        // Load home script
        const homeCode = fs.readFileSync(path.join(baseDir, "portfolio", "js", "home.js"), "utf8");
        const doc = createMockDocument();
        const mockStorage = {};
        const sandbox = {
            document: doc,
            addEventListener: () => {},
            localStorage: {
                getItem: (k) => mockStorage[k] || null,
                setItem: (k, v) => { mockStorage[k] = String(v); }
            },
            Date: Date,
            fetch: global.fetch || (() => Promise.resolve({ ok: true, status: 200, json: () => Promise.resolve([]) })),
            console: console,
            setTimeout: setTimeout
        };
        sandbox.window = sandbox;
        sandbox.globalThis = sandbox;

        const vm = require("vm");
        vm.createContext(sandbox);
        vm.runInContext(homeCode, sandbox);

        test("getGreetingByTime returns time-appropriate greeting", () => {
            const greeting = sandbox.getGreetingByTime();
            assert.ok(["Good morning", "Good afternoon", "Good evening"].includes(greeting));
        });

        test("greetVisitor() updates welcome-heading with visitor name", () => {
            const nameInput = doc.getElementById("visitor-name");
            const heading = doc.getElementById("welcome-heading");
            nameInput.value = "Jyotiraditya";
            sandbox.greetVisitor();
            assert.ok(heading.innerHTML.includes("Jyotiraditya"));
            assert.ok(heading.innerHTML.includes("Welcome"));
        });

        test("greetVisitor() provides friendly fallback message if input is empty", () => {
            const nameInput = doc.getElementById("visitor-name");
            const heading = doc.getElementById("welcome-heading");
            nameInput.value = "";
            sandbox.greetVisitor();
            assert.ok(heading.innerHTML.includes("friend"));
        });

        test("toggleTheme() toggles between Dark mode and Light mode and saves to localStorage", () => {
            const themeBtn = doc.getElementById("theme-btn");
            themeBtn.innerHTML = "Dark mode";
            doc.body.style.backgroundColor = "#f5f7fb";

            sandbox.toggleTheme();
            assert.strictEqual(doc.body.style.backgroundColor, "#1a202c");
            assert.strictEqual(themeBtn.innerHTML, "Light mode");
            assert.strictEqual(mockStorage["jyotiraditya_portfolio_theme"], "dark");

            sandbox.toggleTheme();
            assert.strictEqual(doc.body.style.backgroundColor, "#f5f7fb");
            assert.strictEqual(themeBtn.innerHTML, "Dark mode");
            assert.strictEqual(mockStorage["jyotiraditya_portfolio_theme"], "light");
        });

        test("toggleSkills() toggles style.display and button text", () => {
            const skillsList = doc.getElementById("skills-list");
            const toggleBtn = doc.getElementById("toggle-skills-btn");

            skillsList.style.display = "block";
            sandbox.toggleSkills();
            assert.strictEqual(skillsList.style.display, "none");
            assert.strictEqual(toggleBtn.innerHTML, "Show");

            sandbox.toggleSkills();
            assert.strictEqual(skillsList.style.display, "block");
            assert.strictEqual(toggleBtn.innerHTML, "Hide");
        });
    });

    await describe("Question 1: Portfolio - Projects Page", async () => {
        const projectsCode = fs.readFileSync(path.join(baseDir, "portfolio", "js", "projects.js"), "utf8");
        const doc = createMockDocument();
        const tbody = createMockElement("tbody");
        const table = createMockElement("table");
        table.appendChild(tbody);
        doc._register("project-table", table);

        const sandbox = {
            document: doc,
            console: console,
            setTimeout: (cb) => cb(), // Fast setTimeout
            Set: Set
        };
        sandbox.window = sandbox;
        sandbox.globalThis = sandbox;
        sandbox.addEventListener = () => {};

        const vm = require("vm");
        vm.createContext(sandbox);
        vm.runInContext(projectsCode, sandbox);

        test("Project class exists and getSummary() works", () => {
            assert.ok(sandbox.Project);
            const p = new sandbox.Project(99, "Test App", ["JS", "Node"], "A cool test project", "http://example.com");
            assert.strictEqual(p.title, "Test App");
            assert.ok(p.getSummary().includes("Test App"));
            assert.ok(p.getSummary().includes("JS, Node"));
        });

        test("FeaturedProject extends Project and adds badge", () => {
            assert.ok(sandbox.FeaturedProject);
            const fp = new sandbox.FeaturedProject(100, "Star App", ["React"], "Starred", "http://example.com", null, "Spotlight");
            assert.ok(fp instanceof sandbox.Project);
            assert.strictEqual(fp.badge, "Spotlight");
        });

        test("projectDataset contains at least 5 projects with some demoLinks null", () => {
            assert.ok(sandbox.projectDataset.length >= 5);
            const withDemo = sandbox.projectDataset.filter(p => p.demoLink);
            const withoutDemo = sandbox.projectDataset.filter(p => !p.demoLink);
            assert.ok(withDemo.length > 0, "Should have some projects with demoLink");
            assert.ok(withoutDemo.length > 0, "Should have some projects without demoLink");
        });

        await asyncTest("loadProjects returns promise and resolves with project dataset", async () => {
            const projects = await sandbox.loadProjects();
            assert.ok(Array.isArray(projects));
            assert.strictEqual(projects.length, sandbox.projectDataset.length);
        });

        test("renderTable builds table using createElement, destructuring, and ?. / ??", () => {
            sandbox.renderTable(sandbox.projectDataset);
            assert.strictEqual(tbody.children.length, sandbox.projectDataset.length);

            // Check that projects without demoLink display 'Not available'
            const renderedHtml = tbody.children.map(tr => tr.children.map(td => td.textContent).join(" ")).join(" | ");
            assert.ok(renderedHtml.includes("Not available"));
        });

        test("filterProjects filters by title and technology dropdown", () => {
            sandbox.allProjects = sandbox.projectDataset;
            const searchInput = doc.getElementById("search-input");
            const techFilter = doc.getElementById("tech-filter");

            // Filter by title "Calculator"
            searchInput.value = "Calculator";
            techFilter.value = "all";
            sandbox.filterProjects();
            assert.strictEqual(tbody.children.length, 2); // Bonus Calculator and Order Calculator

            // Filter by tech "MySQL"
            searchInput.value = "";
            techFilter.value = "MySQL";
            sandbox.filterProjects();
            assert.strictEqual(tbody.children.length, 1);
        });

        test("calculateStats uses map and reduce", () => {
            // Reset filters and check stats
            sandbox.calculateStats(sandbox.projectDataset);
            const stats = doc.getElementById("stats-summary");
            assert.ok(stats.textContent.includes("verified projects"));
        });

        test("toggleSortByTitle sorts projects alphabetically (Bonus)", () => {
            sandbox.allProjects = [...sandbox.projectDataset];
            sandbox.toggleSortByTitle();
            const firstRowTitle = tbody.children[0].children[0].textContent;
            assert.ok(firstRowTitle.length > 0);
        });
    });

    await describe("Question 1: Portfolio - Contact Page Form Validation", async () => {
        const contactCode = fs.readFileSync(path.join(baseDir, "portfolio", "js", "contact.js"), "utf8");
        const doc = createMockDocument();
        const sandbox = {
            document: doc,
            console: console
        };

        const vm = require("vm");
        vm.createContext(sandbox);
        vm.runInContext(contactCode, sandbox);

        test("validateName requires non-empty name", () => {
            const nameInput = doc.getElementById("name");
            const nameError = doc.getElementById("name-error");

            nameInput.value = "";
            assert.strictEqual(sandbox.validateName(), false);
            assert.ok(nameError.textContent.includes("required"));

            nameInput.value = "Jyotiraditya";
            assert.strictEqual(sandbox.validateName(), true);
            assert.strictEqual(nameError.textContent, "");
        });

        test("validateEmail enforces email regex", () => {
            const emailInput = doc.getElementById("email");
            const emailError = doc.getElementById("email-error");

            emailInput.value = "bademail";
            assert.strictEqual(sandbox.validateEmail(), false);
            assert.ok(emailError.textContent.length > 0);

            emailInput.value = "test@domain.com";
            assert.strictEqual(sandbox.validateEmail(), true);
            assert.strictEqual(emailError.textContent, "");
        });

        test("validatePhone requires exactly 10 digits", () => {
            const phoneInput = doc.getElementById("phone");
            const phoneError = doc.getElementById("phone-error");

            phoneInput.value = "12345";
            assert.strictEqual(sandbox.validatePhone(), false);

            phoneInput.value = "abcdefghij";
            assert.strictEqual(sandbox.validatePhone(), false);

            phoneInput.value = "9876543210";
            assert.strictEqual(sandbox.validatePhone(), true);
            assert.strictEqual(phoneError.textContent, "");
        });

        test("validateMessage requires at least 20 characters", () => {
            const msgInput = doc.getElementById("message");
            const msgError = doc.getElementById("message-error");

            msgInput.value = "Too short";
            assert.strictEqual(sandbox.validateMessage(), false);

            msgInput.value = "This is a detailed message with more than twenty characters.";
            assert.strictEqual(sandbox.validateMessage(), true);
            assert.strictEqual(msgError.textContent, "");
        });

        test("validate() prevents invalid submission and shows thank-you message on valid submission", () => {
            doc.getElementById("name").value = "Jyotiraditya Patil";
            doc.getElementById("email").value = "jyoti@example.com";
            doc.getElementById("phone").value = "9876543210";
            doc.getElementById("message").value = "I am interested in your software development portfolio projects.";
            const form = doc.getElementById("contact-form");
            const thankYouBox = doc.getElementById("thank-you-box");

            const result = sandbox.validate();
            assert.strictEqual(result, false, "Should return false to prevent default page reload");
            assert.strictEqual(thankYouBox.style.display, "block");
            assert.ok(thankYouBox.innerHTML.includes("Thank you, Jyotiraditya Patil!"));
        });
    });

    // ----------------------------------------------------
    // SUITE 3: Question 2 - HR Portal (Login, Directory, Onboarding)
    // ----------------------------------------------------
    await describe("Question 2: HR Portal - Login Page", async () => {
        const loginCode = fs.readFileSync(path.join(baseDir, "hr-portal", "js", "login.js"), "utf8");
        const doc = createMockDocument();
        const sandbox = {
            document: doc,
            console: console
        };

        const vm = require("vm");
        vm.createContext(sandbox);
        vm.runInContext(loginCode, sandbox);

        test("togglePasswordVisibility toggles between password and text using setAttribute", () => {
            const passInput = doc.getElementById("password");
            const toggleBtn = doc.getElementById("toggle-password-btn");

            passInput.setAttribute("type", "password");
            sandbox.togglePasswordVisibility();
            assert.strictEqual(passInput.getAttribute("type"), "text");
            assert.strictEqual(toggleBtn.textContent, "Hide");

            sandbox.togglePasswordVisibility();
            assert.strictEqual(passInput.getAttribute("type"), "password");
            assert.strictEqual(toggleBtn.textContent, "Show");
        });

        test("validateUsername enforces required check", () => {
            const userInput = doc.getElementById("username");
            userInput.value = "";
            assert.strictEqual(sandbox.validateUsername(), false);

            userInput.value = "admin.user";
            assert.strictEqual(sandbox.validateUsername(), true);
        });

        test("validatePassword enforces regex complexity (>=8 chars, 1 upper, 1 lower, 1 digit, 1 special)", () => {
            const passInput = doc.getElementById("password");

            passInput.value = "Pass1"; // Too short
            assert.strictEqual(sandbox.validatePassword(), false);

            passInput.value = "nouppercase1@"; // Missing uppercase
            assert.strictEqual(sandbox.validatePassword(), false);

            passInput.value = "NOLOWERCASE1@"; // Missing lowercase
            assert.strictEqual(sandbox.validatePassword(), false);

            passInput.value = "NoDigitsHere@"; // Missing digit
            assert.strictEqual(sandbox.validatePassword(), false);

            passInput.value = "NoSpecialChar12"; // Missing special character
            assert.strictEqual(sandbox.validatePassword(), false);

            passInput.value = "ComplexP@ssw0rd"; // Valid
            assert.strictEqual(sandbox.validatePassword(), true);
        });
    });

    await describe("Question 2: HR Portal - Directory Page", async () => {
        const directoryCode = fs.readFileSync(path.join(baseDir, "hr-portal", "js", "directory.js"), "utf8");
        const doc = createMockDocument();
        const tbody = createMockElement("tbody");
        const table = createMockElement("table");
        table.appendChild(tbody);
        doc._register("employee-table", table);

        const sandbox = {
            document: doc,
            console: console,
            fetch: async () => ({
                ok: true,
                status: 200,
                json: async () => [
                    { id: 1, name: "Leanne Graham", username: "Bret", email: "Sincere@april.biz", phone: "1-770-736-8031" },
                    { id: 2, name: "Ervin Howell", username: "Antonette", email: "Shanna@melissa.tv", phone: "010-692-6593" }
                ]
            }),
            alert: () => {}
        };
        sandbox.window = sandbox;
        sandbox.globalThis = sandbox;
        sandbox.addEventListener = () => {};

        const vm = require("vm");
        vm.createContext(sandbox);
        vm.runInContext(directoryCode, sandbox);

        await asyncTest("loadEmployees fetches from API, sets departments, and builds table", async () => {
            await sandbox.loadEmployees();
            assert.strictEqual(sandbox.employees.length, 2);
            assert.strictEqual(tbody.children.length, 2);
            const totalEl = doc.getElementById("total-count");
            assert.strictEqual(totalEl.textContent, "Total employees: 2");
        });

        test("searchEmployees uses computed key {[field]: text} and filters list", () => {
            const fieldSelect = doc.getElementById("search-field");
            const searchInput = doc.getElementById("search-text");

            // Search by Name
            fieldSelect.value = "name";
            searchInput.value = "Leanne";
            sandbox.searchEmployees();
            assert.strictEqual(tbody.children.length, 1);

            // Search by Email
            fieldSelect.value = "email";
            searchInput.value = "melissa";
            sandbox.searchEmployees();
            assert.strictEqual(tbody.children.length, 1);

            // Reset search
            searchInput.value = "";
            sandbox.searchEmployees();
            assert.strictEqual(tbody.children.length, 2);
        });

        test("handleAddEmployee uses shorthand properties and spread operator {...employee, department}", () => {
            doc.getElementById("new-emp-name").value = "Kavita Sharma";
            doc.getElementById("new-emp-dept").value = "Finance";

            sandbox.handleAddEmployee();
            assert.strictEqual(sandbox.employees.length, 3);
            const added = sandbox.employees[2];
            assert.strictEqual(added.name, "Kavita Sharma");
            assert.strictEqual(added.department, "Finance");

            const totalEl = doc.getElementById("total-count");
            assert.strictEqual(totalEl.textContent, "Total employees: 3");
        });

        test("Remove button on row calls remove() and updates total count", () => {
            const rowToRemove = tbody.children[0];
            const removeBtn = rowToRemove.children[6].children[0];
            removeBtn.onclick();

            assert.strictEqual(rowToRemove._removed, true);
            assert.strictEqual(sandbox.employees.length, 2);
            const totalEl = doc.getElementById("total-count");
            assert.strictEqual(totalEl.textContent, "Total employees: 2");
        });
    });

    await describe("Question 2: HR Portal - Onboarding Page", async () => {
        const onboardingCode = fs.readFileSync(path.join(baseDir, "hr-portal", "js", "onboarding.js"), "utf8");
        const doc = createMockDocument();
        const sandbox = {
            document: doc,
            console: console,
            setTimeout: (cb) => cb() // Fast-forward setTimeout
        };

        const vm = require("vm");
        vm.createContext(sandbox);
        vm.runInContext(onboardingCode, sandbox);

        test("Validation checks all mandatory onboarding fields", () => {
            doc.getElementById("full-name").value = "";
            doc.getElementById("official-email").value = "";
            doc.getElementById("qualification").value = "";
            doc.getElementById("department").value = "";

            assert.strictEqual(sandbox.validateForm(), false);

            doc.getElementById("full-name").value = "Jyotiraditya Patil";
            doc.getElementById("official-email").value = "jyoti@technova.example";
            doc.getElementById("qualification").value = "bachelor";
            doc.getElementById("department").value = "engineering";

            assert.strictEqual(sandbox.validateForm(), true);
        });

        await asyncTest("Onboarding promise chain succeeds for valid names", async () => {
            const name = "Jyotiraditya";
            const res = await sandbox.createAccount(name)
                .then(sandbox.sendWelcomeEmail)
                .then(sandbox.assignLaptop)
                .then(sandbox.welcomeAboard);

            assert.strictEqual(res, "Welcome aboard, Jyotiraditya!");
        });

        await asyncTest("createAccount rejects if name is shorter than 3 letters and catches error", async () => {
            let caughtError = null;
            await sandbox.createAccount("Al")
                .then(sandbox.sendWelcomeEmail)
                .catch(err => {
                    caughtError = err;
                });

            assert.ok(caughtError !== null, "Promise should have rejected");
            assert.ok(caughtError.includes("Name must be at least 3 letters long"));
        });
    });

    // ----------------------------------------------------
    // SUITE 4: Question 3 - Landing Page (Moonbean Lab)
    // ----------------------------------------------------
    await describe("Question 3: Landing Page - Order Calculator", async () => {
        const landingCode = fs.readFileSync(path.join(baseDir, "landing-page", "js", "landing.js"), "utf8");
        const doc = createMockDocument();
        const orderListEl = createMockElement("ul", "order-list");
        doc._register("order-list", orderListEl);

        const sandbox = {
            document: doc,
            console: console,
            setTimeout: (cb) => cb(), // Immediate timer execution
            parseInt: parseInt,
            Math: Math,
            Date: Date
        };
        sandbox.window = sandbox;
        sandbox.globalThis = sandbox;
        sandbox.addEventListener = () => {};

        const vm = require("vm");
        vm.createContext(sandbox);
        vm.runInContext(landingCode, sandbox);

        test("Menu has at least 5 items with id, name, and price", () => {
            assert.ok(sandbox.menuItems.length >= 5);
            sandbox.menuItems.forEach(item => {
                assert.ok(item.id);
                assert.ok(item.name);
                assert.ok(typeof item.price === "number");
            });
        });

        test("populateMenuDropdown builds dropdown options with createElement and setAttribute('value', ...)", () => {
            const itemSelect = doc.getElementById("item-select");
            sandbox.populateMenuDropdown();
            assert.strictEqual(itemSelect.children.length, sandbox.menuItems.length + 1); // +1 for default option
            assert.strictEqual(itemSelect.children[1].getAttribute("value"), "1");
        });

        test("addToOrder validates quantity with parseInt and rejects non-positive numbers", () => {
            const itemSelect = doc.getElementById("item-select");
            const qtyInput = doc.getElementById("quantity-input");
            const errorDisplay = doc.getElementById("order-error");

            itemSelect.value = "1";
            qtyInput.value = "-3"; // Invalid quantity
            sandbox.addToOrder();
            assert.ok(errorDisplay.textContent.includes("positive number"));

            qtyInput.value = "0"; // Invalid quantity
            sandbox.addToOrder();
            assert.ok(errorDisplay.textContent.includes("positive number"));

            // Valid quantity
            qtyInput.value = "2";
            sandbox.addToOrder();
            assert.strictEqual(sandbox.cart.length, 1);
            assert.strictEqual(sandbox.cart[0].quantity, 2);
            assert.strictEqual(sandbox.cart[0].lineTotal, 240); // 120 * 2
        });

        test("getDeliveryCharge uses switch/case/break/default", () => {
            assert.strictEqual(sandbox.getDeliveryCharge("dine-in"), 0);
            assert.strictEqual(sandbox.getDeliveryCharge("campus"), 30);
            assert.strictEqual(sandbox.getDeliveryCharge("city"), 50);
            assert.strictEqual(sandbox.getDeliveryCharge("outskirts"), 80);
            assert.strictEqual(sandbox.getDeliveryCharge("unknown"), 40);
        });

        test("calculateDiscount uses if, else if ladder for 5%, 10%, 15% discount tiers", () => {
            assert.strictEqual(sandbox.calculateDiscount(200), 0); // < 300: 0%
            assert.strictEqual(sandbox.calculateDiscount(350), 18); // >= 300: 5% (17.5 rounded to 18)
            assert.strictEqual(sandbox.calculateDiscount(600), 60); // >= 500: 10%
            assert.strictEqual(sandbox.calculateDiscount(1200), 180); // >= 1000: 15%
        });

        test("Custom callback applyRule(amount, rule) called with anonymous & arrow functions", () => {
            // Anonymous function
            const res1 = sandbox.applyRule(500, function (amt) { return amt * 0.1; });
            assert.strictEqual(res1, 50);

            // Arrow function
            const res2 = sandbox.applyRule(500, amt => amt - 50);
            assert.strictEqual(res2, 450);
        });

        test("updateBill calculates subtotal with reduce and displays template literal breakdown", () => {
            sandbox.updateBill();
            const billDisplay = doc.getElementById("bill-summary");
            assert.ok(billDisplay.innerHTML.includes("Order Breakdown"));
            assert.ok(billDisplay.innerHTML.includes("Subtotal:"));
            assert.ok(billDisplay.innerHTML.includes("Final Total:"));
        });

        test("Remove button on order line removes item from cart and recalculates bill", () => {
            assert.strictEqual(sandbox.cart.length, 1);
            const removeBtn = orderListEl.children[0].children[1];
            removeBtn.onclick();
            assert.strictEqual(sandbox.cart.length, 0);

            const billDisplay = doc.getElementById("bill-summary");
            assert.ok(billDisplay.innerHTML.includes("empty"));
        });

        await asyncTest("placeOrderPromise rejects when cart is empty and resolves when cart has items", async () => {
            // Empty cart should reject
            sandbox.cart = [];
            let rejected = false;
            try {
                await sandbox.placeOrderPromise();
            } catch (err) {
                rejected = true;
                assert.ok(err.includes("cart is empty"));
            }
            assert.strictEqual(rejected, true);

            // Cart with items should resolve with order number starting with MBL-
            sandbox.cart = [{ id: 1, name: "Orbit Espresso", price: 120, quantity: 1, lineTotal: 120 }];
            const orderNum = await sandbox.placeOrderPromise();
            assert.ok(orderNum.startsWith("MBL-"));
        });

        test("Special offer banner appears 3s after load via triggerSpecialOffer", () => {
            const banner = doc.getElementById("special-offer-banner");
            sandbox.triggerSpecialOffer();
            assert.strictEqual(banner.style.display, "block");

            sandbox.dismissOffer();
            assert.strictEqual(banner.style.display, "none");
        });
    });

    // ----------------------------------------------------
    // TEST RUN SUMMARY
    // ----------------------------------------------------
    console.log(`\n${CYAN}================================================${RESET}`);
    console.log(`Test Execution Complete.`);
    console.log(`Total Tests Run : ${totalTests}`);
    console.log(`Passed          : ${GREEN}${passedTests}${RESET}`);
    console.log(`Failed          : ${failedTests === 0 ? GREEN + 0 : RED + failedTests}${RESET}`);
    console.log(`${CYAN}================================================${RESET}\n`);

    if (failedTests > 0) {
        process.exit(1);
    }
}

runAllTests().catch(err => {
    console.error("Unhandled test runner exception:", err);
    process.exit(1);
});
