# mvn clean package spring-boot:repackage -Dmaven.test.skip=true
# docker rmi choerodon-iam
# docker build -t choerodon-iam -f Dockerfile .

FROM ubuntu:bionic

RUN apt-get update && \
    apt-get install -y openjdk-8-jdk openjdk-8-dbg wget

USER root
WORKDIR /root
VOLUME /tmp
VOLUME /root/async-profiler
EXPOSE 8030

COPY target/app.jar /root/app.jar

CMD ["java", "-XX:+PreserveFramePointer", "-XX:+UnlockDiagnosticVMOptions", "-XX:+DebugNonSafepoints", "-XX:+HeapDumpOnOutOfMemoryError", "-jar", "app.jar"]
