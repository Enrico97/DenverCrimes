package it.polito.tdp.crimes.db;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public double Peso (String s1, String s2, String categoria, int mese) {
		String sql = "Select COUNT(DISTINCT (f1.neighborhood_id)) as peso FROM events f1, events f2 WHERE f1.neighborhood_id = f2.neighborhood_id and f1.offense_category_id=? and f2.offense_category_id=? and MONTH(f1.reported_date)=? and MONTH(f2.reported_date)=? and f1.offense_type_id != f2.offense_type_id and f1.offense_type_id =? and f2.offense_type_id =?" ;
		int peso=-1;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setString(1, categoria);
			st.setString(2, categoria);
			st.setInt(3, mese);
			st.setInt(4, mese);
			st.setString(5, s1);
			st.setString(6, s2);
			
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				
					peso = res.getInt("peso");
			}
			
			conn.close();
			return peso;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1 ;
		}
	}
}
