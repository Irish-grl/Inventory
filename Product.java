package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
  @author Jessica Thomas
  C 482 Software I Inventory project
 */

/**
 Class Product.java creates product objects, sets/gets id, name, price, in stock, min, and max;
 Also adds/removes/returns associated parts to lists attached to product.
  */

public class Product {

    private static int newId;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**Constructor creates Product.
      @param name is name of product
      @param price is price of product
      @param stock is amount of product in stock
      @param min is minimum amount of product to have on hand
      @param max is maximum amount of product to have on hand
      */
    public Product(String name, double price, int stock, int min, int max){

        newId ++;
        id = newId;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
    }

    /**Method sets name to product name.
     @param name is product name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**Method sets price as product price.
     @param price is product price
     */
    public void setPrice(double price) {

        this.price = price;
    }

    /**Method sets stock number as product stock amount.
     @param stock is product amount in stock
     */
    public void setStock(int stock) {

        this.stock = stock;
    }

    /**Method sets product stock on hand minimum.
     @param min is stock on hand minimum
     */
    public void setMin(int min) {

        this.min = min;
    }

    /**Method sets product stock on hand maximum.
      @param max is the stock on hand maximum
     */
    public void setMax(int max) {

        this.max = max;
    }

    /**Method gets product id number.
      @return id
      */
    public int getId() {

        return id;
    }

    /**Method gets product name.
     @return name
     */
    public String getName() {

        return name;
    }

    /**Method gets product price.
      @return price
      */
    public double getPrice() {

        return price;
    }

    /**Method gets amount of product in stock.
      @return stock
     */
    public int getStock() {

        return stock;
    }

    /**Method gets minimum amount of product to have on hand.
     @return min
      */
    public int getMin() {

        return min;
    }

    /**Method gets maximum amount of product to have on hand.
      @return max
      */
    public int getMax() {

        return max;
    }

    /**Method adds a part to a product.
      @param part is the part used to make the product
     */
    public void addAssociatedPart(Part part){

        associatedParts.add(part);
    }

    /**Method checks that part is a part of product and then removes it from the product.
      @param part is part to remove from the product  */
    public void deleteAssociatedPart(Part part){

        associatedParts.remove(part);
    }

    /**Method gets all parts associated with a particular product.
      @return associatedParts list
     */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }
}
