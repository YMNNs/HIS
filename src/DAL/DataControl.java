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

package DAL;

import BLL.*;

import java.io.File;
import java.io.IOException;

public class DataControl {
    private static File Ffolder = new File("data");
    private static File Fregistry = new File("data/registry.ser");
    private static File Fmedicine = new File("data/medicine.ser");
    private static File Fdoctors = new File("data/doctors.ser");
    private static File Fdepartments = new File("data/departments.ser");
    private static File Fitems = new File("data/items.ser");
    private static File FdiseaseType = new File("data/disease-type.ser");
    private static File FRecords = new File("data/records.ser");


    public static void init() {
        if (!Ffolder.exists())
            Ffolder.mkdirs();
    }

    public static void writeRecords(RecordMgr recordMgr) {
        createIfNotExist(FRecords);
        try {
            SerializeUtil.writeObject(recordMgr, FRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeDiseaseType(DiseaseType diseaseType) {
        createIfNotExist(FdiseaseType);
        try {
            SerializeUtil.writeObject(diseaseType, FdiseaseType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void writeRegistry(Registry registry) {
        createIfNotExist(Fregistry);
        try {
            SerializeUtil.writeObject(registry, Fregistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*JsonUtil.hashTableToJson(tickets, Ftickets.getPath());*/
    }

    public static void writeMedicine(Pharmacy pharmacy) {
        createIfNotExist(Fmedicine);
        //JsonUtil.hashTableToJson(store, Fmedicine.getPath());
        try {
            SerializeUtil.writeObject(pharmacy, Fmedicine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeDoctor(DoctorMgr doctors) {
        createIfNotExist(Fdoctors);
        //JsonUtil.hashSetToJson(doctors, Fdoctors.getPath());
        try {
            SerializeUtil.writeObject(doctors, Fdoctors);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeDepartment(DepartmentMgr departmentMgr) {
        //createIfNotExist(Fdepartments);
        //JsonUtil.hashSetToJson(departments, Fdepartments.getPath());
        createIfNotExist(Fdepartments);
        try {
            SerializeUtil.writeObject(departmentMgr, Fdepartments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeItem(ItemMgr itemMgr) {
        createIfNotExist(Fitems);
        //JsonUtil.hashSetToJson(items, Fitems.getPath());
        try {
            SerializeUtil.writeObject(itemMgr, Fitems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Registry readRegistry() throws Exception {
        if (createIfNotExist(Fregistry)) {
            return null;
        }
        /*Hashtable<Ticket, Date> tickets = new Hashtable<>();
        String[][] mid = JsonUtil.jsonToString2dArray(Ftickets.getPath());
        assert mid != null;
        for (String[] strings : mid) {
            if (strings[0] != null)
                tickets.put(JSON.parseObject(strings[0], Ticket.class), JSON.parseObject(strings[1], Date.class));
        }
        return tickets;*/

        return (Registry) SerializeUtil.readObject(Fregistry);

    }

    public static Pharmacy readMedicine() throws Exception {
        if (createIfNotExist(Fmedicine)) {
            return null;
        }
        /*Hashtable<Medicine, Integer> medicine = new Hashtable<>();
        String[][] mid = JsonUtil.jsonToString2dArray(Fmedicine.getPath());
        assert mid != null;
        for (String[] strings : mid) {
            if (strings[0] != null) {
                medicine.put(JSON.parseObject(strings[0], Medicine.class), JSON.parseObject(strings[1], Integer.class));
            }
        }
        return medicine;*/
        return (Pharmacy) SerializeUtil.readObject(Fmedicine);
    }

    public static DoctorMgr readDoctor() throws Exception {
        if (createIfNotExist(Fdoctors)) {
            return null;
        }
        /*HashSet<Doctor> doctors = new HashSet<>();
        String[] mid = JsonUtil.jsonToStringArray(Fdoctors.getPath());
        assert mid != null;
        for (String s : mid) {
            doctors.add(JSON.parseObject(s, Doctor.class));
        }
        return doctors;*/
        return (DoctorMgr) SerializeUtil.readObject(Fdoctors);
    }

    public static DepartmentMgr readDepartment() throws Exception {
        if (createIfNotExist(Fdepartments)) {
            //System.out.println("科室为空");
            return null;
        }
        /*HashSet<Department> departments = new HashSet<>();
        String[] mid = JsonUtil.jsonToStringArray(Fdepartments.getPath());
        assert mid != null;
        for (String s : mid) {
            departments.add(JSON.parseObject(s, Department.class));
        }
        return departments;*/

        return (DepartmentMgr) SerializeUtil.readObject(Fdepartments);

    }

    public static ItemMgr readItem() throws Exception {
        if (createIfNotExist(Fitems)) {
            return null;
        }
        return (ItemMgr) SerializeUtil.readObject(Fitems);
    }
    /*public static HashSet<Item> readItem() {
        if (createIfNotExist(Fitems)) {
            return null;
        }
        HashSet<Item> items = new HashSet<>();
        String[] mid = JsonUtil.jsonToStringArray(Fitems.getPath());
        assert mid != null;
        for (String s : mid) {
            items.add(JSON.parseObject(s, Item.class));
        }
        return items;
    }*/

    public static DiseaseType readDiseaseType() throws Exception {
        if (createIfNotExist(FdiseaseType)) {
            return null;
        }

        return (DiseaseType) SerializeUtil.readObject(FdiseaseType);

    }

    public static RecordMgr readRecords() throws Exception {
        if (createIfNotExist(FRecords)) {
            return null;
        }

        return (RecordMgr) SerializeUtil.readObject(FRecords);

    }

    private static boolean createIfNotExist(File file) {
        if (!file.exists())
            try {
                file.createNewFile();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }
        return false;
    }

}
