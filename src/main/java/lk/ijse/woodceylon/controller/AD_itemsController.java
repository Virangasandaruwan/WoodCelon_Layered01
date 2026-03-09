package lk.ijse.woodceylon.controller;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.woodceylon.dto.ProductDTO;
import lk.ijse.woodceylon.model.ProductModel;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lk.ijse.woodceylon.App;

public class AD_itemsController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private TextField itemID, itemName, itemDiscription, itemPrice, txtSupplierId, itemQty;

    @FXML
    private TableView<ProductDTO> tableProduct;
    @FXML
    private TableColumn<ProductDTO, Integer> colId, colSupplierId;
    @FXML
    private TableColumn<ProductDTO, String> colName, colDesc, colPrice;

    @FXML
    private TableColumn<ProductDTO, Integer> colQty;

    private final ProductModel productModel = new ProductModel();
    private final ObservableList<ProductDTO> productList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        
        colId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadAllProducts();

        
        tableProduct.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                itemID.setText(String.valueOf(newVal.getProductId()));
                itemName.setText(newVal.getName());
                itemDiscription.setText(newVal.getDescription());
                itemPrice.setText(newVal.getPrice());
                txtSupplierId.setText(String.valueOf(newVal.getSupplierId()));
                itemQty.setText(String.valueOf(newVal.getQty()));
            }
        });

        main_form.setOnMouseClicked(this::handleBackgroundClick);
        startImageSlideshow();
        createIndicators();
    }


    private void loadAllProducts() {
        try {
            productList.clear();
            productList.addAll(productModel.getAllProducts());
            tableProduct.setItems(productList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load products: " + e.getMessage()).show();
        }
    }

  
    @FXML
    void saveProduct(ActionEvent event) {
        try {
            ProductDTO dto = new ProductDTO(
                    Integer.parseInt(itemID.getText()),
                    itemName.getText(),
                    itemDiscription.getText(),
                    itemPrice.getText(),
                    Integer.parseInt(txtSupplierId.getText()),
                    Integer.parseInt(itemQty.getText().isEmpty() ? "0" : itemQty.getText())
            );

            String result = productModel.addProduct(dto);
            new Alert(Alert.AlertType.INFORMATION, result).show();
            loadAllProducts();
            clearFields();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Save Failed: " + e.getMessage()).show();
        }
    }

    
    @FXML
    void updateProduct(ActionEvent event) {
        try {
            ProductDTO dto = new ProductDTO(
                    Integer.parseInt(itemID.getText()),
                    itemName.getText(),
                    itemDiscription.getText(),
                    itemPrice.getText(),
                    Integer.parseInt(txtSupplierId.getText()),
                    Integer.parseInt(itemQty.getText().isEmpty() ? "0" : itemQty.getText())
            );

            String result = productModel.updateProduct(dto);
            new Alert(Alert.AlertType.INFORMATION, result).show();
            loadAllProducts();
            clearFields();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Update Failed: " + e.getMessage()).show();
        }
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        try {
            String idText = itemID.getText().trim();
            if (idText.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please select a product to delete").show();
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this product?");
            if (confirm.showAndWait().get() == ButtonType.OK) {
                int productId = Integer.parseInt(idText);
                String result = productModel.deleteProduct(productId);  // <-- int pass කළා
                new Alert(Alert.AlertType.INFORMATION, result).show();
                loadAllProducts();
                clearFields();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Invalid Product ID").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Delete Failed: " + e.getMessage()).show();
        }
    }

    
    @FXML
    void resetProduct(ActionEvent event) {
        clearFields();
    }

    // Optional: Search product by ID (real-time)
//    @FXML
//    void searchProduct(KeyEvent event) {
//        try {
//            if (!itemID.getText().isEmpty()) {
//                ProductDTO dto = productModel.searchProduct(itemID.getText());
//                if (dto != null) {
//                    itemName.setText(dto.getName());
//                    itemDiscription.setText(dto.getDescription());
//                    itemPrice.setText(dto.getPrice());
//                    txtSupplierId.setText(String.valueOf(dto.getSupplierId()));
//                }
//            }
//        } catch (Exception ignored) {}
//    }
    private void clearFields() {
        itemID.clear();
        itemName.clear();
        itemDiscription.clear();
        itemPrice.clear();
        txtSupplierId.clear();
        itemQty.clear();
    }


    @FXML
    private AnchorPane main_form;
    @FXML
    private HBox indicatorBox;

    private String[] imagePaths = {
        "/images/login_bg001.png",
        "/images/login_bg002.png",
        "/images/login_bg003.png",
        "/images/login_bg004.png",
        "/images/login_bg005.png",
        "/images/login_bg006.png",
        "/images/login_bg007.png",
        "/images/login_bg008.png"

    };

    private int currentImageIndex = 0;
    private Timeline imageSlider;

    private void handleBackgroundClick(MouseEvent event) {
        currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
        loadBackgroundImage(imagePaths[currentImageIndex]);
        updateIndicators();
    }

    // Auto slideshow
    private void startImageSlideshow() {
        loadBackgroundImage(imagePaths[0]);
        updateIndicators();

        imageSlider = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
            loadBackgroundImage(imagePaths[currentImageIndex]);
            updateIndicators();
        }));
        imageSlider.setCycleCount(Timeline.INDEFINITE);
        imageSlider.play();
    }

    private void loadBackgroundImage(String imagePath) {
        try {
            java.net.URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl == null) {
                System.out.println("Image not found: " + imagePath);
                return;
            }

            Image image = new Image(imageUrl.toExternalForm());
            BackgroundImage bgImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, true, true)
            );

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.4), main_form);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(1.0);//0.3

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.4), main_form);
            fadeIn.setFromValue(1.0);//0.3
            fadeIn.setToValue(1.0);

            fadeOut.setOnFinished(e -> {
                main_form.setBackground(new Background(bgImage));
                fadeIn.play();
            });

            fadeOut.play();

        } catch (Exception e) {
            System.out.println("Error loading image: " + imagePath);
            e.printStackTrace();
        }
    }

    private void createIndicators() {
        indicatorBox.getChildren().clear();
        for (int i = 0; i < imagePaths.length; i++) {
            Circle dot = new Circle(8);
            dot.setFill(i == 0 ? Color.WHITE : Color.rgb(255, 255, 255, 0.4));
            dot.setStroke(Color.WHITE);
            dot.setStrokeWidth(1.5);

            final int index = i;
            dot.setOnMouseClicked(e -> {
                currentImageIndex = index;
                loadBackgroundImage(imagePaths[currentImageIndex]);
                updateIndicators();
                imageSlider.stop();
                imageSlider.playFromStart();
            });

            indicatorBox.getChildren().add(dot);
        }
        indicatorBox.setAlignment(Pos.CENTER);
        indicatorBox.setSpacing(12);
    }

    private void updateIndicators() {
        for (int i = 0; i < indicatorBox.getChildren().size(); i++) {
            Circle dot = (Circle) indicatorBox.getChildren().get(i);
            if (i == currentImageIndex) {
                dot.setFill(Color.WHITE);
                dot.setRadius(10);
            } else {
                dot.setFill(Color.rgb(255, 255, 255, 0.4));
                dot.setRadius(8);
            }
        }
    }

    @FXML
    private void handlePrintItem(ActionEvent evnet) {

        try {
            productModel.printReport();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something Went Wrong !").show();
            e.printStackTrace();

        }

    }
}
