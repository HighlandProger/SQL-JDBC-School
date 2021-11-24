package ua.com.foxminded.domain;

public class Student {

    private final String name;
    private final String lastName;
    private long id;
    private Long groupId;

    public Student(long id, Long groupId, String name, String lastName) {
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

    public Long getGroupId(){
        return groupId;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

}
