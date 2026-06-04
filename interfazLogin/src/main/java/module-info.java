module org.example.interfazlogin {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.interfazlogin to javafx.fxml;
    exports org.example.interfazlogin;
}