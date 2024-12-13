package bondarenko.avtoservice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.InputStream;

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
        InputStream iconStream = getClass().getResourceAsStream("/images/car.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);
        primaryStage.setWidth(678);
        primaryStage.setHeight(461);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxml));
        loader.setControllerFactory(context::getBean); // Используем Spring для создания контроллера
        return loader.load();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close(); // Закрываем Spring контекст при завершении приложения
    }
}