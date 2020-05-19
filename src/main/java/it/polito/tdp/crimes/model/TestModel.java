package it.polito.tdp.crimes.model;

public class TestModel {
	
	
	public static void main(String[] args) {
		Model model = new Model();
		model.creaGrafo("traffic-accident", 1);
	//	System.out.println("grafo con "+model.getGrafo().vertexSet().size()+" vertici e "+model.getGrafo().edgeSet().size()+" archi");
		System.out.println(model.trovaPercorso("traffic-accident", "traffic-accident-hit-and-run"));
	}
}
