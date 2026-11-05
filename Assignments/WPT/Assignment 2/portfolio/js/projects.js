/**
 * Jyotiraditya Patil - Portfolio Projects Script
 * Assignment 2: Projects page data-driven table, cards, classes, filtering, and promises
 */

// ==========================================
// 1. Classes: Project and FeaturedProject
// ==========================================

/**
 * Base Project class representing a coding project
 */
class Project {
    constructor(id, title, tech, description, link, demoLink = null) {
        this.id = id;
        this.title = title;
        this.tech = tech; // Array of technologies used
        this.description = description;
        this.link = link;
        this.demoLink = demoLink; // Optional live link
    }

    /**
     * Returns a formatted summary string of the project
     */
    getSummary() {
        return `${this.title}: Built with ${this.tech.join(", ")}. ${this.description}`;
    }
}

/**
 * FeaturedProject class extending Project with an extra badge property
 */
class FeaturedProject extends Project {
    constructor(id, title, tech, description, link, demoLink = null, badge = "Featured") {
        super(id, title, tech, description, link, demoLink);
        this.badge = badge;
    }
}

// ==========================================
// 2. Project Data Store (at least 5 projects)
// ==========================================
const projectDataset = [
    new FeaturedProject(
        1,
        "Employee Bonus Calculator",
        ["Java", "OOP", "JUnit"],
        "Calculates employee bonuses from tenure and rating with business rule validations.",
        "https://github.com/jyotiraditya-patil/employee-bonus-calculator",
        "https://demo.example.com/bonus-calc",
        "Top Pick"
    ),
    new FeaturedProject(
        2,
        "Library Management Database",
        ["MySQL", "Database Design", "SQL"],
        "Models authors, books, members and transaction logs with relational constraints.",
        "https://github.com/jyotiraditya-patil/library-database",
        null, // No demoLink, tests ?. and ??
        "Staff Choice"
    ),
    new Project(
        3,
        "Sleeper Coach Booking Engine",
        ["Java", "Data Structures", "Algorithms"],
        "Implements seating berth arrays, real-time allocations, and cancellation queues.",
        "https://github.com/jyotiraditya-patil/sleeper-coach-booking",
        "https://demo.example.com/sleeper-booking"
    ),
    new Project(
        4,
        "Interactive Developer Portfolio",
        ["HTML", "CSS", "JavaScript"],
        "A multi-page responsive personal website with DOM manipulation and asynchronous APIs.",
        "https://github.com/jyotiraditya-patil/portfolio",
        "index.html"
    ),
    new Project(
        5,
        "TechNova HR Portal System",
        ["JavaScript", "HTML", "CSS"],
        "An enterprise internal directory and multi-stage onboarding workflow application.",
        "https://github.com/jyotiraditya-patil/technova-hr",
        null // No demoLink
    ),
    new Project(
        6,
        "Moonbean Lab Order Calculator",
        ["JavaScript", "ES6", "CSS"],
        "Dynamic interactive order calculator with real-time discounts, bills, and promises.",
        "https://github.com/jyotiraditya-patil/moonbean-lab",
        null // No demoLink
    )
];

// In-memory project holder
let allProjects = [];
let sortAscending = true;

// ==========================================
// 3. Loading from a pretend server (Promise)
// ==========================================

/**
 * Returns a promise that simulates network latency with setTimeout.
 * Resolves after 1.5s with the project array or rejects if empty.
 */
function loadProjects() {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (projectDataset && projectDataset.length > 0) {
                resolve(projectDataset);
            } else {
                reject("No project records found in repository database.");
            }
        }, 1500); // 1.5 seconds wait
    });
}

/**
 * Initializes the projects page.
 * Uses async, await, try and catch to consume loadProjects().
 */
