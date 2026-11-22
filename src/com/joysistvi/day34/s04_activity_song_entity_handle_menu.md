# Session 4 Activity: Song Entity, View `handleMenu()` Methods, & Full Dashboard Routing

## Objective

By the end of this activity, you will:

1. Build the **`Song`** entity across all 3 tiers (`Model`, `Repository`, `Service`, `View`) alongside the remaining management modules (`Artist`, `Album`, `User`).

2. Add a `handleMenu()` method to every View class (`SongView`, `ArtistView`, `AlbumView`, `UserView`) following the same menu loop pattern used in `AlbumView`.

3. Wire all four management modules into `App.java` and call each View's `handleMenu()` from `dashboard()` so all management flows are tested and working end-to-end.

## Architecture Reminders (3-Tier Rules)

* **Model (`com.joysistvi.recordingapp.model`)**: Plain data fields, constructors, getters, and setters (including `is_archived` / `archived` support where applicable).

* **Repository (`com.joysistvi.recordingapp.repository`)**: `PreparedStatement` SQL queries only; handle `SQLException` cleanly and return domain models/lists/booleans.

* **Service (`com.joysistvi.recordingapp.service`)**: Guard clauses and business validation rules (non-blank strings, positive IDs `> 0`, valid ranges, valid emails, password length, and `BCrypt` hashing/verification strictly in `UserServiceImpl`).

* **View (`com.joysistvi.recordingapp.view`)**: Console prompts, formatted tables, `promptForInt(...)` helper, and `handleMenu()` loops. No raw SQL or business validation rules in the View.

* **`App.java` (`com.joysistvi.recordingapp.App`)**: Bottom-up dependency wiring (`DbConnection -> Repository -> Service -> View`), authentication entry (`showAuthMenuAndAuthenticate()`), and routing via `dashboard()`. No raw SQL or business validation rules inside `App.java`.

## Task 1: Implement the `Song` Entity Across All Layers

Create and wire the `Song` module across your packages:

1. **`model/Song.java`**

   * Define fields for `id`, `title` (or `name`), `albumId` (and optional `albumName` if joined with `albums`), `duration` or `trackNumber` (matching your `songs` table columns), and `archived` (`is_archived`).

   * Include constructors, getters, and setters.

2. **`repository/SongRepository.java` & `repository/SongRepositoryImpl.java`**

   * Implement active listing (`is_archived = 0`), archived listing (`is_archived = 1`), `save(Song)`, `update(Song)`, `deleteById(int)`, `archiveById(int)`, `restoreById(int)`, and search by title/name.

   * Use `PreparedStatement` and check your actual `songs` table column names before running queries.

3. **`service/SongService.java` & `service/SongServiceImpl.java`**

   * Implement CRUD, archive, restore, and search methods.

   * Enforce guard clauses for blank strings, non-positive IDs (`<= 0`), and invalid numeric fields before calling the repository.

4. **`view/SongView.java`**

   * Implement display/table methods, create/update/search/archive/restore/delete prompts (displaying archived songs first before prompting for an ID to restore), `promptForInt(...)`, and `handleMenu()`.

## Task 2: Add `handleMenu()` to Every View Class

Every View class must implement `handleMenu()` (matching the pattern in `AlbumView`) that loops until the user chooses `0` to go back:

* **`SongView`**: `handleMenu()` — View Active, Create, Update, Search, Archive, Restore, Delete, and `0. Back`.

* **`ArtistView`**: `handleMenu()` — View Active, Create, Update, Search, Archive, Restore, Delete, and `0. Back`.

* **`AlbumView`**: `handleMenu()` — View Active, Create, Update, Search, Archive, Restore, Delete, and `0. Back`.

* **`UserView`**: `handleMenu()` — View Active, Create/Register, Update, Search, Archive, Restore, Delete, and `0. Back`.

### Reference Pattern (`handleMenu()` Loop)

```java
public void handleMenu() {
    while (true) {
        System.out.println("\n=== MANAGEMENT MENU ===");
        System.out.println("1. View All Active");
        System.out.println("2. Create");
        System.out.println("3. Update");
        System.out.println("4. Search");
        System.out.println("5. Archive (Soft Delete)");
        System.out.println("6. Restore Archived");
        System.out.println("7. Delete (Permanent)");
        System.out.println("0. Back");

        int choice = promptForInt("Choose an option: ");
        switch (choice) {
            case 1 -> displayAllActive();
            case 2 -> promptCreate();
            case 3 -> promptUpdate();
            case 4 -> promptSearch();
            case 5 -> promptArchive();
            case 6 -> promptRestore();
            case 7 -> promptDelete();
            case 0 -> {
                return;
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
    }
}
```

## Task 3: Connect All Management Modules in `App.java` via `dashboard()`

In `App.java`:

1. Wire `Song`, `Artist`, `Album`, and `User` bottom-up (`DbConnection -> Repository -> Service -> View`).

2. Keep `showAuthMenuAndAuthenticate()` as the entry point in `run()`, then call `dashboard()` once a user is authenticated (`currentUser != null`).

