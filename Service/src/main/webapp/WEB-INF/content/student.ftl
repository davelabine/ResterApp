<#include "common/student-base.ftl">

<#macro page_body>
<div class="well">
<#if student?has_content>

    <div>${student.name}</div>
    <div>${student.id}</div>
    <div><img src="${photoUrl}" class="img-responsive"></div>
    <div><button type="button" class="btn btn-primary" href="/edit">Edit</button></div>

<#else> <!-- no student hash found -->
  <div> Student not found! </div>
</#if>
        </div> <!-- class="well" -->
</#macro>
<@display_page/>