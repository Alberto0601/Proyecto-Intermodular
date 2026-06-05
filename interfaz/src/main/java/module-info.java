module org.example.interfaz {
    opens entidades;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires spring.security.crypto;

    opens org.example.interfaz to javafx.fxml;
    exports org.example.interfaz;
}