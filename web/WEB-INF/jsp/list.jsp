<%@ page import="ru.javawebinar.basejavaold.web.HtmlUtil" %>
<%@ page import="ru.javawebinar.basejavaold.storage.XmlFileStorage" %>
<%@ page import="ru.javawebinar.basejavaold.model.ContactType" %>
<%@ page import="ru.javawebinar.basejavaold.model.Resume" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ru.javawebinar.basejavaold.WebAppConfig" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
<section>
    <table>
        <tr>
            <td colspan="5" style="text-align: right"><a href="resume?action=create"><img src="img/add.png"> Добавить
                Резюме</a></td>
        </tr>
        <tr>
            <td>
                <table border="1" cellpadding="8" cellspacing="0">
                    <tr>
                        <th>Имя</th>
                        <th>Проживание</th>
                        <th>Email</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                    </tr>
                    <%
                        Collection<Resume> resumes = WebAppConfig.get().getStorage().getAllSorted();
                        request.setAttribute("resumeList", resumes);
                    %>
                    <c:forEach items="${resumeList}" var="resume">
                        <jsp:useBean id="resume" type="ru.javawebinar.basejavaold.model.Resume"/>
                        <tr>
                            <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                            <td>${resume.location}</td>
                            <td><%=HtmlUtil.getContact(resume, ContactType.MAIL)%></td>
                            <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
                            <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>
                        </tr>
                    </c:forEach>
                <%--    <%
                        for (Resume r: resumes) {
                        request.setAttribute("r", r);
                    %>
                    <tr>
                        <td><a href="resume?uuid=${r.uuid}&action=view">${r.fullName}</a></td>
                        <td>${resume.location}</td>
                        <td><%=HtmlUtil.getContact(r, ContactType.MAIL)%></td>
                        <td><a href="resume?uuid=${r.uuid}&action=delete"><img src="img/delete.png"></a></td>
                        <td><a href="resume?uuid=${r.uuid}&action=edit"><img src="img/pencil.png"></a></td>
                    </tr>
<%
                        }
                    %>--%>
                </table>
            </td>
                <td>
                </td>
            </tr>
    </table>
</section>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
