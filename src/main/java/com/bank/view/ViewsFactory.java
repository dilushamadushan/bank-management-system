package com.bank.view;

import com.bank.Main;
import com.bank.controller.TransactionController;
import com.bank.models.Transfer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewsFactory {

    public void showWindow(String window, String title){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(window));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static AnchorPane loadTransactionCard(Transfer transfer) {
        try {
            FXMLLoader loder = new FXMLLoader(ViewsFactory.class.getResource("/com/bank/views/transaction.fxml"));
            AnchorPane card = loder.load();

            TransactionController controller = loder.getController();
            controller.setTransactionData(transfer);
            return  card;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
