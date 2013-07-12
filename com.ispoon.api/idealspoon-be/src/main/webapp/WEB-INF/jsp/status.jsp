<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>${info.serverName} Health</title>
</head>
<body>
  <h1>${info.serverName}</h1>
  Server Okay: <b>${info.serverOkay}</b><br/>
  Server Time: <b>${info.serverDateTime}</b><br/>

  <h2>Build Version</h2>
  <pre>${info.buildVersion}</pre>

  <h2>Services</h2>
  <table border="1" padding="2">
    <c:forEach var="si" items="${info.services}">
      <tr>
        <td>${si.name}</td>
        <td>${si.uri}</td>
        <c:choose>
          <c:when test="${si.okay}">
            <td bgcolor="#00ff00">OKAY</td>
          </c:when>
          <c:otherwise>
            <td bgcolor="#ff0000">FAIL</td>
          </c:otherwise>
        </c:choose>
      </tr>
    </c:forEach>
  </table>
</body>
</html>