package com.bank.controller;

import com.bank.models.RegularUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AccountController {
    @FXML
    private Label tagTitle;

    @FXML
    private Label setDate;

    private RegularUser regularUser;

    public void setRegularUser(RegularUser regularUser) {
        this.regularUser = regularUser;
    }

    public void setTagTitle() {
        tagTitle.setText("Hello " + regularUser.getUsername() + ", Wellcome back.");
        currentDate();
    }

    public void currentDate(){
        LocalDateTime currentDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm, d MMMM yyyy", Locale.ENGLISH);
        String formattedDate = currentDate.format(formatter);
        setDate.setText(formattedDate);
    }

}
