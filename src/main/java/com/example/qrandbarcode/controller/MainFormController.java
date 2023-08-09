package com.example.qrandbarcode.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    void btnGenerateCodeOnAction(ActionEvent event) {
        pane.getChildren().clear();
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/GeneratorForm.fxml"))));
            stage.setTitle("Generate Code");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnScanCodeOnAction(ActionEvent event) {
//        pane.getChildren().clear();
//        Stage stage = (Stage) pane.getScene().getWindow();
//        try {
//            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ScanForm.fxml"))));
//            stage.setTitle("Scan Code");
//            stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

}
