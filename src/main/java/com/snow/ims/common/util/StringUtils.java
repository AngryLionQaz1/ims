package com.snow.ims.common.util;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {

    /**
     * 处理内存分页
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static <T> List<T> getPageConfigList(List<T> list, int pageNo, int pageSize){
        List<T> currentPagelist = null;
        if (list != null && !list.isEmpty()) {
            currentPagelist = new ArrayList<T>();
            int iEnd = 0;
            int iStart = 0;

            iStart = pageSize * (pageNo - 1);
            iEnd = iStart + pageSize;
            if (iEnd > list.size()) {
                iEnd = list.size();
            }
            for (int i = iStart; i < iEnd; i++) {
                currentPagelist.add(list.get(i));
            }

        }
        return currentPagelist;
    }



    /**判断身份证*/
    public static boolean isIdCard(String idCard){
        //第一代身份证正则表达式(15位)
        String isIDCard1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        //第二代身份证正则表达式(18位)
        String isIDCard2 ="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$";

        //验证身份证
        if (idCard.matches(isIDCard1) || idCard.matches(isIDCard2)) {
            return true;
        }
        return false;
    }

    /**2到8个字的汉字,或者2到16个字的英文*/
    public static boolean isRealName(String realName){
        String reg ="^(([\u4e00-\u9fa5]{2,8})|([a-zA-Z]{2,16}))$";
        final Pattern pattern = Pattern.compile(reg);
        final Matcher mat = pattern.matcher(realName);
        return mat.matches();
    }

    public static boolean isEmail(String email) {
        try {
            // 正常邮箱
            // /^\w+((-\w)|(\.\w))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
            // 含有特殊 字符的 个人邮箱 和 正常邮箱
            // js: 个人邮箱
            // /^[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+@[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+(\.[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+)+$/
            // java：个人邮箱
            // [\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+@[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+\\.[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+
            // 范围 更广的 邮箱验证 “/^[^@]+@.+\\..+$/”
            final String pattern1 = "[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+@[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+\\.[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+";
            final Pattern pattern = Pattern.compile(pattern1);
            final Matcher mat = pattern.matcher(email);
            return mat.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static final String REGEX_MOBILE = "(134[0-8]\\d{7})" + "|(" + "((13([0-3]|[5-9]))" + "|149"
            + "|15([0-3]|[5-9])" + "|166" + "|17(3|[5-8])" + "|18[0-9]" + "|19[8-9]" + ")" + "\\d{8}" + ")";

    /**
     * 判断是否是手机号
     *
     * @param tel
     *            手机号
     * @return boolean true:是 false:否
     */
    public static boolean isMobile(String tel) {
        return Pattern.matches(REGEX_MOBILE, tel);
    }



    /**
     * 生成strLength位 随机数
     */
    public static String getNumber(Integer strLength){

        Random rm = new Random();
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);

    }

    /**
     * 订单号
     */
    public static String orderNumber(){
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String seconds = new SimpleDateFormat("HHmmss").format(new Date());
        //产生随机的2位数
        Random rad=new Random();
        String result  = rad.nextInt(100) +"";
        if(result.length()==1){
            result = "0" + result;
        }
        return date+"000010000"+result+seconds+result;
    }


    /**
     * 获取string
     */
    public static List<String> getStrings(String str){
         String[] strs=str.split(",");
        return Arrays.stream(strs).collect(Collectors.toList());
    }


    /**
     * 判断是不是空
     */
    public static boolean isNotNull(Object o){
        if ((o!=null)&&(!"".equals(o)))return true;
        return false;
    }

    public static boolean isNull(Object o){
        return !isNotNull(o);
    }

   /**uuid*/
   public static String getUUID(){
      return UUID.randomUUID().toString().replace("-", "").toLowerCase();
   }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

}
