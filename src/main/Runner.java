/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package main;

import input.InputHandler;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Objects;

public class Runner extends Application {
    public static ImageCursor crosshair;
    public static ImageCursor selectCrosshair;

    /**
     * @author gk646
     */

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Screen screen = Screen.getPrimary();
        Canvas canvas = new Canvas(screen.getBounds().getWidth(), screen.getBounds().getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        scene.setCursor(Cursor.HAND);
        stage.setScene(scene);
        stage.setTitle("Mage Quest");
        InputStream is = getClass().getResourceAsStream("/Icons/icon2.png");
        Image cursor = new Image(Objects.requireNonNull(getClass().getResource("/ui/crosshair_1.png")).toExternalForm());
        Image cursor3 = new Image(Objects.requireNonNull(getClass().getResource("/ui/crosshair_3.png")).toExternalForm());
        crosshair = new ImageCursor(cursor, 8, 8);
        selectCrosshair = new ImageCursor(cursor3, 16, 16);
        scene.setCursor(crosshair);
        assert is != null;
        stage.getIcons().add(new Image(is));
        stage.setMaxWidth(screen.getBounds().getWidth());
        stage.setMaxHeight(screen.getBounds().getHeight());
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        MainGame mainGame = new MainGame((int) screen.getBounds().getWidth(), (int) screen.getBounds().getHeight(), gc);
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

        stage.setOnCloseRequest(e -> mainGame.sqLite.saveGameAndExit());

        Thread.setDefaultUncaughtExceptionHandler((s, throwable) -> {
            PrintWriter writer;
            try {
                writer = new PrintWriter("error.log");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            throwable.printStackTrace(writer);
            writer.close();
            throwable.printStackTrace();
        });
    }
}