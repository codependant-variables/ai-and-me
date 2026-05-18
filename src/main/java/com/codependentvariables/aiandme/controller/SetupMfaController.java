package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.modules.Router;
import com.codependentvariables.aiandme.modules.Toast;
import com.codependentvariables.aiandme.modules.ToastMessageType;
import com.codependentvariables.aiandme.services.AuthService;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.state.AppState;
import com.codependentvariables.aiandme.validation.ValidationEntry;
import com.codependentvariables.aiandme.validation.validators.DynamicValidator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.util.converter.DefaultStringConverter;


public class SetupMfaController {
    private final AuthService authService = AuthService.getInstance();
    private final UserService userService = UserService.getInstance();

    private User currentUser;
    private String currentSecret;

    private static final int SIDE = 200;

    @FXML
    public ImageView qrCodeImageView;

    @FXML
    public TextField totpField;

    private final TextFormatter<String> totpTextFormatter = new TextFormatter<>(
            new DefaultStringConverter(),
            "",
            change -> {
                String newText = change.getControlNewText(); // Proposed new text
                if (newText.length() > AuthService.TOTP_SIZE) {
                    return null;
                }

                for (char c : newText.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        return null; // Reject change
                    }
                }
                return change;
            }
    );

    private final ValidationEntry<String> totpValidator = new ValidationEntry<>(
            () -> this.totpField.getText(),
            "One-time password",
            new DynamicValidator<>((value, display) -> value.length() == AuthService.TOTP_SIZE ? null : String.format("%s must be %d characters.", display, AuthService.TOTP_SIZE))
    );

    @FXML
    public void initialize() {
        currentUser = AppState.getInstance().getCurrentUser();
        if (currentUser == null) {
            navigateBack();
            return;
        }

        totpField.setTextFormatter(totpTextFormatter);

        refreshQrCode();
    }

    @FXML
    public void refreshQrCode() {
        currentSecret = authService.createTotpSecret();
        String totpUri = authService.createTotpUri(currentSecret, currentUser.getEmail());

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix matrix = qrCodeWriter.encode(totpUri, BarcodeFormat.QR_CODE, SIDE, SIDE);

            WritableImage image = new WritableImage(matrix.getWidth(), matrix.getHeight());
            PixelWriter writer = image.getPixelWriter();

            boolean isDarkMode = AppState.getInstance().getIsDarkMode();
            Color main = isDarkMode ? Color.WHITE : Color.BLACK;
            Color secondary = isDarkMode ? Color.BLACK : Color.WHITE;

            for (int y = 0; y < matrix.getHeight(); y++) {
                for (int x = 0; x < matrix.getWidth(); x++) {
                    writer.setColor(x, y, matrix.get(x, y) ? main : secondary);
                }
            }

            qrCodeImageView.setImage(image);
        } catch (Exception ex) {
            throw new RuntimeException("Failed generating a QR code.", ex);
        }
    }

    public void submit() {
        if (!totpValidator.validate(x -> Toast.addMessage("Invalid", x, ToastMessageType.WARNING))) {
            return;
        }

        if (!authService.compareTotp(currentSecret, totpField.getText())) {
            Toast.addMessage("Invalid", "Incorrect code.", ToastMessageType.ERROR);
            return;
        }

        currentUser.setTotpSecret(currentSecret);
        userService.updateCurrentUser();
        Toast.addMessage("Success", "MFA setup.", ToastMessageType.INFORMATION);
        navigateBack();
    }

    @FXML
    public void navigateBack() {
        Router.navigateBack();
    }
}
