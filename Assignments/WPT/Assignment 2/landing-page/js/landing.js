/**
 * Moonbean Lab - Order Calculator Script
 * Assignment 2, Question 3: Interactive order system for local business landing page
 */

// ==========================================
// 1. Menu Data (at least 5 items)
// ==========================================
const menuItems = [
    { id: 1, name: "Orbit Espresso", price: 120 },
    { id: 2, name: "Nebula Latte", price: 180 },
    { id: 3, name: "Launchpad Study Pass", price: 250 },
    { id: 4, name: "Cold Brew Float", price: 160 },
    { id: 5, name: "Artisan Almond Croissant", price: 140 },
    { id: 6, name: "Cosmic Dark Mocha", price: 210 }
];

// In-memory cart array
let cart = [];

/**
 * Builds the menu dropdown options with a loop using createElement and setAttribute("value", ...)
 */
function populateMenuDropdown() {
    const itemSelect = document.getElementById("item-select");
    if (!itemSelect) return;

    itemSelect.innerHTML = "";
    const defaultOption = document.createElement("option");
    defaultOption.setAttribute("value", "");
    defaultOption.textContent = "-- Choose an item --";
    itemSelect.appendChild(defaultOption);

    menuItems.forEach(item => {
        const option = document.createElement("option");
        option.setAttribute("value", String(item.id));
        option.textContent = `${item.name} - ₹${item.price}`;
        itemSelect.appendChild(option);
    });
}

/**
 * Adds an item to the order.
 * Reads quantity with parseInt, verifies positive integer,
 * pushes object to cart array, and updates DOM order list and bill.
 */
function addToOrder() {
    const itemSelect = document.getElementById("item-select");
    const quantityInput = document.getElementById("quantity-input");
    const errorDisplay = document.getElementById("order-error");

    errorDisplay.textContent = "";

    const selectedId = parseInt(itemSelect.value, 10);
    // Read quantity with parseInt
    const quantity = parseInt(quantityInput.value, 10);

    if (isNaN(selectedId)) {
        errorDisplay.textContent = "Please select an item from the menu.";
        return;
    }

    // Check if quantity is a positive number
    if (isNaN(quantity) || quantity <= 0) {
        errorDisplay.textContent = "Quantity must be a valid positive number (1 or more).";
        return;
    }

    const menuItem = menuItems.find(item => item.id === selectedId);
    if (!menuItem) {
        errorDisplay.textContent = "Selected item not found in catalog.";
        return;
    }

    // Unique cart item entry
    const cartItem = {
        cartItemId: Date.now() + Math.random(),
        id: menuItem.id,
        name: menuItem.name,
        price: menuItem.price,
        quantity: quantity,
        lineTotal: menuItem.price * quantity
    };

    cart.push(cartItem);

    // Reset inputs
    itemSelect.value = "";
    quantityInput.value = "1";

    // Refresh UI
    renderOrderList();
    updateBill();
}

/**
 * Renders the order list using createElement and appendChild.
 * Attaches a Remove button to each line that deletes the line from the page and from the cart array.
 */
function renderOrderList() {
    const orderListEl = document.getElementById("order-list");
    if (!orderListEl) return;

    orderListEl.innerHTML = "";

    cart.forEach(item => {
        const li = document.createElement("li");
        li.className = "order-item";
        li.style.display = "flex";
        li.style.justifyContent = "space-between";
        li.style.alignItems = "center";
        li.style.padding = "8px 0";
        li.style.borderBottom = "1px solid #f1f5f9";

        const textSpan = document.createElement("span");
        textSpan.textContent = `${item.name} × ${item.quantity} = ₹${item.lineTotal}`;
        li.appendChild(textSpan);

        // Remove button
        const removeBtn = document.createElement("button");
        removeBtn.type = "button";
        removeBtn.textContent = "Remove";
        removeBtn.className = "remove-item-btn";
        removeBtn.style.marginLeft = "12px";
        removeBtn.style.padding = "4px 8px";
        removeBtn.style.background = "#ef4444";
        removeBtn.style.color = "#ffffff";
        removeBtn.style.border = "none";
        removeBtn.style.borderRadius = "4px";
        removeBtn.style.cursor = "pointer";

        // Deletes the line from the page and from the cart array
        removeBtn.onclick = function () {
            // Remove from cart array
            cart = cart.filter(c => c.cartItemId !== item.cartItemId);
            // Remove line from the DOM
            li.remove();
            // Re-render and update bill
            updateBill();
        };

        li.appendChild(removeBtn);
        orderListEl.appendChild(li);
    });
}

