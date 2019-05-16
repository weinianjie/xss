package com.xss;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2018/8/2.
 */
public class Xss2 {

    Pattern p = null;
    Matcher m = null;
    HashMap<String, String> map = null;
    StringBuilder sb = null;

    public String run(String content){
//             2.寻找:
//               String xxxxx = utb.get("xxxxx");
//               String xxxxx = utb.nvl(utb.get("xxxxx"));
//               String xxxxx = utb.nvl(utb.get("xxxxx"),"");
//               String xxxxx = (String)utb.get("xxxxx");
//               String xxxxx = utb.nvl((String)utb.get("xxxxx"));
//               String xxxxx = utb.nvl((String)utb.get("xxxxx"),"");
//               String xxxxx = request.getParameter("xxxxx");
//               String xxxxx = utb.nvl(request.getParameter("xxxxx"));
//               String xxxxx = utb.nvl(request.getParameter("xxxxx"),"");
//               且
//               <%=xxxxx %>
//               替换:
//               <c:out value="${param.xxxxx}"/>
        map = new HashMap<String, String>();

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.get\\(\"\\1\"\\);");// 前置条件1
        m = p.matcher(content);
        while (m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1), "String\\s*" + m.group(1) + "\\s*=\\s*utb\\.get\\(\"" + m.group(1) + "\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(utb\\.get\\(\"\\1\"\\)\\);");// 前置条件2
        m = p.matcher(content);
        while (m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1), "String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(utb\\.get\\(\"" + m.group(1) + "\"\\)\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(utb\\.get\\(\"\\1\"\\),\\s*\"\"\\);");// 前置条件3
        m = p.matcher(content);
        while (m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1), "String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(utb\\.get\\(\"" + m.group(1) + "\"\\),\\s*\"\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*\\(String\\)utb\\.get\\(\"\\1\"\\);");// 前置条件4
        m = p.matcher(content);
        while (m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1), "String\\s*" + m.group(1) + "\\s*=\\s*\\(String\\)utb\\.get\\(\"" + m.group(1) + "\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(\\(String\\)utb\\.get\\(\"\\1\"\\)\\);");// 前置条件5
        m = p.matcher(content);
        while (m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1), "String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(\\(String\\)utb\\.get\\(\"" + m.group(1) + "\"\\)\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(\\(String\\)utb\\.get\\(\"\\1\"\\),\\s*\"\"\\);");// 前置条件6
        m = p.matcher(content);
        while (m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1), "String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(\\(String\\)utb\\.get\\(\"" + m.group(1) + "\"\\),\\s*\"\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*request\\.getParameter\\(\"\\1\"\\);");// 前置条件7
        m = p.matcher(content);
        while (m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1), "String\\s*" + m.group(1) + "\\s*=\\s*request\\.getParameter\\(\"" + m.group(1) + "\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(request\\.getParameter\\(\"\\1\"\\)\\);");// 前置条件8
        m = p.matcher(content);
        while (m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1), "String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(request\\.getParameter\\(\"" + m.group(1) + "\"\\)\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(request\\.getParameter\\(\"\\1\"\\),\\s*\"\"\\);");// 前置条件9
        m = p.matcher(content);
        while (m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1), "String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(request\\.getParameter\\(\"" + m.group(1) + "\"\\),\\s*\"\"\\);");// 保存前置条件和关键字
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            p = Pattern.compile("<%=\\s*" + entry.getKey() + "\\s*%>");
            if (p.matcher(content).find()) {
                content = content.replaceAll(entry.getValue(), "");// 删除前置条件
                content = content.replaceAll("<%=\\s*" + entry.getKey() + "\\s*%>", "<c:out value=\"\\${param." + entry.getKey() + "}\"/>");// 关键字替换
            }
        }
        return content;
    }
}
