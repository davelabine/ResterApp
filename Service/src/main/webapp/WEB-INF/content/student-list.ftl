
<h2>Students List!</h2>

<div class="buttons">
  <a id="addArticleButton" type="button" class="btn btn-primary" href="new/">Add Article</a>
</div>

<table class="table table-striped table-bordered table-hover">
  <thead>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>URL</th>
    </tr>
  </thead>
  <tbody>
    <#list students as student>
    <tr>
      <td>${article.id}</td>
      <td>${article.name}</td>
      <td>${article.url}</td>
    </tr>
    </#list>
  </tbody>
</table>
