package impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import impl.helpClasses.AlertMessage;
import impl.helpClasses.Database;
import impl.helpClasses.DirectoryChooser;
import impl.helpClasses.forTableviews.HouseholderData;
import impl.helpClasses.forTableviews.ServicedBuildings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class HAUMCMainFormController implements Initializable {

    @FXML
    private Button signOutBtn;

    @FXML
    private Label greet_username;

    @FXML
    private Button haumcServicedBuildingsBtn;

    @FXML
    private Button haumcClientsBtn;

    @FXML
    private VBox servicedForm;

    @FXML
    private TableView<ServicedBuildings> servicedTableview;

    @FXML
    private TableColumn<ServicedBuildings, String> servicedColPhysicalNumber;

    @FXML
    private TableColumn<ServicedBuildings, String> servicedColStreetName;

    @FXML
    private TableColumn<ServicedBuildings, String> servicedColFloorsCount;

    @FXML
    private Button servicedGetReport;

    @FXML
    private VBox findRealEstateForm;

    @FXML
    private VBox clientsForm;

    @FXML
    private TableView<HouseholderData> clientsTableview;

    @FXML
    private TableColumn<HouseholderData, String> clientsColFirstName;

    @FXML
    private TableColumn<HouseholderData, String> clientsColLastName;

    @FXML
    private TableColumn<HouseholderData, String> clientsColDateOfBirth;

    @FXML
    private TableColumn<HouseholderData, String> clientsColGender;

    @FXML
    private TableColumn<HouseholderData, String> clientsColContactNumber;

    @FXML
    private TableColumn<HouseholderData, String> clientsColEmail;

    @FXML
    private AnchorPane findRealEstateResults;

    @FXML
    private Label findRealEstateLabelFoundRP;

    @FXML
    private Label clientsLabel;

    @FXML
    private Button clientGetReport;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private final AlertMessage alert = new AlertMessage();

    private static String userName;
    private static Integer HAUMCID;
    private static String Name;
    private static String Description;
    private static String ContactPhone;
    private static String Type;


    public static void setUserName(String login) {
        userName = login;
    }

    public static void setHAUMCID(Integer haumcid) {
        HAUMCID = haumcid;

        String selectIDData = "select * from haumc where haumcid = '" + HAUMCID + "'";

        Connection connect = Database.connectDB();
        try {
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery(selectIDData);
            result.next();
            Name = result.getString("Name");
            Description = result.getString("Description");
            ContactPhone = result.getString("ContactPhone");
            Type = result.getString("Type");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // region Serviced buildings
    private ObservableList<ServicedBuildings> servicedListData;

    private ObservableList<ServicedBuildings> servicedBuildingsData() {
        servicedListData = FXCollections.observableArrayList();

        String selectData = "select \"StreetName\", \"BuildingPhysicalNumber\", \"FloorsCount\"\n" +
                "from \"Buildings\" as B\n" +
                "         inner join public.\"Streets\" S on B.\"StreetID\" = S.\"StreetID\"\n" +
                "where B.haumcid = '" + HAUMCID + "'\n" +
                "order by \"StreetName\"";

        connect = Database.connectDB();

        ServicedBuildings data;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                data = new ServicedBuildings(
                        result.getInt("BuildingPhysicalNumber"),
                        result.getInt("FloorsCount"),
                        result.getString("StreetName")
                );
                servicedListData.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return servicedListData;
    }

    public void buildingsDisplayData() {
        ObservableList<ServicedBuildings> listData = servicedBuildingsData();

        servicedColStreetName.setCellValueFactory(new PropertyValueFactory<>("StreetName"));
        servicedColPhysicalNumber.setCellValueFactory(new PropertyValueFactory<>("BuildingPhysicalNumber"));
        servicedColFloorsCount.setCellValueFactory(new PropertyValueFactory<>("FloorsCount"));

        servicedTableview.setItems(listData);
    }

    private String generatePDFName(String dirName) {
        java.util.Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy-h_mm_ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        String formattedDate = sdf.format(date);
        return dirName + File.separator + Name.replace(" ", "") + "-" + formattedDate + ".pdf";
    }

    private void servicedGeneratePDF(String path) {
        try {
            OutputStream file = new FileOutputStream(path);

            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            document.addCreationDate();

            com.itextpdf.text.Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            com.itextpdf.text.Font additionalFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
            com.itextpdf.text.Font ordinaryFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

            document.add(new Paragraph(new Date().toString(), ordinaryFont));

            String titleString = "ID: " + HAUMCID + " " +
                    Name + System.lineSeparator();
            document.add(new Paragraph(titleString, headerFont));
            String additionalString = Description + System.lineSeparator() +
                    "Type: " + Type + System.lineSeparator() +
                    "Contact phone number: " + ContactPhone + System.lineSeparator();
            document.add(new Paragraph(additionalString, additionalFont));

            document.add(new Paragraph("Serviced buildings: ",
                    headerFont));
            document.add(new Paragraph(" "));
            PdfPTable servicedTable = new PdfPTable(3);
            PdfPCell header1 = new PdfPCell(new Phrase(new Chunk("Street", ordinaryFont)));
            PdfPCell header2 = new PdfPCell(new Phrase(new Chunk("Building physical number", ordinaryFont)));
            PdfPCell header3 = new PdfPCell(new Phrase(new Chunk("Floors count", ordinaryFont)));
            header1.setHorizontalAlignment(Element.ALIGN_CENTER);
            header2.setHorizontalAlignment(Element.ALIGN_CENTER);
            header3.setHorizontalAlignment(Element.ALIGN_CENTER);
            servicedTable.addCell(header1);
            servicedTable.addCell(header2);
            servicedTable.addCell(header3);
            servicedTable.setHeaderRows(1);

            servicedListData.forEach((serviced) -> {
                servicedTable.addCell(new Phrase(new Chunk(serviced.getStreetName(), ordinaryFont)));
                servicedTable.addCell(new Phrase(new Chunk(serviced.getBuildingPhysicalNumber().toString(), ordinaryFont)));
                servicedTable.addCell(new Phrase(new Chunk(serviced.getFloorsCount().toString(), ordinaryFont)));
            });
            document.add(servicedTable);
            document.close();
            file.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void servicedGetReportByButton() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String dirPath = directoryChooser.getPathFromDirectoryChooser(servicedGetReport.getScene().getWindow());
        String pdfPath = generatePDFName(dirPath);
        servicedGeneratePDF(pdfPath);
        alert.successMessage("PDF was successfully generated. Find as: " + pdfPath);
    }

    // endregion

    // region Clients
    private ObservableList<HouseholderData> clientListData;
    public ObservableList<HouseholderData> clientsData() {
        clientListData = FXCollections.observableArrayList();

        String selectData = "select \"FirstName\", \"LastName\", \"DateOfBirth\", \"Gender\", \"ContactNumber\", \"Email\"\n" +
                "from \"OwnersOfResidentialProperty\"\n" +
                "where \"OwnerID\" in (select \"OwnerID\"\n" +
                "                    from \"Ownerships\"\n" +
                "                    where \"ResidentialPropertyID\" in (select \"ResidentialPropertyID\"\n" +
                "                                                      from \"ResidentialProperty\"\n" +
                "                                                      where \"BuildingID\" in (select \"BuildingID\"\n" +
                "                                                                             from \"Buildings\"\n" +
                "                                                                             where haumcid = '" + HAUMCID + "')))\n" +
                "union\n" +
                "select \"FirstName\", \"LastName\", \"DateOfBirth\", \"Gender\", \"ContactNumber\", \"Email\"\n" +
                "from \"OwnersOfResidentialProperty\"\n" +
                "where \"OwnerID\" in (select \"OwnerID\"\n" +
                "                    from \"ParkingPlaces\"\n" +
                "                    where \"ParkingID\" in (select \"ParkingID\"\n" +
                "                                          from \"Parking\"\n" +
                "                                          where \"BuildingID\" in (select \"BuildingID\"\n" +
                "                                                                 from \"Buildings\"\n" +
                "                                                                 where haumcid = '" + HAUMCID + "')))";

        connect = Database.connectDB();

        HouseholderData data;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            int clientCounter = 0;

            while (result.next()) {
                data = new HouseholderData(
                        0,
                        result.getString("FirstName"),
                        result.getString("LastName"),
                        result.getDate("DateOfBirth"),
                        result.getString("Gender"),
                        result.getString("ContactNumber"),
                        result.getString("Email")
                );
                clientListData.add(data);
                ++clientCounter;
            }
            clientsLabel.setText("Found " + clientCounter + " clients");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return clientListData;
    }

    public void clientsDisplayData() {
        ObservableList<HouseholderData> listData = clientsData();

        clientsColFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        clientsColLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        clientsColDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));
        clientsColGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        clientsColContactNumber.setCellValueFactory(new PropertyValueFactory<>("ContactNumber"));
        clientsColEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        clientsTableview.setItems(listData);
    }

    private void clientGeneratePDF(String path) {
        try {
            OutputStream file = new FileOutputStream(path);

            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            document.addCreationDate();

            com.itextpdf.text.Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            com.itextpdf.text.Font additionalFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
            com.itextpdf.text.Font ordinaryFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

            document.add(new Paragraph(new Date().toString(), ordinaryFont));

            String titleString = "ID: " + HAUMCID + " " +
                    Name + System.lineSeparator();
            document.add(new Paragraph(titleString, headerFont));
            String additionalString = Description + System.lineSeparator() +
                    "Type: " + Type + System.lineSeparator() +
                    "Contact phone number: " + ContactPhone + System.lineSeparator();
            document.add(new Paragraph(additionalString, additionalFont));

            document.add(new Paragraph("Clients: ",
                    headerFont));
            document.add(new Paragraph(" "));
            PdfPTable clientsTable = new PdfPTable(6);
            PdfPCell header1 = new PdfPCell(new Phrase(new Chunk("First name", ordinaryFont)));
            PdfPCell header2 = new PdfPCell(new Phrase(new Chunk("Last name", ordinaryFont)));
            PdfPCell header3 = new PdfPCell(new Phrase(new Chunk("Date of birth", ordinaryFont)));
            PdfPCell header4 = new PdfPCell(new Phrase(new Chunk("Gender", ordinaryFont)));
            PdfPCell header5 = new PdfPCell(new Phrase(new Chunk("Contact number", ordinaryFont)));
            PdfPCell header6 = new PdfPCell(new Phrase(new Chunk("Email", ordinaryFont)));
            header1.setHorizontalAlignment(Element.ALIGN_CENTER);
            header2.setHorizontalAlignment(Element.ALIGN_CENTER);
            header3.setHorizontalAlignment(Element.ALIGN_CENTER);
            header4.setHorizontalAlignment(Element.ALIGN_CENTER);
            header5.setHorizontalAlignment(Element.ALIGN_CENTER);
            header6.setHorizontalAlignment(Element.ALIGN_CENTER);
            clientsTable.addCell(header1);
            clientsTable.addCell(header2);
            clientsTable.addCell(header3);
            clientsTable.addCell(header4);
            clientsTable.addCell(header5);
            clientsTable.addCell(header6);
            clientsTable.setHeaderRows(1);

            clientListData.forEach((client) -> {
                clientsTable.addCell(new Phrase(new Chunk(client.getFirstName(), ordinaryFont)));
                clientsTable.addCell(new Phrase(new Chunk(client.getLastName(), ordinaryFont)));
                clientsTable.addCell(new Phrase(new Chunk(client.getDateOfBirth().toString(), ordinaryFont)));
                clientsTable.addCell(new Phrase(new Chunk(client.getGender(), ordinaryFont)));
                clientsTable.addCell(new Phrase(new Chunk(client.getContactNumber(), ordinaryFont)));
                clientsTable.addCell(new Phrase(new Chunk(client.getEmail(), ordinaryFont)));
            });
            document.add(clientsTable);
            document.close();
            file.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clientGetReportByButton() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String dirPath = directoryChooser.getPathFromDirectoryChooser(clientGetReport.getScene().getWindow());
        String pdfPath = generatePDFName(dirPath);
        clientGeneratePDF(pdfPath);
        alert.successMessage("PDF was successfully generated. Find as: " + pdfPath);
    }
    // endregion

    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == haumcServicedBuildingsBtn) {
            servicedForm.setVisible(true);
            clientsForm.setVisible(false);
            buildingsDisplayData();
        } else if (event.getSource() == haumcClientsBtn) {
            servicedForm.setVisible(false);
            clientsForm.setVisible(true);
            clientsDisplayData();
        }
    }

    @FXML
    void signOutByBtn() {
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
        servicedForm.setVisible(true);
        buildingsDisplayData();
        clientsForm.setVisible(false);
        greet_username.setText("Welcome, " + userName);
    }
}
