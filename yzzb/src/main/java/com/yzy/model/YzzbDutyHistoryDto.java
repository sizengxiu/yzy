package com.yzy.model;

import lombok.Data;

/**
 * @user szx
 * @date 2021/5/17 22:20
 */
@Data
public class YzzbDutyHistoryDto extends YzzbDutyHistory {

    private String employeeAName;

    private String employeeBName;

    private String employeeCName;

}
