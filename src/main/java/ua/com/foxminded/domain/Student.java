package ua.com.foxminded.domain;

public class Student {

    private long id;
    private long groupId;
    private final String name;
    private final String lastName;

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

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

}
