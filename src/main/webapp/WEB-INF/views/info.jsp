<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div style="margin-bottom: 20px;">
  <div class="row">
    <div class="col-sm-3" style="text-align: left; font-size: 1.5rem;">
      Имя: ${name}
    </div>
    <div class="col-sm-3" style="text-align: left; font-size: 1.5rem;">
      Фамилия: ${lastName}
    </div>
    <div class="balance col-sm-6" style="text-align: left; font-size: 1.5rem;">
      Баланс: <fmt:formatNumber value="${balance}"
                                type="currency"/>
    </div>
  </div>
</div>
