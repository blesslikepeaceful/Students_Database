module sample.Students_Database {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires mysql.connector.j;


    opens sample.Students_Database to javafx.fxml;
    exports sample.Students_Database;
}