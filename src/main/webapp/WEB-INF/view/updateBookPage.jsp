<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Book</title>
    <%@ include file="header.jspf" %>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 sidebar p-0">
            <h4 class="p-3 text-white border-bottom border-secondary">Library System</h4>
                <a href="${pageContext.request.contextPath}/"><i class="bi bi-house-door me-2"></i> Dashboard</a>
            <a href="${pageContext.request.contextPath}/books" class="active"><i class="bi bi-book me-2"></i> Books</a>
            <a href="${pageContext.request.contextPath}/categories"><i class="bi bi-grid me-2"></i> Categories</a>
            <a href="${pageContext.request.contextPath}/authors"><i class="bi bi-people me-2"></i> Authors</a>
            <a href="${pageContext.request.contextPath}/book-authors"><i class="bi bi-link-45deg me-2"></i> Book Authors</a>
        </div>

        <div class="col-md-10 main-content">
            <h2 class="mb-4">Update Book</h2>

            <div class="card card-custom p-4">
                <form:form action="${pageContext.request.contextPath}/books/${book.id}/update" modelAttribute="book" method="post">

                    <form:hidden path="id"/>
                    <form:hidden path="bookDetails.id"/>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Title *</label>
                            <form:input path="title" cssClass="form-control" placeholder="Enter book title"/>
                            <form:errors path="title" cssClass="text-danger small"/>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Category *</label>
                            <form:select path="category.id" cssClass="form-select">
                                <form:option value="" label="-- Select Category --"/>
                                <form:options items="${categories}" itemValue="id" itemLabel="name"/>
                            </form:select>
                            <form:errors path="category" cssClass="text-danger small"/>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label class="form-label">ISBN *</label>
                            <form:input path="bookDetails.isbn" cssClass="form-control" placeholder="Enter ISBN"/>
                            <form:errors path="bookDetails.isbn" cssClass="text-danger small"/>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Number of Pages *</label>
                            <form:input path="bookDetails.numberOfPages" type="number" cssClass="form-control"/>
                            <form:errors path="bookDetails.numberOfPages" cssClass="text-danger small"/>
                        </div>

                        <div class="col-md-6 mb-3">
                            <label class="form-label">Price ($) *</label>
                            <form:input path="price" type="number" step="0.01" cssClass="form-control" placeholder="0.00"/>
                            <form:errors path="price" cssClass="text-danger small"/>
                        </div>

                        <div class="col-md-4">
                            <label class="form-label">Publication Date</label>
                            <form:input path="bookDetails.publicationDate" type="date" cssClass="form-control"/>
                            <form:errors path="bookDetails.publicationDate" cssClass="text-danger small"/>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Publisher *</label>
                            <form:input path="bookDetails.publisher" cssClass="form-control" placeholder="Enter Publisher"/>
                            <form:errors path="bookDetails.publisher" cssClass="text-danger small"/>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Language</label>
                            <form:input path="bookDetails.language" cssClass="form-control" placeholder="e.g. English"/>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Select Author(s) *</label>
                        <select name="authorIds" multiple="multiple" class="form-select" size="4">
                            <c:forEach items="${authors}" var="a">
                                <option value="${a.id}"
                                    <c:if test="${selectedAuthorIds != null && selectedAuthorIds.contains(a.id)}">selected</c:if>>
                                    ${a.name}
                                </option>
                            </c:forEach>
                        </select>
                        <div class="form-text">Hold Ctrl (or Cmd on Mac) to select multiple authors.</div>
                        <form:errors path="authors" cssClass="text-danger small"/>
                    </div>

                    <div class="d-flex justify-content-end gap-2">
                        <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Cancel</a>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </div>

                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
