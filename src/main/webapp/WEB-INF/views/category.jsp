<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${categoryName}</title>
    <jsp:include page="dependencies.jsp"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/style/pagination.css"/>" rel="stylesheet">
    <link href="<c:url value="/style/common.css"/>" rel="stylesheet">
    <link href="<c:url value="/style/category.css"/>" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="files">
    <form method="POST" action="<c:url value="/categories/saveProducts"/>">
        <input id="categoryNameSave" name="categoryName" value="${categoryName}" hidden>
        <button type="submit" class="btn btn-primary">Экспорт продуктов</button>
    </form>
    <form method="POST" action="<c:url value="/categories/loadProducts"/>" enctype="multipart/form-data" class="file-import">
        <input id="categoryNameLoad" name="categoryName" value="${categoryName}" hidden>
        <label class="label">
            <i>&#128204</i>
            <input id="file" name="file" type="file" class="title" accept=".csv">
        </label>
        <button type="submit" class="btn btn-primary">Импорт продуктов</button>
    </form>
</div>
<div class="container-fluid">
    <div class="row">
        <c:forEach items="${categoryProducts}" var="product">
            <div class="col d-flex justify-content-center">
                <div class="card" style="width: 22rem; margin: 20px 0 20px 0; background-color: #dee2e6">
                    <a href="<c:url value="/products/${product.getId()}"/>"><img
                            src="<c:url value="/${product.getImagePath()}"/>" class="card-img-top"
                            style="height: 25rem;" alt="..."></a>
                    <div class="card-body" style="text-align: center">
                        <h2 class="card-title">${product.getName()}</h2>
                        <p class="card-text"><span style="color: green; font-size: 1.5rem;">Цена: <fmt:formatNumber
                                value="${product.getPrice()}"
                                type="currency"/></span><br>
                                ${product.getDescription()}</p>
                        <a href="<c:url value="/products/${product.getId()}"/>" class="btn btn-primary">Посмотреть</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<div class="pagination-management">
    <nav class="pagination-nav">
        <ul class="pagination">
            <li class="page-item"><a class="page-link" href="<c:url value="/categories/${categoryName}/prev"/>"><<</a></li>
            <li class="page-item"><a class="page-link" href="<c:url value="/categories/${categoryName}/0"/>">1</a></li>
            <li class="page-item"><a class="page-link" href="<c:url value="/categories/${categoryName}/1"/>">2</a></li>
            <li class="page-item"><a class="page-link" href="<c:url value="/categories/${categoryName}/2"/>">3</a></li>
            <li class="page-item"><a class="page-link" href="<c:url value="/categories/${categoryName}/next"/>">>></a></li>
        </ul>
    </nav>
    <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
            Размер
        </button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <c:forEach begin="1" end="9" var="pageSize">
                <a class="dropdown-item" href="<c:url value="/categories/${categoryName}/pageSize/${pageSize}"/>">${pageSize}</a>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
