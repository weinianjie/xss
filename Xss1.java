package com.xss;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pt-weinj on 2018/8/2.
 */
public class Xss1 {

    Pattern p = null;
    Matcher m = null;
    HashMap<String, String> map = null;
    StringBuilder sb = null;

    public String run(String content){
//            1.寻找:
//                  <%=webAppPath %>
//                  <%=version%>
//                  <%=scriptPath%>
//                  <%=System.currentTimeMillis()%>
//                  <%=context.getDataElement().toString()%>--暂时不用
//              替换:
//                  <c:out value="${webAppPath}"/>
//                  <c:out value="${version}"/>
//                  <c:out value="${scriptPath}"/>
//                  <c:out value="${timeMillis}"/>
//                  <% pageContext.setAttribute("ctxDataElement", utb.getContext().getDataElement().toString());%>\r\n<c:out value="${ctxDataElement}" escapeXml="false"/>

        content = content.replaceAll("<%=\\s*(webAppPath)\\s*%>", "<c:out value=\"\\${webAppPath}\"/>");
        content = content.replaceAll("<%=\\s*(version)\\s*%>", "<c:out value=\"\\${version}\"/>");
        content = content.replaceAll("<%=\\s*(scriptPath)\\s*%>", "<c:out value=\"\\${scriptPath}\"/>");
        content = content.replaceAll("<%=\\s*(System\\.currentTimeMillis\\(\\))\\s*%>", "<c:out value=\"\\${timeMillis}\"/>");
//                content = content.replaceAll("<%=\\s*context\\.getDataElement\\(\\)\\.toString\\(\\)\\s*%>", "<% pageContext.setAttribute(\"ctxDataElement\", utb.getContext().getDataElement().toString());%>\r\n<c:out value=\"\\${ctxDataElement}\" escapeXml=\"false\"/>");
        return content;
    }
}
