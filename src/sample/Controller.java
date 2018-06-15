package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final ToggleGroup tg = new ToggleGroup();
    private double tmpX = 0.0;
    private double tmpY = 0.0;

    private Group root = new Group();
    private Object object = new Object();

    @FXML
    private Pane pane2D;

    @FXML
    private Button clone;

    @FXML
    private Button delete;

    @FXML
    private ColorPicker color;

    @FXML
    private RadioButton select;

    @FXML
    private RadioButton ellipse;

    @FXML
    private RadioButton line;

    @FXML
    private RadioButton rectangle;

    EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            object = getShape(mouseEvent.getSource());
        }
    };

    public void makeDraggable(Node node)
    {
        node.setOnMousePressed(mousePressed);
        node.setOnMouseDragged(mouseDragged);
        node.setOnMouseReleased(mouseReleased);
    }

    EventHandler<MouseEvent> mousePressed = event -> {
        if (select.isSelected())
        {
            if (event.getSource() instanceof Ellipse) {
                Ellipse e = (Ellipse) (event.getSource());

                tmpX = e.getCenterX() - event.getSceneX();
                tmpY = e.getCenterY() - event.getSceneY();

            }
            if (event.getSource() instanceof Line) {
                Line l = (Line) (event.getSource());

                tmpX = l.getStartX() - event.getSceneX();
                tmpX = l.getStartY() - event.getSceneY();

            }
            if (event.getSource() instanceof Rectangle) {
                Rectangle r = (Rectangle) (event.getSource());

                tmpX = r.getX() - event.getSceneX();
                tmpY = r.getY() - event.getSceneY();
            }
        }
    };

    EventHandler<MouseEvent> mouseDragged = event -> {
        if (select.isSelected())
        {
            if (event.getSource() instanceof Ellipse) {
                Ellipse e = (Ellipse) (event.getSource());

                e.setCenterX(tmpX + event.getSceneX());
                e.setCenterY(tmpY + event.getSceneY());
            }
            if (event.getSource() instanceof Line) {
                Line l = (Line) (event.getSource());

                l.setStartX(tmpX + event.getSceneX());
                l.setStartY(tmpY + event.getSceneY());
                l.setEndX(tmpX + 100 + event.getSceneX());
                l.setEndY(tmpY + event.getSceneY());
            }
            if (event.getSource() instanceof Rectangle) {
                Rectangle r = (Rectangle) (event.getSource());

                r.setX(tmpX + event.getSceneX());
                r.setY(tmpY + event.getSceneY());
            }
        }
    };

    EventHandler<MouseEvent> mouseReleased = event -> {
    };

    @FXML
    public void initialize()
    {
        select.setToggleGroup(tg);
        ellipse.setToggleGroup(tg);
        rectangle.setToggleGroup(tg);
        line.setToggleGroup(tg);
    }

    public void SelectRadio(ActionEvent event) {
        delete.setDisable(false);
        clone.setDisable(false);
    }

    public void EllipseRadio(ActionEvent event) {
        delete.setDisable(true);
        clone.setDisable(true);
    }

    public void RectangleRadio(ActionEvent event) {
        delete.setDisable(true);
        clone.setDisable(true);
    }

    public void LineRadio(ActionEvent event) {
        delete.setDisable(true);
        clone.setDisable(true);
    }

    public void DeleteButton(ActionEvent event) {
        root.getChildren().remove(object);
    }

    public void CloneButton(ActionEvent event) {
        if (object instanceof Ellipse)
        {
            Ellipse e = new Ellipse(((Ellipse) object).getCenterX()+70,
                                    ((Ellipse) object).getCenterY()+70,
                                            ((Ellipse) object).getRadiusX(),
                                            ((Ellipse) object).getRadiusY());
            e.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
            makeDraggable(e);
            e.setFill(((Ellipse) object).getFill());
            root.getChildren().add(e);
        }
        if (object instanceof Rectangle)
        {
            Rectangle r = new Rectangle(((Rectangle) object).getX(),
                                        ((Rectangle) object).getY()+60,
                                        ((Rectangle) object).getWidth(),
                                        ((Rectangle) object).getHeight());
            r.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
            makeDraggable(r);
            r.setFill(((Rectangle)object).getFill());
            root.getChildren().add(r);
        }
        if (object instanceof Line)
        {
            Line l = new Line(((Line)object).getStartX(),
                        ((Line)object).getStartY()+20,
                              ((Line)object).getEndX(),
                        ((Line)object).getEndY()+20);
            l.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
            makeDraggable(l);
            l.setStroke(((Line)object).getStroke());
            l.setStrokeWidth(5);
            root.getChildren().add(l);
        }
    }

    public void draw(MouseEvent e)
    {
        Shape shape;
        if (ellipse.isSelected())
        {
            shape = new Ellipse(e.getX(), e.getY(), 50, 50);
            shape.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
            makeDraggable(shape);
            shape.setFill(Color.ROYALBLUE);
            root.getChildren().add(shape);
        }
        if (line.isSelected())
        {
            shape = new Line(e.getX(), e.getY(), e.getX()+100, e.getY());
            shape.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
            makeDraggable(shape);
            shape.setStroke(Color.ROYALBLUE);
            shape.setStrokeWidth(5);
            root.getChildren().add(shape);
        }
        if (rectangle.isSelected())
        {
            shape = new Rectangle(e.getX()-50, e.getY()-25, 100, 50);
            shape.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
            makeDraggable(shape);
            shape.setFill(Color.ROYALBLUE);
            root.getChildren().add(shape);
        }
    }

    public void changeColor(ActionEvent e)
    {
        if (object instanceof Ellipse)
        {
            ((Ellipse)object).setFill(color.getValue());
        }
        if (object instanceof Rectangle)
        {
            ((Rectangle)object).setFill(color.getValue());
        }
        if (object instanceof Line)
        {
            ((Line)object).setStroke(color.getValue());
        }
    }

    public Object getShape(Object o)
    {
        return o;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.initialize();

        // Create the sub-scene
        SubScene subscene = new SubScene(root, 640, 500, true, SceneAntialiasing.BALANCED);
        subscene.setFill(Color.LIGHTGREY);

        pane2D.getChildren().addAll(subscene);
        pane2D = new Pane(root);

    }
}
