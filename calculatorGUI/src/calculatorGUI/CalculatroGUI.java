package calculatorGUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.lang.Math;

/**
 * CalculatorGUI
 * Description: A GUI of a calculator and how it is used
 * GitURL: https://github.com/Brogoming/GUICalculator.git
 * 
 * @author kotag
 * @since 2022.04.22
 * @version 1.0 beta
 */
public class CalculatroGUI extends Application {

	/**
	 * display textfield
	 */
	private TextField tfDisplay;

	/**
	 * display textfield
	 */
	private Text tMemory;

	/**
	 * 24 buttons
	 */
	public static Button[] btns;
	
	/**
	 * sets up the root BorderPane
	 */
	public static BorderPane root = new BorderPane();

	/**
	 * Labels of 24 buttons
	 */
	public static String[] btnLabels = {  
			"Light", "Dark", "Kota", "Reg",
			"+M", "-M", "MC", "MR", 
			"PI", ".", "^X", "e^X",
			"7", "8", "9", "+",
			"4", "5", "6", "-",
			"1", "2", "3", "x",
			"C", "0", "=", "/",
	};

	/**
	 * Result of computation
	 */
	private double result = 0.0;  

	/**
	 * default of memory
	 */
	private double memory = 0.0;		

	/**
	 * Input number as String
	 */
	private String inStr = "0"; 

	/**
	 * Previous operator: ' '(nothing), '+', '-', '*', '/', '='
	 */
	private char lastOperator = ' ';

	/**
	 * Event handler for all the 24 Buttons 
	 */
	EventHandler<ActionEvent> handler = evt -> {
		String currentBtnLabel = ((Button)evt.getSource()).getText();
		switch (currentBtnLabel) {
		// Number buttons
		case "0": case "1": case "2": case "3": case "4":
		case "5": case "6": case "7": case "8": case "9":
		case ".":
			if (inStr.equals("0")) {
				inStr = currentBtnLabel;  // no leading zero
			} else {
				inStr += currentBtnLabel; // append input digit
			}
			tfDisplay.setText(inStr);
			// Clear buffer if last operator is '='
			if (lastOperator == '='){
				result = 0;
				lastOperator = ' ';
			}
			break;

			// Operator buttons: '+', '-', 'x', '/', '=', 'X', 'P', 'E'
		case "+":
			compute();
			lastOperator = '+';
			break;
		case "-":
			compute();
			lastOperator = '-';
			break;
		case "x":
			compute();
			lastOperator = '*';
			break;
		case "/":
			compute();
			lastOperator = '/';
			break;
		case "=":
			compute();
			lastOperator = '=';
			break;
		case "^X":
			compute();
			lastOperator = 'X';
			break;
		case "e^X":
			compute();
			lastOperator = 'E';
			break;
		case "PI":
			lastOperator = 'P';
			compute();
			break;

		case "+M":
			memory += result;
			tMemory.setText("Memory = " + memory);
			break;
		case "-M":
			memory -= result;
			tMemory.setText("Memory = " + memory);
			break;
		case "MR":
			inStr = String.valueOf(memory);
			tfDisplay.setText(memory + "");
			break;

			//Clears the memory value
		case "MC":
			memory = 0.0; 
			tMemory.setText("Memory = " + memory);
			break; 

			// Clear button
		case "C":
			result = 0;
			inStr = "0";
			lastOperator = ' ';
			tfDisplay.setText("0");
			break;

			//changes the colors of the calculator when pressed
		case "Light":
			for (int i = 0; i < btns.length; ++i) {
				switch(btnLabels[i]) {
				case "+M": case "-M": case "MC": case "MR":
					btns[i].setStyle("-fx-color: #BBDCDB");
					break;
				case "0": case "1": case "2": case "3": case "4":
				case "5": case "6": case "7": case "8": case "9":
					btns[i].setStyle("-fx-color: #BBCCDC");
					break;
				case "PI": case".": case"^X": case"e^X":
				case "+": case "-": case "x": case "/":
				case "C": case "=":
					btns[i].setStyle("-fx-color: #BBDCCB");
					break;
				}
				root.setStyle("-fx-background-color: white");
			}
			break;
		case "Dark":
			for (int i = 0; i < btns.length; ++i) {
				switch(btnLabels[i]) {
				case "+M": case "-M": case "MC": case "MR":
					btns[i].setStyle("-fx-color: #234343");
					break;
				case "0": case "1": case "2": case "3": case "4":
				case "5": case "6": case "7": case "8": case "9":
					btns[i].setStyle("-fx-color: #233443");
					break;
				case "PI": case".": case"^X": case"e^X":
				case "+": case "-": case "x": case "/":
				case "C": case "=":
					btns[i].setStyle("-fx-color: #234332");
					break;
				}
				root.setStyle("-fx-background-color: gray");
			}
			break;
		case "Kota":
			for (int i = 0; i < btns.length; ++i) {
				switch(btnLabels[i]) {
				case "+M": case "-M": case "MC": case "MR":
					btns[i].setStyle("-fx-color: #008006");
					break;
				case "0": case "1": case "2": case "3": case "4":
				case "5": case "6": case "7": case "8": case "9":
					btns[i].setStyle("-fx-color: #800600");
					break;
				case "PI": case".": case"^X": case"e^X":
				case "+": case "-": case "x": case "/":
				case "C": case "=":
					btns[i].setStyle("-fx-color: #060080");
					break;
				}
				root.setStyle("-fx-background-color: #807A00");
			}
			break;
		case "Reg":
			Reg();
			break;
		}
	};

