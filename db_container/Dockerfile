FROM ubuntu:latest
MAINTAINER Federico Tomassetti

RUN apt-get install -y wget
RUN echo "deb http://apt.postgresql.org/pub/repos/apt/ trusty-pgdg main" >> /etc/apt/sources.list.d/pgdg.list

RUN wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -

RUN apt-get update
RUN apt-get install -y python-software-properties software-properties-common
RUN apt-get -y install postgresql-9.4 vim
RUN echo "local   all             all trust" >> /etc/postgresql/9.4/main/pg_hba.conf
RUN echo "host    all             all             0.0.0.0/0            trust" >> /etc/postgresql/9.4/main/pg_hba.conf

RUN echo "listen_addresses='*'" >> /etc/postgresql/9.4/main/postgresql.conf

ADD ./setup.sql /db/setup.sql
ADD ./schema.sql /db/schema.sql

RUN /etc/init.d/postgresql start && sudo -u postgres psql < /db/setup.sql && /etc/init.d/postgresql stop
RUN /etc/init.d/postgresql start && sudo -u postgres psql -d blog < /db/schema.sql && /etc/init.d/postgresql stop

EXPOSE 5432

USER postgres

CMD ["/usr/lib/postgresql/9.4/bin/postgres", "-D", "/var/lib/postgresql/9.4/main", "-c", "config_file=/etc/postgresql/9.4/main/postgresql.conf"]
