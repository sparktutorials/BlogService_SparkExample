<html>
<head>
</head>
<body>
<h1>The marvellous blog of mine</h1>

<#list posts as post>
    <div class="post" style="border: 1px black solid; margin:5px; padding:10px;background-color:#eef">
        <h2>${post.title}</h2>
        <p>${post.content}</p>
        <br/>

        <p>
            <b>Categories:</b>
            <#list post.categories as category>
                <span style="padding:5px;">${category}</span>
            </#list>
        </p>
        <i>Published on the ${post.publishing_date}</i>
    </div>
</#list>

</body>
</html>