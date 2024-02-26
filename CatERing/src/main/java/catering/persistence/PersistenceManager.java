package catering.persistence;

import java.sql.*;

public class PersistenceManager {
    private static final String url = "jdbc:mysql://localhost:3306/catering";
    private static final String username = "root";
    private static final String password = "";
    private static int lastId;

    public static String escapeString(String input) {
        input = input.replace("\\", "\\\\");
        input = input.replace("\'", "\\\'");
        input = input.replace("\"", "\\\"");
        input = input.replace("\n", "\\n");
        input = input.replace("\t", "\\t");
        return input;
    }

    public static void testSQLConnection() {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String role = rs.getString("role");
                System.out.println(name + " ha id = " + id + " e ruolo = " + role);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void myExecuteQuery(String query, ResultHandler handler) {
        try(Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    handler.handle(rs);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int[] ExecuteBatchUpdate(String paramQuery, int itemNumber, BatchUpdateHandler handler) {
        int[] result = new int[0];
        try(Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = conn.prepareStatement(paramQuery, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < itemNumber; i++) {
                    handler.handleBatchItem(ps, i);
                    ps.addBatch();
                }
                result = ps.executeBatch();
                ResultSet keys = ps.getGeneratedKeys();
                int count = 0;
                while (keys.next()) {
                    handler.handleGeneratedIds(keys, count);
                    count ++;
                }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int myExecuteUpdate(String update) {
        int result = 0;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(update, Statement.RETURN_GENERATED_KEYS)) {
                result = ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    lastId = rs.getInt(1);
                } else {
                    lastId = 0;
                }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static int getLastId() {
        return lastId;
    }

}
