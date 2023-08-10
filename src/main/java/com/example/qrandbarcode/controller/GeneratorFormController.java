package com.example.qrandbarcode.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneratorFormController implements Initializable {

    public AnchorPane qrImgPane;
    @FXML
    private AnchorPane pane;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnGenerateCode;

    @FXML
    private ImageView imgQrCode;

    @FXML
    private RadioButton radioBtnBarcode;

    @FXML
    private RadioButton radioBtnQr;

    @FXML
    private TextArea txtEnter;

    private String codeType;

    private Image generatedCodeImage;

    Image fxImageBarCode;

    ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        pane.getChildren().clear();
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"))));
            stage.setTitle("Generate Code");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnGenerateCodeOnAction(ActionEvent event) {
        if (radioBtnQr.isSelected()) {
            codeType = "QR Code";
            generateQRCode();
        } else if (radioBtnBarcode.isSelected()) {
            codeType = "Bar Code";
            generateBarCode();
        }
        updatePreview();
        btnGenerateCode.setDisable(false);
    }

    private void updatePreview() {
        if (generatedCodeImage != null) {
            imgQrCode.setImage(generatedCodeImage);
        }
    }

    private void generateBarCode() {
        String textToEncode = txtEnter.getText(); // Get the text from the TextArea

        try {
            Barcode barcode = BarcodeFactory.createCode128(textToEncode);
            barcode.setDrawingText(false);
            BufferedImage image = BarcodeImageHandler.getImage(barcode);

            // Resize the image
            int targetWidth = 180; // Set your desired width
            int targetHeight = 70; // Set your desired height

            // Convert the BufferedImage to JavaFX Image
            fxImageBarCode = SwingFXUtils.toFXImage(image, null);

            // Create an ImageView to scale the image
            imageView = new ImageView(fxImageBarCode);
            imageView.setFitWidth(targetWidth);
            imageView.setFitHeight(targetHeight);

            imgQrCode.setVisible(false);
            imageView.setVisible(true);

            // Set the alignment of the ImageView within the AnchorPane
            AnchorPane.setTopAnchor(imageView, (qrImgPane.getHeight() - imageView.getFitHeight()) / 2);
            AnchorPane.setLeftAnchor(imageView, (qrImgPane.getWidth() - imageView.getFitWidth()) / 2);

            qrImgPane.getChildren().add(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during barcode generation
        }
    }

    private void generateQRCode() {

        String textToEncode = txtEnter.getText(); // Get the text from the TextArea
        int width = 200;
        int height = 200;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(textToEncode, BarcodeFormat.QR_CODE, width, height);
            generatedCodeImage = SwingFXUtils.toFXImage(MatrixToImageWriter.toBufferedImage(bitMatrix), null);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (radioBtnQr.isSelected()) {
            saveQRCode();
        } else if (radioBtnBarcode.isSelected()) {
            saveBarCode();
        }
    }

    private void saveBarCode() {
        if (fxImageBarCode != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save " + codeType);
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
            File fileToSave = fileChooser.showSaveDialog(null);

            if (fileToSave != null) {
                try {
                    // Ensure the selected file has .png extension
                    if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
                    }

                    ImageIO.write(SwingFXUtils.fromFXImage(fxImageBarCode, null), "png", fileToSave);

                    // Show a success message
                    System.out.println(codeType + " saved successfully!");
                    txtEnter.setText(null);
                    imgQrCode.setVisible(true);
                    imageView.setVisible(false);

                    radioBtnBarcode.setSelected(false);
                    radioBtnQr.setSelected(false);

                    imgQrCode.setImage(new Image("images/qr1.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                    // Show an error message
                    System.out.println("Error saving " + codeType);
                }
            }
        }
    }

    private void saveQRCode() {
        if (generatedCodeImage != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save " + codeType);
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
            File fileToSave = fileChooser.showSaveDialog(null);

            if (fileToSave != null) {
                try {
                    // Ensure the selected file has .png extension
                    if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
                    }

                    ImageIO.write(SwingFXUtils.fromFXImage(generatedCodeImage, null), "png", fileToSave);

                    // Show a success message
                    System.out.println(codeType + " saved successfully!");
                    txtEnter.setText(null);
                    imgQrCode.setVisible(true);

                    radioBtnBarcode.setSelected(false);
                    radioBtnQr.setSelected(false);

                    imgQrCode.setImage(new Image("images/qr1.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                    // Show an error message
                    System.out.println("Error saving " + codeType);
                }
            }
        }
    }


}
