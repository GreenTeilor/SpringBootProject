<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${categoryName}</title>
    <jsp:include page="dependencies.jsp"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/style/common.css"/>" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="row">
    <c:forEach items="${categoryProducts}" var="product">
        <div class="col">
            <div class="card mx-auto" style="width: 22rem; margin: 20px; background-color: #dee2e6">
                <a href="<c:url value="/products/${product.getId()}"/>"><img src="<c:url value="/${product.getImagePath()}"/>" class="card-img-top" style="height: 25rem;" alt="..."></a>
                <div class="card-body" style="text-align: center">
                    <h2 class="card-title">${product.getName()}</h2>
                    <p class="card-text">Цена: <fmt:formatNumber value="${product.getPrice()}"
                                                                 type="currency"/><br>
                        ${product.getDescription()}</p>
                    <a href="<c:url value="/products/${product.getId()}"/>" class="btn btn-primary">Посмотреть</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
