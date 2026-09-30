#Базовый докер образ (будем создавать
#образ поверх другого образа, у которого уже установлены утилиты и тд, java, maven
#исходя из того что тебе нужно
#найти нужный образ можно на Dockerhub
FROM maven:3.9.16-eclipse-temurin-17

# Дефолтные значения аргумента
ARG TEST_PROFILE=api
ARG APIBASEURL=http://localhost:4111
ARG UIBASEURL=http://localhost:3000

#Переменные окружения для контейнера
ENV TEST_PROFILE=${TEST_PROFILE}
ENV APIBASEURL=${APIBASEURL}
ENV UIBASEURL=${UIBASEURL}

#/app указывается по умолчанию - это наша рабочая директория
WORKDIR /app

#Копируем помник ( точка значит копируем
# в текущую папку)
COPY pom.xml .

#Скачиваем все зависимости и кешируем их
RUN mvn dependency:go-offline

#Копируем весь проект в нашу текущую папку
#Первая точка - откуда - весь проект
#Вторая точка - куда - сюда
COPY . .

#Теперь внутри есть зависимости, есть весь проект
#и мы готовы запускать тесты

#Запускаем тесты под админом(root)
USER root

# mvn test -P api
# mvn -DskipTests=true surefire-report:report
# хочу копировать лог в отдельный файл, а не консоль

# если только одна команда, можно было бы написать
# CMD mvn test -P api
# но так как много команд, проще создать bash file

#mkdir -p  /app/logs; - создай папку logs в /app
#-p значит что если такая папка уже есть, то все хорошо, пропускай

#{}2>&1 | tee /app/logs/run.log - все что случится в этих скобках,
#логируй в этот файл - и в консоль выводи, и в файл сохраняй

#mvn test -q -P api  - тут -q (quite режим) убираем большую часть info логов
CMD /bin/bash -c " \
    mkdir -p /app/logs ; \
    { \
    echo '>>> Running tests with profile: ${TEST_PROFILE}' ; \
    mvn test -q -P ${TEST_PROFILE} ; \
    \
    echo '>>> Running surefire-report:report' ; \
    mvn -DskipTests=true surefire-report:report ; \
   } 2>&1 | tee /app/logs/run.log"

