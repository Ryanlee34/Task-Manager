package repository;
import model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TaskRepository implements CrudRepository<Task, Integer> {
    private final Map<Integer, Task> taskMap;

    private void validateTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be Null.");
        }
    }

    private void validateId(Task task) {
        if (!taskMap.containsKey(task.getId())) {
            throw new IllegalArgumentException("Task key Invalid.");
        }
    }

    private void validateId(int id) {
        if (!taskMap.containsKey(id)) {
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


    public TaskRepository(Map<Integer, Task> taskMap) {
        this.taskMap = taskMap;
    }

    @Override
    public void create(Task task) {
        validateTask(task);
        validateId(task);
        validateTitle(task);
        validateDescription(task);
        validateStatus(task);
        validatePriority(task);
        taskMap.put(task.getId(), task);
    }

    @Override
    public Task read(Integer id) {
        validateId(id);
        return taskMap.get(id);

    }

    @Override
    public List<Task> readAll() {
        return new ArrayList<>(taskMap.values());

    }

    @Override
    public void update(Integer id, Task task) {
        validateTask(task);
        validateId(task);
        validateTitle(task);
        validateDescription(task);
        validateStatus(task);
        validatePriority(task);

        taskMap.put(task.getId(), task);
    }

    @Override
    public void delete(Integer id) {
        validateId(id);

        taskMap.remove(id);
    }


}






























