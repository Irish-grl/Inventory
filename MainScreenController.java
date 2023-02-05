package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**@author Jessica Thomas
  C 482 Software I Inventory project
  */


/**Class MainScreenController.java controls Main Screen UI
  */

public class MainScreenController implements Initializable {
    Stage stage;
    Parent scene;


    @FXML
    private TextField searchForPartTxt;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInStockCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TextField searchForProductTxt;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInStockCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    /**Method onActionAddProduct takes user to Add Product UI.
      @param event is the button event initiating the screen change
      @throws IOException if load error occurs
      */
    @FXML
    public void onActionAddProduct(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Method onActionAddPart takes user to Add Part UI.
      @param event is the button event initiating the screen change
      @throws IOException if load error occurs
      */
    @FXML
    public void onActionAddPart(ActionEvent event) throws IOException{

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**Method onActionDeletePart removes part from the Part table.
      */
    @FXML
    public void onActionDeletePart(){

            Part part = partTableView.getSelectionModel().getSelectedItem();
            int id = part.getId();

            if (part == null) {
                return;
            }
            Inventory.deletePart(id, part);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Selected part has been deleted.");
            alert.showAndWait();
    }

    /**Method onActionDeleteProduct removes product from the product table.
      */
    @FXML
    public void onActionDeleteProduct(){

            Product product = productTableView.getSelectionModel().getSelectedItem();
            int id = product.getId();

            if (product == null) {
                return;
            }
            if (product.getAllAssociatedParts().size() > 0) {

                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setHeaderText("This product has parts associated. Remove the associated parts before deletion.");
                alert1.showAndWait();

            } else {

                Inventory.deleteProduct(id, product);
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setContentText("Selected product has been deleted.");
                alert1.showAndWait();
            }
    }

    /**Method onActionModifyPart takes user to Modify Part UI
      and sends the selected part information to the Modify Part UI screen.
      @param event is the button event initiating the screen change
      @throws IOException if load error occurs
     */
    @FXML
    public void onActionModifyPart(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyPart.fxml"));
        loader.load();

        ModifyPartController MPController = loader.getController();
        MPController.sendPart(partTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Method onActionModifyProduct takes user to Modify Product UI
      and sends the selected product information to the Modify Product UI screen.
      @param event is the button event initiating the screen change
      @throws IOException if load error occurs
      */
    @FXML
    public void onActionModifyProduct(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyProduct.fxml"));
        loader.load();

        ModifyProductController MPrController = loader.getController();
        MPrController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Method onActionModifyProduct takes user to Modify Product UI
      and sends the selected product information to the Modify Product UI screen.
      */
    @FXML
    public void onActionSearchParts() {

        String part = searchForPartTxt.getText();
        Inventory.setFilteredParts(partTableView.getSelectionModel().getSelectedItem());

        ObservableList<Part> searchParts = lookUpPartName(part);

        if (searchParts.size() == 0){
            try{
                int id = Integer.parseInt(part);
                Part tempPart = searchForPartId(id);

                if (tempPart != null){
                    searchParts.add(tempPart);
                }
            }catch (NumberFormatException e){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Entry Error");
                alert.setContentText("Id number must be an integer.");
                alert.showAndWait();
            }
        }

        partTableView.setItems(searchParts);

        searchForPartTxt.clear();

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**Method onActionSearchProducts takes information entered in search text field
      and modifies the Product table with the search results.
      */
    @FXML
    public void onActionSearchProducts() {

        String product = searchForProductTxt.getText();
        Inventory.setFilteredProducts(productTableView.getSelectionModel().getSelectedItem());

        ObservableList<Product> searchProducts = lookUpProductName(product);

        if (searchProducts.size() == 0){
            try {
                int id = Integer.parseInt(product);
                Product tempProduct = searchForProductId(id);

                if (tempProduct != null) {
                    searchProducts.add(tempProduct);
                }
            }catch (NumberFormatException e){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Entry Error");
                alert.setContentText("Id number must be an integer.");
                alert.showAndWait();
            }
        }

        productTableView.setItems(searchProducts);

        searchForProductTxt.clear();

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**Method searchForPartId hunts for the part id
      that user enters in the search text field.
      @param id is the id to search for
      @return part is the list returned.
      */
    private Part searchForPartId(int id){

        ObservableList<Part> allPartsList = Inventory.getAllParts();

        for (int i = 0; i < allPartsList.size(); i++) {
            Part part = allPartsList.get(i);

            if (part.getId() == id)
                return part;
        }
        return null;
    }

    /**Method searchForProductId hunts for the product id
      that user enters in the search text field.
      @param id is the id to search for
      @return product is the list returned.
      */
    private Product searchForProductId(int id){

        ObservableList<Product> allProductsList = Inventory.getAllProducts();

        for (int i = 0; i < allProductsList.size(); i++){
            Product product = allProductsList.get(i);

            if (product.getId() == id)
                return product;
        }
        return null;
    }

    /**Method looks for partName in all of the parts in allPartsList, if found, adds part to partialName.
      @param partName is the part name to look for in the allPartsList
      @return partialName is the list returned.
      */
    public ObservableList<Part> lookUpPartName(String partName) {
        ObservableList<Part> partialName = FXCollections.observableArrayList();
        ObservableList<Part> allPartsList = Inventory.getAllParts();

        for (Part tempPart : allPartsList){
            if (tempPart.getName().contains(partName))
                partialName.add(tempPart);
        }
        return partialName;
    }

    /**Method looks for productName in all of the parts in allProductsLIst, if found, adds product to partialName.
      @param productName is the product name to look for in the allProductsList
      @return partialName is the list returned.
      */
    public ObservableList<Product> lookUpProductName(String productName){
        ObservableList<Product> partialName = FXCollections.observableArrayList();
        ObservableList<Product> allProductsList = Inventory.getAllProducts();

        for (Product tempProduct : allProductsList){
            if (tempProduct.getName().contains(productName))
                partialName.add(tempProduct);
        }
        return partialName;
    }

    /**Method onActionExitInventoryMgmt exits the program.
      */
    @FXML
    public void onActionExitInventoryMgmt() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the program?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            System.exit(0);
        }
    }

    /**Method initialize sets up the Main Screen Parts and Products tables
      populating them with the information from the main.java class.
      @param url is the Inventory URL that brings in all of the products and parts
      @param resourceBundle is the resource bundle from Inventory.
     <p><b>The item I would add to this project to extend functionality, would be a third table to the right of the
      product table that pulls in all associated parts for a selected/highlighted product in the product table. This
      would show the user the associated parts without the user needing to go to the 'Modify Part' screen to see which
      parts are associated with the product in question.
      </b></p>
      */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){

        partTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}



