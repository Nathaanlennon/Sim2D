package com.example.simul2d.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InitController {
    @FXML private TextField widthField;
    @FXML private TextField heightField;
    @FXML private CheckBox toricBox;
    @FXML private Button validateButton;
    @FXML private Label errorLabel;

    public interface ValidationHandler {
        void onValidated(int width, int height, boolean toric);
    }

    private ValidationHandler handler;

    public void setOnValidated(ValidationHandler h) {
        this.handler = h;
    }

    @FXML
    public void initialize() {
        widthField.setText("30");
        heightField.setText("30");
        toricBox.setSelected(false);
        errorLabel.setText("");
        validateButton.setOnAction(evt -> doValidate());
    }

    private void doValidate() {
        try {
            int w = Integer.parseInt(widthField.getText().trim());
            int h = Integer.parseInt(heightField.getText().trim());
            boolean toric = toricBox.isSelected();
            if (w <= 0 || h <= 0) {
                errorLabel.setText("Dimensions must be positive.");
                return;
            }
            if (handler != null) handler.onValidated(w, h, toric);
        } catch (NumberFormatException ex) {
            errorLabel.setText("Please enter valid integers.");
        }
    }
}
