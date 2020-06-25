package client;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegController {
    public TextField loginField;
    public PasswordField passwordField;
    public TextField nickField;
    MsgController msgController;


    public void clickOk() {
        msgController.tryRegistration(loginField.getText().trim(), passwordField.getText().trim(), nickField.getText().trim());
    }
}
