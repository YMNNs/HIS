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
import Common.Generator;
import UIL.MainFrame;
import UIL.model.ItemModel;
import com.gluonhq.charm.glisten.control.ToggleButtonGroup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistryStationController {

    private MainFrame mainFrame;
    private Order order;
    private ItemMgr itemMgr = ItemMgr.getInstance();
    private DepartmentMgr departmentMgr = DepartmentMgr.getInstance();
    private DoctorMgr doctorMgr = DoctorMgr.getInstance();
    private int departmentId;
    private ObservableList<ItemModel> data = FXCollections.observableArrayList();
    private Ticket ticket;
    private Cashier cashier = Cashier.getInstance();
    private Registry registry = Registry.getInstance();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private GridPane gridPane;
    @FXML
    private JFXTextField inputAge;
    @FXML
    private JFXTextField inputName;
    @FXML
    private JFXComboBox<String> selectPayMethod;
    @FXML
    private JFXTextField inputIdNumber;
    @FXML
    private JFXTextField inputAddress;
    @FXML
    private JFXComboBox<String> selectDepartment;
    @FXML
    private JFXComboBox<String> selectPriority;
    @FXML
    private JFXButton selectDoctorButton;
    @FXML
    private JFXButton addMedicalRecordButton;
    @FXML
    private JFXButton registerButton;
    @FXML
    private ToggleButtonGroup selectSex;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private JFXTextField inputTicketId;
    @FXML
    private TableView<ItemModel> orderTable;
    @FXML
    private TableColumn<ItemModel, String> item;
    @FXML
    private TableColumn<ItemModel, Integer> quantity;
    @FXML
    private TableColumn<ItemModel, String> price;
    @FXML
    private TableColumn<ItemModel, String> subtotal;
    @FXML
    private TableColumn<ItemModel, String> status;
    @FXML
    private JFXButton proceedButton;
    @FXML
    private JFXButton refundButton;
    @FXML
    private Text toPay;
    @FXML
    private Text toRefund;
    @FXML
    private JFXButton returnButton;
    @FXML
    private JFXButton searchButton;

    public RegistryStationController() {
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @FXML
    void search(ActionEvent event) {
        Ticket result = null;
        data.clear();
        String id = inputTicketId.getText();
        String idNumber = inputIdNumber.getText();
        if (id.length() == 0) {
            if (idNumber.length() == 0) {
                inputTicketId.clear();
                inputIdNumber.clear();
            } else {
                result = registry.getByIdNumber(idNumber);

            }
        } else {
            result = registry.getById(id);
        }
        if (result == null) {
            inputTicketId.clear();
            inputIdNumber.clear();
            mainFrame.alert("搜索", "未找到结果");
            return;
        }
        ticket = result;
        inputName.setText(result.getName());
        inputAddress.setText(result.getAddress());
        inputAge.setText(result.getAge() + "");
        inputTicketId.setText(ticket.getId());
        inputIdNumber.setText(ticket.getIdNumber());
        if (result.isSex()) {
            male.setSelected(true);
            female.setSelected(false);
        } else {
            male.setSelected(false);
            female.setSelected(true);
        }
        inputAge.setText(result.getAge() + "");
        //根据搜索结果填写表单，然后载入订单
        order = result.getOrders(result.getOrders().size() - 1);
        if (order == null)
            return;
        if (order.getStatus() != 0 && order.getStatus() != 1)
            return;
        for (Item orderItem : order.getItems()) {
            addToTable(orderItem);
        }
        if (order.getStatus() == 0) {
            proceedButton.setDisable(false);
        } else if (order.getStatus() == 1) {
            refundButton.setDisable(false);
        }
    }

    @FXML
    void addMedicalRecord(ActionEvent event) {
        order.add(itemMgr.get("blb"));
        addToTable(itemMgr.get("blb"));
    }

    @FXML
    void goMainMenu(ActionEvent event) {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.startMainMenu(mainFrame.getPrimaryStage());
    }

    @FXML
    void proceed(ActionEvent event) {
        if (order == null)
            return;
        order.settleForProceed();
        if (!ticket.getOrders().contains(order)) {
            ticket.addOrders(order);
        }

        if (cashier.proceed(order, ticket.getPayMethod())) {
            //ticket.removeOrder(order);
            switch (order.getType()) {
                case 0: {
                    ticket.setStatus(true);
                    registry.register(ticket);
                    break;
                }
                case 1: {
                    ticket.getRx().setStatus(1);
                    break;
                }
                case 2: { //检查完成后重新加入队列
                    ticket.getRx().setStatus(1);
                    ticket.setPriority(1);
                    ticket.setRealPriority(2); //确定排队位置
                    ticket.getDepartment().getQueue().add(ticket);
                    break;
                }
            }

        }
        //成功
        mainFrame.alert("成功", "病历号：" + ticket.getId() + "，患者" + ticket.getName() + "已付款");
        data.clear();
        initialize();
    }

    @FXML
    void refund(ActionEvent event) {
        if (order == null)
            return;
        if (order.getType() == 0 && order.getStatus() == 1) { //退号
            order.settleForRefund();
            cashier.refund(order, ticket.getPayMethod());
            registry.cancelTicket(ticket);
            order.setStatus(2);
            data.clear();
            mainFrame.alert("成功", "病历号：" + ticket.getId() + "，患者" + ticket.getName() + "已退号");
            ticket.removeOrder(order);
            initialize();
        } else if (order.getType() == 1 && order.getStatus() == 1) { //退药费
            order.settleForRefund();
            cashier.refund(order, ticket.getPayMethod());
            ticket.getRx().setStatus(3);
            order.setStatus(2);
            ticket.removeOrder(order);
            data.clear();
            mainFrame.alert("成功", "病历号：" + ticket.getId() + "，患者" + ticket.getName() + "已退药费");
            ticket.removeOrder(order);
            initialize();
        }
        initialize();
        registry.save();
    }

    @FXML
    void register(ActionEvent event) {
        //未做数据验证
        boolean sexBool = male.isSelected();
        int payMethod;
        if (selectPayMethod.getValue() == null)
            payMethod = 0;
        else if (selectPayMethod.getValue().equals("现金")) {
            payMethod = 0;
        } else if (selectPayMethod.getValue().equals("刷卡")) {
            payMethod = 1;
        } else {
            payMethod = 2;
        }
        /*int doctorId = 0;
        try {
            doctorId = doctorMgr.getByName(selectDoctor.getValue()).getId();
        } catch (Exception e) {
            mainFrame.alert("错误", "未选择医生");
            initialize();
            return;
        }*/
        int priority = 2;
        switch (selectPriority.getValue()) {
            case "复诊": {
                priority = 1;
                break;
            }
            case "初诊": {
                priority = 2;
                break;
            }
            case "加急": {
                priority = 3;
                break;
            }

        }
        departmentId = departmentMgr.get(selectDepartment.getValue()).getId();
        Ticket old = registry.getByIdNumber(inputIdNumber.getText());
        if (old == null) {
            ticket = new Ticket(Generator.randomCapsString(6), inputName.getText(), sexBool, Integer.parseInt(inputAge.getText()), payMethod, inputIdNumber.getText(), inputAddress.getText(), departmentId, priority);
        } else {
            ticket = old;
            ticket.setDepartmentId(departmentMgr.get(selectDepartment.getValue()).getId());
            ticket.setPriority(priority);
            ticket.setPayMethod(payMethod);
            //不考虑退款，因为订单系统有BUG
            ticket.getOrders().clear();
            data.clear();
        }
        //Order order = new Order();
        order.setType(0);
        order.add(ticket.toItem());
        addToTable(ticket.toItem());
        //清空
        reset();
        proceedButton.setDisable(false);
        refundButton.setDisable(true);
        //selectDoctor.getItems().clear();
    }

    void reset() {
        inputTicketId.clear();
        inputName.clear();
        inputTicketId.clear();
        inputAge.clear();
        inputAddress.clear();
        inputIdNumber.clear();
        selectSex.getToggles().get(0).setSelected(false);
        selectSex.getToggles().get(1).setSelected(false);
        selectDepartment.getItems().clear();
        loadPriority();
    }

    void loadPriority() {
        selectPriority.getItems().clear();
        selectPriority.getItems().addAll("初诊", "加急");
    }

    @FXML
    void initialize() {
        reset();
        selectPriority.getItems().clear();
        selectPayMethod.getItems().clear();
        selectPayMethod.getItems().addAll("现金", "刷卡", "其他"); //对应0,1,2
        toPay.setText("应收：0元");
        toRefund.setText("应退：0元");
        item.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        subtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        status.setCellValueFactory(new PropertyValueFactory<>("refundable"));
        orderTable.setItems(data);
        data.clear();
        loadDepartment();
        proceedButton.setDisable(true);
        refundButton.setDisable(true);
        ticket = null;
        order = new Order();
        loadPriority();
        //orderTable.getColumns().addAll(item,quantity,price,subtotal,status);
    }

    void loadDepartment() {
        for (Department department : departmentMgr.getDepartments()) {
            selectDepartment.getItems().add(department.getName());
        }
    }

    /*void loadDoctors(ActionEvent event) {
        if (selectDepartment.getValue() == null) {
            selectPriority.getItems().clear();
            return;
        }

        selectPriority.getItems().clear();
        Department selectedDepartment = departmentMgr.get(selectDepartment.getValue());
        departmentId = selectedDepartment.getId();
        for (Integer integer : selectedDepartment.getAvailableDoctors().keySet()) {
            //获取医生Id并添加到下拉菜单
            selectPriority.getItems().add(doctorMgr.getById(integer).getName());
        }
    }*/


    void addToTable(Item toAdd) {
        data.add(new ItemModel(toAdd.getName(), toAdd.getQuantity(), toAdd.getPrice(), toAdd.isRefundable()));
        if (order.getStatus() == 0) {
            toPay.setText("应收：" + order.getAmount() + "元");
            toRefund.setText("应退：0元");
        }
        if (order.getStatus() == 1) {
            toRefund.setText("应退：" + order.getAmount() + "元");
            toPay.setText("应收：0元");
        }

    }
}