package dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class DBFunctions implements DBControllers{
    Scanner scan = new Scanner(System.in);
    
    
    static Connection con = null;
    int id,age,lastId,autoId;
    int parameterIndex;
    String TABLE_NAME = "student";
    String firstName;
    String lastName;
    String selectQuery;
    String insertQuery = String.format("insert into %s values(?,?,?,?)",TABLE_NAME);
    String deleteQuery = String.format("delete from %s",TABLE_NAME);
    String updateQuery = String.format("update %s set ",TABLE_NAME);
    
    @Override
    public void setOneValue(){        
        Student s = inputData();
        setData(insertQuery,s);
    }
    
    @Override
    public void setMultiValues(){
        System.out.println("Enter the number of student you want to add: ");
        int num = scan.nextInt();
        while(num>0) {
            Student s = inputData();
            setData(insertQuery,s);
            num--;
        }
    }
    
    @Override
    public void getOneValue(){
        System.out.println("Enter the id of the student: ");
        int i = scan.nextInt();
        selectQuery = String.format("select * from %s where id = %d;",TABLE_NAME,i);
        getData(selectQuery);
        
    }
    @Override
    public void getAllValues(){
        selectQuery = String.format("select * from %s;",TABLE_NAME);
        getData(selectQuery);    
    }
    
    @Override
    public void updateValue(){
        try {
            
            System.out.println("Enter id of the student to update:");
            int id = scan.nextInt();
            
            //Getting user choice for updation
            switch(getChoice()){
                case '1':
                        updateQuery = updateQuery + "age = ? where id = ?;";
                        updateAge(id,updateQuery);
                        break;
                case '2': 
                        updateQuery = updateQuery + "first_name = ? where id = ?;";
                        updateFirstName(id,updateQuery);
                        break;
                case '3': 
                        updateQuery = updateQuery + "last_name = ? where id = ?;";
                        updateLastName(id,updateQuery);
                        break;
                default:
                    System.out.println("GOD DAMMIT, CARL! PICK THE DAMN NUMBER!");
            }
        }
        catch(SQLException sqex) {
            System.out.println("Unable to update a value!");
        }    
    }
    
    @Override
    public void deleteAllValues(){
        try {
            Statement stt = getConnection().createStatement();
            stt.execute(deleteQuery);
            System.out.println("Deletion complete");
        }
        catch(SQLException sqex) {
            System.out.println("Unable to delete");
        }
    }
    
    
    // Private functions that are called only in this particular file
    
    private Student inputData() {
        System.out.println("Enter info(age/first-name/last-name): ");
        age = scan.nextInt();
        firstName = scan.next();
        lastName = scan.next();
        Student s = new Student(id,age,firstName,lastName);
        return s;
    }
    
    private void setData(String insertQuery, Student s) {
        try {
            PreparedStatement preStt = getConnection().prepareStatement(insertQuery);
            parameterIndex = 1;
            preStt.setInt(parameterIndex,autoGenId());
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
    
    private void getData(String selectQuery){    
        try {
            Statement stt = getConnection().createStatement();
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
    
    private char getChoice() {
        System.out.println("1. Press 1 to update age\n"
                          +"2. Press 2 to update first name\n"
                          +"3. Press 3 to update last name\n");
        char ch = scan.next().charAt(0);
        return ch;
    }
    
    private void updateAge(int id, String query) throws SQLException {
        PreparedStatement preStt = getConnection().prepareStatement(query);
        System.out.println("Enter new age:");
        int age = scan.nextInt();
        parameterIndex = 1;
        preStt.setInt(parameterIndex,age);
        preStt.setInt(++parameterIndex,id);
        preStt.execute();
    }
    
    private void updateFirstName(int id, String query) throws SQLException {
        PreparedStatement preStt = getConnection().prepareStatement(query);
        System.out.println("Enter new first name :");
        String fname = scan.next();
        parameterIndex = 1;
        preStt.setString(parameterIndex,fname);
        preStt.setInt(++parameterIndex,id);
        preStt.execute(); 
    }
    
    private void updateLastName(int id, String query) throws SQLException {
        PreparedStatement preStt = getConnection().prepareStatement(query);
        System.out.println("Enter new last name :");
        String lname = scan.next();
        parameterIndex = 1;
        preStt.setString(parameterIndex,lname);
        preStt.setInt(++parameterIndex,id);
        preStt.execute(); 
    }
    
    private int autoGenId() {
        int newId=1;
        try {
            selectQuery = "select * from student;";
            Statement stt = getConnection().createStatement();
            ResultSet rs = stt.executeQuery(selectQuery);
            if(rs.last()){
                lastId = rs.getInt(1);
                newId = lastId + 1;
            }
        }
        catch(SQLException sqex) {
            System.out.println("Unable to generate new id!");
            System.out.println(sqex.getErrorCode());
            System.out.println(sqex.getSQLState());
        }
        return newId;
    }
    
    private static Connection getConnection() {
        try {
            con = DriverManager.getConnection(DBManager.myUrl,DBManager.user,DBManager.passw);
        }
        catch(SQLException sqlex) {
            System.out.println("Problem with sql...");
            System.out.println(sqlex.getErrorCode());
            System.out.println(sqlex.getSQLState());
        }
        return con;
    }
}
