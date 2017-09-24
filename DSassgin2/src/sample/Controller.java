package sample;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;


public class Controller {

    private double startX;

    private double startY;

    private double endX;

    private double endY;

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider slider;

    @FXML
    private Label sliderSize;

    @FXML
    private Pane newPane;



    public void initialize() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setOnAction(e ->
                g.setStroke(colorPicker.getValue())
        );
    }
//        slider.valueProperty().addListener(e -> {
//            double sliderValue = slider.getValue();
//            String str = String.format("%.1f", sliderValue);
//            sliderSize.setText(str);
//            g.setLineWidth(sliderValue);
//
//        });
//    }

    public void setFont(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        slider.valueProperty().addListener(e -> {
            double sliderValue = slider.getValue();
            String str = String.format("%.1f", sliderValue);
            sliderSize.setText(str);
            g.setLineWidth(sliderValue);

        });
    }

    public void sketch(){
        setFont();
        GraphicsContext g = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
                g.beginPath();
                g.lineTo(e.getX(), e.getY());
                g.stroke();

        });
        canvas.setOnMouseDragged(e -> {

                g.lineTo(e.getX(), e.getY());
                g.stroke();

        });
        canvas.setOnMouseReleased(e->{

        });

    }

    public void erase(){
        setFont();
        GraphicsContext g = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
            g.beginPath();
            double size = slider.getValue();
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            g.clearRect(x, y, size, size);
        });
        canvas.setOnMouseDragged( e -> {
            double size = slider.getValue();
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            g.clearRect(x, y, size, size);
            g.closePath();
        });
        canvas.setOnMouseReleased(e->{

        });
    }


    public void lineDraw(){
        setFont();
        GraphicsContext g = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
            g.beginPath();
            startX =  e.getX();
            startY =  e.getY();
    });
        canvas.setOnMouseDragged(e -> {

        });
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY= e.getY();
            g.strokeLine(startX,startY,endX,endY);
            g.closePath();
        });
    }

    public void cirDraw(){
        setFont();
        GraphicsContext g = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
            g.beginPath();
            startX =  e.getX();
            startY =  e.getY();
        });
        canvas.setOnMouseDragged(e -> {

        });
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY= e.getY();
            double x = Math.min(startX,endX);
            double y = Math.min(startY,endY);
            double height = Math.abs(startY-endY);
            g.strokeOval(x,y,height,height);
            g.closePath();
        });
    }

    public void rectDraw(){
        setFont();
        GraphicsContext g = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
            g.beginPath();
            startX =  e.getX();
            startY =  e.getY();
        });
        canvas.setOnMouseDragged(e -> {

        });
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY= e.getY();
            double x = Math.min(startX,endX);
            double y = Math.min(startY,endY);
            double width = Math.abs(startX-endX);
            double height = Math.abs(startY-endY);
            g.strokeRect(x,y,width,height);
            g.closePath();
        });
    }

    public void ovalDraw(){
        setFont();
        GraphicsContext g = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
            g.beginPath();
            startX =  e.getX();
            startY =  e.getY();
        });
        canvas.setOnMouseDragged(e -> {

        });
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY= e.getY();
            double x = Math.min(startX,endX);
            double y = Math.min(startY,endY);
            double width = Math.abs(startX-endX);
            double height = Math.abs(startY-endY);
            g.strokeOval(x,y,width,height);
            g.closePath();
        });
    }

    public void newFile() {
        newPane.getChildren().clear();
        canvas = new Canvas(newPane.getWidth(), newPane.getHeight());
        newPane.getChildren().add(canvas);


    }



    public void onSave() {
        try {
            Image snapshot = canvas.snapshot(null, null);

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e);
        }
    }

    public void onExit() {
        Platform.exit();
    }
}
