package repository;
import model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskRepository implements CrudRepository<String, Task> {
    private final Map<String, Task> taskMap;

    private void validateTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be Null.");
        }
    }

    private void validateId(Task task) {
        if (!taskMap.containsKey(task.getTaskId())) {
            throw new IllegalArgumentException("Task key Invalid.");
        }
    }

    private void validateId(String id) {
        if (!taskMap.containsKey(id)) {
            throw new IllegalArgumentException("Task key Invalid.");
        }
    }

    private void validateUnusedId(Task task) {
        if (taskMap.containsKey(task.getTaskId())) {
            throw new IllegalArgumentException("Task key Invalid.");
        }
    }


    private void validateTitle(Task task) {
        if (task.getTitle() == null) {
            throw new IllegalArgumentException("Title Cannot be Null");
        }
    }

    private void validateDescription(Task task) {
        if (task.getDescription() == null) {
            throw new IllegalArgumentException("Description Cannot be Null");
        }
    }

    private void validatePriority(Task task) {
        if (task.getPriority() == null) {
            throw new IllegalArgumentException("Priority Cannot be Null");
        }
    }

    private void validateStatus(Task task) {
        if (task.getStatus() == null) {
            throw new IllegalArgumentException("Status Cannot be Null");
        }
    }


    public TaskRepository(Map<String, Task> taskMap) {
        this.taskMap = taskMap;
    }

    @Override
    public void create(Task task) {
        validateTask(task);
        validateUnusedId(task);
        validateTitle(task);
        validateDescription(task);
        validateStatus(task);
        validatePriority(task);
        taskMap.put(task.getTaskId(), task);
    }

    @Override
    public Task read(String id) {

        return taskMap.get(id);

    }

    @Override
    public List<Task> readAll() {
        return new ArrayList<>(taskMap.values());

    }

    @Override
    public void update(String id, Task task) {
        validateTask(task);

        validateTitle(task);
        validateDescription(task);
        validateStatus(task);
        validatePriority(task);

        taskMap.put(task.getTaskId(), task);
    }

    @Override
    public void delete(String id) {


        taskMap.remove(id);
    }

    public Map<String, Task> getTaskMap () {
        return new HashMap<>(taskMap);
    }


}






























