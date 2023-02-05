package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

/**@author Jessica Thomas
  C 482 Software I Inventory project
 */

/**Class ModifyProductController.java controls Modify Product UI
  */


public class ModifyProductController  implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField modifyProductIdTxt;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TextField modifyProductInStockTxt;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField searchPartIdOrNameTxt;

    @FXML
    private TableView<Part> partToAddToProductTableView;

    @FXML
    private TableColumn<Part, Integer> addPartIdCol;

    @FXML
    private TableColumn<Part, String> addPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addPartInStockCol;

    @FXML
    private TableColumn<Part, Double> addPartCostCol;

    @FXML
    private TableView<Part> partAddedToProductTableView;

    @FXML
    private TableColumn<Part, Integer> addedPartIdCol;

    @FXML
    private TableColumn<Part, String> addedPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addedPartInStockCol;

    @FXML
    private TableColumn<Part, Double> addedPartPriceCol;


    /**Method sendProduct brings the selected product information into the
      Modify Product UI screen to be modified.
      @param product is the object that this information is added to
      */
    public void sendProduct(Product product){

        modifyProductIdTxt.setText(String.valueOf(product.getId()));
        modifyProductNameTxt.setText(product.getName());
        modifyProductInStockTxt.setText(String.valueOf(product.getStock()));
        modifyProductPriceTxt.setText(String.valueOf(product.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(product.getMax()));
        modifyProductMinTxt.setText(String.valueOf(product.getMin()));

        partAddedToProductTableView.setItems(product.getAllAssociatedParts());

        if (product.getAllAssociatedParts().size() > 0){
            for (int i = 0; i < product.getAllAssociatedParts().size(); i++) {
                Part part = product.getAllAssociatedParts().get(i);
                Inventory.setFilteredParts(part);
            }
        }

        addedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**Method onActionAddPartToProduct takes the part selected by
      user and adds it to the addPartsToProduct tableview and filtered parts list.
      */
    @FXML
    public void onActionAddPartToProduct() {

        Part part = partToAddToProductTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            return;
        }else{
            Inventory.setFilteredParts(part);
        }

        partAddedToProductTableView.setItems(Inventory.getFilteredParts());

        addedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**Method rtnToInventory takes user back to Main Screen UI.
      @param event is the button press event initiating the screen change
      @throws IOException if load error is encountered
      */
    @FXML
    public void rtnToInventory(ActionEvent event) throws IOException{

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }

    /**Method onActionRmvAsscPart removes the part selected by user
      from the partAddedToProduct table and removes the part from
      the filtered parts list.
      */
    @FXML
    public void onActionRmvAsscPart() {

            Part part = partAddedToProductTableView.getSelectionModel().getSelectedItem();

            if (part == null) {
                return;
            }

            Inventory.getFilteredParts().remove(part);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Part has been removed from Product.");
            alert.showAndWait();

            partAddedToProductTableView.setItems(Inventory.getFilteredParts());

            addedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            addedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            addedPartInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            addedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**Method onActionSaveModifyProduct saves the product modifications that user made
      and adds the parts from the partAddedToProduct table and adds them
      to the associated parts list.
      @param event is the button event that initiates the save
      @throws IOException if NumberFormatException encountered with text field entries
      */
    @FXML
   public void onActionSaveModifyProduct(ActionEvent event) throws IOException{
        try {
            int id = Integer.parseInt(modifyProductIdTxt.getText());
            String name = modifyProductNameTxt.getText();
            double price = Double.parseDouble(modifyProductPriceTxt.getText());
            int stock = Integer.parseInt(modifyProductInStockTxt.getText());
            int max = Integer.parseInt(modifyProductMaxTxt.getText());
            int min = Integer.parseInt(modifyProductMinTxt.getText());

            if (max < min){
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setContentText("Maximum to have on hand should be more than minimum.");
                alert1.showAndWait();
            }else if (stock < min || stock > max) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setContentText("Amount in stock must be more than minimum and less than maximum.");
                alert2.showAndWait();
            }

            Product newProduct = new Product(name, price, stock, min, max);
            if (Inventory.getFilteredParts().size() > 0){

                for (int i = 0; i < Inventory.getFilteredParts().size(); i++) {
                    Part part = Inventory.getFilteredParts().get(i);
                    newProduct.addAssociatedPart(part);
                }
            }
            Inventory.updateProduct(id, newProduct);
            Inventory.getFilteredParts().clear();

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }catch (NumberFormatException e){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Entry Error");
            alert.setContentText("Please enter a string for name, an integer for inventory, maximum and minimum, and " +
                    "a double for price.");
            alert.showAndWait();
        }
    }

    /**Method onActionSearchPartsToAdd hunts for the part id
      that user enters in the search text field and adds the part to the
      partToAddToProduct table.
      */
    @FXML
    public void onActionSearchPartsToAdd() {

        String part = searchPartIdOrNameTxt.getText();
        Inventory.setFilteredParts(partToAddToProductTableView.getSelectionModel().getSelectedItem());

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

        partToAddToProductTableView.setItems(searchParts);

        searchPartIdOrNameTxt.clear();

        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**Method creates a Part list and adds all parts from Inventory to the list.
      @param id is the id number to search for in the list of parts
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

    /**Method iterates across all parts in one list, then adds the filtered parts to second list.
      @param partName is the part name to search for in the list of all parts. If found, adds the part to filtered
      list of parts.
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

    /**Method initialize sets up the top part table and populates it with all
      parts from Inventory.java.
      @param url is url to bring in from Inventory.java
      @param resourceBundle is the resourceBundle to bring in from Inventory
      */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){

        partToAddToProductTableView.setItems(Inventory.getAllParts());

        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
