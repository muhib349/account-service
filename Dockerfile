FROM openjdk:17-oracle

#Information around who maintains the image
MAINTAINER com.abc.mohib.bank

COPY target/account-service-0.0.1-SNAPSHOT.jar account-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/account-service-0.0.1-SNAPSHOT.jar"]