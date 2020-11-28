package com.wolf.common.util;


import java.math.BigDecimal;

/**
 * BigDecimal计算工具类
 */
public class BigDecimalUtil {
    /**
     * 获得
     *
     * @param price
     * @param size
     * @return
     */
    public static String getFormatString(BigDecimal price, int size) {
        price = price == null ? BigDecimal.valueOf(0) : price;
        return price.setScale(size, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }

    public static String getFormatString(Double param, int size) {
        BigDecimal price = BigDecimalUtil.getBigDecimal(param);
        return price.setScale(size, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }

    public static BigDecimal getBigDecimal(Double salePrice) {
        salePrice = salePrice == null ? 0 : salePrice;
        return BigDecimal.valueOf(salePrice);
    }


    public static BigDecimal getBigDecimal(Integer salePrice) {
        salePrice = salePrice == null ? 0 : salePrice;
        return BigDecimal.valueOf(salePrice);
    }


    /**
     * 加
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.ZERO;
        }
        return v1.add(v2);
    }

    /**
     * 减
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    /**
     * 减法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        return v1.subtract(v2);
    }


    /**
     * 乘
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        return v1.multiply(v2);
    }

    /**
     * 除
     * 四舍五入，默认保留2位小数
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);//四舍五入,保留2位小数
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return v1.divide(v2, 2, BigDecimal.ROUND_HALF_UP);//四舍五入,保留2位小数
    }

    /**
     * 出发运算保留4位小数
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal divFloat(BigDecimal v1, BigDecimal v2) {
        return v1.divide(v2, 4, BigDecimal.ROUND_HALF_UP);//四舍五入,保留4位小数
    }

    /**
     * 除法向上取整
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal ceilUp(BigDecimal v1, BigDecimal v2) {
        BigDecimal result = v1.divide(v2, 4, BigDecimal.ROUND_HALF_UP);
        double upValue = Math.ceil(result.doubleValue());
        return BigDecimal.valueOf(upValue);
    }

    /**
     * 比较2个数大小
     *
     * @param v1
     * @return
     */
    public static boolean compareToZero(BigDecimal v1) {
        if (v1.compareTo(BigDecimal.ZERO) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 比较2个数大小
     *
     * @param v1
     * @param v2
     * @return
     */
    public static boolean compareToElse(BigDecimal v1, BigDecimal v2) {
        if (v1.compareTo(v2) >= 0) {
            return true;
        }
        return false;
    }
    /**
     * 保留2位小数
     *
     * @param v
     * @return
     */
    public static String round(String v) {
        BigDecimal b = new BigDecimal(v);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 保留2位小数
     *
     * @param v
     * @return
     */
    public static BigDecimal round(BigDecimal v) {
        return v.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
