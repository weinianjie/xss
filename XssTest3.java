package com.xss;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2017/10/17.
 */
public class XssTest3 {
    public static void main(String[] args) {
        String content = "String bankId1 = context.getDataValue(\"bankId1\");\n" +
                "String bankId2 = (String)context.getDataValue(\"bankId2\");\n" +
                "String bankId3 = utb.nvl(context.getDataValue(\"bankId3\"));\n" +
                "String bankId4 = utb.nvl(context.getDataValue(\"bankId4\"),\"\");\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId1 %>\"/>\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId2 %>\"/>\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId3 %>\"/>\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId4 %>\"/>\n" +
                "<input type=\"hidden\" name=\"orderId\" value=\"<%=orderId %>\" />\n";
        Pattern p = null;
        Matcher m = null;
        HashMap<String, String> map = null;

//             3.寻找:
//               String xxxxx = context.getDataValue("xxxxx");
//               String xxxxx = (String)context.getDataValue("xxxxx");
//               String xxxxx = utb.nvl(context.getDataValue("xxxxx"));
//               String xxxxx = utb.nvl(context.getDataValue("xxxxx"),"");
//               且
//               <%=xxxxx %>
//               替换:
//               <c:out value="${context.xxxxx}"/>
        map = new HashMap<String, String>();

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*context\\.getDataValue\\(\"\\1\"\\);");// 前置条件1
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*context\\.getDataValue\\(\"" + m.group(1) + "\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*\\(String\\)context\\.getDataValue\\(\"\\1\"\\);");// 前置条件2
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*\\(String\\)context\\.getDataValue\\(\"" + m.group(1) + "\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(context\\.getDataValue\\(\"\\1\"\\)\\);");// 前置条件3
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(context\\.getDataValue\\(\"" + m.group(1) + "\"\\)\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(context\\.getDataValue\\(\"\\1\"\\),\\s*\"\"\\);");// 前置条件4
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(context\\.getDataValue\\(\"" + m.group(1) + "\"\\),\\s*\"\"\\);");// 保存前置条件和关键字
        }

        for(Map.Entry<String, String> entry : map.entrySet()){
            p = Pattern.compile("<%=\\s*" + entry.getKey() + "\\s*%>");
            if(p.matcher(content).find()){
                content = content.replaceAll(entry.getValue(), "");// 删除前置条件
                content = content.replaceAll("<%=\\s*" + entry.getKey() + "\\s*%>", "<c:out value=\"\\${context." + entry.getKey() + "}\"/>");// 关键字替换
            }
        }

        System.out.println(content);
    }
}
