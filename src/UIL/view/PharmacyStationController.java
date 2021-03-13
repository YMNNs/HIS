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
import UIL.model.PharmacyModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class PharmacyStationController {
    private MainFrame mainFrame;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private JFXTextField ticketId;

    @FXML
    private Text name;

    @FXML
    private Text rxId;

    @FXML
    private TableView<PharmacyModel> medicineTable;

    @FXML
    private TableColumn<PharmacyModel, String> alias;

    @FXML
    private TableColumn<PharmacyModel, String> medicineName;

    @FXML
    private TableColumn<PharmacyModel, String> quantity;

    @FXML
    private TableColumn<PharmacyModel, String> unit;

    @FXML
    private TableColumn<PharmacyModel, String> usage;

    @FXML
    private JFXButton confirmOfferButton;

    @FXML
    private Text status;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXButton returnButton;
    private ObservableList<PharmacyModel> data = FXCollections.observableArrayList();
    private Registry registry = Registry.getInstance();
    private Ticket ticket;
    private Rx rx;

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @FXML
    void cancel(ActionEvent event) {
        initialize();
    }

    @FXML
    void confirmOffer(ActionEvent event) {
        data.clear();
        rx.setStatus(2);
        mainFrame.alert("成功", "患者" + ticket.getName() + "已取药");
        initialize();
    }

    @FXML
    void goMainMenu(ActionEvent event) {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.startMainMenu(mainFrame.getPrimaryStage());
    }

    @FXML
    void search(ActionEvent event) {
        data.clear();
        String id = ticketId.getText();
        if (registry.getById(id) == null)
            return;
        ticket = registry.getById(id);
        name.setText(ticket.getName());
        if (ticket.getRx() == null) {
            rxId.setText("无");
            return;
        }
        rx = ticket.getRx();
        rxId.setText(ticket.getRx().getId() + "");
        switch (rx.getStatus()) {
            case 0: {
                status.setText("未支付");
                disable();
                break;
            }
            case 1: {
                status.setText("已支付");
                enable();
                break;
            }
            case 2: {
                status.setText("已取药");
                disable();
                break;
            }
            case 3: {
                status.setText("无效");
                disable();
                break;
            }
        }
        loadData();
        ticketId.clear();

    }

    @FXML
    void initialize() {
        ticketId.setText("");
        name.setText("");
        rxId.setText("");
        status.setText("");
        alias.setCellValueFactory(new PropertyValueFactory<>("alias"));
        medicineName.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        usage.setCellValueFactory(new PropertyValueFactory<>("usage"));
        unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        medicineTable.setItems(data);
        data.clear();
        disable();
    }

    void disable() {
        confirmOfferButton.setDisable(true);
    }

    void enable() {
        confirmOfferButton.setDisable(false);
    }

    void loadData() {
        data.clear();
        for (Item item : rx.getMedicineList().keySet()) {
            data.add(new PharmacyModel((Medicine) item, rx.getMedicineList().get(item)));
        }
    }


}
