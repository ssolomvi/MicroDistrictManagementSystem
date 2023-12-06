package impl.adminMainFormHelpClasses;

import impl.helpClasses.AlertMessage;
import impl.helpClasses.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static impl.helpClasses.ListData.tempBuildingsID;

public class ManageBuildingsController implements Initializable {

    @FXML
    private TextField manageBuildingsTextFieldPhysicalNumber;

    @FXML
    private TextField manageBuildingsTextFieldFloors;

    @FXML
    private TextField manageBuildingsTextFieldStreet;

    @FXML
    private TextField manageBuildingsTextFieldHAUMC;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private final AlertMessage alert = new AlertMessage();

    public int generateBuildingsID() {
        String selectData = "select max(\"BuildingID\") from \"Buildings\"";
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
        if (manageBuildingsTextFieldPhysicalNumber.getText().isEmpty()
                || manageBuildingsTextFieldHAUMC.getText().isEmpty()
                || manageBuildingsTextFieldFloors.getText().isEmpty()
                || manageBuildingsTextFieldStreet.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            connect = Database.connectDB();

            String checkData = "select \"BuildingPhysicalNumber\" from \"Buildings\" " +
                    "where \"BuildingPhysicalNumber\" = '" + manageBuildingsTextFieldPhysicalNumber.getText() + "'" +
                    " and \"StreetID\" = '" + manageBuildingsTextFieldStreet.getText() + "'";
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert.errorMessage("Buildings with physical number = " + manageBuildingsTextFieldPhysicalNumber.getText()
                            + " already exists on street with ID = " + manageBuildingsTextFieldStreet.getText());
                } else {
                    String insertData = "insert into \"Buildings\""
                            + "(\"BuildingID\", \"BuildingPhysicalNumber\", \"FloorsCount\", \"Haumcid\", \"StreetID\")"
                            + "values(?, ?, ?, ?, ?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setInt(1, generateBuildingsID());
                    prepare.setString(2, manageBuildingsTextFieldPhysicalNumber.getText());
                    prepare.setString(3, manageBuildingsTextFieldFloors.getText());
                    prepare.setString(4, manageBuildingsTextFieldHAUMC.getText());
                    prepare.setString(5, manageBuildingsTextFieldStreet.getText());
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
        if (manageBuildingsTextFieldPhysicalNumber.getText().isEmpty()
                || manageBuildingsTextFieldHAUMC.getText().isEmpty()
                || manageBuildingsTextFieldFloors.getText().isEmpty()
                || manageBuildingsTextFieldStreet.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            if (alert.confirmMessage("Are you sure you want to update?")) {
                String updateData = "update \"Buildings\" set "
                        + "\"BuildingPhysicalNumber\" = '" + manageBuildingsTextFieldPhysicalNumber.getText() + "', "
                        + "\"FloorsCount\" = '" + manageBuildingsTextFieldFloors.getText() + "', "
                        + "\"Haumcid\" = '" + manageBuildingsTextFieldHAUMC.getText() + "', "
                        + "\"StreetID\" = '" + manageBuildingsTextFieldStreet.getText() + "' "
                        + "where \"BuildingID\" = " + tempBuildingsID;
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
            if (tempBuildingsID != null) {
                String sql = "SELECT * FROM \"Buildings\" WHERE \"BuildingID\" = '"
                        + tempBuildingsID + "'";
                connect = Database.connectDB();
                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();

                if (result.next()) {
                    manageBuildingsTextFieldPhysicalNumber.setText(result.getString("BuildingPhysicalNumber"));
                    manageBuildingsTextFieldHAUMC.setText(result.getString("Haumcid"));
                    manageBuildingsTextFieldStreet.setText(result.getString("StreetID"));
                    manageBuildingsTextFieldFloors.setText(result.getString("FloorsCount"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearFields() {
        manageBuildingsTextFieldPhysicalNumber.clear();
        manageBuildingsTextFieldHAUMC.clear();
        manageBuildingsTextFieldFloors.clear();
        manageBuildingsTextFieldStreet.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setFields();
    }
}
