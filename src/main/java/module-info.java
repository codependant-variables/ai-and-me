module com.codependentvariables.cab302groupproject {
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

    exports com.codependentvariables.cab302groupproject;
    exports com.codependentvariables.cab302groupproject.controller;
    exports com.codependentvariables.cab302groupproject.model;
    exports com.codependentvariables.cab302groupproject.repository;
    exports com.codependentvariables.cab302groupproject.service;

    opens com.codependentvariables.cab302groupproject;
    opens com.codependentvariables.cab302groupproject.controller;
    opens com.codependentvariables.cab302groupproject.model;
    opens com.codependentvariables.cab302groupproject.repository;
    opens com.codependentvariables.cab302groupproject.service;
}