package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	private Graph<String, DefaultWeightedEdge> grafo;
	private List <String> best;
	EventsDao dao = new EventsDao();
	
	public List<Event> getEventi () {
		return dao.listAllEvents();
	}
	
	public List<String> categorie () {
		List<String> categorie = new ArrayList<>();
		for (Event e : dao.listAllEvents()) {
			if (!categorie.contains(e.getOffense_category_id()))
				categorie.add(e.getOffense_category_id());
		}
		return categorie;
	}
	
	public void creaGrafo (String categoria, int mese) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for (Event e : dao.listAllEvents()) {
			if (e.getOffense_category_id().compareTo(categoria)==0 && e.getReported_date().getMonthValue()==mese)
				grafo.addVertex(e.getOffense_type_id());
		}
		for (String s1 : grafo.vertexSet()) {
			for (String s2 : grafo.vertexSet()) {
				if (this.grafo.getEdge(s1, s2)==null) {
						if (dao.Peso(s1, s2, categoria, mese)>0 && s1.compareTo(s2)!=0)
							Graphs.addEdge(this.grafo, s1, s2, dao.Peso(s1, s2, categoria, mese));
			}
			} 
		}
	}
	
	public Graph <String, DefaultWeightedEdge> getGrafo () {
		return this.grafo;
	}
	
	public List<String> trovaPercorso (String v1, String v2) {
		List<String> visitati = new ArrayList<>();
		best = new ArrayList<>();
		visitati.add(v1);
		cerca (v2, visitati);
		return best;
	}
	
	public void cerca (String v2, List<String> visitati) {
		if (visitati.size()>best.size()) {
			best.clear();
			best.addAll(visitati);
		}
		if (visitati.get(visitati.size()-1).equals(v2))
			return;
		
		for (String s1 : Graphs.neighborListOf(grafo, visitati.get(visitati.size()-1))) {
			if (!visitati.contains(s1)) {
				visitati.add(s1);
				cerca (v2, visitati);
				visitati.remove(visitati.size()-1);
			}
		}
	}
}
