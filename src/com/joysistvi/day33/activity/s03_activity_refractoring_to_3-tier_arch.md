# 🎵 Recording Studio Console App: Session 3 Activity: Refactoring to 3-Tier Architecture (`Artist`, `Album`, and `User`)

## Objectives
By the end of this activity, trainees will be able to:
1. Refactor monolithic JDBC logic from `Main.java` into a 3-tier architecture (`view`, `service`, `repository`) with `config`, `model`, and `App`.
2. Wire dependencies bottom-up (`DbConnection` -> `Repository` -> `Service` -> `View` -> `App`) while keeping runtime calls top-down (`App` -> `view` -> `service` -> `repository`).
3. Enforce Java 8-compatible guard clauses and `BCrypt` password hashing/verification inside the `service` layer.
4. Implement full CRUD, soft-delete (`archive` / `restore`), and `search` operations across `Artist`, `Album`, and `User`.

---

## Starting Point
Before starting the tasks below, make sure you have verified end-to-end **Read** and **Create** for `Artist` across all layers (`model/Artist.java`, `repository/ArtistRepository.java` & `ArtistRepositoryImpl.java`, `service/ArtistService.java` & `ArtistServiceImpl.java`, `view/ArtistView.java`, and `App.java`).

---

## Task 1: Complete `Artist` Across All Layers
Refactor and complete the remaining `Artist` operations (`update`, `delete`, `archive`, `restore`, `search`, plus `findAllArchived` / `getAllArchivedArtists`):

1. **`ArtistRepository` & `ArtistRepositoryImpl`:**
   - Implement `findAllActive()`, `findAllArchived()`, `save(Artist)`, `update(Artist)`, `deleteById(int)`, `archiveById(int)`, `restoreById(int)`, and `searchByName(String)`.
   - Use `PreparedStatement` with `is_archived = 0` for active lists/search and `is_archived = 1` for archived lists.
2. **`ArtistService` & `ArtistServiceImpl`:**
   - Implement `getAllActiveArtists()`, `getAllArchivedArtists()`, `createArtist(String)`, `updateArtist(int, String)`, `deleteArtist(int)`, `archiveArtist(int)`, `restoreArtist(int)`, and `searchArtists(String)`.
   - Add guard clauses for blank strings (`name == null || name.trim().isEmpty()`, `keyword == null || keyword.trim().isEmpty()`) and non-positive IDs (`id <= 0`).
3. **`ArtistView`:**
   - Implement `displayAllArtists()`, `promptCreateArtist()`, `promptUpdateArtist()`, `promptDeleteArtist()`, `promptArchiveArtist()`, `promptRestoreArtist()`, and `promptSearchArtists()`.
   - In `promptRestoreArtist()`, **display archived artists first** (`is_archived = 1`). If none exist, exit early without asking for an ID; otherwise prompt for the ID to restore.

---

## Task 2: Refactor `Album` to 3-Tier Architecture
Build `model/Album.java`, `repository/AlbumRepository.java` & `AlbumRepositoryImpl.java`, `service/AlbumService.java` & `AlbumServiceImpl.java`, and `view/AlbumView.java`:

1. **`Album` Model (`com.joysistvi.recordingapp.model.Album`):**
   - Fields for `id`, `name`, `year`, `artistId`, `artistName` (for joined display), and `archived` (`is_archived`), with constructors, getters, and setters.
2. **`AlbumRepository` & `AlbumRepositoryImpl`:**
   - Implement `findAllActive()`, `findAllArchived()`, `save(Album)`, `update(Album)`, `deleteById(int)`, `archiveById(int)`, `restoreById(int)`, and `searchByName(String)`.
   - Use `JOIN artists ON albums.artist_id = artists.id` when reading/searching active or archived albums so `artist_name` is included.
