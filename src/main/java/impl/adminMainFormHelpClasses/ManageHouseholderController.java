package impl.adminMainFormHelpClasses;

import impl.helpClasses.AlertMessage;
import impl.helpClasses.ListData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import impl.helpClasses.Database;

import static impl.helpClasses.ListData.tempHouseholderID;

public class ManageHouseholderController implements Initializable {
    @FXML
    private TextField manageHouseholderTextFieldFirstName;

    @FXML
    private TextField manageHouseholderTextFieldLastName;

    @FXML
    private DatePicker manageHouseholderDatePickerDateOfBirth;

    @FXML
    private ComboBox<String> manageHouseholderComboBoxGender;

    @FXML
    private TextField manageHouseholderTextFieldContactNumber;

    @FXML
    private TextField manageHouseholderTextFieldEmail;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private final AlertMessage alert = new AlertMessage();

    public static java.sql.Date getDateFromDatePicker(DatePicker datePicker) {
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        java.util.Date date = java.util.Date.from(instant);
        return new java.sql.Date(date.getTime());
    }

    public int generateOwnerID() {
        String selectData = "select max(\"OwnerID\") from \"OwnersOfResidentialProperty\"";
        connect = Database.connectDB();

        try {
            Statement statement = connect.createStatement();
            result = statement.executeQuery(selectData);

            if (!result.next()) {
                return 1;
            }
            return result.getInt(1) + 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBtn() {
        if (manageHouseholderTextFieldFirstName.getText().isEmpty()
                || manageHouseholderTextFieldLastName.getText().isEmpty()
                || manageHouseholderDatePickerDateOfBirth.getValue() == null
                || manageHouseholderComboBoxGender.getSelectionModel().getSelectedItem() == null
                || manageHouseholderTextFieldContactNumber.getText().isEmpty()
                || manageHouseholderTextFieldEmail.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            connect = Database.connectDB();

            String insertData = "insert into \"OwnersOfResidentialProperty\""
                    + "(\"OwnerID\", \"FirstName\", \"LastName\", \"DateOfBirth\", \"Gender\", \"ContactNumber\", \"Email\")"
                    + "values(?, ?, ?, ?, ?, ?, ?)";

            try {
                prepare = connect.prepareStatement(insertData);
                prepare.setInt(1, generateOwnerID());
                prepare.setString(2, manageHouseholderTextFieldFirstName.getText());
                prepare.setString(3, manageHouseholderTextFieldLastName.getText());
                prepare.setDate(4, getDateFromDatePicker(manageHouseholderDatePickerDateOfBirth));
                prepare.setString(5, manageHouseholderComboBoxGender.getSelectionModel().getSelectedItem());
                prepare.setString(6, manageHouseholderTextFieldContactNumber.getText());
                prepare.setString(7, manageHouseholderTextFieldEmail.getText());
                prepare.executeUpdate();
                alert.successMessage("Added Successfully!");

                clearFields();
//                impl.AdminMainFormController.householderDisplayData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateBtn() {
        if (manageHouseholderTextFieldFirstName.getText().isEmpty()
                || manageHouseholderTextFieldLastName.getText().isEmpty()
                || manageHouseholderDatePickerDateOfBirth.getValue() == null
                || manageHouseholderComboBoxGender.getSelectionModel().getSelectedItem() == null
                || manageHouseholderTextFieldContactNumber.getText().isEmpty()
                || manageHouseholderTextFieldEmail.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            if (alert.confirmMessage("Are you sure you want to update?")) {
                String updateData = "update \"OwnersOfResidentialProperty\" set "
                        + "\"FirstName\" = '" + manageHouseholderTextFieldFirstName.getText() + "', "
                        + "\"LastName\" = '" + manageHouseholderTextFieldLastName.getText() + "', "
                        + "\"DateOfBirth\" = '" + getDateFromDatePicker(manageHouseholderDatePickerDateOfBirth) + "', "
                        + "\"Gender\" = '" + manageHouseholderComboBoxGender.getSelectionModel().getSelectedItem() + "', "
                        + "\"ContactNumber\" = '" + manageHouseholderTextFieldContactNumber.getText() + "', "
                        + "\"Email\" = '" + manageHouseholderTextFieldEmail.getText() + "' "
                        + "where \"OwnerID\" = " + tempHouseholderID;
                try {
                    connect = Database.connectDB();
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();
                    alert.successMessage("Updated Successfully!");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
//                impl.AdminMainFormController.householderDisplayData();
            } else {
                alert.errorMessage("Cancelled");
            }
        }
    }

    public void setFields() {
        try {
            if (tempHouseholderID != null) {
                String sql = "SELECT * FROM \"OwnersOfResidentialProperty\" WHERE \"OwnerID\" = '"
                        + tempHouseholderID + "'";
                connect = Database.connectDB();
                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();

                if (result.next()) {
                    manageHouseholderTextFieldFirstName.setText(result.getString("FirstName"));
                    manageHouseholderTextFieldLastName.setText(result.getString("LastName"));
                    manageHouseholderDatePickerDateOfBirth.setValue(result.getDate("DateOfBirth").toLocalDate());
                    manageHouseholderComboBoxGender.getSelectionModel().select(result.getString("Gender"));
                    manageHouseholderTextFieldContactNumber.setText(result.getString("ContactNumber"));
                    manageHouseholderTextFieldEmail.setText(result.getString("Email"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void clearFields() {
        manageHouseholderTextFieldFirstName.clear();
        manageHouseholderTextFieldLastName.clear();
        manageHouseholderDatePickerDateOfBirth.setValue(null);
        manageHouseholderComboBoxGender.getSelectionModel().clearSelection();
        manageHouseholderTextFieldContactNumber.clear();
        manageHouseholderTextFieldEmail.clear();
    }

    public void genderList() {
        List<String> listG = new ArrayList<>(Arrays.asList(ListData.gender));
        ObservableList<String> listData = FXCollections.observableArrayList(listG);
        manageHouseholderComboBoxGender.setItems(listData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderList();
        setFields();
    }
}
