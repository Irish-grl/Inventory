package Controller;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;
import javafx.event.ActionEvent;

/**@author Jessica Thomas
  C 482 Software I Inventory project
  */

/**Class AddProductController.fxml controls Add Product UI
  */


public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productInStockTxt;

    @FXML
    private TextField productPriceTxt;

    @FXML
    private TextField productMaxOnHandTxt;

    @FXML
    private TextField productMinOnHandTxt;

    @FXML
    private TextField searchPartByIdOrNameTxt;

    @FXML
    private TableView<Part> partToAddToProductTableView;

    @FXML
    private TableColumn<Part, Integer> addPartIdCol;

    @FXML
    private TableColumn<Part, String> addPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addPartInStockCol;

    @FXML
    private TableColumn<Part, Double> addPartPriceCol;

    @FXML
    private TableView<Part> partAddedToProductTableView;

    @FXML
    private TableColumn<Part, Integer> addedPartIdCol;

    @FXML
    private TableColumn<Part, String> addedPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addedPartInStockCol;

    @FXML
    private TableColumn<Part, Double> addedPartCostCol;


    /**Method onActionAddPartToProduct takes the part selected by
      user and adds it to the partAddedToProductTableView and filtered parts list
      */
    @FXML
    public void onActionAddPartToProduct(){

        Part part = (Part) partToAddToProductTableView.getSelectionModel().getSelectedItem();
        Inventory.setFilteredParts(part);

        partAddedToProductTableView.setItems(Inventory.getFilteredParts());

        addedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedPartInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**Method onActionBackToInventoryMgmt takes user back to Main Screen UI
      @param event is the button press event initiating the screen change
      @throws IOException if load error is encountered
     */
    @FXML
    public void onActionBackToInventoryMgmt(ActionEvent event) throws IOException{

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

    }

    /**Method onActionRmvAsscPart removes the part selected by user
      from the partAddedToProduct table and removes the part from
      the filtered parts list
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
        addedPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**Method onActionSaveAddProduct saves the product information user enters in text fields
      and adds the parts from the partAddedToProduct table; then adds them
      to the associated parts list.
      @param event is the button event that initiates the save
      @throws IOException if NumberFormatException encountered with text field entries
      */
    @FXML
    public void onActionSaveAddProduct(ActionEvent event) throws IOException{
        try {
            String name = productNameTxt.getText();
            double price = Double.parseDouble(productPriceTxt.getText());
            int stock = Integer.parseInt(productInStockTxt.getText());
            int max = Integer.parseInt(productMaxOnHandTxt.getText());
            int min = Integer.parseInt(productMinOnHandTxt.getText());

            if (max < min){
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setContentText("Maximum to have on hand should be more than minimum.");
                alert1.showAndWait();
            }else if (stock < min || stock > max) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setContentText("Amount in stock must be more than minimum and less than maximum.");
                alert2.showAndWait();
            }

            Product product = new Product(name, price, stock, max, min);

            for (int i = 0; i < Inventory.getFilteredParts().size(); i++) {
                product.addAssociatedPart(Inventory.getFilteredParts().get(i));
            }

            Inventory.addProduct(product);
            Inventory.deleteAllFilteredParts();

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
      partToAddToProduct table
      */
    @FXML
    public void onActionSearchPartsToAdd() {

        String part = searchPartByIdOrNameTxt.getText();
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

        searchPartByIdOrNameTxt.clear();

        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**Method creates a Part list and adds all parts from Inventory to the list
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
    public void initialize(URL url, ResourceBundle resourceBundle){

        partToAddToProductTableView.setItems(Inventory.getAllParts());

        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}


