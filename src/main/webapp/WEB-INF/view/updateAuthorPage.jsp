<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Author</title>
    <%@ include file="header.jspf" %>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 sidebar p-0">
            <h4 class="p-3 text-white border-bottom border-secondary">Library System</h4>
                <a href="${pageContext.request.contextPath}/"><i class="bi bi-house-door me-2"></i> Dashboard</a>
            <a href="${pageContext.request.contextPath}/books"><i class="bi bi-book me-2"></i> Books</a>
            <a href="${pageContext.request.contextPath}/categories"><i class="bi bi-grid me-2"></i> Categories</a>
            <a href="${pageContext.request.contextPath}/authors" class="active"><i class="bi bi-people me-2"></i> Authors</a>
        </div>

        <div class="col-md-10 main-content">
            <h2 class="mb-4">Update Author</h2>

            <div class="card card-custom p-4" style="max-width: 500px;">
                <form:form action="${pageContext.request.contextPath}/authors/${author.id}/update" modelAttribute="author" method="post">
                    <form:hidden path="id"/>

                    <div class="mb-3">
                        <label class="form-label">Author Name *</label>
                        <form:input path="name" cssClass="form-control"/>
                        <form:errors path="name" cssClass="text-danger small"/>
                    </div>

                    <div class="d-flex justify-content-end gap-2">
                        <a href="${pageContext.request.contextPath}/authors" class="btn btn-secondary">Cancel</a>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
