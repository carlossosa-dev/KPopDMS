# KPopDMS - K-Pop Data Management System

KPopDMS is a simple command-line program that helps store, update, and rank K-Pop groups. Whether you want to keep track of debut dates, members, agencies, or rank groups based on popularity, this program makes it easy. It provides a straightforward way to manage K-Pop group data without needing a database. The program loads and saves data from a text file, allowing you to update your list anytime and share it with others.

## Features

- Load data from a file – Import group details from a `.txt` file.  
- Add a new group – Input a new group with proper validation.  
- View groups – See all stored groups and their details.  
- Update group info – Modify an existing group’s details.  
- Delete a group – Remove a group when needed.  
- Rank groups by popularity – Automatically sort groups based on their popularity score.  

The program expects group data to be formatted like this:

Group Name,Debut Date,Member1|Member2|...,Agency,Latest Album,Status,Popularity Score

Members are separated by | (not commas).
Status must be "active", "disbanded", or "hiatus".
Popularity score should be a number between 0-100.
If the data does not follow this format, the program will not process it correctly.

