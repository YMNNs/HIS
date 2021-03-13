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

import BLL.*;
import Common.Generator;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class HIS_cli {
    static ItemMgr itemMgr = ItemMgr.getInstance();
    static Pharmacy pharmacy = Pharmacy.getInstance();
    static DepartmentMgr departmentMgr = DepartmentMgr.getInstance();
    static DoctorMgr doctorMgr = DoctorMgr.getInstance();
    static DiseaseType diseaseType = DiseaseType.getInstance();
    static Registry registry = Registry.getInstance();
    static RecordMgr recordMgr = RecordMgr.getInstance();
    static Cashier cashier = Cashier.getInstance();

    public static void main(String[] args) {
        //init();
        loadData();
        while (true) {
            System.out.println();
            System.out.println("HIS-cli 未处理异常");
            System.out.println("1.挂号/收费工作站");
            System.out.println("2.医生工作站");
            System.out.println("3.科室管理");
            System.out.println("4.医生管理");
            System.out.println("5.物品管理");
            System.out.println("6.药房工作站");
            System.out.println("7.初始化数据");
            System.out.println("8.病种管理");
            System.out.println("9.护士工作站");
            System.out.println("10.保存数据");
            System.out.println("11.读取数据");
            System.out.print(">");
            Scanner scanner = new Scanner(System.in);
            String opt = scanner.nextLine();
            switch (opt) {
                case "1": {
                    挂号收费工作站();
                    break;
                }
                case "2": {
                    医生工作站();
                    break;
                }
                case "3": {
                    科室管理();
                    break;
                }
                case "4": {
                    医生管理();
                    break;
                }
                case "6": {
                    药房工作站();
                    break;
                }
                case "7": {
                    init();
                    break;
                }
                case "8": {
                    病种管理();
                    break;
                }
                case "9": {
                    护士工作站();
                    break;
                }
                case "10": {
                    saveData();
                    break;
                }
                case "11": {
                    loadData();
                    break;
                }
                default: {
                    return;
                }
            }
        }
    }

    public static void 挂号收费工作站() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("挂号/收费工作站");
        System.out.println("1.挂号并收挂号费");
        System.out.println("2.收药费");
        System.out.println("3.退费");
        System.out.println("4.复诊挂号");
        System.out.print(">");
        String opt2 = scanner.nextLine();
        switch (opt2) {
            case "1": {
                System.out.println("输入姓名 String");
                String name = "刘益先";
                System.out.println("输入性别 boolean");
                boolean sex = true;
                System.out.println("输入年龄 int");
                int age = 40;
                System.out.println("输入支付方式 int");
                int payMethod = 1;
                System.out.println("输入身份证号 String");
                String idNumber = "123";
                System.out.println("输入地址 String");
                String address = "rwer";
                System.out.println("选择科室,输入科室简称");
                //int departmentId = departmentMgr.get(scanner.nextLine()).getId();
                int departmentId = 1;
                departmentMgr.printAll();
                System.out.println("已选择" + departmentMgr.getById(departmentId).getName());
                System.out.println("输入挂号优先级2");
                //DoctorMgr doctorMgr = DoctorMgr.getInstance();
                //int doctorId = doctorMgr.getById(1).getId();
                //System.out.println("已选择医生" + doctorMgr.getById(doctorId).getName());
                int priority = 2;
                Ticket ticket = new Ticket(Generator.randomCapsString(6), name, sex, age, payMethod, idNumber, address, departmentId, priority);
                Order order = new Order();
                order.setType(0);
                order.add(ticket.toItem());
                System.out.println("是否需要病历本？默认是");
                order.add(itemMgr.get("blb"));
                order.settleForProceed();
                ticket.addOrders(order);
                if (cashier.proceed(order, ticket.getPayMethod())) {
                    //ticket.removeOrder(order);
                    ticket.setStatus(true);
                }
                registry.register(ticket);
                break;
                // TODO 完善挂号分诊流程
            }
            case "2": {
                Cashier cashier = Cashier.getInstance();
                Pharmacy pharmacy = Pharmacy.getInstance();
                System.out.println("输入病历号");
                String ticketId = scanner.nextLine();
                Ticket ticket = registry.getById(ticketId);
                System.out.println(ticket.getName());
                for (Order ticketOrder : ticket.getOrders()) {
                    if (ticketOrder.getStatus() == 0 || ticketOrder.getType() == 1) {
                        System.out.println("\nindex = " + ticket.getOrders().indexOf(ticketOrder));
                        ticketOrder.print();
                    }
                }
                System.out.println("已显示全部订单，输入订单index");
                int index = scanner.nextInt();
                Order order = ticket.getOrders(index);
                order.settleForProceed();
                cashier.proceed(order, ticket.getPayMethod());
                ticket.getRx().setStatus(1);
                break;
            }
            case "3": {
                Cashier cashier = Cashier.getInstance();
                System.out.println("输入病历号");
                String ticketId = scanner.nextLine();
                Ticket ticket = registry.getById(ticketId);
                for (Order ticketOrder : ticket.getOrders()) {
                    if (ticketOrder.getStatus() == 1) {
                        System.out.println("\nindex = " + ticket.getOrders().indexOf(ticketOrder));
                        ticketOrder.print();
                    }
                }
                System.out.println("输入订单index");
                int index = scanner.nextInt();
                Order order = ticket.getOrders(index);
                order.settleForRefund();
                cashier.refund(order, ticket.getPayMethod());
                if (order.getType() == 0) { //退号
                    registry.cancelTicket(ticket);
                } else if (order.getType() == 1) { //退药费
                    ticket.getRx().setStatus(3);
                }
                break;
            }
            case "4": {
                //复诊挂号
                System.out.println("输入病历号");
                String ticketId = scanner.nextLine();
                Ticket ticket = registry.getById(ticketId);

                break;
            }
        }
    }

    public static void 科室管理() {
        departmentMgr.printAll();
    }

    public static void 医生工作站() {
        System.out.println("选择医生角色");
        doctorMgr.printAll();
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        Doctor doctor = doctorMgr.getById(option);
        System.out.println("当前候诊名单");
        for (Ticket ticket : doctor.getQueue()) {
            System.out.println(ticket.getName());
        }
        System.out.println("呼叫下一患者");
        if (!doctor.callNext()) {
            return;
        }
        Rx rx = doctor.makeRx();
        Order order = new Order();
        order.add(rx.toItem());
        order.setType(1);
        order.settleForProceed();
        registry.getById(doctor.getCurrentTicketID()).addOrders(order);
        //System.out.println(registry.getById(doctor.getCurrentTicketID()).getName());
    }

    public static void 护士工作站() {
        departmentMgr.printAll();
        System.out.println("输入科室ID");
        Scanner scanner = new Scanner(System.in);
        Department department = departmentMgr.getById(scanner.nextInt());
        if (department.getQueue().size() == 0) {
            return;
        }
        System.out.println("当前候诊患者名单");
        for (Ticket ticket : department.getQueue()) {
            System.out.println(ticket.toString());
        }
        System.out.println("调取下一位患者的就诊记录");
        Ticket ticket = department.getQueue().peek();
        ArrayList<Record> record = recordMgr.get(ticket.getId());
        if (record != null) {
            for (Record record1 : record) {
                System.out.println(record1);
            }
        }
        System.out.println("选择病种");
        System.out.println(diseaseType.toString());
        diseaseType.get(scanner.nextInt()).add(ticket.getId());
        //显示可用医生
        System.out.println("当前可用医生");
        for (Map.Entry<Integer, Integer> integerIntegerEntry : department.getAvailableDoctors().entrySet()) {
            Doctor doctor = doctorMgr.getById(integerIntegerEntry.getKey());
            System.out.println(doctor.getId() + "\t" + doctor.getName() + "\t" + integerIntegerEntry.getValue());
        }
        Doctor doctor = doctorMgr.getById(scanner.nextInt());
        doctor.add(ticket);
    }

    public static void 医生管理() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.查看当前医生");
        System.out.println("2.安排科室");
        System.out.print(">");
        switch (scanner.nextLine()) {
            case "1": {
                doctorMgr.printAll();
                System.out.println("科室安排");
                departmentMgr.printAll();
                break;
            }
            case "2": {
                System.out.println("选择医生，输入编号");
                int doctorId = doctorMgr.getById(scanner.nextInt()).getId();
                System.out.println("已选择医生" + doctorMgr.getById(doctorId).getName());
                System.out.println("选择科室,输入科室简称");

                departmentMgr.printAll();
                scanner = new Scanner(System.in);
                Department department = departmentMgr.get(scanner.nextLine());
                doctorMgr.assignDepartment(doctorId, department);
                System.out.println("已安排" + doctorMgr.getById(doctorId).getName() + "到" + department.getName());
                break;
            }
        }
    }

    public static void 药房工作站() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.取药");
        //System.out.println("2.退药");
        System.out.print(">");
        switch (scanner.nextLine()) {
            case "1": {
                System.out.println("输入病历号");
                String ticketId = scanner.nextLine();
                Ticket ticket = registry.getById(ticketId);
                Rx rx = ticket.getRx();
                if (rx.getStatus() == 1) {
                    rx.printAll();
                    pharmacy.offerMedicine(rx);
                }
                break;
            }
        }
    }

    public static void 病种管理() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.创建树");
        System.out.println("2.查询树");
        System.out.println("3.查询病种下的病人");
        System.out.println("4.显示全部内容");
        System.out.print(">");
        switch (scanner.nextLine()) {
            case "1": {
                System.out.println("输入父节点id或名称");
                String query = scanner.nextLine();
                System.out.println("输入子节点名称");
                String name = scanner.nextLine();
                System.out.println("输入子节点简称");
                String alias = scanner.nextLine();
                int queryInt;
                try {
                    if (query.matches("^[1-9]\\d*|0$")) {
                        queryInt = Integer.parseInt(query);
                        diseaseType.add(diseaseType.get(queryInt), name, alias);
                    } else {
                        diseaseType.add(diseaseType.get(query), name, alias);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("创建失败");
                }
                break;
            }
            case "2": {
                System.out.println("输入id或名称");
                String query = scanner.nextLine();
                int queryInt;
                try {
                    if (query.matches("^[1-9]\\d*|0$")) {
                        queryInt = Integer.parseInt(query);
                        System.out.println(diseaseType.get(queryInt).describe());
                    } else {
                        System.out.println(diseaseType.get(query).describe());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("未找到该节点");
                }
                break;
            }
            case "3": {
                break;
            }
            case "4": {
                System.out.println(diseaseType);
                break;
            }
        }

    }

    public static void init() {
        itemMgr.init();
        pharmacy.init();
        departmentMgr.init();
        doctorMgr.init();
        diseaseType.init();
        registry.init();
    }

    public static void loadData() {
        itemMgr.load();
        pharmacy.load();
        departmentMgr.load();
        doctorMgr.load();
        diseaseType.load();
        registry.load();
        recordMgr.load();
        itemMgr = ItemMgr.getInstance();
        pharmacy = Pharmacy.getInstance();
        departmentMgr = DepartmentMgr.getInstance();
        doctorMgr = DoctorMgr.getInstance();
        diseaseType = DiseaseType.getInstance();
        registry = Registry.getInstance();
        recordMgr = RecordMgr.getInstance();
        cashier = Cashier.getInstance();
    }

    public static void saveData() {
        itemMgr.save();
        pharmacy.save();
        departmentMgr.save();
        doctorMgr.save();
        diseaseType.save();
        registry.save();
        recordMgr.save();
        System.out.println("已保存所有数据");
    }


}
