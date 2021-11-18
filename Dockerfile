FROM azul/zulu-openjdk-alpine:11
EXPOSE 8102
ADD target/lms-1.0.jar lms-1.0.jar 
ENTRYPOINT ["java","-jar","/lms-1.0.jar"]