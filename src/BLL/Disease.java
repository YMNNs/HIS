package BLL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Vector;

public class Disease implements Serializable {
    private int id; // 病种ID
    private String name; // 名称
    private String alias; // 简称
    private int parentId; // 父节点的ID
    private Vector<Disease> subDisease; // 子节点
    private Vector<String> tickets; // 所属病人
    private boolean isLeaf; // 是否为叶子结点

    Disease(int id, String name, String alias, int parentId, boolean isLeaf) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.tickets = new Vector<>();
        this.subDisease = new Vector<>();
        this.isLeaf = isLeaf;
        this.alias = alias;
    }


    /**
     * 通过id递归查找
     *
     * @param id 病种id
     * @return 目标节点
     */
    Disease get(int id) {
        Disease result;
        if (id == this.id) {
            return this;
        } else {
            for (Disease disease : this.subDisease) {
                result = disease.get(id);
                if (result != null)
                    return result;
            }
        }
        return null;
    }

    /**
     * 通过名称或简称递归查找
     *
     * @param nameOrAlias 病种名称或简称
     * @return 目标节点
     */
    Disease get(String nameOrAlias) {
        Disease result;
        if (this.name.contains(nameOrAlias) || this.alias.contains(nameOrAlias)) {
            return this;
        } else {
            for (Disease disease : this.subDisease) {
                result = disease.get(nameOrAlias);
                if (result != null)
                    return result;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Vector<Disease> getSubDisease() {
        return subDisease;
    }

    public void setSubDisease(Vector<Disease> subDisease) {
        this.subDisease = subDisease;
    }

    public Vector<String> getTickets() {
        return tickets;
    }

    public void setTickets(Vector<String> tickets) {
        this.tickets = tickets;
    }

    public void add(String ticketId) {
        if (!isLeaf)
            System.err.println("该节点不可添加病人");
        if (!tickets.contains(ticketId))
            tickets.add(ticketId);
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }


    public String describe() {
        return describe(1);
    }

    public String describe(int depth) {
        Registry registry = Registry.getInstance();
        StringBuilder result = new StringBuilder();
        result.append("ID:").append(this.id)
                .append("\t名称:").append(this.name).append("\t简称:").append(this.alias);
        if (!isLeaf) {
            result.append("\t子节点数量:").append(this.subDisease.size());
        } else {
            result.append("\t病人数量:").append(this.tickets.size()).append("\t");
            result.append("病人名单：").append("\t");
            for (String ticketId : tickets) {
                Ticket ticket = registry.getById(ticketId);
                result.append(ticketId).append("\t").append(ticket.getName()).append("\t");
            }
        }
        result.append("\n");
        if (this.subDisease.size() > 0) {
            for (Disease disease : this.subDisease) {
                for (int i = 0; i <= depth; i++) {
                    result.append("\t");
                }
                result.append(disease.describe(depth + 1));
            }
            depth--;
        }
        depth--;
        return result.toString();
    }

    /**
     * 递归获取当前病种和所有子病种的就诊记录
     *
     * @return 以日期排序的队列
     */
    public PriorityQueue<Record> getAllPatients() {
        PriorityQueue<Record> result = new PriorityQueue<>();
        if (isLeaf) {
            RecordMgr recordMgr = RecordMgr.getInstance();
            for (String s : tickets) {
                ArrayList<Record> records = recordMgr.get(s);
                if (records != null) {
                    result.addAll(records);
                }
            }
        } else {
            for (Disease disease : subDisease) {
                result.addAll(disease.getAllPatients());
            }
        }
        return result;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
