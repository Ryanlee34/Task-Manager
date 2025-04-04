package model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

/** Class that defines and initializes attributes to each individual class*/

public class Task {

    private final String taskId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private Status status;
    public enum Priority{
        HIGH("HIGH"),
        MEDIUM("MEDIUM"),
        LOW("LOW");

        private final String displayName;

        Priority(String displayName) {
            this.displayName = displayName;
        }

        //Getter for name string within enum
        public String getDisplayName() {
            return displayName;
        }
    }

    public enum Status{
        TODO("TO-DO"),
        IN_PROGRESS("IN PROGRESS"),
        COMPLETED("COMPLETED");

        private final String statusName;

        Status(String statusName) {
            this.statusName = statusName;
        }

        //Getter for name string within enum
        public String getStatusName(){
            return statusName;
        }
    }
    private void validateId(String taskId){
        if (taskId== null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task Id can't be Null or Empty.");
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
    public Task(String title, String description, LocalDate dueDate, Status status, Priority priority) {
        this.taskId = UUID.randomUUID().toString();
        validateId(taskId);


        validateTitle(title);
        validatedDescription(description);
        validateDueDate(dueDate);
        validateStatus(status);
        validatePriority(priority);


        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
    }

    /** Constructor for Jackson */
    @JsonCreator
    public static Task create(
            @JsonProperty("taskId") String taskId,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
        @JsonProperty("dueDate") LocalDate dueDate,
        @JsonProperty("status") Status status,
        @JsonProperty("priority") Priority priority
    ) {
        return new Task(taskId, title, description, dueDate, status, priority);

    }
    private Task(String taskId,String title, String description, LocalDate dueDate, Status status, Priority priority) {
            this.taskId = taskId;
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.status = status;
            this.priority = priority;
        }




    /** Getter Methods */
    public String getTaskId() {
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
        return "Task{Title: " + title + '\'' +
                ", Description: "+description+ '\'' +
                ", Task ID: "+ taskId + '\'' +
                ", Due Date: "+ dueDate + '\'' +
                ", Status: "
                + status+ '\''  +
                ", Priority: " + priority+ '\'' +
                "}";
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
        return 31 *  taskId.hashCode() + title.hashCode() + description.hashCode() + dueDate.hashCode() + status.hashCode() + priority.hashCode();
    }




}
