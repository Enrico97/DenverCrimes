/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.model.Event;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<String> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	String[] v = boxArco.getValue().split(":");
    	String v1 = v[0].substring(1).trim().toString();
    	String v2 = v[1].substring(0, v[1].length()-1).trim().toString();
    	txtResult.appendText(model.trovaPercorso(v1, v2).toString());
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	model.creaGrafo(boxCategoria.getValue(), boxMese.getValue());
    	int archi=model.getGrafo().edgeSet().size();
    	double somma = 0;
    	for (DefaultWeightedEdge e : model.getGrafo().edgeSet())
    		somma+=model.getGrafo().getEdgeWeight(e);
    	double media=somma/archi;
    	for (DefaultWeightedEdge e : model.getGrafo().edgeSet()) {
    		if(model.getGrafo().getEdgeWeight(e)>media)
    			txtResult.appendText(e.toString()+model.getGrafo().getEdgeWeight(e)+"\n");
    	}
    	for (DefaultWeightedEdge e : model.getGrafo().edgeSet())
    		boxArco.getItems().add(e.toString());

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxCategoria.getItems().addAll(model.categorie());
    	for (int i=1; i<=12; i++)	
    		this.boxMese.getItems().add(i);
    	
    }
}
