FROM openjdk:17
ADD build/libs/library-book-management-service.jar library-book-management-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-book-management-service.jar"]