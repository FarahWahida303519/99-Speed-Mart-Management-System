import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseConnectionProduct {

    //connections db
    private static final String URL = "jdbc:mysql://localhost:3306/99_speedmart_db";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    //read product record
    public static ArrayList<Product> readData() throws SQLException {

        //Create arraylist to store product
        ArrayList<Product> productList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        //sql statemetn here
        String sql = "SELECT * FROM product";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        //loop throught rs
        while (rs.next()) {
            //retrieve each column
            String productID = rs.getString(1);
            String productName = rs.getString(2);
            double productPrice = rs.getDouble(3);
            int productQuantity = rs.getInt(4);
            Date productDate = rs.getDate(5);

            //create prod obj
            Product prod = new Product(productID, productName, productPrice,productQuantity,productDate);
            productList.add(prod);//add into list
        }

        //close connection db
        rs.close();
        stmt.close();
        connection.close();

        return productList;
    }

    // Add prod
    public static void addRecord(Product prod) throws SQLException {

        //connect to db
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        //sql statement
        String sql = "INSERT INTO product(ProductID,ProductName,ProductPrice,ProductQuantity,ProductDate) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);

        //set values
        stmt.setString(1, prod.getProductID());
        stmt.setString(2, prod.getProductName());
        stmt.setDouble(3, prod.getProductPrice());
        stmt.setInt(4, prod.getProductQuantity());

        // to chck data not null
        if (prod.getProductDate() != null) {
            stmt.setDate(5, prod.getProductDate());
        } else {
            //use current date
            stmt.setDate(5, new Date(System.currentTimeMillis()));
        }

        stmt.executeUpdate();//insert

        //close db
        stmt.close();
        connection.close();
    }

    //searching
    public static Product searchName(String searchName) throws SQLException {

        Product prod = null;
        //connect to db
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        //sql statement
        String sql = "SELECT * FROM product WHERE ProductName = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, searchName);
        ResultSet rs = stmt.executeQuery();

        //record found
        if (rs.next()) {
            prod = new Product(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4),
                    rs.getDate(5));
        }

        //close db
        rs.close();
        stmt.close();
        connection.close();

        return prod;
    }

    //searching id
    public static Product searchID(String searchID) throws SQLException {

        Product prod = null;
        //connect db
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

    
    //update 
    public static int updateData(Product prod) throws SQLException {

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        //sql statement
        String sql = "UPDATE product SET ProductName=?, ProductPrice=?, ProductQuantity=?, ProductDate=? WHERE ProductID=?";
        PreparedStatement stmt = connection.prepareStatement(sql);

        //set new data
        stmt.setString(1, prod.getProductName());
        stmt.setDouble(2, prod.getProductPrice());
        stmt.setInt(3, prod.getProductQuantity());
        stmt.setDate(4, prod.getProductDate());
        stmt.setString(5, prod.getProductID());

        int row = stmt.executeUpdate();//execute update

        //close db
        stmt.close();
        connection.close();

        return row;
    }

    //delete
    public static int DeleteRecord(String DeletedByID) throws SQLException {

        //connect to db
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        //sql statement
        String sql = "DELETE FROM product WHERE ProductID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, DeletedByID);//assign id values to sql place holder in stmt

        int row = stmt.executeUpdate();

        stmt.close();
        connection.close();

        return row;
    }

    // MONTHLY STOCK 
    public static ArrayList<Product> getMonthlyStock(int month, int year) throws SQLException {

        ArrayList<Product> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        //filter prod based on month and year
        String sql = "SELECT * FROM product WHERE MONTH(ProductDate)=? AND YEAR(ProductDate)=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, month);
        stmt.setInt(2, year);

        ResultSet rs = stmt.executeQuery();

        //retrieves
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

    
    ///reduce quantity product after sales
    public static int reduceStock(String productID, int qty) throws SQLException {

        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        //reduce quantity sold based on id prod
        String sql = "UPDATE product SET ProductQuantity = ProductQuantity - ? WHERE ProductID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, qty);
        stmt.setString(2, productID);

        int row = stmt.executeUpdate();//update

        //close db
        stmt.close();
        conn.close();

        return row;
    }
    
    
}