async function initializeProjects() {
    const loadingStatus = document.getElementById("loading-status");
    if (loadingStatus) {
        loadingStatus.textContent = "Loading projects...";
    }

    try {
        allProjects = await loadProjects();
        if (loadingStatus) {
            loadingStatus.textContent = "";
        }

        // Render table, featured cards, filter options, and stats
        renderTable(allProjects);
        renderFeaturedCards(allProjects);
        populateTechDropdown(allProjects);
        calculateStats(allProjects);

    } catch (err) {
        if (loadingStatus) {
            loadingStatus.style.color = "red";
            loadingStatus.textContent = `Error: ${err}`;
        }
        console.error("Failed to load projects:", err);
    }
}

// ==========================================
// 4. Render Table with createElement & Destructuring
// ==========================================

/**
 * Builds the project table using createElement and destructuring.
 * Uses ?. and ?? to handle missing demoLink.
 * @param {Array<Project>} projectsToDisplay
 */
function renderTable(projectsToDisplay) {
    const tbody = document.querySelector("#project-table tbody");
    if (!tbody) return;

    // Clear previous rows
    tbody.innerHTML = "";

    projectsToDisplay.forEach(project => {
        // Destructuring assignment
        const { id, title, tech, description, link } = project;

        const tr = document.createElement("tr");

        // Title cell
        const tdTitle = document.createElement("td");
        tdTitle.textContent = title;
        tr.appendChild(tdTitle);

        // Tech cell
        const tdTech = document.createElement("td");
        tdTech.textContent = Array.isArray(tech) ? tech.join(", ") : tech;
        tr.appendChild(tdTech);

        // Description cell
        const tdDesc = document.createElement("td");
        tdDesc.textContent = description;
        tr.appendChild(tdDesc);

        // Link cell (Where demoLink is missing, show "Not available" using ?. and ??)
        const tdLink = document.createElement("td");
        const safeDemo = project?.demoLink ?? "Not available";

        const codeAnchor = document.createElement("a");
        codeAnchor.href = link;
        codeAnchor.target = "_blank";
        codeAnchor.rel = "noopener";
        codeAnchor.textContent = "Source Code";
        tdLink.appendChild(codeAnchor);

        const separator = document.createTextNode(" | ");
        tdLink.appendChild(separator);

        if (safeDemo !== "Not available") {
            const demoAnchor = document.createElement("a");
            demoAnchor.href = safeDemo;
            demoAnchor.target = "_blank";
            demoAnchor.rel = "noopener";
            demoAnchor.textContent = "Live Demo";
            tdLink.appendChild(demoAnchor);
        } else {
            const naSpan = document.createElement("span");
            naSpan.style.color = "#888888";
            naSpan.textContent = safeDemo; // "Not available"
            tdLink.appendChild(naSpan);
        }

        tr.appendChild(tdLink);
        tbody.appendChild(tr);
    });

    // Update count indicator with template literal
    const countDisplay = document.getElementById("project-counter");
    if (countDisplay) {
        countDisplay.textContent = `Showing ${projectsToDisplay.length} of ${allProjects.length} projects`;
    }
}

// ==========================================
// 5. Render Top Two Featured Project Cards
// ==========================================

/**
 * Builds the top two featured project cards and shows each badge.
 * @param {Array<Project>} projects
 */
function renderFeaturedCards(projects) {
    const cardsContainer = document.getElementById("featured-cards-container");
    if (!cardsContainer) return;

    cardsContainer.innerHTML = "";

    // Find the featured projects
    const featuredList = projects.filter(p => p instanceof FeaturedProject).slice(0, 2);

    featuredList.forEach(featuredItem => {
        const { title, description, link, badge } = featuredItem;

        const card = document.createElement("div");
        card.className = "project-card card featured";

        // Badge
        const badgeSpan = document.createElement("span");
        badgeSpan.className = "card-badge";
        badgeSpan.textContent = badge;
        card.appendChild(badgeSpan);

        // Title
        const h3 = document.createElement("h3");
        h3.textContent = title;
        card.appendChild(h3);

        // Description
        const p = document.createElement("p");
        p.textContent = description;
        card.appendChild(p);

        // Link
        const a = document.createElement("a");
        a.href = link;
        a.target = "_blank";
        a.rel = "noopener";
        a.textContent = "Explore project";
        card.appendChild(a);

        cardsContainer.appendChild(card);
    });
}

