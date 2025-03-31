import service.AdminService;
import service.DataHandler;
import model.*;
import service.TaskManager;
import ui.UserInterface;
import java.util.Scanner;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {

        DataHandler dataHandler = new DataHandler();
        Scanner scanner = new Scanner(System.in);

        Map<String, User> userMap;
        Map<String, Task> taskMap;
        Map<String, TaskCategory> taskCatMap;
        Map<String, String> userToCategory;
        Map<String, String> taskToTaskCat;

        try{
            userMap = dataHandler.loadUserData();
            taskMap = dataHandler.loadTaskData();
            taskCatMap = dataHandler.loadTaskCategoryData();
            userToCategory = dataHandler.loadUserToTaskCategoryData();
            taskToTaskCat = dataHandler.loadTaskCategoryToTaskData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TaskManager taskManager = new TaskManager(userMap, taskMap, taskCatMap, userToCategory, taskToTaskCat, dataHandler);
        AdminService adminService = new AdminService(userMap, taskMap, taskCatMap);
        UserInterface userInterface = new UserInterface(taskManager, adminService);

        boolean endProgram = false;
        while(!endProgram) {
            System.out.println("""
                    ===Welcome to Taskify===
                    1. Sign-in
                    2. Create Account
                    
                    Type "STOP" to exit app
                    """);
            String login = scanner.nextLine().trim().toUpperCase();

            switch (login) {
                case ("1"):
                    userInterface.logIn();
                    break;

                case ("2"):
                    userInterface.newUser();
                    break;

                case ("STOP"):
                    endProgram = true;
                    break;

                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }

            System.out.println(taskManager.getSessionToken());
            while (userInterface.sessionStatus() != null) {
                    boolean backToEntitySelection = false;

                    while (!backToEntitySelection) {
                        System.out.println("""
                                ===Menu Options===
                                    1. Create
                                    2. View
                                    3. Update
                                    4. Delete
                                    5. View All
                                    6. Entity Selection Menu
                                    7. Log-Out
                                """);
                        String option = scanner.nextLine().trim().toLowerCase();

                        switch (option) {
                            case ("1"):
                                userInterface.Create();
                                break;
                            case ("2"):
                                userInterface.Read();
                                break;
                            case ("3"):
                                userInterface.Update();
                                break;
                            case ("4"):
                                userInterface.Delete();
                                userInterface.sessionStatus();
                                break;
                            case ("5"):
                                // Will add Method for Users
                                break;
                            case ("6"):
                                backToEntitySelection = true;
                                break;
                            case ("7"):
                                userInterface.stop();
                                break;
                            default:
                                System.out.println("Invalid, please try again");
                                break;
                        }
                    }
            }
        }
    }
}

