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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.Event;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private AuthService auth;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        auth = new AuthService();
    }

    @FXML
    private TextField accNo;

    @FXML
    private TextField contactNo;

    @FXML
    private TextField nicNumber;

    @FXML
    private PasswordField password;

    @FXML
    private TextField user_name;

    @FXML
    private Label errorMsg;

    @FXML
    void loginCon(MouseEvent event) throws IOException {
       log(event);
    }

    @FXML
    void register(ActionEvent event) {

        BUtils sh = new BUtils();

        String _username = user_name.getText();
        String _password = password.getText();
        String _nicNumber = nicNumber.getText();
        String _accNo = accNo.getText();
        String _contactNo = contactNo.getText();

        if (_username.isEmpty() || _password.isEmpty() || _nicNumber.isEmpty() || _accNo.isEmpty() || _contactNo.isEmpty()) {
            errorMsg.setText("Please fill all the fields.");
            return;
        }

        if (!_username.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            sh.showMessage("Error", "Invalid email address.");
            return;
        }

        if (!_password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            sh.showMessage("Error", "Password must be at least 8 characters and include uppercase, lowercase, number, and special character.");
            return;
        }

        if (!_nicNumber.matches("[0-9]{9}[VvXx]")) {
            sh.showMessage("Error", "Invalid NIC number format.");
            return;
        }

        if (!_contactNo.matches("\\d{10}")) {
            sh.showMessage("Error", "Invalid contact number. Must be 10 digits.");
            return;
        }

        User user = new RegularUser(_username, _nicNumber, _accNo, _password, _contactNo);

        try {
            if(auth.register(user)){
                sh.showMessage("Registered","Registered  successfully!");
                log(event);
            }else {
                sh.showMessage("Registered","Registered  Failed!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void log(Event event) throws IOException {
        ViewModel.getInstance().getViewsFactory().showWindow("views/login.fxml","Bank Management System");
        Stage stage = new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}

