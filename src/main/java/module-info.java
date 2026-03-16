module com.codependentvariables.aiandme {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires spring.data.jpa;
    requires spring.context;
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires org.hibernate.orm.core;
    requires org.xerial.sqlitejdbc;
    requires jakarta.cdi;
    requires jakarta.persistence;
    requires jakarta.transaction;
    requires static lombok;

    exports com.codependentvariables.aiandme;
    exports com.codependentvariables.aiandme.controller;
    exports com.codependentvariables.aiandme.model;
    exports com.codependentvariables.aiandme.repository;
    exports com.codependentvariables.aiandme.service;

    opens com.codependentvariables.aiandme;
    opens com.codependentvariables.aiandme.controller;
    opens com.codependentvariables.aiandme.model;
    opens com.codependentvariables.aiandme.repository;
    opens com.codependentvariables.aiandme.service;
}