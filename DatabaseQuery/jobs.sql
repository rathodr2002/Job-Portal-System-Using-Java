CREATE TABLE jobs (
  job_id INT AUTO_INCREMENT PRIMARY KEY,
  company_id INT NOT NULL,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  FOREIGN KEY (company_id) REFERENCES companies(company_id) ON DELETE CASCADE
);