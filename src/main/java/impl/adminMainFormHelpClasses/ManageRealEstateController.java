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

import static impl.helpClasses.ListData.tempRealEstateID;

public class ManageRealEstateController implements Initializable {
    @FXML
    private TextField manageRealEstateTextFieldBuildingID;

    @FXML
    private TextField manageRealEstateTextFieldSize;

    @FXML
    private ComboBox<String> manageRealEstateComboBoxText;

    @FXML
    private TextField manageRealEstateTextFieldRoomCount;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private final AlertMessage alert = new AlertMessage();

    public int generateRealEstateID() {
        String selectData = "select max(\"ResidentialPropertyID\") from \"ResidentialProperty\"";
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
        if (manageRealEstateTextFieldBuildingID.getText().isEmpty()
                || manageRealEstateComboBoxText.getSelectionModel().getSelectedItem() == null
                || manageRealEstateTextFieldSize.getText().isEmpty()
                || manageRealEstateTextFieldRoomCount.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            connect = Database.connectDB();

            String insertData = "insert into \"ResidentialProperty\""
                    + "(\"ResidentialPropertyID\", \"BuildingID\", \"Type\", \"Size\", \"RoomCount\")"
                    + "values(?, ?, ?, ?, ?)";

            try {
                prepare = connect.prepareStatement(insertData);
                prepare.setInt(1, generateRealEstateID());
                prepare.setInt(2, Integer.parseInt(manageRealEstateTextFieldBuildingID.getText()));
                prepare.setString(3, manageRealEstateComboBoxText.getSelectionModel().getSelectedItem());
                prepare.setInt(4, Integer.parseInt(manageRealEstateTextFieldSize.getText()));
                prepare.setInt(5, Integer.parseInt(manageRealEstateTextFieldRoomCount.getText()));
                prepare.executeUpdate();
                alert.successMessage("Added Successfully!");

                clearFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateBtn() {
        if (manageRealEstateTextFieldBuildingID.getText().isEmpty()
                || manageRealEstateComboBoxText.getSelectionModel().getSelectedItem() == null
                || manageRealEstateTextFieldSize.getText().isEmpty()
                || manageRealEstateTextFieldRoomCount.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            if (alert.confirmMessage("Are you sure you want to update?")) {
                String updateData = "update \"ResidentialProperty\" set "
                        + "\"BuildingID\" = '" + manageRealEstateTextFieldBuildingID.getText() + "', "
                        + "\"Type\" = '" + manageRealEstateComboBoxText.getSelectionModel().getSelectedItem() + "', "
                        + "\"Size\" = '" + manageRealEstateTextFieldSize.getText() + "', "
                        + "\"RoomCount\" = '" + manageRealEstateTextFieldRoomCount.getText() + "' "
                        + "where \"ResidentialPropertyID\" = " + tempRealEstateID;
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
            if (tempRealEstateID != null) {
                String sql = "SELECT * FROM \"ResidentialProperty\" WHERE \"ResidentialPropertyID\" = '"
                        + tempRealEstateID + "'";
                connect = Database.connectDB();
                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();

                if (result.next()) {
                    manageRealEstateTextFieldBuildingID.setText(String.valueOf(result.getInt("BuildingID")));
                    manageRealEstateComboBoxText.setValue(result.getString("Type"));
                    manageRealEstateTextFieldSize.setText(String.valueOf(result.getInt("Size")));
                    manageRealEstateTextFieldRoomCount.setText(String.valueOf(result.getInt("RoomCount")));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearFields() {
        manageRealEstateTextFieldBuildingID.clear();
        manageRealEstateComboBoxText.getSelectionModel().clearSelection();
        manageRealEstateTextFieldSize.clear();
        manageRealEstateTextFieldRoomCount.clear();
    }

    public void realEstateTypeList() {
        List<String> list = new ArrayList<>(Arrays.asList(ListData.realEstateType));
        ObservableList<String> listData = FXCollections.observableArrayList(list);
        manageRealEstateComboBoxText.setItems(listData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        realEstateTypeList();
        setFields();
    }
}
