package com.bank;

import com.bank.models.ViewModel;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewModel.getInstance().getViewsFactory().showWindow("views/login.fxml","Bank Management System");
    }

    public static void main(String[] args) {
        launch();
    }
}