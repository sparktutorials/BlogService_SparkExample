docker ps|grep blog_db_container|cut -d ' '  -f1|xargs docker kill
