package lab04.lab04;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.w3c.dom.ls.LSOutput;
import org.apache.commons.validator.EmailValidator;


public class HelloController {

    @FXML TextField username;
    @FXML TextField password;
    @FXML TextField fullname;
    @FXML TextField email;
    @FXML TextField phonenumber;
    @FXML DatePicker dateofbirth;
    @FXML Label emailCheck;

    @FXML
    protected void onRegisterButtonClick() {

        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(email.getText())) {
            emailCheck.setText("");
            System.out.println("Username: " + username.getText());
            System.out.println("Password: " + password.getText());
            System.out.println("Full Name: " + fullname.getText());
            System.out.println("E-Mail: " + email.getText());
            System.out.println("Phone Number: " + phonenumber.getText());
            System.out.println("Date Of Birth: " + dateofbirth.getValue());
            System.out.println("\n\n");
        } else {
            emailCheck.setText("Invalid E-Mail Address");
        }



    }

}