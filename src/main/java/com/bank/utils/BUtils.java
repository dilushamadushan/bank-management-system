package com.bank.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class BUtils {
    public void showMessage(String title,String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        DialogPane dialogPane = alert.getDialogPane();
        alert.showAndWait();
    }
}
