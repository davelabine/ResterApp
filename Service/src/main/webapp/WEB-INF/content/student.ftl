<#include "common/student-base.ftl">

<#macro page_body>
<div class="well">
<#if student?has_content>
  <div>${student.id}</div>
  <div>${student.name}</div>

  <form action="" method="post" enctype="multipart/form-data">
      <div class="form-group">
          <label for="name">Name: </label>
          <input type="text" name="name" value="${student.name}">
      </div>
      <div class="form-group">
          <label for="id">Id: </label>
          <input type="text" name="id" value="${student.id}">
      </div>
      <div class="form-group">
          <label for="photo">Photo: </label>
          <input type="file" name="photo" accept="image/*"></input>
      </div>
      <button type="submit" class="btn btn-primary">Save</button>
  </form>
<#else>
<div> Student not found! </div>
</#if>
        </div> <!-- class="well" -->
</#macro>
<@display_page/>