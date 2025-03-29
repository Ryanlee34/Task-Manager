package service;
import repository.*;
import model.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;


public class TaskManager {
    private String sessionToken = null;
    private String categoryToken = null;
    private String taskToken = null;
    private final Map<String, String> categoryToTaskMap;
    private final Map<String, String> userToCategoryMap;
    private final UserRepository userCrud;
    private final TaskRepository taskCrud;
    private final TaskCategoryRepository categoryCrud;
    private final DataHandler dataHandler;



    /**Constructor */
    public TaskManager(Map<String, User> userMap, Map<String, Task> taskMap, Map<String, TaskCategory> taskCatMap, Map<String, String> userToCategoryMap, Map<String, String> categoryToTaskMap, DataHandler dataHandler) {

        this.userCrud = new UserRepository(userMap);
        this.taskCrud = new TaskRepository(taskMap);
        this.categoryCrud = new TaskCategoryRepository(taskCatMap);
        this.userToCategoryMap = userToCategoryMap;
        this.categoryToTaskMap = categoryToTaskMap;
        this.dataHandler = dataHandler;
    }

    public String getSessionToken () {
        return sessionToken;
    }

    public String getCategoryToken () {
        return categoryToken;
    }

    public String getTaskToken () {
        return taskToken;
    }


    /** Relationship methods*/
    private void createCategoryToTaskRelationship(String taskId, String categoryId) {
        categoryToTaskMap.put(taskId, categoryId);
    }

    private void createUserToCategoryRelationship(String userId, String taskCatId) {
        userToCategoryMap.put(userId, taskCatId);
    }

    /** Getter methods for Relationship Maps */
    public Map<String, String> getCategoryToTaskMap() {
        return categoryToTaskMap;
    }

    public Map<String, String> getUserToCategoryMap() {
        return userToCategoryMap;
    }

    /** Sign in/out Methods*/
    public void signIn(String userName, String password) {

        List<User> users = userCrud.readAll();

        for (User user : users) {
            if (user.getUserName().equals(userName) && user.verifyPassword(password)) {
                sessionToken = user.getUniqueId();
            } else {
                throw new IllegalArgumentException("Invalid UserName or Password.");
            }
        }
    }

    public void selectCategory(String categoryName) {
        List<TaskCategory> categories = categoryCrud.readAll();

        for (TaskCategory category: categories) {
            if (category.getName().equals(categoryName)) {
                categoryToken = category.getId();
            } else {
                throw new IllegalArgumentException("Invalid Category Selected");
            }
        }
    }

    public void selectTask(String taskName) {
        List<Task> tasks = taskCrud.readAll();

        for (Task task: tasks) {
            if (task.getTitle().equals(taskName)) {
                taskToken = task.getId();
            } else {
                throw new IllegalArgumentException("Invalid Category Selected");
            }
        }
    }



    private boolean validSession(){
        if(sessionToken != null) {
            return true;
        }
        throw new IllegalArgumentException("Must be signed in.");
    }

    private void validCategoryToken() {
        if (!categoryCrud.getCategoryMap().containsKey(categoryToken)) {
            throw new IllegalArgumentException("Invalid Category Selected");
        }
    }

    private void validTaskToken() {
        if (!taskCrud.getTaskMap().containsKey(taskToken)) {
            throw new IllegalArgumentException("Invalid Task Selected");
        }
    }


    public void logOut() {
        sessionToken = null;
        categoryToken = null;
    }

    /**User CRUD Methods */
    public void createUser(String userName, String password, String fullName, String emailAddress) throws IOException {
        User newUser = new User(userName,password,fullName, emailAddress);

        userCrud.create(newUser);
        dataHandler.userJson(userCrud.getUserMap());
    }

    public User readUser() throws IOException {
        validSession();
        dataHandler.loadUserData();
         return userCrud.read(sessionToken);
    }

