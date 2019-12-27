import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GUI extends Application  {
	
	public static void main(String[] args) {
        launch(args);
    }
	
	File selectedInputFile, selectedOutputFile;  	
	
	// Array to hold the String labels and radio buttons
	String [] selectionLabels = {"Create Concordance from File", "Create Concordance from Text"}; 
	RadioButton[] radioButtons;   
	
	Button createConcordanceButton;  
	Button selectInputButton;  			
	Button selectOutputButton;  
	Button clearButton;    
	Button exitButton;        
	TextArea displayTextArea;  
	
	
	ConcordanceDataManager object = new ConcordanceDataManager(); //Object of the data manager class
	
	
	public void start(Stage stage)
	{
		//define buttons
		createConcordanceButton = new Button("Create Concordance");		
		selectInputButton = new Button("Select Input File");		
		selectOutputButton = new Button("Select Output File");		
		clearButton = new Button("Clear");		
		exitButton = new Button("Exit");	
		
		// the text area
		displayTextArea = new TextArea();
		//displayTextArea.setPrefWidselectedInputFile, selectedOutputFileth(500);
		displayTextArea.setPrefHeight(200);
		displayTextArea.setPadding(new Insets(10,10,10,10));
		displayTextArea.setDisable(true);
	
		// HBox for the radio buttons 
		HBox concordanceRadioBox = new HBox(15);
		concordanceRadioBox.setAlignment(Pos.CENTER_LEFT);
		// set the toggleGroup  
		ToggleGroup groupRadio = new ToggleGroup();
		radioButtons = new RadioButton[selectionLabels.length];
		for( int i = 0; i < radioButtons.length; i++)
		{
			radioButtons[i] = new RadioButton(selectionLabels[i]);
			radioButtons[i].setToggleGroup(groupRadio);
			radioButtons[i].setOnAction(e-> ButtonClicked(e));
		}
		concordanceRadioBox.getChildren().addAll(radioButtons);
		
		TitledPane radioPane = new TitledPane("Please Select from Following Options:",concordanceRadioBox  );
		radioPane.setCollapsible(false);
		
		TitledPane displayTextPane = new TitledPane("Enter Text:",displayTextArea  );
		displayTextPane.setCollapsible(false);
		
        StackPane stack = new StackPane();
        stack.getChildren().addAll(displayTextPane);
                
		// Create a horizontal box for buttons
		HBox buttonPane = new HBox();
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setPadding(new Insets(5,10,5,10));
		// Add the buttons to the children of the buttonPane horizontal box
		buttonPane.getChildren().addAll(createConcordanceButton,selectInputButton,selectOutputButton,clearButton,exitButton);		
		 
		VBox contentPane = new VBox();
		contentPane.setAlignment(Pos.CENTER);
		contentPane.getChildren().addAll(radioPane,displayTextPane,buttonPane);
		
		// Sets buttons on action 
		createConcordanceButton.setOnAction(new CreateConcordanceEventHandler());
		selectInputButton.setOnAction(new SelectInputEventHandler());
		selectOutputButton.setOnAction(new SelectOutputEventHandler());
		clearButton.setOnAction(new ClearEventHandler());
		exitButton.setOnAction(new ExitEventHandler());
		       
		BorderPane displayPane = new BorderPane();
		displayPane.setCenter(contentPane);
		
		Scene scene = new Scene(displayPane);
		stage.setTitle("Concordance Generator");
		stage.setScene(scene);
		stage.show();	
	}
	
	public void ButtonClicked(ActionEvent e)
	{
		//create concordance from File clicked
        if (e.getSource() == radioButtons[0])
        {
			displayTextArea.setVisible(false);
			selectInputButton.setDisable(false);
			selectOutputButton.setDisable(false);
			displayTextArea.setDisable(true);	
			createConcordanceButton.setDisable(false);
        }
      //create concordance from text clicked
        else if (e.getSource() == radioButtons[1])
        {	
			selectInputButton.setDisable(true);
			selectOutputButton.setDisable(true);
			displayTextArea.setVisible(true);
			createConcordanceButton.setDisable(false);
			displayTextArea.setDisable(false);
        }
    }
	
	
	// Will ask the user to select a file 
	class SelectInputEventHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event)
		{			
			// FileChooser to display a window box allowing the user to choose
			JFileChooser fileChooser = new JFileChooser();
			
			int status = fileChooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION)
			{
				selectedInputFile = fileChooser.getSelectedFile();
			}	
			 
			if(selectedOutputFile != null)
			{
				createConcordanceButton.setDisable(false);
			}
		}
	}
	
	 
	class SelectOutputEventHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event)
		{
			// Will display a window box allowing the user to select an output file 
			JFileChooser fileChooser = new JFileChooser();
			
			int status = fileChooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION)
			{
				selectedOutputFile = fileChooser.getSelectedFile();
			}
		 
			if(selectedInputFile != null)
			{
				createConcordanceButton.setDisable(false);
			}
		}
	}
	
	public class CreateConcordanceEventHandler implements EventHandler<ActionEvent>
	{
			@Override
			public void handle(ActionEvent event)
			{
				String selectedPosition = null;
				
				for(RadioButton buttonChosen : radioButtons)
				{
					if(buttonChosen.isSelected())
					{
						selectedPosition = buttonChosen.getText();
					}	
				}
				
				if(selectedPosition.equals("Create Concordance from File"))
				{
					try {
							object.createConcordanceFile(selectedInputFile, selectedOutputFile);				
					}
					catch (FileNotFoundException e)
					{
						e.printStackTrace();
					}	
				}
				
				else if(selectedPosition.equals("Create Concordance from Text"))
				{
					ArrayList<String> data = new ArrayList<String>();				
					String textData = displayTextArea.getText();
					String concordanceText = "";
					
					data = object.createConcordanceArray(textData);
						
					for( int i = 0; i < data.size(); i++)
					{
						concordanceText += data.get(i);	
					}
					
					displayTextArea.setText(concordanceText);
				}
			}
		}
	 
	class ClearEventHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event)
		{
			displayTextArea.clear();		
		}
	}
	 
	class ExitEventHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event)
		{
			System.exit(0);		
		}
	}
}


