# KPopDMS - K-Pop Data Management System

KPopDMS is a desktop application that helps you store, update, delete, and rank K-Pop groups using a SQLite database.  
You can manage group names, debut dates, members, agencies, and popularity scores through an easy-to-use interface.  
All data is saved in a `.db` file, and no external file management is needed.

## Features

- Connect to a SQLite database file
- Add a new group with proper input validation
- View all stored groups in a table
- Update existing group details
- Delete a group when needed
- Rank groups by popularity score

## Database Structure

The program expects a table named `groups` with the following columns:
name, debut_date, members, agency, latest_album, status, popularity_score

- `members`: separated by commas  
- `status`: must be "active", "disbanded", or "hiatus"  
- `popularity_score`: must be a number (e.g. 0–100)  

If the table does not follow this structure, the program may not work correctly.
