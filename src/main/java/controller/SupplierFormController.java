package controller;

import com.jfoenix.controls.JFXButton;
import dto.Supplier;
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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import service.ServiceFactory;
import service.custom.SupplierService;
import util.HibernateUtil;
import util.ServiceType;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<Supplier> cartTable;

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
    private ComboBox<String> comboItemID;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Supplier supplier = new Supplier(
                txtSupplierId.getText(),
                name.getText(),
                txtCompany.getText(),
                txtEmail.getText(),
                comboItemID.getValue()
        );
        if (service.addSupplier(supplier)) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier added Successfully!").show();
            txtSupplierId.setText(service.generateId());
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Add Supplier!").show();
        }
        loadTable();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
                txtSupplierId.setText("");
                name.setText("");
                txtCompany.setText("");
                txtEmail.setText("");
                comboItemID.setValue("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (service.deleteSupplier(txtSupplierId.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted Successfully").show();
            txtSupplierId.setText(service.generateId());
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Delete Supplier!").show();
        }
        loadTable();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Supplier supplier = new Supplier(
                txtSupplierId.getText(),
                name.getText(),
                txtCompany.getText(),
                txtEmail.getText(),
                comboItemID.getValue()
        );
        if (service.updateSupplier(supplier)) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Update Supplier!").show();
        }
        loadTable();

    }
    SupplierService service = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtSupplierId.setText(service.generateId());
        supplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        itemId.setCellValueFactory(new PropertyValueFactory<>("item"));
        cartTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) ->
        {
            if (null != newValue) {
                setTextToValues(newValue);
            }
        }));
        loadTable();
        loadDateAndTime();
        loadItemCodes();
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

    private void loadTable() {
        ObservableList<Supplier> suppliersList = service.getAll();
        cartTable.setItems(suppliersList);
    }

    private void setTextToValues(Supplier newValue) {
        txtSupplierId.setText(newValue.getId());
        txtEmployeeName.setText(newValue.getName());
        txtCompany.setText(newValue.getCompany());
        txtEmail.setText(newValue.getEmail());
        comboItemID.setValue(newValue.getItem());
    }

    private void loadItemCodes(){

        comboItemID.setItems(getItemCodes());
    }
    public ObservableList<String> getItemCodes(){
        ObservableList<String> itemCodes = FXCollections.observableArrayList();
        ObservableList<Supplier> all = getAll();

        all.forEach(supplier->{
            itemCodes.add(supplier.getId());
        });
        return itemCodes;

    }
    public ObservableList<Supplier> getAll() {
        ObservableList<Supplier> itemObservableList = FXCollections.observableArrayList();
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSession()) {
            // Start a transaction
            transaction = session.beginTransaction();

            // Use HQL to fetch all items
            Query<Supplier> query = session.createQuery("FROM Item", Supplier.class);
            itemObservableList.addAll(query.list());

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

        return itemObservableList;
    }
}
