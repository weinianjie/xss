package com.xss;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2018/8/2.
 */
public class Xss11 {

    Pattern p = null;
    Matcher m = null;
    HashMap<String, String> map = null;
    StringBuilder sb = null;

    // 解决utb的一些问题
    public String run(String content){
//             11.
//                11-1.解决getRequiredHtmlFields
//                    <%--<%=utb.getRequiredHtmlFields(true)%>--%>
//                    <% if(utb.haveSession(request) && utb.getSessionTraceType(request) != 0){
//                        pageContext.setAttribute("sessionIdLabel",utb.getSessionIdLabel(request));
//                        pageContext.setAttribute("sessionId2",utb.getSessionId2(request));
//                        pageContext.setAttribute("serverTime",utb.getServerTime(null));
//                    %>
//                        <input type="hidden" name="<c:out value="${sessionIdLabel}"/>" value="<c:out value="${sessionId2}"/>"/>
//                        <input type="hidden" name="submitTimestamp" value="<c:out value="${serverTime}"/>"/>
//                    <% } %>
        if(content.indexOf("requiredHtmlFieldsTrue") != -1) {
            sb = new StringBuilder();
            sb.append("\r\n\t").append("<%--<%=utb.getRequiredHtmlFields(true)%>--%>");
            sb.append("\r\n\t").append("<% if(utb.haveSession(request) && utb.getSessionTraceType(request) != 0){");
            sb.append("\r\n\t\t").append("pageContext.setAttribute(\"sessionIdLabel\",utb.getSessionIdLabel(request));");
            sb.append("\r\n\t\t").append("pageContext.setAttribute(\"sessionId2\",utb.getSessionId2(request));");
            sb.append("\r\n\t\t").append("pageContext.setAttribute(\"serverTime\",utb.getServerTime(null));");
            sb.append("\r\n\t").append("%>");
            sb.append("\r\n\t\t").append("<input type=\"hidden\" name=\"<c:out value=\"\\${sessionIdLabel}\"/>\" value=\"<c:out value=\"\\${sessionId2}\"/>\"/>");
            sb.append("\r\n\t\t").append("<input type=\"hidden\" name=\"submitTimestamp\" value=\"<c:out value=\"\\${serverTime}\"/>\"/>");
            sb.append("\r\n\t").append("<% } %>");
            content = content.replaceAll("[\\s\\n\\r\\t]*<%\\s*pageContext\\.setAttribute\\(\"requiredHtmlFieldsTrue\",utb\\.getRequiredHtmlFields\\(true\\)\\);\\s*%>[\\s\\n\\r\\t]*<c:out\\s*value=\"\\$\\{requiredHtmlFieldsTrue\\}\"\\s*/>", sb.toString());
        }
        if(content.indexOf("requiredHtmlFieldsFalse") != -1) {
            sb = new StringBuilder();
            sb.append("\r\n\t").append("<%--<%=utb.getRequiredHtmlFields(false)%>--%>");
            sb.append("\r\n\t").append("<% if(utb.haveSession(request) && utb.getSessionTraceType(request) != 0){");
            sb.append("\r\n\t\t").append("pageContext.setAttribute(\"sessionIdLabel\",utb.getSessionIdLabel(request));");
            sb.append("\r\n\t\t").append("pageContext.setAttribute(\"sessionId2\",utb.getSessionId2(request));");
            sb.append("\r\n\t").append("%>");
            sb.append("\r\n\t\t").append("<input type=\"hidden\" name=\"<c:out value=\"\\${sessionIdLabel}\"/>\" value=\"<c:out value=\"\\${sessionId2}\"/>\"/>");
            sb.append("\r\n\t").append("<% } %>");
            content = content.replaceAll("[\\s\\n\\r\\t]*<%\\s*pageContext\\.setAttribute\\(\"requiredHtmlFieldsFalse\",utb\\.getRequiredHtmlFields\\(false\\)\\);\\s*%>[\\s\\n\\r\\t]*<c:out\\s*value=\"\\$\\{requiredHtmlFieldsFalse\\}\"\\s*/>", sb.toString());
        }

//                11-2.解决getConfirmFields
//                    <%--<%=utb.getConfirmFields(request)%>--%>
//                    <% pageContext.setAttribute("confirmFieldsMap", utb.getConfirmFieldsMap(request)); %>
//                    <c:forEach var="item" items="${confirmFieldsMap}">
//                    <c:forEach var="item2" items="${item.value}">
//                    <input type="hidden" name="<c:out value="${item.key}"/>" value="<c:out value="${item2}"/>"/>
//                    </c:forEach>
//                    </c:forEach>
        if(content.indexOf("confirmFields") != -1) {
            sb = new StringBuilder();
            sb.append("\r\n\t").append("<%--<%=utb.getConfirmFields(request)%>--%>");
            sb.append("\r\n\t").append("<% pageContext.setAttribute(\"confirmFieldsMap\", utb.getConfirmFieldsMap(request)); %>");
            sb.append("\r\n\t").append("<c:forEach var=\"item\" items=\"\\${confirmFieldsMap}\">");
            sb.append("\r\n\t\t").append("<c:forEach var=\"item2\" items=\"\\${item.value}\">");
            sb.append("\r\n\t\t\t").append("<input type=\"hidden\" name=\"<c:out value=\"\\${item.key}\"/>\" value=\"<c:out value=\"\\${item2}\"/>\"/>");
            sb.append("\r\n\t\t").append("</c:forEach>");
            sb.append("\r\n\t").append("</c:forEach>");
            content = content.replaceAll("[\\s\\n\\r\\t]*<%\\s*pageContext\\.setAttribute\\(\"confirmFields\",utb\\.getConfirmFields\\(request\\)\\);\\s*%>[\\s\\n\\r\\t]*<c:out value=\"\\$\\{confirmFields\\}\"\\s*/>", sb.toString());
        }
        return content;
    }
}
