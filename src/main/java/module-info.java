module com.goatnote.goatnote {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.goatnote.goatnote to javafx.fxml;
    exports com.goatnote.goatnote;
}