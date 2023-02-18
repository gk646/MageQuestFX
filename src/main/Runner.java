package main;

import input.InputHandler;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class Runner extends Application {
    public static ImageCursor crosshair;
    public static ImageCursor crosshair3;

    /**
     * @author Lukas Gilch
     */

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GraphicsEnvironment gE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Canvas canvas = new Canvas(gE.getDefaultScreenDevice().getDisplayMode().getWidth(), gE.getDefaultScreenDevice().getDisplayMode().getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        scene.setCursor(Cursor.HAND);
        stage.setScene(scene);
        stage.setTitle("Mage Quest_2D");
        InputStream is = getClass().getResourceAsStream("/Icons/icon2.png");
        Image cursor = new Image(getClass().getResource("/ui/crosshair_1.png").toExternalForm());
        Image cursor3 = new Image(getClass().getResource("/ui/crosshair_3.png").toExternalForm());
        crosshair = new ImageCursor(cursor, 8, 8);
        crosshair3 = new ImageCursor(cursor3, 16, 16);
        scene.setCursor(crosshair);
        assert is != null;
        stage.getIcons().add(new Image(is));
        stage.setMaxWidth(gE.getDefaultScreenDevice().getDisplayMode().getWidth());
        stage.setMaxHeight(gE.getDefaultScreenDevice().getDisplayMode().getHeight());
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreenExitHint("");
        TextField textField = new TextField();
        textField.setVisible(false);
        textField.setPrefSize(300, 100);
        root.getChildren().add(textField);
        stage.setFullScreen(true);
        Slider slider = new Slider(60, 240, 120);
        slider.setVisible(false);
        slider.setPrefSize(200, 100);
        root.getChildren().add(slider);
        MainGame mainGame = new MainGame(gE.getDefaultScreenDevice().getDisplayMode().getWidth(), gE.getDefaultScreenDevice().getDisplayMode().getHeight(), gc);
        InputHandler.instance = new InputHandler(mainGame, scene);
        mainGame.inputH = InputHandler.instance;
        Thread thread = new Thread(mainGame::run);
        stage.show();
        thread.start();

        //MOUSE
        scene.setOnMousePressed(event -> mainGame.inputH.handleMousePressed(event));
        scene.setOnMouseReleased(event -> mainGame.inputH.handleMouseReleased(event));
        scene.setOnMouseMoved(event -> mainGame.inputH.handleMouseMovement(event));
        scene.setOnMouseDragged(event -> mainGame.inputH.handleMouseMovement(event));
        scene.setOnMouseClicked(event -> mainGame.inputH.handleMouseClick(event));
        scene.setOnScroll(event -> mainGame.inputH.handleScroll(event));
        //KEYS
        scene.setOnKeyTyped(event -> mainGame.inputH.handleKeyType(event));
        scene.setOnKeyPressed(event -> mainGame.inputH.handleKeyPressed(event));
        scene.setOnKeyReleased(event -> mainGame.inputH.handleKeyReleased(event));

        stage.setOnCloseRequest(e -> System.exit(1));
    }
}
//TODO optimizations with ai
//TODO and maybe few misc items
//TODO and more scripting for tutorial and quest
//TODO upscaling all images to 48
//TODO
//TODO
//TODO
//TODO