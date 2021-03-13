package BLL;

import DAL.DataControl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class RecordMgr implements Serializable {
    private static RecordMgr recordMgr = new RecordMgr(); // 单例模式
    private HashMap<String, ArrayList<Record>> records; // 就诊记录集合

    private RecordMgr() {
        records = new HashMap<>();
    }

    public static RecordMgr getInstance() {
        return recordMgr;
    }

    public ArrayList<Record> get(String ticketId) {
        if (!records.containsKey(ticketId)) {
            System.err.println("未找到该病人的就诊记录");
            return null;
        }
        return records.get(ticketId);
    }

    public void add(Record record) {
        if (!records.containsKey(record.getTicketId())) {
            records.put(record.getTicketId(), new ArrayList<>());
            add(record);
        } else {
            records.get(record.getTicketId()).add(record);
        }
    }

    public void save() {
        DataControl.writeRecords(recordMgr);
    }

    public void load() {
        try {
            RecordMgr recordMgr1 = DataControl.readRecords();
            if (recordMgr1 != null)
                recordMgr = recordMgr1;
            else records = new HashMap<>();
            save();
        } catch (Exception e) {
            records = new HashMap<>();
            System.err.println("就诊记录数据已损坏并重建");
        }
    }


}