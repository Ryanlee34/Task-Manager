package repository;
import model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskCategoryRepository implements CrudRepository<String, TaskCategory> {
    private final Map<String, TaskCategory> taskCatMap;


    private void validateTaskCat(TaskCategory taskCategory) {
        if(taskCategory == null) {
            throw new IllegalArgumentException("Task Category cannot be Null");
        }
    }

    private void validateId(String id) {
        if (taskCatMap.containsKey(id)) {
            throw new IllegalArgumentException("Id must be Unique.");
        }
    }

    private void validateId(TaskCategory taskCategory) {
        if(taskCatMap.containsKey(taskCategory.getCategoryId())) {
            throw new IllegalArgumentException("ID must be Unique.");
        }
    }

    private void validateName(TaskCategory taskCategory) {
        if ((taskCategory.getName() == null) || (taskCategory.getName().trim().isEmpty())) {
            throw new IllegalArgumentException("Task Name cannot be null or Empty.");
        }
    }

    private void validateDescription(TaskCategory taskCategory) {
        if ((taskCategory.getDescription() == null) || (taskCategory.getDescription().trim().isEmpty())) {
            throw new IllegalArgumentException("Task Category cannot be null or Empty.");
        }
    }

    public TaskCategoryRepository (Map taskCatMap) {
        this.taskCatMap = taskCatMap;
    }

    @Override
    public void create(TaskCategory taskCategory) {
        validateTaskCat(taskCategory);
        validateId(taskCategory);
        validateName(taskCategory);
        validateDescription(taskCategory);
        taskCatMap.put(taskCategory.getCategoryId(), taskCategory);
    }

    @Override
    public TaskCategory read(String id) {
        return taskCatMap.get(id);
    }

    @Override
    public List<TaskCategory> readAll() {
        return new ArrayList<>(taskCatMap.values());
    }

    @Override
    public void update(String id, TaskCategory taskCategory) {


        taskCatMap.put(taskCategory.getCategoryId(), taskCategory);

    }

    @Override
    public void delete(String id) {

        taskCatMap.remove(id);

    }

    public Map<String, TaskCategory> getCategoryMap () {
        return new HashMap<>(taskCatMap);
    }


}
