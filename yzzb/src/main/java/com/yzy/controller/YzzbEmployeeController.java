package com.yzy.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzy.dao.UserDao;
import com.yzy.model.Result;
import com.yzy.model.User;
import com.yzy.model.UserVo;
import com.yzy.service.YzzbUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 员工管理
 *
 * @user szx
 * @date 2021/6/1 20:41
 */
@RestController
@RequestMapping("employee")
@Slf4j
public class YzzbEmployeeController {

    @Autowired
    private YzzbUserService yzzbUserService;
    /**
     * 批量添加用户
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/1 21:38
     */
    @RequestMapping("addEmployeeByBatch")
    public Result addEmployeeByBatch(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName.length() < 6 || !fileName.substring(fileName.length() - 5).equals(".xlsx")) {
            return Result.getErrorResult("文件格式错误");
        }
        List<User> list=new LinkedList<>();
        User user=new User();
        user.setCreateTime(new Date());
        user.setState(1);
        user.setStop(0);
        user.setUpdateTime(new Date());
        //解析EXCEL
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        DataFormatter dataFormatter = new DataFormatter();
        dataFormatter.addFormat("###########", null);
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        for (int i=1;i<=sheetAt.getLastRowNum();i++) {
            Row row =sheetAt.getRow(i);
            String name = row.getCell(0).getStringCellValue();
            String code = row.getCell(1).getStringCellValue();
            String sex = row.getCell(2).getStringCellValue();
            String phone= dataFormatter.formatCellValue(row.getCell(3)).toString();
            if (StringUtils.isEmpty(name) || StringUtils.isEmpty(code) || StringUtils.isEmpty(sex) || StringUtils.isEmpty(phone)||(!"男".equals("sex") &&"女".equals("sex") )) {
                return Result.getErrorResult("第"+i+"个员工信息不全");
            }
            User userClone=user.clone();
            userClone.setName(name);
            userClone.setCode(code);
            userClone.setSex("男".equals(sex)?1:0);
            userClone.setPhone(phone);
            list.add(userClone);
            log.info(userClone.toString());
        }
        yzzbUserService.addUserByBatch(list);
        return Result.getSuccessResult("新增员工成功！",null);
    }

    /**
     * 获取用户列表
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/25 21:32
     */
    @RequestMapping("getUsers")
    public Result getUsers(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum){
        PageHelper.startPage(pageNum,pageSize);
        List<UserVo> userList = yzzbUserService.getAllUsers();
        PageInfo<UserVo> pageInfo = new PageInfo<>(userList);
        return Result.getSuccessResult(pageInfo);
    }
}
