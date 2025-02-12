# Job Portal System

## Overview
The **Job Portal System** is a console-based Java application integrated with a MySQL database. It allows job seekers to apply for jobs, employers to post job listings, and administrators to manage job applications. The system includes functionalities for managing applicants, jobs, companies, and job applications.

## Features
### Applicant Operations
- Add an applicant
- View all applicants
- Update applicant details
- Delete an applicant
- Upload resume

### Job Operations
- Post a new job
- View all job listings
- Update job details
- Delete a job listing

### Company Operations
- Add a company
- View all companies
- Update company details
- Delete a company

### Application Operations
- Apply for a job (link applicant to a job)
- View all applications
- Update application status (Pending, Accepted, Rejected)
- Delete an application

## Technologies Used
- **Programming Language**: Java (Core Java)
- **Database**: MySQL
- **Database Connector**: MySQL JDBC Driver
- **Development Environment**: IntelliJ IDEA / Eclipse

## Database Schema
### Applicants Table
```sql
CREATE TABLE applicants (
  applicant_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  resume BLOB NULL
);
```

### Jobs Table
```sql
CREATE TABLE jobs (
  job_id INT AUTO_INCREMENT PRIMARY KEY,
  company_id INT NOT NULL,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  FOREIGN KEY (company_id) REFERENCES companies(company_id) ON DELETE CASCADE
);
```

### Companies Table
```sql
CREATE TABLE companies (
  company_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  location VARCHAR(255)
);
```

### Applications Table
```sql
CREATE TABLE applications (
  application_id INT AUTO_INCREMENT PRIMARY KEY,
  job_id INT NOT NULL,
  applicant_id INT NOT NULL,
  status ENUM('Pending', 'Accepted', 'Rejected') DEFAULT 'Pending',
  FOREIGN KEY (job_id) REFERENCES jobs(job_id) ON DELETE CASCADE,
  FOREIGN KEY (applicant_id) REFERENCES applicants(applicant_id) ON DELETE CASCADE
);
```

## Setup Instructions
1. Clone the repository:
   ```sh
   git clone https://github.com/rathodr2002/job-portal-system.git
   ```
2. Install MySQL and create a database:
   ```sql
   CREATE DATABASE jobportal;
   ```
3. Execute the provided SQL schema to create tables.
4. Update the database connection settings in the Java code:
   ```java
   Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "password");
   ```
5. Compile and run the Java project:
   ```sh
   javac Main.java
   java Main
   ```

## Future Enhancements
- Implement a graphical user interface (GUI) using Java Swing or JavaFX
- Add authentication for users and employers
- Implement search and filter functionalities for jobs and applicants

## License
This project is open-source and available under the [MIT License](LICENSE).

## Contact
For any issues or contributions, feel free to reach out or submit a pull request!

