<#macro common_page_head>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
    <link href="/service/css/students.css" rel="stylesheet">
    <title>${title!"ResterApp"}</title>
</head>
<body class="main-site">
</#macro>

<#macro page_head>
    <@common_page_head/>
</#macro>

<!-- should be overridden by pages that inherit these macros -->
<#macro page_body>
    <h1>Default Page Body</h1>
    <p>If you are seeing this, something went wrong...</p>
</#macro>

<#macro page_footer>
<p>
    <a href="${rootUrl}">Students Webapp</a><br>
    <a href="api/roster/">Rosters Web API</a><br>
    <a href="api/students/post">Students POST API</a><br>
    <a href="api/students/123456">Students GET API</a>
</p>

<script src="https://code.jquery.com/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
</body>
</html>
</#macro>

<#macro display_page>
    <@page_head/>
    <@page_body/>
    <@page_footer/>
</#macro>