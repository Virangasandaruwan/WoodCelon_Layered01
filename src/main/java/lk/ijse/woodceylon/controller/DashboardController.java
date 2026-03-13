package lk.ijse.woodceylon.controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import lk.ijse.woodceylon.bo.BOFactory;
import lk.ijse.woodceylon.bo.custom.OrderBO;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.event.ActionEvent;

public class DashboardController implements Initializable {

    @FXML private Label lblTotalEarnings;
    @FXML private Label lblItemsSold;
    @FXML private Label lblTopItem;
    @FXML private Label lblSupplierCount;
    @FXML private Label lblCustomerCount;
    @FXML private PieChart pieChart;
    @FXML private ListView<String> lstLowStock;
    @FXML private Label lblTime;
    @FXML private Label lblDate;

    private final OrderBO orderBO = (OrderBO) BOFactory.getInstance()
            .getBOFactory(BOFactory.BOTypes.ORDER);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calculateTime();
        Platform.runLater(() -> {
            loadDashboardData();
            checkLowStock();
        });
    }

    private void loadDashboardData() {
        try {
            // 1. Total Earnings
            double totalEarnings = orderBO.getTotalEarnings();
            double earningsInK = totalEarnings / 1000.0;
            animateNumber(lblTotalEarnings, earningsInK, "%.1fK");

            // 2. Pie Chart
            ObservableList<PieChart.Data> chartData = orderBO.getPieChartData();
            if (chartData != null && !chartData.isEmpty()) {
                animatePieChart(chartData);
                pieChart.setTitle("Sales Distribution by Product");
            } else {
                pieChart.setTitle("No Sales Data Available");
            }

            // 3. Other stats
            int itemsSold = orderBO.getTotalItemsSold();
            int customerCount = orderBO.getTotalCustomers();
            int supplierCount = orderBO.getTotalSuppliers();

            lblItemsSold.setText(String.valueOf(itemsSold));
            lblCustomerCount.setText(String.valueOf(customerCount));
            lblSupplierCount.setText(String.valueOf(supplierCount));

            String topItem = orderBO.getTopSellingItem();
            lblTopItem.setText(topItem != null && !topItem.isEmpty() ? topItem : "No Sales Yet");
            applyFadeIn(lblTopItem);

            System.out.println("Dashboard loaded — Earnings: " + totalEarnings
                    + ", Items Sold: " + itemsSold
                    + ", Customers: " + customerCount
                    + ", Suppliers: " + supplierCount);

        } catch (Exception e) {
            e.printStackTrace();
            lblTotalEarnings.setText("0.0K");
            lblItemsSold.setText("0");
            lblCustomerCount.setText("0");
            lblSupplierCount.setText("0");
            lblTopItem.setText("N/A");
            pieChart.setTitle("Error Loading Data");
        }
    }

    private void animateNumber(Label label, double targetValue, String format) {
        int steps = 60;
        Duration totalDuration = Duration.seconds(1.5);
        Timeline timeline = new Timeline();

        for (int i = 1; i <= steps; i++) {  // start from 1 to avoid multiply(0)
            final double progress = (double) i / steps;
            final double currentValue = targetValue * progress;
            Duration time = totalDuration.multiply(progress);

            timeline.getKeyFrames().add(
                    new KeyFrame(time, e -> label.setText(String.format(format, currentValue)))
            );
        }

        timeline.setOnFinished(e -> label.setText(String.format(format, targetValue)));
        timeline.play();
    }

    private void animatePieChart(ObservableList<PieChart.Data> data) {
        pieChart.getData().clear();
        Timeline timeline = new Timeline();
        double delay = 0.1;

        for (PieChart.Data entry : data) {
            final String name = entry.getName();
            final double value = entry.getPieValue();
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(delay), e ->
                            pieChart.getData().add(new PieChart.Data(name, value))
                    )
            );
            delay += 0.3;
        }

        timeline.play();
    }

    private void applyFadeIn(javafx.scene.Node node) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    private void checkLowStock() {
        try {
            ObservableList<String> lowStockItems = orderBO.getLowStockItems();
            lstLowStock.getItems().clear();

            if (lowStockItems == null || lowStockItems.isEmpty()) {
                lstLowStock.getItems().add("All items are in stock! ✅");
            } else {
                lstLowStock.setItems(lowStockItems);
                Notifications.create()
                        .title("Low Stock Alert")
                        .text("There are " + lowStockItems.size() + " items running low.")
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT)
                        .showWarning();
            }
        } catch (Exception e) {
            e.printStackTrace();
            lstLowStock.getItems().add("Error loading stock data");
        }
    }

    private void calculateTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalDateTime now = LocalDateTime.now();
                    lblTime.setText(now.format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
                    lblDate.setText(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void handlePrintInventoryReport(ActionEvent event) {
        try {
            orderBO.printReport();
            Notifications.create()
                    .title("Success")
                    .text("Inventory Report Generated!")
                    .hideAfter(Duration.seconds(3))
                    .showInformation();
        } catch (Exception e) {
            e.printStackTrace();
            Notifications.create()
                    .title("Error")
                    .text("Failed to generate report: " + e.getMessage())
                    .hideAfter(Duration.seconds(5))
                    .showError();
        }
    }
}