package sample.Students_Database;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

// Отвечает за окно Statistics-view.fxml

public class StatisticsController {
    private static final Logger logger = LogManager.getLogger();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button grades_button;

    @FXML
    private Button import_button;

    @FXML
    private TableColumn<Students, String> name;

    @FXML
    private TableColumn<Students, String> surname;

    @FXML
    private TableView<Students> table;

    @FXML
    private ChoiceBox<String> GroupBox;

    @FXML
    private Label name_field;

    @FXML
    private Label Grades_field;

    @FXML
    private Label Avarage_grade;

    @FXML
    private Label no_work;

    @FXML
    private Label no_classes;

    @FXML
    private Label Avarage_classes;

    @FXML
    private Label name_group;

    @FXML
    private Label group_classes;

    @FXML
    private Label group_grades;

    @FXML
    private Label group_works;

    /**
     * Инициализация окна
     * Обрабатывает все события окна
     */
    @FXML
    void initialize() {
        //Открытие окна с выставлением оценок
        grades_button.setOnAction(actionEvent -> {
            try {
                NewWindow("/sample/Students_Database/Grades-view.fxml");
            } catch (IOException e) {
                logger.error("Ошибка открытия окна");
                throw new RuntimeException(e);
            }
        });

        //Открытие окна импорта студентов
        import_button.setOnAction(actionEvent -> {
            try {
                NewWindow("/sample/Students_Database/Import-view.fxml");
            } catch (IOException e) {
                logger.error("Ошибка открытия окна");
                throw new RuntimeException(e);
            }
        });


        //Добавление списка групп в ChoiceBox
        DatabaseHandler db = new DatabaseHandler();
        GroupBox.getItems().addAll(db.getGroups());
        //Вывод списка группы при ее выборе
        GroupBox.setOnAction(this::SetStudents);

        //Обработка выбора студента в таблице
        TableView.TableViewSelectionModel<Students> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Students>() {
            @Override
            public void changed(ObservableValue<? extends Students> observableValue, Students students, Students t1) {
                if (t1 != null) student_statistics(GroupBox.getValue(), t1.getSurname(), t1.getName());
            }
        });
    }

    /**
     * Открытие нового окна
     *
     * @param Window новое окно
     * @throws IOException
     */
    public void NewWindow(String Window) throws IOException {
        logger.info("Открытие нового окна");
        root = FXMLLoader.load(getClass().getResource(Window));
        stage = (Stage) grades_button.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Вывод списка студентов в таблицу

    private void SetStudents(ActionEvent actionEvent) {
        DatabaseHandler db = new DatabaseHandler();
        ResultSet Set = db.GetStudents(GroupBox.getValue());

        name.setCellValueFactory(new PropertyValueFactory<Students, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<Students, String>("surname"));


        ArrayList<Students> list = new ArrayList<Students>();
        try {
            while (Set.next()) {
                list.add(new Students(Set.getString(2), Set.getString(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        table.setItems(FXCollections.observableList(list));
        group_statistics(GroupBox.getValue());
    }

    // Вывод статистики студента
    public void student_statistics(String group, String surname, String name) {
        DecimalFormat Format = new DecimalFormat("#.##");
        DatabaseHandler db = new DatabaseHandler();
        String[] Works = db.getPracticalWork();
        String[] Classes = db.getClasses();
        int[] geades = db.getStudentGrades(group, surname, Works.length);
        int[] studentClasses = db.getStudentClasses(group, surname, Classes.length);
        //Вывод имени студента
        name_field.setText("Статистика студента " + surname + " " + name + ":");
        //Вывод оценок студента
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < Works.length; i++) {
            text.append(Works[i]).append(": ");
            if (geades[i] == 0) {
                text.append("нет").append("; ");
            } else {
                text.append(geades[i]).append("; ");
            }
        }
        Grades_field.setText(text.toString());
        //Вывод средней оценки
        int count = Works.length;
        int sum = 0;
        for (int grade : geades) {
            if (grade != 0) {
                sum += grade;
            } else {
                count -= 1;
            }
        }
        float out = (float) sum / count;
        Avarage_grade.setText("Средняя оценка: " + Format.format(out));
        //Нет работ
        text = new StringBuilder();
        for (int i = 0; i < Works.length; i++) {
            if (geades[i] == 0) {
                text.append(Works[i]).append("; ");
            }
        }
        if (text.isEmpty()) {
            text.append("Все работы сданы");
        }
        no_work.setText(text.toString());
        //Был на занятиях
        sum = 0;
        text = new StringBuilder();
        for (int i = 0; i < Classes.length; i++) {
            if (studentClasses[i] == 1) {
                text.append(Classes[i]).append("; ");
                sum += 1;
            }
        }
        if (text.isEmpty()) {
            text.append("Не был ни на одном занятии");
        }
        no_classes.setText(text.toString());
        out = (float) sum / Classes.length * 100;
        Avarage_classes.setText("Был на " + sum + " из " + Classes.length + " занятий (" +
                Format.format(out) + "%)");
    }

    // Получение массива фамилий группы

    public String[] GetSurnames(String group) {
        DatabaseHandler db = new DatabaseHandler();
        ResultSet Set = db.GetStudents(group);
        ArrayList<String> surnames = new ArrayList<>();
        try {
            while (Set.next()) {
                surnames.add(Set.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return surnames.toArray(new String[0]);
    }

    // Статистика группы

    public void group_statistics(String group) {
        DatabaseHandler db = new DatabaseHandler();
        String[] Works = db.getPracticalWork();
        String[] Classes = db.getClasses();
        String[] Surnames = GetSurnames(group);
        //Вывод названия группы
        name_group.setText("Статистика группы " + group + ":");
        //Подсчет статистики группы
        int sum_classes = 0;
        int sum_grades = 0;
        int count_grades = Works.length * Surnames.length;
        for (String surname : Surnames) {
            int[] grades = db.getStudentGrades(group, surname, Works.length);
            int[] studentClasses = db.getStudentClasses(group, surname, Classes.length);
            for (int grade : grades) {
                if (grade != 0) {
                    sum_grades += grade;
                } else {
                    count_grades -= 1;
                }
            }
            for (int classe : studentClasses) {
                sum_classes += classe;
            }
        }

        //Вывод значений

        DecimalFormat Format = new DecimalFormat("#.##");
        float out = (float) sum_classes / Classes.length / Surnames.length * 100;
        group_classes.setText("На занятиях присутствует в среднем " + Format.format(out) + "% группы");
        out = (float) sum_grades / count_grades;
        group_grades.setText("Средняя оценка по группе " + Format.format(out));
        int no_works = Works.length * Surnames.length - count_grades;
        group_works.setText("Работ не сдано " + no_works);

    }
}

