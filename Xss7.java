package com.xss;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2018/8/2.
 */
public class Xss7 {

    Pattern p = null;
    Matcher m = null;
    HashMap<String, String> map = null;
    StringBuilder sb = null;

    public String run(String content){
//             7.寻找:
//               <%=utb.formatCurrencyData(allAmount) %>
//               <%=utb.getFmtDate(orderDate,"yyyy年MM月dd日 HH时mm分ss秒") %>
//               替换:
//               <c:out value="${formatAllAmount}"/>
//               <c:out value="${formatOrderDate}"/>
//                且内容前附加
//                formatAllAmount|
//                formatOrderDate|
        p = Pattern.compile("<%=\\s*utb\\.formatCurrencyData\\(allAmount\\)\\s*%>");
        if(p.matcher(content).find()) {
            content = "formatAllAmount|" + content.replaceAll(p.pattern(), "<c:out value=\"\\${formatAllAmount}\"/>");
        }

        p = Pattern.compile("<%=\\s*utb\\.getFmtDate\\(orderDate,\"yyyy年MM月dd日\\s*HH时mm分ss秒\"\\)\\s*%>");
        if(p.matcher(content).find()) {
            content = "formatOrderDate|" + content.replaceAll(p.pattern(), "<c:out value=\"\\${formatOrderDate}\"/>");
        }
        return content;
    }
}
