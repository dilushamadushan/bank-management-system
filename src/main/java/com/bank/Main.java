package com.bank;

import com.bank.models.ViewModel;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        ViewModel.getInstance().getViewsFactory().showWindow("views/login.fxml","Bank Management System");
    }

    public static void main(String[] args) {
        launch();
    }
}