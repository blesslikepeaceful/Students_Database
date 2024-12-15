# Инструкция по запуску и созданию базы данных

## Запуск программы

| Шаг | Действие                                                                                       |
|-----|------------------------------------------------------------------------------------------------|
| 1   | Клонировать репозиторий в IntelliJ IDEA                                                        |
| 2   | Установить драйвер MySQL ([скачать здесь](https://dev.mysql.com/downloads/connector/j/5.1.html)) |
| 3   | Создать базу данных по инструкции ниже                                                         |

## Создание базы данных

 Название базы данных: **STUDENTS_DATABASE**
        ### Шаг 1. Создание схемы:

   ```
CREATE SCHEMA STUDENTS_DATABASE;
   ```

   База данных должна содержать таблицы:
   
   **Группы**

      ```
      CREATE TABLE `spbgut`.`groups` (
         `id` INT NOT NULL AUTO_INCREMENT,
         `group` VARCHAR(45) NOT NULL,
         PRIMARY KEY (`id`));
      ```
   
   **Практические занятия**
      ```
      CREATE TABLE `STUDENTS_DATABASE`.`practical_works` (
         `id` INT NOT NULL AUTO_INCREMENT,
         `practical_work` VARCHAR(45) NOT NULL,
         PRIMARY KEY (`id`));
      ```
   
   **Занятия**
      ```
      CREATE TABLE `STUDENTS_DATABASE`.`classes` (
         `id` INT NOT NULL AUTO_INCREMENT,
         `class` VARCHAR(45) NOT NULL,
         PRIMARY KEY (`id`));
      ```

### Шаг 2. Подключение БД:

Этот репозиторий содержит XML-файл (`src/main/resources/DataBaseConfigs.xml`), предназначенный для хранения параметров
подключения к базе данных. Следуйте инструкциям ниже, чтобы заполнить необходимую информацию.

## Инструкции:

**Заполните параметры подключения:**
Замените значения на свои.

   ```
   xml
   <?xml version="1.0" encoding="UTF-8"?>
   <databaseConnection>
       <host>Ваш_Хост_Базы_Данных</host>
       <port>Ваш_Порт_Базы_Данных</port>
       <databaseName>spbgut</databaseName>
       <username>Ваше_Имя_Пользователя</username>
       <password>Ваш_Пароль</password>
   </databaseConnection>