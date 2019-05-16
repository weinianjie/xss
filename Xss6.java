package com.xss;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2018/8/2.
 */
public class Xss6 {

    Pattern p = null;
    Matcher m = null;
    HashMap<String, String> map = null;
    StringBuilder sb = null;

    public String run(String content){

//             6.寻找:
//               String mobileNo = (String)sessionCtx.get("session_B2EmobileNo");
//               String allAmount = (String)sessionCtx.get("session_amount");
//               String dataSign = signData;
//               且
//               <%=mobileNo %>
//               <%=allAmount %>
//               <%=dataSign %>
//               替换:
//               <c:out value="${sessionContext.session_B2EmobileNo}"/>
//               <c:out value="${sessionContext.session_amount}"/>
//               <c:out value="${context.signData}"/>
        map = new HashMap<String, String>();

        p = Pattern.compile("String\\s*(\\w*)\\s*=\\s*\\(String\\)sessionCtx\\.get\\(\"(session_B2EmobileNo|session_amount)\"\\);");// 前置条件1和2
        m = p.matcher(content);
        while(m.find()) {
            map.put(m.group(1),"sessionContext." + m.group(2));
        }

        p = Pattern.compile("String\\s*dataSign\\s*=\\s*signData;");// 前置条件3
        m = p.matcher(content);
        while(m.find()) {
            map.put("dataSign","context.signData");
        }

        for(Map.Entry<String, String> entry : map.entrySet()){
            p = Pattern.compile("<%=\\s*" + entry.getKey() + "\\s*%>");
            if(p.matcher(content).find()){
                content = content.replaceAll("<%=\\s*" + entry.getKey() + "\\s*%>", "<c:out value=\"\\${" + entry.getValue() + "}\"/>");// 关键字替换
            }
        }
        return content;
    }
}
