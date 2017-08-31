package seu.hejianan.grademanage2;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.Scanner;

import org.sqlite.JDBC;
/**
 * Created by 贺建安 on 2017/5/15.
 */
public class GradeManage {

    int studentsnum;

    Connection con = null;
    Statement stmt = null;


    GradeManage(int n){
        studentsnum = n;

        try {
            Class.forName("org.sqlite.JDBC").newInstance();                    //注册JDBC驱动
            con = DriverManager.getConnection("jdbc:sqlite:GradeList.db");//连接
            if (!con.isClosed())
                System.out.println("数据库连接成功！");
            stmt = con.createStatement();                                      //关联
            String sql1 = creatTableTitle();
            stmt.executeUpdate ("DROP TABLE IF EXISTS list1");
            stmt.executeUpdate(sql1);
        }
          catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
              }
        }

    public void inputGrade(){
        Scanner sc = new Scanner(System.in);
        System.out.println("输入"+studentsnum+"个学生的学号，姓名，成绩：");
        String num;
        String name;
        int grade;
        for (int i = 0; i < studentsnum; i++) {
            System.out.println("第"+(i+1)+"个：");
            num = sc.next();
            name = sc.next();
            grade = sc.nextInt();
            dataToTable((i+1),num,name,(grade));
        }
    }
    public void dataToTable(int i, String num,String name,int grade) {
        try {

            String info= "INSERT INTO list1 (序号,学号,姓名,分数)"
                    + " VALUES"  + "("+i+",'"+num+"','"+name+"',"+grade+")";
            stmt.executeUpdate(info);

        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public static String creatTableTitle()
    {
        String sql1 = "CREATE TABLE  list1"+
                "(序号 INT PRIMARY KEY     NOT NULL," +                           //PRIMARY KEY：主键
                " 学号           TEXT    NOT NULL,"+
                " 姓名           TEXT    NOT NULL,"+
                " 分数           INT    NOT NULL) " ;

        return sql1;
    }

    public void closeconnect()
    {
        try {
            if (con != null)
                con.close();                                                  //关闭连接
        } catch(SQLException e) {}

    }
    public void getFromTable(String s)
    {
        InfoNotEqual ine = new InfoNotEqual();
        try {
            ResultSet rs = stmt.executeQuery("SELECT 学号, 姓名,分数 " + "FROM list1");
            ine.InfoCompare(rs,s);
        }catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

    }
    public void statisGrade()
    {
        int t;
        int sum=0;
        int range[] = new int[5];

        try {
            ResultSet rs = stmt.executeQuery("SELECT 分数 " + "FROM list1");
            while (rs.next()) {
                t = rs.getInt("分数");
                sum+=t;
                if (t < 60)
                    range[0]++;
                else if(t < 70 && t>=60)
                    range[1]++;
                else if(t < 80 && t>=70)
                    range[2]++;
                else if(t < 90 && t>=80)
                    range[3]++;
                else
                    range[4]++;
            }
        }catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        System.out.println("低于60分的有 "+ range[0]+ " 个人");
        System.out.println("60分到70分的有 "+ range[1]+ " 个人");
        System.out.println("70分到80分的有 "+ range[2]+ " 个人");
        System.out.println("80分到90分的有 "+ range[3]+ " 个人");
        System.out.println("90分到100分的有 "+ range[4]+ " 个人");

        DecimalFormat df  = new DecimalFormat(".000");
        double aver = Double.parseDouble(df.format(((double) sum/studentsnum)));
        System.out.println("平均成绩是 "+aver+" 分。");

    }
}
