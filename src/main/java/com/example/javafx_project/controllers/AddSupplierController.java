package com.example.javafx_project.controllers;

import com.example.javafx_project.entities.Supplier;
import com.example.javafx_project.services.SupplierService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupplierController implements Initializable {
    @FXML
    private TextField nameField;

    @FXML
    private TextField cinField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button addButton;

    private SupplierService SupplierService;

    public void setSupplierService(SupplierService SupplierService) {
        this.SupplierService = SupplierService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SupplierService = new SupplierService();
    }

    @FXML
    private void handleAddButtonAction() {
        String name = nameField.getText();
        String cin = cinField.getText();
        String address = addressField.getText();
        int phoneNumber = Integer.parseInt(phoneNumberField.getText());

        Supplier Supplier = new Supplier();
        Supplier.setName(name);
        Supplier.setCIN(cin);
        Supplier.setAddress(address);
        Supplier.setPhoneNumber(phoneNumber);

        SupplierService.save(Supplier);

        clearFields();
    }

    private void clearFields() {
        nameField.clear();
        cinField.clear();
        addressField.clear();
        phoneNumberField.clear();
    }


}
