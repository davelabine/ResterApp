<#include "common/student-base.ftl">

<#macro page_body>
<div class="well">
<#if student?has_content>

    <div>${student.name}</div>
    <div>${student.id}</div>
    <div><img src="${photoUrl}" class="img-thumbnail" style="width:25%" height="auto"></div>
    <div><a type="button" class="btn btn-primary" href="${rootUrl}id/${student.skey}/edit">Edit</a></div>

<#else> <!-- no student hash found -->
  <div> Student not found! </div>
</#if>
        </div> <!-- class="well" -->
</#macro>
<@display_page/>