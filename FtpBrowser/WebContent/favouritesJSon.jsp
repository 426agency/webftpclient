<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
${callback}(<json:object>
  <json:array name="items" var="item" items="${favourites}">
    <json:object>
      <json:property name="folderpath" value="${item.folderPATH}"/>
      <json:property name="connectionname" value="${item.connectionNAME}"/>
      <json:property name="connectionid" value="${item.connectionID}"/>
    </json:object>
  </json:array>
</json:object>)