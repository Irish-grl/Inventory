package Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**@author Jessica Thomas
  C 482 Software I Inventory project
  */


/**
  Class Inventory.java provides methods that add/remove parts and products to lists, filters lists for searches, updates
  parts and products in the list, and looks up parts and products in the lists.
  */
public class Inventory{


    /**@param allParts is list of all parts in Inventory
      @param allProducts is list of all products in Inventory
      @param filteredParts is list of filtered parts
      @param filteredProducts is list of filtered products
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();


    /**Method adds a part to the filteredParts list.
      @param filteredPart is the part to add to filteredParts list
      */
    public static void setFilteredParts(Part filteredPart) {

        filteredParts.add(filteredPart);
    }

    /**Method clears filteredParts list.
      */
    public static void deleteAllFilteredParts(){

        filteredParts.clear();
    }


    /**Method adds a product to the filteredProducts list.
      @param filteredProduct is the product to add to the list
      */
    public static void setFilteredProducts(Product filteredProduct) {

        filteredProducts.add(filteredProduct);
    }

    /**Method clears the filteredProducts list.
      */
    public static void clearFilteredProducts(){

        filteredProducts.clear();
    }

    /**Method adds parts to part list.
      @param newPart is new part to add to allParts list
      */
    public static void addPart(Part newPart){

        allParts.add(newPart);
    }

    /**Method adds product to product list.
      @param newProduct is product to add to allProducts list
      */
    public static void addProduct(Product newProduct){

        allProducts.add(newProduct);
    }

    /**Method updates part list.
      @param id is index where part is added in list
      @param selectedPart is new part to add to list
      <p><b>
      The 'updatePart' method (and the 'updateProduct' as well), both initially were adding the updated part into the list,
      but not at the desired index, it was added as a new part. I was using the id and selectedPart to set the location
      for the new part/product. This was due to the methods not getting the index and then replacing the current
      part/product with the new updated part/product. Once I added the line 'int index = allPartsList.indexOf(part
      );' and used the index to set the selectedPart location in the list, the error was corrected.
      </b></p>
     */
    public static void updatePart(int id, Part selectedPart) {
        ObservableList<Part> allPartsList = Inventory.getAllParts();

        for (int i = 0; i < allPartsList.size(); i++) {
            Part part = allPartsList.get(i);
            int index = allPartsList.indexOf(part);

            if (part.getId() == id)
                allPartsList.set(index, selectedPart);
        }
    }

    /**Method updates product list.
      @param id is index where product will be added
      @param selectedProduct is new product to add to list
     */
    public static void updateProduct(int id, Product selectedProduct){
        ObservableList<Product> allProductsList = Inventory.getAllProducts();

        for (int i = 0; i < allProductsList.size(); i++){
            Product product = allProductsList.get(i);
            int index = allProductsList.indexOf(product);

            if (product.getId() == id)
                allProductsList.set(index, selectedProduct);
        }
    }

    /**Method removes part from list if it exists in the list.
      @param id is the id to search for
      @param part is part to be deleted from Part list
      @return part removed from allParts or false
      */
    public static boolean deletePart(int id, Part part){

        int index = -1;
        for (Part newPart : allParts)
            index ++;
        if (part.getId() == id){
            return allParts.remove(part);
        }
        return false;
    }

    /**Method removes product from list if it exists in the list.
      @param id is the id to search for
      @param product is product to be deleted from Product list
      @return product removed from allProducts or false
      */
    public static boolean deleteProduct(int id, Product product){

        int index = -1;
        for (Product newProduct : allProducts)
            index ++;
        if (product.getId() == id){
            return allProducts.remove(product);
        }
        return false;
    }

    /**Method gets all parts and prints out the full list
     to the monitor.
     @return allParts list
     */
    public static ObservableList<Part> getAllParts(){

        return allParts;
    }

    /**Method gets all products and prints out the full list
      to the monitor.
      @return allProducts list
     */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

    /**
      Method returns filteredParts observable list.
      @return filteredParts list
      */
    public static ObservableList<Part> getFilteredParts() {

        return filteredParts;
    }

    /**
      Method returns filteredProducts observable list.
      @return filteredProducts list
      */
    public static ObservableList<Product> getFilteredProducts() {

        return filteredProducts;
    }

}

