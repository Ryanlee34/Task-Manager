package ui;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import model.TaskCategory;
import model.Task;
import model.User;
import service.*;

public class UserInterface {
    private final TaskManager taskManager;
    private final AdminService adminService;
    private final Scanner scanner;
    private static final Map<String, List<String>> promptMap = Map.of(
            "userPrompts", List.of("Enter Username: ", "Enter password: ", "Enter Full Name: ", "Enter Email address: "),
            "categoryPrompts", List.of("Enter Category Name: ", "Enter Category Description: "),
            "taskPrompts", List.of("Enter Task Title: ", "Enter Description: ","Enter Due Date (YYYY-MM-DD): ", "Enter Priority: ", "Enter Status: "),
            "crudPrompts", List.of("Creation Successful.", "Update Successful.","Deletion Successful.","Are you sure you want to delete this? (yes or no)")
    );

    public UserInterface (TaskManager taskManager,AdminService adminService) {
        this.adminService = adminService;
        this.taskManager = taskManager;
        this.scanner = new Scanner(System.in);
    }

    public String entitySelector() {
        while (true) {
            System.out.println("""
                    ===Select Entity===
                        1. User
                        2. Category
                        3. Task
                        4. Back to Main Menu
                    """);
            String entity = scanner.nextLine().trim().toLowerCase();
            if (entity.equals("1")|| entity.equals("2") || entity.equals("3") || entity.equals("4")) {
                return entity;
            } else {
                System.out.println("Invalid Choice, Select Again.");
            }
        }
    }


    public void logIn() {
        boolean validInput = false;
        while(!validInput) {

            System.out.print(promptMap.get("userPrompts").get(0));
            String userName = scanner.nextLine().trim();

            System.out.print(promptMap.get("userPrompts").get(1));
            String password = scanner.nextLine().trim();

            try {

                taskManager.signIn(userName, password);
                validInput = true;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("To Go to Main Menu Press 1.");
                String main = scanner.nextLine().trim();
                if (main.equals("1")) {
                    break;
                }
            }
        }
    }

    public void newUser() throws IOException {
        System.out.println(promptMap.get("userPrompts").getFirst());
        String userName = scanner.nextLine().trim();
        System.out.println(promptMap.get("userPrompts").get(1));
        String password = scanner.nextLine().trim();
        System.out.println(promptMap.get("userPrompts").get(2));
        String fullName = scanner.nextLine().trim().toLowerCase();
        System.out.println(promptMap.get("userPrompts").getLast());
        String email = scanner.nextLine().trim().toLowerCase();
        taskManager.createUser(userName, password, fullName, email);
    }

    public void Create() throws IOException {
        String choice = entitySelector();
        switch(choice) {
            case("1"):
                System.out.println("Must be signed out to Create New User.");
                break;
            case("2"):
                System.out.println(promptMap.get("categoryPrompts").getFirst());
                String name = scanner.nextLine().trim().toLowerCase();
                System.out.println(promptMap.get("categoryPrompts").get(1));
                String description = scanner.nextLine().trim().toLowerCase();

                taskManager.createTaskCat(name, description);
                break;

            case("3"):
                System.out.println(promptMap.get("taskPrompts").getFirst());
                String taskTitle = scanner.nextLine().trim().toLowerCase();
                System.out.println(promptMap.get("taskPrompts").get(1));
                String taskDescription = scanner.nextLine().trim().toLowerCase();

                System.out.println(promptMap.get("taskPrompts").get(2));
                String dueDate = scanner.nextLine().trim();
                LocalDate taskDueDate = LocalDate.parse(dueDate);

                System.out.println(promptMap.get("taskPrompts").getLast());
                String status = scanner.nextLine().trim().toUpperCase();
                Task.Status taskStatus = Task.Status.valueOf(status);

                System.out.println(promptMap.get("taskPrompts").get(3));
                String priority = scanner.nextLine().trim().toUpperCase();
                Task.Priority taskPriority = Task.Priority.valueOf(priority);
                taskManager.createTask(taskTitle, taskDescription, taskDueDate, taskStatus, taskPriority);
                break;

            case("4"):

                break;

        }
    }

    public void Read() throws IOException {
        String choice = entitySelector();
        switch(choice) {
            case("1"):
                taskManager.readUser();
                break;
            case("2"):
                System.out.println(promptMap.get("categoryPrompts").getFirst());
                String catName = scanner.nextLine().trim().toLowerCase();
                taskManager.selectCategory(catName);
                taskManager.readTaskCat(taskManager.getCategoryToken());
                break;

            case("3"):
                System.out.println(promptMap.get("taskPrompts").getFirst());
                String taskName = scanner.nextLine().trim().toLowerCase();
                taskManager.selectTask(taskName);
                taskManager.readTask(taskManager.getTaskToken());
                break;
            case("4"):

                break;
        }
    }

