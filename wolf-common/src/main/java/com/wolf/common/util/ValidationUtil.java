package com.wolf.common.util;

import com.wolf.common.exception.ServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author lizg
 * @date 2020/10/16
 */
public final class ValidationUtil {

    private static final ValidationUtil VALIDATION = new ValidationUtil();


    public ValidationUtil checkNull(Object obj, String errorMsg) {
        if (obj instanceof String) {
            if (StringUtils.isBlank((String) obj)) {
                throw new ServiceException("", errorMsg);
            }
            return VALIDATION;
        } else if (obj instanceof Collection) {
            if (CollectionUtils.isEmpty((Collection) obj)) {
                throw new ServiceException("", errorMsg);
            }
            return VALIDATION;
        } else {
            if (null == obj) {
                throw new ServiceException("", errorMsg);
            }
            return VALIDATION;
        }

    }

    public static ValidationUtil newInstance() {
        return VALIDATION;
    }

    private ValidationUtil() {
    }

}
