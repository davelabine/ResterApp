<#include "common/student-base.ftl">

<#macro page_body>
<h1>Oops... something went wrong!</h1><br>
    <#if body?has_content>
    <h3>${body}</h3>
    </#if>
    <#if status?has_content>
    <P><B>HTTP Status: ${status}</B><BR>
    </#if>
    <#if message?has_content>
    ${message}
    </#if>
</#macro>

<@display_page/>