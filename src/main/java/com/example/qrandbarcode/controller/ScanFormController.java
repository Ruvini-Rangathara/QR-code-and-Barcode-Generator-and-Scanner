package com.example.qrandbarcode.controller;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScanFormController implements Initializable {

    public Label lblDecode;
    @FXML
    private Button btnExit;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnScanCode;

    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView imgCode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnScanCode.setDisable(true);
    }

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
    void btnOkOnAction(ActionEvent event) {
        imgCode.setImage(new Image("images/qr1.png"));
        lblDecode.setText("The decoding process involves using specialized algorithms " +
                "to interpret the pattern of lines or squares and translate it back " +
                "into human-readable data. This data can then be used for various purposes, " +
                "such as looking up product details, accessing websites, " +
                "or processing information in applications.");

        btnScanCode.setDisable(true);
    }


    @FXML
    void btnOpenCodeOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));

        File selectedFile = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());

                imgCode.setImage(image);
                imgCode.setFitWidth(200);
                imgCode.setFitHeight(170);

                lblDecode.setText("");
                btnScanCode.setDisable(false); // Enable the decoding button
            } catch (Exception e) {
                e.printStackTrace();
                lblDecode.setText("Error loading image.");
            }
        }
    }


    @FXML
    void btnScanCodeOnAction(ActionEvent event) {
        if (imgCode.getImage() != null) {
            try {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imgCode.getImage(), null);
                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));

                Result result = new MultiFormatReader().decode(binaryBitmap);
                lblDecode.setText("Decoded data: " + result.getText());
            } catch (NotFoundException e) {
                lblDecode.setText("QR code not found.");
            }
        }
    }

}
