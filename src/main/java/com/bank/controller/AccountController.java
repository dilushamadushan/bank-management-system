package com.bank.controller;

import com.bank.models.Transfer;
import com.bank.services.TransferManger;
import javafx.event.ActionEvent;
import com.bank.models.Account;
import com.bank.models.RegularUser;
import com.bank.services.AccountManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
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
    private Label ammount_de;

    @FXML
    private Label from_Acc;

    @FXML
    private Label remark_de;

    @FXML
    private Label to_Acc;

    private TransferManger trm;
    private Account acc;
    private AccountManager accManager;
    private Transfer tr;
    private RegularUser regularUser;

    @FXML
    void Pay_Conform(ActionEvent event){
        mainDash.setVisible(false);
        fundTransferPane.setVisible(false);
        fundTransferPane1.setVisible(true);

        String fromAcc = accFrom.getText();
        String to_Acc = AccTo.getText();
        double transfer_ammount = Double.parseDouble(TransAmount.getText());
        String trans_remark = transRemark.getText();
        String date = currentDate();

        tr = new Transfer(fromAcc,to_Acc,transfer_ammount,trans_remark,date);
        setTr(tr);

        this.from_Acc.setText(tr.get_fromAcc());
        this.to_Acc.setText(tr.get_toAcc());
        ammount_de.setText(String.valueOf(tr.get_amount()));
        remark_de.setText(tr.get_remarker());
    }

    @FXML
    void paymentCansal(ActionEvent event) {

    }

    @FXML
    void paymentConform(ActionEvent event) {
        trm = new  TransferManger();
        try {
            trm.transfer(tr,regularUser.getUsername());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void transferBtn(ActionEvent event) {
        mainDash.setVisible(false);
        transferDash.setVisible(true);
        accManager = new AccountManager();
        acc = accManager.getAccountByUsername(regularUser.getUsername());
        accFrom.setText(acc.getAccountNumber());
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

    public void setRegularUser(RegularUser regularUser) {
        this.regularUser = regularUser;
    }

    public void setTr(Transfer tr) {
        this.tr = tr;
    }

    public void setTagTitle() {
        tagTitle.setText("Hello " + regularUser.getUsername() + ", Wellcome back.");
        setDate.setText(currentDate());
    }

    public void setAccountDetails() {
        accManager = new AccountManager();
        acc = accManager.getAccountByUsername(regularUser.getUsername());

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

    public String currentDate(){
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm, d MMMM yyyy", Locale.ENGLISH);
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }

}
