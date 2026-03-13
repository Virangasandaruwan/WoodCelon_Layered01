package lk.ijse.woodceylon.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.woodceylon.bo.BOFactory;
import lk.ijse.woodceylon.bo.custom.SupplierBO;
import lk.ijse.woodceylon.dto.SupplierDTO;
import lk.ijse.woodceylon.entity.Supplier;

public class AD_supplierController implements Initializable {

    @FXML
    private TextField txtSupplierId, txtSupplierName, txtSupplierPhone, txtSupplierAddress, txtSupplierEmail;

    @FXML
    private TableView<SupplierDTO> tblSupplier;
    @FXML
    private TableColumn<SupplierDTO, Integer> colId;
    @FXML
    private TableColumn<SupplierDTO, String> colName, colPhone, colAddress, colEmail;

    private final SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.SUPPLIER);
    private final ObservableList<SupplierDTO> supplierList = FXCollections.observableArrayList();

    private final String NAME_REGEX = "^[A-Za-z. ]{3,100}$";
    private final String PHONE_REGEX = "^07[0-9]{8}$";
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("supplierPhone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));

        loadAllSuppliers();
        generateNextId();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtSupplierId.setText(String.valueOf(newVal.getSupplierId()));
                txtSupplierName.setText(newVal.getSupplierName());
                txtSupplierPhone.setText(newVal.getSupplierPhone());
                txtSupplierAddress.setText(newVal.getSupplierAddress());
                txtSupplierEmail.setText(newVal.getSupplierEmail());
            }
        });
    }

    private void loadAllSuppliers() {
        try {
            supplierList.clear();
            supplierList.addAll(supplierBO.getAllSuppliers());
            tblSupplier.setItems(supplierList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load suppliers").show();
        }
    }

    @FXML
    void saveSupplier(ActionEvent event) {
        try {
            String name = txtSupplierName.getText().trim();
            String phone = txtSupplierPhone.getText().trim();
            String email = txtSupplierEmail.getText().trim();
            String address = txtSupplierAddress.getText().trim();

            if (!name.matches(NAME_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Supplier Name").show();
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

            SupplierDTO dto = new SupplierDTO(
                    Integer.parseInt(txtSupplierId.getText()),
                    name, address, phone, email
            );

            String result = supplierBO.addSupplier(dto);
            new Alert(Alert.AlertType.INFORMATION, result).show();

            loadAllSuppliers();
            clearFields();
            generateNextId();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Supplier Save Failed").show();
        }
    }

    @FXML
    void updateSupplier(ActionEvent event) {
        try {
            SupplierDTO dto = new SupplierDTO(
                    Integer.parseInt(txtSupplierId.getText()),
                    txtSupplierName.getText(),
                    txtSupplierAddress.getText(),
                    txtSupplierPhone.getText(),
                    txtSupplierEmail.getText()
            );

            String result = supplierBO.updateSupplier(dto);
            new Alert(Alert.AlertType.INFORMATION, result).show();
            loadAllSuppliers();
            clearFields();
            generateNextId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Supplier Update Failed").show();
        }
    }

    @FXML
    void deleteSupplier(ActionEvent event) {
        String idText = txtSupplierId.getText().trim();
        if (idText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Select a Supplier to delete").show();
            return;
        }

        try {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete supplier ID: " + idText + "?", ButtonType.YES, ButtonType.NO);
            if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                String result = supplierBO.deleteSupplier(Integer.parseInt(idText));
                new Alert(Alert.AlertType.INFORMATION, result).show();
                loadAllSuppliers();
                clearFields();
                generateNextId();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Supplier Delete Failed").show();
        }
    }

    @FXML
    void searchSupplier(KeyEvent event) {
        String idText = txtSupplierId.getText().trim();
        if (idText.isEmpty()) {
            clearFieldsExceptId();
            return;
        }

        try {
            SupplierDTO dto = supplierBO.searchSupplier(Integer.parseInt(idText));
            if (dto != null) {
                txtSupplierName.setText(dto.getSupplierName());
                txtSupplierPhone.setText(dto.getSupplierPhone());
                txtSupplierAddress.setText(dto.getSupplierAddress());
                txtSupplierEmail.setText(dto.getSupplierEmail());
            } else {
                clearFieldsExceptId();
            }
        } catch (Exception ignored) {
        }
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
        generateNextId();
    }

    private void clearFieldsExceptId() {
        txtSupplierName.clear();
        txtSupplierPhone.clear();
        txtSupplierAddress.clear();
        txtSupplierEmail.clear();
    }

    private void clearFields() {
        txtSupplierId.clear();
        clearFieldsExceptId();
        tblSupplier.getSelectionModel().clearSelection();
    }

    private void generateNextId() {
        try {
            int nextId = supplierBO.getNextSupplierId();
            txtSupplierId.setText(String.valueOf(nextId));
            txtSupplierId.setEditable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePrintSupplier(ActionEvent event) {
        try {
            supplierBO.printReport();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Report Generation Failed !").show();
            e.printStackTrace();
        }
    }
}
