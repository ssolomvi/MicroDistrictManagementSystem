package impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import impl.helpClasses.ListData;
import impl.helpClasses.Database;
import impl.helpClasses.AlertMessage;
import javafx.stage.Stage;

public class LoginRegistrationController implements Initializable {

    @FXML
    private AnchorPane login_form;

    @FXML
    private TextField login_username;

    @FXML
    private PasswordField login_password;

    @FXML
    private Button login_btn;

    @FXML
    private ComboBox<String> login_role;

    @FXML
    private AnchorPane admin_form;

    @FXML
    private TextField admin_username;

    @FXML
    private PasswordField admin_password;

    @FXML
    private Button admin_signupBtn;

    @FXML
    private Hyperlink admin_signin;

    @FXML
    private PasswordField admin_cPassword;

    @FXML
    private AnchorPane householder_form;

    @FXML
    private TextField householder_username;

    @FXML
    private PasswordField householder_password;

    @FXML
    private Button householder_signupBtn;

    @FXML
    private Hyperlink householder_signin;

    @FXML
    private PasswordField householder_cPassword;

    @FXML
    private TextField householder_id;

    @FXML
    private AnchorPane haumc_form;

    @FXML
    private TextField haumc_username;

    @FXML
    private PasswordField haumc_password;

    @FXML
    private Button haumc_signupBtn;

    @FXML
    private Hyperlink haumc_signin;

    @FXML
    private PasswordField haumc_cPassword;

    @FXML
    private TextField haumc_id;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private final AlertMessage alert = new AlertMessage();

    // region login
    public void loginAccount() {
        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Please, fill all blank fields");
            return;
        }

        String selectData = "select * from users where username = ? and password = ?";

