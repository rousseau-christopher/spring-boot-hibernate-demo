# Spring boot JPA with Hibernate
This project is a demo project to test different pattern with Spring boot data and Jpa.

## debug SQL
To understand what happens, it's better to have all sql and transaction in logs.
- https://www.baeldung.com/sql-logging-spring-boot
- https://github.com/gavlyukovskiy/spring-boot-data-source-decorator
- In Intellij, there is also a spring debugger plugin : https://www.youtube.com/watch?v=K2tYAHG2XJ8&t=1324s

## Open Session in View
Spring automatically open a session on the controller layer. It will also auto-commit<br/>
It's usually better to have a controller over this.
- https://www.baeldung.com/spring-open-session-in-view

## N+1 problem with entityGraph
@EntityGraph is the Jpa Solution for n+1 problem
- https://www.baeldung.com/spring-data-jpa-named-entity-graphs

## Equal, Hash Code, toString
Beware of Equal, HashCode and ToString. They can pull all your lazy Fetch !!!
- https://thorben-janssen.com/ultimate-guide-to-implementing-equals-and-hashcode-with-hibernate/
- https://jpa-buddy.com/blog/hopefully-the-final-article-about-equals-and-hashcode-for-jpa-entities-with-db-generated-ids/

## Inheritance
- https://www.baeldung.com/hibernate-inheritance

## Pagination and Sorting using Pageable
Pagination and Sorting model can be managed by spring MVC and correctly shown in Swagger ui
- https://bootify.io/spring-boot/pagination-in-spring-boot-rest-api.html
- https://springdoc.org/faq.html#how-can-i-map-pageable-spring-date-commons-object-to-correct-url-parameter-in-swagger-ui


## Update using @Query
update a column with Jpa can be inefficient. Lot of time I see code that read the object from the repository, change fields and save.

This way of updating field create 2 query : one SELECT and one UPDATE

another way is to use @Modify with @Query to update fields at once
- https://www.baeldung.com/spring-data-jpa-modifying-annotation


## Batch Update
When we have multiple line updates, it's better to regroup them. This will let the DB optimize the multiple insert
- https://thorben-janssen.com/implementing-bulk-updates-with-spring-data-jpa/

## JSON column in postgresql with jpa
postgres can store json in 2 format : json and jsonb
jsonb seams better; https://www.dbvis.com/thetable/json-vs-jsonb-in-postgresql-a-complete-comparison/

Adding a library to handle json type : https://www.baeldung.com/hibernate-types-library

Hibernate and Jpa with one annotation: https://vladmihalcea.com/how-to-map-json-objects-using-generic-hibernate-types/

Other doc convert by hand: https://www.baeldung.com/spring-boot-jpa-storing-postgresql-jsonb

- https://vladmihalcea.com/hibernate-types-hypersistence-utils/
- https://github.com/vladmihalcea/hypersistence-utils
- https://thorben-janssen.com/persist-postgresqls-jsonb-data-type-hibernate/


## Indexing json properties
- https://www.yugabyte.com/blog/index-json-postgresql/
  https://gist.github.com/Fabricio20/83c86ccf055c8efc359463dc8a1e895c#file-postgres-md

## Streaming
There is a lot of problem then streaming over Api:
- the stream will be close by the transaction manager before sended to the api
- Entities will stay in memory because of the hibernate cache
- How to stream data: https://vladmihalcea.com/spring-data-jpa-stream/
- How to stream memory efficiently: https://dev.to/ratulsharker/streaming-large-json-response-in-spring-2pho

## Auditing
- https://docs.spring.io/spring-data/jpa/reference/auditing.html
- https://www.baeldung.com/database-auditing-jpa
- https://vladmihalcea.com/the-best-way-to-implement-an-audit-log-using-hibernate-envers/
- https://www.baeldung.com/java-hibernate-envers-extending-revision-custom-fields
- https://thorben-janssen.com/hibernate-envers-query-data-audit-log/
- https://vladmihalcea.com/spring-data-envers/
- https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#envers-revisionlog
- https://www.delia.tech/delia-academy-articles/hibernate-envers-la-librairie-java-pour-le-suivi-des-actions-en-bdd

There are limit with hibernate enver. Only action on managed entity are audited. Any modification done with @query @Modifying are not audited !

## Transaction
With hibernate it's best to always have a transaction then accessing database. You can set a readonly transaction when not changing database. It can improve speed. And it will cancel all database modifications if they are not inside a write transaction!


## Locking
- https://www.baeldung.com/jpa-pessimistic-locking
- https://www.baeldung.com/jpa-optimistic-locking
- https://medium.com/jpa-java-persistence-api-guide/optimistic-vs-pessimistic-locking-in-spring-data-69ae32402fe3
- https://discourse.hibernate.org/t/envers-version-field-is-nul/2113

## GIN index
- https://pganalyze.com/blog/gin-index

## Test