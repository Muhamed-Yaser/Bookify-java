<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Authors</title>
    <%@ include file="header.jspf" %>
    <style>
        .author-box {
            border: 1px solid #ccc;
            border-radius: 6px;
            min-height: 220px;
            max-height: 220px;
            overflow-y: auto;
            padding: 10px;
        }
        .author-item {
            display: flex;
               align-items: center;
               gap: 8px;
               padding: 6px 4px;
        }
        .author-item input[type="checkbox"] {
            width: 16px;
            height: 16px;
            margin: 0;
            flex-shrink: 0;
        }

        .middle-buttons button {
            width: 45px;
            margin-bottom: 8px;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 sidebar p-0">
            <h4 class="p-3 text-white border-bottom border-secondary">Library System</h4>
            <a href="${pageContext.request.contextPath}/"><i class="bi bi-house-door me-2"></i> Dashboard</a>
            <a href="${pageContext.request.contextPath}/books"><i class="bi bi-book me-2"></i> Books</a>
            <a href="${pageContext.request.contextPath}/categories"><i class="bi bi-grid me-2"></i> Categories</a>
            <a href="${pageContext.request.contextPath}/authors"><i class="bi bi-people me-2"></i> Authors</a>
            <a href="${pageContext.request.contextPath}/book-authors" class="active"><i class="bi bi-link-45deg me-2"></i> Book Authors</a>
        </div>

        <div class="col-md-10 main-content">
            <h2 class="mb-4">Book Authors</h2>


           <c:if test="${not empty successMessage}">
               <div id="successMessageBox" class="alert alert-success">${successMessage}</div>
           </c:if>

            <div class="card card-custom p-4">

                <!-- Step 1: choose a book from the dropdown list -->
                <div class="mb-4">
                    <label class="form-label">Select Book *</label>
                    <select id="bookSelect" class="form-select"
                            onchange="location.href = '${pageContext.request.contextPath}/book-authors?bookId=' + this.value;">
                        <c:forEach items="${allBooks}" var="b">
                            <option value="${b.id}"
                                <c:if test="${selectedBook != null && selectedBook.id == b.id}">selected</c:if>>
                                ${b.title}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <c:if test="${selectedBook != null}">

                    <label class="form-label">Select Authors *</label>

                    <form id="bookAuthorsForm" action="${pageContext.request.contextPath}/book-authors/save" method="post">
                        <input type="hidden" name="bookId" value="${selectedBook.id}"/>

                        <div class="row align-items-center">

                            <!-- Left box: authors NOT yet on this book -->
                            <div class="col-md-5">
                                <p class="mb-1">Available Authors</p>
                                <div id="availableList" class="author-box">
                                    <c:forEach items="${availableAuthors}" var="a">
                                        <div class="author-item" data-id="${a.id}">
                                            <input type="checkbox">
                                            <span>${a.name}</span>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>

                            <!-- Middle buttons that move authors between the two boxes -->
                            <div class="col-md-2 text-center middle-buttons">
                                <div>
                                    <button type="button" class="btn btn-outline-primary"
                                            onclick="moveChecked('availableList', 'selectedList')">&gt;</button>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-outline-primary"
                                            onclick="moveAll('availableList', 'selectedList')">&gt;&gt;</button>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-outline-secondary"
                                            onclick="moveChecked('selectedList', 'availableList')">&lt;</button>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-outline-secondary"
                                            onclick="moveAll('selectedList', 'availableList')">&lt;&lt;</button>
                                </div>
                            </div>

                            <!-- Right box: authors already on this book -->
                            <div class="col-md-5">
                                <p class="mb-1">Selected Authors</p>
                                <div id="selectedList" class="author-box">
                                    <c:forEach items="${selectedAuthors}" var="a">
                                       <div class="author-item" data-id="${a.id}">
                                           <input type="checkbox">
                                           <span>${a.name}</span>
                                       </div>
                                    </c:forEach>
                                </div>
                            </div>

                        </div>

                        <div class="d-flex justify-content-end gap-2 mt-4">
                            <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Cancel</a>
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>

                    </form>

                </c:if>

                <c:if test="${empty allBooks}">
                    <p class="text-muted">No books found. Please add a book first.</p>
                </c:if>

            </div>
        </div>
    </div>
</div>

<script>
    // Moves only the checked authors from one box to the other box.
    function moveChecked(fromBoxId, toBoxId) {
        var fromBox = document.getElementById(fromBoxId);
        var toBox = document.getElementById(toBoxId);

        // querySelectorAll gives us a fixed list, so it is safe to move
        // items out of fromBox while we are still looping over this list.
        var items = fromBox.querySelectorAll('.author-item');

        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            var checkbox = item.querySelector('input[type="checkbox"]');

            if (checkbox.checked) {
                checkbox.checked = false;
                toBox.appendChild(item);
            }
        }
    }

    // Moves ALL authors from one box to the other box.
    function moveAll(fromBoxId, toBoxId) {
        var fromBox = document.getElementById(fromBoxId);
        var toBox = document.getElementById(toBoxId);

        while (fromBox.firstChild) {
            toBox.appendChild(fromBox.firstChild);
        }
    }

    // Right before the form is submitted, we look at whatever authors
    // are currently sitting inside the "Selected Authors" box, and add
    // one hidden input for each one, so the server receives their ids
    // as the "authorIds" parameter.
    var bookAuthorsForm = document.getElementById('bookAuthorsForm');

    if (bookAuthorsForm != null) {
        bookAuthorsForm.addEventListener('submit', function () {
            var selectedBox = document.getElementById('selectedList');
            var items = selectedBox.querySelectorAll('.author-item');

            for (var i = 0; i < items.length; i++) {
                var authorId = items[i].getAttribute('data-id');

                var hiddenInput = document.createElement('input');
                hiddenInput.type = 'hidden';
                hiddenInput.name = 'authorIds';
                hiddenInput.value = authorId;

                bookAuthorsForm.appendChild(hiddenInput);
            }
        });
    }

    // Hide the success message box automatically after 3 seconds.
    var successMessageBox = document.getElementById('successMessageBox');

    if (successMessageBox != null) {
        setTimeout(function () {
            successMessageBox.style.display = 'none';
        }, 3000);
    }
</script>

</body>
</html>
