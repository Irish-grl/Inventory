package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
  @author Jessica Thomas
  C482 Software I project
  */

/**
  Class Main.java adds parts and products to the UI for testing
  */
public class Main extends Application {

    /**
      Method starts the Inventory Management program.
      @param primaryStage is the stage for the start scene
      */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        primaryStage.setScene(new Scene(root, 1250, 600));
        primaryStage.show();
    }

    /**
      Main Method creates Outsourced objects and InHouse objects and adds them to Inventory part list;
      Also creates three products and adds them to Inventory product list.
      @param args is the arguments to launch
      */
    public static void main(String[] args) {

        Part part = new OutSourced("gasket", 1.99, 5, 0,  10,"Lowes");

        Part part1 = new OutSourced( "nut", 0.05, 20, 10,  25,"Lowes");

        Part part2 = new OutSourced( "bolt", 0.07, 20, 10, 25,"Lowes");

        Part part3 = new OutSourced( "sheet metal", 11.00, 3, 0,  10,"Acme");

        Part part4 = new OutSourced( "rebar", 2.99, 10, 5,  20,"Acme");

        Part part5 = new OutSourced( "glue", 1.99, 5, 0,  20,"Acme");


        Part part6 = new InHouse( "housing", 104.15, 5, 2,  10,001);

        Part part7 = new InHouse( "body piece", 107.99, 2, 1, 5,002);

        Part part8 = new InHouse( "flange", 10.99, 2, 0, 5,003);

        Part part9 = new InHouse( "clamp", 2.99, 5, 2,  10,004);

        Part part10 = new InHouse( "housing base", 52.98, 2, 1, 10,005);

        Inventory.addPart(part);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        Inventory.addPart(part7);
        Inventory.addPart(part8);
        Inventory.addPart(part9);
        Inventory.addPart(part10);

        Product product = new Product("body assembly", 3000.00, 3, 1, 15);

        Product product1 = new Product("housing assembly", 2000.00, 3, 1, 15);

        Product product2 = new Product("assembly kit", 199.99, 10, 5, 30);


        product.addAssociatedPart(part3);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part10);

        Inventory.addProduct(product);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        launch(args);
    }
}
