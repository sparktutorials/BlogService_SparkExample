Blog service
============

Simple example application descrived in a couple of tutorials: 

* [Getting started with Spark: it is possible to create lightweight RESTful application also in Java](http://tomassetti.me/getting-started-with-spark-it-is-possible-to-create-lightweight-restful-application-also-in-java/)
* [Spark and Databases: Configuring Spark to work with Sql2o in a testable way](http://sparktutorials.weebly.com/tutorials/spark-and-databases-configuring-spark-to-work-with-sql2o-in-a-testable-way)

Start the application
=====================

You need a PostgreSQL database. Run on it the code listed in the directory db.
Done? Cool.

Now run the application. You can specify where to find your database, for example:

```
mvn exec:java -Dexec.args="--db-host myDBServer --db-port 5432"
```

See class BlogService for more details.

In the project there is also a Docker container for the DB, you can use it if you know what you are doing.


Insert a post
=============

TBW

Get post lists
==============

TBW


Add a comment
=============

TBW

Get comments for a post
=======================

TBW