/**
 * Sets the delivery charge for each area using a switch with case, break and default.
 * @param {string} area
 * @returns {number}
 */
function getDeliveryCharge(area) {
    let charge = 0;
    switch (area) {
        case "dine-in":
            charge = 0;
            break;
        case "campus":
            charge = 30;
            break;
        case "city":
            charge = 50;
            break;
        case "outskirts":
            charge = 80;
            break;
        default:
            charge = 40;
            break;
    }
    return charge;
}

/**
 * Custom higher-order callback function:
 * Takes an amount and a rule callback function, returning the result of applying that rule.
 * @param {number} amount
 * @param {Function} rule
 * @returns {number}
 */
function applyRule(amount, rule) {
    return rule(amount);
}

/**
 * Calculates discount using an if, else if ladder at three distinct subtotal thresholds.
 * @param {number} subtotal
 * @returns {number} discount amount in rupees
 */
function calculateDiscount(subtotal) {
    let rate = 0;

    // if, else if ladder
    if (subtotal >= 1000) {
        rate = 0.15; // 15% discount for orders >= 1000
    } else if (subtotal >= 500) {
        rate = 0.10; // 10% discount for orders >= 500
    } else if (subtotal >= 300) {
        rate = 0.05; // 5% discount for orders >= 300
    } else {
        rate = 0.00; // 0% discount
    }

    return Math.round(subtotal * rate);
}

/**
 * Calculates subtotal with reduce, calculates discounts and delivery charges,
 * and formats the entire summary in one template literal.
 * Uses conditional ? : operator to show "1 item" or "N items" correctly.
 */
function updateBill() {
    const billDisplay = document.getElementById("bill-summary");
    if (!billDisplay) return;

    if (cart.length === 0) {
        billDisplay.innerHTML = "<em>Your cart is currently empty. Add drinks or study passes above.</em>";
        return;
    }

    // 1. Calculate subtotal with reduce
    const subtotal = cart.reduce((acc, curr) => acc + curr.lineTotal, 0);

    // Total quantity of items in cart
    const totalItems = cart.reduce((acc, curr) => acc + curr.quantity, 0);

    // Conditional ? : operator to show "1 item" or "2 items" correctly
    const itemLabel = `${totalItems} ${totalItems === 1 ? "item" : "items"}`;

    // 2. Delivery charge via switch
    const areaSelect = document.getElementById("delivery-area");
    const selectedArea = areaSelect ? areaSelect.value : "dine-in";
    const deliveryCharge = getDeliveryCharge(selectedArea);

    // 3. Discount via if, else if ladder
    const discount = calculateDiscount(subtotal);

    // 4. Custom callback demonstration:
    // Call 1: with an anonymous function
    const studentDiscount = applyRule(subtotal, function (amt) {
        return amt >= 600 ? 10 : 0;
    });

    // Call 2: with an arrow function
    const greenPackagingCredit = applyRule(totalItems, items => items >= 3 ? 5 : 0);

    const totalDeductions = discount + studentDiscount + greenPackagingCredit;
    const finalTotal = Math.max(0, subtotal - totalDeductions + deliveryCharge);

    // Show subtotal, discount, delivery charge and final total in one message made with a template literal
    billDisplay.innerHTML = `
        <div class="bill-card" style="background: #fff8f0; padding: 1.25rem; border-radius: 8px; border: 1px solid #fed7aa; margin-top: 1rem;">
            <h4 style="margin-top: 0; color: #9a3412;">Order Breakdown (${itemLabel})</h4>
            <p><strong>Subtotal:</strong> ₹${subtotal}</p>
            <p><strong>Tier Discount:</strong> -₹${discount}</p>
            ${studentDiscount > 0 ? `<p><strong>Bonus Study Perk:</strong> -₹${studentDiscount}</p>` : ""}
            ${greenPackagingCredit > 0 ? `<p><strong>Green Packaging Perk:</strong> -₹${greenPackagingCredit}</p>` : ""}
            <p><strong>Delivery Charge:</strong> ₹${deliveryCharge} (${selectedArea})</p>
            <hr style="border: none; border-top: 1px solid #fdba74; margin: 8px 0;">
            <p style="font-size: 1.2rem; color: #7c2d12; margin: 0;"><strong>Final Total: ₹${finalTotal}</strong></p>
        </div>
    `;
}

