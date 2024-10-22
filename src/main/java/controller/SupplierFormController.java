package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SupplierFormController {

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
    private TableColumn<?, ?> company;

    @FXML
    private TableColumn<?, ?> email;

    @FXML
    private TableColumn<?, ?> itemId;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> supplierId;

    @FXML
    private TextField txtCompany;

    @FXML
    private Label txtDate;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private Label txtOrderID;

    @FXML
    private TextField txtSupplierId;

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
