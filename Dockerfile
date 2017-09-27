FROM tomcat:8

ARG team=undefined
ARG app=undefined
ARG traceability=undefined
ARG JAVA_OPTS=""

# RUN echo "${traceability}" > /traceability.json

# LABEL traceability=$traceability team=$team app=$app

RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/application.war /usr/local/tomcat/webapps/ROOT.war

ENV JAVA_OPTS $JAVA_OPTS
