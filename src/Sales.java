import java.sql.Timestamp;

public class Sales {

    private String invoiceNo;
    private String productID;
    private int quantity;
    private double unitPrice;
    private double subTotal;
    private Timestamp saleDateTime;

    public Sales(String invoiceNo, String productID, int quantity,
            double unitPrice, double subTotal, Timestamp saleDateTime) {
        this.invoiceNo = invoiceNo;
        this.productID = productID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
        this.saleDateTime = saleDateTime;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public String getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public Timestamp getSaleDateTime() {
        return saleDateTime;
    }

    // Receipt item line
    @Override
    public String toString() {
        return String.format(
                "%-12s %15d  RM %6.2f  RM %7.2f\n",
                productID,
                quantity,
                unitPrice,
                subTotal);
    }
}
