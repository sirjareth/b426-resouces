# Session 2 Activity: Adding Guard-Clause Validations in `Main`

## Overview
In this activity, you will add **input validation guard clauses** to the existing methods in `com.joysistvi.recordingapp.main.Main` before running database queries (`PreparedStatement` / `Statement`) or password hashing (`BCrypt`).

### What to Remember
- **Validation comes first:** Validate parameters at the very top of a method so invalid inputs fail fast and never hit the database unnecessarily.
- **Guard Clause pattern:** Check for invalid conditions early, print a clear `Validation Error: ...` message, and `return;` immediately.
- **Keep everything inside `Main`:** Do not split into separate layers/packages yet in this activity.

---

## Target Methods in `Main`

### 1. Albums Methods
Add appropriate validation guard clauses to each album method:
- **`createAlbum(String name, int year, int artistId)`**
  - Check that `name` is not `null` or blank (`trim().isEmpty()`).
  - Check that `year` is valid (e.g., greater than `0` and within a reasonable release year range such as `1900` to `2100`).
  - Check that `artistId` is positive (`artistId > 0`).
- **`updateAlbum(int id, String name, int year, int artistId)`**
  - Check that `id` is positive (`id > 0`).
  - Check that `name` is not `null` or blank.
  - Check that `year` is valid.
  - Check that `artistId` is positive (`artistId > 0`).
- **`deleteAlbum(int id)`**
  - Check that `id` is positive (`id > 0`).
- **`archiveAlbum(int id)`**
  - Check that `id` is positive (`id > 0`).
- **`restoreAlbum(int id)`**
  - Check that `id` is positive (`id > 0`).
- **`searchAlbum(String keyword)`**
  - Check that `keyword` is not `null` and not blank after `trim()` (or handle empty/null search cleanly with a validation message if required).

---

### 2. Artists Methods
Add validation guard clauses to each artist method:
- **`createArtist(String name)`**
  - Check that `name` is not `null` or blank (`trim().isEmpty()`).
- **`updateArtist(int id, String name)`**
  - Check that `id` is positive (`id > 0`).
  - Check that `name` is not `null` or blank (`trim().isEmpty()`).
- **`deleteArtist(int id)`**
  - Check that `id` is positive (`id > 0`).
- **`archiveArtist(int id)`**
  - Check that `id` is positive (`id > 0`).
- **`restoreArtist(int id)`**
  - Check that `id` is positive (`id > 0`).
- **`searchArtist(String keyword)`**
  - Check that `keyword` is not `null` and not blank after `trim()` before running the `LIKE` query.

---

### 3. Users Methods
Review and complete validation guard clauses for user operations:
- **`createUser(String uname, String rawPassword, String email)`**
  - Ensure `uname` is non-null and non-blank.
  - Ensure `email` is non-null, non-blank, and has a basic valid format (e.g., contains `@` and `.`).
  - Ensure `rawPassword` is non-null and meets the minimum length (`length() >= 6`).
  - Keep validation **before** `BCrypt.hashpw(...)` and `INSERT INTO users`.
- **`loginUser(String uname, String candidatePassword)`**
  - Ensure `uname` is non-null and non-blank.
  - Ensure `candidatePassword` is non-null and non-empty before querying or calling `BCrypt.checkpw(...)`.

---

## Sample Guard Clause Format

```java
if (name == null || name.trim().isEmpty()) {
    System.out.println("\nValidation Error: Artist name is required.");
    return;
}

if (id <= 0) {
    System.out.println("\nValidation Error: ID must be greater than 0.");
    return;
}


```

## 📤 Submission Guidelines

* **Where to submit:** Google Classroom.
* **Deliverable:** Upload your completed `Main.java` file (`com.joysistvi.recordingapp.main`).