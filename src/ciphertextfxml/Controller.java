/*
    CipherText
    Created by Dillon Esponda
    
    Encryption and decryption of input text with a given key/offset
    Implements encryption methods from seperate class files for each cipher
*/
package ciphertextfxml;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class Controller implements Initializable
{
    // fun fact! if you remove the @FXML tags, the fx:id never gets injected!
    // so the code actually breaks!

    // begin FXML 
    // variables
    @FXML
    private Button goButton;
    @FXML
    private TextArea inputTextArea;
    @FXML
    private TextArea outputTextArea;
    @FXML
    private CheckBox preserveSpacesCheckBox;
    @FXML
    private CheckBox preservePunctuationCheckBox;
    @FXML
    private ToggleButton encryptButton;
    @FXML
    private ToggleButton decryptButton;
    @FXML
    private ChoiceBox<String> encryptionMethodChoiceBox;
    @FXML
    private TextArea keyTextArea;
    @FXML
    private Label keyLabel;
    @FXML
    private Label warningLabel;
    @FXML
    private ToggleGroup group;
    // end FXML 
    // variables

    // edit this as new encryption methods are added.
    // be sure to also modify encrypt and decrypt!
    private final List<String> choiceBoxOptions = Arrays.asList(
	    "Caesar Cipher",
	    "Vigenere Cipher");
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	resetWarningLabel();

	// setup the encryption method choice box
	encryptionMethodChoiceBox.setItems(
		FXCollections.observableArrayList(choiceBoxOptions));

	// set default value for choice box
	encryptionMethodChoiceBox.setValue("Caesar Cipher");
    }

    @FXML
    private void goButtonPressed(ActionEvent event)
    {
	if (inputTextArea.getText().equals(""))
	{
	    showWarningLabel("Input Field is Empty!");
	    return;
	}
	if (!encryptButton.isSelected() && !decryptButton.isSelected())
	{
	    showWarningLabel("Must Select Decrypt or Encrypt!");
	    return;
	}
	validateKeyInput();
	if (keyTextArea.getText().equals(""))
	{
	    showWarningLabel("Please enter a valid key!");
	    return;
	}

	// now the magic happens!
	resetWarningLabel();
	prepareInputForOperation();

	if (encryptButton.isSelected())
	    encrypt();

	if (decryptButton.isSelected())
	    decrypt();
    }
    
    private void prepareInputForOperation()
    {
	String inputStr = inputTextArea.getText();
	
	inputStr = inputStr.toLowerCase();
	
	// remove numbers
	inputStr = inputStr.replaceAll("\\d","");
	
	if(!preserveSpacesCheckBox.isSelected())
	    inputStr = inputStr.replaceAll("\\s+", "");
	if(!preservePunctuationCheckBox.isSelected())
	    inputStr = inputStr.replaceAll("\\p{Punct}", "");
	 
	inputTextArea.setText(inputStr);
    }
    
    // this function takes the value in the KeyTextArea
    private void validateKeyInput()
    {
	String key = keyTextArea.getText().toLowerCase();
	
	// let's start by removing spaces
	key = key.replaceAll("\\s+", "");
	if (encryptionMethodChoiceBox.getValue().compareTo("Caesar Cipher") == 0)
	{
	    // try to convert key string value to int
	    // if conversion fails, we don't have a valid key
	    try
	    {
		key = Integer.toString(Math.abs(Integer.parseInt(key)));
	    }
	    catch(NumberFormatException e)
	    {
		key = "";
	    }
	}
	else
	{
	    // remove everything thats not a letter
	    key = key.replaceAll("\\p{Punct}|\\d", "");
	}
	
	keyTextArea.setText(key);
    }

    private void encrypt()
    {
	String output;
	switch (encryptionMethodChoiceBox.getValue())
	{
	    case "Caesar Cipher":
		output = CaesarCipher.encrypt(
			inputTextArea.getText(), 
			Integer.parseInt(keyTextArea.getText())
		);
		break;
	    case "Vigenere Cipher":
		output = VigenereCipher.encrypt(
			inputTextArea.getText(), 
			keyTextArea.getText());
		break;
	    default:
		System.out.println("unsupported encryption method found in decrypt: "
			+ encryptionMethodChoiceBox.getValue());
		return;
	}
	outputTextArea.setText(output);
    }
    
    private void decrypt()
    {
	String output;
	switch (encryptionMethodChoiceBox.getValue())
	{
	    case "Caesar Cipher":
		output = CaesarCipher.decrypt(
			inputTextArea.getText(), 
			Integer.parseInt(keyTextArea.getText())
		);
		break;
	    case "Vigenere Cipher":
		output = VigenereCipher.decrypt(
			inputTextArea.getText(), 
			keyTextArea.getText());
		break;
	    default:
		System.out.println("unsupported encryption method found in decrypt: "
			+ encryptionMethodChoiceBox.getValue());
		return;
	}
	outputTextArea.setText(output);
    }
     
    private void resetWarningLabel()
    {
	warningLabel.setText("");
	warningLabel.setVisible(false);
    }

    private void showWarningLabel(String warningText)
    {
	warningLabel.setText(warningText);
	warningLabel.setVisible(true);
    }
}
