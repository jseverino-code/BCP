FROM centos:7
RUN yum install -y java-11
CMD ["/bin/bash"]
COPY target/conversor*.jar /bcp/api/conversor.jar
WORKDIR /bcp/api/
EXPOSE 8080
CMD ["java", "-jar", "/bcp/api/conversor.jar"]