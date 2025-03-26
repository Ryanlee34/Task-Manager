package model;
import java.util.Objects;

/** Class that defines hashMap and relationships with task category class and tacitness of Task class */

public class TaskCategory {

    /** Set variables*/
    private int categoryId;
    private String name;
    private String description;

    /** Validation Functions*/
    private void validateId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Invalid Argument for Id passed.");
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
    public TaskCategory(int id, String name, String description) {
        validateId(id);
        validateName(name);
        validateDescription(description);

        this.categoryId = categoryId;
        this.name = name;
        this.description = description;

    }

    /** Getters*/
    public int getId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /** Setters*/
    public void setId(int id) {
        validateId(id);
        this.categoryId = id;
    }

    public void setName(String name) {
        validateName(name);
        this.name =  name;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
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
