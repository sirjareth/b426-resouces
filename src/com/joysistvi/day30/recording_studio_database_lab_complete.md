# 🗄️ Recording Studio Database Lab: Full Schema Setup & Data Seeding

## 🎯 Objectives

* Create the `recording_studio_db` database in phpMyAdmin using `utf8mb4_unicode_ci` collation.

* Build all six core tables (`artists`, `albums`, `songs`, `users`, `playlists`, and `playlist_songs`) with appropriate data types, primary keys, unique indexes, and constraints.

* Establish Foreign Key relationships across all parent-child and many-to-many junction tables using `ON DELETE RESTRICT` and `ON UPDATE CASCADE`.

* Manually insert OPM (Original Pilipino Music) seed data, sample users, and playlists using the phpMyAdmin interface.

* Export the complete database into an `.sql` file for submission on Google Classroom.

## 📝 Instructions

### Step 1: Create the Database (`recording_studio_db`)

1. Open your browser and navigate to **phpMyAdmin** (`http://localhost/phpmyadmin`).

2. Click on the **Databases** tab at the top navigation bar (or click **New** in the left sidebar).

3. Under **Create database**, enter the following details:

   * **Database name:** `recording_studio_db`

   * **Collation:** `utf8mb4_unicode_ci`

4. Click **Create**.

---

### Step 2: Create the `artists` Table (Parent Table)

1. Make sure **`recording_studio_db`** is selected in the left sidebar.

2. In the **Create table** section, enter:

   * **Table name:** `artists`

   * **Number of columns:** `2`

3. Click **Create**, then configure the columns as follows:

| Field (Column Name) | Type | Length / Values | Attributes / Default | Extra / Notes | 
| ----- | ----- | ----- | ----- | ----- | 
| **id** | `INT` | *(leave blank)* | Not Null | Check the **A_I** (Auto Increment) box to set as Primary Key | 
| **name** | `VARCHAR` | `100` | Not Null | Name of the artist or band | 

> **Note:** Click **Go** when prompted to add a Primary Index after checking **A_I**. Click **Save** at the bottom.

---

### Step 3: Create the `albums` Table (Child of `artists` / Parent of `songs`)

1. Click on **`recording_studio_db`** in the left sidebar to return to the database level.

2. In the **Create table** section, enter:

   * **Table name:** `albums`

   * **Number of columns:** `4`

3. Click **Create**, then configure the columns as follows:

| Field (Column Name) | Type | Length / Values | Attributes / Default | Extra / Notes | 
| ----- | ----- | ----- | ----- | ----- | 
| **id** | `INT` | *(leave blank)* | Not Null | Check the **A_I** (Auto Increment) box to set as Primary Key | 
| **name** | `VARCHAR` | `100` | Not Null | Title of the album | 
| **year** | `INT` | *(leave blank)* | Not Null | Release year of the album | 
| **artist_id** | `INT` | *(leave blank)* | Not Null | Foreign Key referencing `artists(id)` | 

4. Click **Save** at the bottom.

#### Set Up the Foreign Key for `albums`:

1. Open the **`albums`** table, go to the **Structure** tab, and click **Relation view** below the column list.

2. Configure the row for `artist_id`:

   * **Constraint name:** `fk_albums_artist`

   * **Column:** `artist_id`

   * **Database:** `recording_studio_db`

   * **Table:** `artists`

   * **Foreign key:** `id`

   * **ON DELETE:** `RESTRICT`

   * **ON UPDATE:** `CASCADE`

3. Click **Save**.

---

### Step 4: Create the `songs` Table (Child of `albums`)

1. Click on **`recording_studio_db`** in the left sidebar to return to the database level.

2. In the **Create table** section, enter:

   * **Table name:** `songs`

   * **Number of columns:** `6`

3. Click **Create**, then configure the columns as follows:

