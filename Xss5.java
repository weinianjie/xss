package com.xss;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2018/8/2.
 */
public class Xss5 {

    Pattern p = null;
    Matcher m = null;
    HashMap<String, String> map = null;
    StringBuilder sb = null;

    public String run(String content){

//             5.寻找:
//               <%@ include file="password.jsp" %>
//               <%@ include file="./password.jsp" %>
//               <%@ include file="../password.jsp" %>
//               <%@ include file="../xxxxx/password.jsp" %>
//               替换:
//               <%--<%@ include file="password.jsp" %>--%>
//               <%--<%@ include file="./password.jsp" %>--%>
//               <%--<%@ include file="../password.jsp" %>--%>
//               <%--<%@ include file="../xxxxx/password.jsp" %>--%>
//                加上
//                <script language="javascript" src="app_static/js/RsaControl/unionrsa1.js"></script>
//                <script language="javascript" src="app_static/js/RsaControl/client.js"></script>
//                <script language="javascript" src="app_static/js/RsaControl/password2Jsp.js"></script>
//                <script type="text/javascript">
//                var passowdTime = "<c:out value="${timeMillis}"/>";
//                </script>
        sb = new StringBuilder();
        sb.append("<%--$0--%>");// 注释掉include导入
        sb.append("\r\n").append("<script language=\"javascript\" src=\"app_static/js/RsaControl/unionrsa1.js\"></script>");
        sb.append("\r\n").append("<script language=\"javascript\" src=\"app_static/js/RsaControl/client.js\"></script>");
        sb.append("\r\n").append("<script language=\"javascript\" src=\"app_static/js/RsaControl/password2Jsp.js\"></script>");
        sb.append("\r\n").append("<script type=\"text/javascript\">");
        sb.append("\r\n\t").append("var passowdTime = \"<c:out value=\"\\${timeMillis}\"/>\";");
        sb.append("\r\n").append("</script>");
        sb.append("\r\n");
        content = content.replaceAll("<%@\\s*include\\s*file=\"[\\./\\w]*password\\.jsp\"\\s*%>(?!--)",sb.toString());
        return content;
    }
}
