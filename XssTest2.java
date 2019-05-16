package com.xss;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2017/10/17.
 */
public class XssTest2 {


    public static void main(String[] args) {
        String content = "String bankId1 = utb.get(\"bankId1\");\n" +
                "String bankId2 = utb.nvl(utb.get(\"bankId2\"));\n" +
                "String bankId3 = utb.nvl(utb.get(\"bankId3\"),\"\");\n" +
                "String bankId4 = (String)utb.get(\"bankId4\");\n" +
                "String bankId5 = utb.nvl((String)utb.get(\"bankId5\"));\n" +
                "String bankId6 = utb.nvl((String)utb.get(\"bankId6\"),\"\");\n" +
                "String bankId7 = request.getParameter(\"bankId7\");\n" +
                "String bankId8 = utb.nvl(request.getParameter(\"bankId8\"));\n" +
                "String bankId9 = utb.nvl(request.getParameter(\"bankId9\"),\"\");\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId1 %>\"/>\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId2 %>\" />\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId3 %>\" />\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId4 %>\" />\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId5 %>\" />\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId6 %>\" />\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId7 %>\" />\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId8 %>\" />\n" +
                "<input type=\"hidden\" name=\"bankId\" value=\"<%=bankId9 %>\" />\n" +
                "<input type=\"hidden\" name=\"orderId\" value=\"<%=orderId %>\" />\n";
        Pattern p = null;
        Matcher m = null;
        HashMap<String, String> map = null;

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
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*utb\\.get\\(\"" + m.group(1) + "\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(utb\\.get\\(\"\\1\"\\)\\);");// 前置条件2
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(utb\\.get\\(\"" + m.group(1) + "\"\\)\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(utb\\.get\\(\"\\1\"\\),\\s*\"\"\\);");// 前置条件3
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(utb\\.get\\(\"" + m.group(1) + "\"\\),\\s*\"\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*\\(String\\)utb\\.get\\(\"\\1\"\\);");// 前置条件4
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*\\(String\\)utb\\.get\\(\"" + m.group(1) + "\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(\\(String\\)utb\\.get\\(\"\\1\"\\)\\);");// 前置条件5
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(\\(String\\)utb\\.get\\(\"" + m.group(1) + "\"\\)\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(\\(String\\)utb\\.get\\(\"\\1\"\\),\\s*\"\"\\);");// 前置条件6
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(\\(String\\)utb\\.get\\(\"" + m.group(1) + "\"\\),\\s*\"\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*request\\.getParameter\\(\"\\1\"\\);");// 前置条件7
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*request\\.getParameter\\(\"" + m.group(1) + "\"\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(request\\.getParameter\\(\"\\1\"\\)\\);");// 前置条件8
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(request\\.getParameter\\(\"" + m.group(1) + "\"\\)\\);");// 保存前置条件和关键字
        }

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*utb\\.nvl\\(request\\.getParameter\\(\"\\1\"\\),\\s*\"\"\\);");// 前置条件9
        m = p.matcher(content);
        while(m.find()) {
//            map.put(m.group(1),m.group(0));// 保存前置条件和关键字
            map.put(m.group(1),"String\\s*" + m.group(1) + "\\s*=\\s*utb\\.nvl\\(request\\.getParameter\\(\"" + m.group(1) + "\"\\),\\s*\"\"\\);");// 保存前置条件和关键字
        }

        for(Map.Entry<String, String> entry : map.entrySet()){
            p = Pattern.compile("<%=\\s*" + entry.getKey() + "\\s*%>");
            if(p.matcher(content).find()){
                content = content.replaceAll(entry.getValue(), "");// 删除前置条件
                content = content.replaceAll("<%=\\s*" + entry.getKey() + "\\s*%>", "<c:out value=\"\\${param." + entry.getKey() + "}\"/>");// 关键字替换
            }
        }

        System.out.println(content);
    }
}
