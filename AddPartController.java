package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**@author Jessica Thomas
  C 482 Software I Inventory project
  */

/**Class AddPartController.java controls Add Part UI
  */

public class AddPartController{

    Stage stage;
    Parent scene;

    @FXML
    private ToggleGroup addPartTG;

    @FXML
    private Label partTypeLabel;

    @FXML
    private RadioButton inHouseRB;

    @FXML
    private RadioButton outSourcedRB;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private TextField addInStockTxt;

    @FXML
    private TextField addPartPriceTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartTypeTxt;


    /**Method onActionInHouseRB sets partTypeLabel to Machine ID
      if the inHouseRB is selected.
      */
    @FXML
    public void onActionInHouseRB() {
        if (inHouseRB.isSelected()){
            partTypeLabel.setText("Machine ID");
        }
    }

    /**Method onActionOutsourcedRB sets partTypeLabel to Company Name
      if the outSourcedRB is selected.
      */
    @FXML
    public void onActionOutsourcedRB() {
        if (outSourcedRB.isSelected()){
            partTypeLabel.setText("Company Name");
        }

    }

    /**Method onActionBackToInventory takes user back to Main Screen UI.
      @param event is the button press event initiating the screen change
      @throws IOException if load error encountered
      */
    @FXML
    public void onActionBackToInventoryMgmt(ActionEvent event) throws IOException{

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }

    /**Method onActionSaveAddPart takes the information entered in the text fields
      and creates the part; then adds the part to to the parts list.
      @param event is the button event initiated to save the part information
      @throws IOException if NumberFormatException encountered with text field entries
      */
    @FXML
    public void onActionSaveAddPart(ActionEvent event) throws IOException {
        try {
            String name = addPartNameTxt.getText();
            int stock = Integer.parseInt(addInStockTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());
            double price = Double.parseDouble(addPartPriceTxt.getText());

            if (max < min){
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setContentText("Maximum to have on hand should be more than minimum.");
                alert1.showAndWait();
            }else if (stock < min || stock > max) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setContentText("Amount in stock must be more than minimum and less than maximum.");
                alert2.showAndWait();
            }

            if (inHouseRB.isSelected()) {

                int machineId = Integer.parseInt(addPartTypeTxt.getText());
                Part newPart = new InHouse(name, price, stock, max, min, machineId);
                Inventory.addPart(newPart);

            } else if (outSourcedRB.isSelected()) {

                String companyName = addPartTypeTxt.getText();
                Part newPart = new OutSourced(name, price, stock, max, min, companyName);
                Inventory.addPart(newPart);
            }
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Entry Error");
            alert.setContentText("Please enter a string for name, an integer for stock, max and min, and a double for" +
                    "price.");
            alert.showAndWait();
        }
    }
}


