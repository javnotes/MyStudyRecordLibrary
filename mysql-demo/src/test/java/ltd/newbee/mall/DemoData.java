package ltd.newbee.mall;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author luf
 * @date 2022/01/03 22:04
 **/
//@Getter
//@Setter
//@EqualsAndHashCode
//public class DemoData {
//    @ExcelProperty(value = "用户id", index = 0)
//    private Integer id;
//    @ExcelProperty(value = "用户名", index = 1)
//    private String username;
//    @ExcelProperty(value = "密码", index = 2)
//    private String password;
//    @ExcelProperty(value = "昵称", index = 3)
//    private String nickname;
//    @ExcelProperty(value = "状态", index = 4)
//    private Integer locked;
//}

@Getter
@Setter
@EqualsAndHashCode
public class DemoData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}