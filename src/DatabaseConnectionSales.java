import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
public class DatabaseConnectionSales {

    private static final String URL = "jdbc:mysql://localhost:3306/99_speedmart_db";
    private static String USER = "root";
    private static String PASSWORD = "12345";

    // READ DATA
    public static ArrayList<Sales> readData() throws SQLException {

        // create list to store sales rec
        ArrayList<Sales> salesList = new ArrayList<>();
        // connect db
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        // sql stmt to retrive
        String sql = "SELECT * FROM sales";
        PreparedStatement stmt = connection.prepareStatement(sql);
        // excute query since use select
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            // retvs data each column
            String invoiceNo = rs.getString(1);
            String productID = rs.getString(2);
            int quantity = rs.getInt(3);
            double unitPrice = rs.getDouble(4);
            double subTotal = rs.getDouble(5);
            Timestamp saleDateTime = rs.getTimestamp(6); // DATETIME

            // create obj sales
            Sales sale = new Sales(invoiceNo, productID, quantity, unitPrice, subTotal, saleDateTime);
            salesList.add(sale);
        }

        rs.close();
        stmt.close();
        connection.close();

        return salesList;
    }

    // ADD SALES RECORD
    public static void addRecord(Sales sale) throws SQLException {

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "INSERT INTO sales (InvoiceNo, ProductID, Quantity, UnitPrice, SubTotal, SalesDateTime) VALUES (?, ?, ?, ?, ?, ?)";

        // replace place holder db with getter sales
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, sale.getInvoiceNo());
        stmt.setString(2, sale.getProductID());
        stmt.setInt(3, sale.getQuantity());
        stmt.setDouble(4, sale.getUnitPrice());
        stmt.setDouble(5, sale.getSubTotal());
        stmt.setTimestamp(6, sale.getSaleDateTime()); // ✅ DATETIME

        int row = stmt.executeUpdate();
        System.out.println("Inserted sales row: " + row);

        stmt.close();
        connection.close();
    }

    // MONTHLY SALES REPORT
    // with input parameterssssss
    public static ArrayList<SalesReport> getMonthlySales(int month, int year) throws SQLException {

        ArrayList<SalesReport> reportList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        // Use sql
        // Group rows that have the same invoice number and same date into ONE row.
        // retrives invoice,date only and with total sales based on the month and year
        //DATE(SalesDateTime) AS SaleDate = extract date only reomve time blh filter based on month year nnti
        
        String sql = "SELECT InvoiceNo, DATE(SalesDateTime) AS SaleDate, SUM(SubTotal) AS TotalAmount FROM sales "
                // filter data based on month and year!
                + "WHERE MONTH(SalesDateTime) = ? "
                + "AND YEAR(SalesDateTime) = ? "
                + "GROUP BY InvoiceNo, DATE(SalesDateTime)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        //replace placeholder in db
        stmt.setInt(1, month);
        stmt.setInt(2, year);

        ResultSet rs = stmt.executeQuery();//read/retrives data

        while (rs.next()) {

            String invoiceNo = rs.getString("InvoiceNo");
            //extrac date only
            java.sql.Date saleDate = rs.getDate("SaleDate"); // report = DATE only
            double totalAmount = rs.getDouble("TotalAmount");

            SalesReport report = new SalesReport(invoiceNo,saleDate,totalAmount);

            reportList.add(report);
        }

        rs.close();
        stmt.close();
        connection.close();

        return reportList;
    }

}
