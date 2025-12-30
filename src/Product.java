/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
import java.sql.Date;

public class Product {
    private String productID, productName;
    private double productPrice;
    private int productQuantity;
    private Date productDate;

    public Product(String productID, String productName, double productPrice, int productQuantity, Date productDate) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productDate = productDate;
    }

    // getter
    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public int getProductDate() {
        return productQuantity;
    }

    // setter
    // not do setter for update since in this system date cant be update
    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductName(String ProductName) {
        this.productName = ProductName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return String.format(
                "\n| %s | %s | RM %.2f | %d | %s |\n",
                productID,
                productName,
                productPrice,
                productQuantity,
                productDate);
    }

}
