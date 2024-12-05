package bondarenko.avtoservice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class AvtoserviceApplication extends Application {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(AvtoserviceApplication.class, args);
        launch(args); // Запускаем JavaFX приложение
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загружаем FXML файл с использованием Spring
        Parent root = loadFXML("main.fxml");
        primaryStage.setTitle("Автосервис");

        // Устанавливаем размеры окна
        primaryStage.setWidth(960);
        primaryStage.setHeight(540);

        // Создаем сцену
        Scene scene = new Scene(root, 960, 540);

        // Загружаем CSS файл
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Устанавливаем сцену
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxml));
        loader.setControllerFactory(context::getBean); // Используем Spring для создания контроллера
        return loader.load();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close(); // Закрываем Spring контекст при завершении приложения
    }
}