3. **`AlbumService` & `AlbumServiceImpl`:**
   - Implement `getAllActiveAlbums()`, `getAllArchivedAlbums()`, `createAlbum(String, int, int)`, `updateAlbum(int, String, int, int)`, `deleteAlbum(int)`, `archiveAlbum(int)`, `restoreAlbum(int)`, and `searchAlbums(String)`.
   - Enforce guard clauses: non-blank `name` / `keyword`, valid release `year` (e.g., `1900` to `2100`), and positive IDs (`id > 0`, `artistId > 0`).
4. **`AlbumView`:**
   - Implement `displayAllAlbums()`, `promptCreateAlbum()`, `promptUpdateAlbum()`, `promptDeleteAlbum()`, `promptArchiveAlbum()`, `promptRestoreAlbum()`, and `promptSearchAlbums()`.
   - Show archived albums before prompting for an ID to restore.

---

## Task 3: Refactor & Complete `User` CRUD + Authentication
Build `model/User.java`, `repository/UserRepository.java` & `UserRepositoryImpl.java`, `service/UserService.java` & `UserServiceImpl.java`, and `view/UserView.java` with **full CRUD** plus authentication:

1. **`User` Model (`com.joysistvi.recordingapp.model.User`):**
   - Fields for `id`, `username`, `password` (hashed), `email`, `role`, and `archived` (`is_archived`), with constructors, getters, and setters.
2. **`UserRepository` & `UserRepositoryImpl`:**
   - Implement full CRUD and lookup methods: `findAllActive()`, `findAllArchived()`, `findByUsername(String)`, `save(User)`, `update(User)`, `deleteById(int)`, `archiveById(int)`, `restoreById(int)`, and `searchByUsername(String)`.
3. **`UserService` & `UserServiceImpl`:**
   - Implement `getAllActiveUsers()`, `getAllArchivedUsers()`, `createUser(String, String, String)`, `updateUser(int, String, String)`, `deleteUser(int)`, `archiveUser(int)`, `restoreUser(int)`, `searchUsers(String)`, and `loginUser(String, String)`.
   - Enforce guard clauses (non-blank username/email, valid email containing `@` and `.`, password length `>= 6`, positive IDs `> 0`) and keep `BCrypt.hashpw(...)` and `BCrypt.checkpw(...)` strictly inside `UserServiceImpl`.
4. **`UserView`:**
   - Implement console prompts and tables for creating/registering a user, viewing active users, updating user details, searching users, archiving/restoring/deleting users (showing archived users first before restoring), and logging in.

---

## Task 4: Wire All Modules in `App.java`
Update `App.java` (`com.joysistvi.recordingapp.App`):
- Wire `DbConnection` -> `Repository` -> `Service` -> `View` for `Artist`, `Album`, and `User` bottom-up inside `main(String[] args)`.
- Test both **invalid inputs** (to verify guard clauses stop execution cleanly) and **valid inputs** (to verify database inserts, updates, searches, soft-deletes/restores, deletes, and `BCrypt` login).
- Ensure no `Scanner` objects, raw SQL queries, or business validation rules exist inside `App.java`.

---

## Expected Behavior & Verification Checklist
- Invalid inputs (blank name/keyword, ID `<= 0`, out-of-range year, short password, invalid email) print a `Validation Error` and do **not** hit the database or hash passwords.
- Soft-deleted records (`is_archived = 1`) disappear from active views (`is_archived = 0`), appear in the archived list during `restore`, and return to active views once restored (`is_archived = 0`).
- `BCrypt.checkpw` succeeds with the matching plain password and rejects wrong passwords or unknown usernames.

## Submission Guidelines
- Push your completed Java 8 3-tier project (`Artist`, `Album`, and `User` across `config`, `model`, `repository`, `service`, `view`, and `App.java`) to a **GitHub repository**.
- Make sure your repository includes clear commit history, organized packages, and no uncompilable code or hardcoded credentials beyond the local `DbConnection` setup.
- Submit the **GitHub repository URL** as instructed for Session 3.