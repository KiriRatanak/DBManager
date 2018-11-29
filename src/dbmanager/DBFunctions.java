package dbmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class DBFunctions implements DBControllers{
    Scanner scan = new Scanner(System.in);
    
    //    String TABLE_NAME = "student";
    
    int id,age,lastId,autoId;
    int parameterIndex;
    String firstName;
    String lastName;
    String insertQuery = "insert into student values(?,?,?,?)";
    String selectQuery;
    String deleteQuery = "delete from student";
    String updateQuery = "update student set age = ? where id = ?;";
    
    @Override
    public void setOneValue(Connection con){        
        Student s = inputData();
        setData(con,insertQuery,s);
    }
    
    @Override
    public void setMultiValues(Connection con){
        System.out.println("Enter the number of student you want to add: ");
        int num = scan.nextInt();
        while(num>0) {
            Student s = inputData();
            setData(con,insertQuery,s);
            num--;
        }
    }
    
    @Override
    public void getOneValue(Connection con){
        System.out.println("Enter the id of the student: ");
        int i = scan.nextInt();
        selectQuery = String.format("select * from student where id = %d;",i);
        getData(con,selectQuery);
        
    }
    @Override
    public void getAllValues(Connection con){
        selectQuery = "select * from student;";
        getData(con,selectQuery);
        
    }
    
    @Override
    public void updateValue(Connection con){
        try {
            PreparedStatement preStt = con.prepareStatement(updateQuery);
            System.out.println("Enter id of the student to update:");
            int id = scan.nextInt();
            System.out.println("Enter new age:");
            int age = scan.nextInt();
            parameterIndex = 1;
            preStt.setInt(parameterIndex,age);
            preStt.setInt(++parameterIndex,id);
            preStt.execute();
        }
        catch(SQLException sqex) {
            System.out.println("Unable to update a value!");
        }
        
        
    }
    
    @Override
    public void deleteAllValues(Connection con){
        try {
            Statement stt = con.createStatement();
            stt.execute(deleteQuery);
            System.out.println("Deletion complete");
        }
        catch(SQLException sqex) {
            System.out.println("Unable to delete");
        }
    }
    
    private Student inputData() {
        System.out.println("Enter info(age/first-name/last-name): ");
        age = scan.nextInt();
        firstName = scan.next();
        lastName = scan.next();
        Student s = new Student(id,age,firstName,lastName);
        return s;
    }
    
    private void setData(Connection con, String insertQuery, Student s) {
        try {
            PreparedStatement preStt = con.prepareStatement(insertQuery);
            parameterIndex = 1;
            preStt.setInt(parameterIndex,autoGenId(con));
            preStt.setInt(++parameterIndex,s.getAge());
            preStt.setString(++parameterIndex,s.getFirstName());
            preStt.setString(++parameterIndex,s.getLastName());
            preStt.addBatch();
            preStt.executeBatch();
            System.out.println("Query executed successfully");
        }
        catch(SQLException sqex) {   
            System.out.println("Unable to set the value");
        }
    }
    
    private void getData(Connection con,String selectQuery){    
        try {
            Statement stt = con.createStatement();
            ResultSet rs = stt.executeQuery(selectQuery);
            while(rs.next()) {
                parameterIndex = 1;
                int id = rs.getInt(parameterIndex);
                int age = rs.getInt(++parameterIndex);
                String firstName = rs.getString(++parameterIndex);
                String lastName = rs.getString(++parameterIndex);
                System.out.format("ID: %d - Age: %d - Name: %s %s\n",id,age,firstName,lastName);
            }
            System.out.println("Query executed successfully\n");
        }
        catch(SQLException sqex) {   
            System.out.println("Unable to get the values\n");
        }
    }
    
    private int autoGenId(Connection con) {
        int newId=0;
        try {
            selectQuery = "select * from student;";
            Statement stt = con.createStatement();
            ResultSet rs = stt.executeQuery(selectQuery);
            rs.last();
            lastId = rs.getInt(1);
            newId = lastId + 1;
        }
        catch(SQLException sqex) {
            System.out.println("Unable to generate new id!");
            System.out.println(sqex.getErrorCode());
            System.out.println(sqex.getSQLState());
        }
        return newId;
    }
    
}
