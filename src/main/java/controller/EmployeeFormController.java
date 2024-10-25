package controller;

import com.jfoenix.controls.JFXButton;
import dto.Employee;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import service.ServiceFactory;
import service.custom.EmployeeService;
import util.ServiceType;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<Employee> cartTable;

    @FXML
    private TableColumn<?, ?> company;

    @FXML
    private TableColumn<?, ?> email;

    @FXML
    private TableColumn<?, ?> employeeId;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TextField txtCompany;

    @FXML
    private Label txtDate;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private Label txtOrderID;

    @FXML
    private Label txtTime;

    EmployeeService service = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Employee employee = new Employee(
                txtEmployeeId.getText(),
                txtEmployeeName.getText(),
                txtCompany.getText(),
                txtEmail.getText()
        );
        if (service.addEmployee(employee)) {
            new Alert(Alert.AlertType.INFORMATION, "Employee added Successfully!").show();
            setTextToEmpty();
            txtEmployeeId.setText(service.generateId());
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Add Employee!").show();
        }
        loadTable();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        setTextToEmpty();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (service.deleteEmployee(txtEmployeeId.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully").show();
            setTextToEmpty();
            txtEmployeeId.setText(service.generateId());
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Delete Employee!").show();
        }
        loadTable();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Employee employee = new Employee(
                txtEmployeeId.getText(),
                txtEmployeeName.getText(),
                txtCompany.getText(),
                txtEmail.getText()
        );
        if (service.updateEmployee(employee)) {
            new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully!").show();
            setTextToEmpty();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Update Employee!").show();
        }
        loadTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtEmployeeId.setText(service.generateId());
        employeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
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

    private void setTextToValues(Employee newValue) {
        txtEmployeeId.setText(newValue.getId());
        txtEmployeeName.setText(newValue.getName());
        txtCompany.setText(newValue.getCompany());
        txtEmail.setText(newValue.getEmail());
    }

    private void setTextToEmpty() {
        txtEmployeeName.setText("");
        txtCompany.setText("");
        txtEmail.setText("");
    }

    private void loadTable(){
        try{
            ObservableList<Employee> employeesList = service.getAll();
            cartTable.setItems(employeesList);
        }catch (NullPointerException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
