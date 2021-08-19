module com.mycompany.proyecto0g2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.proyecto0g2 to javafx.fxml;
    exports com.mycompany.proyecto0g2;
}
