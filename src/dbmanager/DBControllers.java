package dbmanager;

import java.sql.Connection;

public interface DBControllers {
    
    public void setOneValue(Connection con);
    public void setMultiValues(Connection con);
    public void getOneValue(Connection con);
    public void getAllValues(Connection con);
    public void updateValue(Connection con);
    public void deleteAllValues(Connection con);
    
}
