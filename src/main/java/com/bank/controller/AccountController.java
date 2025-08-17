package com.bank.controller;

import com.bank.models.Transfer;
import com.bank.services.TransactionManager;
import com.bank.services.TransferManger;
import com.bank.utils.BUtils;
import com.bank.view.ViewsFactory;
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
import java.util.List;
import java.util.Locale;

public class AccountController{

    @FXML
    private TextField AccTo;

    @FXML
    private TextArea transRemark;

    @FXML
    private Button transaction;

    @FXML
    private AnchorPane transactionHistory;

    @FXML
    private AnchorPane transactionPane;

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
    private BUtils sh = new BUtils();

    @FXML
    void Pay_Conform(ActionEvent event){
        String fromAcc = accFrom.getText();
        String toAcc = AccTo.getText();
        String trans_remark = transRemark.getText();
        String date = currentDate();

        double transfer_ammount = 0;
        try {
            transfer_ammount = Double.parseDouble(TransAmount.getText());
        } catch (NumberFormatException e) {
            sh.showMessage("Error", "Please enter a valid amount.");
            return;
        }

        if(fromAcc.isEmpty() && toAcc.isEmpty() && transfer_ammount <= 0){
            sh.showMessage("Error", "Please enter all details correctly.");
            return;
        }

        tr = new Transfer(fromAcc,toAcc,transfer_ammount,trans_remark,date);
        setTr(tr);

        this.from_Acc.setText(tr.get_fromAcc());
        this.to_Acc.setText(tr.get_toAcc());
        ammount_de.setText(String.valueOf(tr.get_amount()));
        remark_de.setText(tr.get_remarker());

        mainDash.setVisible(false);
        fundTransferPane.setVisible(false);
        fundTransferPane1.setVisible(true);
    }

    @FXML
    void paymentCansal(ActionEvent event) {
        mainDash.setVisible(false);
        transferDash.setVisible(true);
        fundTransferPane.setVisible(true);
        fundTransferPane1.setVisible(false);
    }

    @FXML
    void paymentConform(ActionEvent event) {
        trm = new  TransferManger();
        try {
            trm.transfer(tr,regularUser.getUsername());
            sh.showMessage("Successfully","Successfully transfer successfully");
            mainDash.setVisible(true);
            transferDash.setVisible(false);
            fundTransferPane.setVisible(false);
            fundTransferPane1.setVisible(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void transferBtn(ActionEvent event) {
        mainDash.setVisible(false);
        transferDash.setVisible(true);
        fundTransferPane.setVisible(true);
        fundTransferPane1.setVisible(false);
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
            transactionPane.setVisible(false);
            fundTransferPane.setVisible(false);
            fundTransferPane1.setVisible(false);
        } else if(event.getSource() == profile) {
            mainDash.setVisible(false);
            transactionPane.setVisible(false);
            transferDash.setVisible(false);
            fundTransferPane.setVisible(false);
            fundTransferPane1.setVisible(false);
        } else if (event.getSource() == transaction) {
            mainDash.setVisible(false);
            transactionPane.setVisible(true);
            loadTransaction();
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

        String accNum = acc.getAccountNumber();
        String maskedAcc = "**** **** **** " + accNum.substring(Math.max(0, accNum.length() - 4));

        if("Savings".equalsIgnoreCase(acc.getType())){
            s_accNo.setText(maskedAcc);
            s_balance.setText(String.valueOf(acc.getBalance()) + " $");
            c_accNo.setText("**** **** **** ****");
            c_balance.setText("0000.00 $");
        }
        else if("Current".equalsIgnoreCase(acc.getType())){
            c_accNo.setText(maskedAcc);
            c_balance.setText(String.valueOf(acc.getBalance()) + " $");
            s_accNo.setText("**** **** **** ****");
            s_balance.setText("0000.00 $");
        }
    }

    public void loadTransaction(){
        TransactionManager tm = new TransactionManager();
        accManager = new AccountManager();
        acc = accManager.getAccountByUsername(regularUser.getUsername());

        List<Transfer> list = tm.getTransactions(acc.getAccountNumber());
        transactionHistory.getChildren().clear();
        double yOffset = 0;
        for (Transfer trans : list) {
            AnchorPane card = ViewsFactory.loadTransactionCard(trans);
            if(card != null){
                card.setLayoutY(yOffset);
                yOffset += 65;
                transactionHistory.getChildren().add(card);
            }
        }
    }

    public String currentDate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm, d MMMM yyyy", Locale.ENGLISH);
        return now.format(formatter);
    }

}
