package pl.coderslab.model;

public class Solution {
    private int id;
    private String created;
    private String updated;
    private String description;
    private int mark;
    private String commentary;

    private int exerciseId;
    private int userId;

    public Solution(String created, String updated, String description, int exerciseId, int userId) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exerciseId = exerciseId;
        this.userId = userId;
    }

    public Solution() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String comment) {
        this.commentary = comment;
    }

    @Override
    public String toString() {
        return "id: " + this.id + " | exercise id: " + this.exerciseId + " | user id: " + this.userId +
                "\ncreated: " + this.created + "\nupdated: " + this.updated + "\ndescription: " + this.description +
                "\nmark: " + this.mark + "\ncomment: " + this.commentary;
    }
}
