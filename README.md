# Gmail Clone – JSP MVC Web Application

A Gmail-inspired web application built using JSP, Servlets, JDBC, MySQL, HTML, CSS, and JavaScript following the MVC architecture pattern. This project was created to practice Java web development concepts such as authentication, session management, database connectivity, and CRUD operations using Apache Tomcat.

---

## Features

- User Signup & Login Authentication
- Session Management
- Inbox Page
- Compose Mail Functionality
- Draft Saving System
- Sent Mail Section
- Mail View Page
- File Upload Support
- Download Attachment Feature
- Responsive UI with Custom CSS
- MVC Architecture using JSP + Servlets

---

## Tech Stack

### Frontend
- HTML5
- CSS3
- JavaScript
- JSP

### Backend
- Java Servlets
- JDBC
- Apache Tomcat

### Database
- MySQL

---

## Project Structure

```bash
Login-signup_jsp_mvc_Copy/
│
├── css/                  # Styling files
├── js/                   # JavaScript files
├── img/                  # Images and assets
├── upload/               # Uploaded files and attachments
├── WEB-INF/
│   ├── classes/          # Java classes and compiled files
│   └── web.xml           # Deployment descriptor
│
├── index.jsp             # Login page
├── Signup.jsp            # Registration page
├── inbox.jsp             # Inbox page
├── compose.jsp           # Compose mail page
├── send.jsp              # Sent mails
├── draft.jsp             # Draft mails
├── veiwmail.jsp          # View mail page
└── connect.jsp           # Database connection
```

---

## How It Works

1. User registers using the signup form.
2. Credentials are stored in the MySQL database.
3. Login authentication is handled using Servlets and JDBC.
4. After login, users can:
   - Compose emails
   - Save drafts
   - View inbox mails
   - Send mails
   - Upload attachments
5. Sessions are used to manage logged-in users.

---

## Installation & Setup

### Prerequisites

- Java JDK 8+
- Apache Tomcat 9+
- MySQL Server
- IDE (Eclipse / IntelliJ IDEA / NetBeans)

---

## Steps to Run

### 1. Clone the Repository

```bash
git clone <your-repository-link>
```

### 2. Import Project

Import the project into your Java IDE as a Dynamic Web Project.

### 3. Configure Tomcat

Add Apache Tomcat server in your IDE and deploy the project.

### 4. Configure Database

Create a MySQL database and required tables.

Update database credentials inside:

```bash
connect.jsp
or
Connect.java
```

Example:

```java
String url = "jdbc:mysql://localhost:3306/database_name";
String user = "root";
String password = "your_password";
```

### 5. Run the Project

Start Tomcat Server and open:

```bash
http://localhost:8080/project-name/
```

---

## Concepts Practiced

- MVC Architecture
- JSP & Servlet Communication
- JDBC Database Connectivity
- Session Handling
- Form Validation
- CRUD Operations
- File Upload & Download
- Dynamic Content Rendering

---

## Learning Purpose

This project was built for learning and practice purposes to strengthen understanding of Java web development fundamentals and backend integration using Servlets and JSP.

---

## Future Improvements

- Password Encryption
- Real Email Sending using SMTP
- Better UI/UX Design
- Search Functionality
- Pagination
- Role-based Authentication
- AJAX Integration
- Spring Boot Migration
