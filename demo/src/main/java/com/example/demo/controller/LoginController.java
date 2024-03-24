package com.example.demo.controller;

import com.example.demo.dto.OperatorCredentialsDto;
import com.example.demo.factory.PopupFactory;
import com.example.demo.rest.Authenticator;
import com.example.demo.rest.AuthenticatorImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private static final String APP_FXML = "/com/example/demo/app.fxml";
    private static final String APP_TITLE="Chris System";
    private PopupFactory popupFactory;
    private Authenticator authenticator;

    @FXML
    private Button exitButton;
    @FXML
    private Button loginButton;
    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    public LoginController(){
        popupFactory = new PopupFactory();
        authenticator= new AuthenticatorImpl();
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
        initializeLoginButton();
    }

    private void initializeLoginButton() {
        loginButton.setOnAction((x)->{
            performAuthentication();
        });
    }

    private void performAuthentication() {
        Stage waintingPopup = popupFactory.createWaintingPopup("Connecting to the server...");
        waintingPopup.show();
        String login = loginTextField.getText();
        String password = passwordTextField.getText();
        OperatorCredentialsDto dto = new OperatorCredentialsDto();
        dto.setLogin(login);
        dto.setPassword(password);
        authenticator.authenticate(dto,(authenticationResult)->{
            Platform.runLater(()->{
                waintingPopup.close();
                //System.out.println("authenticationResult: "+authenticationResult.isAuthenticated()+", authenticationResult : "+authenticationResult.toString());
                if(authenticationResult.isAuthenticated()){//jeżeli uzytkownik jest uwierzytelniny to otwieramy nowe okno
                    try {
                        openApp();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{//jezeli nie jest uwierzytelniny pojawi sie stosowny komunikat
                    incorrectMessage();
                }
            });
        });
    }

    private void incorrectMessage() {
        //TODO
        System.out.println("Niepoprawne dane");
    }

    private void openApp() throws IOException {
        Stage appStage = new Stage();
        Parent appRoot = null; //do tej referencji przypiszemy wczytany login.fxml
        try {
           appRoot= FXMLLoader.load(getClass().getResource(APP_FXML));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(appRoot,1024,768);
        appStage.setTitle(APP_TITLE);
        appStage.setScene(scene);
        appStage.show();
        getStage().close();//zamkniecie login.fxml

    }

    private void initializeExitButton() {
        exitButton.setOnAction((x)->{
            getStage().close();
        });
    }


    private Stage getStage(){
        return (Stage) loginAnchorPane.getScene().getWindow();
    }
}
