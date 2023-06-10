package com.example.javafx_project.controllers;

import com.example.javafx_project.entities.Supplier;
import com.example.javafx_project.services.SupplierService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditSupplierController  {
    private Supplier Supplier;
    private boolean updateRequired = false;

    @FXML
    private TextField nameField;
    @FXML
    private TextField cinField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;

    private SupplierService SupplierService; // Assuming you have the SupplierService injected or instantiated properly

    public void setSupplierService(SupplierService SupplierService) {
        this.SupplierService = SupplierService;
    }


    public void setSupplier(Supplier Supplier) {
        this.Supplier = Supplier;
        populateFields();
    }

    public boolean isUpdateRequired() {
        return updateRequired;
    }

    public Supplier getUpdatedSupplier() {
        updateSupplier();
        return Supplier;
    }

    private void populateFields() {
        if (Supplier != null) {
            nameField.setText(Supplier.getName());
            cinField.setText(Supplier.getCIN());
            addressField.setText(Supplier.getAddress());
            phoneNumberField.setText(Supplier.getPhoneNumber().toString());
        }
    }

    private void updateSupplier() {
        if (Supplier != null) {
            Supplier.setName(nameField.getText());
            Supplier.setCIN(cinField.getText());
            Supplier.setAddress(addressField.getText());
            Supplier.setPhoneNumber(Integer.parseInt(phoneNumberField.getText()));
            updateRequired = true;
        }
    }

    @FXML
    private void saveChanges() {
        updateSupplier();
        SupplierService.update(Supplier); // Assuming you have the update method in your SupplierService implementation
        closeForm();
    }

    private void closeForm() {
        // Close the form or perform any necessary cleanup
        nameField.getScene().getWindow().hide();
    }
}