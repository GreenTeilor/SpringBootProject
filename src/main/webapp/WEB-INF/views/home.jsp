<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
    <jsp:include page="dependencies.jsp"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/style/pagination.css"/>" rel="stylesheet">
    <link href="<c:url value="/style/common.css"/>" rel="stylesheet">
    <link href="<c:url value="/style/home.css"/>" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp"/>
<c:if test="${sessionScope.user != null}">
    <jsp:include page="info.jsp"/>
</c:if>
<div class="files">
    <form method="POST" action="<c:url value="/home/saveCategories"/>">
        <button type="submit" class="btn btn-primary">Экспорт категорий</button>
    </form>
    <form method="POST" action="<c:url value="/home/loadCategories"/>" enctype="multipart/form-data"
          class="file-import">
        <label class="label">
            <i>&#128204</i>
            <input id="file" name="file" type="file" class="title" accept=".csv">
        </label>
        <button type="submit" class="btn btn-primary">Импорт категорий</button>
    </form>
</div>
<div class="container-fluid">
    <div class="row">
        <c:forEach items="${categories}" var="item">
            <div class="col d-flex justify-content-center">
                <div class="card"
                     style="width: 22rem; margin: 20px 0 20px 0; background-color: #dee2e6 !important; border-radius: 40px;">
                    <a href="<c:url value="/categories/${item.getName()}"/>"><img src="<c:url value="/${item.getImagePath()}"/>"
                                                                class="card-img-top"
                                                                style="height: 25rem; border-radius: 40px 40px 0 0;"
                                                                alt="..."></a>
                    <div class="card-body" style="text-align: center">
                        <h2 class="card-title">${item.getName()}</h2>
                        <a href="<c:url value="/categories/${item.getName()}"/>" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<div class="pagination-management">
    <nav class="pagination-nav">
        <ul class="pagination">
            <li class="page-item"><a class="page-link" href="<c:url value="/home/prev"/>"><<</a></li>
            <li class="page-item"><a class="page-link" href="<c:url value="/home/0"/>">1</a></li>
            <li class="page-item"><a class="page-link" href="<c:url value="/home/1"/>">2</a></li>
            <li class="page-item"><a class="page-link" href="<c:url value="/home/2"/>">3</a></li>
            <li class="page-item"><a class="page-link" href="<c:url value="/home/next"/>">>></a></li>
        </ul>
    </nav>
    <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
            Размер
        </button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <c:forEach begin="1" end="9" var="pageSize">
                <a class="dropdown-item" href="<c:url value="/home/pageSize/${pageSize}"/>">${pageSize}</a>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>
