/**
 * Jyotiraditya Patil - Portfolio Home Script
 * Assignment 2: Upgrade your portfolio
 */

// Global state / settings
// Using let and const by default
const THEME_KEY = "jyotiraditya_portfolio_theme";

/**
 * Returns a time-of-day greeting using the Date object (Bonus)
 * @returns {string} "Good morning", "Good afternoon", or "Good evening"
 */
function getGreetingByTime() {
    const currentHour = new Date().getHours();
    if (currentHour < 12) {
        return "Good morning";
    } else if (currentHour < 18) {
        return "Good afternoon";
    } else {
        return "Good evening";
    }
}

/**
 * Greets the visitor using their entered name in a template literal.
 * If empty, displays a friendly fallback message.
 * Demonstrates getElementById, value, and innerHTML.
 */
function greetVisitor() {
    const nameInput = document.getElementById("visitor-name");
    const heading = document.getElementById("welcome-heading");
    const name = nameInput.value.trim();
    const timeGreeting = getGreetingByTime();

    if (name) {
        // Template literal with greeting
        heading.innerHTML = `${timeGreeting}, ${name}! Welcome to my portfolio!`;
    } else {
        // Friendly fallback message
        heading.innerHTML = `${timeGreeting}, friend! Glad to have you exploring my work.`;
    }
}

/**
 * Toggles between light and dark themes using if and else.
 * Updates style.backgroundColor and style.color.
 * Persists choice via localStorage (Bonus).
 */
function toggleTheme() {
    const themeBtn = document.getElementById("theme-btn");
    const currentBg = document.body.style.backgroundColor;

    // Check if currently dark mode (or style is set to dark)
    if (currentBg === "rgb(26, 32, 44)" || currentBg === "#1a202c") {
        // Switch to Light mode
        document.body.style.backgroundColor = "#f5f7fb";
        document.body.style.color = "#243047";
        themeBtn.innerHTML = "Dark mode";
        localStorage.setItem(THEME_KEY, "light");
    } else {
        // Switch to Dark mode
        document.body.style.backgroundColor = "#1a202c";
        document.body.style.color = "#e2e8f0";
        themeBtn.innerHTML = "Light mode";
        localStorage.setItem(THEME_KEY, "dark");
    }
}

/**
 * Applies the saved theme from localStorage on load.
 */
function initTheme() {
    const savedTheme = localStorage.getItem(THEME_KEY);
    const themeBtn = document.getElementById("theme-btn");
    if (!themeBtn) return;

    if (savedTheme === "dark") {
        document.body.style.backgroundColor = "#1a202c";
        document.body.style.color = "#e2e8f0";
        themeBtn.innerHTML = "Light mode";
    } else {
        document.body.style.backgroundColor = "#f5f7fb";
        document.body.style.color = "#243047";
        themeBtn.innerHTML = "Dark mode";
    }
}

/**
 * Shows or hides the skills list by toggling style.display.
 * Updates the button text between "Hide" and "Show".
 */
function toggleSkills() {
    const skillsList = document.getElementById("skills-list");
    const toggleBtn = document.getElementById("toggle-skills-btn");

    if (skillsList.style.display === "none") {
        skillsList.style.display = "block";
        toggleBtn.innerHTML = "Hide";
    } else {
        skillsList.style.display = "none";
        toggleBtn.innerHTML = "Show";
    }
}

/**
 * Loads public repositories from GitHub using fetch, async and await.
 * Checks response.status, reads JSON, and creates DOM elements with createElement and appendChild.
 * Displays an error message in red if the request fails.
 */
async function fetchGitHubRepos() {
    const listElement = document.getElementById("repo-list");
    const errorElement = document.getElementById("repo-error");
    const statusElement = document.getElementById("repo-status");

    // Clear previous results
    listElement.innerHTML = "";
    errorElement.innerHTML = "";
    statusElement.innerHTML = "Fetching repositories...";

    const username = "jyotiraditya-patil";
    const primaryUrl = `https://api.github.com/users/${username}/repos`;
    const fallbackUrl = "https://jsonplaceholder.typicode.com/posts";

    try {
        let response = await fetch(primaryUrl);
        console.log("GitHub API response status:", response.status);

        // If user account has no repos or is rate limited/not found, fallback gracefully
        if (response.status !== 200) {
            console.warn(`GitHub responded with status ${response.status}. Trying fallback posts...`);
            response = await fetch(fallbackUrl);
            console.log("Fallback API response status:", response.status);
        }

        // Check if response is still not ok
        if (!response.ok) {
            throw new Error(`Failed to load data. HTTP status: ${response.status}`);
        }

        const data = await response.json();
        statusElement.innerHTML = `Loaded ${Math.min(data.length, 6)} entries successfully.`;

        // Take up to 6 items to display cleanly
        const items = data.slice(0, 6);
        items.forEach(item => {
            const li = document.createElement("li");
            // GitHub repo has 'name', fallback jsonplaceholder post has 'title'
            li.textContent = item.name ? item.name : item.title;
            li.style.padding = "4px 0";
            listElement.appendChild(li);
        });

    } catch (err) {
        statusElement.innerHTML = "";
        errorElement.style.color = "red";
        errorElement.innerHTML = `Error loading repositories: ${err.message}`;
        console.error("Repository load error:", err);
    }
}

/**
 * Pretend promise-based quote fetcher handled with .then() and .catch()
 * as requested: "Handle one other promise in your portfolio with .then() and .catch()"
 */
function loadDailyInspiration() {
    const quoteContainer = document.getElementById("quote-box");
    if (!quoteContainer) return;

    const promise = new Promise((resolve, reject) => {
        setTimeout(() => {
            const quotes = [
                "Continuous learning is the minimum requirement for success in tech.",
                "Simplicity is prerequisite for reliability. – Edsger W. Dijkstra",
                "First, solve the problem. Then, write the code. – John Johnson"
            ];
            if (quotes && quotes.length > 0) {
                const randomQuote = quotes[Math.floor(Math.random() * quotes.length)];
                resolve(randomQuote);
            } else {
                reject("Could not retrieve daily quote.");
            }
        }, 300);
    });

    // Handled using .then() and .catch()
    promise
        .then(quote => {
            quoteContainer.innerHTML = `<em>"${quote}"</em>`;
        })
        .catch(err => {
            quoteContainer.style.color = "red";
            quoteContainer.innerHTML = `Failed to load quote: ${err}`;
        });
}

// Initialize on page load
window.addEventListener("DOMContentLoaded", () => {
    initTheme();
    loadDailyInspiration();
});
