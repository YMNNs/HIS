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

import BLL.UserInfo;
import UIL.LoginForm;
import UIL.MainFrame;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

import static java.util.jar.Pack200.Packer.ERROR;


public class LoginController {
    FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXButton button1;

    @FXML
    private JFXButton button2;

    @FXML
    private JFXButton button3;

    @FXML
    private Group button2and3;

    @FXML
    private Text text1;
    private LoginForm thisWindow;


    @FXML
    void initialize() {
        warnIcon.getStyleClass().add(ERROR);
        text1.setText("登录");
        username.setVisible(true);
        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    checkUsername(new ActionEvent());
                }
            }
        });
        password.setVisible(false);
        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    checkPassword(new ActionEvent());
                }
            }
        });
        button1.setVisible(true);
        username.clear();
        username.setPromptText("用户名");
        button2and3.setVisible(false);
    }

    public void setThisWindow(LoginForm thisWindow) {
        this.thisWindow = thisWindow;
    }

    @FXML
    void checkUsername(ActionEvent event) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("请至少输入一个字符");
        validator.setIcon(warnIcon);
        username.getValidators().add(validator);
        username.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                username.validate();
            }
        });
        if (username.validate()) {
            String usernameStr = this.username.getText();
            if (usernameStr.equalsIgnoreCase(UserInfo.username)) {
                username.setVisible(false);
                password.setVisible(true);
                password.setText("");
                button1.setVisible(false);
                button2and3.setVisible(true);
                text1.setText("欢迎，" + UserInfo.username);
            } else {
                username.clear();
                username.setPromptText("用户名错误，请重试");
            }
        }
    }

    @FXML
    boolean checkPassword(ActionEvent event2) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("密码不能为空");
        warnIcon.getStyleClass().add(ERROR);
        validator.setIcon(warnIcon);
        password.getValidators().add(validator);
        password.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                password.validate();
            }
        });
        if (password.validate()) {
            String passwordStr = this.password.getText();
            if (passwordStr.equals(UserInfo.password)) {
                password.setVisible(false);
                button2and3.setVisible(false);
                showAndClose();
                return true;
            } else if (passwordStr.length() > 0) {
                password.clear();
                password.setPromptText("密码错误，请重试");
            }
        }
        return false;
    }

    @FXML
    void back(ActionEvent event3) {
        initialize();
    }

    void showAndClose() {
        thisWindow.getPrimaryStage().close();//关闭当前窗口
        MainFrame mainFrame = MainFrame.getInstance();
        Stage stage = new Stage();
        mainFrame.startMainMenu(stage);//打开新窗口
    }
}
