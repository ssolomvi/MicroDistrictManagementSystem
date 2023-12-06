module impl {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires itextpdf;

    opens impl to javafx.base, javafx.graphics, javafx.fxml;
    opens impl.helpClasses to javafx.base, javafx.fxml, javafx.graphics;
    exports impl;
    exports impl.adminMainFormHelpClasses;
    opens impl.adminMainFormHelpClasses to javafx.base, javafx.fxml, javafx.graphics;
    opens impl.helpClasses.forTableviews to javafx.base, javafx.fxml, javafx.graphics;
}