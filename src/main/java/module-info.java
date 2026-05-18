module com.codependentvariables.aiandme {
    requires atlantafx.base;
    requires com.google.zxing;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires org.apache.commons.codec;
    requires org.xerial.sqlitejdbc;
    requires org.json;

    exports com.codependentvariables.aiandme;
    exports com.codependentvariables.aiandme.controller;
    exports com.codependentvariables.aiandme.database;
    exports com.codependentvariables.aiandme.model;
    exports com.codependentvariables.aiandme.modules;
    exports com.codependentvariables.aiandme.services;
    exports com.codependentvariables.aiandme.state;
    exports com.codependentvariables.aiandme.validation;

    opens com.codependentvariables.aiandme to javafx.fxml;
    opens com.codependentvariables.aiandme.controller to javafx.fxml;
    opens com.codependentvariables.aiandme.model to javafx.fxml;
    opens com.codependentvariables.aiandme.modules to javafx.fxml;
}