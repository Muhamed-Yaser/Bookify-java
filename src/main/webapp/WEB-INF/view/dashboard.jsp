<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Library System</title>
    <%@ include file="header.jspf" %>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-2 sidebar p-0">
            <h4 class="p-3 text-white border-bottom border-secondary">Library System</h4>
            <!-- Notice Dashboard is now "active" -->
            <a href="${pageContext.request.contextPath}/" class="active"><i class="bi bi-house-door me-2"></i> Dashboard</a>
            <a href="${pageContext.request.contextPath}/books"><i class="bi bi-book me-2"></i> Books</a>
            <a href="${pageContext.request.contextPath}/categories"><i class="bi bi-grid me-2"></i> Categories</a>
            <a href="${pageContext.request.contextPath}/authors"><i class="bi bi-people me-2"></i> Authors</a>
        </div>

        <!-- Main Content -->
        <div class="col-md-10 main-content">
            <h2 class="mb-4 mt-2">Dashboard</h2>

            <!-- Welcome Banner -->
            <div class="card border-0 shadow-sm mb-4 bg-light">
                <div class="card-body p-4 text-center">
                    <h3 class="fw-bold text-dark mb-3">Welcome to the Library Management System!</h3>
                    <p class="text-muted mb-4">Manage your books, authors, and categories efficiently from one central place.</p>
                    <a href="${pageContext.request.contextPath}/books/add" class="btn btn-primary px-4 py-2 shadow-sm">
                        <i class="bi bi-plus-circle me-2"></i>Add New Book
                    </a>
                </div>
            </div>

            <!-- Statistics Cards Row -->
            <div class="row mt-4">

                <!-- Books Stat Card -->
                <div class="col-md-4 mb-3">
                    <div class="card text-white bg-primary shadow-sm h-100 border-0">
                        <div class="card-body d-flex flex-column justify-content-center align-items-center p-4">
                            <i class="bi bi-book fs-1 mb-2"></i>
                            <h1 class="display-4 fw-bold">${totalBooks != null ? totalBooks : 0}</h1>
                            <span class="fs-5">Total Books</span>
                        </div>
                        <div class="card-footer text-center bg-transparent border-top border-light border-opacity-25">
                            <a href="${pageContext.request.contextPath}/books" class="text-white text-decoration-none d-block">
                                View Details <i class="bi bi-arrow-right-circle ms-1"></i>
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Categories Stat Card -->
                <div class="col-md-4 mb-3">
                    <div class="card text-white bg-success shadow-sm h-100 border-0">
                        <div class="card-body d-flex flex-column justify-content-center align-items-center p-4">
                            <i class="bi bi-grid fs-1 mb-2"></i>
                            <h1 class="display-4 fw-bold">${totalCategories != null ? totalCategories : 0}</h1>
                            <span class="fs-5">Total Categories</span>
                        </div>
                        <div class="card-footer text-center bg-transparent border-top border-light border-opacity-25">
                            <a href="${pageContext.request.contextPath}/categories" class="text-white text-decoration-none d-block">
                                View Details <i class="bi bi-arrow-right-circle ms-1"></i>
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Authors Stat Card -->
                <div class="col-md-4 mb-3">
                    <div class="card text-white bg-warning shadow-sm h-100 border-0">
                        <div class="card-body d-flex flex-column justify-content-center align-items-center p-4">
                            <i class="bi bi-people fs-1 mb-2 text-dark"></i>
                            <h1 class="display-4 fw-bold text-dark">${totalAuthors != null ? totalAuthors : 0}</h1>
                            <span class="fs-5 text-dark">Total Authors</span>
                        </div>
                        <div class="card-footer text-center bg-transparent border-top border-dark border-opacity-25">
                            <a href="${pageContext.request.contextPath}/authors" class="text-dark text-decoration-none d-block">
                                View Details <i class="bi bi-arrow-right-circle ms-1"></i>
                            </a>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>