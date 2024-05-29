FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD root_password
ENV MYSQL_DATABASE qlik_cloud_audition
ENV MYSQL_USER qlik-user
ENV MYSQL_PASSWORD qlik-password

ADD init.sql /docker-entrypoint-initdb.d

EXPOSE 3306
