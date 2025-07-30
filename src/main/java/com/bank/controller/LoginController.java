package com.bank.controller;

import com.bank.services.AuthService;
import com.bank.utils.BUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private AuthService authService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authService = new AuthService();
    }

    private Stage stage;

    @FXML
    private TextField password;

    @FXML
    private TextField userName;

    @FXML
    void loginCon(ActionEvent event) {
        BUtils bu =  new BUtils();
        try {
            if(authService.login(userName.getText(),password.getText())){
                bu.showMessage("Login Successful!", "You have successfully logged in.");
            }else {
                bu.showMessage("Login Failed!", "You have not logged in.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void register(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bank/views/register.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
