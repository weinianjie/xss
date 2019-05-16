package com.xss;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2018/8/2.
 */
public class Xss9 {

    Pattern p = null;
    Matcher m = null;
    HashMap<String, String> map = null;
    StringBuilder sb = null;

    public String run(String content){
//             9.寻找:
//               <%= utb.getContext().get("resultData")%>
//               替换:
//                <%
//                String outString = (String)utb.getContext().getDataValue("resultData");
//                // 如果是xml格式但又不含xml头，则补上，否则有些客户端无法解析，包括EMP的js端
//                if(outString.startsWith("<") && !outString.startsWith("<?xml")){
//                    outString = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + outString;
//                }
//                byte[] responseMessage = outString.getBytes("UTF-8");
//                response.setCharacterEncoding("UTF-8");
//                response.setContentLength(responseMessage.length);
//                response.getOutputStream().write(responseMessage);
//                response.flushBuffer();
//                out.clearBuffer();
//                out = pageContext.pushBody();
//                %>
        // 必须不能是json格式
        if(content.indexOf("application/json") == -1) {
            p = Pattern.compile("<%=\\s*utb\\.getContext\\(\\)\\.get\\(\"resultData\"\\)\\s*%>");
            if (p.matcher(content).find()) {
                sb = new StringBuilder();
                sb.append("\r\n").append("<%");
                sb.append("\r\n\t").append("String outString = (String)utb.getContext().getDataValue(\"resultData\");");
                sb.append("\r\n\t").append("// 如果是xml格式但又不含xml头，则补上，否则有些客户端无法解析，包括EMP的js端");
                sb.append("\r\n\t").append("if(outString.startsWith(\"<\") && !outString.startsWith(\"<?xml\")){");
                sb.append("\r\n\t\t").append("outString = \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" ?>\" + outString;");
                sb.append("\r\n\t").append("}");
                sb.append("\r\n\t").append("byte[] responseMessage = outString.getBytes(\"UTF-8\");");
                sb.append("\r\n\t").append("response.setCharacterEncoding(\"UTF-8\");");
                sb.append("\r\n\t").append("response.setContentLength(responseMessage.length);");
                sb.append("\r\n\t").append("response.getOutputStream().write(responseMessage);");
                sb.append("\r\n\t").append("response.flushBuffer();");
                sb.append("\r\n\t").append("out.clearBuffer();");
                sb.append("\r\n\t").append("out = pageContext.pushBody();");
                sb.append("\r\n").append("%>");
                content = content.replaceAll(p.pattern(), sb.toString());
            }
        }
        return content;
    }
}
