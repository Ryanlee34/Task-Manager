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
    private final Scanner scanner;
    private static final Map<String, List<String>> promptMap = Map.of(
            "userPrompts", List.of("Enter Username: ", "Enter password: ", "Enter Full Name: ", "Enter Email address: "),
            "categoryPrompts", List.of("Enter Category Name: ", "Enter Category Description: "),
            "taskPrompts", List.of("Enter Task Title: ", "Enter Description: ","Enter Due Date (YYY-MM-DD): ", "Enter Priority: ", "Enter Status: ")
    );

    public UserInterface (TaskManager taskManager) {
        this.taskManager = taskManager;
        this.scanner = new Scanner(System.in);
    }

    private String entitySelector() {
        while (true) {
            System.out.println("User, Category, or Task?");
            String choice = scanner.nextLine().trim().toLowerCase();

            if ((choice.equals("user")) || (choice.equals("category")) || (choice.equals("task"))) {
                return choice;
            }
            System.out.println("Invalid Choice, Select Again.");
        }
    }

    public void start() {
        boolean validInput = false;
        while(!validInput) {

            System.out.print(promptMap.get("userPrompts").get(1));
            String userName = scanner.nextLine().trim().toLowerCase();

            System.out.print(promptMap.get("userPrompts").get(2));
            String password = scanner.nextLine().trim();

            try {

                taskManager.signIn(userName, password);
                validInput = true;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void create() throws IOException {
        String choice = entitySelector();
        switch (choice) {
            case ("user"):
                System.out.print((promptMap.get("userPrompts").get(1)));
                String userName = scanner.nextLine().trim();
                System.out.print((promptMap.get("userPrompts").get(2)));
                String password = scanner.nextLine().trim();
                System.out.print((promptMap.get("userPrompts").get(3)));
                String fullName = scanner.nextLine().trim();
                System.out.print((promptMap.get("userPrompts").get(4)));
                String email = scanner.nextLine().trim();

                try {
                    taskManager.createUser(userName, password, fullName, email);
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            case ("category"):
                System.out.print((promptMap.get("categoryPrompts").get(1)));
                String name = scanner.nextLine().trim();
                System.out.print((promptMap.get("categoryPrompts").get(2)));
                String description = scanner.nextLine().trim();

                try {
                    taskManager.createTaskCat(name, description);
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            case ("task"):
                System.out.print((promptMap.get("taskPrompts").get(1)));
                String title = scanner.nextLine().trim();

                System.out.print((promptMap.get("taskPrompts").get(2)));
                String taskDescription = scanner.nextLine().trim();

                System.out.print((promptMap.get("taskPrompts").get(3)));
                String date = scanner.nextLine().trim();
                LocalDate dueDate = LocalDate.parse(date);

                System.out.print((promptMap.get("taskPrompts").get(4)));
                String statusInput = scanner.nextLine().trim();

                Task.Status status;

                try {
                    status = Task.Status.valueOf(statusInput.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid Status, using default");
                    status = Task.Status.TODO;
                }

                System.out.print((promptMap.get("taskPrompts").get(4)));
                String priorityInput = scanner.nextLine().trim();
                Task.Priority priority;

                try {
                    priority = Task.Priority.valueOf(priorityInput.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid Priority, using default");
                    priority = Task.Priority.MEDIUM;
                }

                taskManager.createTask(title, taskDescription, dueDate,status, priority);
                break;
        }
    }



    public void read() throws IOException {
        String choice = entitySelector();
        switch (choice) {
            case ("user"):
                User user = taskManager.readUser();
                System.out.println("Username: " + user.getUserName());
                System.out.println("Creation Date: " + user.getTimeStamp());
                System.out.println("Email: " + user.getEmailAddress());
                System.out.println("Name: " + user.getFullName());
                break;

            case ("category"):
                System.out.print(promptMap.get("categoryPrompts").get(1));
                String cat = scanner.nextLine().trim().toLowerCase();

                taskManager.selectCategory(cat);
                String catToken = taskManager.getCategoryToken();

                try {
                    TaskCategory taskCategory = taskManager.readTaskCat(catToken);
                    System.out.println("Category Name: " + taskCategory.getName());
                    System.out.println("Description: " + taskCategory.getDescription());
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            case ("task"):
                System.out.print(promptMap.get("taskPrompt").get(1));
                String title = scanner.nextLine().trim().toLowerCase();
                taskManager.selectTask(title);
                String taskId = taskManager.getTaskToken();
                try {
                    Task task = taskManager.readTask(taskId);

                    System.out.println("Task Title: "+ task.getTitle());
                    System.out.println("Description: "+ task.getDescription());
                    System.out.println("Due Date: "+ task.getDueDate());
                    System.out.println("Priority: "+ task.getPriority());
                    System.out.println("Status: "+ task.getStatus());
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());;
                }
        }

    }

    public void update() {
        String choice = entitySelector();
        switch (choice) {
            case ("user"):
                taskManager.update();
                break;

            case ("category"):
                taskManager.updateTaskCat();
                break;

            case ("task"):
                taskManager.updateTask();
                break;
        }

    }

    public void delete() {
        String choice = entitySelector();
        switch (choice) {
            case ("user"):
                taskManager.deleteUser();
                break;

            case ("category"):
                taskManager.deleteTaskCat();
                break;

            case ("task"):
                taskManager.deleteTask();
                break;
        }

    }


    public void stop() {
        taskManager.logOut();
        System.out.println("User Logged Out Successfully");
    }



}
