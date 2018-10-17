/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Restaurant;

/**
 *
 * @author kos
 */
import java.sql.*;
import java.util.*;

// dish details
public class Dish {
    String name; // name of the dish
    List<String> extra; // extra ingredients
    List<String> without; // excluded ingredients
    int pizza; // pizza size (if provided)
    String pasta; // chosen pasta (if provided)


    public Dish() {
        name = null;
        extra = new ArrayList<String>();
        without = new ArrayList<String>();
        pizza = 0;
        pasta = null;
    }
    
    // returns the type of a main dish:
    // pasta, pizza, rice, gnocchi
    public static String getCategory(String name)
    {
        String categ=null;
        try{
            ResultSetMetaData rsmd=null;
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection(  
            "jdbc:mysql://localhost:3306/restaurant","root",""); 

            Statement stmt=con.createStatement();  
            ResultSet rs = stmt.executeQuery("select main from dish "
                    + "where name = '" + name + "'");
            
            if (rs.next()) categ = rs.getString(1);

            con.close();
        } catch(Exception e){ System.out.println(e);}  
        return categ;
    }

}
