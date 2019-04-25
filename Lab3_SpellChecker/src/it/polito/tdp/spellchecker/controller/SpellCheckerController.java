package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.*;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {
	
	private Dictionary dizionario;
	private List<String> inputTextList;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> languageBox;

    @FXML
    private TextArea txtDaCorreggere;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private TextArea txtCorretto;

    @FXML
    private Label errorLabel;

    @FXML
    private Button btnClearText;

    @FXML
    private Label timeLabel;

    @FXML
    void doActivation(ActionEvent event) {
    	
    	dizionario = new Dictionary();
    	
    	dizionario.loadDictionary(languageBox.getValue());
    	if(txtDaCorreggere.isDisabled()) 
    		txtDaCorreggere.setText("");
    	txtDaCorreggere.setDisable(false);
    	btnSpellCheck.setDisable(false);
    	txtCorretto.setText("");
    	txtCorretto.setDisable(true);
    	errorLabel.setText("");
    	timeLabel.setText("");
    	
    }
    
    
    @FXML
    void doClearText(ActionEvent event) {
    	
    	txtDaCorreggere.clear();
    	txtCorretto.clear();
    	errorLabel.setText("");
    	timeLabel.setText("");
    	
    	txtCorretto.setDisable(true);
    	btnClearText.setDisable(true);
    	txtDaCorreggere.setDisable(false);
    	
    }
    

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	txtDaCorreggere.setDisable(true);
    	txtCorretto.setDisable(false);
    	btnClearText.setDisable(false);
    	
    	String inputText = txtDaCorreggere.getText();
    	if(inputText.isEmpty()) {
    		txtDaCorreggere.appendText("Inserire un testo!\n");
    		return ;
    	}
    	
    	inputTextList = new ArrayList<String>();
    	
    	// Si rende il testo eseguibile dal programma
    	inputText = inputText.replaceAll("\n", " ");
    	inputText = inputText.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]]", "");
    	
    	StringTokenizer st = new StringTokenizer(inputText, " ");
    	while(st.hasMoreTokens())
    		inputTextList.add(st.nextToken());
    	
    	double t1 = System.nanoTime();
    	List<RichWord> outputWords = dizionario.spellCheckText(inputTextList);
    	double t2 = System.nanoTime();
    	
    	int errors = 0;
    	for(RichWord rw : outputWords) {
    		if(!rw.isCorrect()) {
    			txtCorretto.appendText(rw.getParola()+"\n");
    			++errors;
    		}
    	}
    	
    	timeLabel.setText("Spell check process time: "+(t2-t1)/1e6+" ms");
    	errorLabel.setText(errors+" error(s)");

    }

    @FXML
    void initialize() {
        assert languageBox != null : "fx:id=\"languageBox\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtDaCorreggere != null : "fx:id=\"txtDaCorreggere\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtCorretto != null : "fx:id=\"txtCorretto\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClearText != null : "fx:id=\"btnClearText\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert timeLabel != null : "fx:id=\"timeLabel\" was not injected: check your FXML file 'SpellChecker.fxml'.";

    }
    
    public void setModel(Dictionary model) {
    	
    	txtDaCorreggere.setDisable(true);
    	txtDaCorreggere.setText("Selezionare una lingua");
    	
    	txtCorretto.setDisable(true);
    	languageBox.getItems().addAll("English","Italian");
    	
    	btnSpellCheck.setDisable(true);
    	btnClearText.setDisable(true);
    	
    	errorLabel.setText("");
    	timeLabel.setText("");
    	
    	this.dizionario = model;
    	
    }
    
    
}
