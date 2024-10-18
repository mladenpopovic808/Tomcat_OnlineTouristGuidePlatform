# Online Tourist Guide Platform

## Overview
The Online Tourist Guide Platform is designed to allow users to explore and discover destinations worldwide. The platform consists of two main components: a Content Management System (CMS) and a public platform for reading tourist articles.

## Features

### Content Management System (CMS)
- **Authentication and Authorization**
  - Users can log in using their email and password.
  - Two types of users: Content Editor and Administrator.
  
- **Content Editor Functionality**
  - Create, view, edit, and delete tourist articles.
  - Manage destinations and associated articles.

- **Administrator Functionality**
  - All content editor capabilities.
  - User management: add, edit, activate/deactivate users.

### Public Reading Platform
- Browse all published articles by destination.
- Filter articles by activities (e.g., skiing, hiking, swimming).
- Comment on articles and view existing comments.

## Entities
- **User**
  - Unique email, name, user type (Content Editor or Admin), status, and password (hashed).

- **Destination**
  - General information about the destination including name and description.

- **Article**
  - Title, text, creation time, number of visits, author, comments, and activities.

- **Activity**
  - Keywords describing tourist activities associated with destinations.

- **Comment**
  - Author's name, comment text, and creation date associated with an article.

## Getting Started

### Prerequisites
- [Node.js](https://nodejs.org/) for frontend development.
- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) for backend development.
- [MySQL](https://www.mysql.com/) for the database.


