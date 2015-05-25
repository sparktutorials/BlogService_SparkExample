<html>
<head>
    <style>
        * { font-weight: normal; font-family: arial; box-sizing: border-box; }
        body { margin: 20px; }
        ul { margin-top: 0; padding: 0; }
        .post { padding: 15px; border: 1px solid #ccc; border-radius: 5px; margin: 15px 0; background: #FCFCFC; }
        .post h2 { margin-top: 0; padding-bottom: 15px; border-bottom: 1px solid #ddd; }
        .post h3 { font-size: 16px; margin-bottom: 5px; }
        .categories li { display: inline-block; }
        .categories li:after { content: ","; }
        .categories li:last-child:after { content: ""; }
    </style>
</head>
<body>
<h1>My marvellous blog</h1>

<#list posts as post>
    <div class="post">
        <h2>${post.title}</h2>
        <p>${post.content}</p>
        
        <h3>Categories:</h3>
        <ul class="categories">
            <#list post.categories as category>
                <li>${category}</li>
            </#list>
        </ul>
        <i>Published on the ${post.publishing_date}</i>
    </div>
</#list>

</body>
</html>
