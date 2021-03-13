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

import BLL.Config;
import BLL.UserInfo;
import UIL.MainFrame;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Text text1;

    @FXML
    private JFXButton exit;

    @FXML
    private Text text11;

    @FXML
    private Button registryStation;

    @FXML
    private Button doctorStation;

    @FXML
    private Button pharmacyStation;

    @FXML
    private Button departmentMgr;

    @FXML
    private Button doctorMgr;
    @FXML
    private Button recordMgr;
    @FXML
    private Text debugText;
    @FXML
    private Button shiftMgr;

    @FXML
    private Button NurseStation;

    @FXML
    private Button itemMgr;

    private MainFrame mainFrame;

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @FXML
    void close(ActionEvent event) {
        mainFrame.getPrimaryStage().close();//关闭当前窗口
    }

    @FXML
    void toDepartmentMgr(ActionEvent event) {

    }

    @FXML
    void toDoctorMgr(ActionEvent event) {

    }

    @FXML
    void toDoctorStation(ActionEvent event) {
        mainFrame.getPrimaryStage().close();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.startDoctorStation(mainFrame.getPrimaryStage());
    }

    @FXML
    void toRecordMgr(ActionEvent event) {
        mainFrame.getPrimaryStage().close();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.startRecordMgr(mainFrame.getPrimaryStage());
    }

    @FXML
    void toPharmacyStation(ActionEvent event) {
        mainFrame.getPrimaryStage().close();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.startPharmacyStation(mainFrame.getPrimaryStage());
    }

    @FXML
    void toRegistryStation(ActionEvent event) {
        mainFrame.getPrimaryStage().close();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.startRegistryStation(mainFrame.getPrimaryStage());
    }

    @FXML
    void toNurseStation(ActionEvent event) {
        mainFrame.getPrimaryStage().close();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.startNurseStation(mainFrame.getPrimaryStage());
    }

    @FXML
    void toShiftMgr(ActionEvent event) {

    }

    @FXML
    void toStoreMgr(ActionEvent event) {

    }

    @FXML
    void initialize() {
        setUsername();
        departmentMgr.setDisable(true);
        doctorMgr.setDisable(true);
        shiftMgr.setDisable(true);
        itemMgr.setDisable(true);
        debugText.setVisible(Config.DEBUG);
    }


    private void setUsername() {
        text1.setText("欢迎，" + UserInfo.username);
    }

}
