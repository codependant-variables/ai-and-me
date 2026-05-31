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
    exports com.codependentvariables.aiandme.database.dao;
    exports com.codependentvariables.aiandme.model;
    exports com.codependentvariables.aiandme.model.dao;
    exports com.codependentvariables.aiandme.modules.dialogue;
    exports com.codependentvariables.aiandme.modules.router;
    exports com.codependentvariables.aiandme.modules.state;
    exports com.codependentvariables.aiandme.modules.toast;
    exports com.codependentvariables.aiandme.modules.validation;
    exports com.codependentvariables.aiandme.services;
    exports com.codependentvariables.aiandme.services.home;

    opens com.codependentvariables.aiandme to javafx.fxml;
    opens com.codependentvariables.aiandme.controller to javafx.fxml;
    opens com.codependentvariables.aiandme.model to javafx.fxml;
    opens com.codependentvariables.aiandme.services.home to javafx.fxml;
    opens com.codependentvariables.aiandme.modules.toast to javafx.fxml;
    opens com.codependentvariables.aiandme.modules.router to javafx.fxml;
    opens com.codependentvariables.aiandme.modules.dialogue to javafx.fxml;
}