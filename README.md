# Spring boot JPA with Hibernate
This project is a demo project to test different pattern with Spring boot data and Jpa.

## debug SQL
To understand what happens, it's better to have all sql and transaction in logs.
- https://www.baeldung.com/sql-logging-spring-boot
- https://github.com/jdbc-observations/datasource-proxy?tab=readme-ov-file
- In Intellij, there is also a spring debugger plugin : https://www.youtube.com/watch?v=K2tYAHG2XJ8&t=1324s

## N+1 problem with entityGraph
@EntityGraph is the Jpa Solution for n+1 problem
- https://www.baeldung.com/spring-data-jpa-named-entity-graphs

## Equal, Hash Code, toString
Beware of Equal, HashCode and ToString. They can pull all your lazy Fetch !!!
- https://thorben-janssen.com/ultimate-guide-to-implementing-equals-and-hashcode-with-hibernate/
- https://jpa-buddy.com/blog/hopefully-the-final-article-about-equals-and-hashcode-for-jpa-entities-with-db-generated-ids/

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

## JSON column in postgresql and jpa

## Steaming

## Transaction

## Test