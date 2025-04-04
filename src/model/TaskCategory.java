package model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

/** Class that defines hashMap and relationships with task category class and tacitness of Task class */

public class TaskCategory {

    /** Set variables*/
    private final String categoryId;
    private String name;
    private String description;

    /** Validation Functions*/
    private void validateId(String categoryId) {
        if (categoryId== null || categoryId.trim().isEmpty()) {
            throw new IllegalArgumentException("Category Id can't be Null or Empty.");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Name Passed.");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Description Entered.");
        }
    }

    /** Constructor*/
    public TaskCategory(String name, String description) {
        this.categoryId = UUID.randomUUID().toString();
        validateId(categoryId);
        validateName(name);
        validateDescription(description);

        this.name = name;
        this.description = description;

    }

    /** Constructor for Jackson */
    @JsonCreator
    public static TaskCategory create(
            @JsonProperty("categoryId") String categoryId,
            @JsonProperty("String name") String name,
            @JsonProperty("String description") String description
    ) {
        return new TaskCategory(categoryId, name, description);
    }
    private TaskCategory (String categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    /** Getters*/
    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /** Setters*/

    public void setName(String name) {
        validateName(name);
        this.name =  name;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    @Override
    public String toString() {
        return "TaskCategory{" +
                "categoryId= " + categoryId+ '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


    /** OverRide Methods*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskCategory taskCategory = (TaskCategory) o;

        // If id is a primitive type (like int)
        return categoryId == taskCategory.categoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId);
    }
}
