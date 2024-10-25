package controller;

import dto.CartTM;
import dto.Order;
import dto.OrderDetail;
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
import service.custom.OrderService;
import service.custom.ProductService;
import util.LoginInfo;
import util.ServiceType;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> Qty;

    @FXML
    private TableView<CartTM> cartTable;

    @FXML
    private ComboBox<String> comboBoxItemCode;

    @FXML
    private ComboBox<String> comboboxCategory;

    @FXML
    private ComboBox<String> comboboxPayment;

    @FXML
    private TableColumn<?, ?> description;

    @FXML
    private TableColumn<?, ?> itemCode;

    @FXML
    private TableColumn<?, ?> total;

    @FXML
    private TextField txtCusName;

    @FXML
    private Label txtDate;

    @FXML
    private TextField txtQTY;

    @FXML
    private Label txtTime;

    @FXML
    private Label txtTotallbl;

    @FXML
    private TextField txtUnit;

    @FXML
    private TextField txtlblOrderId;

    @FXML
    private TextField txtlblUserId;

    @FXML
    private TextField txtStock;

    @FXML
    private TableColumn<?, ?> unitPrize;

    private int index;
    ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();
    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCT);
    OrderService orderService = ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);

    @FXML
    void btnAddCart(ActionEvent event) {
        itemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        description.setCellValueFactory(new PropertyValueFactory<>("name"));
        Qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        unitPrize.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));

        if (Integer.parseInt(txtQTY.getText()) > Integer.parseInt(txtStock.getText())) {
            new Alert(Alert.AlertType.WARNING, "Invalid qty").show();
        } else {
            cartTMS.add(new CartTM(
                    comboBoxItemCode.getValue(),
                    txtCusName.getText(),
                    comboboxCategory.getValue(),
                    Integer.parseInt(txtQTY.getText()),
                    Double.parseDouble(txtUnit.getText()),
                    Double.parseDouble(txtUnit.getText()) * Integer.parseInt(txtQTY.getText())
            ));
            getNetTotal();
            cartTable.setItems(cartTMS);
            setTextToEmpty();
        }
    }

    @FXML
    void btnPlaceOrder(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Place Order");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.OK)) {
            String orderId = txtlblOrderId.getText();
            LocalDate orderDate = LocalDate.now();
            String orderTime = txtTime.getText();
            String employeeId = txtlblUserId.getText();

            List<OrderDetail> orderDetails = new ArrayList<>();
            cartTMS.forEach(obj -> {
                orderDetails.add(new OrderDetail(
                        obj.getItemCode(),
                        obj.getName(),
                        obj.getQty(),
                        obj.getUnitPrice(),
                        obj.getTotal()
                ));
            });
            Order order = new Order(orderId, orderDate, orderTime, employeeId, Double.parseDouble(txtTotallbl.getText()), orderDetails);
            if (orderService.addOrder(order)) {
                new Alert(Alert.AlertType.INFORMATION, "Order Placed Successfully!").show();
                txtlblOrderId.setText(orderService.generateId());
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to place order!").show();
            }
            System.out.println(order);
        } else {

        }
    }
    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        getNetTotal();
        cartTMS.remove(index);
        setTextToEmpty();

    }
    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        cartTMS.set(index, new CartTM(
                comboBoxItemCode.getValue(),
                txtCusName.getText(),
                comboboxCategory.getValue(),
                Integer.parseInt(txtQTY.getText()),
                Double.parseDouble(txtUnit.getText()),
                Double.parseDouble(txtUnit.getText()) * Integer.parseInt(txtQTY.getText())
        ));
        getNetTotal();
        cartTable.setItems(cartTMS);
        setTextToEmpty();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtlblUserId.setText(LoginInfo.getInstance().getUserId());
        loadItem();
        loadDateAndTime();
        txtlblOrderId.setText(orderService.generateId());
        comboBoxItemCode.getSelectionModel().selectedItemProperty().addListener(((observableValue, o, newValue) -> {
            if (newValue != null) {
                searchItems(newValue);
            }
        }));
        ObservableList<String> paymentList = FXCollections.observableArrayList();
        paymentList.add("Cash");
        paymentList.add("Card");
        comboboxPayment.setItems(paymentList);

        ObservableList<String> categoryList = FXCollections.observableArrayList();
        categoryList.add("Men");
        categoryList.add("Women");
        categoryList.add("Kids");
        comboboxCategory.setItems(categoryList);

        cartTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) ->
        {
            if (null != newValue) {
                setTextToValues(newValue);
                index = cartTMS.indexOf(new CartTM(
                        comboBoxItemCode.getValue(),
                        txtCusName.getText(),
                        comboboxCategory.getValue(),
                        Integer.parseInt(txtQTY.getText()),
                        Double.parseDouble(txtUnit.getText()),
                        Double.parseDouble(txtUnit.getText()) * Integer.parseInt(txtQTY.getText())
                ));
            }
        }));
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
    private void loadItem() {
        ObservableList<String> itemIdList = productService.getProductIds();
        comboBoxItemCode.setItems(itemIdList);
    }

    private void searchItems(String itemCode) {
        Product product = productService.searchProduct(itemCode);
        txtCusName.setText(product.getName());
        comboboxCategory.setValue(product.getCategory());
        txtTotallbl.setText(product.getQty().toString());
        txtUnit.setText(product.getPrice().toString());
    }

    private void getNetTotal() {
        Double total = 0.0;
        for (CartTM cartTM : cartTMS) {
            total += cartTM.getTotal() != null ? cartTM.getTotal() : 0.0;
        }
        txtTotallbl.setText(total.toString());
    }

    private void setTextToValues(CartTM newValue) {
        comboBoxItemCode.setValue(newValue.getItemCode());
        txtCusName.setText(newValue.getName());
        comboboxCategory.setValue(newValue.getCategory());
        txtQTY.setText(newValue.getQty().toString());
        txtUnit.setText(newValue.getUnitPrice().toString());
    }

    private void setTextToEmpty() {
        txtCusName.setText("");
        txtUnit.setText("");
        txtQTY.setText("");
        comboboxCategory.setValue("");
    }

}
