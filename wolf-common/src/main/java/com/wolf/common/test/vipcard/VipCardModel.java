package com.wolf.common.test.vipcard;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;


@Data
public class VipCardModel  extends BaseRowModel {
    @ExcelProperty(value = "会员卡名称", index = 0)
    private String cardName;

    @ExcelProperty(value = "会员卡号", index = 1)
    private String cardNum;

    @ExcelProperty(value = "会员姓名", index = 2)
    private String name;

    @ExcelProperty(value = "性别", index = 3)
    private String sex;

    @ExcelProperty(value = "出生日期", index = 4)
    private String birthday;

    @ExcelProperty(value = "身份证号", index = 5)
    private String idCard;

    @ExcelProperty(value = "通讯地址", index = 6)
    private String address;

    @ExcelProperty(value = "手机号码", index = 7)
    private String phone;

}
