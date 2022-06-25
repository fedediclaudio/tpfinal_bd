FROM python:3.9-alpine

# Install GCC as it is required for cryptography library
RUN apk add gcc libc-dev libffi-dev openssl-dev

ENV PYTHONUNBUFFERED 1

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

COPY requirements.txt /
RUN pip3 install -r requirements.txt

# ENV PYTHONPATH "${PYTHONPATH}:/usr/local/lib/python3.9/site-packages/"

COPY . /src/
WORKDIR /src/

EXPOSE 8081