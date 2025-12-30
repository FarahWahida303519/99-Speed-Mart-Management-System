/*
*FARAH WAHIDA BINTI AHMAD FAIZU
*303519
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class ProductFileOperation {

    public static void saveRecord(Product prod) throws IOException {

        FileWriter fw = new FileWriter("StockList.txt", true);
        PrintWriter pw = new PrintWriter(fw);// Write
        pw.println(prod.getProductID());
        pw.println(prod.getProductName());
        pw.println(prod.getProductPrice());
        pw.println(prod.getProductQuantity());
        pw.println(prod.getProductDate());
        pw.println();

        fw.close();
        pw.close();

    }

    // read the record
    public static ArrayList<Product> readRecord() throws IOException {

        FileReader fw = new FileReader("StockList.txt");
        BufferedReader br = new BufferedReader(fw);

        String productID, productName;
        double productPrice;
        int productQuantity;
        Date productDate;
        Product prod;
        ArrayList<Product> prods = new ArrayList();
        String readData = br.readLine();

        while (readData != null) {
            if (readData.trim().isEmpty()) {
                readData = br.readLine();// move to next line
                continue;// to skip blank line
            }
            productID = readData;
            productName = br.readLine();
            productPrice = Double.parseDouble(br.readLine());
            productQuantity = Integer.parseInt(br.readLine());
            productDate = Date.valueOf(br.readLine());

            prod = new Product(productID, productName, productPrice, productQuantity, productDate);
            prods.add(prod);
            readData = br.readLine();

        }

        fw.close();
        br.close();
        return prods;
    }

    public static void updateRecord(ArrayList<Product> prod) throws IOException {

        FileWriter fw = new FileWriter("StockList.txt");
        PrintWriter pw = new PrintWriter(fw);

        for (Product update : prod) {
            pw.println(update.getProductID());
            pw.println(update.getProductName());
            pw.println(update.getProductPrice());
            pw.println(update.getProductQuantity());
            pw.println(update.getProductDate());

            pw.println("\n");
        }

        fw.close();
        ;
        pw.close();
    }

}