	/**
	 * User pushes '+', '-', '*', '/', '=' 'X', 'P', and 'E' button.
	 * Perform computation on the previous result and the current input number,
	 * based on the previous operator.
	 */
	private void compute() {
		double inNum = Double.parseDouble(inStr);
		inStr = "0";
		if (lastOperator == ' ') {
			result = inNum;
		} else if (lastOperator == '+') {
			result += inNum;
		} else if (lastOperator == '-') {
			result -= inNum;
		} else if (lastOperator == '*') {
			result *= inNum;
		} else if (lastOperator == '/') {
			result /= inNum;
		} else if (lastOperator == '=') {
			// Keep the result for the next operation
		} else if (lastOperator == 'P') { //the number PI
			result = Math.PI;
		} else if (lastOperator == 'X') { //result to the power of X
			result = Math.pow(result, inNum);
		} else if (lastOperator == 'E') { //e to the power of X
			result = Math.exp(inNum);
		}

		tfDisplay.setText(result + "");
	}

	/**
	 * Setup the UI
	 */
	@Override
	public void start(Stage primaryStage) {
		// Setup the Display TextField
		tfDisplay = new TextField("0");
		tfDisplay.setEditable(false);
		tfDisplay.setAlignment(Pos.CENTER_RIGHT);

		tMemory = new Text("Memory = 0.0");
		tMemory.setTextAlignment(TextAlignment.LEFT);

		// Setup a GridPane for 4x6 column Buttons
		int numCols = 4;
		int numRows = 7;
		GridPane paneButton = new GridPane();
		paneButton.setPadding(new Insets(15, 0, 15, 0));  // top, right, bottom, left
		paneButton.setVgap(5);  // Vertical gap between nodes
		paneButton.setHgap(5);  // Horizontal gap between nodes
		// Setup 4 columns of equal width, fill parent
		ColumnConstraints[] columns = new ColumnConstraints[numCols];
		for (int i = 0; i < numCols; ++i) {
			columns[i] = new ColumnConstraints();
			columns[i].setHgrow(Priority.ALWAYS) ;  // Allow column to grow
			columns[i].setFillWidth(true);  // Ask nodes to fill space for column
			paneButton.getColumnConstraints().add(columns[i]);
		}
		// Setup 7 columns of equal height, fill parent
		RowConstraints[] rows = new RowConstraints[numRows];
		for (int i = 0; i < numRows; ++i) {
			rows[i] = new RowConstraints();
			rows[i].setVgrow(Priority.ALWAYS) ;  // Allow column to grow
			rows[i].setFillHeight(true);  // Ask nodes to fill space for column
			paneButton.getRowConstraints().add(rows[i]);
		}

		// Setup 4x7 Buttons and add to GridPane; and event handler
		btns = new Button[numRows * numCols];
		for (int i = 0; i < btns.length; ++i) {
			btns[i] = new Button(btnLabels[i]);
			btns[i].setOnAction(handler);  // Register event handler
			btns[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);  // full-width
			paneButton.add(btns[i], i % numCols, i / numCols);  // control, col, row
		}
		
		btns[0].setStyle("-fx-color: white");
		btns[1].setStyle("-fx-color: black");
		btns[2].setStyle("-fx-color: red");
		btns[3].setStyle("-fx-color: blue");
		Reg(); //calls this method at the default

		// Setup up the scene graph rooted at a BorderPane (of 5 zones)
		
		root.setPadding(new Insets(15, 15, 15, 15));  // top, right, bottom, left
		root.setTop(tfDisplay);     // Top zone contains the TextField
		root.setCenter(paneButton); // Center zone contains the GridPane of Buttons
		root.setBottom(tMemory); // Bottom zone contains the Label

		// Set up scene and stage
		Scene scene = new Scene(root, 300, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("JavaFX Calculator");
		primaryStage.show();
	}

	/**
	 * Sets the button colors to regular colors
	 */
	private static void Reg() {
		for (int i = 0; i < btns.length; ++i) {
			switch(btnLabels[i]) {
			case "+M": case "-M": case "MC": case "MR":
				btns[i].setStyle("-fx-color: #00A1A4");
				break;
			case "0": case "1": case "2": case "3": case "4":
			case "5": case "6": case "7": case "8": case "9":
				btns[i].setStyle("-fx-color: #004FA4");
				break;
			case "PI": case".": case"^X": case"e^X":
			case "+": case "-": case "x": case "/":
			case "C": case "=":
				btns[i].setStyle("-fx-color: #00A455");
				break;
			}
		}
		root.setStyle("-fx-background-color: #B7C2BD");
	}

	/**
	 * calls the start method to show the GUI
	 * 
	 * @param args No command line input args are used for this application
	 */
	public static void main(String[] args) {
		launch(args);
	}
}