| Field (Column Name) | Type | Length / Values | Attributes / Default | Extra / Notes | 
| ----- | ----- | ----- | ----- | ----- | 
| **id** | `INT` | *(leave blank)* | Not Null | Check the **A_I** (Auto Increment) box to set as Primary Key | 
| **title** | `VARCHAR` | `150` | Not Null | Title of the song | 
| **genre** | `VARCHAR` | `50` | Not Null | Musical genre (e.g., Folk Pop, Pinoy Rock) | 
| **length** | `VARCHAR` | `10` | Not Null | Stored as text (e.g., `3:45`) | 
| **album_id** | `INT` | *(leave blank)* | Not Null | Foreign Key referencing `albums(id)` | 
| **is_archived** | `TINYINT` | `1` | Default: `0` | `0` = Active (`FALSE`), `1` = Archived (`TRUE`) | 

4. Click **Save** at the bottom.

#### Set Up the Foreign Key for `songs`:

1. Open the **`songs`** table, go to the **Structure** tab, and click **Relation view** below the column list.

2. Configure the row for `album_id`:

   * **Constraint name:** `fk_songs_album`

   * **Column:** `album_id`

   * **Database:** `recording_studio_db`

   * **Table:** `albums`

   * **Foreign key:** `id`

   * **ON DELETE:** `RESTRICT`

   * **ON UPDATE:** `CASCADE`

3. Click **Save**.

---

### Step 5: Create the `users` Table (Parent of `playlists`)

1. Click on **`recording_studio_db`** in the left sidebar to return to the database level.

2. In the **Create table** section, enter:

   * **Table name:** `users`

   * **Number of columns:** `6`

3. Click **Create**, then configure the columns as follows:

| Field (Column Name) | Type | Length / Values | Attributes / Default | Index / Extra | 
| ----- | ----- | ----- | ----- | ----- | 
| **id** | `INT` | *(leave blank)* | Not Null | Check **A_I** (Auto Increment) — sets as `PRIMARY` | 
| **username** | `VARCHAR` | `50` | Not Null | Select `UNIQUE` under the **Index** dropdown | 
| **password** | `VARCHAR` | `255` | Not Null | Stores hashed passwords | 
| **email** | `VARCHAR` | `100` | Not Null | Select `UNIQUE` under the **Index** dropdown | 
| **role** | `VARCHAR` | `10` | Default: `'USER'` | Stores `'USER'` or `'ADMIN'` | 
| **created_at** | `TIMESTAMP` | *(leave blank)* | Default: `CURRENT_TIMESTAMP` | Automatically records account creation date/time | 

4. Click **Save** at the bottom.

> **Optional Role Constraint:** Go to the **SQL** tab of `recording_studio_db` and run:  
> `ALTER TABLE users ADD CONSTRAINT chk_user_role CHECK (role IN ('USER', 'ADMIN'));`

---

### Step 6: Create the `playlists` Table (Child of `users`)

1. Click on **`recording_studio_db`** in the left sidebar to return to the database level.

2. In the **Create table** section, enter:

   * **Table name:** `playlists`

   * **Number of columns:** `4`

3. Click **Create**, then configure the columns as follows:

| Field (Column Name) | Type | Length / Values | Attributes / Default | Extra / Notes | 
| ----- | ----- | ----- | ----- | ----- | 
| **id** | `INT` | *(leave blank)* | Not Null | Check the **A_I** (Auto Increment) box to set as Primary Key | 
| **name** | `VARCHAR` | `100` | Not Null | Name of the playlist | 
| **date_created** | `TIMESTAMP` | *(leave blank)* | Default: `CURRENT_TIMESTAMP` | Automatically records playlist creation date/time | 
| **user_id** | `INT` | *(leave blank)* | Not Null | Foreign Key referencing `users(id)` | 

4. Click **Save** at the bottom.

#### Set Up the Foreign Key for `playlists`:

1. Open the **`playlists`** table, go to the **Structure** tab, and click **Relation view** below the column list.

