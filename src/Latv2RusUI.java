import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.geometry.Pos.CENTER;

public class Latv2RusUI extends Application {
    static LatvianLanguageTrainer latvianLanguageTrainer;

    Label labelToTranslate = new Label("");
    Button[] buttonTranslationVariants = new Button[LatvianLanguageTrainer.numberOfTranslations];
    Button buttonNext = new Button();

    public void fillControlsWithText() {
        latvianLanguageTrainer.challenge();
        String wordForTranslate = latvianLanguageTrainer.getTheWord();
        greyAllButton();
        labelToTranslate.setText(wordForTranslate);
        for (int i = 0; i < LatvianLanguageTrainer.numberOfTranslations; i++) {
            buttonTranslationVariants[i].setText(latvianLanguageTrainer.getTranslation(i));
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
        for (int i = 0; i < LatvianLanguageTrainer.numberOfTranslations; i++) {
            final int j = i;
            buttonTranslationVariants[i].setOnAction(value -> {
                greyAllButton();
                changeButtonColorBasedOnRightWrongAnswer(buttonTranslationVariants[j],
                        latvianLanguageTrainer.getTranslation(j));
            });
        }
    }

    private void createAllButtonInstances() {
        for (int i = 0; i < LatvianLanguageTrainer.numberOfTranslations; i++) {
            buttonTranslationVariants[i] = new Button();
            buttonTranslationVariants[i].setPrefWidth(100);
        }
    }

    private void greyAllButton() {
        for (int i = 0; i < LatvianLanguageTrainer.numberOfTranslations; i++) {
            buttonTranslationVariants[i].setStyle("-fx-background-color: none;");
        }

    }

    private void changeButtonColorBasedOnRightWrongAnswer(Button choiceButton, String translation) {
        if (latvianLanguageTrainer.getTrueTranslation().equals(translation)) {
            choiceButton.setStyle("-fx-background-color: #00ff00;");
        } else {
            choiceButton.setStyle("-fx-background-color: #ff0000;");
        }
    }

    public static void main(String[] args) {
        latvianLanguageTrainer = new LatvianLanguageTrainer(LatvianLanguageTrainer.numberOfTranslations);
        latvianLanguageTrainer.loadVocabularyFromClassLoader();
        Application.launch(args);
    }
}