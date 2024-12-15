package sample.Students_Database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//Главный класс, с которого начинается работа программы

public class MainApplication extends Application {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Стартовый метод
     * Открывает стартовое окно Home-view.fxml
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        //Получение параметров для подключения к база данных
        Configs.init();
        logger.info("Открытие стартового окна");

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Students_Database_App");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}