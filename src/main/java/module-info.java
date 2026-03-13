module lk.ijse.woodceylon {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.desktop;
    requires java.logging;
    
    requires org.controlsfx.controls;

    opens lk.ijse.woodceylon to javafx.fxml;
    opens lk.ijse.woodceylon.controller to javafx.fxml;
    opens lk.ijse.woodceylon.dto to javafx.base;

    exports lk.ijse.woodceylon;
    exports lk.ijse.woodceylon.controller;
    requires net.sf.jasperreports.core;
//    requires lk.ijse.woodceylon;
    requires javafx.base;
//    requires lk.ijse.woodceylon;
//    requires lk.ijse.woodceylon;
//    requires lk.ijse.woodceylon;
//    requires lk.ijse.woodceylon;
}
