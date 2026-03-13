package lk.ijse.woodceylon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.woodceylon.bo.BOFactory;
import lk.ijse.woodceylon.bo.custom.CustomerBO;
import lk.ijse.woodceylon.bo.custom.OrderBO;
import lk.ijse.woodceylon.bo.custom.ProductBO;
import lk.ijse.woodceylon.dto.*;
//import lk.ijse.woodceylon.model.ProductModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AD_orderController implements Initializable {

    @FXML
    private AnchorPane AD_order;

    private final CustomerBO customerModel = new CustomerBO() {
        @Override
        public String addCustomer(CustomerDTO dto) throws Exception {
            return "";
        }

        @Override
        public String updateCustomer(CustomerDTO dto) throws Exception {
            return "";
        }

        @Override
        public String deleteCustomer(int customerId) throws Exception {
            return "";
        }

        @Override
        public ArrayList<CustomerDTO> getAllCustomers() throws Exception {
            return null;
        }

        @Override
        public CustomerDTO searchCustomer(int customerId) throws Exception {
            return null;
        }

        @Override
        public int getNextCustomerId() throws Exception {
            return 0;
        }

        @Override
        public void printCustomerReport() throws Exception {

        }
    };
    private final ProductBO productModel = new ProductBO() {
        @Override
        public String addProduct(ProductDTO dto) throws Exception {
            return "";
        }

        @Override
        public String updateProduct(ProductDTO dto) throws Exception {
            return "";
        }

        @Override
        public String deleteProduct(int id) throws Exception {
            return "";
        }

        @Override
        public ProductDTO searchProduct(int id) throws Exception {
            return null;
        }

        @Override
        public List<ProductDTO> getAllProducts() throws Exception {
            return List.of();
        }

        @Override
        public void printReport() throws Exception {

        }

        @Override
        public boolean updateStock(int productId, int qtyChange) throws Exception {
            return false;
        }

        @Override
        public List<ProductDTO> getLowStockItems() throws Exception {
            return List.of();
        }
    };
    private final OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBOFactory(BOFactory.BOTypes.ORDER);

    private final ObservableList<OrderItemTM> cartList = FXCollections.observableArrayList();

    @FXML private ComboBox<Integer> comboCustomerId;
    @FXML private ComboBox<Integer> comboItemId;
    @FXML private Label lblCustomerNameValue;
    @FXML private Label lblCustomerAddressValue;
    @FXML private Label lblCustomerContactValue;
    @FXML private Label lblItemNameValue;
    @FXML private Label lblItemPriceValue;
    @FXML private Label lblItemQtyValue;
    @FXML private Label lblOrderTotal;
    @FXML private TextField qtyField;

    @FXML private TableView<OrderItemTM> tblOrderItem;
    @FXML private TableColumn<OrderItemTM, String> colItemName;
    @FXML private TableColumn<OrderItemTM, Double> colUnitPrice;
    @FXML private TableColumn<OrderItemTM, Integer> colQty;
    @FXML private TableColumn<OrderItemTM, Double> colTotalPrice;
    @FXML private TableColumn<OrderItemTM, Void> colAction;

    @FXML private TableView<OrderDTO> tblAllDetails;
    @FXML private TableColumn<OrderDTO, Integer> colOrderId;
    @FXML private TableColumn<OrderDTO, Integer> colCustId;
    @FXML private TableColumn<OrderDTO, String> colDate;
    @FXML private TableColumn<OrderDetailsDTO, Integer> colItemId;
    @FXML private TableColumn<OrderDetailsDTO, String> col__ItemName;
    @FXML private TableColumn<OrderDetailsDTO, Integer> col_Qty;

    @FXML private TableView<OrderDetailsDTO> tblAllOrdersSummary;
    @FXML private TableColumn<OrderDetailsDTO, Void> colOrderAction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));


        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));


        tblAllDetails.setRowFactory(tv -> {
            TableRow<OrderDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    OrderDTO selectedOrder = row.getItem();
                    showOrderItemsPopup(selectedOrder.getOrderId());
                }
            });
            return row;
        });

        setupRemoveButton();
        setupCancelOrderButton();
        loadCustomerIds();
        loadProductIds();
        loadFullOrderTable();
        refreshCart();
    }

    private void setupRemoveButton() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button removeBtn = new Button("Remove");

            {
                removeBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
                removeBtn.setOnAction(event -> {
                    OrderItemTM item = getTableView().getItems().get(getIndex());
                    cartList.remove(item);
                    refreshCart();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : removeBtn);
            }
        });
    }

    private void loadCustomerIds() {
        try {
            List<CustomerDTO> customers = customerModel.getAllCustomers();
            ObservableList<Integer> ids = FXCollections.observableArrayList();
            for (CustomerDTO c : customers) {
                ids.add(c.getCustomerId());
            }
            comboCustomerId.setItems(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProductIds() {
        try {
            List<ProductDTO> products = productModel.getAllProducts();
            ObservableList<Integer> ids = FXCollections.observableArrayList();
            for (ProductDTO p : products) {
                ids.add(p.getProductId());
            }
            comboItemId.setItems(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSelectComboCustomerId(ActionEvent event) {
        Integer selectedId = comboCustomerId.getValue();
        if (selectedId == null) return;

        try {
            CustomerDTO customerDTO = customerModel.searchCustomer(selectedId);
            if (customerDTO != null) {
                lblCustomerNameValue.setText(customerDTO.getName());
                lblCustomerAddressValue.setText(customerDTO.getAddress());
                lblCustomerContactValue.setText(String.valueOf(customerDTO.getPhone()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSelectComboItemId(ActionEvent event) {
        Integer selectedId = comboItemId.getValue();
        if (selectedId == null) return;

        try {
            ProductDTO dto = productModel.searchProduct(selectedId);
            if (dto != null) {
                lblItemNameValue.setText(dto.getName());
                lblItemPriceValue.setText(dto.getPrice());
                lblItemQtyValue.setText(String.valueOf(dto.getQty()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddToCart(ActionEvent event) {
        if (comboItemId.getValue() == null || qtyField.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Select Item & Enter Qty").show();
            return;
        }

        try {
            int itemId = comboItemId.getValue();
            int qty = Integer.parseInt(qtyField.getText());
            int availableQty = Integer.parseInt(lblItemQtyValue.getText());
            double unitPrice = Double.parseDouble(lblItemPriceValue.getText().replace(",", ""));

            for (OrderItemTM tm : cartList) {
                if (tm.getItemId() == itemId) {
                    int newQty = tm.getQty() + qty;
                    if (newQty > availableQty) {
                        new Alert(Alert.AlertType.WARNING, "Stock Limit Exceeded! Available: " + availableQty).show();
                        return;
                    }
                    tm.setQty(newQty);
                    tm.setTotalPrice(unitPrice * newQty);
                    refreshCart();
                    qtyField.clear();
                    return;
                }
            }

            if (qty > availableQty) {
                new Alert(Alert.AlertType.WARNING, "Stock Limit Exceeded! Available: " + availableQty).show();
                return;
            }

            double total = unitPrice * qty;
            cartList.add(new OrderItemTM(itemId, lblItemNameValue.getText(), unitPrice, qty, total));
            refreshCart();
            qtyField.clear();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid number!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void handlePlaceOrder(ActionEvent event) {
        if (cartList.isEmpty() || comboCustomerId.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Cart or Customer is empty!").show();
            return;
        }

        try {
            List<OrderItemDTO> orderItems = new ArrayList<>();
            for (OrderItemTM tm : cartList) {
                orderItems.add(new OrderItemDTO(tm.getItemId(), tm.getQty(), tm.getItemPrice()));
            }

            OrderDTO orderDTO = new OrderDTO(comboCustomerId.getValue(), LocalDate.now(), orderItems);
            int orderId = orderBO.placeOrder(orderDTO);

            if (orderId > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Order Success! ID: " + orderId).show();
                clearFormFields();
                loadFullOrderTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Order placement failed: " + e.getMessage()).show();
        }
    }

    @FXML
    void clearAllFields(ActionEvent event) {
        clearFormFields();
    }

    private void clearFormFields() {
        comboCustomerId.getSelectionModel().clearSelection();
        comboItemId.getSelectionModel().clearSelection();
        qtyField.clear();
        cartList.clear();
        lblCustomerNameValue.setText("-");
        lblCustomerAddressValue.setText("-");
        lblCustomerContactValue.setText("-");
        lblItemNameValue.setText("-");
        lblItemPriceValue.setText("-");
        lblItemQtyValue.setText("-");
        refreshCart();
    }

    private void refreshCart() {
        tblOrderItem.setItems(cartList);
        double total = cartList.stream().mapToDouble(OrderItemTM::getTotalPrice).sum();
        lblOrderTotal.setText(String.format("%.2f", total));
    }

    @FXML
    private void handlePrintCompleatedOrders(ActionEvent event) {
        try {
            orderBO.printReport();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something Went Wrong!").show();
            e.printStackTrace();
        }
    }

    private void setupCancelOrderButton() {
        colOrderAction.setCellFactory(param -> new TableCell<>() {
            private final Button cancelBtn = new Button("Cancel");

            {
                cancelBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
                cancelBtn.setOnAction(event -> {
                    OrderDetailsDTO selected = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to cancel this order item?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            try {
                                boolean isSuccess = orderBO.cancelOrder(
                                        selected.getOrderId(),
                                        selected.getProductId(),
                                        selected.getQty()
                                );

                                if (isSuccess) {
                                    new Alert(Alert.AlertType.INFORMATION, "Order Cancelled!").show();
                                    loadFullOrderTable();
                                } else {
                                    new Alert(Alert.AlertType.ERROR, "Failed to cancel!").show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
                            }
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : cancelBtn);
            }
        });
    }

    private void handleCancelOrderLogic(OrderDetailsDTO order) {
        try {
            boolean isCancelled = orderBO.cancelOrder(order.getOrderId(), order.getProductId(), order.getQty());

            if (isCancelled) {
                new Alert(Alert.AlertType.INFORMATION, "Order Cancelled Successfully!").show();
                loadFullOrderTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Cancel Order!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    private void showOrderItemsPopup(int orderId) {
        try {
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Order Details - ID: " + orderId);

            TableView<OrderDetailsDTO> detailTable = new TableView<>();

            TableColumn<OrderDetailsDTO, Integer> colId = new TableColumn<>("Item ID");
            colId.setCellValueFactory(new PropertyValueFactory<>("productId"));

            TableColumn<OrderDetailsDTO, String> colName = new TableColumn<>("Item Name");
            colName.setCellValueFactory(new PropertyValueFactory<>("productName"));

            TableColumn<OrderDetailsDTO, Integer> colQty = new TableColumn<>("Quantity");
            colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

            detailTable.getColumns().addAll(colId, colName, colQty);

            ArrayList<OrderDetailsDTO> items = orderBO.getOrderItemsByOrderId(orderId);
            detailTable.setItems(FXCollections.observableArrayList(items));

            VBox vbox = new VBox(detailTable);
            Scene scene = new Scene(vbox, 400, 400);
            popupStage.setScene(scene);
            popupStage.show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading details!").show();
            e.printStackTrace();
        }
    }

    private void loadFullOrderTable() {
        try {
            colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

            colItemId.setCellValueFactory(new PropertyValueFactory<>("productId"));
            col__ItemName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            col_Qty.setCellValueFactory(new PropertyValueFactory<>("qty"));

            ArrayList<OrderDTO> list = orderBO.getAllOrdersGrouped();
            tblAllDetails.setItems(FXCollections.observableArrayList(list));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Can't Load Order Table!").show();
            e.printStackTrace();
        }
    }
}