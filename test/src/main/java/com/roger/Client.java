package com.roger;
import java.io.IOException;
import java.util.Scanner;

public class Client {
    private static HbaseDDL ddl = new HbaseDDL();
    private static HbaseDML dml = new HbaseDML();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("欢迎来到基于hbase的学生管理系统");
            System.out.println("1. 初始化学生管理系统");
            System.out.println("2. 查看所有学生的信息");
            System.out.println("3. 添加学生");
            System.out.println("4. 更新学生信息");
            System.out.println("5. 删除学生");
            System.out.println("6. 删除学生管理系统原有信息");
            System.out.println("7. 退出系统");
            System.out.print("请选择你需要的操作：");

            int choice = sc.nextInt();
            sc.nextLine(); // 清除输入流中的换行符

            switch (choice) {
                case 1:
                    initStudentManager();
                    break;
                case 2:
                    showAllStu();
                    break;
                case 3:
                    addStu();
                    break;
                case 4:
                    updateStuInfo();
                    break;
                case 5:
                    deleteStu();
                    break;
                case 6:
                    deleteStudentManager();
                    break;
                case 7:
                    System.out.println("感谢使用，再见！");
                    return;
                default:
                    System.out.println("无效的输入，请重新输入！");
                    break;
            }
        }
    }



    //        Row Key   | info:name   | info:age | contact:email
//------------------------------------------------------------------------------------
//        1         | John Doe    | 30       | john.doe@email.com
//        2         | Jane Doe    | 28       | jane.doe@email.com
    //系统初始化
    public static void initStudentManager() throws IOException {
        ddl.createTable("default","StudentManager","info","contact");
        //在StudentManager表中添加两个学生信息
        Student stu1 = new Student("1","John Doe", "18","john.doe@email.com");
        Student stu2 = new Student("2","Jane Doe", "28","Jane.doe@email.com");
        dml.putCell("default","StudentManager",stu1.getId(),"info","name",stu1.getName());
        dml.putCell("default","StudentManager",stu1.getId(),"info","age",stu1.getAge());
        dml.putCell("default","StudentManager",stu1.getId(),"contact","email",stu1.getEmail());

        dml.putCell("default","StudentManager",stu2.getId(),"info","name",stu2.getName());
        dml.putCell("default","StudentManager",stu2.getId(),"info","age",stu2.getAge());
        dml.putCell("default","StudentManager",stu2.getId(),"contact","email",stu2.getEmail());

    }

    //删除系统信息
    public static void deleteStudentManager() throws IOException {
        ddl.deleteTable("default","StudentManager");
        System.out.println("系统信息删除成功！");
    }

    //查看所有学生的信息
    public static void showAllStu() throws IOException {
        System.out.println("所有学生的信息如下：");
        dml.printTable("default","StudentManager");
    }

    //添加学生
    public static void addStu() throws IOException {
        //获取当前最大的rowkey
        int index = Integer.parseInt(dml.getMaxRowKey("default","StudentManager"))+1;
        Student addStudent = new Student();
        String addid = String.valueOf(index);
        String addName = " ";
        String addAge = " ";
        String addEmail = " ";
        addStudent.id = addid;
        System.out.println("请输入要添加学生的姓名：");
        addName = sc.nextLine();
        addStudent.name = addName;
        System.out.println("请输入要添加学生的年龄：");
        addAge = sc.nextLine();
        addStudent.age = addAge;
        System.out.println("请输入要添加学生的邮箱：");
        addEmail = sc.nextLine();
        addStudent.email = addEmail;

        dml.putCell("default","StudentManager",addStudent.getId(),"info","name",addStudent.getName());
        dml.putCell("default","StudentManager",addStudent.getId(),"info","age",addStudent.getAge());
        dml.putCell("default","StudentManager",addStudent.getId(),"contact","email",addStudent.getEmail());
        System.out.println("添加成功！");
    }

    //更改学生信息
    public static void updateStuInfo() throws IOException {
        Student changeStudent = new Student();
        System.out.println("请输入要更改学生的id：");
        changeStudent.setId(sc.nextLine());
        System.out.println("请输入更新后学生的姓名：");
        changeStudent.setName(sc.nextLine());
        System.out.println("请输入更新后学生的年龄：");
        changeStudent.setAge(sc.nextLine());
        System.out.println("请输入更新后学生的邮箱：");
        changeStudent.setEmail(sc.nextLine());
        dml.putCell("default","StudentManager",changeStudent.getId(),"info","name",changeStudent.getName());
        dml.putCell("default","StudentManager",changeStudent.getId(),"info","age",changeStudent.getAge());
        dml.putCell("default","StudentManager",changeStudent.getId(),"contact","email",changeStudent.getEmail());
        System.out.println("更新成功！");
    }

    //删除学生
    public static void deleteStu() throws IOException {
        Student delStu = new Student();
        System.out.println("请输入要删除学生的id：");
        delStu.id = sc.nextLine();
        dml.deleteRow("default","StudentManager",delStu.getId());
        System.out.println("删除成功！");
    }
}

