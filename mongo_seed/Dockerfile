FROM mongo

COPY init.json /tmp/init.json

ADD start.sh /start.sh

RUN chmod +x /start.sh

CMD ["/start.sh"]