3. Inside `dashboard()`, connect each case so **all four management modules** call their respective View `handleMenu()` method:

```java
private void dashboard() {
    while (true) {
        System.out.println("\n1. Song Management");
        System.out.println("2. Artist Management");
        System.out.println("3. Album Management");
        System.out.println("4. Users Management");
        System.out.println("0. Back");

        int choice = promptForInt("Choice: ");
        switch (choice) {
            case 1 -> songView.handleMenu();
            case 2 -> artistView.handleMenu();
            case 3 -> albumView.handleMenu();
            case 4 -> userView.handleMenu();
            case 0 -> {
                return;
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
}
```

---

## Task 4: Implement Playlist Management (CRUD, Archive/Restore, and Search)

Implement the `Playlist` entity across the 3-tier architecture (`Model`, `Repository`, `Service`, `View`) to handle playlist management operations. Focus on the `playlists` table CRUD, soft-delete (`is_archived`), and search in this activity—handling of the `playlist_songs` junction table will be covered in the discussion part.

### 1. `Playlist` Model (`model/Playlist.java`)
Create the `Playlist` entity with fields matching your `playlists` table (such as `id`, `name`, `userId`, optional `username` for joined display, and `archived` for `is_archived`), along with constructors, getters, and setters.

### 2. `PlaylistRepository` & `PlaylistRepositoryImpl`
Implement the following repository methods using `DbConnection` and `PreparedStatement`:
- `List<Playlist> findAllActive()` — retrieve active playlists (`WHERE is_archived = 0`)
- `List<Playlist> findAllArchived()` — retrieve archived playlists (`WHERE is_archived = 1`)
- `boolean save(Playlist playlist)` — insert a new playlist into the `playlists` table
- `boolean update(Playlist playlist)` — update existing playlist details in the `playlists` table
- `boolean deleteById(int id)` — permanently delete a playlist (`DELETE FROM playlists WHERE id = ?`)
- `boolean archiveById(int id)` — soft-delete a playlist (`UPDATE playlists SET is_archived = 1 WHERE id = ?`)
- `boolean restoreById(int id)` — restore an archived playlist (`UPDATE playlists SET is_archived = 0 WHERE id = ?`)
- `List<Playlist> searchByName(String keyword)` — search active playlists by name (`WHERE is_archived = 0 AND name LIKE ?`)

### 3. `PlaylistService` & `PlaylistServiceImpl`
Implement the service layer delegating to `PlaylistRepository` with proper input validation and guard clauses (such as non-blank `name` / `keyword` and positive `id` / `userId`):
- `getAllActivePlaylists()`
- `getAllArchivedPlaylists()`
- `createPlaylist(String name, int userId)`
- `updatePlaylist(int id, String name, int userId)`
- `deletePlaylist(int id)`
- `archivePlaylist(int id)`
- `restorePlaylist(int id)`
- `searchPlaylists(String keyword)`

### 4. `PlaylistView` with `handleMenu()`
Create `PlaylistView` with a `handleMenu()` loop covering:
- View All Active Playlists
- Create Playlist
- Update Playlist
- Search Playlists
- Archive Playlist
- Restore Playlist (display archived playlists first; return early if none exist)
- Delete Playlist
- `0. Back` to return to the caller menu

> **Note:** Junction table (`playlist_songs`) operations (adding/removing songs in a playlist) are intentionally set aside for the discussion part so this activity stays focused on `Playlist` CRUD and lifecycle management.

*(Tip: Wrapping `dashboard()` in a `while (true)` loop lets the user return to the dashboard after finishing with one management module, and pressing `0` exits back to `run()` to log out.)*

## Verification & Testing Checklist
Before submitting Session 4, test all management options (`Song`, `Artist`, `Album`, `User`, and `Playlist`) from `dashboard()`:
- [ ] **Invalid Input Guard Clauses:** Blank names/titles/keywords, IDs `<= 0`, invalid numbers, out-of-range values, invalid emails, or short passwords are rejected cleanly without crashing or corrupting data.
- [ ] **CRUD + Search:** Creating, viewing active records, searching by keyword, updating records, and permanently deleting records work for `Song`, `Artist`, `Album`, `User`, and `Playlist`.
- [ ] **Soft Delete & Restore (`is_archived`):** Archiving sets `is_archived = 1` (hiding the record from active lists), while restoring displays archived records (`is_archived = 1`) first and sets `is_archived = 0` so it reappears in active lists.
- [ ] **Authentication (BCrypt):** Registering creates a valid BCrypt hash, and logging in succeeds with matching credentials while rejecting wrong passwords or unknown usernames.
- [ ] **Navigation:** Choosing `0` inside any management menu (`handleMenu()`) returns to `dashboard()`, and choosing `0` in `dashboard()` logs out and returns to `showAuthMenuAndAuthenticate()`.

## Submission

1. Push your completed project (including `model/`, `repository/`, `service/`, `view/`, and `App.java`) to your GitHub repository.
2. Submit the link to your GitHub repository along with screenshots showing working CRUD, soft-delete/restore, and login execution across all four management modules (`Song`, `Artist`, `Album`, `User`).