// ==========================================
// 8. Place Order (Promise with setTimeout, async/await, try/catch)
// ==========================================

/**
 * Returns a promise that simulates placing the order with an external backend.
 * Waits 2 seconds with setTimeout, then resolves with an order number or rejects if cart is empty.
 * @returns {Promise<string>}
 */
function placeOrderPromise() {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (cart.length === 0) {
                reject("Your cart is empty. Please add at least one item before placing an order.");
            } else {
                const orderNumber = `MBL-${Math.floor(100000 + Math.random() * 900000)}`;
                resolve(orderNumber);
            }
        }, 2000); // 2 seconds wait
    });
}

/**
 * Handles placing the order.
 * Shows "Placing your order..." while waiting.
 * Handles the promise with async, await, try and catch.
 */
async function placeOrder() {
    const statusDisplay = document.getElementById("order-status");
    const errorDisplay = document.getElementById("order-error");
    const placeBtn = document.getElementById("place-order-btn");

    errorDisplay.textContent = "";
    statusDisplay.style.color = "#2563eb";
    statusDisplay.textContent = "Placing your order...";

    if (placeBtn) placeBtn.disabled = true;

    try {
        const orderNumber = await placeOrderPromise();
        statusDisplay.style.color = "#047857";
        statusDisplay.innerHTML = `<strong>Order Confirmed!</strong> Your order number is <strong>${orderNumber}</strong>. We are brewing it now!`;

        // Clear cart
        cart = [];
        renderOrderList();
        updateBill();

    } catch (err) {
        statusDisplay.textContent = "";
        errorDisplay.style.color = "#dc2626";
        errorDisplay.textContent = `Order placement failed: ${err}`;
    } finally {
        if (placeBtn) placeBtn.disabled = false;
    }
}

// ==========================================
// 9. Special Offer Banner (Appears 3s after load)
// ==========================================

/**
 * Displays a special offer banner 3 seconds after the page loads using setTimeout.
 */
function triggerSpecialOffer() {
    setTimeout(() => {
        const banner = document.getElementById("special-offer-banner");
        if (banner) {
            banner.style.display = "block";
        }
    }, 3000); // 3 seconds
}

/**
 * Dismisses the special offer banner
 */
function dismissOffer() {
    const banner = document.getElementById("special-offer-banner");
    if (banner) {
        banner.style.display = "none";
    }
}

// Initialization on DOM load
if (typeof window !== "undefined") {
    window.addEventListener("DOMContentLoaded", () => {
        populateMenuDropdown();
        updateBill();
        triggerSpecialOffer();
    });

    // Expose symbols on window for browser scripts and automated testing
    window.menuItems = menuItems;
    Object.defineProperty(window, "cart", {
        get: () => cart,
        set: (val) => { cart = val; },
        configurable: true
    });
    window.populateMenuDropdown = populateMenuDropdown;
    window.addToOrder = addToOrder;
    window.renderOrderList = renderOrderList;
    window.getDeliveryCharge = getDeliveryCharge;
    window.applyRule = applyRule;
    window.calculateDiscount = calculateDiscount;
    window.updateBill = updateBill;
    window.placeOrderPromise = placeOrderPromise;
    window.placeOrder = placeOrder;
    window.triggerSpecialOffer = triggerSpecialOffer;
    window.dismissOffer = dismissOffer;
}
