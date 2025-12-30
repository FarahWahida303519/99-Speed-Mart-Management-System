import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/99_speedmart_db";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public static ArrayList<Product> readData() throws SQLException {

        ArrayList<Product> productList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT * FROM product";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String productID = rs.getString(1);
            String productName = rs.getString(2);
            double productPrice = rs.getDouble(3);
            int productQuantity = rs.getInt(4);
            Date productDate = rs.getDate(5);

            Product prod = new Product(
                    productID,
                    productName,
                    productPrice,
                    productQuantity,
                    productDate);

            productList.add(prod);
        }

        rs.close();
        stmt.close();
        connection.close();

        return productList;
    }

    // searchingName
    public static Product searchName(String searchName) throws SQLException {

        Product prod = null;
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT * FROM product where productName = ? ";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, searchName);
        ResultSet rs = stmt.executeQuery();

        // use if instead while since we want to display 1 product only not many
        if (rs.next()) {
            String productID = rs.getString(1);
            String productName = rs.getString(2);
            double productPrice = rs.getDouble(3);
            int productQuantity = rs.getInt(4);
            Date productDate = rs.getDate(5);

            prod = new Product(productID, productName, productPrice, productQuantity, productDate);

        } // close while

        rs.close();
        stmt.close();
        connection.close();
        return prod;

    }

    // search by id
    public static Product searchID(String searchID) throws SQLException {

        Product prod = null;
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT * FROM product where productID = ? ";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, searchID);
        ResultSet rs = stmt.executeQuery();

        // use if instead while since we want to display 1 product only not many
        if (rs.next()) {
            String productID = rs.getString(1);
            String productName = rs.getString(2);
            double productPrice = rs.getDouble(3);
            int productQuantity = rs.getInt(4);
            Date productDate = rs.getDate(5);

            prod = new Product(productID, productName, productPrice, productQuantity, productDate);

        } // close while

        rs.close();
        stmt.close();
        connection.close();
        return prod;

    }
}
