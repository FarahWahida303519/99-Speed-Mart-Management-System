
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
public class SalesReport {
    private String invoiceNo;
    private Date saleDate;
    private double totalAmount;

    public SalesReport(String invoiceNo, Date saleDate, double totalAmount) {
        this.invoiceNo = invoiceNo;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // Optional: simple display
    @Override
    public String toString() {
        return String.format(
                "%-10s %-12s RM %8.2f",
                invoiceNo,
                saleDate,
                totalAmount);
    }
}
