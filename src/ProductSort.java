/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Comparator;

/**
 *
 * @author HP
 */
public class ProductSort implements Comparator<Product> {

    private String sortType;

    public ProductSort(String sortType) {
        this.sortType = sortType;
    }

    public int compare(Product p1, Product p2) {

        if (sortType.equals("Product Name ")) {
            return p1.getProductName().compareTo(p2.getProductName());
        }

        // most quantitiy
        if (sortType.equals("Most Stock Quantity")) {
            return p2.getProductQuantity() - p1.getProductQuantity();
        }

        if (sortType.equals("Least Stock Quantity")) {
            return p1.getProductQuantity() - p2.getProductQuantity();
        }
        return 0;
    }
}
