package sample.Students_Database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

// Контроллер стартового окна Home-view.fxml

public class HomeController {
    private static final Logger logger = LogManager.getLogger();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button grades_button;

    @FXML
    private Button import_button;

    @FXML
    private Button statistics_button;

    // Обрабатывает все события окна

    @FXML
    void initialize() {
        //Открытие окна выставления оценок
        grades_button.setOnAction(actionEvent -> {
            try {
                NewWindow("/sample/Students_Database/Grades-view.fxml");
            } catch (IOException e) {
                logger.error("Ошибка открытия окна");
                throw new RuntimeException(e);
            }
        });
        //Открытие окна статистики
        statistics_button.setOnAction(actionEvent -> {
            try {
                NewWindow("/sample/Students_Database/Statistics-view.fxml");
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
    }

    // Открытие нового окна

    public void NewWindow(String Window) throws IOException {
        logger.info("Открытие нового окна");
        root = FXMLLoader.load(getClass().getResource(Window));
        stage = (Stage) grades_button.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

