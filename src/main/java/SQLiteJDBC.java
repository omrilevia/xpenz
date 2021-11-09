import org.sqlite.date.DateParser;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.time.*;
public class SQLiteJDBC {
    private static Connection connection = null;
    private SQLiteJDBC(){

    }

    public static void createConnection() {
        if(connection == null){
            try{
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:expenses.db");
                Statement s = connection.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS EXPENSES " +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "DATE DATETIME NOT NULL," +
                        "AMOUNT DECIMAL NOT NULL," +
                        "TAG VARCHAR(25) NOT NULL" +
                        ");";
                s.executeUpdate(sql);
                s.close();

            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }



    public static void writeExpense(String input) throws SQLException {
        createConnection();
        connection = DriverManager.getConnection("jdbc:sqlite:expenses.db");
        String[] parts = Arrays.stream(input.split(" ")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
        String[] dateParts = Arrays.stream(parts[0].split("-")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
        LocalDate ld = LocalDate.of(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]));

        String sqlString = "INSERT INTO EXPENSES (DATE, AMOUNT, TAG) values (?, ?, ?)";
        try{

            PreparedStatement ps = connection.prepareStatement(sqlString);
            ps.setDate(1, Date.valueOf(ld));
            ps.setDouble(2, Double.parseDouble(parts[1]));
            ps.setString(3, parts[2]);
            ps.executeUpdate();
            connection.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }

    }

    public static void dumpRows(){
        String sql = "SELECT * FROM EXPENSES";

    }
}
