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

        //Create arraylist for product
        ArrayList<Product> productList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        //sql statemetn here
        String sql = "SELECT * FROM product";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String productID = rs.getString(1);
            String productName = rs.getString(2);
            double productPrice = rs.getDouble(3);
            int productQuantity = rs.getInt(4);
            Date productDate = rs.getDate(5);

            Product prod = new Product(productID, productName, productPrice,productQuantity,productDate);
            productList.add(prod);
        }

        rs.close();
        stmt.close();
        connection.close();

        return productList;
    }

    // ================= FIXED ADD PRODUCT =================
    public static void addRecord(Product prod) throws SQLException {

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "INSERT INTO product(ProductID,ProductName,ProductPrice,ProductQuantity,ProductDate) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, prod.getProductID());
        stmt.setString(2, prod.getProductName());
        stmt.setDouble(3, prod.getProductPrice());
        stmt.setInt(4, prod.getProductQuantity());

        // 🔴 FIX: ENSURE DATE IS NEVER NULL
        if (prod.getProductDate() != null) {
            stmt.setDate(5, prod.getProductDate());
        } else {
            stmt.setDate(5, new Date(System.currentTimeMillis()));
        }

        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }
    // ====================================================

    public static Product searchName(String searchName) throws SQLException {

        Product prod = null;
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT * FROM product WHERE ProductName = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, searchName);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            prod = new Product(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4),
                    rs.getDate(5));
        }

        rs.close();
        stmt.close();
        connection.close();

        return prod;
    }

    public static Product searchID(String searchID) throws SQLException {

        Product prod = null;
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT * FROM product WHERE ProductID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, searchID);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            prod = new Product(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4),
                    rs.getDate(5));
        }

        rs.close();
        stmt.close();
        connection.close();

        return prod;
    }

    public static int updateData(Product prod) throws SQLException {

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "UPDATE product SET ProductName=?, ProductPrice=?, ProductQuantity=?, ProductDate=? WHERE ProductID=?";
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, prod.getProductName());
        stmt.setDouble(2, prod.getProductPrice());
        stmt.setInt(3, prod.getProductQuantity());
        stmt.setDate(4, prod.getProductDate());
        stmt.setString(5, prod.getProductID());

        int row = stmt.executeUpdate();

        stmt.close();
        connection.close();

        return row;
    }

    public static int DeleteRecord(String DeletedByID) throws SQLException {

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "DELETE FROM product WHERE ProductID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, DeletedByID);

        int row = stmt.executeUpdate();

        stmt.close();
        connection.close();

        return row;
    }

    // ================= MONTHLY STOCK =================
    public static ArrayList<Product> getMonthlyStock(int month, int year) throws SQLException {

        ArrayList<Product> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT * FROM product WHERE MONTH(ProductDate)=? AND YEAR(ProductDate)=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, month);
        stmt.setInt(2, year);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            list.add(new Product(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4),
                    rs.getDate(5)));
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

    // ================= CHECK PRODUCT USED IN SALES =================
    public static boolean productHasSales(String productID) throws SQLException {

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT COUNT(*) FROM sales WHERE ProductID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, productID);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        boolean hasSales = rs.getInt(1) > 0;

        rs.close();
        stmt.close();
        connection.close();

        return hasSales;
    }

}
