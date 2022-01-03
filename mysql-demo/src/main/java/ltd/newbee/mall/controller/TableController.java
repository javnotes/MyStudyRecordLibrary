package ltd.newbee.mall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author luf
 * @date 2022/01/03 21:19
 **/

@RestController
public class TableController {


    ///**
    // * 导出excel
    // */
    //@GetMapping("/exportChargeExcel")
    //public void exportPhpExcel(HttpServletRequest request, HttpServletResponse response) {
    //
    //    HashMap<String, Object> queryMap = new HashMap<>();
    //    queryMap.put("dealStartDateStr", orderInfoEx.getDealStartDateStr());
    //    queryMap.put("dealEndDateStr", orderInfoEx.getDealEndDateStr());
    //    queryMap.put("phoneNumber", orderInfoEx.getPhoneNumber());
    //    queryMap.put("cardNum", orderInfoEx.getCardNum());
    //    queryMap.put("cardType", orderInfoEx.getCardType());
    //    queryMap.put("orgName", orderInfoEx.getOrgName());
    //    queryMap.put("orderStatusShow", orderInfoEx.getOrderStatusShow());
    //    queryMap.put("city", orderInfoEx.getCity());
    //    queryMap.put("csCode", orderInfoEx.getCsCode());
    //
    //    try {
    //        // 设置列宽
    //        Map columnWidth = new HashMap();
    //        columnWidth.put(0, 8000);
    //
    //        String fileName = "正常订单";
    //        if (StringUtils.isNotEmpty(orderInfoEx.getExportType())) {
    //            if ("1".equals(orderInfoEx.getExportType())) {
    //                // 查询正常订单导出数据
    //                List<OrderExportDTO> orderExportDTOList = orderService.getOrderExportByParam(queryMap);
    //
    //                Sheet sheet1 = new Sheet(1, 0, OrderExportDTO.class);
    //                sheet1.setColumnWidthMap(columnWidth);
    //                sheet1.setSheetName(fileName);
    //                // 导出
    //                EasyExcelUtils.writeExcelOneSheet(response, orderExportDTOList, sheet1, fileName);
    //            } else {
    //                fileName = "异常订单";
    //                // 查询异常订单导出数据
    //                List<ExceptionOrderExportDTO> exceptionOrderExportDTOList = orderService.getExceptionOrderExportByParam(queryMap);
    //
    //                Sheet sheet1 = new Sheet(1, 0, ExceptionOrderExportDTO.class);
    //                sheet1.setColumnWidthMap(columnWidth);
    //                sheet1.setSheetName(fileName);
    //
    //                // 导出
    //                EasyExcelUtils.writeExcelOneSheet(response, exceptionOrderExportDTOList, sheet1, fileName);
    //            }
    //        } else {
    //            throw new RuntimeException("导出类型为空");
    //        }
    //    } catch (Exception e) {
    //        logger.error("订单导出异常", e);
    //        throw new RuntimeException(e);
    //    }
    //}

}