package com.wolf.common.base;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 获取登录用户id
 *
 * @Author honglou
 * @Date 2020/9/10 19:50
 */
public class BaseController {

    @Nullable
    protected Long getCustomerId() {

        if (null == RequestContextHolder.getRequestAttributes()) {
            return null;
        }
        Object claimsObject = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest()
                .getAttribute("claims");
        if (claimsObject != null && ((Claims) claimsObject).containsKey("id")) {
            return NumberUtils.toLong(ObjectUtils.toString(((Claims) claimsObject).get("id")));
        }
        return null;
    }
}
