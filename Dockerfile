FROM python:3.8

ENV SRC_DIR /usr/bin/src/webapp/src

COPY empty_server.py3 ${SRC_DIR}/

WORKDIR ${SRC_DIR}

ENV PYTHONUNBUFFERED=1

CMD ["python", "empty_server.py3"]

