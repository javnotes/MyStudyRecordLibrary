package ltd.newbee.mall;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import util.TestFileUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@SpringBootTest
class NewbeeMallApplicationTests {

    // 注入数据源对象
    @Autowired
    private DataSource defaultDataSource;

    @Test
    public void datasourceClassTest() throws SQLException {
        // 获取数据源类型
        System.out.println("默认数据源为：" + defaultDataSource.getClass());
    }

    @Test
    public void datasourceTest() throws SQLException {
        // 获取数据库连接对象
        Connection connection = defaultDataSource.getConnection();
        System.out.print("获取连接：");
        // 判断连接对象是否为空
        System.out.println(connection != null);
        connection.close();
    }

    @Test
    void contextLoads() {
    }


    /**
     * 最简单的写
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        // 注意 simpleWrite在数据量不大的情况下可以使用（5000以内，具体也要看实际情况），数据量大参照 重复多次写入
        //
        //// 写法1 JDK8+
        //// since: 3.0.0-beta1
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        System.out.println(fileName);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class)
                .sheet("模板")
                .doWrite(() -> {
                    // 分页查询数据
                    return data();
                });

        // 写法2
        //fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        //// 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        //// 如果这里想使用03 则 传入excelType参数即可
        //EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());

        //// 写法3
        //fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        //// 这里 需要指定写用哪个class去写
        //ExcelWriter excelWriter = null;
        //try {
        //    excelWriter = EasyExcel.write(fileName, DemoData.class).build();
        //    WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        //    excelWriter.write(data(), writeSheet);
        //} finally {
        //    // 千万别忘记finish 会帮忙关闭流
        //    if (excelWriter != null) {
        //        excelWriter.finish();
        //    }
        //}
    }

    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 指定写入的列
     * <p>1. 创建excel对应的实体对象 参照{@link IndexData}
     * <p>3. 直接写即可
     */
    @Test
    public void indexWrite() {
        String fileName = TestFileUtil.getPath() + "indexWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, IndexData.class).sheet("模板").doWrite(data());
    }


}