/*@author: sparrow*/

package dbmanager;

import java.util.Scanner;

public class DBManager {
    
    
    /*Change these block only*****************/
    static String myDriver = "com.mysql.jdbc.Driver";
    static String myUrl = "jdbc:mysql://localhost:8889/batch4";
    static String user = "root";
    static String passw = "root";
    /*****************************************/
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        DBControllers dbc = new DBFunctions();
        
        
        try {
            /*******Load driver********************************************/
            Class.forName(myDriver);
            System.out.println("Driver loaded successfully!");
            
            /*******Create DB connection***********************************/
//            con = DriverManager.getConnection(myUrl,user,passw);
            
            System.out.println("Database connected successfully!");
            System.out.println("1. Press 1 to set one value\n"
                              +"2. Press 2 to set multiple values\n"
                              +"3. Press 3 to get a value\n"
                              +"4. Press 4 to get all values\n"
                              +"5. Press 5 to update a student age\n"
                              +"6. Press 6 to delete all values\n");
            char opt = scan.next().charAt(0);
            switch(opt){
                case '1': dbc.setOneValue();
                        break;
                case '2': dbc.setMultiValues();
                        break;
                case '3': dbc.getOneValue();
                        break;
                case '4': dbc.getAllValues();
                        break;
                case '5': dbc.updateValue();
                        break;
                case '6': dbc.deleteAllValues();
                        break;
                default: 
                        System.out.println("PICK THE DAMN CHOICE!!");
            }
        }
        catch(ClassNotFoundException cnf) {
            System.out.println("Problem loading driver...");
            System.out.println(cnf.getMessage());
        }
        
    }
    
}