2. Configure the row for `user_id`:

   * **Constraint name:** `fk_playlists_user`

   * **Column:** `user_id`

   * **Database:** `recording_studio_db`

   * **Table:** `users`

   * **Foreign key:** `id`

   * **ON DELETE:** `RESTRICT`

   * **ON UPDATE:** `CASCADE`

3. Click **Save**.

---

### Step 7: Create the `playlist_songs` Junction Table (Many-to-Many)

This bridge table connects `playlists` and `songs` so that one playlist can have multiple songs, and one song can appear in multiple playlists.

1. Click on **`recording_studio_db`** in the left sidebar to return to the database level.

2. In the **Create table** section, enter:

   * **Table name:** `playlist_songs`

   * **Number of columns:** `2`

3. Click **Create**, then configure the columns as follows:

| Field (Column Name) | Type | Length / Values | Attributes / Default | Index / Extra | 
| ----- | ----- | ----- | ----- | ----- | 
| **playlist_id** | `INT` | *(leave blank)* | Not Null | Select `PRIMARY` under the **Index** dropdown | 
| **song_id** | `INT` | *(leave blank)* | Not Null | Select `PRIMARY` under the **Index** dropdown | 

> **Note on Composite Primary Key:** Setting both `playlist_id` and `song_id` as `PRIMARY` creates a composite key that prevents duplicate entries of the same song in the same playlist.

4. Click **Save** at the bottom.

#### Set Up Both Foreign Keys for `playlist_songs`:

1. Open the **`playlist_songs`** table, go to the **Structure** tab, and click **Relation view** below the column list.

2. Configure the **first constraint** (`playlist_id`):
   * **Constraint name:** `fk_playlist_songs_playlist`
   * **Column:** `playlist_id`
   * **Database:** `recording_studio_db`
   * **Table:** `playlists`
   * **Foreign key:** `id`
   * **ON DELETE:** `RESTRICT` | **ON UPDATE:** `CASCADE`

3. Configure the **second constraint** (`song_id`):
   * **Constraint name:** `fk_playlist_songs_song`
   * **Column:** `song_id`
   * **Database:** `recording_studio_db`
   * **Table:** `songs`
   * **Foreign key:** `id`
   * **ON DELETE:** `RESTRICT` | **ON UPDATE:** `CASCADE`

4. Click **Save**.

---

### Step 8: Insert Seed Data (In Parent-to-Child Order!)

Because of the Foreign Key constraints, always insert records into parent tables before child tables:

1. **Insert into `artists`:** Add **at least 5 OPM artists/bands** (e.g., *Ben&Ben*, *Eraserheads*, *Moira Dela Torre*, *SB19*, *Zack Tabudlo*).
2. **Insert into `albums`:** Add **at least 5 albums** linked to valid `artist_id` values.
3. **Insert into `songs`:** Add **at least 10 OPM songs** linked to valid `album_id` values.
4. **Insert into `users`:** Add **at least 3 users** (include at least `1` account with role `ADMIN` and `2` accounts with role `USER`).
5. **Insert into `playlists`:** Add **at least 3 playlists** linked to valid `user_id` values.
6. **Insert into `playlist_songs`:** Link **at least 5 song entries** across your created playlists using valid `playlist_id` and `song_id` pairs.
7. Use the **Browse** tab on each table to verify that all records are properly saved and linked.

---

## 📤 Submission Guidelines

* **Where to submit:** Google Classroom.

* **How to submit:**

  1. Click on the root database name **`recording_studio_db`** in the left sidebar.

  2. Go to the **Export** tab in the top navigation bar.

  3. Keep the export method set to **Quick** and the format set to **SQL**, then click **Go**.

  4. Upload the downloaded `.sql` file directly to the Google Classroom assignment.

---

## 🚀 Stretch Goal

* Open the **Designer** tab at the database level (`recording_studio_db`) to inspect your complete visual Entity-Relationship Diagram (ERD) showing all 6 connected tables.

* Test the `ON DELETE RESTRICT` rule by attempting to delete a user from the `users` table who already owns a playlist—observe the foreign key constraint error message generated by MySQL.