    public void Update() throws IOException {
        String choice = entitySelector();
        switch(choice) {
            case("1"):
                System.out.println(promptMap.get("userPrompts").getFirst());
                String userName = scanner.nextLine().trim();
                System.out.println(promptMap.get("userPrompts").get(1));
                String password = scanner.nextLine().trim();
                System.out.println(promptMap.get("userPrompts").get(2));
                String fullName = scanner.nextLine().trim();
                System.out.println(promptMap.get("userPrompts").getLast());
                String email = scanner.nextLine().trim();

                taskManager.update(userName, password, fullName, email);
                System.out.println(promptMap.get("crudPrompts").get(1));
                break;
            case("2"):
                System.out.println(promptMap.get("categoryPrompts").getFirst());
                String name = scanner.nextLine().trim();
                System.out.println(promptMap.get("categoryPrompts").get(1));
                String description = scanner.nextLine().trim();


                taskManager.updateTaskCat(name, description);
                System.out.println(promptMap.get("crudPrompts").get(1));
                break;
            case("3"):
                System.out.println(promptMap.get("taskPrompts").getFirst());
                String taskTitle = scanner.nextLine().trim();
                System.out.println(promptMap.get("taskPrompts").get(1));
                String taskDescription = scanner.nextLine().trim();

                System.out.println(promptMap.get("taskPrompts").get(2));
                String dueDate = scanner.nextLine().trim();
                LocalDate taskDueDate = LocalDate.parse(dueDate);

                System.out.println(promptMap.get("taskPrompts").getLast());
                String status = scanner.nextLine().trim().toUpperCase();
                Task.Status taskStatus = Task.Status.valueOf(status);

                System.out.println(promptMap.get("taskPrompts").get(3));
                String priority = scanner.nextLine().trim().toUpperCase();
                Task.Priority taskPriority = Task.Priority.valueOf(priority);

                taskManager.updateTask(taskTitle, taskDescription, taskDueDate, taskStatus, taskPriority);
                System.out.println(promptMap.get("crudPrompts").get(1));
                break;
            case("4"):

                break;

        }
    }

    public void Delete() throws IOException {
        String choice = entitySelector();
        switch(choice) {
            case("1"):
                System.out.println(promptMap.get("crudPrompts").getLast());
                String confirm = scanner.nextLine().trim().toLowerCase();
                if (confirm.equals("yes")) {
                    taskManager.deleteUser(taskManager.getSessionToken());
                    System.out.println(promptMap.get("crudPrompts").get(2));
                    stop();
                    break;
                } else if (confirm.equals("no")) {
                System.out.println("Deletion Canceled");
                break;
                } else {
                    System.out.println("Invalid Input. Deletion Not completed.");
                    break;
            }
            case("2"):
                System.out.println(promptMap.get("categoryPrompts").getFirst());
                String categoryName = scanner.nextLine().trim().toLowerCase();
                taskManager.selectCategory(categoryName);
                System.out.println(promptMap.get("crudPrompts").getLast());
                String confirmCat = scanner.nextLine().trim().toLowerCase();
                if (confirmCat.equals("yes")) {
                    taskManager.deleteTaskCat();
                    System.out.println(promptMap.get("crudPrompts").get(2));
                    break;
                } else if (confirmCat.equals("no")) {
                    System.out.println("Deletion Canceled");
                    break;
                } else {
                    System.out.println("Invalid Input. Deletion Not completed.");
                    break;
                }
            case("3"):
                System.out.println(promptMap.get("taskPrompts").getFirst());
                String taskName = scanner.nextLine().trim().toLowerCase();
                taskManager.selectTask(taskName);
                System.out.println(promptMap.get("crudPrompts").getLast());
                String confirmTask = scanner.nextLine().trim().toLowerCase();
                if (confirmTask.equals("yes")) {
                    taskManager.deleteTask();
                    System.out.println(promptMap.get("crudPrompts").get(2));
                    break;
                } else if (confirmTask.equals("no")) {
                    System.out.println("Deletion Canceled");
                    break;
                } else {
                    System.out.println("Invalid Input. Deletion Not completed.");
                    break;
                }
            case("4"):
                break;

        }
    }






    public void adminViewAll() {
        try {
            adminService.validateIsAdmin();
            System.out.println("Select an option(1,2,or 3): View all Users, View all Category's, View all Tasks.");
            String value = scanner.nextLine().trim();
            switch (value) {
                case ("1"):
                    List<User> users = adminService.readAllUser();
                    for (User user : users) {
                        System.out.println("AccountType: " + user.getAccountType());
                        System.out.println("UserId: " + user.getUserUniqueId());
                        System.out.println("Username: " + user.getUserName());
                        System.out.println("Account Creation Date: " + user.getTimeStamp());
                        System.out.println("Full Name: " + user.getFullName());
                        System.out.println("Email Address: " + user.getEmailAddress());
                    }
                    break;
                case("2"):
                    List<TaskCategory> categories = adminService.readAllCategory();
                    for (TaskCategory category : categories) {
                        System.out.println("Category Id: " + category.getCategoryId());
                        System.out.println("Category Name: "+ category.getName());
                        System.out.println("Category Description: "+ category.getDescription());
                    }
                    break;
                case ("3"):
                    List<Task> tasks = adminService.readAllTask();
                    for (Task task: tasks) {
                        System.out.println("Task Id: " + task.getTaskId());
                        System.out.println("Task Title: " + task.getTitle());
                        System.out.println("Task Description: " + task.getDescription());
                        System.out.println("Task Due Date: " + task.getDueDate());
                        System.out.println("Task Priority: " + task.getPriority());
                        System.out.println("Task Status: " + task.getStatus());
                    }
                    break;

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String sessionStatus() {
        return taskManager.getSessionToken();
    }

    public void stop() {
        taskManager.logOut();
        System.out.println("User Logged Out Successfully");
    }



}
