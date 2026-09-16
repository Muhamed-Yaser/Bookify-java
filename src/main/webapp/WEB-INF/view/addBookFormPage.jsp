<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Book</title>
    <%@ include file="header.jspf" %>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 sidebar p-0">
            <h4 class="p-3 text-white border-bottom border-secondary">Library System</h4>
            <a href="${pageContext.request.contextPath}/books" class="active"><i class="bi bi-book me-2"></i> Books</a>
            <a href="${pageContext.request.contextPath}/categories"><i class="bi bi-grid me-2"></i> Categories</a>
            <a href="${pageContext.request.contextPath}/authors"><i class="bi bi-people me-2"></i> Authors</a>
        </div>

        <div class="col-md-10 main-content">
            <h2 class="mb-4">Add Book</h2>

            <div class="card card-custom p-4">
                <form:form action="${pageContext.request.contextPath}/books/add" modelAttribute="book" method="post">

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Title *</label>
                            <form:input path="title" cssClass="form-control" placeholder="Enter book title"/>
                            <form:errors path="title" cssClass="text-danger small"/>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Category</label>
                            <form:select path="category.id" cssClass="form-select">
                                <form:option value="" label="-- Select Category --"/>
                                <form:options items="${categories}" itemValue="id" itemLabel="name"/>
                            </form:select>
                                    <form:errors path="category" cssClass="text-danger small"/>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">ISBN *</label>
                            <form:input path="bookDetails.isbn" cssClass="form-control" placeholder="Enter ISBN"/>
                            <form:errors path="bookDetails.isbn" cssClass="text-danger small"/>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Number of Pages *</label>
                            <form:input path="bookDetails.numberOfPages" type="number" cssClass="form-control"/>
                            <form:errors path="bookDetails.numberOfPages" cssClass="text-danger small"/>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Publisher</label>
                            <form:input path="bookDetails.publisher" cssClass="form-control" placeholder="Enter Publisher"/>
                                    <form:errors path="bookDetails.publisher" cssClass="text-danger small"/>

                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Language</label>
                            <form:input path="bookDetails.language" cssClass="form-control" placeholder="e.g. English"/>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Select Authors</label>
                        <select name="authorIds" multiple="true" class="form-select" size="4">
                            <c:forEach items="${authors}" var="auth">
                                <option value="${auth.id}">${auth.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="d-flex justify-content-end gap-2">
                        <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Cancel</a>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>

                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>