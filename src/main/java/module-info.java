module com.mycompany.proyectopoo2g2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.proyectopoo2g2 to javafx.fxml;
    exports com.mycompany.proyectopoo2g2;
}
