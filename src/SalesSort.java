/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
import java.util.Comparator;
import java.util.Date;

public class SalesSort implements Comparator<SalesReport> {

    private String sortType;

    public SalesSort(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public int compare(SalesReport s1, SalesReport s2) {
        // Sort by Invoice Number (A → Z)
        if (sortType.equals("Invoice No")) {
            return s1.getInvoiceNo().compareTo(s2.getInvoiceNo());
        }

        // Sort by Date (Latest first)
        if (sortType.equals("Latest Date")) {
            return s2.getSaleDate().compareTo(s1.getSaleDate());
        }

        // Sort by Date (Oldest first)
        if (sortType.equals("Oldest Date")) {
            return s1.getSaleDate().compareTo(s2.getSaleDate());
        }

        // Sort by Total Amount (Highest first)
        if (sortType.equals("Highest Amount")) {
            return Double.compare(s2.getTotalAmount(), s1.getTotalAmount());
        }

        // Sort by Total Amount (Lowest first)
        if (sortType.equals("Lowest Amount")) {
            return Double.compare(s1.getTotalAmount(), s2.getTotalAmount());
        }

        return 0;
    }
}
