<#include "common/student-base.ftl">

<#macro page_body>
    <#list data as student>
<div>
  <div>${student.name}</div>
  <div>${student.id}</div>
</div>
    </#list>
</#macro>

<@display_page/>