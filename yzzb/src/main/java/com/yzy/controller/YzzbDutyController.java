package com.yzy.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzy.model.DutyResultVo;
import com.yzy.model.KeyValueEntity;
import com.yzy.model.Result;
import com.yzy.model.UserVo;
import com.yzy.service.DutyPlanService;
import com.yzy.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * @user szx
 * @date 2021/5/18 20:54
 */
@RestController
@RequestMapping("duty")
@Slf4j
public class YzzbDutyController {

    @Autowired
    private DutyPlanService dutyPlanService;

    /**
     * 当前月份数据是否允许发布
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/26 20:45
     */
    @RequestMapping("checkAllowPublishCurrentMonthData")
    public Result checkAllowPublishCurrentMonthData(@RequestParam("year") int year, @RequestParam("month") int month) {
        boolean success = dutyPlanService.checkAllowPublishCurrentMonthData(year, month);
        Result result = new Result();
        result.setSuccess(success);
        return result;
    }

    /**
     * 获取年份下拉列表
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/27 21:25
     */
    @RequestMapping("getZbYearList")
    public Result getZbYearList() {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        List<KeyValueEntity> list = new LinkedList<>();
        for (int i = 2020; i <= year + 1; i++) {
            list.add(new KeyValueEntity<Integer, Integer>(i, i));
        }
        return Result.getSuccessResult(list);
    }

    /**
     * 获取月份下拉列表
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/27 22:02
     */
    @RequestMapping("getZbMonthList")
    public Result getZbMonthList() {
        List<KeyValueEntity> list = new LinkedList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(new KeyValueEntity<Integer, Integer>(i, i));
        }
        return Result.getSuccessResult(list);
    }


    /**
     * 按月排班
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/29 21:53
     */
    @RequestMapping("planByDate")
    public Result planByDate(@RequestParam("year") int year, @RequestParam("month") int month) {
        List<DutyResultVo> list = dutyPlanService.planByDate(year, month);
        return Result.getSuccessResult(list);
    }


    /**
     * 根据日期获取排班数据
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/18 19:41
     */
    @RequestMapping("getPbListByDate")
    public Result getPbListByDate(@RequestParam("year") int year, @RequestParam("month") int month) {
        PageHelper.startPage(0, 200);
        List<DutyResultVo> list = dutyPlanService.getPbListByDate(year, month);
        PageInfo<DutyResultVo> pageInfo = new PageInfo<>(list);
        return Result.getSuccessResult(pageInfo);
    }

    /**
     * 发布当月排班数据
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/27 20:19
     */
    @RequestMapping("publishPbData")
    public Result publishPbData(@RequestParam("year") int year, @RequestParam("month") int month) {
        dutyPlanService.publishPbData(year, month);
        return new Result();
    }

    /**
     * 当月数据是否已发布
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/9/28 21:56
     */

    @RequestMapping("isDataPublished")
    public Result isDataPublished(@RequestParam("year") int year, @RequestParam("month") int month) {
        boolean dataPublished = dutyPlanService.isDataPublished(year, month);
        Result result = new Result();
        result.setSuccess(dataPublished);
        return result;
    }

    /**
     * 导出excel
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/10/9 20:46
     */
    @RequestMapping(value = "/export")
    public void export(@RequestParam("year") int year, @RequestParam("month") int month, HttpServletResponse response) {
        //获取数据
        List<DutyResultVo> list = dutyPlanService.getPbListByDate(year, month);
        String sheetName = year + "年" + month + "月排班数据";
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setColumnWidth(0, 4500);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        //excel标题
        String[] title = {"日期", "星期", "姓名", "手机号", "固话"};
        String[] week = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};


        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle centerStyle = wb.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(centerStyle);
        }
        HSSFCellStyle hssfCellStyleDate = wb.createCellStyle();
        HSSFDataFormat fm = wb.createDataFormat();
        hssfCellStyleDate.setDataFormat(fm.getFormat("yyyy年MM月dd日"));//
        hssfCellStyleDate.setAlignment(HorizontalAlignment.CENTER);//水平居中
        hssfCellStyleDate.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //创建内容
        for (int i = 0, size = list.size(); i < size; i++) {
            row = sheet.createRow(i + 1);
            int col = 0;
            DutyResultVo resultVo = list.get(i);

            cell = row.createCell(col++);
            cell.setCellStyle(hssfCellStyleDate);
            cell.setCellValue(resultVo.getDate());


            cell = row.createCell(col++);
            cell.setCellStyle(centerStyle);
            cell.setCellValue(week[resultVo.getWeekIndex() - 1]);

            if (i % 3 == 1) {
                CellRangeAddress region = new CellRangeAddress(i, i + 2, 0, 0);
                sheet.addMergedRegion(region);
                region = new CellRangeAddress(i, i + 2, 1, 1);
                sheet.addMergedRegion(region);
            }


            row.createCell(col++).setCellValue(resultVo.getName());
            row.createCell(col++).setCellValue(resultVo.getPhone());
            row.createCell(col++).setCellValue(resultVo.getFixedPhone());
        }

        try {
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(sheetName.getBytes(), "ISO-8859-1") + ".xls");
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("文件导出异常！{}", e.getMessage());
        }
    }

    /**
     * 第一次添加人员时，需手动调用该接口，将人员的上次值班时间改为两个月之前
     *   该接口只调用一次
     *
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/10/10 15:43
     */
    @RequestMapping("initFirstZbDate")
    public Result initFirstZbDate() {
        return new Result();
    }

    @RequestMapping("test")
    public String test(String t) {
        System.out.println("1");
        return t;
    }
}
