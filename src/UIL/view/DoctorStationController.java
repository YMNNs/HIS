/*
 * MIT License
 *
 * Copyright (c) 2019. 杨梦博
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package UIL.view;

import BLL.*;
import UIL.MainFrame;
import UIL.model.MedicineModel;
import UIL.model.RxModel;
import UIL.model.WaitingListModel;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DoctorStationController {
    private static ObservableList<MedicineModel> medicineData = FXCollections.observableArrayList();
    private MainFrame mainFrame;
    private DiseaseType diseaseType = DiseaseType.getInstance();
    private Disease confirmedDisease;
    @FXML
    private ResourceBundle resources;
    private int currentDisease;
    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private TableView<WaitingListModel> waitingTable;

    @FXML
    private TableColumn<WaitingListModel, String> id;

    @FXML
    private TableColumn<WaitingListModel, String> name;

    @FXML
    private JFXButton callNextButton;

    @FXML
    private Text currentName;

    @FXML
    private Text currentId;

    @FXML
    private JFXTextField medicineQuery;

    @FXML
    private TableView<MedicineModel> searchResult;

    @FXML
    private TableColumn<MedicineModel, String> alias;

    @FXML
    private TableColumn<MedicineModel, String> stockName;

    @FXML
    private TableColumn<MedicineModel, String> unit;

    @FXML
    private TableColumn<MedicineModel, String> usage;

    @FXML
    private TableColumn<MedicineModel, String> price;

    @FXML
    private TableColumn<MedicineModel, String> stock;

    @FXML
    private TableView<RxModel> rx;

    @FXML
    private TableColumn<RxModel, String> rxName;

    @FXML
    private TableColumn<RxModel, String> quantity;

    @FXML
    private JFXButton searchMedicineButton;

    @FXML
    private JFXButton confirmTicketButton;

    @FXML
    private JFXButton confirmRxButton;

    @FXML
    private JFXButton clearButton;

    @FXML
    private JFXTextArea diagnose;

    @FXML
    private JFXButton addToRxButton;

    @FXML
    private JFXTextField quantityBox;

    @FXML
    private JFXButton deleteFromRxButton;

    @FXML
    private JFXButton returnButton;

    @FXML
    private JFXComboBox<String> selectDoctorBox;

    @FXML
    private JFXTreeView<String> diseaseTypeTree;

    @FXML
    private JFXButton confirmDiseaseButton;

    @FXML
    private JFXTextField diseaseTypeQuery;

    private boolean isExam;

    @FXML
    private JFXButton searchDiseaseTypeButton;

    private ItemMgr itemMgr = ItemMgr.getInstance();
    private DoctorMgr doctorMgr = DoctorMgr.getInstance();
    //private Doctor doctor = doctorMgr.getById(1); //示例数据
    private Doctor doctor;
    private ObservableList<WaitingListModel> waitingData = FXCollections.observableArrayList();
    private ObservableList<RxModel> rxData = FXCollections.observableArrayList();
    private Registry registry = Registry.getInstance();
    private Pharmacy pharmacy = Pharmacy.getInstance();
    private Rx rxBLL;
    private Ticket ticket;

    public static void addToResult(Medicine medicine, int stock) {
        medicineData.add(new MedicineModel(medicine, stock));
    }

    public static void addToResult(Item item) {
        medicineData.add(new MedicineModel(item));
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    void addWaitingList() {
        try {
            waitingData.clear();
            if (doctor.getQueue().isEmpty()) {
                waitingTable.setItems(waitingData);
                return;
            }
            for (Ticket ticket : doctor.getQueue()) {
                waitingData.add(new WaitingListModel(ticket));
            }
            waitingTable.setItems(waitingData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addToRx(ActionEvent event) {
        if (searchResult.getSelectionModel().isEmpty()) {
            return;
        }
        MedicineModel selectedMedicine = searchResult.getSelectionModel().getSelectedItem();
        int quantityValue = 1;
        if (quantityBox.getText().matches("^[1-9]\\d*$")) {
            quantityValue = Integer.parseInt(quantityBox.getText());
        }
        if (itemMgr.get(selectedMedicine.getAlias()) == null) { //是药品则不能添加检查
            if (!isExam || rxData.size() == 0) {
                if (pharmacy.lockStock(pharmacy.getExactMedicine(selectedMedicine.getAlias()), quantityValue)) {
                    rxData.add(new RxModel(selectedMedicine, Integer.parseInt(quantityBox.getText()), 1));
                    isExam = false;
                } else {
                    mainFrame.alert("错误", "库存不足");
                    System.err.println("库存不足");
                }
            } else {
                mainFrame.alert("错误", "该处方为检查处方，不能添加药品");
                System.err.println("该处方为检查处方，不能添加药品");
            }
        } else { //是检查则不能添加药品
            if (isExam || rxData.size() == 0) {
                rxData.add(new RxModel(selectedMedicine, Integer.parseInt(quantityBox.getText()), 2));
                isExam = true;
            } else {
                mainFrame.alert("错误", "该处方为药品处方，不能添加检查");
                System.err.println("该处方为药品处方，不能添加检查");
            }
        }
        quantityBox.setText("");

    }

    @FXML
    void callNext(ActionEvent event) {
        doctor.callNext();
        currentId.setText("病历号：" + doctor.getCurrentTicketID() + "（未确认）");
        currentName.setText("当前患者：" + registry.getById(doctor.getCurrentTicketID()).getName());
        addWaitingList();
        waitingTable.refresh();
        confirmTicketButton.setDisable(false);
    }

    @FXML
    void clear(ActionEvent event) {
        rxData.clear();
    }

    //确认处方
    @FXML
    void confirmRx(ActionEvent event) {
        if (currentDisease == 0) {
            mainFrame.alert("错误", "未指定病种");
            return;
        }
        if (rxData.size() == 0) {
            mainFrame.alert("错误", "未开具处方");
            return;
        }

        for (RxModel rxDatum : rxData) {
            if (rxDatum.getType() == 1)
                rxBLL.addMedicine(pharmacy.getExactMedicine(rxDatum.getAlias()), Integer.parseInt(rxDatum.getQuantity()));
            else rxBLL.addMedicine(itemMgr.get(rxDatum.getAlias()), Integer.parseInt(rxDatum.getQuantity()));
        }
        rxBLL.setStatus(0);
        ticket.setRx(rxBLL);
        Order order = new Order();
        order.add(rxBLL.toItem());
        if (isExam) {
            System.out.println("订单类型：检查");
            order.setType(2);
        } else {
            order.setType(1);
            System.out.println("订单类型：药品");
        }

        order.settleForProceed();
        registry.getById(doctor.getCurrentTicketID()).addOrders(order);
        doctor.addRecord(diagnose.getText(), rxBLL, currentDisease);

        mainFrame.alert("成功", "患者" + ticket.getName() + "已确认处方");
        doctor.setCurrentTicketID(null);
        ticket = null;
        //initialize();
        afterConfirmRx();
    }

    @FXML
    void confirmTicket(ActionEvent event) {
        doctor.confirmTicket();
        currentId.setText("病历号：" + doctor.getCurrentTicketID());
        confirmTicketButton.setDisable(true);
        System.out.println("已确认");
        //准备处方
        confirmRxButton.setDisable(false);
        ticket = registry.getById(doctor.getCurrentTicketID());
        rxBLL = new Rx();
        addToRxButton.setDisable(false);
        deleteFromRxButton.setDisable(false);
        confirmDiseaseButton.setDisable(false);
    }

    @FXML
    void deleteFromRx(ActionEvent event) {
        if (rx.getSelectionModel().isEmpty()) {
            return;
        }
        RxModel selectedMedicine = rx.getSelectionModel().getSelectedItem();
        rxData.remove(selectedMedicine);
    }

    @FXML
    void goMainMenu(ActionEvent event) {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.startMainMenu(mainFrame.getPrimaryStage());
    }

    @FXML
    void searchDiseaseType(ActionEvent event) {
        if (searchDiseaseTypeButton.getText().equals("清空筛选条件")) {
            diseaseTypeQuery.clear();
            initDiseaseTypeTree();
            searchDiseaseTypeButton.setText("搜索");
            diseaseTypeQuery.setDisable(false);
            return;
        }
        diseaseFilter(diseaseTypeTree.getRoot());
        if (diseaseTypeTree.getRoot().getChildren().size() == 0) {
            mainFrame.alert("搜索病种", "未找到结果");
            initDiseaseTypeTree();
            diseaseTypeQuery.clear();
        } else {
            diseaseTypeQuery.clear();
            searchDiseaseTypeButton.setText("清空筛选条件");
            diseaseTypeQuery.setDisable(true);
        }
    }

    /**
     * 递归清除不符合条件的病种
     *
     * @param root 用于过滤的根节点
     */
    void diseaseFilter(TreeItem<String> root) {
        if (!root.isLeaf()) {
            if (root.getChildren().size() != 0) {
                try {
                    for (TreeItem<String> treeItem : root.getChildren()) {
                        diseaseFilter(treeItem);
                        if (root.getChildren().size() == 0) {
                            root.getParent().getChildren().remove(root);
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            } else {
                TreeItem<String> parent = root.getParent();
                parent.getChildren().remove(root);
                diseaseFilter(parent);
            }
        } else {
            Disease disease = diseaseType.get(Integer.parseInt(root.getValue().split(" ")[0]));
            if (!disease.getName().contains(diseaseTypeQuery.getText()) && !disease.getAlias().contains(diseaseTypeQuery.getText())) {
                TreeItem<String> parent = root.getParent();
                parent.getChildren().remove(root);
                diseaseFilter(parent);
            }
        }
    }

    @FXML
    void confirmDisease(ActionEvent event) {
        String diseaseStr = diseaseTypeTree.getSelectionModel().getSelectedItem().getValue();
        if (!diseaseTypeTree.getSelectionModel().getSelectedItem().isLeaf()) {
            mainFrame.alert("错误", "选定的层级不可添加病人");
            return;
        }
        if (diseaseStr.length() > 0) {
            currentDisease = Integer.parseInt(diseaseStr.split(" ")[0]);
            confirmedDisease = diseaseType.get(currentDisease);
            confirmedDisease.add(ticket.getId());
            confirmDiseaseButton.setDisable(true);

        }
    }

    @FXML
    void searchMedicine(ActionEvent event) {
        if (searchMedicineButton.getText().equals("清空筛选条件")) {
            medicineData.clear();
            medicineQuery.clear();
            searchMedicineButton.setText("搜索");
            medicineQuery.setDisable(false);
            return;
        }
        medicineData.clear();
        pharmacy.search(medicineQuery.getText());
        itemMgr.search(medicineQuery.getText());
        medicineQuery.clear();
        searchMedicineButton.setText("清空筛选条件");
        medicineQuery.setDisable(true);
        if (medicineData.size() == 0) {
            mainFrame.alert("搜索药品或检查", "未找到结果");
        }
    }

    void afterConfirmRx() {
        medicineQuery.setDisable(false);
        medicineQuery.clear();
        searchMedicineButton.setText("搜索");
        searchMedicineButton.setDisable(false);
        medicineData.clear();
        quantityBox.clear();
        addToRxButton.setDisable(true);
        deleteFromRxButton.setDisable(true);
        diagnose.clear();
        confirmDiseaseButton.setDisable(true);
        confirmRxButton.setDisable(true);
        confirmTicketButton.setDisable(true);
        diseaseTypeQuery.clear();
        diseaseTypeQuery.setDisable(false);
        searchDiseaseTypeButton.setDisable(false);
        searchDiseaseTypeButton.setText("搜索");
        rxData.clear();
        initDiseaseTypeTree();
        addWaitingList();
        currentId.setText("病历号：无");
        currentName.setText("当前患者：无");
        currentDisease = 0;
    }

    @FXML
    void initialize() {
        isExam = false;
        currentName.setText("当前患者：无");
        currentId.setText("病历号：无");
        diseaseTypeQuery.clear();
        diagnose.clear();
        medicineQuery.clear();
        quantityBox.clear();
        id.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        waitingTable.setItems(waitingData);
        alias.setCellValueFactory(new PropertyValueFactory<>("alias"));
        stockName.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        usage.setCellValueFactory(new PropertyValueFactory<>("usage"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        searchResult.setItems(medicineData);
        medicineData.clear();
        //addToRxButton.setDisable(true);
        confirmRxButton.setDisable(true);
        rxName.setCellValueFactory(new PropertyValueFactory<>("rxName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        rx.setItems(rxData);
        callNextButton.setDisable(true);
        confirmTicketButton.setDisable(true);
        confirmDiseaseButton.setDisable(true);
        rxData.clear();
        ticket = null;
        rxBLL = null;
        isExam = false;
        selectDoctorBox.getItems().clear();
        for (Doctor mgrDoctor : doctorMgr.getDoctors()) {
            selectDoctorBox.getItems().add(mgrDoctor.getName());
        }
        confirmTicketButton.setDisable(true);
        addToRxButton.setDisable(true);
        deleteFromRxButton.setDisable(true);
        initDiseaseTypeTree();
        currentDisease = 0;

    }

    @FXML
    void selectDoctor(ActionEvent event) {
        doctor = doctorMgr.getByName(selectDoctorBox.getValue());
        addWaitingList();
        if (waitingData.size() > 0)
            callNextButton.setDisable(false);
    }

    void initDiseaseTypeTree() {
        TreeItem<String> root = new TreeItem<>(diseaseType.getRoot().getId() + " " + diseaseType.getRoot().getName());
        addToDiseaseTypeTree(root);
        diseaseTypeTree.setRoot(root);
    }


    /**
     * 递归向相对根节点中添加子节点
     *
     * @param relativelyRootItem 相对根节点
     */
    void addToDiseaseTypeTree(TreeItem<String> relativelyRootItem) {
        Disease relativelyRoot = diseaseType.get(Integer.parseInt(relativelyRootItem.getValue().split(" ")[0]));
        for (Disease disease : relativelyRoot.getSubDisease()) {
            TreeItem<String> newItem = new TreeItem<>(disease.getId() + " " + disease.getName());
            if (disease.getSubDisease().size() != 0)
                addToDiseaseTypeTree(newItem);
            relativelyRootItem.getChildren().add(newItem);
        }
    }
}
