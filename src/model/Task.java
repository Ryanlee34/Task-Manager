package model;
import java.time.LocalDate;

/** Class that defines and initializes attributes to each individual class*/

public class Task {

    private int taskId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private Status status;
    public enum Priority{
        HIGH("High"),
        MEDIUM("Medium"),
        LOW("Low");

        private String displayName;

        Priority(String displayName) {
            this.displayName = displayName;
        }

        //Getter for name string within enum
        public String getDisplayName() {
            return displayName;
        }
    }

    public enum Status{
        TODO("To-do"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed");

        private String statusName;

        Status(String statusName) {
            this.statusName = statusName;
        }

        //Getter for name string within enum
        public String getStatusName(){
            return statusName;
        }
    }
    private void validateId(int id){
        if (id <= 0){
            throw new IllegalArgumentException("Task ID must be a positive number greater than zero.");
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
    }

    private void validatedDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
    }

    private void validateDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("Due Date cannot be null.");
        }
    }

    private void validateStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
    }

    private void validatePriority(Priority priority){
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null.");
        }

    }

    /** Constructor */
    public Task(int id, String title, String description, LocalDate dueDate, Status status, Priority priority) {

        validateId(id);
        validateTitle(title);
        validatedDescription(description);
        validateDueDate(dueDate);
        validateStatus(status);
        validatePriority(priority);

        this.taskId = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
    }

    /** Getter Methods */
    public int getId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    /**Setters */
    public void setId(int id) {
        validateId(id);
        this.taskId = id;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void setDescription(String description) {
        validatedDescription(description);
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        validateDueDate(dueDate);
        this.dueDate = dueDate;
    }

    public void setStatus(Status status) {
        validateStatus(status);
        this.status = status;
    }

    public void  setPriority(Priority priority) {
        validatePriority(priority);
        this.priority = priority;
    }

    /** Override Methods */
    @Override
    public String toString(){
        return "Task{Title: " + title + ", Description: "+description+ ", Task ID: "+ taskId + ", Due Date: "+ dueDate + ", Status: "+ status+ ",Priority: " + priority+ "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Task task = (Task) obj;
        return this.title.equals(task.title);
    }

    @Override
    public int hashCode() {
        return 31 *  taskId + title.hashCode() + description.hashCode() + dueDate.hashCode() + status.hashCode() + priority.hashCode();
    }




}
