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

import static impl.helpClasses.ListData.tempParkingID;

public class ManageParkingController implements Initializable {

    @FXML
    private TextField manageParkingTextFieldBuildingID;

    @FXML
    private TextField manageParkingTextFieldParkingPlacesCount;

    @FXML
    private ComboBox<String> manageParkingComboBoxTextType;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private final AlertMessage alert = new AlertMessage();

    public int generateParkingID() {
        String selectData = "select max(\"ParkingID\") from \"Parking\"";
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
        if (manageParkingTextFieldBuildingID.getText().isEmpty()
                || manageParkingComboBoxTextType.getSelectionModel().getSelectedItem() == null
                || manageParkingTextFieldParkingPlacesCount.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            connect = Database.connectDB();

            String checkData = "select \"BuildingID\" from \"Buildings\" " +
                    "where \"BuildingID\" = '" + manageParkingTextFieldBuildingID.getText() + "'";
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (!result.next()) {
                    alert.errorMessage("Building with ID = " + manageParkingTextFieldBuildingID.getText()
                            + "does not exist");
                } else {
                    String insertData = "insert into \"Parking\""
                            + "(\"ParkingID\", \"BuildingID\", \"ParkingType\", \"ParkingPlacesCount\")"
                            + "values(?, ?, ?, ?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setInt(1, generateParkingID());
                    prepare.setInt(2, Integer.parseInt(manageParkingTextFieldBuildingID.getText()));
                    prepare.setString(3, manageParkingComboBoxTextType.getSelectionModel().getSelectedItem());
                    prepare.setInt(4, Integer.parseInt(manageParkingTextFieldParkingPlacesCount.getText()));
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
        if (manageParkingTextFieldBuildingID.getText().isEmpty()
                || manageParkingComboBoxTextType.getSelectionModel().getSelectedItem() == null
                || manageParkingTextFieldParkingPlacesCount.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            if (alert.confirmMessage("Are you sure you want to update?")) {
                String checkData = "select \"BuildingID\" from \"Buildings\" " +
                        "where \"BuildingID\" = '" + manageParkingTextFieldBuildingID.getText() + "'";
                try {
                    statement = connect.createStatement();
                    result = statement.executeQuery(checkData);

                    if (!result.next()) {
                        alert.errorMessage("Building with ID = " + manageParkingTextFieldBuildingID.getText()
                                + " does not exist");
                    } else {
                        String updateData = "update \"Parking\" set "
                                + "\"BuildingID\" = '" + manageParkingTextFieldBuildingID.getText() + "', "
                                + "\"ParkingType\" = '" + manageParkingComboBoxTextType.getSelectionModel().getSelectedItem() + "', "
                                + "\"ParkingPlacesCount\" = '" + manageParkingTextFieldParkingPlacesCount.getText() + "' "
                                + "where \"ParkingID\" = " + tempParkingID;
                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();
                        alert.successMessage("Updated Successfully!");
                    }
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
            if (tempParkingID != null) {
                String sql = "SELECT * FROM \"Parking\" WHERE \"ParkingID\" = '"
                        + tempParkingID + "'";
                connect = Database.connectDB();
                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();

                if (result.next()) {
                    manageParkingTextFieldBuildingID.setText(String.valueOf(result.getInt("BuildingID")));
                    manageParkingComboBoxTextType.setValue(result.getString("ParkingType"));
                    manageParkingTextFieldParkingPlacesCount.setText(String.valueOf(result.getInt("ParkingPlacesCount")));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearFields() {
        manageParkingTextFieldBuildingID.clear();
        manageParkingComboBoxTextType.getSelectionModel().clearSelection();
        manageParkingTextFieldParkingPlacesCount.clear();
    }

    public void parkingTypeList() {
        List<String> list = new ArrayList<>(Arrays.asList(ListData.parkingType));
        ObservableList<String> listData = FXCollections.observableArrayList(list);
        manageParkingComboBoxTextType.setItems(listData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parkingTypeList();
        setFields();
    }
}