    public void update(String userName, String password, String fullName, String emailAddress) throws IOException {
        validSession();
        User newUser = new User(userName,password,fullName,emailAddress);

        //Update all HashMaps.
        userCrud.update(sessionToken, newUser);
        userToCategoryMap.put(sessionToken, (userToCategoryMap.get(sessionToken)));

        //Save updates to Json Files using HashMaps
        dataHandler.userJson(userCrud.getUserMap());
        dataHandler.userToCategoryJson(getUserToCategoryMap());


    }

    public void deleteUser(String userId) throws IOException {
        validSession();
        userCrud.delete(userId);

        //Remove all relationships to user from HashMaps.
        String categoryId = userToCategoryMap.get(userId);
        userToCategoryMap.remove(userId, categoryId);
        categoryToTaskMap.remove(categoryId);

        //Update all Json Files using HashMaps
        dataHandler.userJson(userCrud.getUserMap());
        dataHandler.userToCategoryJson(getUserToCategoryMap());
        dataHandler.categoryToTaskJson(getCategoryToTaskMap());


    }

    /**Task Category CRUD Methods */
    public void createTaskCat(String name, String description)throws IOException {
        validSession();
        if (categoryCrud.getCategoryMap().containsKey(categoryToken)) {
            throw new IllegalArgumentException("New CategoryId must be unique");
        }
        TaskCategory newTask = new TaskCategory( name, description);

        createUserToCategoryRelationship(sessionToken, categoryToken);
        categoryCrud.create(newTask);
        dataHandler.categoryJson(categoryCrud.getCategoryMap());


    }

    public TaskCategory readTaskCat(String categoryId) {
        validSession();

        return categoryCrud.read(categoryId);
    }


    public void updateTaskCat (String name, String description) throws IOException{
        validSession();
        validCategoryToken();
        TaskCategory newCat = new TaskCategory (name, description);

        categoryCrud.update(categoryToken, newCat);
        userToCategoryMap.put(sessionToken, categoryToken);
        categoryToTaskMap.put(categoryToken,categoryToTaskMap.get(categoryToken));

        dataHandler.categoryJson(categoryCrud.getCategoryMap());
        dataHandler.userToCategoryJson(getUserToCategoryMap());
        dataHandler.categoryToTaskJson(getCategoryToTaskMap());

    }

    public void deleteTaskCat() throws IOException{
        validSession();
        validCategoryToken();

        categoryCrud.delete(categoryToken);
        userToCategoryMap.remove(sessionToken, categoryToken);
        categoryToTaskMap.remove(categoryToken);

        dataHandler.categoryJson(categoryCrud.getCategoryMap());
        dataHandler.userToCategoryJson(getUserToCategoryMap());
        dataHandler.categoryToTaskJson(getCategoryToTaskMap());
    }

    /**Task CRUD Methods */
    public void createTask(String title, String description, LocalDate dueDate, Task.Status status, Task.Priority priority)throws IOException {
        validSession();
        validCategoryToken();

        Task newTask = new Task(title, description, dueDate, status, priority);
        taskCrud.create(newTask);

        createUserToCategoryRelationship(sessionToken, categoryToken);
        createCategoryToTaskRelationship(categoryToken, newTask.getId());

        dataHandler.taskJson(taskCrud.getTaskMap());
        dataHandler.categoryToTaskJson(getCategoryToTaskMap());

    }

    public Task readTask(String id) {
        validSession();
        return taskCrud.read(id);
    }


    public void updateTask(String title, String description, LocalDate dueDate, Task.Status status, Task.Priority priority) throws IOException{
        validSession();
        validCategoryToken();
        validTaskToken();

        Task newTask = new Task(title,description,dueDate,status, priority);

        taskCrud.update(categoryToken,newTask);
        categoryToTaskMap.put(categoryToken, categoryToTaskMap.get(categoryToken));

        dataHandler.taskJson(taskCrud.getTaskMap());
        dataHandler.categoryToTaskJson(getCategoryToTaskMap());
    }

    public void deleteTask(String taskName) throws IOException{
        validSession();
        validCategoryToken();

        taskCrud.delete(taskToken);
        categoryToTaskMap.remove(categoryToken, taskToken);

        dataHandler.taskJson(taskCrud.getTaskMap());
        dataHandler.categoryToTaskJson(getCategoryToTaskMap());

    }


}