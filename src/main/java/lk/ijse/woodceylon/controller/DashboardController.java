package lk.ijse.woodceylon.controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import lk.ijse.woodceylon.model.OrderModel;
import org.controlsfx.control.Notifications;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.event.ActionEvent;

public class DashboardController implements Initializable {

    @FXML
    private Label lblTotalEarnings;
    @FXML
    private Label lblItemsSold;
    @FXML
    private Label lblTopItem;
    @FXML
    private Label lblSupplierCount;
    @FXML
    private Label lblCustomerCount;
    @FXML
    private PieChart pieChart;
    @FXML
    private ListView<String> lstLowStock;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblDate;

    private final OrderModel dashboardModel = new OrderModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calculateTime();
        loadDashboardData();
        checkLowStock();
    }

    private void loadDashboardData() {
        try {
            double totalEarnings = dashboardModel.getTotalEarnings();
            animateNumber(totalEarnings);

            ObservableList<PieChart.Data> chartData = dashboardModel.getPieChartData();
            animatePieChart(chartData);
            pieChart.setTitle("Sales Distribution by Product");

            lblItemsSold.setText(String.valueOf(dashboardModel.getTotalItemsSold()));
            lblCustomerCount.setText(String.valueOf(dashboardModel.getTotalCustomers()));
            lblSupplierCount.setText(String.valueOf(dashboardModel.getTotalSuppliers()));

            String topItem = dashboardModel.getTopSellingItem();
            lblTopItem.setText(topItem);
            applyFadeIn(lblTopItem);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void animateNumber(double targetValue) {
        double displayValue = targetValue / 1000.0;
        Timeline timeline = new Timeline();
        int steps = 50;
        Duration duration = Duration.seconds(1.0);

        for (int i = 0; i <= steps; i++) {
            final double currentStepValue = (displayValue / steps) * i;
            KeyFrame keyFrame = new KeyFrame(duration.divide(steps).multiply(i), event -> {
                lblTotalEarnings.setText(String.format("%.1fK", currentStepValue));
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setOnFinished(e -> lblTotalEarnings.setText(String.format("%.1fK", displayValue)));
        timeline.play();
    }

    private void animatePieChart(ObservableList<PieChart.Data> data) {
        pieChart.getData().clear();
        Timeline timeline = new Timeline();
        double delay = 0;
        for (PieChart.Data entry : data) {
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(delay), event -> {
                pieChart.getData().add(new PieChart.Data(entry.getName(), entry.getPieValue()));
            });
            timeline.getKeyFrames().add(keyFrame);
            delay += 0.2;
        }
        timeline.play();
    }

    private void applyFadeIn(javafx.scene.Node node) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), node);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private void checkLowStock() {
        try {
            ObservableList<String> lowStockItems = dashboardModel.getLowStockItems();
            if (lowStockItems.isEmpty()) {
                lstLowStock.getItems().clear();
                lstLowStock.getItems().add("All items are in stock! ✅");
            } else {
                lstLowStock.setItems(lowStockItems);
                Notifications.create()
                        .title("Low Stock Warning")
                        .text("There are " + lowStockItems.size() + " items running low on stock.")
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT)
                        .showWarning();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePrintInventoryReport(ActionEvent event) {
        try {
            lk.ijse.woodceylon.model.DashboardModel dashboard_Model = new lk.ijse.woodceylon.model.DashboardModel();
            dashboard_Model.printReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateTime() {

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {

            lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
            lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
