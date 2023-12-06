package impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import impl.helpClasses.AlertMessage;
import impl.helpClasses.Database;
import impl.helpClasses.DirectoryChooser;
import impl.helpClasses.forTableviews.HAUMCForHouseholderMainForm;
import impl.helpClasses.forTableviews.PPForHouseholderMainForm;
import impl.helpClasses.forTableviews.RPForHouseholderMainForm;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class HouseholderMainFormController implements Initializable {

    @FXML
    private Label greet_username;

    @FXML
    private Label labelFirstName;

    @FXML
    private Label labelLastName;

    @FXML
    private Label labelDate;

    @FXML
    private Label labelGender;

    @FXML
    private Label labelContact;

    @FXML
    private Label labelEmail;

    @FXML
    private Button reBtn;

    @FXML
    private Button haumcBtn;

    @FXML
    private Button signOutBtn;

    @FXML
    private VBox reForm;

    @FXML
    private TableView<RPForHouseholderMainForm> rpTableview;

    @FXML
    private TableColumn<RPForHouseholderMainForm, ?> rpColBuilding;

    @FXML
    private TableColumn<RPForHouseholderMainForm, ?> rpColStreet;

    @FXML
    private TableColumn<RPForHouseholderMainForm, ?> rpColType;

    @FXML
    private TableColumn<RPForHouseholderMainForm, ?> rpColSize;

    @FXML
    private TableColumn<RPForHouseholderMainForm, ?> rpColRoomCount;

    @FXML
    private TableView<PPForHouseholderMainForm> ppTableview;

    @FXML
    private TableColumn<PPForHouseholderMainForm, ?> ppColParkingNumber;

    @FXML
    private TableColumn<PPForHouseholderMainForm, ?> ppColBuilding;

    @FXML
    private TableColumn<PPForHouseholderMainForm, ?> ppColStreet;

    @FXML
    private Button reGetReport;

    @FXML
    private Button haumcGetReport;

    @FXML
    private VBox haumcForm;

    @FXML
    private TableView<HAUMCForHouseholderMainForm> haumcTableView;

    @FXML
    private TableColumn<HAUMCForHouseholderMainForm, String> haumcName;

    @FXML
    private TableColumn<HAUMCForHouseholderMainForm, String> haumcDescription;

    @FXML
    private TableColumn<HAUMCForHouseholderMainForm, String> haumcContact;

    @FXML
    private TableColumn<HAUMCForHouseholderMainForm, String> haumcType;

    @FXML
    private TableColumn<HAUMCForHouseholderMainForm, String> haumcRealEstateType;

    @FXML
    private TableColumn<HAUMCForHouseholderMainForm, String> haumcBuilding;

    @FXML
    private TableColumn<HAUMCForHouseholderMainForm, String> haumcStreet;

    @FXML
    private VBox haumcResultForm;

    @FXML
    private Label findRealEstateLabelFoundRP;

    @FXML
    private Label haumcLabelResult;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private final AlertMessage alert = new AlertMessage();

    private static String username;
    private static Integer ID;
    private static String firstName;
    private static String lastName;
    private static Date dateOfBirth;
    private static String gender;
    private static String contact;
    private static String email;

    public static void setUserName(String login) {
        username = login;
    }

    public static void setID(Integer id) {
        ID = id;

        String selectIDData = "select * from \"OwnersOfResidentialProperty\" where \"OwnerID\" = '" + ID + "'";

        Connection connect = Database.connectDB();
        try {
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery(selectIDData);
            result.next();
            firstName = result.getString("FirstName");
            lastName = result.getString("LastName");
            dateOfBirth = result.getDate("DateOfBirth");
            gender = result.getString("Gender");
            contact = result.getString("ContactNumber");
            email = result.getString("Email");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generatePDFName(String dirName) {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy-h_mm_ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        String formattedDate = sdf.format(date);
        return dirName + File.separator + firstName + lastName + "-" + formattedDate + ".pdf";
    }
    // region Real Estate
    private ObservableList<RPForHouseholderMainForm> rpListData;

    private ObservableList<RPForHouseholderMainForm> rpData() {
        rpListData = FXCollections.observableArrayList();

        String selectData = "select \"BuildingPhysicalNumber\", \"StreetName\", \"Type\", \"Size\", \"RoomCount\" from \"ResidentialProperty\"\n" +
                "inner join public.\"Buildings\" B on B.\"BuildingID\" = \"ResidentialProperty\".\"BuildingID\"\n" +
                "inner join public.\"Streets\" S on S.\"StreetID\" = B.\"StreetID\"\n" +
                "inner join public.\"Ownerships\" O on \"ResidentialProperty\".\"ResidentialPropertyID\" = O.\"ResidentialPropertyID\"\n" +
                "where \"OwnerID\" = '" + ID + "'";

        connect = Database.connectDB();

        RPForHouseholderMainForm data;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                data = new RPForHouseholderMainForm(
                        result.getInt("BuildingPhysicalNumber"),
                        result.getString("StreetName"),
                        result.getString("Type"),
                        result.getInt("Size"),
                        result.getInt("RoomCount")
                );
                rpListData.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return rpListData;
    }

    public void rpDisplayData() {
        ObservableList<RPForHouseholderMainForm> listData = rpData();

        rpColBuilding.setCellValueFactory(new PropertyValueFactory<>("BuildingPhysicalNumber"));
        rpColStreet.setCellValueFactory(new PropertyValueFactory<>("StreetName"));
        rpColType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        rpColSize.setCellValueFactory(new PropertyValueFactory<>("Size"));
        rpColRoomCount.setCellValueFactory(new PropertyValueFactory<>("RoomCount"));

        rpTableview.setItems(listData);
    }

    private ObservableList<PPForHouseholderMainForm> ppListData;

    private ObservableList<PPForHouseholderMainForm> ppData() {
        ppListData = FXCollections.observableArrayList();

        String selectData = "select \"ParkingPlaceNumber\", \"BuildingPhysicalNumber\", \"StreetName\" from \"ParkingPlaces\"\n" +
                "inner join public.\"Parking\" P on P.\"ParkingID\" = \"ParkingPlaces\".\"ParkingID\"\n" +
                "inner join public.\"Buildings\" B on B.\"BuildingID\" = P.\"BuildingID\"\n" +
                "inner join public.\"Streets\" S on S.\"StreetID\" = B.\"StreetID\"\n" +
                "where \"OwnerID\" = '" + ID + "'";

        connect = Database.connectDB();

        PPForHouseholderMainForm data;

        try {
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                data = new PPForHouseholderMainForm(
                        result.getInt("ParkingPlaceNumber"),
                        result.getInt("BuildingPhysicalNumber"),
                        result.getString("StreetName")
                );
                ppListData.add(data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ppListData;
    }

    public void ppDisplayData() {
        ObservableList<PPForHouseholderMainForm> listData = ppData();

        ppColBuilding.setCellValueFactory(new PropertyValueFactory<>("BuildingPhysicalNumber"));
        ppColStreet.setCellValueFactory(new PropertyValueFactory<>("StreetName"));
        ppColParkingNumber.setCellValueFactory(new PropertyValueFactory<>("ParkingPlaceNumber"));

        ppTableview.setItems(listData);
    }

    private void reGeneratePDF(String path) {
        try {
            OutputStream file = new FileOutputStream(path);

            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            document.addCreationDate();

            com.itextpdf.text.Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            com.itextpdf.text.Font additionalFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
            com.itextpdf.text.Font ordinaryFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

            document.add(new Paragraph(new java.util.Date().toString(), ordinaryFont));

            String titleString = "ID: " + ID + " " +
                    firstName + " " + lastName + System.lineSeparator();
            document.add(new Paragraph(titleString, headerFont));
            String additionalString = "Date of birth: " + dateOfBirth + System.lineSeparator() +
                    "Gender: " + gender + System.lineSeparator() +
                    "Contact phone number: " + contact + System.lineSeparator() +
                    "Email: " + email + System.lineSeparator();
            document.add(new Paragraph(additionalString, additionalFont));

            document.add(new Paragraph("Owned residential property: ",
                    headerFont));
            document.add(new Paragraph(" "));
            PdfPTable rpTable = new PdfPTable(5);
            PdfPCell header1 = new PdfPCell(new Phrase(new Chunk("Building physical number", ordinaryFont)));
            PdfPCell header2 = new PdfPCell(new Phrase(new Chunk("Street", ordinaryFont)));
            PdfPCell header3 = new PdfPCell(new Phrase(new Chunk("Type", ordinaryFont)));
            PdfPCell header4 = new PdfPCell(new Phrase(new Chunk("Size", ordinaryFont)));
            PdfPCell header5 = new PdfPCell(new Phrase(new Chunk("Room count", ordinaryFont)));
            header1.setHorizontalAlignment(Element.ALIGN_CENTER);
            header2.setHorizontalAlignment(Element.ALIGN_CENTER);
            header3.setHorizontalAlignment(Element.ALIGN_CENTER);
            header4.setHorizontalAlignment(Element.ALIGN_CENTER);
            header5.setHorizontalAlignment(Element.ALIGN_CENTER);
            rpTable.addCell(header1);
            rpTable.addCell(header2);
            rpTable.addCell(header3);
            rpTable.addCell(header4);
            rpTable.addCell(header5);
            rpTable.setHeaderRows(1);

            rpListData.forEach((rp) -> {
                rpTable.addCell(new Phrase(new Chunk(rp.getBuildingPhysicalNumber().toString(), ordinaryFont)));
                rpTable.addCell(new Phrase(new Chunk(rp.getStreetName(), ordinaryFont)));
                rpTable.addCell(new Phrase(new Chunk(rp.getType(), ordinaryFont)));
                rpTable.addCell(new Phrase(new Chunk(rp.getSize().toString(), ordinaryFont)));
                rpTable.addCell(new Phrase(new Chunk(rp.getRoomCount().toString(), ordinaryFont)));
            });

            document.add(rpTable);

            document.add(new Paragraph("Owned parking places: ",
                    headerFont));
            document.add(new Paragraph(" "));
            PdfPTable ppTable = new PdfPTable(3);
            PdfPCell ppHeader1 = new PdfPCell(new Phrase(new Chunk("Number", ordinaryFont)));
            PdfPCell ppHeader2 = new PdfPCell(new Phrase(new Chunk("Parking next to building", ordinaryFont)));
            PdfPCell ppHeader3 = new PdfPCell(new Phrase(new Chunk("Street", ordinaryFont)));
            ppHeader1.setHorizontalAlignment(Element.ALIGN_CENTER);
            ppHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
            ppHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
            ppTable.addCell(ppHeader1);
            ppTable.addCell(ppHeader2);
            ppTable.addCell(ppHeader3);
            ppTable.setHeaderRows(1);

            ppListData.forEach((pp) -> {
                ppTable.addCell(new Phrase(new Chunk(pp.getParkingPlaceNumber().toString(), ordinaryFont)));
                ppTable.addCell(new Phrase(new Chunk(pp.getBuildingPhysicalNumber().toString(), ordinaryFont)));
                ppTable.addCell(new Phrase(new Chunk(pp.getStreetName(), ordinaryFont)));
            });

            document.add(ppTable);
            document.close();
            file.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void reGetReportbyBtn() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String dirPath = directoryChooser.getPathFromDirectoryChooser(reGetReport.getScene().getWindow());
        String pdfPath = generatePDFName(dirPath);
        reGeneratePDF(pdfPath);
        alert.successMessage("PDF was successfully generated. Find as: " + pdfPath);
    }
    // endregion

    // region HAUMC
    private ObservableList<HAUMCForHouseholderMainForm> haumcListData;

    private ObservableList<HAUMCForHouseholderMainForm> haumcData() {
        haumcListData = FXCollections.observableArrayList();

        connect = Database.connectDB();

        HAUMCForHouseholderMainForm data;

        try {
            String selectData = "select \"Name\", \"Description\", \"ContactPhone\", haumc.\"Type\", \"BuildingPhysicalNumber\", \"StreetName\" from haumc\n" +
                    "inner join public.\"Buildings\" B on haumc.haumcid = B.haumcid\n" +
                    "inner join public.\"Streets\" S on S.\"StreetID\" = B.\"StreetID\"\n" +
                    "inner join public.\"Parking\" P on B.\"BuildingID\" = P.\"BuildingID\"\n" +
                    "inner join public.\"ParkingPlaces\" PP on P.\"ParkingID\" = PP.\"ParkingID\"\n" +
                    "where \"OwnerID\" = '" + ID + "'";

            connect = Database.connectDB();
            statement = connect.createStatement();
            result = statement.executeQuery(selectData);

            int counter = 0;
            while (result.next()) {
                data = new HAUMCForHouseholderMainForm(
                        result.getString("Name"),
                        result.getString("Description"),
                        result.getString("ContactPhone"),
                        result.getString("Type"),
                        "Parking place",
                        result.getInt("BuildingPhysicalNumber"),
                        result.getString("StreetName")
                );
                haumcListData.add(data);
                ++counter;
            }

            selectData = "select \"Name\", \"Description\", \"ContactPhone\", haumc.\"Type\", \"BuildingPhysicalNumber\", \"StreetName\" from haumc\n" +
                    "inner join public.\"Buildings\" B on haumc.haumcid = B.haumcid\n" +
                    "inner join public.\"Streets\" S on S.\"StreetID\" = B.\"StreetID\"\n" +
                    "inner join public.\"ResidentialProperty\" RP on B.\"BuildingID\" = RP.\"BuildingID\"\n" +
                    "inner join public.\"Ownerships\" O on RP.\"ResidentialPropertyID\" = O.\"ResidentialPropertyID\"\n" +
                    "where \"OwnerID\" = '" + ID + "'";

            result = statement.executeQuery(selectData);
            while (result.next()) {
                data = new HAUMCForHouseholderMainForm(
                        result.getString("Name"),
                        result.getString("Description"),
                        result.getString("ContactPhone"),
                        result.getString("Type"),
                        "Residential property",
                        result.getInt("BuildingPhysicalNumber"),
                        result.getString("StreetName")
                );
                ++counter;
                haumcListData.add(data);
            }
            haumcResultForm.setVisible(true);
            haumcLabelResult.setText("Found " + counter + " notes");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return haumcListData;
    }

    public void haumcDisplayData() {
        ObservableList<HAUMCForHouseholderMainForm> listData = haumcData();

        haumcName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        haumcDescription.setCellValueFactory(new PropertyValueFactory<>("StreetName"));
        haumcContact.setCellValueFactory(new PropertyValueFactory<>("ContactPhone"));
        haumcType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        haumcRealEstateType.setCellValueFactory(new PropertyValueFactory<>("reType"));
        haumcBuilding.setCellValueFactory(new PropertyValueFactory<>("BuildingPhysicalNumber"));
        haumcStreet.setCellValueFactory(new PropertyValueFactory<>("StreetName"));

        haumcTableView.setItems(listData);
    }

    private void haumcGeneratePDF(String path) {
        try {
            OutputStream file = new FileOutputStream(path);

            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            document.addCreationDate();

            com.itextpdf.text.Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            com.itextpdf.text.Font additionalFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
            com.itextpdf.text.Font ordinaryFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

            document.add(new Paragraph(new java.util.Date().toString(), ordinaryFont));

            String titleString = "ID: " + ID + " " +
                    firstName + " " + lastName + System.lineSeparator();
            document.add(new Paragraph(titleString, headerFont));
            String additionalString = "Date of birth: " + dateOfBirth + System.lineSeparator() +
                    "Gender: " + gender + System.lineSeparator() +
                    "Contact phone number: " + contact + System.lineSeparator() +
                    "Email: " + email + System.lineSeparator();
            document.add(new Paragraph(additionalString, additionalFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("House & utility management companies by residential property: ",
                    headerFont));
            String selectData = "select RP.\"ResidentialPropertyID\", \"BuildingPhysicalNumber\", \"StreetName\"\n" +
                    "from \"ResidentialProperty\" as RP\n" +
                    "         inner join public.\"Buildings\" B on B.\"BuildingID\" = RP.\"BuildingID\"\n" +
                    "         inner join public.\"Streets\" S on B.\"StreetID\" = S.\"StreetID\"\n" +
                    "         inner join public.\"Ownerships\" O on RP.\"ResidentialPropertyID\" = O.\"ResidentialPropertyID\"\n" +
                    "where O.\"OwnerID\" = '" + ID + "'";

            connect = Database.connectDB();
            statement = connect.createStatement();
            result = statement.executeQuery(selectData);

            while (result.next()) {
                String toParagraph = "Residential property ID: " + result.getInt("ResidentialPropertyID") + System.lineSeparator()
                        + "Building physical number: " + result.getInt("BuildingPhysicalNumber") + " \t " +
                        "Street: " + result.getString("StreetName") + System.lineSeparator();
                Paragraph singleNoteParagraph = new Paragraph(toParagraph, ordinaryFont);

                String anotherSelectData = "select \"Name\", \"Description\", \"ContactPhone\", \"Type\" from haumc " +
                        "where haumcid in (select haumcid from \"Buildings\" where \"BuildingID\" in\n" +
                        "(select \"BuildingID\" from \"ResidentialProperty\" where \"ResidentialPropertyID\" = '" + result.getInt("ResidentialPropertyID") + "'));";

                Statement anotherStatement = connect.createStatement();
                ResultSet anotherResult = anotherStatement.executeQuery(anotherSelectData);
                anotherResult.next();
                String anotherToParagraph = "Housing & utility management company:" + System.lineSeparator() +
                        "Name: " + anotherResult.getString("Name") + "\t" + "Type: " + anotherResult.getString("Type") +
                        System.lineSeparator() + "Description: " + anotherResult.getString("Description") +
                        System.lineSeparator() + "Contact number: " + anotherResult.getString("ContactPhone") + System.lineSeparator();
                singleNoteParagraph.add(anotherToParagraph);
                document.add(singleNoteParagraph);
                document.add(new Paragraph(" "));
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("House & utility management companies by parking places: ",
                    headerFont));
            selectData = "select \"ParkingPlaces\".\"ParkingPlaceID\", \"ParkingPlaceNumber\", \"BuildingPhysicalNumber\", \"StreetName\"\n" +
                    "from \"ParkingPlaces\"\n" +
                    "         inner join public.\"Parking\" P on P.\"ParkingID\" = \"ParkingPlaces\".\"ParkingID\"\n" +
                    "         inner join public.\"Buildings\" B on B.\"BuildingID\" = P.\"BuildingID\"\n" +
                    "         inner join public.\"Streets\" S on S.\"StreetID\" = B.\"StreetID\"\n" +
                    "where \"OwnerID\" = '" + ID + "'";

            statement = connect.createStatement();
            result = statement.executeQuery(selectData);

            while (result.next()) {
                String toParagraph = "Parking place number: " + result.getInt("ParkingPlaceNumber") + System.lineSeparator()
                        + "Building physical number: " + result.getInt("BuildingPhysicalNumber") + " \t " +
                        "Street: " + result.getString("StreetName") + System.lineSeparator();
                Paragraph singleNoteParagraph = new Paragraph(toParagraph, ordinaryFont);

                String anotherSelectData = "select \"Name\", \"Description\", \"ContactPhone\", \"Type\"\n" +
                        "from haumc\n" +
                        "where haumcid in (select haumcid\n" +
                        "                  from \"Buildings\"\n" +
                        "                  where \"BuildingID\" in\n" +
                        "                        (select \"BuildingID\"\n" +
                        "                         from \"Parking\"\n" +
                        "                         where \"ParkingID\" in (select \"ParkingID\" from \"ParkingPlaces\" " +
                        "where \"ParkingPlaceID\" = '" + result.getInt("ParkingPlaceID") + "')))";

                Statement anotherStatement = connect.createStatement();
                ResultSet anotherResult = anotherStatement.executeQuery(anotherSelectData);
                anotherResult.next();
                String anotherToParagraph = "Housing & utility management company:" + System.lineSeparator() +
                        "Name: " + anotherResult.getString("Name") + "\t" + "Type: " + anotherResult.getString("Type") +
                        System.lineSeparator() + "Description: " + anotherResult.getString("Description") +
                        System.lineSeparator() + "Contact number: " + anotherResult.getString("ContactPhone") + System.lineSeparator();
                singleNoteParagraph.add(anotherToParagraph);
                document.add(singleNoteParagraph);
                document.add(new Paragraph(" "));
            }

            document.close();
            file.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void haumcGetReportByBtn() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String dirPath = directoryChooser.getPathFromDirectoryChooser(haumcGetReport.getScene().getWindow());
        String pdfPath = generatePDFName(dirPath);
        haumcGeneratePDF(pdfPath);
        alert.successMessage("PDF was successfully generated. Find as: " + pdfPath);
    }
    // endregion

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

    private void setReFormVisible(boolean boo) {
        reForm.setVisible(boo);
        if (boo) {
            rpDisplayData();
            ppDisplayData();
        }
    }

    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == reBtn) {
            setReFormVisible(true);
            haumcForm.setVisible(false);
            haumcResultForm.setVisible(false);
        } else if (event.getSource() == haumcBtn) {
            reForm.setVisible(false);
            haumcForm.setVisible(true);
            haumcResultForm.setVisible(false);
            haumcDisplayData();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setReFormVisible(true);
        haumcForm.setVisible(false);
        haumcResultForm.setVisible(false);
        greet_username.setText("Welcome, " + username);
        labelFirstName.setText(firstName);
        labelLastName.setText(lastName);
        labelDate.setText(dateOfBirth.toString());
        labelGender.setText(gender);
        labelContact.setText(contact);
        labelEmail.setText(email);
    }
}
