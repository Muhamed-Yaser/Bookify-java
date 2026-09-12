<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Management System - Books</title>
    <%@ include file="header.jspf" %>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar Navigation -->
        <div class="col-md-2 sidebar p-0">
            <h4 class="p-3 text-white border-bottom border-secondary">Library System</h4>
            <a href="${pageContext.request.contextPath}/"><i class="bi bi-house-door me-2"></i> Dashboard</a>
            <a href="${pageContext.request.contextPath}/books" class="active"><i class="bi bi-book me-2"></i> Books</a>
            <a href="${pageContext.request.contextPath}/categories"><i class="bi bi-grid me-2"></i> Categories</a>
            <a href="${pageContext.request.contextPath}/authors"><i class="bi bi-people me-2"></i> Authors</a>
            <a href="${pageContext.request.contextPath}/book-authors"><i class="bi bi-link-45deg me-2"></i> Book Authors</a>
        </div>

        <!-- Main Content Area -->
        <div class="col-md-10 main-content">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Books</h2>
                <a href="${pageContext.request.contextPath}/books/add" class="btn btn-primary"><i class="bi bi-plus-lg"></i> Add Book</a>
            </div>

            <!-- Search by title or ISBN -->
            <div class="card card-custom p-3 mb-3">
                <form action="${pageContext.request.contextPath}/books" method="get" class="row g-2 align-items-center">
                    <div class="col-auto flex-grow-1">
                        <input type="text" name="search" class="form-control" placeholder="Search by title or ISBN..." value="${search}"/>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-outline-primary">Search</button>
                        <c:if test="${not empty search}">
                            <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-secondary">Clear</a>
                        </c:if>
                    </div>
                </form>
            </div>

            <div class="card card-custom p-3">
                <table class="table table-hover align-middle">
                    <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tempBook" items="${books}">
                            <tr>
                                <td>${tempBook.id}</td>
                                <td><strong><c:out value="${tempBook.title}"/></strong></td>
                                <td>
                                    <span class="badge bg-info text-dark">
                                        <c:out value="${tempBook.category != null ? tempBook.category.name : 'Uncategorized'}"/>
                                    </span>
                                </td>
                                <td>$${tempBook.price}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/books/${tempBook.id}" class="btn btn-sm btn-primary">Details</a>
                                    <a href="${pageContext.request.contextPath}/books/${tempBook.id}/edit" class="btn btn-sm btn-warning text-dark">Update</a>

                                    <form action="${pageContext.request.contextPath}/books/${tempBook.id}/delete" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this book?');">
                                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty books}">
                            <tr>
                                <td colspan="4" class="text-center text-muted py-4">No books found.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>

                <!-- Pagination -->
                <c:if test="${totalPages > 1}">
                    <nav>
                        <ul class="pagination justify-content-center mb-0">
                            <c:forEach begin="1" end="${totalPages}" var="p">
                                <li class="page-item ${p == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/books?page=${p}${not empty search ? '&search='.concat(search) : ''}">${p}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
