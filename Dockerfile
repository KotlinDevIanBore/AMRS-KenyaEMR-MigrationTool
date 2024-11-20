FROM openjdk:17
MAINTAINER rugute
VOLUME /tmp
EXPOSE 80
ARG JAR_FILE=target/amrs_kenyaemr-0.0.1.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
ENV TZ="Africa/Nairobi"
RUN date
# Copy the .env file into the container
COPY ./.env ./