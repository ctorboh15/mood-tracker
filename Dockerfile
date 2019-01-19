FROM openjdk:10-jre-slim
COPY ./target/moodTracker-0.0.1-SNAPSHOT.jar /usr/src/moodtracker/
WORKDIR /usr/src/moodtracker
EXPOSE 8080
CMD ["java", "-jar", "moodTracker-0.0.1-SNAPSHOT.jar"]