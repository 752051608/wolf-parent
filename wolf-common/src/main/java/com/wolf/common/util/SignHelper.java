package com.wolf.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 签名计算工具类
 */
public class SignHelper {
    private static final Log logger = LogFactory.getLog(SignHelper.class);

    public static String generateSign(Map<String, String> params, String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String encodeString = getEncodeString(params, secret);
        logger.info(String.format("encodeString: %s", encodeString));
        String sign = generateSign(encodeString);
        logger.info(String.format("generateSign: %s", sign));
        return sign;
    }

    private static String getEncodeString(Map<String, String> params, String secret) {
        Iterator<String> keyIter = params.keySet().iterator();
        Set<String> sortedParams = new TreeSet<>();
        while (keyIter.hasNext()) {
            sortedParams.add(keyIter.next());
        }

        StringBuilder strB = new StringBuilder(secret);

        // 排除sign和空值参数
        for (String key : sortedParams) {
            if (key.equals("sign")) {
                continue;
            }

            String value = params.get(key);

            if (StringUtils.isNotEmpty(value)) {
                strB.append(key).append(value);
            }
        }
        return strB.toString();
    }

    private static String generateSign(String content) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return SHA1Util.Sha1(content).toLowerCase();
    }
}
