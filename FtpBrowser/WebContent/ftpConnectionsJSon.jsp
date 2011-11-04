<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
${callback}(<json:object>
  <json:array name="items" var="item" items="${ftpConnections}">
    <json:object>
      <json:property name="username" value="${item.username}"/>
      <json:property name="password" value="${item.password}"/>
      <json:property name="host" value="${item.host}"/>
      <json:property name="port" value="${item.port}"/>
    </json:object>
  </json:array>
</json:object>)