module hr.java.vjezbe.glavna {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;


    opens hr.java.vjezbe.glavna to javafx.fxml;
    exports hr.java.vjezbe.glavna;
}