package com.example.qrandbarcode.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ScanFormController {

    public AnchorPane pane;
    @FXML
    private Button btnExit;

    @FXML
    private Button btnScanCode;

    @FXML
    void btnExitOnAction(ActionEvent event) {
        pane.getChildren().clear();
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"))));
            stage.setTitle("Scan Code");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnScanCodeOnAction(ActionEvent event) {

    }

}
