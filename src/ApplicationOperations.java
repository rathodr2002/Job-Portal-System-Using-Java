import java.sql.*;
import Database.*;
import java.util.Scanner;

public class ApplicationOperations {
    private final Connection connection;
    private final Scanner scanner;

    public ApplicationOperations() {
        this.connection = Database.getConnection();
        this.scanner = new Scanner(System.in);
    }

    // Apply for a job
    public void applyForJob() {
        try {
            System.out.print("Enter Applicant ID: ");
            int applicantId = scanner.nextInt();
            System.out.print("Enter Job ID: ");
            int jobId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String query = "INSERT INTO applications (applicant_id, job_id, status) VALUES (?, ?, 'Pending')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, applicantId);
            preparedStatement.setInt(2, jobId);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Application submitted successfully!");
            } else {
                System.out.println("Failed to submit application.");
            }
        } catch (SQLException e) {
            System.out.println("Error applying for job: " + e.getMessage());
        }
    }

    // View all applications
    public void viewAllApplications() {
        try {
            String query = "SELECT * FROM applications";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("\n=== Job Applications ===");
            while (resultSet.next()) {
                System.out.println("Application ID: " + resultSet.getInt("application_id") +
                        ", Applicant ID: " + resultSet.getInt("applicant_id") +
                        ", Job ID: " + resultSet.getInt("job_id") +
                        ", Status: " + resultSet.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving applications: " + e.getMessage());
        }
    }

    // Update application status
    public void updateApplicationStatus() {
        try {
            System.out.print("Enter Application ID: ");
            int applicationId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new status (Pending, Accepted, Rejected): ");
            String status = scanner.nextLine();

            String query = "UPDATE applications SET status = ? WHERE application_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, applicationId);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Application status updated successfully!");
            } else {
                System.out.println("Application ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating application status: " + e.getMessage());
        }
    }

    // Delete an application
    public void deleteApplication() {
        try {
            System.out.print("Enter Application ID to delete: ");
            int applicationId = scanner.nextInt();

            String query = "DELETE FROM applications WHERE application_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, applicationId);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Application deleted successfully!");
            } else {
                System.out.println("Application ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting application: " + e.getMessage());
        }
    }
}
