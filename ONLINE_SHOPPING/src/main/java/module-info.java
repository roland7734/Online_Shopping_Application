module com.example.online_shopping {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.postgresql.jdbc;
    requires java.sql;
    requires org.apache.commons.lang3;

    opens com.example.online_shopping to javafx.fxml;
    exports com.example.online_shopping;
    exports com.example.online_shopping.constants;
    opens com.example.online_shopping.constants to javafx.fxml;
    exports com.example.online_shopping.Services;
    opens com.example.online_shopping.Services to javafx.fxml;
    exports com.example.online_shopping.Controllers;
    opens com.example.online_shopping.Controllers to javafx.fxml;
    exports com.example.online_shopping.Listeners;
    opens com.example.online_shopping.Listeners to javafx.fxml;
}