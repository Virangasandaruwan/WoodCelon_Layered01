package lk.ijse.woodceylon.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.woodceylon.App;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.dto.EmployeeDTO;
import lk.ijse.woodceylon.model.EmployeeModel;

public class AD_employeeController implements Initializable {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;

    @FXML
    private TableView<EmployeeDTO> tblEmployee;
    @FXML
    private TableColumn<EmployeeDTO, Integer> colId;
    @FXML
    private TableColumn<EmployeeDTO, String> colName;
    @FXML
    private TableColumn<EmployeeDTO, String> colPhone;
    @FXML
    private TableColumn<EmployeeDTO, String> colEmail;
    @FXML
    private TableColumn<EmployeeDTO, String> colAddress;

    private final EmployeeModel employeeModel = new EmployeeModel();
    private final ObservableList<EmployeeDTO> employeeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

      
        colId.setCellValueFactory(new PropertyValueFactory<>("Employee_ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));

        loadAllEmployees();

       
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                idField.setText(String.valueOf(newVal.getEmployee_ID()));
                nameField.setText(newVal.getName());
                contactField.setText(newVal.getPhone());
                emailField.setText(newVal.getEmail());
                addressField.setText(newVal.getAddress());
            }
        });
    }

   
    private void loadAllEmployees() {


        try {
            employeeList.clear();
            employeeList.addAll(employeeModel.getAllEmployee());
            tblEmployee.setItems(employeeList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load employees").show();
        }
    }

    
    @FXML
    private void saveEmployee() {

        try {
            EmployeeDTO dto = new EmployeeDTO(
                    Integer.parseInt(idField.getText()),
                    nameField.getText(),
                    contactField.getText(),
                    emailField.getText(),
                    addressField.getText()
            );

            String result = employeeModel.addEmployee(dto);
            new Alert(Alert.AlertType.INFORMATION, result).show();

            loadAllEmployees();
            clearFields();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Save Failed").show();
        }
    }

    
    @FXML
    void updateEmployee(ActionEvent event) {
        try {
            EmployeeDTO dto = new EmployeeDTO(
                    Integer.parseInt(idField.getText()),
                    nameField.getText(),
                    contactField.getText(),
                    emailField.getText(),
                    addressField.getText()
            );

            String result = employeeModel.updateEmployee(dto);
            new Alert(Alert.AlertType.INFORMATION, result).show();

            loadAllEmployees();
            clearFields();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Update Failed").show();
        }
    }

  
    @FXML
    void deleteEmployee(ActionEvent event) {
        try {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this employee?");
            if (confirm.showAndWait().get() == ButtonType.OK) {

                EmployeeDTO dto = new EmployeeDTO();
                dto.setEmployee_ID(Integer.parseInt(idField.getText()));

                String result = employeeModel.deleteEmployee(dto);
                new Alert(Alert.AlertType.INFORMATION, result).show();

                loadAllEmployees();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Delete Failed").show();
        }
    }

    
    @FXML
    void clear(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        contactField.clear();
        emailField.clear();
        addressField.clear();
        tblEmployee.getSelectionModel().clearSelection();
    }

   
    @FXML
    void handleBackClick() {
        try {
            App.setRoot("AdminLayer");
        } catch (IOException ex) {
            Logger.getLogger(AD_employeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handlePrintEmployee(ActionEvent evnet) {

        try {
            employeeModel.printReport();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something Went Wrong !").show();
            e.printStackTrace();

        }

    }

}
