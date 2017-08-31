package seu.hejianan.grademanage2;
import java.util.Scanner;
/**
 * Created by LIVE on 2017/5/15.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("输入学生个数：");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println("正在连接数据库……");
        GradeManage gm = new GradeManage(n);

        gm.inputGrade();
        System.out.println("\n-------------------------------\n输入要查询的学生学号或姓名(\"q\" 退出）：");
        String s = sc.next();
        while (!s.equals("q")){
            gm.getFromTable(s);
            System.out.println("输入要查询的学生学号或姓名(\"q\" 退出）：");
            s = sc.next();
        }

        System.out.println("\n-------------------------------\n成绩统计信息：");
        gm.statisGrade();
        gm.closeconnect();
    }
}
