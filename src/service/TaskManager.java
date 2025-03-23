package service;
import repository.*;
import model.*;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private Map<String, User> userMap = new HashMap<>();
    private Map<String, Task> taskMap = new HashMap<>();
    private Map<String, TaskCategory> categoryMap= new HashMap<>();
    private Map<HashMap<String, User>, HashMap<String, Task>> relationshipMap = new HashMap<>();

    private CrudRepository<User, String> userCrud;

    /**Constructor */
    public TaskManager(Map userMap, Map taskMap, Map categoryMap, Map relationshipMap) {
        this.userCrud = new UserRepository(userMap);
        this.taskCrud = new HashMap<>();
        this.categoryCrud = new HashMap<>();
        this.relationshipCrud = new HashMap<>();
    }

    /**User Methods*/

    /**Category Methods*/

    /**Task Management Methods*/

    /**Search/Filter Methods*/
}