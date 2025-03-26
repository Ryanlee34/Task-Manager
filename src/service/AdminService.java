package service;
import repository.*;
import model.Task;
import model.TaskCategory;
import model.User;
import java.util.List;
import java.util.Map;


public class AdminService {
    private String sessionToken = null;
    private final UserRepository userCrud;
    private final TaskRepository taskCrud;
    private final TaskCategoryRepository taskCatCrud;


    private void validateToken() {
        if(sessionToken == null) {
            throw new IllegalArgumentException("Invalid User");
        }
    }

    private void validateIsAdmin() {
        User currentuser = userCrud.read(sessionToken);
        if(!currentuser.isAdmin()) {
            throw new IllegalArgumentException("Must be admin to access.");
        }
    }

    public AdminService(Map<String, User> userMap, Map<Integer, Task> taskMap, Map<Integer, TaskCategory> taskCatMap) {
        this.userCrud = new UserRepository(userMap);
        this.taskCrud = new TaskRepository(taskMap);
        this.taskCatCrud = new TaskCategoryRepository(taskCatMap);
    }

    public String signIn(String userName, String password) {

        List<User> users = userCrud.readAll();

        for (User user : users) {
            if (user.getUserName().equals(userName) && user.verifyPassword(password)) {
                return sessionToken = user.getUniqueId();
            }
        }
        return sessionToken = null;
    }

    public String logOut() {
        return sessionToken = null;
    }




    /** Admin only access methods */
    public List<User> readAllUser() {
        validateToken();
        validateIsAdmin();
        return userCrud.readAll();
    }

    public List<Task> readAllTask() {
        validateToken();
        validateIsAdmin();
        return taskCrud.readAll();
    }

    public List<TaskCategory> readAllCategory() {
        validateToken();
        validateIsAdmin();
        return taskCatCrud.readAll();
    }
}
