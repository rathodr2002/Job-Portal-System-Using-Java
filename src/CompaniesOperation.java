import Database.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CompaniesOperation {

    Scanner scanner = new Scanner(System.in);

    //Method to Add/Register Company
    public void addCompany()
    {
        System.out.print("Enter Company name :");
        String companyName = scanner.nextLine();
        System.out.print("Enter Company location :");
        String companyLocation = scanner.nextLine();
        System.out.print("Enter Industry type :");
        String industry = scanner.nextLine();

        String query = "INSERT INTO companies (name,location,industry) VALUES ( ? , ? , ?)";

        try (Connection connect = Database.getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(query))
        {
            preparedStatement.setString(1,companyName);
            preparedStatement.setString(2,companyLocation);
            preparedStatement.setString(3,industry);

            preparedStatement.executeUpdate();
            System.out.println("✅ Company added successfully!");
        }
        catch(SQLException e)
        {
            System.out.println("❌ Error Adding companies : " + e.getMessage());
        }
    }

    //Method to View/Retrieve All Company
    public void getAllCompany() {

        String query = "SELECT * FROM companies";

        try (Connection connect = Database.getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(query);
             ResultSet result = preparedStatement.executeQuery())
        {
            System.out.println("---- Company Details ----");
            while(result.next())
            {
                System.out.println("ID: " + result.getInt("company_id") +
                        ", Name: " + result.getString("name") +
                        ", Location: " + result.getString("location") +
                        ", Industry: " + result.getString("industry"));
            }
        }
        catch (SQLException e) {
            System.out.println("❌ Error fetching companies: " + e.getMessage());
        }
    }

    // ✅ 3. Update Company Details
    public void updateCompany() {
        System.out.print("Enter company ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter new company name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new company location: ");
        String location = scanner.nextLine();
        System.out.print("Enter new industry type: ");
        String industry = scanner.nextLine();

        String sql = "UPDATE companies SET name = ?, location = ?, industry = ? WHERE company_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, location);
            stmt.setString(3, industry);
            stmt.setInt(4, id);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✅ Company updated successfully!");
            } else {
                System.out.println("❌ Company ID not found!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error updating company: " + e.getMessage());
        }
    }

    //Delete Company from Database
    public void deleteCompany()
    {
        System.out.print("Enter company ID to delete: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM companies WHERE company_id = ?";
        try(Connection connect = Database.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(query))
        {
            preparedStatement.setInt(1,id);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected >0)
            {
                System.out.println("✅ Company Details Deleted successfully!");
            }
            else
            {
                System.out.println("❌ No Company Found to Delete");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
