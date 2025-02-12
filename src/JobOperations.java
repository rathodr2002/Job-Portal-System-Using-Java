import java.sql.*;
import java.util.Scanner;
import Database.*;

public class JobOperations {
    private static final Scanner scanner = new Scanner(System.in);

    // ✅ 1. Add a New Job
    public void addJob() {
        System.out.print("Enter job title: ");
        String title = scanner.nextLine();
        System.out.print("Enter job description: ");
        String description = scanner.nextLine();
        System.out.print("Enter job salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter job location: ");
        String location = scanner.nextLine();
        System.out.print("Enter company ID: ");
        int companyId = scanner.nextInt();

        String sql = "INSERT INTO jobs (title, description, salary, location, company_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setDouble(3, salary);
            stmt.setString(4, location);
            stmt.setInt(5, companyId);
            stmt.executeUpdate();

            System.out.println("✅ Job added successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error adding job: " + e.getMessage());
        }
    }

    // ✅ 2. View All Jobs
    public void viewJobs() {
        String sql = "SELECT j.job_id, j.title, j.salary, j.location, c.name " +
                "FROM jobs j JOIN companies c ON j.company_id = c.company_id";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== Job Listings ===");
            while (rs.next()) {
                System.out.println("Job ID: " + rs.getInt("job_id") +
                        ", Title: " + rs.getString("title") +
                        ", Salary: " + rs.getDouble("salary") +
                        ", Location: " + rs.getString("location") +
                        ", Company: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching jobs: " + e.getMessage());
        }
    }

    // ✅ 3. Update Job Details
    public void updateJob() {
        System.out.print("Enter job ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter new job title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new job description: ");
        String description = scanner.nextLine();
        System.out.print("Enter new salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter new location: ");
        String location = scanner.nextLine();
        System.out.print("Enter new company ID: ");
        int companyId = scanner.nextInt();

        String sql = "UPDATE jobs SET title = ?, description = ?, salary = ?, location = ?, company_id = ? WHERE job_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setDouble(3, salary);
            stmt.setString(4, location);
            stmt.setInt(5, companyId);
            stmt.setInt(6, id);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✅ Job updated successfully!");
            } else {
                System.out.println("❌ Job ID not found!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error updating job: " + e.getMessage());
        }
    }

    // ✅ 4. Delete a Job
    public void deleteJob() {
        System.out.print("Enter job ID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM jobs WHERE job_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("✅ Job deleted successfully!");
            } else {
                System.out.println("❌ Job ID not found!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting job: " + e.getMessage());
        }
    }
}
