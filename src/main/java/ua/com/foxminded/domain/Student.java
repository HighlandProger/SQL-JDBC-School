package ua.com.foxminded.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id == student.id &&
            name.equals(student.name) &&
            lastName.equals(student.lastName) &&
            Objects.equals(groupId, student.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, id, groupId);
    }
}
