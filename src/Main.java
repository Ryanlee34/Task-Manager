import service.DataHandler;
import model.*;
import service.TaskManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        DataHandler dataHandler = new DataHandler();

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

        TaskManager taskManager = new TaskManager(
                userMap, taskMap, taskCatMap, userToCategory, taskToTaskCat, dataHandler
        );


    }
}
