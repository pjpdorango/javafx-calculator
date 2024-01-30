/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package javafxapplication1;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.text.Text;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author pj
 */
public class JavaFXApplication1 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        int windowX = 500, windowY = 400;
        
        Group root = new Group();
        Scene calculatorScene = new Scene(root, windowX, windowY);
        primaryStage.setTitle("Awesome sauce calculator");
        primaryStage.setScene(calculatorScene);

        Text title = new Text("Welcome to CALCULATOR!!!!");
        
        root.getChildren().add(title);
        
        VBox rowSet = new VBox(20);
        rowSet.setTranslateY(50);
        
        AnchorPane inputs = new AnchorPane();
        Text currentInput = new Text();
        Text result = new Text();
        
        root.getChildren().add(inputs);
        inputs.setPrefWidth(inputs.getParent().getBoundsInLocal().getWidth());
        inputs.getChildren().add(currentInput);
        inputs.getChildren().add(result);
        
        inputs.setTranslateY(30);
        
        AnchorPane.setRightAnchor(result, 5.0d);
        
        
        
        String[][] calculatorOptions = {
            {"1", "2", "3", "+"},
            {"4", "5", "6", "-"},
            {"7", "8", "9", "="},
            {"*", "/", "C", "AC"}
        };
        
        for (int i = 0; i < calculatorOptions.length; i++) {
            HBox row = new HBox(20);
            
            for (int j = 0; j < calculatorOptions[i].length; j++) {
                Button optionButton = new Button(calculatorOptions[i][j]);
                optionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        String option = ((Button) e.getSource()).getText();
                        String currentText = currentInput.getText();
                        
                        if (option.equals("=")) {
                            if (!currentText.endsWith("=")) {
                                String resultNum = Float.toString(evaluate(currentText));
                            
                            if (resultNum.contains(".")) {
                                while (resultNum.endsWith("0")) {
                                    resultNum = resultNum.substring(0, resultNum.length() - 1);
                                }
                                
                                if (resultNum.endsWith(".")) {
                                    resultNum = resultNum.replace(".", "");
                                }
                            }
                            
                            result.setText(resultNum);
                            currentInput.setText(currentText + "=");
                            }
                        } else if (option.equals("AC")) {
                            currentInput.setText("");
                        } else if (option.equals("C")) {
                            currentInput.setText(currentText.substring(0, currentText.length() - 1));
                        } else {
                            if (currentText.endsWith("=")) {
                                currentText = "";
                            }
                            currentInput.setText(currentText + option);
                        }
                    }
                    
                    public float evaluate(String currentText) {
                        String[] regexList = {"+", "m", "*", "/"};
                        for (String regex : regexList) {
                            if (currentText.replaceAll("-", "m").contains(regex)) {
                                if (regex.equals("+")) { regex = "\\+"; }
                                
                                String[] splitText = currentText.replaceAll("-", "m").split(regex, 2);
                                float num1 = Float.parseFloat(splitText[0]);
                                System.out.println(splitText[1]);
                                try {
                                    float num2 = Float.parseFloat(splitText[1]);

                                    switch (regex) {
                                        case "\\+":
                                            return num1 + num2;
                                        case "m":
                                            return num1 - num2;
                                        case "*":
                                            return num1 * num2;
                                        case "/":
                                            return num1 / num2;
                                    }
                                } catch (NumberFormatException e) {
                                    evaluate(splitText[1]);
                                }
                            }
                        }
                        
                        return 0;
                    }
                });
                row.getChildren().add(optionButton);
            }
            
            rowSet.getChildren().add(row);
        }
        
        root.setTranslateY((windowY - root.getBoundsInLocal().getWidth()) / 2);
        root.setTranslateX((windowX - root.getBoundsInLocal().getWidth()) / 2);
        root.getChildren().add(rowSet);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