// ==========================================
// 6. Search and Filter (Title search & Tech dropdown)
// ==========================================

/**
 * Populates the technology filter dropdown with unique technologies.
 */
function populateTechDropdown(projects) {
    const dropdown = document.getElementById("tech-filter");
    if (!dropdown) return;

    // Reset dropdown to just 'All Technologies'
    dropdown.innerHTML = '<option value="all">All Technologies</option>';

    // Collect all unique technologies
    const allTechSet = new Set();
    projects.forEach(p => p.tech.forEach(t => allTechSet.add(t)));

    allTechSet.forEach(tech => {
        const option = document.createElement("option");
        option.value = tech;
        option.textContent = tech;
        dropdown.appendChild(option);
    });
}

/**
 * Filters projects by title and technology dropdown using filter with an arrow function.
 */
function filterProjects() {
    const searchInput = document.getElementById("search-input");
    const techFilter = document.getElementById("tech-filter");

    const query = searchInput ? searchInput.value.toLowerCase().trim() : "";
    const selectedTech = techFilter ? techFilter.value : "all";

    // filter with arrow function
    const filtered = allProjects.filter(project => {
        const matchesTitle = project.title.toLowerCase().includes(query);
        const matchesTech = selectedTech === "all" || project.tech.includes(selectedTech);
        return matchesTitle && matchesTech;
    });

    renderTable(filtered);
}

// ==========================================
// 7. Array map and reduce demonstration
// ==========================================

/**
 * Demonstrates map and reduce on the projects array:
 * map: builds an array of project titles.
 * reduce: counts the total technologies referenced across all projects.
 */
function calculateStats(projects) {
    // 1. map to build a list of titles
    const titlesList = projects.map(p => p.title);
    console.log("Projects Map (titles):", titlesList);

    // 2. reduce to count technologies used in total
    const totalTechInstances = projects.reduce((acc, curr) => acc + curr.tech.length, 0);
    console.log("Total technologies listed (reduce):", totalTechInstances);

    const statsBanner = document.getElementById("stats-summary");
    if (statsBanner) {
        statsBanner.textContent = `Portfolio includes ${titlesList.length} verified projects with ${totalTechInstances} technology applications.`;
    }
}

// ==========================================
// 8. Bonus: Sort projects by title
// ==========================================

/**
 * Toggles sorting of the project table by title alphabetically (Bonus).
 */
function toggleSortByTitle() {
    sortAscending = !sortAscending;
    const sorted = [...allProjects].sort((a, b) => {
        return sortAscending
            ? a.title.localeCompare(b.title)
            : b.title.localeCompare(a.title);
    });

    const sortBtn = document.getElementById("sort-btn");
    if (sortBtn) {
        sortBtn.textContent = `Sort Title (${sortAscending ? "A-Z" : "Z-A"})`;
    }

    renderTable(sorted);
}

// Kick off when DOM is ready
if (typeof window !== "undefined") {
    window.addEventListener("DOMContentLoaded", () => {
        initializeProjects();
    });
    // Expose symbols on window for browser scripts and test runners
    window.Project = Project;
    window.FeaturedProject = FeaturedProject;
    window.projectDataset = projectDataset;
    Object.defineProperty(window, "allProjects", {
        get: () => allProjects,
        set: (val) => { allProjects = val; },
        configurable: true
    });
    window.loadProjects = loadProjects;
    window.initializeProjects = initializeProjects;
    window.renderTable = renderTable;
    window.renderFeaturedCards = renderFeaturedCards;
    window.populateTechDropdown = populateTechDropdown;
    window.filterProjects = filterProjects;
    window.calculateStats = calculateStats;
    window.toggleSortByTitle = toggleSortByTitle;
}
