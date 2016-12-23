/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw6_nt;

import javafx.animation.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author Nathen
 */
public class HW6_NT extends Application {
    
    BorderPane layout;
    GridPane grid;
    AnchorPane canHolder;
    Stage stage;
    Scene scene;
    MenuBar menu;
    Menu app;
    MenuItem itmExit;
    Label lbl1, lbl2, lbl3, lbl4;
    TextField textBox;
    Color setFiller, setStrokeC;
    Ellipse newElli;
    float setStrokeSize;
    String sSize;
    int iSize;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        //Creates Menu Bar
        menu = new MenuBar();
        
        //Creates Menu
        app = new Menu("Menu");
        
        //Creates Menu Items
        itmExit = new MenuItem("    Exit, Esc    ");
        
        //Adds Menu Items to Menu and Menu to Menu Bar
        app.getItems().addAll(itmExit);
        menu.getMenus().add(app);
        
        //Creates new grid
        grid = new GridPane();
        grid.setPrefWidth(210);
        grid.setVgap(5);
        grid.setHgap(10);
        

        Slider slider = new Slider();
        slider.setMin(5);
        slider.setMax(50);
        slider.setValue(5);
        slider.setMaxWidth(70);
        
        Tooltip tp = new Tooltip();
        sSize = Integer.toString((int)slider.getValue());
        iSize = (int)slider.getValue();
        tp.setText(sSize);
        slider.setTooltip(tp);
        
        
        //Creates all color blocks with Rectangle objects
        Rectangle red = new Rectangle(20, 10, Color.RED);
        Rectangle white = new Rectangle(20, 10, Color.WHITE);
        Rectangle gray = new Rectangle(20, 10, Color.GRAY);
        Rectangle black= new Rectangle(20, 10, Color.BLACK);
        Rectangle yellow = new Rectangle(20, 10, Color.YELLOW);
        Rectangle orange = new Rectangle(20, 10, Color.ORANGE);
        Rectangle green = new Rectangle(20, 10, Color.GREEN);
        Rectangle blue = new Rectangle(20, 10, Color.BLUE);
        Rectangle violet = new Rectangle(20, 10, Color.VIOLET);
        Rectangle pink = new Rectangle(20, 10, Color.PINK);
        
        //Call back for Cell Factory of filler and stroke color
        //Displays rectangle with according colors in a specific cell
        Callback cbc = new Callback<ListView<Rectangle>, ListCell<Rectangle>>(){
            @Override
            public ListCell<Rectangle> call(ListView<Rectangle> param) {
                return new ListCell<Rectangle>(){
                    
                    //Default Rectangle
                    private final Rectangle rec;
                    {
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        rec = new Rectangle (20, 10);
                    }
                    
                    @Override
                    public void updateItem(Rectangle itm, boolean empty){
                        super.updateItem(itm, empty);
                        
                        if(itm == null || empty){
                            setGraphic(null);
                            
                        //Sets color of rectangle and displays graphics
                        }else{
                            rec.setFill(itm.getFill());
                            setGraphic(rec);
                        }
                    }
                };
            }
        };
        
        //Creates Fill ComboBox and sets Cell Factory
        ComboBox <Rectangle> fill = new ComboBox<>();
        fill.getItems().addAll(red, white, gray, black, yellow, orange, green, blue, violet, pink);
        fill.getSelectionModel().selectFirst();
        fill.setCellFactory(cbc);
        
        //Creates OutLine ComboBox for the stroke color and sets Cell Factory
        ComboBox <Rectangle> outLine = new ComboBox <>();
        outLine.getItems().addAll(black, red, white, gray, yellow, orange, green, blue, violet, pink);
        outLine.getSelectionModel().selectFirst();
        outLine.setCellFactory(cbc);
        
        
        
        //Creates textfield for stroke size
        textBox = new TextField("1");
        textBox.setPrefWidth(4);
        
        //Creates labels
        lbl1 = new Label("Size");
        lbl2 = new Label("Fill Color");
        lbl3 = new Label("Stroke Thickness");
        lbl4 = new Label("Stroke Color");
        
        //Adds labels to the grid
        grid.add(lbl1, 1, 0);
        grid.add(lbl2, 1, 1);
        grid.add(lbl3, 1, 2);
        grid.add(lbl4, 1, 3);
        
        //Adds ComboBoxs and TextField to grid
        grid.add(slider, 2, 0);
        grid.add(fill, 2, 1);
        grid.add(textBox, 2, 2);
        grid.add(outLine, 2, 3);
        
        //Creates AnchorPane to act as a canvas      
        canHolder = new AnchorPane();
        canHolder.setStyle("-fx-background-color: White; -fx-border-color: Black;");
        
        //Adds Creates BorderPane and adds MenuBar, Grid and Anchor to the pane
        layout = new BorderPane();
        layout.setTop(menu);
        layout.setLeft(grid);
        layout.setCenter(canHolder);
        
        
        scene = new Scene(layout, 650, 400);
        
        stage.setTitle("MainWindow");
        stage.setScene(scene);
        stage.show();
        
        slider.setOnMouseDragged(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent e){
                sSize = Integer.toString((int)slider.getValue());
                iSize = (int)slider.getValue();
                tp.setText(sSize);
            }
        });
        
        canHolder.setOnMousePressed(new EventHandler <MouseEvent>(){
            public void handle(MouseEvent me){
                
                //Checks to make sure input value is positive
                if(Integer.parseInt(textBox.getText()) < 0){
                    textBox.setText("0");
                }
                
                //Initializes starting point and gets values selected from user.
                double x_s = me.getX();
                double y_s = me.getY();
                setFiller = (Color) fill.getValue().getFill();
                setStrokeC = (Color) outLine.getValue().getFill();
                setStrokeSize = Integer.parseInt(textBox.getText());
                
                Circle circle = new Circle();
                circle.setCenterX(x_s);
                circle.setCenterY(y_s);
                circle.setRadius(2.5);
                circle.setFill(setFiller);
                circle.setStroke(setStrokeC);
                circle.setStrokeWidth(setStrokeSize/iSize);
                
                
                
                FadeTransition ft = new FadeTransition();
                    ft.setNode(circle);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.setCycleCount(2);
                    ft.setAutoReverse(true);
                    ft.setDuration(Duration.millis(500));
                
                ScaleTransition st = new ScaleTransition();
                    st.setNode(circle);
                    st.setFromX(0);
                    st.setFromY(0);
                    st.setToX(iSize);
                    st.setToY(iSize);
                    st.setCycleCount(2);
                    st.setAutoReverse(true);
                    st.setDuration(Duration.millis(500));
                    
                    
                canHolder.getChildren().add(circle);
                
                ft.play();
                st.play();
  
                

            }
        });
     
        
        //Menu item closes window when clicked
        itmExit.setOnAction(new EventHandler(){
            public void handle(Event event){
                Platform.exit();
            }
        });        
        
        //Hot Key commands to reset and exit program with keyboard
        KeyCodeCombination key = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            
            //Exits program when Esc is pressed
            public void handle(KeyEvent e){
                if(e.getCode() == KeyCode.ESCAPE){
                    Platform.exit();
                }
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
