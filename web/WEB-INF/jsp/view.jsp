<%@ page import="ru.javawebinar.basejavaold.model.*" %>
<%@ page import="ru.javawebinar.basejavaold.web.HtmlUtil" %>
<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <style type="text/css">
        th, td {
            padding: 4px 20px 4px 0;
            vertical-align: top;
        }

        tr {
            border-bottom: 1px solid #DDDDDD;
        }

        table {
            margin-bottom: 1.4em;
        }

        table {
            border-collapse: collapse;
            border-spacing: 0;
        }
    </style>
    <jsp:useBean id="resume" type="ru.javawebinar.basejavaold.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}</h2>
    <c:if test="${not empty resume.homePage}">
        Домашняя страница: ${resume.homePage}<br>
    </c:if>
    <c:if test="${not empty resume.location}">
        Проживание: ${resume.location}
    </c:if>
    <p>
        <c:forEach var="type" items="${resume.contacts.keySet()}">
            <jsp:useBean id="type" type="ru.javawebinar.basejavaold.model.ContactType"/>
                <%=HtmlUtil.getContact(resume, type)%><br>
        </c:forEach>
    <p>
        <table>
            <c:forEach var="entry" items="${resume.sections.entrySet()}">
                <jsp:useBean id="entry" type="java.util.Map.Entry"/>
                    <%
                SectionType sectionType = ((SectionType) entry.getKey());
                Section section = ((Section) entry.getValue());
            %>

            <tr>
                <td><h3><a name="<%=sectionType.name()%>"><%=sectionType.getTitle()%>
                </a></h3></td>
                <%
                    switch (sectionType) {
                        case OBJECTIVE:
                %>
                <td><h4><%=((TextSection) entry.getValue()).getValue()%>
                </h4></td>
            </tr>
                    <%
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
            %>
            <td>
                <ul>
                    <c:forEach var="item" items="<%=((MultiTextSection) entry.getValue()).getItems()%>">
                        <li>${item}</li>
                    </c:forEach>
                </ul>
            </td>
            </tr>
                    <% break;
                case EXPERIENCE:
                case EDUCATION:
                    %>
                </tr>
                <c:forEach var="org" items="<%=((OrganizationSection) entry.getValue()).getValues()%>">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${empty org.link.url}">
                                    ${org.link.name}
                                </c:when>
                                <c:otherwise>
                                    <a href="${org.link.url}">${org.link.name}</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <c:forEach var="period" items="${org.periods}">
                        <jsp:useBean id="period" type="ru.javawebinar.basejavaold.model.Organization.Period"/>
                        <tr>
                            <td><%=HtmlUtil.format(period.getStartDate())%> - <%=HtmlUtil.format(period.getEndDate())%></td>
                            <td><b>${period.position}</b><br>${period.content}</td>
                        </tr>
                    </c:forEach>
                </c:forEach>
                <%
                    }
                %>
            </c:forEach>
</table>
    <button onclick="window.history.back()">ОК</button>
</section>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>