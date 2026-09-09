<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Categories</title>
    <%@ include file="header.jspf" %>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 sidebar p-0">
            <h4 class="p-3 text-white border-bottom border-secondary">Library System</h4>
            <a href="${pageContext.request.contextPath}/books"><i class="bi bi-book me-2"></i> Books</a>
            <a href="${pageContext.request.contextPath}/categories" class="active"><i class="bi bi-grid me-2"></i> Categories</a>
            <a href="${pageContext.request.contextPath}/authors"><i class="bi bi-people me-2"></i> Authors</a>
        </div>

        <div class="col-md-10 main-content">
            <h2>Categories</h2>

            <!-- نموذج إضافة تصنيف سريع -->
            <div class="card card-custom p-3 my-3">
                <form:form action="${pageContext.request.contextPath}/categories/add" modelAttribute="category" method="post" class="row g-3 align-items-center">
                    <div class="col-auto">
                        <label class="visually-hidden">Category Name</label>
                        <form:input path="name" cssClass="form-control" placeholder="New Category Name"/>
                        <form:errors path="name" cssClass="text-danger small"/>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary">+ Add Category</button>
                    </div>
                </form:form>
            </div>

            <!-- جدول التصنيفات -->
            <div class="card card-custom p-3">
                <table class="table table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cat" items="${categories}">
                            <tr>
                                <td>${cat.id}</td>
                                <td><c:out value="${cat.name}"/></td>
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
