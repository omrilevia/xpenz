import org.sqlite.date.DateParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.time.*;
import java.io.*;
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
                        "DATE TEXT NOT NULL," +
                        "AMOUNT INTEGER NOT NULL," +
                        "TAG TEXT NOT NULL" +
                        ");";
                s.executeUpdate(sql);
                s.close();
                connection.close();
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
        //String[] dateParts = Arrays.stream(parts[0].split("-")).filter(e -> e.trim().length() > 0).toArray(String[]::new);
        //LocalDate ld = LocalDate.of(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]));

        String sqlString = "INSERT INTO EXPENSES (DATE, AMOUNT, TAG) values (?, ?, ?)";
        try{
            //System.out.println(Date.valueOf(ld).toString());
            PreparedStatement ps = connection.prepareStatement(sqlString);
            ps.setString(1, parts[0]);
            String s = String.valueOf(100 * Double.parseDouble(parts[1]));
            String[] sParts = s.split("\\.");
            ps.setInt(2, Integer.valueOf(sParts[0]) );
            ps.setString(3, parts[2]);
            ps.executeUpdate();
            ps.close();
            connection.close();
        }
        catch(SQLException se){
            se.printStackTrace();
        }

    }

    public static void dumpRows(){
        //String sql = "SELECT * FROM EXPENSES WHERE ID <= MAX(ID) AND ID >= (MAX(ID) - 5)";
        createConnection();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:expenses.db");
            DBTablePrinter.printTable(connection, "expenses");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void toCSV(String name){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:expenses.db");
            String sql = "SELECT * FROM expenses";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(name));

            // write header line containing column names
            fileWriter.write("id, date, amount, tag");

            while (result.next()) {
                Integer id = result.getInt("id");
                String date = result.getString("date");
                String amount = String.valueOf(result.getInt("amount")/100.0);
                String tag = result.getString("tag");



                String line = String.format("\"%s\",%s,%.sf,%s",
                        id, date, amount, tag);

                fileWriter.newLine();
                fileWriter.write(line);
            }

            statement.close();
            fileWriter.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void showTotalPerMonthByTag(){
        String sql = "select TOTAL(amount) as total, \n" +
                "       strftime(\"%m-%Y\", date) as 'month-year', tag \n" +
                "       from expenses group by strftime(\"%m-%Y\", date), tag;";
        createConnection();
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:expenses.db");
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            while(rs.next()){

                for(int i = 0; i < numCols; i++){
                    switch(i){
                        case 0:
                            System.out.print( rs.getInt(1) /100.0 + " ");
                            break;
                        case 1:
                            System.out.print(rs.getString(2) + " ");
                            break;
                        case 2:
                            System.out.print(rs.getString(3));
                            break;
                    }

                }
                System.out.println();
            }
            //DBTablePrinter.printResultSet(rs);
            ps.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }
}
