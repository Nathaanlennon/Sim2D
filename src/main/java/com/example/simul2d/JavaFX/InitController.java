package com.example.simul2d.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for the initialization screen of the simulation.
 * <p>
 * This screen lets the user specify the grid dimensions (width and height)
 * and choose whether the world should be toric (wrap-around). After clicking
 * the validate button, the provided values are checked and then passed to
 * a {@link ValidationHandler} callback.
 * </p>
 */
public class InitController {

    /** Text field for the grid width. */
    @FXML private TextField widthField;

    /** Text field for the grid height. */
    @FXML private TextField heightField;

    /** Check box to enable toric (wrap-around) mode. */
    @FXML private CheckBox toricBox;

    /** Button that triggers validation of the inputs. */
    @FXML private Button validateButton;

    /** Label used to display error messages when input is invalid. */
    @FXML private Label errorLabel;

    /**
     * Callback interface that receives the validated simulation parameters.
     * <p>
     * Implementations of this interface are notified when the user has
     * provided valid dimensions and toric selection.
     * </p>
     */
    public interface ValidationHandler {
        /**
         * Called when the input has been successfully validated.
         *
         * @param width  the chosen grid width (positive integer)
         * @param height the chosen grid height (positive integer)
         * @param toric  whether toric mode is enabled
         */
        void onValidated(int width, int height, boolean toric);
    }

    /** The registered handler for successful validation. */
    private ValidationHandler handler;

    /**
     * Sets the validation handler that will be called when the user
     * confirms valid simulation parameters.
     *
     * @param h the handler to invoke on successful validation
     */
    public void setOnValidated(ValidationHandler h) {
        this.handler = h;
    }

    /**
     * Initializes the controller after the FXML has been loaded.
     * <p>
     * Default values are set (30×30, non-toric), the error label is cleared,
     * and the validate button is wired to the {@link #doValidate()} method.
     * </p>
     */
    @FXML
    public void initialize() {
        widthField.setText("30");
        heightField.setText("30");
        toricBox.setSelected(false);
        errorLabel.setText("");
        validateButton.setOnAction(evt -> doValidate());
    }

    /**
     * Validates the user input from the text fields and check box.
     * <p>
     * If the width and height are positive integers, the {@link ValidationHandler}
     * is notified. Otherwise, an error message is displayed in the error label.
     * </p>
     */
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