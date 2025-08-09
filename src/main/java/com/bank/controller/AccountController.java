package com.bank.controller;

import javafx.event.ActionEvent;
import com.bank.models.Account;
import com.bank.models.RegularUser;
import com.bank.services.AccountManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AccountController{

    @FXML
    private TextField AccTo;

    @FXML
    private TextArea transRemark;

    @FXML
    private Button transaction;

    @FXML
    private AnchorPane transferDash;

    @FXML
    private TextField TransAmount;

    @FXML
    private TextField accFrom;

    @FXML
    private Button account;

    @FXML
    private Button dashbord;

    @FXML
    private AnchorPane fundTransferPane;

    @FXML
    private AnchorPane fundTransferPane1;

    @FXML
    private AnchorPane mainDash;

    @FXML
    private Button profile;

    @FXML
    private Label tagTitle;

    @FXML
    private Label setDate;

    @FXML
    private Label c_accNo;

    @FXML
    private Label c_balance;

    @FXML
    private Label s_accNo;

    @FXML
    private Label s_balance;

    @FXML
    void Pay_Conform(ActionEvent event){
        mainDash.setVisible(false);
        fundTransferPane.setVisible(false);
        fundTransferPane1.setVisible(true);
    }

    @FXML
    void transferBtn(ActionEvent event) {
        mainDash.setVisible(false);
        transferDash.setVisible(true);
    }

    @FXML
    void logoutBtn(ActionEvent event) {

    }

    @FXML
    void menuBtn(ActionEvent event) {
        if(event.getSource()==dashbord){
            mainDash.setVisible(true);
            transferDash.setVisible(false);
        } else if(event.getSource() == profile) {
            mainDash.setVisible(false);
            transferDash.setVisible(false);
        }
    }

    private RegularUser regularUser;

    public void setRegularUser(RegularUser regularUser) {
        this.regularUser = regularUser;
    }

    public void setTagTitle() {
        tagTitle.setText("Hello " + regularUser.getUsername() + ", Wellcome back.");
        currentDate();
    }

    public void setAccountDetails() {
        AccountManager accManager = new AccountManager();
        Account acc = accManager.getAccount(regularUser.getUsername());

        if("Savings".equalsIgnoreCase(acc.getType())){
            s_accNo.setText("**** **** **** "+acc.getAccountNumber());
            s_balance.setText(String.valueOf(acc.getBalance()) + " $");
            c_accNo.setText("**** **** **** ****");
            c_balance.setText("0000.00 $");
        }
        else if("Current".equalsIgnoreCase(acc.getType())){
            c_accNo.setText("**** **** **** "+acc.getAccountNumber());
            c_balance.setText(String.valueOf(acc.getBalance()) + " $");
            s_accNo.setText("**** **** **** ****");
            s_balance.setText("0000.00 $");
        }
    }

    public void currentDate(){
        LocalDateTime currentDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm, d MMMM yyyy", Locale.ENGLISH);
        String formattedDate = currentDate.format(formatter);
        setDate.setText(formattedDate);
    }

}
