package controller;

import com.jfoenix.controls.JFXButton;
import dto.Product;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import service.ServiceFactory;
import service.custom.ProductService;
import service.custom.SupplierService;
import util.ServiceType;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class ProductInventoryController implements Initializable {

    @FXML
    private TableColumn<?, ?> category;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<Product> cartTable;

    @FXML
    private ComboBox<String> comboCategory;
    @FXML
    private ComboBox<String> comboSupplier;

    @FXML
    private ComboBox<String> comboSize;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> price;

    @FXML
    private TableColumn<?, ?> productId;

    @FXML
    private TableColumn<?, ?> qty;

    @FXML
    private TableColumn<?, ?> supplier;

    @FXML
    private TableColumn<?, ?> size;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtOrderID;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtQty;

    @FXML
    private Label txtTime;

    ProductService service = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Product product = new Product(
                txtProductId.getText(),
                name.getText(),
                comboCategory.getValue(),
                comboSize.getValue(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText()),
                comboSupplier.getValue()
        );
        if (service.addProduct(product)) {
            new Alert(Alert.AlertType.INFORMATION, "Product added Successfully!").show();
            txtProductId.setText(service.generateId());
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Add Product!").show();
        }
        loadTable();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
                txtProductId.setText("");
                name.setText("");
                comboCategory.setValue("");
                comboSize.setValue("");
                txtPrice.setText("");
                txtQty.setText("");
                comboSupplier.setValue("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (service.deleteProduct(txtProductId.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "Product Deleted Successfully").show();
            txtProductId.setText(service.generateId());
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Delete Product!").show();
        }
        loadTable();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Product product = new Product(
                txtProductId.getText(),
                name.getText(),
                comboCategory.getValue(),
                comboSize.getValue(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText()),
                comboSupplier.getValue()
        );
        if (service.updateProduct(product)) {
            new Alert(Alert.AlertType.INFORMATION, "Product Updated Successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Update Product!").show();
        }
        loadTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtProductId.setText(service.generateId());
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        ObservableList<String> categoryList = FXCollections.observableArrayList();
        categoryList.add("Men");
        categoryList.add("Women");
        categoryList.add("Kids");
        comboCategory.setItems(categoryList);

        ObservableList<String> sizeList = FXCollections.observableArrayList();
        sizeList.add("Small");
        sizeList.add("Medium");
        sizeList.add("Large");
        sizeList.add("XL");
        sizeList.add("XXL");
        comboSize.setItems(sizeList);

        loadSupplierList();

        cartTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) ->
        {
            if (null != newValue) {
                setTextToValues(newValue);
            }
        }));
        loadTable();

        loadDateAndTime();
    }
    private void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = f.format(date);

        txtDate.setText(dateNow);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime localTime = LocalTime.now();
            txtTime.setText(localTime.getHour() + " : " + localTime.getMinute() + " : " + localTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setTextToValues(Product newValue) {
        txtProductId.setText(newValue.getId());
        txtProductName.setText(newValue.getName());
        comboCategory.setValue(newValue.getCategory());
        comboSize.setValue(String.valueOf((newValue.getSize())));
        txtPrice.setText((newValue.getPrice()).toString());
        txtQty.setText((newValue.getQty()).toString());
        comboSupplier.setValue(newValue.getSupplier());
    }

    private void loadTable() {
        try {
            ObservableList<Product> productsList = service.getAll();
            cartTable.setItems(productsList);
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void loadSupplierList() {
        ObservableList<String> supplierList = supplierService.getSupplierNames();
        try {
            comboSupplier.setItems(supplierList);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please add the suppliers before continuing");
            alert.setHeaderText("No Suppliers Available!");
            alert.show();
        }
    }
}
