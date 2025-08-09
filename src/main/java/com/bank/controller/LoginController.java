package com.bank.controller;

import com.bank.Main;
import com.bank.models.RegularUser;
import com.bank.models.User;
import com.bank.models.ViewModel;
import com.bank.services.AuthService;
import com.bank.utils.BUtils;
import com.bank.view.ViewsFactory;
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
        String username = userName.getText();
        String pass = password.getText();

        try {
            if(authService.login(username, pass)){
                bu.showMessage("Login Successful!", "You have successfully logged in.");

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bank/views/account.fxml"));
                    Parent root = loader.load();

                    AccountController ac = loader.getController();
                    ac.setRegularUser(new RegularUser(username));
                    ac.setTagTitle();
                    ac.setAccountDetails();

                    Scene scene = new Scene(root,800,600);
                    stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    stage = (Stage) ((Node)  event.getSource()).getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }else {
                bu.showMessage("Login Failed!", "You have not logged in.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void register(MouseEvent event) {
        ViewModel.getInstance().getViewsFactory().showWindow("/com/bank/views/register.fxml","Bank Management System");
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }


}
