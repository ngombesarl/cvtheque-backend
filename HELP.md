# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.0-SNAPSHOT/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.0-SNAPSHOT/maven-plugin/build-image.html)
* [Validation](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#io.validation)
* [Java Mail Sender](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#io.email)
* [Spring Cache Abstraction](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#io.caching)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#web.security)
* [JDBC API](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#data.sql)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Data JDBC](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#data.sql.jdbc)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#web)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#web.reactive)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#howto.data-access.exposing-spring-data-repositories-as-rest)
* [Spring Web Services](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#io.webservices)
* [WebSocket](https://docs.spring.io/spring-boot/docs/3.4.0-SNAPSHOT/reference/htmlsingle/index.html#messaging.websockets)
* [RSocket](https://rsocket.io/)
* [Spring REST Docs](https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/)

### Guides
The following guides illustrate how to use some features concretely:

* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Caching Data with Spring](https://spring.io/guides/gs/caching/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
* [Producing a SOAP web service](https://spring.io/guides/gs/producing-web-service/)
* [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

