# 🎵 Recording Studio Console App: Session 5 Activity: Final Submission Guide: Recording Studio Application

Before submitting your completed **Recording Studio Application**, please carefully review and follow the guidelines below. Your project will be evaluated not only on core CRUD functionality, but also on application stability, console user experience, repository completeness, and documentation quality.

---

## 1. Application Stability & Error Handling

Your application must remain stable and predictable throughout the entire user session:

* **Logout vs. Exit Behavior:** Logging out of an Admin or User account must return the user to the authentication menu (`Login / Register / Exit`). The application should **never** terminate abruptly upon logout—only when the user explicitly selects the Exit option.
* **Zero Unhandled Exceptions:** Thoroughly test edge cases such as entering letters or symbols into numeric prompts, pressing `Enter` on blank inputs, inputting non-existent IDs, or querying empty lists. Ensure proper exception handling (`NumberFormatException`, `InputMismatchException`, `NullPointerException`, `SQLException`, etc.) so the program never crashes during evaluation.
* **End-to-End Flow Verification:** Test all user and admin workflows (authentication, CRUD operations, soft-delete archiving/restoring, search, and playlist management) from start to finish to verify that no menu loops break or exit unexpectedly.

---

## 2. Console UI/UX Polish

Even though this is a console-based application, the output must be clean, readable, and visually pleasing to the user:

* **Aligned Tables & Columns:** Format all tabular outputs (Artists, Albums, Songs, Users, Playlists) with consistent borders (`+---+`) and fixed column widths using `printf`. Apply string truncation helpers (`...`) so long titles or names do not break table alignment.
* **Screen Clearing (`clearScreen`):** Implement screen clearing between major menu transitions (e.g., Login, Dashboards, Management Menus, Browse Catalog) so old prompts and outputs do not clutter the terminal screen.
* **Clear Prompts & Feedback:** Display concise, user-friendly prompts and confirmation/error messages after every action (e.g., creation, update, archive, restore, delete, login failure).

---

## 3. GitHub Repository Requirements (Public)

* **Public Visibility:** Your GitHub repository **must be set to Public** so your instructor can access and review your work without permission issues.
* **Complete Source Code:** Ensure all packages and layers (`model`, `repository`, `service`, `view`, `config`, and the main entry point `App.java`) along with `pom.xml` (and any required configuration files) are committed and pushed.
* **Include the Database Schema (`.sql`):** Add your `.sql` database/table creation script to the **root folder** of your repository (e.g., `schema.sql` or `recording_studio.sql`) or inside a clearly documented `database/` folder so the database can be recreated easily during testing.
  * *How to push your `.sql` file:*
    ```bash
    git add .
    git commit -m "Add database schema for Recording Studio App"
    git push origin main
    ```


---

## 4. Documentation Standards

* **Follow the Provided Template:** Include complete documentation based on the official template provided in class (covering project overview, architecture/layer wiring, setup instructions, and features).
* **Custom Additions Are Welcome:** You may expand or enhance the documentation with additional notes, database setup steps, or feature explanations—just make sure everything remains well-organized, properly formatted in Markdown, and consistent with your actual codebase.

---

## 5. Final LMS Submission

* **Submit on the LMS:** Do not forget to submit your **Public GitHub Repository link** (and any required deliverables) in our **LMS** before the deadline.
* **Verify Your Link:** Open your GitHub repository URL in an **Incognito / Private browser window** before submitting to confirm it is publicly accessible and displays your latest commit, documentation, and `.sql` file.

---

## Pre-Submission Checklist

- [ ] Logging out returns to the main authentication menu without closing the app.
- [ ] All invalid inputs and edge cases are handled without unhandled exceptions.
- [ ] All console tables have even, aligned columns and clean formatting.
- [ ] `clearScreen()` is implemented across major menu transitions for a tidy UI/UX.
- [ ] Complete source code and `.sql` database schema file are pushed to GitHub.
- [ ] GitHub repository is set to **Public** and verified in an Incognito window.
- [ ] Project documentation follows the provided template and is neatly organized.
- [ ] Final repository link is submitted on the **LMS**.