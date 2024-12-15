package sample.Students_Database;

// Используется для работы с такой переменной как "Студенты"

public class Students {

    private String name;
    private String surname;

    /**
     * Конструктор класса
     *
     * @param name    имя
     * @param surname фамилия
     */
    public Students(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    /**
     * Возвращает имя
     *
     * @return имя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает фамилию
     *
     * @return фамилия
     */
    public String getSurname() {
        return surname;
    }
}
