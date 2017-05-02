<#include "common/student-base.ftl">

<#macro page_body>
<div class="well" xmlns="http://www.w3.org/1999/html">
<#if student?has_content>

    <form action="${submitUrl}" method="post" enctype="multipart/form-data">
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
          <div><img src="${photoUrl}" class="img-thumbnail" style="width:25%" height="auto"></div>
          <input type="file" name="photo" accept="image/*"></input>
      </div>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
    <div>
        <form action="${deleteUrl}" method='post'>
            <button type="submit" class="btn btn-primary">Delete</button>
        </form>
    </div>


<#else> <!-- no student hash found -->
  <div> Student not found! </div>
</#if>
        </div> <!-- class="well" -->
</#macro>
<@display_page/>