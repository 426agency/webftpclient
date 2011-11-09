<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
${callback}(<json:object>
  <json:array name="items" var="item" items="${foldercontent}">
    <json:object>
      <json:property name="itemname" value="${item.name}"/>
      <json:property name="typename" value="${item.type}"/>
    </json:object>
  </json:array>
</json:object>)