<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.itis.MyTube.auxiliary.constants.UrlPatterns" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <form method="post" action="${pageContext.servletContext.contextPath}${UrlPatterns.VIDEO_UPLOAD_PAGE}" enctype="multipart/form-data">
        <label>Name
            <input type="text" name="name" placeholder="name">
        </label>
        <input type="file" name="icon" placeholder="icon">
        <input type="file" name="video" placeholder="video">
        <label>Information
            <input type="text" name="info" placeholder="info">
        </label>

        <input type="submit" placeholder="upload" value="upload">
    </form>
</body>
</html>
