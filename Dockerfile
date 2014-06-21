FROM  centos
MAINTAINER Alex King "docker@alexanderjamesking.com"

RUN yum -y update
RUN yum -y install openssh-server openssh-clients
RUN echo 'root:centos' |chpasswd
RUN service sshd start

RUN yum install -y java-1.7.0-openjdk-devel.x86_64
ADD hello-world-0.1.0-standalone.jar /
ADD docker-startup.sh /

EXPOSE 22 3000

CMD /docker-startup.sh

