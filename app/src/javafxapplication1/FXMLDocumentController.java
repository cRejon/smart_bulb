/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;


import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ResourceBundle;
import java.util.Random;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Paint;


/**
 *
 * @author administrador
 */
public class FXMLDocumentController implements Initializable {
    
    private String red = "0";
    private String green = "0";
    private String blue = "0";
    
    @FXML
    private Label label_red;
    @FXML
    private Label label_green;
    @FXML
    private Label label_blue;
    @FXML
    private Circle circle_RGB;
    @FXML
    private Label label_prediccion;
    
    @FXML
    private CheckBox cb_0_0_0 ;
    @FXML
    private CheckBox cb_0_0_127 ;
    @FXML
    private CheckBox cb_0_0_255 ;
    @FXML
    private CheckBox cb_0_127_0 ;
    @FXML
    private CheckBox cb_0_127_127 ;
    @FXML
    private CheckBox cb_0_127_255 ;
    @FXML
    private CheckBox cb_0_255_0 ;
    @FXML
    private CheckBox cb_0_255_127 ;
    @FXML
    private CheckBox cb_0_255_255 ;
    @FXML
    private CheckBox cb_127_0_0 ;
    @FXML
    private CheckBox cb_127_0_127 ;
    @FXML
    private CheckBox cb_127_0_255 ;
    @FXML
    private CheckBox cb_127_127_0 ;
    @FXML
    private CheckBox cb_127_127_127 ;
    @FXML
    private CheckBox cb_127_127_255 ;
    @FXML
    private CheckBox cb_127_255_0 ;
    @FXML
    private CheckBox cb_127_255_127 ;
    @FXML
    private CheckBox cb_127_255_255 ;
    @FXML
    private CheckBox cb_255_0_0 ;
    @FXML
    private CheckBox cb_255_0_127 ;
    @FXML
    private CheckBox cb_255_0_255 ;
    @FXML
    private CheckBox cb_255_127_0 ;
    @FXML
    private CheckBox cb_255_127_127 ;
    @FXML
    private CheckBox cb_255_127_255 ;
    @FXML
    private CheckBox cb_255_255_0 ;
    @FXML
    private CheckBox cb_255_255_127 ;
    @FXML
    private CheckBox cb_255_255_255 ;
    
    ArrayList<CheckBox> checkBoxList;
    
    protected void guardadoList(){
        checkBoxList.add(cb_0_0_0);
        checkBoxList.add(cb_0_0_127);
        checkBoxList.add(cb_0_0_255);
        checkBoxList.add(cb_0_127_0);
        checkBoxList.add(cb_0_127_127);
        checkBoxList.add(cb_0_127_255);
        checkBoxList.add(cb_0_255_0);
        checkBoxList.add(cb_0_255_127);
        checkBoxList.add(cb_0_255_255);
        checkBoxList.add(cb_127_0_0);
        checkBoxList.add(cb_127_0_127);
        checkBoxList.add(cb_127_0_255);
        checkBoxList.add(cb_127_127_0);
        checkBoxList.add(cb_127_127_127);
        checkBoxList.add(cb_127_127_255);
        checkBoxList.add(cb_127_255_0);
        checkBoxList.add(cb_127_255_127);
        checkBoxList.add(cb_127_255_255);
        checkBoxList.add(cb_255_0_0);
        checkBoxList.add(cb_255_0_127);
        checkBoxList.add(cb_255_0_255);
        checkBoxList.add(cb_255_127_0);
        checkBoxList.add(cb_255_127_127);
        checkBoxList.add(cb_255_127_255);
        checkBoxList.add(cb_255_255_0);
        checkBoxList.add(cb_255_255_127);
        checkBoxList.add(cb_255_255_255);
    }
    
    
    @FXML
    private void handleButtonEntrenar(ActionEvent event) {
        
        JSONObject json = new JSONObject();
        checkBoxList = new ArrayList<>();
        guardadoList();
      
        for(CheckBox checkBox : checkBoxList ){
            String[] partsId = checkBox.getId().split("_");
            String rgb = partsId[1]+"_"+partsId[2]+"_"+partsId[3];
            int seleccionado;
            if (checkBox.isSelected() == true){
                seleccionado = 1;
            }else{
                seleccionado = 0;
            }
            json.put(rgb, seleccionado);
        }
        System.out.println(json.toString());
        
        try{
            URL url = new URL ("http://localhost:8080/entrenamiento");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoOutput(true);
            con.setUseCaches(false); 
            con.connect(); 
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(json.toString());
            os.flush();
            // es necesario leer la respuesta aunque no se use
            BufferedReader in = new BufferedReader(
                    new InputStreamReader (con.getInputStream()));
            String result = in.readLine();
            os.close();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    private void handleButtonRamdon(ActionEvent event) {
        
        label_prediccion.setText("");
        
        Random rand = new Random();
        red = Integer.toString(rand.nextInt(256));
        green = Integer.toString(rand.nextInt(256));
        blue = Integer.toString(rand.nextInt(256));
        
        label_red.setText(red);
        label_green.setText(green);
        label_blue.setText(blue);
        
        circle_RGB.setFill(Paint.valueOf("rgb("+red+","+green+","+blue+")"));
        circle_RGB.setVisible(true);
        
    }
    
    @FXML
    private void handleButtonPredecir(ActionEvent event) {
        try{
            URL url = new URL ("http://localhost:8080/prediccion?red="
                                        +red+"&green="+green+"&blue="+blue);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader (con.getInputStream()));
            String result = in.readLine();
            if (result.equals("0")){
                label_prediccion.setText("El color NO es del gusto del usuario. "
                                            + "Pruebe con un nuevo color");
            }else{
                label_prediccion.setText("El color SÍ es del gusto del usuario");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
