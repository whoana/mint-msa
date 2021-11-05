<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="pep.per.mint.database.service.co.CommonService"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	ApplicationContext ac = RequestContextUtils.findWebApplicationContext(request);
	CommonService cs = (CommonService) ac.getBean("commonService");
	String interfacdId = cs.getInterfaceID("dfdf");
%>
<html>
    <head>
        <title>View Interfaces</title>
        <%-- 
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
         --%>
    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>interfaceId</th>
                    <th>interfaceIdNm</th>
                    <th>integrationId</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${interfaces}" var="interfaze">
                    <tr>
                        <td>${interfaze.interfaceId}</td>
                        <td>${interfaze.interfaceNm}</td>
                        <td>${interfaze.integrationId}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>