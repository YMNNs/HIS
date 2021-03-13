package UIL.view;

import BLL.Disease;
import BLL.DiseaseType;
import BLL.Record;
import UIL.MainFrame;
import UIL.model.RecordModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.PriorityQueue;

public class RecordMgrController {
    private static ObservableList<RecordModel> recordData = FXCollections.observableArrayList();
    private MainFrame mainFrame;
    private DiseaseType diseaseType = DiseaseType.getInstance();
    @FXML
    private JFXButton returnButton;
    @FXML
    private JFXTreeView<String> diseaseTypeTree;

    @FXML
    private TableView<RecordModel> recordTable;

    @FXML
    private TableColumn<RecordModel, String> ticketIdColumn;

    @FXML
    private TableColumn<RecordModel, String> nameColumn;

    @FXML
    private TableColumn<RecordModel, String> sexColumn;

    @FXML
    private TableColumn<RecordModel, String> dateColumn;

    @FXML
    private TableColumn<RecordModel, String> diseaseTypeColumn;

    @FXML
    private TableColumn<RecordModel, String> diagnoseColumn;

    @FXML
    private TableColumn<RecordModel, String> rxColumn;

    @FXML
    private JFXButton getPatientsButton;

    @FXML
    private JFXTextField diseaseTypeQuery;

    @FXML
    private JFXButton searchDiseaseTypeButton;

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @FXML
    void getPatients(ActionEvent event) {
        recordData.clear();
        if (diseaseTypeTree.getSelectionModel().isEmpty()) {
            mainFrame.alert("错误", "未选择病种");
            return;
        }
        Disease disease = diseaseType.get(Integer.parseInt(diseaseTypeTree.getSelectionModel().getSelectedItem().getValue().split(" ")[0]));
        PriorityQueue<Record> records = disease.getAllPatients();
        if (records != null) {
            for (Record record : records) {
                recordData.add(new RecordModel(record));
            }
        }

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

    void initDiseaseTypeTree() {
        TreeItem<String> root = new TreeItem<>(diseaseType.getRoot().getId() + " " + diseaseType.getRoot().getName());
        addToDiseaseTypeTree(root);
        diseaseTypeTree.setRoot(root);
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
                    //不会发生什么
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

    @FXML
    void initialize() {
        initDiseaseTypeTree();
        diseaseTypeQuery.clear();
        diseaseTypeQuery.setDisable(false);
        searchDiseaseTypeButton.setText("搜索");
        /* 就诊记录配置 */
        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        diagnoseColumn.setCellValueFactory(new PropertyValueFactory<>("diagnose"));
        rxColumn.setCellValueFactory(new PropertyValueFactory<>("rx"));
        diseaseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("disease"));
        recordTable.setItems(recordData);
    }

}
