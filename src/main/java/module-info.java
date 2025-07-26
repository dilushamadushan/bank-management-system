module com.bank {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bank to javafx.fxml;
    opens com.bank.views to javafx.fxml;

    exports com.bank;
    exports com.bank.controller;

    opens com.bank.controller to javafx.fxml;
}