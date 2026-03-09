package lk.ijse.woodceylon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.woodceylon.bo.BOFactory;
import lk.ijse.woodceylon.bo.custom.CustomerBO;
import lk.ijse.woodceylon.dto.CustomerDTO;

import java.net.URL;
import java.util.ResourceBundle;


public class AD_customerController implements Initializable {

    @FXML private TextField idField, customerName, customerNumber, customerEmail, customerAddress;

    @FXML private TableView<CustomerDTO> tableCustomer;
    @FXML private TableColumn<CustomerDTO, Integer> colId;
    @FXML private TableColumn<CustomerDTO, String> colName, colNumber, colEmail, colAddress;

    private final CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.CUSTOMER);
    private final ObservableList<CustomerDTO> customerList = FXCollections.observableArrayList();

    private final String NAME_REGEX    = "^[A-Za-z. ]{3,100}$";
    private final String PHONE_REGEX   = "^07[0-9]{8}$";
    private final String EMAIL_REGEX   = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final String ADDRESS_REGEX = "^[A-Za-z0-9 ,./-]{5,200}$";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadAllCustomers();
        generateNextId();

        tableCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                idField.setText(String.valueOf(newVal.getCustomerId()));
                customerName.setText(newVal.getName());
                customerNumber.setText(newVal.getPhone());
                customerEmail.setText(newVal.getEmail());
                customerAddress.setText(newVal.getAddress());
            }
        });
    }

    private void loadAllCustomers() {
        try {
            customerList.clear();
            customerList.addAll(customerBO.getAllCustomers());
            tableCustomer.setItems(customerList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customers").show();
        }
    }

    @FXML
    void saveCustomer(ActionEvent event) {
        try {
            String name    = customerName.getText().trim();
            String phone   = customerNumber.getText().trim();
            String email   = customerEmail.getText().trim();
            String address = customerAddress.getText().trim();

            if (!name.matches(NAME_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Customer Name").show();
                return;
            }
            if (!phone.matches(PHONE_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Phone Number").show();
                return;
            }
            if (!email.matches(EMAIL_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Email Address").show();
                return;
            }
            if (!address.matches(ADDRESS_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Address").show();
                return;
            }

            // ID comes from auto-generated field
            int id = Integer.parseInt(idField.getText().trim());

            CustomerDTO dto = new CustomerDTO(id, name, phone, email, address);

            String result = customerBO.addCustomer(dto);
            new Alert(Alert.AlertType.INFORMATION, result).show();

            loadAllCustomers();
            clearFields();
            generateNextId();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID format").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Customer Save Failed").show();
        }
    }

    @FXML
    void handleCustomerUpdate(ActionEvent event) {
        try {
            int id = Integer.parseInt(idField.getText().trim());

            CustomerDTO dto = new CustomerDTO(
                    id,
                    customerName.getText().trim(),
                    customerNumber.getText().trim(),
                    customerEmail.getText().trim(),
                    customerAddress.getText().trim()
            );

            String result = customerBO.updateCustomer(dto);
            new Alert(Alert.AlertType.INFORMATION, result).show();

            loadAllCustomers();
            clearFields();
            generateNextId();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID format").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Customer Update Failed").show();
        }
    }

    @FXML
    void handleCustomerDelete(ActionEvent event) {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Select a Customer to delete").show();
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete customer ID: " + id + " ?",
                    ButtonType.YES, ButtonType.NO);

            if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                String result = customerBO.deleteCustomer(id);
                new Alert(Alert.AlertType.INFORMATION, result).show();

                loadAllCustomers();
                clearFields();
                generateNextId();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Customer ID must be a number").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Customer Delete Failed").show();
        }
    }

    @FXML
    void handleSearchCustomer(KeyEvent event) {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            clearFieldsExceptId();
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            CustomerDTO dto = customerBO.searchCustomer(id);

            if (dto != null) {
                customerName.setText(dto.getName());
                customerNumber.setText(dto.getPhone());
                customerEmail.setText(dto.getEmail());
                customerAddress.setText(dto.getAddress());
            } else {
                clearFieldsExceptId();
            }
        } catch (NumberFormatException ignored) {
            clearFieldsExceptId();
        } catch (Exception ignored) {
            clearFieldsExceptId();
        }
    }

    @FXML
    void handleCustomerReset(ActionEvent event) {
        clearFields();
        generateNextId();
    }

    private void clearFieldsExceptId() {
        customerName.clear();
        customerNumber.clear();
        customerEmail.clear();
        customerAddress.clear();
    }

    private void clearFields() {
        idField.clear();
        clearFieldsExceptId();
        tableCustomer.getSelectionModel().clearSelection();
    }

    private void generateNextId() {
        try {
            int nextId = customerBO.getNextCustomerId();
            idField.setText(String.valueOf(nextId));
            idField.setEditable(false);
        } catch (Exception e) {
            e.printStackTrace();
            idField.setText("Error");
        }
    }

    @FXML
    private void handlePrintCustomer(ActionEvent event) {
        try {
            customerBO.printCustomerReport();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Report Generation Failed !").show();
            e.printStackTrace();
        }
    }
}



