import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;

import static javafx.geometry.Pos.CENTER;

public class Latv2RusUI extends Application {
    static LatviesuValodaTrainer latviesuValodaTrainer;

    Label labelToTranslate = new Label("");
    Button button1 = new Button();
    Button button2 = new Button();
    Button button3 = new Button();
    Button button4 = new Button();
    Button buttonNext = new Button();

    public void fillControlsWithText() {
        latviesuValodaTrainer.challenge();
        String wordForTranslate = latviesuValodaTrainer.getTheWord();
        greyAllButton();
        labelToTranslate.setText(wordForTranslate);
        button1.setText(latviesuValodaTrainer.getTranslation1());
        button2.setText(latviesuValodaTrainer.getTranslation2());
        button3.setText(latviesuValodaTrainer.getTranslation3());
        button4.setText(latviesuValodaTrainer.getTranslation4());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Latviesu valoda");
        Label labelBlank1 = new Label("");
        Label labelBlank2 = new Label("");

        labelToTranslate.setStyle("-fx-font-style:italic;-fx-font-weight: bold");
        buttonNext.setText("Nākamais");
        button1.setPrefWidth(100);
        button2.setPrefWidth(100);
        button3.setPrefWidth(100);
        button4.setPrefWidth(100);
        buttonNext.setPrefWidth(100);
        fillControlsWithText();
        button1.setOnAction(value -> {
            greyAllButton();
            checkTranslation(button1, latviesuValodaTrainer.getTranslation1());
        });
        button2.setOnAction(value -> {
            greyAllButton();
            checkTranslation(button2, latviesuValodaTrainer.getTranslation2());
        });
        button3.setOnAction(value -> {
            greyAllButton();
            checkTranslation(button3, latviesuValodaTrainer.getTranslation3());
        });
        button4.setOnAction(value -> {
            greyAllButton();
            checkTranslation(button4, latviesuValodaTrainer.getTranslation4());
        });

        buttonNext.setOnAction(value -> {
            fillControlsWithText();
        });

        VBox vbox = new VBox(labelToTranslate, labelBlank1, button1, button2, button3, button4, labelBlank2, buttonNext);
        vbox.setAlignment(CENTER);

        Scene scene = new Scene(vbox, 100, 200);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void greyAllButton() {
        button1.setStyle("-fx-background-color: none;");
        button2.setStyle("-fx-background-color: none;");
        button3.setStyle("-fx-background-color: none;");
        button4.setStyle("-fx-background-color: none;");

    }

    private void checkTranslation(Button button, String translation) {
        if (latviesuValodaTrainer.getTrueTranslation().equals(translation)) {
            button.setStyle("-fx-background-color: #00ff00;");
        } else {
            button.setStyle("-fx-background-color: #ff0000;");
        }
    }

    public static void main(String[] args) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream("vocabulary.txt");
        latviesuValodaTrainer = new LatviesuValodaTrainer(is);
        Application.launch(args);
    }
}