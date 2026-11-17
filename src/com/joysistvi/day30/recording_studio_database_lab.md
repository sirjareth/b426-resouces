# 🗄️ Recording Studio Database Lab: Full Schema Setup & Data Seeding

## 🎯 Objectives

* Create the `recording_studio_db` database in phpMyAdmin using `utf8mb4_unicode_ci` collation.
* Build the `artists`, `albums`, and `songs` tables with appropriate data types, primary keys, and constraints.
* Establish Foreign Key relationships linking `albums` to `artists` and `songs` to `albums` using `ON DELETE RESTRICT` and `ON UPDATE CASCADE`.
* Manually insert OPM (Original Pilipino Music) seed data across all three tables using the phpMyAdmin interface.
* Export the complete database into an `.sql` file for submission on Google Classroom.

---

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
| :--- | :--- | :--- | :--- | :--- |
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
| :--- | :--- | :--- | :--- | :--- |
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
| :--- | :--- | :--- | :--- | :--- |
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

### Step 5: Insert OPM Seed Data (In Order!)

Because of the Foreign Key constraints, you must insert data starting from the highest parent table down to the child tables: **`artists` $\rightarrow$ `albums` $\rightarrow$ `songs`**.

1. **Insert into `artists`:** Go to the `artists` table $\rightarrow$ **Insert** tab, and add **at least 5 OPM artists/bands** (e.g., *Ben&Ben*, *Eraserheads*, *Moira Dela Torre*, *SB19*, *Zack Tabudlo*).
2. **Insert into `albums`:** Go to the `albums` table $\rightarrow$ **Insert** tab, and add **at least 5 albums** matching the `artist_id` values generated in the `artists` table.
3. **Insert into `songs`:** Go to the `songs` table $\rightarrow$ **Insert** tab, and add **at least 10 OPM songs** matching the `album_id` values generated in the `albums` table.
4. Use the **Browse** tab on each table to verify that all records are properly saved and linked.

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

* Open the **Designer** tab at the database level (`recording_studio_db`) to inspect your visual Entity-Relationship Diagram (ERD).
* Test the `ON DELETE RESTRICT` rule by attempting to delete an artist from the `artists` table that still has connected albums—observe the foreign key constraint error message generated by MySQL.