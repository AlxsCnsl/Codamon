module com.example.codamon {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;

    opens com.example.codamon to javafx.fxml;
    exports com.example.codamon;
    exports com.example.codamon.controllers;
    opens com.example.codamon.controllers to javafx.fxml;
    opens com.example.codamon.views to javafx.fxml;
}