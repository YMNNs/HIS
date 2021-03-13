package BLL;

import DAL.DataControl;

import java.io.Serializable;
import java.util.Vector;

public class DiseaseType implements Serializable {
    private static DiseaseType diseaseType = new DiseaseType(); // 单例模式
    Disease root; // 根节点
    int maxId; // 当前最大ID

    private DiseaseType() {
        root = new Disease(0, "病", "b", -1, false);
        maxId = 0;
    }

    /**
     * 此构造器仅供解序列化使用，私自调用打断腿
     *
     * @param root  猜
     * @param maxId 猜
     */
    public DiseaseType(Disease root, int maxId) {
        this.root = root;
        this.maxId = maxId;
    }

    public static void setDiseaseType(DiseaseType diseaseType) {
        DiseaseType.diseaseType = diseaseType;
    }

    public static DiseaseType getInstance() {
        return diseaseType;
    }

    /**
     * 增加节点
     *
     * @param parent 父节点
     * @param name   名称
     * @param alias  简称
     * @return 新节点的id
     * @throws Exception 未找到父节点
     */
    public int add(Disease parent, String name, String alias) {
        if (parent == null) {
            System.err.println("未找到父节点");
            return maxId;
        }
        parent.setLeaf(false);
        parent.getSubDisease().add(new Disease(++maxId, name, alias, parent.getId(), true));
        //System.out.println("节点id = " + maxId);
        return maxId;
    }

    public Disease get(int id) {
        return root.get(id);
    }


    public Disease get(String nameOrAlias) {
        return root.get(nameOrAlias);
    }


    /**
     * 删除节点
     *
     * @param id 节点id
     * @throws Exception 节点不存在或为根节点
     */
    public void remove(int id) throws Exception {
        Disease toRemove = root.get(id);
        if (toRemove == null)
            throw new Exception("节点不存在");
        if (id == 0)
            throw new Exception("不允许删除根节点");
        System.err.println("警告：该节点的子节点也将一并被删除");
        Disease parent = get(toRemove.getParentId());
        parent.getSubDisease().removeIf(disease -> disease.getId() == id);
        if (parent.getSubDisease().isEmpty()) {
            parent.setLeaf(true);
        }
    }

    public Disease getRoot() {
        return root;
    }

    public void setRoot(Disease root) {
        this.root = root;
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public String toString() {
        return root.describe(0);
    }

    public void load() {
        try {
            DiseaseType diseaseType1 = DataControl.readDiseaseType();
            if (diseaseType1 != null)
                diseaseType = diseaseType1;
            else init();
        } catch (Exception e) {
            init();
            System.err.println("病种数据已损坏并重建");
        }
    }

    public void save() {
        DataControl.writeDiseaseType(diseaseType);
    }

    private void clear() {
        root.setSubDisease(new Vector<>());
    }

    public void init() {
        root = new Disease(0, "病", "b", -1, false);
        maxId = 0;
        add(get(0), "内科", "nk");
        add(get(0), "外科", "wk");
        add(get("内科"), "泌尿内科", "mnnk");
        add(get("内科"), "呼吸内科", "hxnk");
        add(get("内科"), "消化内科", "xhnk");
        add(get("外科"), "泌尿外科", "mnwk");
        add(get("外科"), "消化外科", "xhwk");
        add(get("外科"), "呼吸外科", "hxwk");
        add(get("泌尿内科"), "尿毒症", "ndz");
        add(get("消化内科"), "胃溃疡", "wky");
        add(get("呼吸内科"), "肺炎", "fy");
        add(get("泌尿外科"), "前列腺癌（晚期）", "qlxawq");
        add(get("消化外科"), "胃癌（晚期）", "wawq");
        add(get("呼吸外科"), "肺癌（晚期）", "fawq");
        save();
    }
}

