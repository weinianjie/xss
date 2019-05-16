package com.xss;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2018/8/2.
 */
public class Xss8 {

    Pattern p = null;
    Matcher m = null;
    HashMap<String, String> map = null;
    StringBuilder sb = null;

    public String run(String content){
//            8.寻找:
//                  <%=utb.getRequiredHtmlFields ( true ) %>
//                  <%=utb.getRequiredHtmlFields ( false ) %>
//                  <%=utb.getConfirmFields(request)%>
//              替换:
//                    <%--<%=utb.getRequiredHtmlFields(true)%>--%>
//                    <% if(utb.haveSession(request) && utb.getSessionTraceType(request) != 0){
//                        pageContext.setAttribute("sessionIdLabel",utb.getSessionIdLabel(request));
//                        pageContext.setAttribute("sessionId2",utb.getSessionId2(request));
//                        pageContext.setAttribute("serverTime",utb.getServerTime(null));
//                    %>
//                        <input type="hidden" name="<c:out value="${sessionIdLabel}"/>" value="<c:out value="${sessionId2}"/>"/>
//                        <input type="hidden" name="submitTimestamp" value="<c:out value="${serverTime}"/>"/>
//                    <% } %>
//--------------------------
//                    <%--<%=utb.getRequiredHtmlFields(false)%>--%>
//                    <% if(utb.haveSession(request) && utb.getSessionTraceType(request) != 0){
//                        pageContext.setAttribute("sessionIdLabel",utb.getSessionIdLabel(request));
//                        pageContext.setAttribute("sessionId2",utb.getSessionId2(request));
//                    %>
//                        <input type="hidden" name="<c:out value="${sessionIdLabel}"/>" value="<c:out value="${sessionId2}"/>"/>
//                    <% } %>
//---------------------------
//                    <%--<%=utb.getConfirmFields(request)%>--%>
//                    <% pageContext.setAttribute("confirmFieldsMap", utb.getConfirmFieldsMap(request)); %>
//                    <c:forEach var="item" items="${confirmFieldsMap}">
//                    <c:forEach var="item2" items="${item.value}">
//                    <input type="hidden" name="<c:out value="${item.key}"/>" value="<c:out value="${item2}"/>"/>
//                    </c:forEach>
//                    </c:forEach>
        p = Pattern.compile("<%=\\s*(utb\\.getRequiredHtmlFields\\s*\\(\\s*true\\s*\\))\\s*%>");
        if(p.matcher(content).find()) {
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
            content = content.replaceAll(p.pattern(), sb.toString());
        }
        p = Pattern.compile("<%=\\s*(utb\\.getRequiredHtmlFields\\s*\\(\\s*false\\s*\\))\\s*%>");
        if(p.matcher(content).find()) {
            sb = new StringBuilder();
            sb.append("\r\n\t").append("<%--<%=utb.getRequiredHtmlFields(false)%>--%>");
            sb.append("\r\n\t").append("<% if(utb.haveSession(request) && utb.getSessionTraceType(request) != 0){");
            sb.append("\r\n\t\t").append("pageContext.setAttribute(\"sessionIdLabel\",utb.getSessionIdLabel(request));");
            sb.append("\r\n\t\t").append("pageContext.setAttribute(\"sessionId2\",utb.getSessionId2(request));");
            sb.append("\r\n\t").append("%>");
            sb.append("\r\n\t\t").append("<input type=\"hidden\" name=\"<c:out value=\"\\${sessionIdLabel}\"/>\" value=\"<c:out value=\"\\${sessionId2}\"/>\"/>");
            sb.append("\r\n\t").append("<% } %>");
            content = content.replaceAll(p.pattern(), sb.toString());
        }
        p = Pattern.compile("<%=\\s*(utb\\.getConfirmFields\\s*\\(\\s*request\\s*\\))\\s*%>");
        if(p.matcher(content).find()) {
            sb = new StringBuilder();
            sb.append("\r\n\t").append("<%--<%=utb.getConfirmFields(request)%>--%>");
            sb.append("\r\n\t").append("<% pageContext.setAttribute(\"confirmFieldsMap\", utb.getConfirmFieldsMap(request)); %>");
            sb.append("\r\n\t").append("<c:forEach var=\"item\" items=\"\\${confirmFieldsMap}\">");
            sb.append("\r\n\t\t").append("<c:forEach var=\"item2\" items=\"\\${item.value}\">");
            sb.append("\r\n\t\t\t").append("<input type=\"hidden\" name=\"<c:out value=\"\\${item.key}\"/>\" value=\"<c:out value=\"\\${item2}\"/>\"/>");
            sb.append("\r\n\t\t").append("</c:forEach>");
            sb.append("\r\n\t").append("</c:forEach>");
            content = content.replaceAll(p.pattern(), sb.toString());
        }
        return content;
    }
}
