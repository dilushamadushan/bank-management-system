module com.bank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.jfr;


    opens com.bank to javafx.fxml;
    opens com.bank.views to javafx.fxml;

    exports com.bank;
    exports com.bank.controller;
    exports com.bank.view;

    opens com.bank.controller to javafx.fxml;
    opens com.bank.view to javafx.fxml;
}