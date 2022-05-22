#### Stage 1: Build the application
FROM maven:3.8.4-jdk-8 as build

ARG BUILD_DATE
ARG GIT_FULL_BRANCH
ARG SHORT_COMMIT_HASH


LABEL build_date=$BUILD_DATE
LABEL git_branch=$GIT_FULL_BRANCH
LABEL git_short_commit_hash=$SHORT_COMMIT_HASH


WORKDIR /app

COPY . /app

ENTRYPOINT java -jar *.jar