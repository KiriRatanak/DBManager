/*@author: sparrow*/

package dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBManager {
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        DBControllers dbc = new DBFunctions();
        
        String myDriver = "com.mysql.jdbc.Driver";
        String myUrl = "jdbc:mysql://localhost:8889/batch4";
        String user = "root";
        String passw = "root";
        
        try {
            /*******Load driver********************************************/
            Class.forName(myDriver);
            System.out.println("Driver loaded successfully!");
            
            /*******Create DB connection***********************************/
            Connection con = DriverManager.getConnection(myUrl,user,passw);
            
            System.out.println("Database connected successfully!");
            System.out.println("1. Press 1 to set one value\n"
                              +"2. Press 2 to set multiple values\n"
                              +"3. Press 3 to get a value\n"
                              +"4. Press 4 to get all values\n"
                              +"5. Press 5 to update a student age\n"
                              +"6. Press 6 to delete all values\n");
            int opt = scan.nextInt();
            switch(opt){
                case 1: dbc.setOneValue(con);
                        break;
                case 2: dbc.setMultiValues(con);
                        break;
                case 3: dbc.getOneValue(con);
                        break;
                case 4: dbc.getAllValues(con);
                        break;
                case 5: dbc.updateValue(con);
                        break;
                case 6: dbc.deleteAllValues(con);
                        break;
                default: 
                        System.out.println("Pick the damn number!");
            }
        }
        catch(ClassNotFoundException cnf) {
            System.out.println("Problem loading driver...");
            System.out.println(cnf.getMessage());
        }
        catch(SQLException sqlex) {
            System.out.println("Problem with sql");
            System.out.println(sqlex.getErrorCode());
            System.out.println(sqlex.getSQLState());
        }
        
    }
    
}
