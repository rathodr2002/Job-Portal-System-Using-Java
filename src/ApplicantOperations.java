import Database.Database;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ApplicantOperations {

    private static final Scanner scanner = new Scanner(System.in);

    //1. Register New Applicant
    public void registerApplicant()
    {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Skills: ");
        String skills = scanner.nextLine();
        System.out.print("Enter Resume File Path: ");
        String filePath = scanner.nextLine();

        String query = "INSERT INTO applicants (name, email, phone, resume , skills) VALUES ( ? , ? , ? , ?, ?)";

        try(Connection connect = Database.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            FileInputStream fileSystem = new FileInputStream(filePath)
            )
        {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,phone);
            preparedStatement.setBinaryStream(4,fileSystem);
            preparedStatement.setString(5,skills);
            preparedStatement.executeUpdate();
            System.out.println("✅ Applicant registered successfully!");
        }
        catch (SQLException | IOException e) {
            System.out.println("❌ Error registering applicant: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    // 2. View All Applicants
    public void viewApplicants() {
        String sql = "SELECT applicant_id, name, email, phone, skills FROM applicants";

        try (Connection conn = Database.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            System.out.println("\n=== Applicants List ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("applicant_id") +
                        ", Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email") +
                        ", Phone: " + rs.getString("phone") +
                        ", Skills: " + rs.getString("skills"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching applicants: " + e.getMessage());
        }
    }

    //3. Update Applicant Details (Including Resume)
    public void updateApplicant() {
        System.out.print("Enter Applicant ID to Update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter New Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter New Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter New Skills: ");
        String skills = scanner.nextLine();
        System.out.print("Enter New Resume File Path (or press Enter to skip): ");
        String filePath = scanner.nextLine();

        String query;
        boolean updateResume = !filePath.isEmpty(); // Fix: updateResume should be true if a new resume is provided.

        if (updateResume) {
            query = "UPDATE applicants SET name = ?, email = ?, phone = ?, resume = ?, skills = ? WHERE applicant_id = ?";
        } else {
            query = "UPDATE applicants SET name = ?, email = ?, phone = ?, skills = ? WHERE applicant_id = ?";
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(updateResume ? 5 : 4, skills);
            preparedStatement.setInt(updateResume ? 6 : 5, id);

            // Handling resume file if provided
            if (updateResume) {
                try (FileInputStream file = new FileInputStream(filePath)) {
                    preparedStatement.setBinaryStream(4, file, file.available());
                } catch (IOException e) {
                    System.out.println("❌ Error reading the resume file: " + e.getMessage());
                    return; // Exit early if file operation fails
                }
            }

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Applicant updated successfully!");
            } else {
                System.out.println("❌ Applicant ID not found!");
            }

        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        }
    }


    //4 . Delete Applicant
    public void deleteApplicant() {
        System.out.print("Enter Applicant ID to delete: ");
        int applicantId = scanner.nextInt();
        scanner.nextLine();

        String query = "DELETE FROM applicants WHERE applicant_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, applicantId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Applicant deleted successfully.");
            } else {
                System.out.println("⚠️ No applicant found with ID: " + applicantId);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting applicant: " + e.getMessage());
        }
    }

    //5. Download Resume
    public void downloadResume()
    {
        System.out.print("Enter Applicant ID to Download Resume: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Folder Path to Save (e.g., D:/Rishi/Resumes): ");
        String folderPath = scanner.nextLine().trim();

        // Ensure folder exists
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directories if they don't exist
        }

        // Generate file path with default file name
        String filePath = folderPath + File.separator + "resume_" + id + ".pdf";

        String query = "SELECT resume FROM applicants WHERE applicant_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                try (InputStream input = result.getBinaryStream("resume");
                     FileOutputStream output = new FileOutputStream(filePath)) {

                    if (input == null) {
                        System.out.println("❌ No resume found for this applicant.");
                        return;
                    }

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }

                    System.out.println("✅ Resume downloaded successfully to: " + filePath);
                }
            } else {
                System.out.println("❌ Applicant ID not found!");
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("❌ File Error: " + e.getMessage());
        }
    }
}
