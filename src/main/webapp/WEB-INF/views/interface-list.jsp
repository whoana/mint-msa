<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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