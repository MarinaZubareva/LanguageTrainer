import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static javafx.geometry.Pos.CENTER;

public class Latv2RusUI extends Application {
    static LatvianLanguageTrainer latvianLanguageTrainer;

    Label labelToTranslate = new Label("");
    List<Button> buttonTranslationVariants = new ArrayList<>();
    Button buttonNext = new Button();

    public void fillControlsWithText() {
        latvianLanguageTrainer.challenge();
        String wordForTranslate = latvianLanguageTrainer.getTheWord();
        greyAllButton();
        labelToTranslate.setText(wordForTranslate);
        for (Button button : buttonTranslationVariants) {
            button.setText(latvianLanguageTrainer.getListOfTranslations().get(buttonTranslationVariants.indexOf(button)));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Latviesu valoda");
        Label labelBlank1 = new Label("");
        Label labelBlank2 = new Label("");

        labelToTranslate.setStyle("-fx-font-style:italic;-fx-font-weight: bold");
        buttonNext.setText("Nākamais");

        createAllButtonInstances();

        buttonNext.setPrefWidth(100);
        fillControlsWithText();
        setActionOnButtons();

        buttonNext.setOnAction(value -> {
            fillControlsWithText();
        });

        VBox vbox = new VBox(labelToTranslate, labelBlank1);
        vbox.getChildren().addAll(buttonTranslationVariants);
        vbox.getChildren().add(labelBlank2);
        vbox.getChildren().add(buttonNext);
        vbox.setAlignment(CENTER);
        Scene scene = new Scene(vbox, 100, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setActionOnButtons() {
        for (Button button : buttonTranslationVariants) {
            button.setOnAction(value -> {
                greyAllButton();
                changeButtonColorBasedOnRightWrongAnswer(button);
            });
        }
    }

    private void createAllButtonInstances() {
        for (int i = 0; i < LatvianLanguageTrainer.numberOfTranslations; i++) {
            Button button = new Button();
            button.setPrefWidth(100);
            buttonTranslationVariants.add(button);
        }
    }

    private void greyAllButton() {
        for (Button button : buttonTranslationVariants) {
            button.setStyle("-fx-background-color: none;");
        }
    }

    private void changeButtonColorBasedOnRightWrongAnswer(Button choiceButton) {
       // if (latvianLanguageTrainer.getTrueTranslation().equals(choiceButton.getText())) {
        if (latvianLanguageTrainer.translationIsRight(choiceButton.getText())) {
            choiceButton.setStyle("-fx-background-color: #00ff00;");
        } else {
            choiceButton.setStyle("-fx-background-color: #ff0000;");
        }
    }

    public static void main(String[] args) {
        latvianLanguageTrainer = new LatvianLanguageTrainer();
        latvianLanguageTrainer.loadVocabularyFromClassLoader();
        Application.launch(args);
    }
}