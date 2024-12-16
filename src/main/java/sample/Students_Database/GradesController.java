package sample.Students_Database;

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
import java.util.ArrayList;

// Контроллер окна Grades-view.fxml

public class GradesController {

    private static final Logger logger = LogManager.getLogger();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ChoiceBox<String> GroupBox;

    @FXML
    private Button grades_button;

    @FXML
    private Button import_button;

    @FXML
    private Button statistics_button;

    @FXML
    private TableColumn<Students, String> name;

    @FXML
    private TableColumn<Students, String> surname;

    @FXML
    private TableView<Students> table;

    @FXML
    private Button new_practical_work_button;

    @FXML
    private TextField new_practical_work_field;

    @FXML
    private ChoiceBox<String> practical_workBox;

    @FXML
    private Button new_grade_button;

    @FXML
    private TextField new_grade_field;

    @FXML
    private ChoiceBox<String> ClassesBox;

    @FXML
    private Button Class_Button;

    @FXML
    private TextField Classes_field;

    @FXML
    private Button NewClass_Button;


    // Обрабатывает все события окна

    @FXML
    void initialize(){

        //Открытие окна импорта студентов

        import_button.setOnAction(actionEvent -> {
            try {
                NewWindow("/sample/Students_Database/Import-view.fxml");
            } catch (IOException e) {
                logger.error("Ошибка открытия окна");
                throw new RuntimeException(e);
            }
        });

        //Открытие окна со статистикой студента
        statistics_button.setOnAction(actionEvent -> {
            try {
                NewWindow("/sample/Students_Database/Statistics-view.fxml");
            } catch (IOException e) {
                logger.error("Ошибка открытия окна");
                throw new RuntimeException(e);
            }
        });

        //Сохранение новой практической работы в базу данных
        new_practical_work_button.setOnAction(actionEvent -> {
            if (!new_practical_work_field.getText().trim().isEmpty()){
                DatabaseHandler db = new DatabaseHandler();
                //Сохранение практической работы
                db.NewPracticalWork(new_practical_work_field.getText().trim());
                //Обновление контента окна
                new_practical_work_field.setText("");
                practical_workBox.getItems().clear();
                practical_workBox.getItems().addAll(db.getPracticalWork());
            }
        });

        //Сохранение нового занятия в базу данных
        NewClass_Button.setOnAction(actionEvent -> {
            if (!Classes_field.getText().isEmpty()){
                DatabaseHandler db = new DatabaseHandler();
                //Сохранение занятия
                db.NewClass(Classes_field.getText().trim());
                //Обновление контента окна
                Classes_field.setText("");
                ClassesBox.getItems().clear();
                ClassesBox.getItems().addAll(db.getClasses());
            }
        });

        //Отметить студента на паре
        Class_Button.setOnAction(actionEvent -> {
            if (!table.getSelectionModel().isEmpty() &&
                    !ClassesBox.getValue().isEmpty()){
                Students Student = table.getSelectionModel().getSelectedItem();
                DatabaseHandler db = new DatabaseHandler();
                db.SetClass(GroupBox.getValue(), ClassesBox.getValue(), Student.getSurname());
            }
        });

        //Сохранение новой оценки за практическую работу
         new_grade_button.setOnAction(actionEvent -> {
             if (!new_grade_field.getText().trim().isEmpty() &&
                     !practical_workBox.getValue().isEmpty() &&
                     !table.getSelectionModel().isEmpty()){
                 Students Student = table.getSelectionModel().getSelectedItem();
                 DatabaseHandler db = new DatabaseHandler();
                 db.NewPracticalWorkGrade(GroupBox.getValue(), Student.getSurname(), practical_workBox.getValue(),
                         new_grade_field.getText().trim());
                 new_grade_field.setText("");
             }
         });

        //Добавление списка групп в ChoiceBox
        DatabaseHandler db = new DatabaseHandler();
        GroupBox.getItems().addAll(db.getGroups());
        //Вывод списка группы при ее выборе
        GroupBox.setOnAction(this::SetStudents);
        //Добавление списка практических работ в ChoiceBox
        practical_workBox.getItems().addAll(db.getPracticalWork());
        //Добавление списка занятий в ChoiceBox
        ClassesBox.getItems().addAll(db.getClasses());
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
    }

    // Открытие нового окна

    public void NewWindow(String Window) throws IOException{
        logger.info("Открытие нового окна");
        root = FXMLLoader.load(getClass().getResource(Window));
        stage = (Stage) grades_button.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}