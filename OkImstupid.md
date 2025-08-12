## Issue 
- The application is not connecting to the database.
- and the real problem is just the VSC settings. - the project currently using mvn/gradle - the vsc keep using gradle as default compiler - 
so changing pom settings is pointless if the VSC settings are not changed to use the correct build tool - or at least point less for calling tests.
**FIXED**

- The problem is that your test is using @DataJpaTest but Spring Boot can't find the main application class. This happens because Spring Boot looks for a class annotated with @SpringBootApplication in the same package or a parent package. 
( This took me like hours to figure out)