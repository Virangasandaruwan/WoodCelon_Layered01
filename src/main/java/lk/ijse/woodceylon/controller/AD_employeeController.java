package lk.ijse.woodceylon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.woodceylon.App;
import lk.ijse.woodceylon.bo.BOFactory;
import lk.ijse.woodceylon.bo.custom.EmployeeBO;
import lk.ijse.woodceylon.dto.EmployeeDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AD_employeeController implements Initializable {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField contactField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;

    @FXML private TableView<EmployeeDTO> tblEmployee;
    @FXML private TableColumn<EmployeeDTO, Integer> colId;
    @FXML private TableColumn<EmployeeDTO, String> colName;
    @FXML private TableColumn<EmployeeDTO, String> colPhone;
    @FXML private TableColumn<EmployeeDTO, String> colEmail;
    @FXML private TableColumn<EmployeeDTO, String> colAddress;

    private final EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance()
            .getBOFactory(BOFactory.BOTypes.EMPLOYEE);

    private final ObservableList<EmployeeDTO> employeeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("employee_ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

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
            ArrayList<EmployeeDTO> allEmployees = employeeBO.getAllEmployees();
            if (allEmployees != null) {
                employeeList.addAll(allEmployees);
            }
            tblEmployee.setItems(employeeList);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load employees: " + e.getMessage()).show();
        }
    }

    @FXML
    private void saveEmployee() {
        try {
            if (idField.getText().isEmpty() || nameField.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Employee ID and Name are required!").show();
                return;
            }

            EmployeeDTO dto = new EmployeeDTO(
                    Integer.parseInt(idField.getText()),
                    nameField.getText(),
                    contactField.getText(),
                    emailField.getText(),
                    addressField.getText()
            );

            String result = employeeBO.addEmployee(dto);
            new Alert(Alert.AlertType.INFORMATION, result).show();

            loadAllEmployees();
            clearFields();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID format!").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Save Failed: " + e.getMessage()).show();
        }
    }

    @FXML
    void updateEmployee(ActionEvent event) {
        try {
            if (idField.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Select an employee to update!").show();
                return;
            }

            EmployeeDTO dto = new EmployeeDTO(
                    Integer.parseInt(idField.getText()),
                    nameField.getText(),
                    contactField.getText(),
                    emailField.getText(),
                    addressField.getText()
            );

            String result = employeeBO.updateEmployee(dto);
            new Alert(Alert.AlertType.INFORMATION, result).show();

            loadAllEmployees();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Update Failed: " + e.getMessage()).show();
        }
    }

    @FXML
    void deleteEmployee(ActionEvent event) {
        try {
            if (idField.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Select an employee to delete!").show();
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this employee?");
            if (confirm.showAndWait().get() == ButtonType.OK) {
                int employeeId = Integer.parseInt(idField.getText());
                String result = employeeBO.deleteEmployee(employeeId);
                new Alert(Alert.AlertType.INFORMATION, result).show();

                loadAllEmployees();
                clearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Delete Failed: " + e.getMessage()).show();
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
    private void handlePrintEmployee(ActionEvent event) {
        try {
            employeeBO.printReport();
            new Alert(Alert.AlertType.INFORMATION, "Employee Report Generated!").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Print Failed: " + e.getMessage()).show();
        }
    }
}