import java.util.Scanner;

public class Main {
    public static void main (String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        ApplicantOperations applicantOperations = new ApplicantOperations();
        JobOperations jobOperations = new JobOperations();
        CompaniesOperation companiesOperation = new CompaniesOperation();
        ApplicationOperations applicationOperations = new ApplicationOperations();

        while(true)
        {
            System.out.println("------- Job Portal System -------");
            System.out.println("1. Manage Applicants");
            System.out.println("2. Manage Jobs");
            System.out.println("3. Manage Companies");
            System.out.println("4. Manage Applications");
            System.out.println("5. Exit");
            System.out.print("Enter Choice : ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice)
            {
                case 1:
                    manageApplicants(scanner,applicantOperations);
                    break;
                case 2:
                    manageJobs(scanner,jobOperations);
                    break;
                case 3:
                    manageCompanies(scanner,companiesOperation);
                    break;
                case 4:
                    manageApplications(scanner,applicationOperations);
                    break;
                case 5:
                    System.out.println("Exiting the System...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid Choice, Please Try Again");
            }
        }
    }

    private static void manageApplicants(Scanner scanner, ApplicantOperations applicantOperations)
            {
                System.out.println("=== Manage Applicants ===");
                System.out.println("1. Add Applicant");
                System.out.println("2. View Applicants");
                System.out.println("3. Update Applicant");
                System.out.println("4. Delete Applicant");
                System.out.println("5. Download Resume Using Applicant Id");
                System.out.println("6. Back to Main Menu");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice)
                {
                    case 1:
                        applicantOperations.registerApplicant();
                        break;
                    case 2:
                        applicantOperations.viewApplicants();
                        break;
                    case 3:
                        applicantOperations.updateApplicant();
                        break;
                    case 4:
                        applicantOperations.deleteApplicant();
                        break;
                    case 5:
                        applicantOperations.downloadResume();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid Choice, Please Try Again!");
        }
    }

    private static void manageJobs(Scanner scanner, JobOperations jobOperations)
    {
        System.out.println("=== Manage Jobs ===");
        System.out.println("1. Post/Add Job");
        System.out.println("2. View Jobs");
        System.out.println("3. Update Job");
        System.out.println("4. Delete Job");
        System.out.println("5. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice)
        {
            case 1:
                jobOperations.addJob();
                break;
            case 2:
                jobOperations.viewJobs();
                break;
            case 3:
                jobOperations.updateJob();
                break;
            case 4:
                jobOperations.deleteJob();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid Choice, Please Try Again!");
        }
    }

    private static void manageCompanies(Scanner scanner , CompaniesOperation companiesOperation)
    {
        System.out.println("=== Manage Companies ===");
        System.out.println("1. Add Company");
        System.out.println("2. View Companies");
        System.out.println("3. Update Company");
        System.out.println("4. Delete Company");
        System.out.println("5. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice)
        {
            case 1:
                companiesOperation.addCompany();
                break;
            case 2:
                companiesOperation.getAllCompany();
                break;
            case 3:
                System.out.print("Enter Company ID to Update: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                companiesOperation.updateCompany();
                break;
            case 4:
                companiesOperation.deleteCompany();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid Choice, Please Try Again!");
        }
    }

    private static void manageApplications(Scanner scanner, ApplicationOperations applicationOperations) {
        System.out.println("\n=== Manage Applications ===");
        System.out.println("1. Apply for a Job");
        System.out.println("2. View Applications");
        System.out.println("3. Update Application Status");
        System.out.println("4. Delete Application");
        System.out.println("5. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                applicationOperations.applyForJob();
                break;
            case 2:
                applicationOperations.viewAllApplications();
                break;
            case 3:
                applicationOperations.updateApplicationStatus();
                break;
            case 4:
                applicationOperations.deleteApplication();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }
}
