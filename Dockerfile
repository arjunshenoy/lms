FROM azul/zulu-openjdk-alpine:11
EXPOSE 8101
ADD target/lms-1.0.jar lms-1.0.jar 
ENTRYPOINT ["java","-jar","/lms-leave-1.0.jar"]