        connect = Database.connectDB();
        try {
            prepare = connect.prepareStatement(selectData);
            prepare.setString(1, login_username.getText());
            prepare.setString(2, login_password.getText());

            result = prepare.executeQuery();

            if (!result.next()) {
                selectData = "select * from users where username = '" + login_username.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(selectData);

                if (!result.next()) {
                    alert.errorMessage("No user with username = " + login_username.getText());
                } else {
                    alert.errorMessage("Invalid password");
                }
            } else {
                String role = result.getString("role");
                switch (role) {
                    case "Admin":
                        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/AdminMainForm.fxml"));
                        AdminMainFormController.setUserName(result.getString("username"));

                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.setTitle("MicroDistrict management system | Admin Portal");
                        stage.setScene(scene);
                        stage.show();

                        login_btn.getScene().getWindow().hide();
                        break;
                    case "Householder":
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/HouseholderMainForm.fxml"));
                        HouseholderMainFormController.setUserName(result.getString("username"));
                        HouseholderMainFormController.setID(result.getInt("inner_id"));

                        scene = new Scene(fxmlLoader.load());
                        stage = new Stage();
                        stage.setTitle("MicroDistrict management system | Householder portal");
                        stage.setScene(scene);
                        stage.show();

                        login_btn.getScene().getWindow().hide();
                        break;
                    case "HAUMC":
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/HAUMCMainForm.fxml"));
                        HAUMCMainFormController.setUserName(result.getString("username"));
                        HAUMCMainFormController.setHAUMCID(result.getInt("inner_id"));

                        scene = new Scene(fxmlLoader.load());
                        stage = new Stage();
                        stage.setTitle("MicroDistrict management system | Housing & utility management company portal");
                        stage.setScene(scene);
                        stage.show();

                        login_btn.getScene().getWindow().hide();
                        break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            // thrown by .load()
            throw new RuntimeException(e);
        }
    }
// endregion


    // region Registration
    public void registerAdmin() {
        if (admin_username.getText().isEmpty()
                || admin_password.getText().isEmpty()
                || admin_cPassword.getText().isEmpty()) {
            alert.errorMessage("Please, fill all blank fields");
            return;
        }
        connect = Database.connectDB();

        String selectData = "select * from users where username = '" + admin_username.getText() + "'";
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(selectData);

            if (result.next()) {
                alert.errorMessage("User " + admin_username.getText() + " already exists");
            } else if (!admin_password.getText().equals(admin_cPassword.getText())) {
                alert.errorMessage("Password does not match");
            } else {
                String insertData = "insert into users (username, password, role) values(?, ?, ?)";
                prepare = connect.prepareStatement(insertData);
                prepare.setString(1, admin_username.getText());
                prepare.setString(2, admin_password.getText());
                prepare.setString(3, "Admin");

                prepare.executeUpdate();

                alert.successMessage("Registered successfully!");
                login_form.setVisible(true);
                admin_form.setVisible(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerHouseholder() {
        if (householder_username.getText().isEmpty()
                || householder_password.getText().isEmpty()
                || householder_cPassword.getText().isEmpty()
                || householder_id.getText().isEmpty()) {
            alert.errorMessage("Please, fill all blank fields");
            return;
        }

        String householderIDAsString = householder_id.getText();
        int householderIDAsInt;
        try {
            householderIDAsInt = Integer.parseInt(householderIDAsString);
        } catch (Exception e) {
            alert.errorMessage("ID must be a positive integer");
            return;
        }

        connect = Database.connectDB();

        String selectData = "select * from users where username = '" + householder_username.getText() + "'";
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(selectData);

            if (result.next()) {
                alert.errorMessage("User " + householder_username.getText() + " already exists. Choose another username");
            } else if (!householder_password.getText().equals(householder_cPassword.getText())) {
                alert.errorMessage("Password does not match");
            } else {
                String selectFromUsersWithSameID = "select * from users where inner_id = '" +
                        householderIDAsString + "' and role = 'Householder'" +
                        " and inner_id in (select \"OwnerID\" from \"OwnersOfResidentialProperty\")";
                statement = connect.createStatement();
                result = statement.executeQuery(selectFromUsersWithSameID);
                if (result.next()) {
                    alert.errorMessage("Householder with ID = " + householderIDAsString +
                            " has already been registered");
                } else {
                    String selectFromOwnersData = "select * from \"OwnersOfResidentialProperty\" where \"OwnerID\"" +
                            " = '" + householderIDAsString + "'";
                    statement = connect.createStatement();
                    result = statement.executeQuery(selectFromOwnersData);

                    if (!result.next()) {
                        alert.errorMessage("No householder with ID = " + householderIDAsString);
                    } else {
                        String insertData = "insert into users (username, password, role, inner_id) values(?, ?, ?, ?)";
                        prepare = connect.prepareStatement(insertData);
                        prepare.setString(1, householder_username.getText());
                        prepare.setString(2, householder_password.getText());
                        prepare.setString(3, "Householder");
                        prepare.setInt(4, householderIDAsInt);

                        prepare.executeUpdate();

                        alert.successMessage("Registered successfully!");
                        login_form.setVisible(true);
                        householder_form.setVisible(false);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerHAUMC() {
        if (haumc_username.getText().isEmpty()
                || haumc_password.getText().isEmpty()
                || haumc_cPassword.getText().isEmpty()
                || haumc_id.getText().isEmpty()) {
            alert.errorMessage("Please, fill all blank fields");
            return;
        }

        String haumcIDAsString = haumc_id.getText();
        int haumcIDAsInt;
        try {
            haumcIDAsInt = Integer.parseInt(haumcIDAsString);
        } catch (Exception e) {
            alert.errorMessage("ID must be a positive integer");
            return;
        }

        connect = Database.connectDB();

        String selectData = "select * from users where username = '" + haumc_username.getText() + "'";
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(selectData);

            if (result.next()) {
                alert.errorMessage("User " + haumc_username.getText() + " already exists");
            } else if (!haumc_password.getText().equals(haumc_cPassword.getText())) {
                alert.errorMessage("Password does not match");
            } else {
                String selectFromUsersWithSameID = "select * from users where inner_id = '" + haumcIDAsString
                        + "' and role = 'HAUMC' and inner_id in (select haumcid from haumc)";
                statement = connect.createStatement();
                result = statement.executeQuery(selectFromUsersWithSameID);
                if (result.next()) {
                    alert.errorMessage("Housing and utilities management company administrator with ID = " + haumcIDAsString +
                            " has already been registered");
                } else {
                    String selectFromHAUMCData = "select * from haumc where \"haumcid\"" +
                            " = '" + haumcIDAsString + "'";
                    statement = connect.createStatement();
                    result = statement.executeQuery(selectFromHAUMCData);

                    if (!result.next()) {
                        alert.errorMessage("No housing and utilities management company with ID = " + haumcIDAsString);
                    } else {
                        String insertData = "insert into users (username, password, role, inner_id) values(?, ?, ?, ?)";
                        prepare = connect.prepareStatement(insertData);
                        prepare.setString(1, haumc_username.getText());
                        prepare.setString(2, haumc_password.getText());
                        prepare.setString(3, "HAUMC");
                        prepare.setInt(4, haumcIDAsInt);

                        prepare.executeUpdate();

                        alert.successMessage("Registered successfully!");
                        login_form.setVisible(true);
                        haumc_form.setVisible(false);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
// endregion

    public void roleList() {
        List<String> listR = new ArrayList<>(Arrays.asList(ListData.role));
        ObservableList<String> listData = FXCollections.observableArrayList(listR);
        login_role.setItems(listData);
    }

    public void switchForm(ActionEvent event) {
        if (login_role.getSelectionModel().getSelectedItem().equals("Admin")) {
            admin_form.setVisible(true);
            householder_form.setVisible(false);
            haumc_form.setVisible(false);
        } else if (login_role.getSelectionModel().getSelectedItem().equals("Householder")) {
            admin_form.setVisible(false);
            householder_form.setVisible(true);
            haumc_form.setVisible(false);
        } else if (login_role.getSelectionModel().getSelectedItem().equals("HAUMC")) {
            admin_form.setVisible(false);
            householder_form.setVisible(false);
            haumc_form.setVisible(true);
        }
    }

    public void signInForm() {
        login_form.setVisible(true);
        admin_form.setVisible(false);
        householder_form.setVisible(false);
        haumc_form.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleList();
    }
}