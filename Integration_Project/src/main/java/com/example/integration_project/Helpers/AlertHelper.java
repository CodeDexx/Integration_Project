package com.example.integration_project.Helpers;

import com.example.integration_project.Model.Ticket;
import javafx.scene.control.Alert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlertHelper {

    // Error Alert
    public static void showErrorAlert(String pInfo, String pHeader, String pMessage) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(pInfo);
        alert.setHeaderText(pHeader);
        alert.setContentText(pMessage);
        alert.showAndWait();
    }

    // Information Alert
    public static void showInfoAlert(String pInfo, String pHeader, String pMessage) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(pInfo);
        alert.setHeaderText(pHeader);
        alert.setContentText(pMessage);
        alert.showAndWait();
    }
    // Ticket ticket = new Ticket("T102AQ5");
    // AlertHelper.showTicketConfirmationAlert(ticket);


    // Ticket Confirmation
    public static void showTicketConfirmationAlert(Ticket pTicket) {
        Alert alert =  new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ticket Confirmation");
        alert.setHeaderText("Your Ticket");
        alert.setContentText(pTicket.toString());
        alert.showAndWait();
    }

    public static class EmailValidator {

        // Regex pattern for a generally accepted email format
        // This pattern checks for: start, name part, @ symbol, domain part, dot, and a 2-7 letter TLD.
        private static final Pattern EMAIL_PATTERN = Pattern.compile(
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        );

        /**
         * Validates if the given string matches the standard email format.
         * @param email The string to validate.
         * @return true if the email is in a valid format, false otherwise.
         */
        public static boolean isValidFormat(String email) {
            if (email == null) {
                return false;
            }
            //
            Matcher matcher = EMAIL_PATTERN.matcher(email);
            return matcher.matches();
        }
    }

}
