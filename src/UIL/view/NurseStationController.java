package UIL.view;

import BLL.*;
import UIL.MainFrame;
import UIL.model.DoctorModel;
import UIL.model.QueueModel;
import UIL.model.RecordModel;
import UIL.model.WaitingListModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Map;

public class NurseStationController {

    private static ObservableList<QueueModel> queueData = FXCollections.observableArrayList();
    private static ObservableList<RecordModel> recordData = FXCollections.observableArrayList();
    private static ObservableList<DoctorModel> doctorData = FXCollections.observableArrayList();
    private static ObservableList<WaitingListModel> doctorQueueData = FXCollections.observableArrayList();
    private DepartmentMgr departmentMgr = DepartmentMgr.getInstance();
    private DoctorMgr doctorMgr = DoctorMgr.getInstance();
    private Department department;
    private Ticket currentTicket;

    private MainFrame mainFrame;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private JFXButton returnButton;

    @FXML
    private JFXComboBox<String> selectDepartmentBox;

    @FXML
    private TableView<QueueModel> queueTable;

    @FXML
    private TableColumn<QueueModel, String> ticketIdColumn;

    @FXML
    private TableColumn<QueueModel, String> nameColumn;

    @FXML
    private TableColumn<QueueModel, String> priorityColumn;

    @FXML
    private TableView<RecordModel> recordTable;

    @FXML
    private TableColumn<RecordModel, String> dateColumn;

    @FXML
    private TableColumn<RecordModel, String> departmentColumn;

    @FXML
    private TableColumn<RecordModel, String> doctorColumn;

    @FXML
    private TableColumn<RecordModel, String> diagnoseColumn;

    @FXML
    private TableColumn<RecordModel, String> rxColumn;

    @FXML
    private TableView<DoctorModel> doctorTable;

    @FXML
    private TableColumn<DoctorModel, String> doctorNameColumn;

    @FXML
    private TableColumn<DoctorModel, String> remainingTicketsColumn;

    @FXML
    private JFXButton addToDoctorQueueButton;

    @FXML
    private TableView<WaitingListModel> doctorQueueTable;

    @FXML
    private TableColumn<WaitingListModel, String> ticketIdInDoctor;

    @FXML
    private TableColumn<WaitingListModel, String> nameInDoctor;

    @FXML
    private JFXButton selectTicketButton;

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    // TODO 更新就诊记录
    @FXML
    void addToDoctorQueue(ActionEvent event) {
        // 重点方法：根据挂号级别加入优先队列
        if (queueData.size() == 0)
            return;
        if (doctorTable.getSelectionModel().isEmpty())
            return;
        Doctor doctor = doctorMgr.getById(doctorTable.getSelectionModel().getSelectedItem().getId());
        doctor.getQueue().add(department.getQueue().poll());
        department.modifyShift(doctor, true);
        updateQueue();
        updateDoctorData();
        updateDoctorQueueData(doctor);
        if (queueData.isEmpty())
            addToDoctorQueueButton.setDisable(true);
    }

    void updateDoctorQueueData(Doctor doctor) {
        doctorQueueData.clear();
        for (Ticket ticket : doctor.getQueue()) {
            doctorQueueData.add(new WaitingListModel(ticket));
        }
    }


    @FXML
    void updateDoctorQueue(ActionEvent event) {
        if (doctorTable.getSelectionModel().isEmpty()) {
            return;
        }
        DoctorModel doctorModel = doctorTable.getSelectionModel().getSelectedItem();
        Doctor doctor = doctorMgr.getById(doctorModel.getId());
        doctorQueueData.clear();
        for (Ticket ticket : doctor.getQueue()) {
            doctorQueueData.add(new WaitingListModel(ticket));
        }
        if (queueData.isEmpty())
            addToDoctorQueueButton.setDisable(true);
        else addToDoctorQueueButton.setDisable(false);
    }

    @FXML
    void goMainMenu(ActionEvent event) {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.startMainMenu(mainFrame.getPrimaryStage());
    }

    @FXML
    void selectDepartment(ActionEvent event) {
        department = departmentMgr.get(selectDepartmentBox.getValue());
        updateDoctorData();
        updateQueue();
    }

    void updateQueue() {
        queueData.clear();
        for (Ticket ticket : department.getQueue()) {
            queueData.add(new QueueModel(ticket));
        }
        if (queueData.size() > 0) {
            currentTicket = department.getQueue().peek();
            queueTable.getSelectionModel().select(0);
            selectTicketButton.setDisable(false);
        } else {
            currentTicket = null;
            selectTicketButton.setDisable(true);
        }
    }

    @FXML
    void selectTicket(ActionEvent event) {
        if (queueTable.getSelectionModel().isEmpty()) {
            mainFrame.alert("错误", "未选择病人");
            return;
        }
        currentTicket = queueTable.getSelectionModel().getSelectedItem().getTicket();
        updateRecordData();
    }

    void updateRecordData() {
        recordData.clear();
        if (currentTicket == null) {
            return;
        }
        RecordMgr recordMgr = RecordMgr.getInstance();
        ArrayList<Record> records = recordMgr.get(currentTicket.getId());
        if (records != null && !records.isEmpty()) {
            for (Record record : records) {
                recordData.add(new RecordModel(record));
            }
        }
    }

    void updateDoctorData() {
        doctorData.clear();
        for (Map.Entry<Integer, Integer> integerIntegerEntry : department.getAvailableDoctors().entrySet()) {
            doctorData.add(new DoctorModel(doctorMgr.getById(integerIntegerEntry.getKey()), integerIntegerEntry.getValue()));
        }
    }

    @FXML
    void initialize() {

        addToDoctorQueueButton.setDisable(true);
        selectDepartmentBox.getItems().clear();
        for (Department department : departmentMgr.getDepartments()) {
            selectDepartmentBox.getItems().add(department.getName());
        }
        department = null;
        /* 科室候诊队列配置 */
        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        queueTable.setItems(queueData);
        /* 就诊记录配置 */
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        diagnoseColumn.setCellValueFactory(new PropertyValueFactory<>("diagnose"));
        rxColumn.setCellValueFactory(new PropertyValueFactory<>("rx"));
        recordTable.setItems(recordData);
        /* 医生列表配置 */
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        remainingTicketsColumn.setCellValueFactory(new PropertyValueFactory<>("ticketsRemaining"));
        doctorTable.setItems(doctorData);
        /* 医生候诊列表配置 */
        ticketIdInDoctor.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        nameInDoctor.setCellValueFactory(new PropertyValueFactory<>("name"));
        doctorQueueTable.setItems(doctorQueueData);
        /* 结束 */
        queueData.clear();
        doctorQueueData.clear();
        doctorData.clear();
        recordData.clear();
        selectTicketButton.setDisable(true);

    }

}
