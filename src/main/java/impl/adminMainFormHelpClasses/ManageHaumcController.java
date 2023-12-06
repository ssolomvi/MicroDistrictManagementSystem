package impl.adminMainFormHelpClasses;

import impl.helpClasses.AlertMessage;
import impl.helpClasses.Database;
import impl.helpClasses.ListData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static impl.helpClasses.ListData.tempHaumcID;

public class ManageHaumcController implements Initializable {

    @FXML
    private TextField manageHaumcTextFieldName;

    @FXML
    private TextField manageHaumcTextFieldContactPhone;

    @FXML
    private ComboBox<String> manageHaumcComboBoxTextType;

    @FXML
    private TextField manageHaumcTextFieldDescription;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private final AlertMessage alert = new AlertMessage();

    public int generateHaumcID() {
        String selectData = "select max(\"haumcid\") from \"haumc\"";
        connect = Database.connectDB();

        try {
            statement = connect.createStatement();
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
        if (manageHaumcTextFieldName.getText().isEmpty()
        || manageHaumcTextFieldContactPhone.getText().isEmpty()
        || manageHaumcTextFieldDescription.getText().isEmpty()
        || manageHaumcComboBoxTextType.getSelectionModel().getSelectedItem() == null) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            connect = Database.connectDB();

            String checkData = "select \"Name\" from \"haumc\" " +
                    "where \"Name\" = '" + manageHaumcTextFieldName.getText() + "'";
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert.errorMessage("Housing&utility management company with Name = " + manageHaumcTextFieldName.getText()
                            + " already exists");
                } else {
                    String insertData = "insert into \"haumc\""
                            + "(\"haumcid\", \"Name\", \"ContactPhone\", \"Description\", \"Type\")"
                            + "values(?, ?, ?, ?, ?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setInt(1, generateHaumcID());
                    prepare.setString(2, manageHaumcTextFieldName.getText());
                    prepare.setString(3, manageHaumcTextFieldContactPhone.getText());
                    prepare.setString(4, manageHaumcTextFieldDescription.getText());
                    prepare.setString(5, manageHaumcComboBoxTextType.getSelectionModel().getSelectedItem());
                    prepare.executeUpdate();
                    alert.successMessage("Added Successfully!");

                    clearFields();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateBtn() {
        if (manageHaumcTextFieldName.getText().isEmpty()
                || manageHaumcTextFieldContactPhone.getText().isEmpty()
                || manageHaumcTextFieldDescription.getText().isEmpty()
                || manageHaumcComboBoxTextType.getSelectionModel().getSelectedItem() == null) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            if (alert.confirmMessage("Are you sure you want to update?")) {
                String updateData = "update \"haumc\" set "
                        + "\"Name\" = '" + manageHaumcTextFieldName.getText() + "', "
                        + "\"ContactPhone\" = '" + manageHaumcTextFieldContactPhone.getText() + "', "
                        + "\"Description\" = '" + manageHaumcTextFieldDescription.getText() + "', "
                        + "\"Type\" = '" + manageHaumcComboBoxTextType.getSelectionModel().getSelectedItem() + "' "
                        + "where \"Haumcid\" = " + tempHaumcID;
                try {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();
                    alert.successMessage("Updated Successfully!");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                alert.errorMessage("Cancelled");
            }
        }
    }

    public void setFields() {
        try {
            if (tempHaumcID != null) {
                String sql = "SELECT * FROM \"haumc\" WHERE \"haumcid\" = '"
                        + tempHaumcID + "'";
                connect = Database.connectDB();
                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();

                if (result.next()) {
                    manageHaumcTextFieldName.setText(result.getString("Name"));
                    manageHaumcTextFieldContactPhone.setText(result.getString("ContactPhone"));
                    manageHaumcTextFieldDescription.setText(result.getString("Description"));
                    manageHaumcComboBoxTextType.setValue(result.getString("Type"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearFields() {
        manageHaumcTextFieldName.clear();
        manageHaumcTextFieldContactPhone.clear();
        manageHaumcComboBoxTextType.getSelectionModel().clearSelection();
        manageHaumcTextFieldDescription.clear();
    }

    public void haumcListData() {
        List<String> list = new ArrayList<>(Arrays.asList(ListData.haumcType));
        ObservableList<String> listData = FXCollections.observableArrayList(list);
        manageHaumcComboBoxTextType.setItems(listData);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        haumcListData();
        setFields();
    }
}
