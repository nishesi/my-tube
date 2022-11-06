<%@tag description="output error description if exists" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="alert" type="ru.itis.MyTube.auxiliary.Alert" %>

<div class="toast show align-items-center border-0 mb-1" role="alert" aria-live="assertive" aria-atomic="true">
    <div class="d-flex alert ${alert.type.alertType} p-0">
        <div class="toast-body">
            ${alert.message}
        </div>
        <button type="button" class="btn-close btn-close me-2 m-auto" data-bs-dismiss="toast"
                aria-label="Close"></button>
    </div>
</div>
