package ua.com.foxminded.domain;

public class Student {

    private int id;
    private int groupId;
    private String name;
    private String lastName;

    public Student(int id, int groupId, String name, String lastName) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.lastName = lastName;
    }

    public Student(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
