package ltd.newbee.mall.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author luf
 * @date 2022/01/03 21:03
 **/
@Data
public class TabExcelProperty {
    /**
     * 设置excel表头名称
     */
    @ExcelProperty(value = "用户id", index = 0)
    private Integer id;
    @ExcelProperty(value = "用户名", index = 1)
    private String username;
    @ExcelProperty(value = "密码", index = 2)
    private String password;
    @ExcelProperty(value = "昵称", index = 3)
    private String nickname;
    @ExcelProperty(value = "状态", index = 4)
    private Integer locked;

}