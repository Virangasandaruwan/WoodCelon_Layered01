package lk.ijse.woodceylon.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.woodceylon.App;
import lk.ijse.woodceylon.bo.custom.OrderBO;
import lk.ijse.woodceylon.dto.OrderDTO;
import lk.ijse.woodceylon.dto.OrderDetailsDTO;
import lk.ijse.woodceylon.dto.ProductDTO;


import org.controlsfx.control.Notifications;
import javafx.util.Duration;
import javafx.geometry.Pos;

//amathara
//import javafx.animation.FadeDDDTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;
import javafx.scene.Node;

public class AdminLayerController implements Initializable {

    @FXML
    private AnchorPane AdminLayer;

    @FXML
    private AnchorPane admin_LoadPane;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane adminContentPane;
    private Object btnBack;

    @FXML
    private Label lblTotalEarnings;
    @FXML
    private Label lblItemsSold;

    private final OrderBO dashboardModel = new OrderBO() {
        @Override
        public String addOrder(OrderDTO orderDTO) throws Exception {
            return "";
        }

        @Override
        public String updateOrder(OrderDTO orderDTO) throws Exception {
            return "";
        }

        @Override
        public String deleteOrder(int orderId) throws Exception {
            return "";
        }

        @Override
        public ArrayList<OrderDTO> getAllOrders() throws Exception {
            return null;
        }

        @Override
        public OrderDTO searchOrder(int orderId) throws Exception {
            return null;
        }

        @Override
        public ProductDTO searchProduct(int id) throws Exception {
            return null;
        }

        @Override
        public int placeOrder(OrderDTO orderDTO) throws Exception {
            return 0;
        }

        @Override
        public ArrayList<OrderDetailsDTO> getAllOrderDetails() throws Exception {
            return null;
        }

        @Override
        public void printReport() throws Exception {

        }

        @Override
        public double getTotalEarnings() throws Exception {
            return 0;
        }

        @Override
        public int getTotalItemsSold() throws Exception {
            return 0;
        }

        @Override
        public String getTopSellingItem() throws Exception {
            return "";
        }

        @Override
        public int getTotalCustomers() throws Exception {
            return 0;
        }

        @Override
        public int getTotalSuppliers() throws Exception {
            return 0;
        }

        @Override
        public ArrayList<OrderDTO> getAllOrdersGrouped() throws Exception {
            return null;
        }

        @Override
        public ArrayList<OrderDetailsDTO> getOrderItemsByOrderId(int orderId) throws Exception {
            return null;
        }

        @Override
        public boolean cancelOrder(int orderId, int productId, int qty) throws Exception {
            return false;
        }

        @Override
        public ObservableList<String> getLowStockItems() {
            return null;
        }

        @Override
        public ObservableList<PieChart.Data> getPieChartData() {
            return null;
        }
    };
    private final OrderBO dashboard_Model = new OrderBO() {
        @Override
        public String addOrder(OrderDTO orderDTO) throws Exception {
            return "";
        }

        @Override
        public String updateOrder(OrderDTO orderDTO) throws Exception {
            return "";
        }

        @Override
        public String deleteOrder(int orderId) throws Exception {
            return "";
        }

        @Override
        public ArrayList<OrderDTO> getAllOrders() throws Exception {
            return null;
        }

        @Override
        public OrderDTO searchOrder(int orderId) throws Exception {
            return null;
        }

        @Override
        public ProductDTO searchProduct(int id) throws Exception {
            return null;
        }

        @Override
        public int placeOrder(OrderDTO orderDTO) throws Exception {
            return 0;
        }

        @Override
        public ArrayList<OrderDetailsDTO> getAllOrderDetails() throws Exception {
            return null;
        }

        @Override
        public void printReport() throws Exception {

        }

        @Override
        public double getTotalEarnings() throws Exception {
            return 0;
        }

        @Override
        public int getTotalItemsSold() throws Exception {
            return 0;
        }

        @Override
        public String getTopSellingItem() throws Exception {
            return "";
        }

        @Override
        public int getTotalCustomers() throws Exception {
            return 0;
        }

        @Override
        public int getTotalSuppliers() throws Exception {
            return 0;
        }

        @Override
        public ArrayList<OrderDTO> getAllOrdersGrouped() throws Exception {
            return null;
        }

        @Override
        public ArrayList<OrderDetailsDTO> getOrderItemsByOrderId(int orderId) throws Exception {
            return null;
        }

        @Override
        public boolean cancelOrder(int orderId, int productId, int qty) throws Exception {
            return false;
        }

        @Override
        public ObservableList<String> getLowStockItems() {
            return null;
        }

        @Override
        public ObservableList<PieChart.Data> getPieChartData() {
            return null;
        }
    };

    @FXML
    private Label lblTopItem;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label lblSupplierCount;
    @FXML
    private Label lblCustomerCount;
    @FXML
    private ListView<String> lstLowStock;

    private void showError(String title, IOException msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        //alert.setHeaderText(msg);
        alert.show();
    }

    // 1
    @FXML
    void lad_cusPane() throws IOException {

        Parent AD_customer = App.loadFXML("AD_customer");
        adminContentPane.getChildren().setAll(AD_customer);
        System.out.println("Item page loaded");

    }

    // 2
    @FXML
    void lad_emPane() throws IOException {
        Parent AD_employee = App.loadFXML("AD_employee");
        adminContentPane.getChildren().setAll(AD_employee);
        System.out.println("AD_employee page loaded");

    }

    // 3
    @FXML
    void lad_itemPane() throws IOException {
        Parent AD_items = App.loadFXML("AD_items");
        adminContentPane.getChildren().setAll(AD_items);
        System.out.println("AD_items page loaded");

    }

    // 4
    @FXML
    void lad_orderPane() throws IOException {
        Parent AD_order = App.loadFXML("AD_order");
        adminContentPane.getChildren().setAll(AD_order);
        System.out.println("AD_order page loaded");

    }

    // 5
    @FXML
    void lad_supPane() throws IOException {
        Parent AD_supplier = App.loadFXML("AD_supplier");
        adminContentPane.getChildren().setAll(AD_supplier);
        System.out.println("Item page loaded");

    }

    @FXML
    void logout(ActionEvent event) {
        try {
            App.setRoot("LoginPage");
        } catch (IOException e) {
            showError("Unable to load Login Page.", e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            lad_dashboard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void lad_dashboard() throws IOException {
        Parent dashboard = App.loadFXML("Dashboard");
        adminContentPane.getChildren().setAll(dashboard);
    }

    public void handleADLayer() {
        try {
            App.setRoot("AdminLayer");
        } catch (IOException ex) {
            Logger.getLogger(AD_customerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handlePrintInventoryReport(ActionEvent evnet) {

        try {
            dashboard_Model.printReport();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something Went Wrong !").show();
            e.printStackTrace();

        }

    }

}
