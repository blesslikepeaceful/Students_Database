package sample.Students_Database;

// Используется для работы с такой переменной как "Студенты"

public class Students {

    private String name;
    private String surname;

    // Конструктор класса Students

    public Students(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    // Возвращает имя

    public String getName() {
        return name;
    }

    // Возвращает фамилию

    public String getSurname() {
        return surname;
    }
}
