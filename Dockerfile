FROM daocloud.io/liushaoping/maven:latest

ADD . /tmp/build/
RUN cd /tmp/build/