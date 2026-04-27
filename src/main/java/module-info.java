module com.codependentvariables.aiandme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires atlantafx.base;
    requires org.apache.commons.codec;
    requires com.google.zxing;


    opens com.codependentvariables.aiandme to javafx.fxml;
    exports com.codependentvariables.aiandme;
    exports com.codependentvariables.aiandme.controller;
    opens com.codependentvariables.aiandme.controller to javafx.fxml;
    exports com.codependentvariables.aiandme.model;
    opens com.codependentvariables.aiandme.model to javafx.fxml;
    exports com.codependentvariables.aiandme.navigation;
    opens com.codependentvariables.aiandme.navigation to javafx.fxml;
}