package com.wolf.common.base;

import com.wolf.common.util.MessageSourceUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;

/**
 * 响应基类
 * Created by aqlu on 15/11/30.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse implements Serializable {

    public static BaseResponse SUCCESSFUL() {
        return new BaseResponse(ResultCode.SUCCESSFUL);
    }

    public static BaseResponse FAILED() {
        return new BaseResponse(ResultCode.FAILED);
    }

    public BaseResponse(String code){
        this.code = code;
        this.message = MessageSourceUtil.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    /**
     * 结果码
     */
    private String code;

    /**
     * 消息内容
     */
    private String message;
}
