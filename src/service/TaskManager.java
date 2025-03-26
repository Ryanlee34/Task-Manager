package service;
import repository.*;
import model.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;


public class TaskManager {
    private String sessionToken = null;
    private final Map<Integer, Integer> taskToTaskCat;
    private final Map<String, Integer> userToCategory;
    private final UserRepository userCrud;
    private final TaskRepository taskCrud;
    private final TaskCategoryRepository taskCatCrud;



    /**Constructor */
    public TaskManager(Map<String, User> userMap, Map<Integer, Task> taskMap, Map<Integer, TaskCategory> taskCatMap, Map<String, Integer> userToCategory,Map<Integer, Integer> taskToTaskCat) {

        this.userCrud = new UserRepository(userMap);
        this.taskCrud = new TaskRepository(taskMap);
        this.taskCatCrud = new TaskCategoryRepository(taskCatMap);
        this.userToCategory = userToCategory;
        this.taskToTaskCat = taskToTaskCat;
    }

    /** Relationship methods*/
    private void createTaskCatToTaskRel(int taskId, int categoryId) {
        taskToTaskCat.put(taskId, categoryId);
    }

    private void createUserToTask(String userId, int taskCatId) {
        userToCategory.put(userId, taskCatId);
    }



    /**Task Category Selection Method */
    private Integer findCategoryIdByName(String categoryName) {
        if(!validSession()) {
            return null;
        }

        List<TaskCategory> allCategories = taskCatCrud.readAll();
        for(TaskCategory category : allCategories) {
            if(userToCategory.containsKey(sessionToken) &&
                    userToCategory.get(sessionToken).equals(category.getId()) &&
                    category.getName().equalsIgnoreCase(categoryName)) {
                return category.getId();
            }
        }
        return null;
    }

    /** Sign in/out Methods*/
    public String authenticate(String userName, String password) {

        List<User> users = userCrud.readAll();

        for (User user : users) {
            if (user.getUserName().equals(userName) && user.verifyPassword(password)) {
                return sessionToken = user.getUniqueId();
            }
        }
        return sessionToken = null;
    }

    private boolean validSession(){
        if(sessionToken != null) {
            return true;
        }
        throw new IllegalArgumentException("Must be signed in.");
    }

    public String logOut() {
        return sessionToken = null;
    }

    /**User CRUD Methods */
    public void createUser(String userName, String password, String fullName, String emailAddress) {
        User newUser = new User(userName,password,fullName, emailAddress);
        userCrud.create(newUser);

    }

    public User readUser() {
        validSession();
        return userCrud.read(sessionToken);
    }

    public void update(String userName, String password, String fullName, String emailAddress){
        validSession();
        User newUser = new User(userName,password,fullName,emailAddress);

        userCrud.update(sessionToken, newUser);

    }

    public void deleteUser(String userId) {
        validSession();
        userCrud.delete(userId);
        userToCategory.remove(userId);
        // Need to implement removal of associated tasks linked to categories removed!!

    }

    /**Task Category CRUD Methods */
    public void createTaskCat(int categoryId, String name, String description) {
        validSession();
        TaskCategory newTask = new TaskCategory(categoryId, name, description);

        createUserToTask(sessionToken, categoryId);
        taskCatCrud.create(newTask);



    }

    public TaskCategory readTaskCat(int categoryId) {
        validSession();

        return taskCatCrud.read(categoryId);
    }


    public void updateTaskCat(int categoryId, String name, String description) {
        validSession();
        TaskCategory newTask = new TaskCategory(categoryId, name, description);

        taskCatCrud.update(categoryId, newTask);
        userToCategory.put(sessionToken, categoryId);
    }

    public void deleteTaskCat(int categoryId) {
        validSession();
        userToCategory.remove(sessionToken, categoryId);
        taskCatCrud.delete(categoryId);
    }

    /**Task CRUD Methods */
    public void createTask(int id, String title, String description, LocalDate dueDate, Task.Status status, Task.Priority priority, int categoryId) {
        validSession();
        Task newTask = new Task(id, title, description, dueDate, status, priority);
        createUserToTask(sessionToken, id);
        createTaskCatToTaskRel(id, categoryId);
        taskCrud.create(newTask);
    }

    public Task readTask(int id) {
        validSession();
        return taskCrud.read(id);
    }


    public void updateTask(String categoryName,int id, String title, String description, LocalDate dueDate, Task.Status status, Task.Priority priority) {
        validSession();
        Task newTask = new Task(id, title,description,dueDate,status, priority);

        taskToTaskCat.put(findCategoryIdByName(categoryName), id);
        taskCrud.update(id, newTask);
    }

    public void deleteTask(String categoryName, int id) {
        validSession();
        taskToTaskCat.remove(findCategoryIdByName(categoryName), id);
        taskCrud.delete(id);
    }


}