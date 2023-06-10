package com.example.javafx_project.controllers;

import com.example.javafx_project.entities.Supplier;
import com.example.javafx_project.services.SupplierService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {
    @FXML
    private TableView<Supplier> SupplierTableView;

    @FXML
    private TableColumn<Supplier, Integer> idColumn;

    @FXML
    private TableColumn<Supplier, String> nameColumn;

    @FXML
    private TableColumn<Supplier, String> cinColumn;

    @FXML
    private TableColumn<Supplier, String> addressColumn;

    @FXML
    private TableColumn<Supplier, Integer> phoneNumberColumn;
    @FXML
    private TableColumn<Supplier, Button> editColumn;
    @FXML
    private TableColumn<Supplier, Button> deleteColumn;

    private SupplierService SupplierService;
    private ObservableList<Supplier> SupplierList;

    public void setSupplierService(SupplierService SupplierService) {
        this.SupplierService = SupplierService;
    }

    @FXML
    private void openSupplierForm() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/views/SupplierForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SupplierService = new SupplierService();
        startAutoRefresh();
        loadSuppliers();
            setupTableView();

    }

    private void loadSuppliers() {
        List<Supplier> Suppliers = SupplierService.findAll();
        SupplierList = FXCollections.observableArrayList(Suppliers);
    }

    private void setupTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cinColumn.setCellValueFactory(new PropertyValueFactory<>("CIN"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        editColumn.setCellFactory(createEditButtonCellFactory());

        deleteColumn.setCellFactory(createDeleteButtonCellFactory());

        SupplierTableView.setItems(SupplierList);
    }

    @FXML
    private void openEditSupplierForm(Supplier Supplier) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditSupplier.fxml"));
            Parent parent = loader.load();
            EditSupplierController editSupplierController = loader.getController();
            editSupplierController.setSupplier(Supplier);
            editSupplierController.setSupplierService(SupplierService);
            System.out.println(Supplier);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }





    private Callback<TableColumn<Supplier, Button>, TableCell<Supplier, Button>> createEditButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Supplier, Button> call(TableColumn<Supplier, Button> column) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");

                    {
                        editButton.setOnAction(event -> {
                            Supplier Supplier = getTableView().getItems().get(getIndex());
                            System.out.println(Supplier);
                            openEditSupplierForm(Supplier);
                        });
                    }

                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setGraphic(editButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        };
    }

    private Callback<TableColumn<Supplier, Button>, TableCell<Supplier, Button>> createDeleteButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Supplier, Button> call(TableColumn<Supplier, Button> column) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            Supplier Supplier = getTableView().getItems().get(getIndex());
                            SupplierService.remove(Supplier);
                            SupplierTableView.getItems().remove(Supplier);
                        });
                    }

                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setGraphic(deleteButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        };
    }

    private void startAutoRefresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            refreshTableView();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void refreshTableView() {
        List<Supplier> Suppliers = SupplierService.findAll();
        SupplierTableView.getItems().clear();
        SupplierTableView.getItems().addAll(Suppliers);
    }




    public void uploadData(ActionEvent actionEvent) {
        SupplierService.readFromStyleSheetAndInsertInDatabase("src/main/resources/ownerInfo.xlsx");
    }
}
