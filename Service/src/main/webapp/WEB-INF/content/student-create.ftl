<#include "common/student-base.ftl">

<#macro page_body>
<div class="well">
    <form action="${submitUrl}" method="post" enctype="multipart/form-data">
      <div class="form-group">
          <label for="name">Name: </label>
          <input type="text" name="name">
      </div>
      <div class="form-group">
          <label for="id">Id: </label>
          <input type="text" name="id">
      </div>
      <div class="form-group">
          <label for="photo">Photo: </label>
          <input type="file" name="photo" accept="image/*"></input>
      </div>
      <button type="submit" class="btn btn-primary">Create</button>
    </form>
</div> <!-- class="well" -->
</#macro>
<@display_page/>