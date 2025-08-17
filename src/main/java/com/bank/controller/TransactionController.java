package com.bank.controller;

import com.bank.models.Transfer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class TransactionController {

    @FXML
    private Label accNo;

    @FXML
    private AnchorPane cartTransaction;

    @FXML
    private Label found;

    @FXML
    private Label transDate;

    @FXML
    private Label transferAccount;

    public void setTransactionData(Transfer transfer) {
        transDate.setText(transfer.getDate());
        accNo.setText(transfer.get_toAcc());
        if(transfer.get_amount() > 0){
            found.setText("+ LKR " + transfer.get_amount());
            found.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            found.setText("- LKR " + transfer.get_amount());
            found.setTextFill(javafx.scene.paint.Color.RED);
        }
    }
}
