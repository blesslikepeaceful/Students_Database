package sample.Students_Database;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Конроллер окна Inport-view.fxml
 */
public class ImportController {
    private static final Logger logger = LogManager.getLogger();

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Button grades_button;

    @FXML
    private Button statistics_button;

    @FXML
    private Button save_button;

    @FXML
    private TextField groupField;

    @FXML
    private TextArea studentsArea;

    /**
     * Инициализация окна
     * Обрабатывает все события окна
     */
    @FXML
    void initialize() {

        //Переход на окно с выставлением оценок
        grades_button.setOnAction(actionEvent -> {
            try {
                NewWindow("/sample/Students_Database/Grades-view.fxml");
            } catch (IOException e) {
                logger.error("Ошибка открытия окна");
                throw new RuntimeException(e);
            }
        });

        //Открытие окна со статистикой
        statistics_button.setOnAction(actionEvent -> {
            try {
                NewWindow("/sample/Students_Database/Statistics-view.fxml");
            } catch (IOException e) {
                logger.error("Ошибка открытия окна");
                throw new RuntimeException(e);
            }
        });


        //Сохранение в базу данных списка новой группы
        save_button.setOnAction(actionEvent -> {
            DatabaseHandler db = new DatabaseHandler();
            db.NewGroup(groupField.getText().trim(), studentsArea.getText());
            groupField.setText("");
            studentsArea.setText("");
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
}