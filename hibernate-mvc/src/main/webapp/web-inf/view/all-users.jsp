<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE HTML>

<HTML>

<BODY>

<H2>
<c:forEach var="user" items = "${users}">

${user.id}
${user.name}
${user.email}

<br>

</c:forEach>

</H2>

</BODY>

</HTML>