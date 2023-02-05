package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;

/**@author Jessica Thomas
  C 482 Software I Inventory project
  */

/**Class ModifyPartController.java controls Modify Part UI
  */


public class ModifyPartController {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inHouseRB;

    @FXML
    private Label partTypeLabel;

    @FXML
    private TextField modifyPartIdTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private TextField modifyPartInStockTxt;

    @FXML
    private TextField modifyPartPriceTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartTypeTxt;

    @FXML
    private RadioButton outSourcedRB;

    @FXML
    private TextField modifyPartMinTxt;



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

    /**Method sendPart takes the part info from the selected part in the part table
      and adds them to the Modify Part UI for modification.
      @param part is the part object bringing in values
      */
    public void sendPart(Part part){

        modifyPartIdTxt.setText(String.valueOf(part.getId()));
        modifyPartNameTxt.setText(part.getName());
        modifyPartInStockTxt.setText(String.valueOf(part.getStock()));
        modifyPartPriceTxt.setText(String.valueOf(part.getPrice()));
        modifyPartMaxTxt.setText(String.valueOf(part.getMax()));
        modifyPartMinTxt.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse){
            partTypeLabel.setText("Machine ID");
            modifyPartTypeTxt.setText(String.valueOf(((InHouse) part).getMachineId()));

        }else if (part instanceof OutSourced){
            partTypeLabel.setText("Company Name");
            modifyPartTypeTxt.setText(((OutSourced) part).getCompanyName());
        }
    }


    /**Method onActionBackToInventory takes user back to Main Screen UI.
      @param event is the button press event initiating the screen change
      @throws IOException if load error occurs
      */
    @FXML
    public void onActionBackToInventory(ActionEvent event) throws IOException {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }

    /**Method onActionSaveModifiedPart saves the modified part information
      and adds part to part table in Main Screen.
      @param event is the button press event initiating the save
      @throws IOException if NumberFormatException encountered with text field entries

      */
    @FXML
    public void onActionSaveModifiedPart(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(modifyPartIdTxt.getText());
            String name = modifyPartNameTxt.getText();
            int stock = Integer.parseInt(modifyPartInStockTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());
            double price = Double.parseDouble(modifyPartPriceTxt.getText());

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
                partTypeLabel.setText("Machine Id");
                int machineId = Integer.parseInt(modifyPartTypeTxt.getText());
                Part newPart = new InHouse(name, price, stock, max, min, machineId);
                Inventory.updatePart(id, newPart);

            } else if (outSourcedRB.isSelected()) {
                partTypeLabel.setText("Company Name");
                String companyName = modifyPartTypeTxt.getText();
                Part newPart = new OutSourced(name, price, stock, max, min, companyName);
                Inventory.updatePart(id, newPart);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Entry Error");
            alert.setContentText("Please enter a string for name, an integer for inventory, maximum and minimum, and " +
                    "a double for price.");
            alert.showAndWait();
        }
    }
}
