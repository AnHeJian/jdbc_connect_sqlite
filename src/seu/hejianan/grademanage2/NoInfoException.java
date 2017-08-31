package seu.hejianan.grademanage2;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by LIVE on 2017/5/16.
 */
public class NoInfoException extends Exception  {
    public NoInfoException(String msg)
    {
        super(msg);
    }
}

class InfoNotEqual {
    public void InfoCompare(ResultSet rs, String s) throws NoInfoException {
        int flag = 0;
        String numVal =null;
        String nameVal = null;
        int gradeVal = 0;

        try {
            while (rs.next()) {
                numVal = rs.getString("学号");
                nameVal = rs.getString("姓名");
                gradeVal = rs.getInt("分数");
                if (s.equals(nameVal) || s.equals(numVal)) {
                    flag = 1;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(flag == 0)
            throw new NoInfoException("未找到学生 "+ s +" 的信息！");                        //抛出异常
        else
            System.out.println("学号 = "+numVal +"，姓名 = "+nameVal+"，分数 = "+gradeVal);

    }
}
