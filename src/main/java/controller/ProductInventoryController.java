package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductInventoryController {

    @FXML
    private TableColumn<?, ?> Catogory;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<?> cartTable;

    @FXML
    private ComboBox<?> comboCatogory;

    @FXML
    private ComboBox<?> comboSize;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> price;

    @FXML
    private TableColumn<?, ?> productId;

    @FXML
    private TableColumn<?, ?> qty;

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
    private TextField txtSupplier;

    @FXML
    private Label txtTime;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
