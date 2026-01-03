import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseConnectionProduct {

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

    // Add product
    public static void addRecord(Product prod) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "INSERT INTO product(ProductID,ProductName,ProductPrice,ProductQuantity,ProductDate) values(?,?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, prod.getProductID());
        stmt.setString(2, prod.getProductName());
        stmt.setDouble(3, prod.getProductPrice());
        stmt.setInt(4, prod.getProductQuantity());
        stmt.setDate(5, prod.getProductDate());

        int count = stmt.executeUpdate();
        System.out.println("The number of inserted product : " + count);
        stmt.close();
        connection.close();

    }

    // searchingName
    public static Product searchName(String searchName) throws SQLException {

        Product prod = null;
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT * FROM product where ProductName = ? ";
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

        String sql = "SELECT * FROM product where ProductID = ? ";
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

    // Update Data
    public static int updateData(Product prod) throws SQLException {

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "UPDATE product SET ProductName = ?, ProductPrice = ?, ProductQuantity = ?, ProductDate = ? WHERE ProductID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);

        //
        stmt.setString(1, prod.getProductName());
        stmt.setDouble(2, prod.getProductPrice());
        stmt.setInt(3, prod.getProductQuantity());
        stmt.setDate(4, prod.getProductDate());
        stmt.setString(5, prod.getProductID()); // WHERE clause

        int row = stmt.executeUpdate();
        System.out.println("rows affected = " + row);

        stmt.close();
        connection.close();
        return row;
    }

    // delete by id
    public static int DeleteRecord(String DeletedByID) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "DELETE FROM product WHERE ProductID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, DeletedByID);

        int row = stmt.executeUpdate();
        System.out.println("Affected Row : " + row);
        stmt.close();
        connection.close();
        return row;
    }

    // filter Data based on month & year
    public static ArrayList<Product> getMonthlyStock(int month, int year) throws SQLException {

        ArrayList<Product> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT * FROM product WHERE MONTH(ProductDate) = ? AND YEAR(ProductDate) = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, month);
        stmt.setInt(2, year);

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

            list.add(prod);
        }

        rs.close();
        stmt.close();
        connection.close();

        return list;
    }

    public static int reduceStock(String productID, int qty) throws SQLException {

        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "UPDATE product SET ProductQuantity = ProductQuantity - ? WHERE ProductID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, qty);
        stmt.setString(2, productID);

        int row = stmt.executeUpdate();
        stmt.close();
        conn.close();

        return row;
    }

}
