<#include "common/student-base.ftl">

<#macro page_body>
<h2>Students List!</h2>

<div class="buttons">
  <a id="addStudentButton" type="button" class="btn btn-primary" href="api/Student/">Add Student</a>
</div>
<#if studentList??>
<table class="table table-striped table-bordered table-hover">
    <thead>
      <tr>
          <th>ID</th>
          <th>Name</th>
          <th>URL</th>
      </tr>
    </thead>
    <#list studentList as student>
    <tbody>
      <tr>
          <td>${student.id}</td>
          <td>${student.name}</td>
          <td>Something helpful</td>
      </tr>
      </#list>
    </tbody>
<#else>
  <div> No Students yet! </div>
</#if>
</table>
</#macro>

<@display_page/>