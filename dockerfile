FROM openjdk:11
LABEL key="Lucas Silva"
ADD target/pedidoapp-0.0.1-SNAPSHOT.jar pedido-app.jar
ENV SPRING.PROFILES.ACTIVE: prod
ENTRYPOINT ["java", "-jar", "pedido-app.jar"]
EXPOSE 8080