<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Details</title>
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
        </div>

        <div class="col-md-10 main-content">
            <h2 class="mb-4">Book Details</h2>

            <div class="card card-custom p-4">
                <div class="row">
                    <div class="col-md-8">
                        <h3><c:out value="${book.title}"/></h3>
                        <p class="text-muted">Category: <strong><c:out value="${book.category != null ? book.category.name : 'N/A'}"/></strong></p>

                        <hr>

                        <dl class="row">
                            <dt class="col-sm-3">ISBN:</dt>
                            <dd class="col-sm-9"><c:out value="${book.bookDetails != null ? book.bookDetails.isbn : 'N/A'}"/></dd>

                            <dt class="col-sm-3">Publisher:</dt>
                            <dd class="col-sm-9"><c:out value="${book.bookDetails != null ? book.bookDetails.publisher : 'N/A'}"/></dd>

                            <dt class="col-sm-3">Publication Date:</dt>
                            <dd class="col-sm-9">
                                <c:choose>
                                    <c:when test="${book.bookDetails != null && book.bookDetails.publicationDate != null}">
                                        <fmt:formatDate value="${book.bookDetails.publicationDate}" pattern="yyyy-MM-dd"/>
                                    </c:when>
                                    <c:otherwise>N/A</c:otherwise>
                                </c:choose>
                            </dd>

                            <dt class="col-sm-3">Pages:</dt>
                            <dd class="col-sm-9"><c:out value="${book.bookDetails != null ? book.bookDetails.numberOfPages : 'N/A'}"/></dd>

                            <dt class="col-sm-3">Language:</dt>
                            <dd class="col-sm-9"><c:out value="${book.bookDetails != null ? book.bookDetails.language : 'N/A'}"/></dd>

                            <dt class="col-sm-3">Author(s):</dt>
                            <dd class="col-sm-9">
                                <c:forEach var="author" items="${book.authors}">
                                    <span class="badge bg-secondary me-1"><c:out value="${author.name}"/></span>
                                </c:forEach>
                            </dd>
                            <dt class="col-sm-3">Price:</dt>
                            <dd class="col-sm-9">$${book.price != null ? book.price : 'N/A'}</dd>

                        </dl>
                    </div>
                </div>
                <div class="mt-3">
                    <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Back to Books</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>