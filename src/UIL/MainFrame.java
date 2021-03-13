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

package UIL;

import BLL.Config;
import UIL.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MainFrame extends Application {
    private static MainFrame instance = new MainFrame();
    private Stage primaryStage = new Stage();

    public static MainFrame getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        if (Config.DEBUG) {
            HIS_cli.init();
        } else {
            HIS_cli.loadData();
        }
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("HIS");
        initMainMenu();
    }

    public void startMainMenu(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("主菜单 - HIS");
        initMainMenu();
    }

    private void initMainMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainFrame.class.getResource("view/MainMenu.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene mainMenuScene = new Scene(anchorPane);
            primaryStage.setScene(mainMenuScene);
            primaryStage.setResizable(false);
            primaryStage.show();
            MainMenuController mainMenuController = fxmlLoader.getController();
            mainMenuController.setMainFrame(this);//?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startRegistryStation(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("挂号收费工作站 - HIS");
        initRegistryStation();
    }


    private void initRegistryStation() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainFrame.class.getResource("view/RegistryStation.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene registryScene = new Scene(anchorPane);
            primaryStage.setScene(registryScene);
            primaryStage.setResizable(false);
            primaryStage.show();
            RegistryStationController registryStationController = fxmlLoader.getController();
            registryStationController.setMainFrame(this);//?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startDoctorStation(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("医生工作站 - HIS");
        initDoctorStation();
    }

    public void startRecordMgr(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("就诊记录 - HIS");
        initRecordMgr();
    }

    private void initRecordMgr() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainFrame.class.getResource("view/RecordMgr.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene recordScene = new Scene(anchorPane);
            primaryStage.setScene(recordScene);
            primaryStage.setResizable(false);
            primaryStage.show();
            RecordMgrController recordMgrController = fxmlLoader.getController();
            recordMgrController.setMainFrame(this);//?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initDoctorStation() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainFrame.class.getResource("view/DoctorStation.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene doctorScene = new Scene(anchorPane);
            primaryStage.setScene(doctorScene);
            primaryStage.setResizable(false);
            primaryStage.show();
            DoctorStationController doctorStationController = fxmlLoader.getController();
            doctorStationController.setMainFrame(this);//?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startPharmacyStation(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("药房工作站 - HIS");
        initPharmacyStation();
    }

    private void initPharmacyStation() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainFrame.class.getResource("view/PharmacyStation.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene pharmacyScene = new Scene(anchorPane);
            primaryStage.setScene(pharmacyScene);
            primaryStage.setResizable(false);
            primaryStage.show();
            PharmacyStationController pharmacyStationController = fxmlLoader.getController();
            pharmacyStationController.setMainFrame(this);//?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startNurseStation(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("护士工作站 - HIS");
        initNurseStation();
    }

    private void initNurseStation() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainFrame.class.getResource("view/NurseStation.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene nurseScene = new Scene(anchorPane);
            primaryStage.setScene(nurseScene);
            primaryStage.setResizable(false);
            primaryStage.show();
            NurseStationController nurseStationController = fxmlLoader.getController();
            nurseStationController.setMainFrame(this);//?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void alert(String p_header, String p_message) {
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("信息");
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
        _alert.initOwner(primaryStage);
        _alert.show();
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }


}