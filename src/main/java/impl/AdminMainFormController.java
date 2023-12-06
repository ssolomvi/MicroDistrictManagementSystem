package impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import impl.helpClasses.*;
import impl.helpClasses.forTableviews.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import impl.helpClasses.DirectoryChooser;

import static impl.helpClasses.ListData.*;

public class AdminMainFormController implements Initializable {
    @FXML
    private Label greet_username;

    @FXML
    private Button manageBtnRealEstate;

    @FXML
    private Button manageBtnOwners;

    @FXML
    private Button manageBtnOwnership;

    @FXML
    private Button manageBtnParking;

    @FXML
    private Button manageBtnHAUMC;

    @FXML
    private Button manageBtnBuildings;

    @FXML
    private Button manageBtnStreets;

    @FXML
    private Button findBtnOwner;

    @FXML
    private Button findBtnRealEstate;

    @FXML
    private AnchorPane realEstateForm;

    @FXML
    private TableView<ResidentialPropertyData> realEstateTableview;

    @FXML
    private TableColumn<ResidentialPropertyData, String> realEstateColID;

    @FXML
    private TableColumn<ResidentialPropertyData, String> realEstateColBuilding;

    @FXML
    private TableColumn<ResidentialPropertyData, String> realEstateColType;

    @FXML
    private TableColumn<ResidentialPropertyData, String> realEstateColSize;

    @FXML
    private TableColumn<ResidentialPropertyData, String> realEstateColRoomCount;

    @FXML
    private TableView<RpmData> rpmTableview;

    @FXML
    private TableColumn<RpmData, String> rpmColOwnerID;

    @FXML
    private TableColumn<RpmData, String> rpmColRealEstateID;

    @FXML
    private TextField rpmTextfieldOwnerID;

    @FXML
    private TextField rpmTextfieldEstateID;

    @FXML
    private TableView<ParkingPData> parkingPTableview;

    @FXML
    private TableColumn<ParkingPData, String> parkingPColOwnerID;

    @FXML
    private TableColumn<ParkingPData, String> parkingPColParkingPID;

    @FXML
    private TableColumn<ParkingPData, String> parkingPColParkingPParking;

    @FXML
    private TableColumn<ParkingPData, String> parkingPColParkingPNumber;

    @FXML
    private TextField parkingPTextfieldOwnerID;

    @FXML
    private TextField parkingPTextfieldParkingPParking;

    @FXML
    private TextField parkingPTextfieldParkingPNumber;

    @FXML
    private AnchorPane parkingForm;

    @FXML
    private TableView<ParkingData> parkingTableview;

    @FXML
    private TableColumn<ParkingData, String> parkingColID;

    @FXML
    private TableColumn<ParkingData, String> parkingColBuilding;

    @FXML
    private TableColumn<ParkingData, String> parkingColType;

    @FXML
    private TableColumn<ParkingData, String> parkingColParkingPlaces;

    @FXML
    private AnchorPane haumcForm;

    @FXML
    private TableView<HAUMCData> haumcTableview;

    @FXML
    private TableColumn<HAUMCData, String> haumcColID;

    @FXML
    private TableColumn<HAUMCData, String> haumcColName;

    @FXML
    private TableColumn<HAUMCData, String> haumcColContact;

    @FXML
    private TableColumn<HAUMCData, String> haumcColDescription;

    @FXML
    private TableColumn<HAUMCData, String> haumcColType;

    @FXML
    private AnchorPane buildingsForm;

    @FXML
    private TableView<BuildingData> buildingsTableview;

    @FXML
    private TableColumn<BuildingData, String> buildingsColID;

    @FXML
    private TableColumn<BuildingData, String> buildingsColNumber;

    @FXML
    private TableColumn<BuildingData, String> buildingsColFloorsCount;

    @FXML
    private TableColumn<BuildingData, String> buildingsColHAUMCID;

    @FXML
    private TableColumn<BuildingData, String> buildingsColStreet;

    @FXML
    private AnchorPane streetsForm;

    @FXML
    private TableView<StreetsData> streetsTableview;

    @FXML
    private TableColumn<StreetsData, String> streetsColID;

    @FXML
    private TableColumn<StreetsData, String> streetColName;

    @FXML
    private TextField streetsTextFieldName;

    @FXML
    private VBox findOwnerForm;

    @FXML
    private TextField findOwnerRPID;

    @FXML
    private TextField findOwnerPPID;

    @FXML
    private AnchorPane findOwnerFoundForm;
    @FXML
    private Label findOwnerFoundID;
    @FXML
    private Label findOwnerFoundFirstName;
    @FXML
    private Label findOwnerFoundLastName;
    @FXML
    private Label findOwnerFoundDateOfBirth;
    @FXML
    private Label findOwnerFoundGender;
    @FXML
    private Label findOwnerFoundContactPhone;
    @FXML
    private Label findOwnerFoundEmail;

    @FXML
    private VBox findRealEstateForm;

    @FXML
    private TextField findRealEstateOwnerID;

    @FXML
    private AnchorPane findRealEstateResults;

    @FXML
    private Label findRealEstateRP;

    @FXML
    private Label findRealEstatePP;

    @FXML
    private TableView<ResidentialPropertyData> findRealEstateRPTableview;

    @FXML
    private TableColumn<ResidentialPropertyData, String> findRealEstateRPColID;

    @FXML
    private TableColumn<ResidentialPropertyData, String> findRealEstateRPColBuilding;

    @FXML
    private TableColumn<ResidentialPropertyData, String> findRealEstateRPColType;

    @FXML
    private TableColumn<ResidentialPropertyData, String> findRealEstateRPColSize;

    @FXML
    private TableColumn<ResidentialPropertyData, String> findRealEstateRPColRoom;

    @FXML
    private TableView<ParkingPData> findRealEstatePPTableview;

    @FXML
    private TableColumn<ParkingPData, String> findRealEstatePPColID;

    @FXML
    private TableColumn<ParkingPData, String> findRealEstatePPColParking;

    @FXML
    private TableColumn<ParkingPData, String> findRealEstatePPColNumber;

    @FXML
    private Button findRealEstatePrintReport;

    @FXML
    private AnchorPane ownersForm;

    @FXML
    private TableView<HouseholderData> ownersTableview;

    @FXML
    private TableColumn<HouseholderData, String> ownersColID;
    @FXML
    private TableColumn<HouseholderData, String> ownersColFirstName;

    @FXML
    private TableColumn<HouseholderData, String> ownersColLastName;

    @FXML
    private TableColumn<HouseholderData, String> ownersColDateOfBirth;

    @FXML
    private TableColumn<HouseholderData, String> ownersColGender;

    @FXML
    private TableColumn<HouseholderData, String> ownersColContact;

    @FXML
    private TableColumn<HouseholderData, String> ownersColEmail;

    @FXML
    private AnchorPane ownershipForm;

    @FXML
    private Button signOutBtn;

