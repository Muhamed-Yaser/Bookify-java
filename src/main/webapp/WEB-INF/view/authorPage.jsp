<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Authors</title>
    <%@ include file="header.jspf" %>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 sidebar p-0">
            <h4 class="p-3 text-white border-bottom border-secondary">Library System</h4>
            <a href="${pageContext.request.contextPath}/books"><i class="bi bi-book me-2"></i> Books</a>
            <a href="${pageContext.request.contextPath}/categories"><i class="bi bi-grid me-2"></i> Categories</a>
            <a href="${pageContext.request.contextPath}/authors" class="active"><i class="bi bi-people me-2"></i> Authors</a>
        </div>

        <div class="col-md-10 main-content">
            <h2>Authors</h2>

            <div class="card card-custom p-3 my-3">
                <form:form action="${pageContext.request.contextPath}/authors/add" modelAttribute="author" method="post" class="row g-3 align-items-center">
                    <div class="col-auto">
                        <form:input path="name" cssClass="form-control" placeholder="New Author Name"/>
                        <form:errors path="name" cssClass="text-danger small"/>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary">+ Add Author</button>
                    </div>
                </form:form>
            </div>

            <div class="card card-custom p-3">
                <table class="table table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="aut" items="${authors}">
                            <tr>
                                <td>${aut.id}</td>
                                <td><c:out value="${aut.name}"/></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/authors/${aut.id}/delete" method="post" style="display:inline;" onsubmit="return confirm('Delete this author?');">
                                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>