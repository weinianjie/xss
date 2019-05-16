package com.xss;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2018/8/2.
 */
public class Xss3 {

    Pattern p = null;
    Matcher m = null;
    HashMap<String, String> map = null;
    StringBuilder sb = null;

    public String run(String content){

//             3.寻找:
//               String xxxxx = context.getDataValue("xxxxx");
//               String xxxxx = (String)context.getDataValue("xxxxx");
//               String xxxxx = utb.nvl(context.getDataValue("xxxxx"));
//               String xxxxx = utb.nvl(context.getDataValue("xxxxx"),"");
//               String xxxxx = utb.nvl(context.get("xxxxx"),"");
//               String xxxxx = (String)context.get("xxxxx");
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

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*\\(String\\)\\s*context\\.getDataValue\\(\"\\1\"\\);");// 前置条件2
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*\\(String\\)\\s*context\\.getDataValue\\(\"" + m.group(1) + "\"\\);");// 保存前置条件和关键字
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

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(context\\.get\\(\"\\1\"\\),\\s*\"\"\\);");// 前置条件5
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(context\\.get\\(\"" + m.group(1) + "\"\\),\\s*\"\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*\\(String\\)context\\.get\\(\"\\1\"\\);");// 前置条件6
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*\\(String\\)context\\.get\\(\"" + m.group(1) + "\"\\);");// 保存前置条件和关键字
        }

        for(Map.Entry<String, String> entry : map.entrySet()){
            p = Pattern.compile("<%=\\s*" + entry.getKey() + "\\s*%>");
            if(p.matcher(content).find()){
                content = content.replaceAll(entry.getValue(), "");// 删除前置条件
                content = content.replaceAll("<%=\\s*" + entry.getKey() + "\\s*%>", "<c:out value=\"\\${context." + entry.getKey() + "}\"/>");// 关键字替换
            }
        }
        return content;
    }
}
