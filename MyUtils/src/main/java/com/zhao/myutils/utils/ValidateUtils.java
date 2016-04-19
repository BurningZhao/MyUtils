package com.zhao.myutils.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    // 用于匹配手机号码
    private static final String REGEX_MOBILE_PHONE =
            "^(0|\\+?86-?|17951)?((13[0-9])|(15[^4,\\D])|(14[57])|(18[0-9])|(17[678]))\\d{8}$";
    // 用于匹配固定电话号码
    private static final String REGEX_DIDPHONE = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";
    // 用于匹配400电话
    private static final String REGEX_FOUR_HUNDERD_PHONE = "^400\\d{7}$";

    // 用于匹配100、955等电话
    private static final String REGEX_SPEICAL_PHONE = "^955\\d{2}$";
    private static final String REGEX_SPEICAL_TWO_PHONE = "^(10|11|12)\\d{3,7}";

    // 用于匹配邮箱
    private final static String REGEX_EMAIL =
            "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    // 用于验证URL
    private static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    // 用于验证IP地址
    private static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    // 用于匹配数字
    private static final String REGEX_NUMBER = "[0-9]*";

    // 用于匹配中文字符
    private static final String REGEX_CHINESE = "^[\u4E00-\u9FA5]+$";

    // 用于验证字符串是否是日期
    private static final String REGEX_DATE = "^((\\\\d{2}(([02468][048])|([13579][26]))" +
            "[\\\\-\\\\/\\\\s]?((((0?[13578])|(1[02]))[\\\\-\\\\/\\\\s]?" +
            "((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\\\-\\\\/\\\\s]?" +
            "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\\\-\\\\/\\\\s]?((0?[1-9])|" +
            "([1-2][0-9])))))|(\\\\d{2}(([02468][1235679])|([13579][01345789]))[\\\\-\\\\/\\\\s]?" +
            "((((0?[13578])|(1[02]))[\\\\-\\\\/\\\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))" +
            "[\\\\-\\\\/\\\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\\\-\\\\/\\\\s]?" +
            "((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\\\s(((0?[0-9])|" +
            "([1-2][0-3]))\\\\:([0-5]?[0-9])((\\\\s)|(\\\\:([0-5]?[0-9])))))?$";

    /**
     * 用于验证电话号码，包括手机、座机、400电话等
     */
    public static boolean isPhoneNo(String numbers) {
        return isMobileNO(numbers) || isDidPhoneNO(numbers)
                || isFourHundredPhone(numbers) || isSpecialPhone(numbers)
                || isSpecialTwoPhone(numbers);
    }

    /**
     * 验证是否是手机号
     */
    public static boolean isMobileNO(String mobiles) {
        if (StringUtil.isBlank(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_MOBILE_PHONE);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证是否是座机号
     */
    public static boolean isDidPhoneNO(String mobiles) {
        if (StringUtil.isBlank(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_DIDPHONE);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证是否是400电话
     */
    public static boolean isFourHundredPhone(String fourHundredNo) {
        if (StringUtil.isBlank(fourHundredNo)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_FOUR_HUNDERD_PHONE);
        Matcher m = p.matcher(fourHundredNo);
        return m.matches();
    }

    /**
     * 用于匹配100、955等电话
     */
    public static boolean isSpecialPhone(String numbers) {
        if (StringUtil.isBlank(numbers)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_SPEICAL_PHONE);
        Matcher m = p.matcher(numbers);
        return m.matches();
    }

    /**
     * 用于匹配11\12\10开头的电话
     */
    public static boolean isSpecialTwoPhone(String numbers) {
        if (StringUtil.isBlank(numbers)) {
            return false;
        }
        Pattern p = Pattern.compile(REGEX_SPEICAL_TWO_PHONE);
        Matcher m = p.matcher(numbers);
        return m.matches();
    }

    /**
     * 验证是否是邮箱
     */
    public static boolean isEmail(String content) {
        return !StringUtil.isBlank(content) && content.matches(REGEX_EMAIL);
    }

    /**
     * 验证中文
     */
    public static boolean isChinese(String chinese) {
        Pattern p = Pattern.compile(REGEX_CHINESE);
        Matcher m = p.matcher(chinese);
        return m.matches();
    }

    // 完整的判断中文汉字和符号
    public static boolean isAllChinese(String strName) {
        if (strName == null) {
            return false;
        }
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    /**
     * 校验URL
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     */
    public static String validateIDStr(String IDStr) {
        String errorInfo;// 记录错误信息
        String[] ValCodeArr = {"1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // 号码的长度 15位或18位
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (!isDigit(Ai)) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (!isDate(strYear + "-" + strMonth + "-" + strDay)) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();

        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || DateTimeUtils.countDays(strYear + "-" + strMonth + "-" + strDay, DateTimeUtils.FORMAT_SHORT) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return errorInfo;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }

        // ================ 判断最后一位的值 ================
        int totalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            totalmulAiWi = totalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = totalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue].toUpperCase();
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (!Ai.equals(IDStr)) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "";
        }
        return "";
    }

    /**
     * 判断一个字符串是否都为数字
     */
    public static boolean isDigit(String strNum) {
        return !StringUtil.isBlank(strNum) && strNum.matches(REGEX_NUMBER);
    }

    /*********************************** 身份证验证开始 ****************************************/
    /**
     * 身份证号码验证
     * 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
     *       八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * 2、地址码(前六位数）表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
     * 3、出生日期码（第七位至十四位）表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
     * 4、顺序码（第十五位至十七位）表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，
     *        顺序码的奇数分配给男性，偶数分配给女性。
     * 5、校验码（第十八位数）
     * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
     *          Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * （2）计算模 Y = mod(S, 11)
     * （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     */

    /**
     * 功能：判断字符串是否为日期格式
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern.compile(REGEX_DATE);
        Matcher m = pattern.matcher(strDate);
        return m.matches();
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    private static Hashtable GetAreaCode() {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }
}
