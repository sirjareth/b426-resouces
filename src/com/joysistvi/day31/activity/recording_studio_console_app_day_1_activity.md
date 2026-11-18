# 🎵 Recording Studio Console App: Day 1 Activity — Albums & Artists JDBC CRUD

## 🎯 Objectives

* Continue building JDBC database operations inside `com.joysistvi.recordingapp.main.Main` connected to `recording_studio_db`.
* Complete the remaining CRUD methods for the **`albums`** table (`updateAlbum` and `deleteAlbum`) following the `PreparedStatement` and `try-with-resources` pattern.
* Implement full CRUD methods for the **`artists`** table (`viewAllArtists`, `createArtist`, `updateArtist`, and `deleteArtist`) inside `Main.java`.
* Verify and test all database methods directly inside the `main(String[] args)` method using hardcoded arguments (no `Scanner` or menu loop required yet).

## 📝 Instructions

### Step 1: Review Your Starter Code (`Main.java`)

1. Open `src/main/java/com/joysistvi/recordingapp/main/Main.java` in **IntelliJ IDEA**.
2. Confirm that your database connection (`recording_studio_db`) and the first two `albums` methods (`viewAllAlbums()` and `createAlbum(String name, int year, int artistId)`) are working properly.
3. Keep all methods `public static` inside `Main.java` for this session. Do **not** add `Scanner` inputs or a `switch` dashboard menu yet.

### Step 2: Complete the Remaining `albums` CRUD Methods (2 Methods)

Add the following two methods to `Main.java` to finish the `albums` table CRUD operations:

1. **`updateAlbum(int id, String name, int year, int artistId)`**
   * Write a parameterized SQL `UPDATE` query:
     ```sql
     UPDATE albums SET name = ?, year = ?, artist_id = ? WHERE id = ?
     ```
   * Use `try-with-resources` with `connect()` and `PreparedStatement`.
   * Bind all four parameters (`name`, `year`, `artistId`, and `id`) and call `executeUpdate()`.
   * Check the affected rows (`int rowsAffected = preparedStatement.executeUpdate();`):
     * If `rowsAffected > 0`, print `"Album ID " + id + " updated successfully!"` and call `viewAllAlbums()`.
     * Otherwise, print `"No album found with ID " + id + "."`.

2. **`deleteAlbum(int id)`**
   * Write a parameterized SQL `DELETE` query:
     ```sql
     DELETE FROM albums WHERE id = ?
     ```
   * Use `try-with-resources` with `connect()` and `PreparedStatement`.
   * Bind the `id` parameter and check `executeUpdate()`:
     * If `rowsAffected > 0`, print `"Album ID " + id + " deleted successfully!"` and call `viewAllAlbums()`.
     * Otherwise, print `"No album found with ID " + id + "."`.
   * Catch `SQLException` and print `"Delete Album: " + e.getMessage()` (this will also display the MySQL constraint message if a song is still linked to the album via `ON DELETE RESTRICT`).

### Step 3: Implement Complete `artists` CRUD Methods (4 Methods)

Still inside `Main.java`, implement all four CRUD operations for the **`artists`** table following the exact same structure as `albums`:

1. **`viewAllArtists()`**
   * Query `SELECT id, name FROM artists` using `Statement` and `ResultSet`.
   * Display the results in a clean formatted table using `System.out.printf("%-4s %-25s%n", "ID", "Artist Name")`.

2. **`createArtist(String name)`**
   * Use `PreparedStatement` to execute `INSERT INTO artists (name) VALUES (?)`.
   * Print a confirmation message and call `viewAllArtists()`.

3. **`updateArtist(int id, String name)`**
   * Use `PreparedStatement` to execute `UPDATE artists SET name = ? WHERE id = ?`.
   * Check `rowsAffected` to confirm the update (and call `viewAllArtists()`), or print `"No artist found with ID " + id + "."`.

4. **`deleteArtist(int id)`**
   * Use `PreparedStatement` to execute `DELETE FROM artists WHERE id = ?`.
   * Check `rowsAffected` to confirm deletion (and call `viewAllArtists()`), or print `"No artist found with ID " + id + "."`.
   * Handle `SQLException` cleanly in the `catch` block.

### Step 4: Test All Methods in `main(String[] args)`

In your `main` method, invoke and test each method using hardcoded sample values (comment/uncomment calls as you verify each step):

```java
public static void main(String[] args) {
    // --- Testing Albums CRUD ---
    viewAllAlbums();
    createAlbum("Coco", 1990, 1);
    updateAlbum(6, "Coco (Remastered)", 1995, 1);
    deleteAlbum(6);

    // --- Testing Artists CRUD ---
    viewAllArtists();
    createArtist("IV of Spades");
    updateArtist(6, "IV Of Spades");
    deleteArtist(6);
}
```

> **Note:** Adjust the `id` values (`6` in the example above) to match the actual `AUTO_INCREMENT` ID generated in your local `recording_studio_db` database.

## 💻 Sample Output

```text
----- Artists -----
ID   Artist Name              
1    Ben&Ben                  
2    Eraserheads              
3    Moira Dela Torre         
4    SB19                     
5    Zack Tabudlo             
Artist: IV of Spades created successfully!

----- Artists -----
ID   Artist Name              
1    Ben&Ben                  
2    Eraserheads              
3    Moira Dela Torre         
4    SB19                     
5    Zack Tabudlo             
6    IV of Spades             
Artist ID 6 updated successfully!
Artist ID 6 deleted successfully!
```

## 📤 Submission Guidelines

* **Where to submit:** Google Classroom.
* **Deliverable:** Upload your completed `Main.java` file (`com.joysistvi.recordingapp.main`) containing all 4 `albums` CRUD methods and all 4 `artists` CRUD methods, verified with test calls inside `main()`.

## 🚀 Stretch Goal

* **Test Foreign Key Restriction (`ON DELETE RESTRICT`):** Inside `main()`, try calling `deleteArtist(1)` (an artist that already has albums in `albums`) and observe the `SQLException` message caught by your `catch` block.
* **Find by ID Helper:** Create `getArtistById(int id)` and `getAlbumById(int id)` methods that execute `SELECT ... WHERE id = ?` and print a single matching record.