    private static String userName;

    public static void setUserName(String userLogin) {
        userName = userLogin;
    }

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private final AlertMessage alert = new AlertMessage();

    // region Residential property form
    public ObservableList<ResidentialPropertyData> residentialPropertyData() {
        ObservableList<ResidentialPropertyData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM \"ResidentialProperty\" order by \"ResidentialPropertyID\"";

        connect = Database.connectDB();

        ResidentialPropertyData rpData;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                rpData = new ResidentialPropertyData(result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getInt(5));

                listData.add(rpData);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listData;
    }

    public void residentialPropertyDisplayData() {
        ObservableList<ResidentialPropertyData> residentialPropertyListData = residentialPropertyData();

        realEstateColID.setCellValueFactory(new PropertyValueFactory<>("ResidentialPropertyID"));
        realEstateColBuilding.setCellValueFactory(new PropertyValueFactory<>("BuildingID"));
        realEstateColType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        realEstateColSize.setCellValueFactory(new PropertyValueFactory<>("Size"));
        realEstateColRoomCount.setCellValueFactory(new PropertyValueFactory<>("RoomCount"));

        realEstateTableview.setItems(residentialPropertyListData);
    }

    public void residentialPropertyManageBtnAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/adminMainForm/ManageRealEstate.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("MicroDistrict management system | Admin Portal | Add real estate");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void residentialPropertyManageBtnUpdate() {
        ResidentialPropertyData rpData = realEstateTableview.getSelectionModel().getSelectedItem();
        int num = realEstateTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select the item first");
        } else {
            ListData.tempRealEstateID = rpData.getResidentialPropertyID();
            ListData.tempRealEstateBuilding = rpData.getBuildingID();
            ListData.tempRealEstateType = rpData.getType();
            ListData.tempRealEstateSize = rpData.getSize();
            ListData.tempRealEstateRoomCount = rpData.getRoomCount();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/adminMainForm/ManageRealEstate.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("MicroDistrict management system | Admin Portal | Update real estate");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void residentialPropertyManageBtnDelete() {
        ResidentialPropertyData rpData = realEstateTableview.getSelectionModel().getSelectedItem();
        int num = realEstateTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select item first");
        } else {
            if (alert.confirmMessage("Are you sure you want to delete Residential property with ID: "
                    + rpData.getResidentialPropertyID() + "?")) {
                String makeSureSelectData = "select \"ResidentialPropertyID\" from \"Ownerships\" where \"ResidentialPropertyID\" = '"
                        + rpData.getResidentialPropertyID() + "'";
                connect = Database.connectDB();
                try {
                    statement = connect.createStatement();
                    result = statement.executeQuery(makeSureSelectData);

                    if (result.next()) {
                        alert.errorMessage("Deletion cannot be performed, because householder with ID = " +
                                rpData.getResidentialPropertyID() + " has residential property registered." +
                                "Delete or modify the appropriate notes in \"ResidentialProperty\" table");
                    } else {
                        String deleteData = "Delete from \"ResidentialProperty\" where \"ResidentialPropertyID\" = '"
                                + rpData.getResidentialPropertyID() + "'";

                        try {
                            statement = connect.createStatement();
                            statement.executeUpdate(deleteData);
                            alert.successMessage("Deleted successfully!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        residentialPropertyDisplayData();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                alert.errorMessage("Cancelled.");
            }
        }
    }
    // endregion

    // region Householder form
    public ObservableList<HouseholderData> householderData() {
        ObservableList<HouseholderData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM \"OwnersOfResidentialProperty\" order by \"OwnerID\"";

        connect = Database.connectDB();

        HouseholderData hData;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                hData = new HouseholderData(result.getInt("OwnerID"),
                        result.getString("FirstName"),
                        result.getString("LastName"),
                        result.getDate("DateOfBirth"),
                        result.getString("Gender"),
                        result.getString("ContactNumber"),
                        result.getString("Email"));

                listData.add(hData);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listData;
    }

    public void householderDisplayData() {
        ObservableList<HouseholderData> householderListData = householderData();

        ownersColID.setCellValueFactory(new PropertyValueFactory<>("OwnerID"));
        ownersColFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        ownersColLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        ownersColDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));
        ownersColGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        ownersColContact.setCellValueFactory(new PropertyValueFactory<>("ContactNumber"));
        ownersColEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        ownersTableview.setItems(householderListData);
    }

    public void householderManageBtnAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/adminMainForm/ManageHouseholder.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("MicroDistrict management system | Admin Portal | Add householder");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void householderManageBtnUpdate() {

        HouseholderData hData = ownersTableview.getSelectionModel().getSelectedItem();
        int num = ownersTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select the item first");
        } else {
            ListData.tempHouseholderID = hData.getOwnerID();
            ListData.tempHouseholderFirstName = hData.getFirstName();
            ListData.tempHouseholderLastName = hData.getLastName();
            ListData.tempHouseholderDateOfBirth = hData.getDateOfBirth();
            ListData.tempHouseholderGender = hData.getGender();
            ListData.tempHouseholderContact = hData.getContactNumber();
            ListData.tempHouseholderEmail = hData.getEmail();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/adminMainForm/ManageHouseholder.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("MicroDistrict management system | Admin Portal | Update householder");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void householderManageBtnDelete() {
        HouseholderData hData = ownersTableview.getSelectionModel().getSelectedItem();
        int num = ownersTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select item first");
        } else {
            if (alert.confirmMessage("Are you sure you want to delete Householder with ID: "
                    + hData.getOwnerID() + "?")) {
                String makeSureSelectData = "select \"OwnerID\" from \"Ownerships\" where \"OwnerID\" = '"
                        + hData.getOwnerID() + "'";
                connect = Database.connectDB();
                try {
                    statement = connect.createStatement();
                    result = statement.executeQuery(makeSureSelectData);

                    if (result.next()) {
                        alert.errorMessage("Deletion cannot be performed, because householder with ID = " +
                                hData.getOwnerID() + " has residential property registered." +
                                "Delete or modify the appropriate notes in \"Ownership\" table");
                    } else {
                        String deleteData = "Delete from \"OwnersOfResidentialProperty\" where \"OwnerID\" = '"
                                + hData.getOwnerID() + "'";

                        try {
                            statement = connect.createStatement();
                            statement.executeUpdate(deleteData);
                            alert.successMessage("Deleted successfully!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        householderDisplayData();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                alert.errorMessage("Cancelled.");
            }
        }
    }
    // endregion

    // region Ownership form
    // region Residential property management (RPM)
    public ObservableList<RpmData> rpmData() {
        ObservableList<RpmData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM \"Ownerships\" order by \"OwnerID\"";

        connect = Database.connectDB();

        RpmData data;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                data = new RpmData(
                        result.getInt("OwnerID"),
                        result.getInt("ResidentialPropertyID")
                );
                listData.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listData;
    }

    public void rpmDisplayData() {
        ObservableList<RpmData> listData = rpmData();

        rpmColOwnerID.setCellValueFactory(new PropertyValueFactory<>("OwnerID"));
        rpmColRealEstateID.setCellValueFactory(new PropertyValueFactory<>("ResidentialPropertyID"));

        rpmTableview.setItems(listData);
    }
    // endregion

    // region Parking places management
    public ObservableList<ParkingPData> parkingPData() {
        ObservableList<ParkingPData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM \"ParkingPlaces\" order by \"OwnerID\"";

        connect = Database.connectDB();

        ParkingPData data;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                data = new ParkingPData(
                        result.getInt("OwnerID"),
                        result.getInt("ParkingPlaceID"),
                        result.getInt("ParkingID"),
                        result.getInt("ParkingPlaceNumber")
                );
                listData.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listData;
    }

    public void parkingPDisplayData() {
        ObservableList<ParkingPData> listData = parkingPData();

        parkingPColOwnerID.setCellValueFactory(new PropertyValueFactory<>("OwnerID"));
        parkingPColParkingPID.setCellValueFactory(new PropertyValueFactory<>("ParkingPlaceID"));
        parkingPColParkingPParking.setCellValueFactory(new PropertyValueFactory<>("ParkingID"));
        parkingPColParkingPNumber.setCellValueFactory(new PropertyValueFactory<>("ParkingPlaceNumber"));

        parkingPTableview.setItems(listData);
    }

    public int generateParkingPID() {
        String selectData = "select max(\"ParkingPlaceID\") from \"ParkingPlaces\"";
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
    // endregion

    public void ownershipsBtnAdd() {
        if (!rpmTextfieldOwnerID.getText().isEmpty()
                && !rpmTextfieldEstateID.getText().isEmpty()) {
            connect = Database.connectDB();

            String checkData = "select \"OwnerID\" from \"OwnersOfResidentialProperty\" " +
                    "where \"OwnerID\" = '" + rpmTextfieldOwnerID.getText() + "'";
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (!result.next()) {
                    alert.errorMessage("Owner with ID = " + rpmTextfieldOwnerID.getText()
                            + " does not exist");
                    return;
                }

                checkData = "select \"ResidentialPropertyID\" from \"ResidentialProperty\" " +
                        "where \"ResidentialPropertyID\" = '" + rpmTextfieldEstateID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (!result.next()) {
                    alert.errorMessage("Residential property with ID = " + rpmTextfieldEstateID.getText()
                            + " does not exist");
                    return;
                }

                checkData = "select \"ResidentialPropertyID\" from \"Ownerships\" " +
                        "where \"ResidentialPropertyID\" = '" + rpmTextfieldEstateID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert.errorMessage("Residential property with ID = " + rpmTextfieldEstateID.getText()
                            + " already is owned");
                    return;
                }
                String insertData = "insert into \"Ownerships\""
                        + "(\"OwnerID\", \"ResidentialPropertyID\")"
                        + "values(?, ?)";

                prepare = connect.prepareStatement(insertData);
                prepare.setInt(1, Integer.parseInt(rpmTextfieldOwnerID.getText()));
                prepare.setInt(2, Integer.parseInt(rpmTextfieldEstateID.getText()));
                prepare.executeUpdate();
                alert.successMessage("Added Successfully!");
                rpmDisplayData();

                rpmTextfieldOwnerID.clear();
                rpmTextfieldEstateID.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (!parkingPTextfieldOwnerID.getText().isEmpty()
                && !parkingPTextfieldParkingPParking.getText().isEmpty()
                && !parkingPTextfieldParkingPNumber.getText().isEmpty()) {
            connect = Database.connectDB();
            // exists parking, exists owner, parking place number is unique on this parking,
            // parking place number is <= parking places count

            String checkData = "select \"ParkingID\" from \"Parking\" where " +
                    "\"ParkingID\" = '" + parkingPTextfieldParkingPParking.getText() + "'";

            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (!result.next()) {
                    alert.errorMessage("Parking with ID = " + parkingPTextfieldParkingPParking.getText()
                            + " does not exist");
                    return;
                }

                checkData = "select \"OwnerID\" from \"OwnersOfResidentialProperty\" " +
                        "where \"OwnerID\" = '" + parkingPTextfieldOwnerID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (!result.next()) {
                    alert.errorMessage("Owner with ID = " + parkingPTextfieldOwnerID.getText()
                            + " does not exist");
                    return;
                }

                checkData = "select \"ParkingPlaceID\" from \"ParkingPlaces\" where " +
                        "\"ParkingPlaceNumber\" = '" + parkingPTextfieldParkingPNumber.getText() + "' and " +
                        "\"ParkingID\" = '" + parkingPTextfieldParkingPParking.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert.errorMessage("Parking place with number = " + parkingPTextfieldParkingPNumber.getText()
                            + " already is owned");
                    return;
                }

                checkData = "select \"ParkingID\" from \"Parking\" where " +
                        "\"ParkingPlacesCount\" >= '" + parkingPTextfieldParkingPNumber.getText() + "' and " +
                        "\"ParkingID\" = '" + parkingPTextfieldParkingPParking.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (!result.next()) {
                    alert.errorMessage("Invalid parking place number. Parking with number = " + parkingPTextfieldParkingPNumber.getText()
                            + " does not contain enough parking places");
                    return;
                }
                String insertData = "insert into \"ParkingPlaces\" "
                        + "(\"ParkingPlaceID\", \"OwnerID\", \"ParkingID\", \"ParkingPlaceNumber\")"
                        + "values(?, ?, ?, ?)";

                prepare = connect.prepareStatement(insertData);
                prepare.setInt(1, generateParkingPID());
                prepare.setInt(2, Integer.parseInt(parkingPTextfieldOwnerID.getText()));
                prepare.setInt(3, Integer.parseInt(parkingPTextfieldParkingPParking.getText()));
                prepare.setInt(4, Integer.parseInt(parkingPTextfieldParkingPNumber.getText()));
                prepare.executeUpdate();
                alert.successMessage("Added Successfully!");
                parkingPDisplayData();

                parkingPTextfieldOwnerID.clear();
                parkingPTextfieldParkingPParking.clear();
                parkingPTextfieldParkingPNumber.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            alert.errorMessage("Please fill all blank fields.");
        }
    }

    public void ownershipsBtnUpdate() {
        RpmData rpmData = rpmTableview.getSelectionModel().getSelectedItem();
        int rpmIndex = rpmTableview.getSelectionModel().getSelectedIndex();

        ParkingPData parkingPData = parkingPTableview.getSelectionModel().getSelectedItem();
        int parkingPIndex = parkingPTableview.getSelectionModel().getSelectedIndex();
        if (rpmIndex >= 0) {
            ListData.tempRpmOwnerID = rpmData.getOwnerID();
            ListData.tempRpmRealEstateID = rpmData.getResidentialPropertyID();

            if (rpmTextfieldOwnerID.getText().isEmpty()
                    || rpmTextfieldEstateID.getText().isEmpty()) {
                alert.errorMessage("Please fill all blank fields.");
            } else {
                // todo: check-ups

                if (alert.confirmMessage("Are you sure you want to update?")) {
                    String updateData = "update \"Ownerships\" set "
                            + "\"OwnerID\" = '" + rpmTextfieldOwnerID.getText() + "', "
                            + "\"ResidentialPropertyID\" = '" + rpmTextfieldEstateID.getText() + "' "
                            + "where \"OwnerID\" = " + tempRpmOwnerID;
                    try {
                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();
                        alert.successMessage("Updated Successfully!");
                        rpmDisplayData();
                        rpmTextfieldOwnerID.clear();
                        rpmTextfieldEstateID.clear();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    alert.errorMessage("Cancelled");
                }
            }
        } else if (parkingPIndex >= 0) {
            ListData.tempParkingPID = parkingPData.getParkingPlaceID();
            ListData.tempParkingPOwnerID = parkingPData.getOwnerID();
            ListData.tempParkingPParking = parkingPData.getParkingID();
            ListData.tempParkingPNumber = parkingPData.getParkingPlaceNumber();

            if (parkingPTextfieldOwnerID.getText().isEmpty()
                    || parkingPTextfieldParkingPParking.getText().isEmpty()
                    || parkingPTextfieldParkingPNumber.getText().isEmpty()) {
                alert.errorMessage("Please fill all blank fields.");
            } else {
                // todo: check-ups

                if (alert.confirmMessage("Are you sure you want to update?")) {
                    String updateData = "update \"ParkingPlaces\" set "
                            + "\"OwnerID\" = '" + parkingPTextfieldOwnerID.getText() + "', "
                            + "\"ParkingID\" = '" + parkingPTextfieldParkingPParking.getText() + "', "
                            + "\"ParkingPlaceNumber\" = '" + parkingPTextfieldParkingPNumber.getText() + "' "
                            + "where \"OwnerID\" = " + tempParkingPOwnerID;
                    try {
                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();
                        alert.successMessage("Updated Successfully!");
                        parkingPDisplayData();
                        parkingPTextfieldOwnerID.clear();
                        parkingPTextfieldParkingPParking.clear();
                        parkingPTextfieldParkingPNumber.clear();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    alert.errorMessage("Cancelled");
                }
            }
        } else {
            alert.errorMessage("Please select the item first");
        }
    }

    public void ownershipsBtnDelete() {
        RpmData rpmData = rpmTableview.getSelectionModel().getSelectedItem();
        int rpmIndex = rpmTableview.getSelectionModel().getSelectedIndex();

        ParkingPData parkingPData = parkingPTableview.getSelectionModel().getSelectedItem();
        int parkingPIndex = parkingPTableview.getSelectionModel().getSelectedIndex();

        if (rpmIndex >= 0) {
            if (alert.confirmMessage("Are you sure you want to delete?")) {
                String deleteData = "Delete from \"Ownerships\" where \"OwnerID\" = '"
                        + rpmData.getOwnerID() + "' and \"ResidentialPropertyID\" = '"
                        + rpmData.getResidentialPropertyID() + "'";
                try {
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);
                    alert.successMessage("Deleted successfully!");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                rpmDisplayData();
            } else {
                alert.errorMessage("Cancelled.");
            }
        } else if (parkingPIndex >= 0) {
            if (alert.confirmMessage("Are you sure you want to delete?")) {
                String deleteData = "Delete from \"ParkingPlaces\" where \"ParkingPlaceID\" = '"
                        + parkingPData.getParkingPlaceID() + "'";
                try {
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);
                    alert.successMessage("Deleted successfully!");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                parkingPDisplayData();
            } else {
                alert.errorMessage("Cancelled.");
            }
        } else {
            alert.errorMessage("Please select the item first");
        }
    }

    // endregion

    // region Parking form
    public ObservableList<ParkingData> parkingData() {
        ObservableList<ParkingData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM \"Parking\" order by \"ParkingID\"";

        connect = Database.connectDB();

        ParkingData pData;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                pData = new ParkingData(result.getInt("ParkingID"),
                        result.getInt("BuildingID"),
                        result.getString("ParkingType"),
                        result.getInt("ParkingPlacesCount"));
                listData.add(pData);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listData;
    }

    public void parkingDisplayData() {
        ObservableList<ParkingData> parkingListData = parkingData();

        parkingColID.setCellValueFactory(new PropertyValueFactory<>("ParkingID"));
        parkingColBuilding.setCellValueFactory(new PropertyValueFactory<>("BuildingID"));
        parkingColType.setCellValueFactory(new PropertyValueFactory<>("ParkingType"));
        parkingColParkingPlaces.setCellValueFactory(new PropertyValueFactory<>("ParkingPlacesCount"));

        parkingTableview.setItems(parkingListData);
    }

    public void parkingManageBtnAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/adminMainForm/ManageParking.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("MicroDistrict management system | Admin Portal | Add parking");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void parkingManageBtnUpdate() {
        ParkingData pData = parkingTableview.getSelectionModel().getSelectedItem();
        int num = parkingTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select the item first");
        } else {
            ListData.tempParkingID = pData.getParkingID();
            ListData.tempParkingBuildingID = pData.getBuildingID();
            ListData.tempParkingParkingType = pData.getParkingType();
            ListData.tempParkingParkingPlacesCount = pData.getParkingPlacesCount();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/adminMainForm/ManageParking.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("MicroDistrict management system | Admin Portal | Update parking");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void parkingManageBtnDelete() {
        ParkingData pData = parkingTableview.getSelectionModel().getSelectedItem();
        int num = parkingTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select item first");
        } else {
            if (alert.confirmMessage("Are you sure you want to delete parking with ID: "
                    + pData.getParkingID() + "?")) {
                String makeSureSelectData = "select \"ParkingID\" from \"ParkingPlaces\" where \"ParkingID\" = '"
                        + pData.getParkingID() + "'";
                connect = Database.connectDB();
                try {
                    statement = connect.createStatement();
                    result = statement.executeQuery(makeSureSelectData);

                    if (result.next()) {
                        alert.errorMessage("Deletion cannot be performed. There are places registered on parking with ID = " +
                                pData.getParkingID() + "." +
                                " Delete or modify the appropriate notes in \"Parking\" table");
                    } else {
                        String deleteData = "Delete from \"Parking\" where \"ParkingID\" = '"
                                + pData.getParkingID() + "'";

                        try {
                            statement = connect.createStatement();
                            statement.executeUpdate(deleteData);
                            alert.successMessage("Deleted successfully!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        parkingDisplayData();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                alert.errorMessage("Cancelled.");
            }
        }
    }
    // endregion

    // region HAUMC form
    public ObservableList<HAUMCData> haumcData() {
        ObservableList<HAUMCData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM \"haumc\" order by \"haumcid\"";

        connect = Database.connectDB();

        HAUMCData hData;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                hData = new HAUMCData(result.getInt("haumcid"),
                        result.getString("Name"),
                        result.getString("ContactPhone"),
                        result.getString("Description"),
                        result.getString("Type"));
                listData.add(hData);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listData;
    }

    public void haumcDisplayData() {
        ObservableList<HAUMCData> haumcListData = haumcData();

        haumcColID.setCellValueFactory(new PropertyValueFactory<>("haumcid"));
        haumcColName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        haumcColContact.setCellValueFactory(new PropertyValueFactory<>("ContactPhone"));
        haumcColDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        haumcColType.setCellValueFactory(new PropertyValueFactory<>("Type"));

        haumcTableview.setItems(haumcListData);
    }

    public void haumcManageBtnAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/adminMainForm/ManageHaumc.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("MicroDistrict management system | Admin Portal | Add house&management company");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void haumcManageBtnUpdate() {
        HAUMCData data = haumcTableview.getSelectionModel().getSelectedItem();
        int num = haumcTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select the item first");
        } else {
            ListData.tempHaumcID = data.getHaumcid();
            ListData.tempHaumcName = data.getName();
            ListData.tempHaumcContactPhone = data.getContactPhone();
            ListData.tempHaumcDescription = data.getDescription();
            ListData.tempHaumcType = data.getType();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/adminMainForm/ManageHaumc.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("MicroDistrict management system | Admin Portal | Add house&management company");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void haumcManageBtnDelete() {
        HAUMCData data = haumcTableview.getSelectionModel().getSelectedItem();
        int num = haumcTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select item first");
        } else {
            if (alert.confirmMessage("Are you sure you want to delete housing&management company with ID: "
                    + data.getHaumcid() + "?")) {
                String makeSureSelectData = "select \"haumcid\" from \"Buildings\" where \"haumcid\" = '"
                        + data.getHaumcid() + "'";
                connect = Database.connectDB();
                try {
                    statement = connect.createStatement();
                    result = statement.executeQuery(makeSureSelectData);

                    if (result.next()) {
                        alert.errorMessage("Deletion cannot be performed. This housing&management company has clients." +
                                " Delete or modify the appropriate notes in \"Buildings\" table");
                    } else {
                        String deleteData = "Delete from \"haumc\" where \"haumcid\" = '"
                                + data.getHaumcid() + "'";

                        try {
                            statement = connect.createStatement();
                            statement.executeUpdate(deleteData);
                            alert.successMessage("Deleted successfully!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        haumcDisplayData();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                alert.errorMessage("Cancelled.");
            }
        }
    }
    // endregion

    // region Buildings form
    public ObservableList<BuildingData> buildingData() {
        ObservableList<BuildingData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM \"Buildings\" order by \"BuildingID\"";

        connect = Database.connectDB();

        BuildingData data;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                data = new BuildingData(
                        result.getInt("BuildingID"),
                        result.getInt("BuildingPhysicalNumber"),
                        result.getInt("FloorsCount"),
                        result.getInt("Haumcid"),
                        result.getInt("StreetID")
                );
                listData.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listData;
    }

    public void buildingsDisplayData() {
        ObservableList<BuildingData> listData = buildingData();

        buildingsColID.setCellValueFactory(new PropertyValueFactory<>("BuildingID"));
        buildingsColNumber.setCellValueFactory(new PropertyValueFactory<>("BuildingPhysicalNumber"));
        buildingsColHAUMCID.setCellValueFactory(new PropertyValueFactory<>("Haumcid"));
        buildingsColFloorsCount.setCellValueFactory(new PropertyValueFactory<>("FloorsCount"));
        buildingsColStreet.setCellValueFactory(new PropertyValueFactory<>("StreetID"));

        buildingsTableview.setItems(listData);
    }

    public void buildingsManageBtnAdd() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/adminMainForm/ManageBuildings.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("MicroDistrict management system | Admin Portal | Add building");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildingManageBtnUpdate() {
        BuildingData data = buildingsTableview.getSelectionModel().getSelectedItem();
        int num = buildingsTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select the item first");
        } else {
            ListData.tempBuildingsID = data.getBuildingID();
            ListData.tempBuildingsPhysicalNumber = data.getBuildingPhysicalNumber();
            ListData.tempBuildingsHaumcid = data.getHaumcid();
            ListData.tempBuildingsFloorsCount = data.getFloorsCount();
            ListData.tempBuildingsStreetID = data.getStreetID();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/adminMainForm/ManageBuildings.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("MicroDistrict management system | Admin Portal | Update building info");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void buildingManageBtnDelete() {
        BuildingData data = buildingsTableview.getSelectionModel().getSelectedItem();
        int num = buildingsTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select item first");
        } else {
            if (alert.confirmMessage("Are you sure you want to delete building with ID: "
                    + data.getBuildingID() + "?")) {
                String makeSureSelectData = "select \"BuildingID\" from \"ResidentialProperty\" where \"BuildingID\" = '"
                        + data.getBuildingID() + "'";
                connect = Database.connectDB();
                try {
                    statement = connect.createStatement();
                    result = statement.executeQuery(makeSureSelectData);

                    if (result.next()) {
                        alert.errorMessage("Deletion cannot be performed. This building has residential property registered." +
                                " Delete or modify the appropriate notes in \"ResidentialProperty\" table");
                    } else {
                        String deleteData = "Delete from \"Buildings\" where \"BuildingID\" = '"
                                + data.getBuildingID() + "'";

                        try {
                            statement = connect.createStatement();
                            statement.executeUpdate(deleteData);
                            alert.successMessage("Deleted successfully!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        buildingsDisplayData();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                alert.errorMessage("Cancelled.");
            }
        }
    }
    // endregion

    // region Streets form
    public ObservableList<StreetsData> streetsData() {
        ObservableList<StreetsData> listData = FXCollections.observableArrayList();

        String selectData = "SELECT * FROM \"Streets\" order by \"StreetID\"";

        connect = Database.connectDB();

        StreetsData data;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                data = new StreetsData(
                        result.getInt("StreetID"),
                        result.getString("StreetName")
                );
                listData.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listData;
    }

    public void streetsDisplayData() {
        ObservableList<StreetsData> listData = streetsData();

        streetsColID.setCellValueFactory(new PropertyValueFactory<>("StreetID"));
        streetColName.setCellValueFactory(new PropertyValueFactory<>("StreetName"));

        streetsTableview.setItems(listData);
    }

    public int generateStreetID() {
        String selectData = "select max(\"StreetID\") from \"Streets\"";
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

    public void streetsManageBtnAdd() {
        if (streetsTextFieldName.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            connect = Database.connectDB();

            String checkData = "select \"StreetName\" from \"Streets\" " +
                    "where \"StreetName\" = '" + streetsTextFieldName.getText() + "'";
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert.errorMessage("Street with name = " + streetsTextFieldName.getText()
                            + " already exists");
                } else {
                    String insertData = "insert into \"Streets\""
                            + "(\"StreetID\", \"StreetName\")"
                            + "values(?, ?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setInt(1, generateStreetID());
                    prepare.setString(2, streetsTextFieldName.getText());
                    prepare.executeUpdate();
                    alert.successMessage("Added Successfully!");
                    streetsDisplayData();

                    streetsTextFieldName.clear();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void streetsManageBtnUpdate() {
        StreetsData data = streetsTableview.getSelectionModel().getSelectedItem();
        int num = streetsTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select the item first");
        } else {
            ListData.tempStreetsID = data.getStreetID();
            ListData.tempStreetsName = data.getStreetName();

            if (streetsTextFieldName.getText().isEmpty()) {
                alert.errorMessage("Please fill all blank fields.");
            } else {
                if (alert.confirmMessage("Are you sure you want to update?")) {
                    if (!Objects.equals(streetsTextFieldName.getText(), ListData.tempStreetsName)) {
                        String checkData = "select \"StreetName\" from \"Streets\" where \"StreetName\" = '" +
                                streetsTextFieldName.getText() + "'";

                        try {
                            statement = connect.createStatement();
                            result = statement.executeQuery(checkData);

                            if (result.next()) {
                                alert.errorMessage("Street with name = " + streetsTextFieldName.getText()
                                        + " already exists");
                                return;
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    String updateData = "update \"Streets\" set "
                            + "\"StreetName\" = '" + streetsTextFieldName.getText() + "' "
                            + "where \"StreetID\" = " + tempStreetsID;
                    try {
                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();
                        alert.successMessage("Updated Successfully!");
                        streetsDisplayData();
                        streetsTextFieldName.clear();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    alert.errorMessage("Cancelled");
                }
            }
        }
    }

    public void streetsManageBtnDelete() {
        StreetsData data = streetsTableview.getSelectionModel().getSelectedItem();
        int num = streetsTableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            alert.errorMessage("Please select item first");
        } else {
            if (alert.confirmMessage("Are you sure you want to delete street with ID: "
                    + data.getStreetID() + "?")) {
                String makeSureSelectData = "select \"StreetID\" from \"Buildings\" where \"StreetID\" = '"
                        + data.getStreetID() + "'";
                connect = Database.connectDB();
                try {
                    statement = connect.createStatement();
                    result = statement.executeQuery(makeSureSelectData);

                    if (result.next()) {
                        alert.errorMessage("Deletion cannot be performed. This street has buildings registered." +
                                " Delete or modify the appropriate notes in \"Buildings\" table");
                    } else {
                        String deleteData = "Delete from \"Streets\" where \"StreetID\" = '"
                                + data.getStreetID() + "'";

                        try {
                            statement = connect.createStatement();
                            statement.executeUpdate(deleteData);
                            alert.successMessage("Deleted successfully!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        streetsDisplayData();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                alert.errorMessage("Cancelled.");
            }
        }
    }
    // endregion

    // region Find owner form
    /*
    @FXML
    private AnchorPane findOwnerFoundForm;
    @FXML
    private Label findOwnerFoundID;
    @FXML
    private Label findOwnerFoundFirstName;
    @FXML
    private Label findOwnerFoundLastName;
    @FXML
    private Label findOwnerFoundDateOfBirth;
    @FXML
    private Label findOwnerFoundGender;
    @FXML
    private Label findOwnerFoundContactPhone;
    @FXML
    private Label findOwnerFoundEmail;
    * */

    private void revealAnOwner() throws SQLException {
        findOwnerFoundID.setText(String.valueOf(result.getInt("OwnerID")));
        findOwnerFoundFirstName.setText(result.getString("FirstName"));
        findOwnerFoundLastName.setText(result.getString("LastName"));
        findOwnerFoundDateOfBirth.setText(String.valueOf(result.getDate("DateOfBirth")));
        findOwnerFoundGender.setText(result.getString("Gender"));
        findOwnerFoundContactPhone.setText(result.getString("ContactNumber"));
        findOwnerFoundEmail.setText(result.getString("Email"));
        findOwnerFoundForm.setVisible(true);
    }

    public void findOwnerRPBtn() {
        if (findOwnerRPID.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            String checkData = "select \"ResidentialPropertyID\" from \"Ownerships\" where " +
                    "\"ResidentialPropertyID\" = '" + findOwnerRPID.getText() + "'";
            connect = Database.connectDB();

            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (!result.next()) {
                    alert.errorMessage("Residential property with ID = " + findOwnerRPID.getText() +
                            " doesn't exist or isn't owned");
                    return;
                }

                String selectData = "select * from \"OwnersOfResidentialProperty\" where " +
                        "\"OwnerID\" in (select \"OwnerID\" from \"Ownerships\" where " +
                        "\"ResidentialPropertyID\" = '" + findOwnerRPID.getText() + "')";

                statement = connect.createStatement();
                result = statement.executeQuery(selectData);

                if (result.next()) {
                    revealAnOwner();
                } else {
                    alert.errorMessage("Can't find an owner of residential property with ID = " + findOwnerRPID);
                }
                findOwnerRPID.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void findOwnerPPBtn() {
        if (findOwnerPPID.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields.");
        } else {
            connect = Database.connectDB();

            try {
                String selectData = "select * from \"OwnersOfResidentialProperty\" where " +
                        "\"OwnerID\" in (select \"OwnerID\" from \"ParkingPlaces\" where " +
                        "\"ParkingPlaceID\" = '" + findOwnerPPID.getText() + "')";

                statement = connect.createStatement();
                result = statement.executeQuery(selectData);

                if (result.next()) {
                    revealAnOwner();
                } else {
                    alert.errorMessage("Parking place with ID = " + findOwnerPPID.getText() +
                            " doesn't exist or isn't owned");
                }
                findOwnerPPID.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // endregion

    // region Find real estate form

    ObservableList<ResidentialPropertyData> rpListData = FXCollections.observableArrayList();
    ObservableList<ParkingPData> ppListData = FXCollections.observableArrayList();

    public void findRealEstateBtnFind() {
        if (findRealEstateOwnerID.getText().isEmpty()) {
            alert.errorMessage("Please select item first");
            return;
        }
        rpListData.removeAll();
        ppListData.removeAll();
        findRealEstateRPTableview.getItems().clear();
        findRealEstatePPTableview.getItems().clear();

        String checkData = "select \"OwnerID\" from \"OwnersOfResidentialProperty\" where " +
                "\"OwnerID\" = '" + findRealEstateOwnerID.getText() + "'";
        try {
            connect = Database.connectDB();
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (!result.next()) {
                alert.errorMessage("No owner with ID = " + findRealEstateOwnerID.getText());
                return;
            }

            String findDataRP = "select * from \"ResidentialProperty\" where " +
                    "\"ResidentialPropertyID\" in (select \"ResidentialPropertyID\" from \"Ownerships\" " +
                    "where \"OwnerID\" = '" + findRealEstateOwnerID.getText() + "')";
            result = statement.executeQuery(findDataRP);
            ResidentialPropertyData data;

            int rpCount = 0;
            while (result.next()) {
                data = new ResidentialPropertyData(result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getInt(5));
                rpListData.add(data);
                rpCount++;
            }
            findRealEstateRPColID.setCellValueFactory(new PropertyValueFactory<>("ResidentialPropertyID"));
            findRealEstateRPColBuilding.setCellValueFactory(new PropertyValueFactory<>("BuildingID"));
            findRealEstateRPColType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            findRealEstateRPColSize.setCellValueFactory(new PropertyValueFactory<>("Size"));
            findRealEstateRPColRoom.setCellValueFactory(new PropertyValueFactory<>("RoomCount"));
            findRealEstateRPTableview.setItems(rpListData);

            String findDataPP = "select * from \"ParkingPlaces\" where " +
                    "\"OwnerID\" = '" + findRealEstateOwnerID.getText() + "'";
            result = statement.executeQuery(findDataPP);
            ParkingPData parkingPData;
            int ppCount = 0;
            while (result.next()) {
                parkingPData = new ParkingPData(
                        result.getInt("OwnerID"),
                        result.getInt("ParkingPlaceID"),
                        result.getInt("ParkingID"),
                        result.getInt("ParkingPlaceNumber")
                );
                ppListData.add(parkingPData);
                ppCount++;
            }
            findRealEstatePPColID.setCellValueFactory(new PropertyValueFactory<>("ParkingPlaceID"));
            findRealEstatePPColParking.setCellValueFactory(new PropertyValueFactory<>("ParkingID"));
            findRealEstatePPColNumber.setCellValueFactory(new PropertyValueFactory<>("ParkingPlaceNumber"));
            findRealEstatePPTableview.setItems(ppListData);

            findRealEstateRP.setText(rpCount + " residential properties");
            findRealEstatePP.setText(ppCount + " parking places");
            findRealEstateResults.setVisible(true);

            String findOwnerData = "select * from \"OwnersOfResidentialProperty\" where " +
                    "\"OwnerID\" = '" + findRealEstateOwnerID.getText() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(findOwnerData);
            result.next();
            tempHouseholderID = result.getInt(1);
            tempHouseholderFirstName = result.getString(2);
            tempHouseholderLastName = result.getString(3);
            tempHouseholderDateOfBirth = result.getDate(4);
            tempHouseholderGender = result.getString(5);
            tempHouseholderContact = result.getString(6);
            tempHouseholderEmail = result.getString(7);

            findRealEstateOwnerID.clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generatePDFName(String dirName, String fullName) {
        java.util.Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy-h_mm_ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        String formattedDate = sdf.format(date);
        return dirName + File.separator + "ownership-" + fullName + "-" + formattedDate + ".pdf";
    }

    private void generatePDF(String pathToPDF, HouseholderData householderData,
                             ObservableList<ResidentialPropertyData> residentialPropertyData, ObservableList<ParkingPData> parkingPData) {
        try {
            OutputStream file = new FileOutputStream(pathToPDF);

            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            document.addCreationDate();

            com.itextpdf.text.Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            com.itextpdf.text.Font additionalFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.ITALIC);
            com.itextpdf.text.Font ordinaryFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);


            document.add(new Paragraph(new Date().toString(), ordinaryFont));

            String titleString = "ID: " + householderData.getOwnerID() + " " +
                    householderData.getFirstName() + " " + householderData.getLastName() + System.lineSeparator();
            document.add(new Paragraph(titleString, headerFont));
            String cursiveString = householderData.getGender() + System.lineSeparator() +
                    "Contact phone number: " + householderData.getContactNumber() + System.lineSeparator() +
                    "Email: " + householderData.getEmail() + System.lineSeparator();
            document.add(new Paragraph(cursiveString, additionalFont));


            document.add(new Paragraph("Residential property: ",
                    headerFont));
            document.add(new Paragraph(" "));
            PdfPTable residentialPropertyTable = new PdfPTable(5);
            PdfPCell header1 = new PdfPCell(new Phrase(new Chunk("ID", ordinaryFont)));
            PdfPCell header2 = new PdfPCell(new Phrase(new Chunk("Building", ordinaryFont)));
            PdfPCell header3 = new PdfPCell(new Phrase(new Chunk("Type", ordinaryFont)));
            PdfPCell header4 = new PdfPCell(new Phrase(new Chunk("Size", ordinaryFont)));
            PdfPCell header5 = new PdfPCell(new Phrase(new Chunk("Room count", ordinaryFont)));
            header1.setHorizontalAlignment(Element.ALIGN_CENTER);
            header2.setHorizontalAlignment(Element.ALIGN_CENTER);
            header3.setHorizontalAlignment(Element.ALIGN_CENTER);
            header4.setHorizontalAlignment(Element.ALIGN_CENTER);
            header5.setHorizontalAlignment(Element.ALIGN_CENTER);
            residentialPropertyTable.addCell(header1);
            residentialPropertyTable.addCell(header2);
            residentialPropertyTable.addCell(header3);
            residentialPropertyTable.addCell(header4);
            residentialPropertyTable.addCell(header5);
            residentialPropertyTable.setHeaderRows(1);

            residentialPropertyData.forEach((rp) -> {
                residentialPropertyTable.addCell(new Phrase(new Chunk(rp.getResidentialPropertyID().toString(), ordinaryFont)));
                residentialPropertyTable.addCell(new Phrase(new Chunk(rp.getBuildingID().toString(), ordinaryFont)));
                residentialPropertyTable.addCell(new Phrase(new Chunk(rp.getType(), ordinaryFont)));
                residentialPropertyTable.addCell(new Phrase(new Chunk(rp.getSize().toString(), ordinaryFont)));
                residentialPropertyTable.addCell(new Phrase(new Chunk(rp.getRoomCount().toString(), ordinaryFont)));
            });
            document.add(residentialPropertyTable);


            document.add(new Paragraph(System.lineSeparator() + "Parking places: ",
                    headerFont));
            document.add(new Paragraph(" "));
            PdfPTable ppTable = new PdfPTable(3);
            PdfPCell headerPP1 = new PdfPCell(new Phrase(new Chunk("Parking place ID", ordinaryFont)));
            PdfPCell headerPP2 = new PdfPCell(new Phrase(new Chunk("Parking ID", ordinaryFont)));
            PdfPCell headerPP3 = new PdfPCell(new Phrase(new Chunk("Physical number", ordinaryFont)));
            headerPP1.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerPP2.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerPP3.setHorizontalAlignment(Element.ALIGN_CENTER);
            ppTable.addCell(headerPP1);
            ppTable.addCell(headerPP2);
            ppTable.addCell(headerPP3);
            ppTable.setHeaderRows(1);

            parkingPData.forEach((pp) -> {
                ppTable.addCell(new Phrase(new Chunk(pp.getParkingPlaceID().toString(), ordinaryFont)));
                ppTable.addCell(new Phrase(new Chunk(pp.getParkingID().toString(), ordinaryFont)));
                ppTable.addCell(new Phrase(new Chunk(pp.getParkingPlaceNumber().toString(), ordinaryFont)));
            });

            document.add(ppTable);

            document.close();
            file.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void findRealEstatePrintReport() {
        HouseholderData hData = new HouseholderData(
                tempHouseholderID,
                tempHouseholderFirstName,
                tempHouseholderLastName,
                tempHouseholderDateOfBirth,
                tempHouseholderGender,
                tempHouseholderContact,
                tempHouseholderEmail
        );

        DirectoryChooser directoryChooser = new DirectoryChooser();
        String dirPath = directoryChooser.getPathFromDirectoryChooser(findRealEstatePrintReport.getScene().getWindow());
        String pdfPath = generatePDFName(dirPath, hData.getFirstName() + hData.getLastName());
        generatePDF(pdfPath, hData, rpListData, ppListData);
        alert.successMessage("PDF was successfully generated. Find as: " + pdfPath);
    }
    // endregion

    public void switchForm(ActionEvent event) {
        if (event.getSource() == manageBtnRealEstate) {
            realEstateForm.setVisible(true);
            ownersForm.setVisible(false);
            ownershipForm.setVisible(false);
            parkingForm.setVisible(false);
            haumcForm.setVisible(false);
            buildingsForm.setVisible(false);
            streetsForm.setVisible(false);
            findOwnerForm.setVisible(false);
            findRealEstateForm.setVisible(false);
            findOwnerFoundForm.setVisible(false);
            findRealEstateResults.setVisible(false);

            residentialPropertyDisplayData();
        } else if (event.getSource() == manageBtnOwners) {
            realEstateForm.setVisible(false);
            ownersForm.setVisible(true);
            ownershipForm.setVisible(false);
            parkingForm.setVisible(false);
            haumcForm.setVisible(false);
            buildingsForm.setVisible(false);
            streetsForm.setVisible(false);
            findOwnerForm.setVisible(false);
            findRealEstateForm.setVisible(false);
            findOwnerFoundForm.setVisible(false);
            findRealEstateResults.setVisible(false);

            householderDisplayData();
        } else if (event.getSource() == manageBtnOwnership) {
            realEstateForm.setVisible(false);
            ownersForm.setVisible(false);
            ownershipForm.setVisible(true);
            parkingForm.setVisible(false);
            haumcForm.setVisible(false);
            buildingsForm.setVisible(false);
            streetsForm.setVisible(false);
            findOwnerForm.setVisible(false);
            findRealEstateForm.setVisible(false);
            findOwnerFoundForm.setVisible(false);
            findRealEstateResults.setVisible(false);

            rpmDisplayData();
            parkingPDisplayData();
        } else if (event.getSource() == manageBtnParking) {
            realEstateForm.setVisible(false);
            ownersForm.setVisible(false);
            ownershipForm.setVisible(false);
            parkingForm.setVisible(true);
            haumcForm.setVisible(false);
            buildingsForm.setVisible(false);
            streetsForm.setVisible(false);
            findOwnerForm.setVisible(false);
            findRealEstateForm.setVisible(false);
            findOwnerFoundForm.setVisible(false);
            findRealEstateResults.setVisible(false);

            parkingDisplayData();
        } else if (event.getSource() == manageBtnHAUMC) {
            realEstateForm.setVisible(false);
            ownersForm.setVisible(false);
            ownershipForm.setVisible(false);
            parkingForm.setVisible(false);
            haumcForm.setVisible(true);
            buildingsForm.setVisible(false);
            streetsForm.setVisible(false);
            findOwnerForm.setVisible(false);
            findRealEstateForm.setVisible(false);
            findOwnerFoundForm.setVisible(false);
            findRealEstateResults.setVisible(false);

            haumcDisplayData();
        } else if (event.getSource() == manageBtnBuildings) {
            realEstateForm.setVisible(false);
            ownersForm.setVisible(false);
            ownershipForm.setVisible(false);
            parkingForm.setVisible(false);
            haumcForm.setVisible(false);
            buildingsForm.setVisible(true);
            streetsForm.setVisible(false);
            findOwnerForm.setVisible(false);
            findRealEstateForm.setVisible(false);
            findOwnerFoundForm.setVisible(false);
            findRealEstateResults.setVisible(false);

            buildingsDisplayData();
        } else if (event.getSource() == manageBtnStreets) {
            realEstateForm.setVisible(false);
            ownersForm.setVisible(false);
            ownershipForm.setVisible(false);
            parkingForm.setVisible(false);
            haumcForm.setVisible(false);
            buildingsForm.setVisible(false);
            streetsForm.setVisible(true);
            findOwnerForm.setVisible(false);
            findRealEstateForm.setVisible(false);
            findOwnerFoundForm.setVisible(false);
            findRealEstateResults.setVisible(false);

            streetsDisplayData();
        } else if (event.getSource() == findBtnOwner) {
            realEstateForm.setVisible(false);
            ownersForm.setVisible(false);
            ownershipForm.setVisible(false);
            parkingForm.setVisible(false);
            haumcForm.setVisible(false);
            buildingsForm.setVisible(false);
            streetsForm.setVisible(false);
            findOwnerForm.setVisible(true);
            findRealEstateForm.setVisible(false);
            findOwnerFoundForm.setVisible(false);
            findRealEstateResults.setVisible(false);
        } else if (event.getSource() == findBtnRealEstate) {
            realEstateForm.setVisible(false);
            ownersForm.setVisible(false);
            ownershipForm.setVisible(false);
            parkingForm.setVisible(false);
            haumcForm.setVisible(false);
            buildingsForm.setVisible(false);
            streetsForm.setVisible(false);
            findOwnerForm.setVisible(false);
            findRealEstateForm.setVisible(true);
            findOwnerFoundForm.setVisible(false);
            findRealEstateResults.setVisible(false);
        }
    }

    public void signOutByBtn() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/LoginRegistration.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("MicroDistrict management system");
        stage.setScene(scene);
        stage.show();

        signOutBtn.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        realEstateForm.setVisible(true);
        residentialPropertyDisplayData();
        ownersForm.setVisible(false);
        ownershipForm.setVisible(false);
        parkingForm.setVisible(false);
        haumcForm.setVisible(false);
        buildingsForm.setVisible(false);
        streetsForm.setVisible(false);
        findOwnerForm.setVisible(false);
        findRealEstateForm.setVisible(false);
        greet_username.setText("Welcome, " + userName);
    }
}
