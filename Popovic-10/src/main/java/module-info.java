module hr.java.vjezbe.glavna {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;


    opens hr.java.vjezbe.glavna to javafx.fxml;
    exports hr.java.vjezbe.glavna;
}