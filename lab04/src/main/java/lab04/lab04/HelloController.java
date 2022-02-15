package lab04.lab04;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.w3c.dom.ls.LSOutput;

public class HelloController {

    @FXML TextField username;
    @FXML TextField password;
    @FXML TextField fullname;
    @FXML TextField email;
    @FXML TextField phonenumber;
    @FXML DatePicker dateofbirth;

    @FXML
    protected void onHelloButtonClick() {
        System.out.println("Username: " + username.getText());
        System.out.println("Password: " + password.getText());
        System.out.println("Full Name: " + fullname.getText());
        System.out.println("E-Mail: " + email.getText());
        System.out.println("Phone Number: " + phonenumber.getText());
        System.out.println("Date Of Birth: " + dateofbirth.getValue());

    }

}