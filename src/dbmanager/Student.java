package dbmanager;

public class Student<List> {
    private int id;
    private int age;
    private String firstName;
    private String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    
    public Student(int id, int age, String firstName, String lastName) {
        setId(id);
        setAge(age);
        setFirstName(firstName);
        setLastName(lastName);
    }
    
}
