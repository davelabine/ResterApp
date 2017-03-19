<#include "common/student-base.ftl">

<#macro page_body>
<div class="well">
<form class="form-inline" action="" method="get">
    <div class="form-group">
        <label for="lname">Search Last Name: </label>
        <input type="text" name="name">
        <button type="submit" class="btn btn-default">Search</button>
    </div>
    <a id="addStudentButton" type="button" class="btn btn-default pull-right" href="api/Student/">Add Student</a>
</form>
<#if studentList?has_content>
<table class="table table-striped table-hover">
    <thead>
      <tr>
          <th>Name</th>
          <th>ID</th>
          <th>URL</th>
      </tr>
    </thead>
    <#list studentList as student>
    <tbody>
      <tr>
          <td>${student.name}</td>
          <td>${student.id}</td>
          <td><a id="editStudent" href="students/${student.key}">edit</a></td>
      </tr>
      </#list>
    </tbody>
<#else>
  <div> No Students yet! </div>
</#if>
</table>
</div>
</#macro>

<@display_page/>