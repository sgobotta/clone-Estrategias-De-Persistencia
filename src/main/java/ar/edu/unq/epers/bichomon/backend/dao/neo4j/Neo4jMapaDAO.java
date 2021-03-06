package ar.edu.unq.epers.bichomon.backend.dao.neo4j;

import java.util.List;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.CaminoCosto;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.mapa.UbicacionMuyLejana;

/**
 * MapaNeo4jDAO encapsula el acceso a Neo4j
 * 
 * @author santiago
 */
public class Neo4jMapaDAO {

	private Driver driver;
	
	public Neo4jMapaDAO() {
		this.driver = GraphDatabase.driver("bolt://localhost", AuthTokens.basic("neo4j", "root"));
	}
	
	/**
	 * Crea una entidad de grafo a partir de una {@link Ubicacion} pasada como parámetro
	 * @param ubicacion - una instancia de {@link Ubicacion}
	 */
	public void crearUbicacion(Ubicacion ubicacion) {
		Session session = this.driver.session();
		
		try {

			String query = "CREATE (u:Ubicacion { nombre: {nombreUbicacion} })"; 
			session.run(query, Values.parameters("nombreUbicacion", ubicacion.getNombre()));

		} finally {
			session.close();
		}
	}
	
	/**
	 * Crea un camino Unidireccional desde la primer ubicación pasado por parámetro
	 * hacia la segunda ubicación del tipo especificado. 
	 * @param ubicacion1 - un nombre de ubicación de origen
	 * @param ubicacion2 - un nombre de ubicacion de destino
	 * @param tipoCamino - un string indicando el tipo de camino
	 */
	public void conectar(String nombreUbicacion1, String nombreUbicacion2, String tipoCamino) {
		Session session = this.driver.session();
		
		try {
			
			String query = 	"MATCH (u1:Ubicacion { nombre: {nombreUbicacion1} }) " +
							"MATCH (u2:Ubicacion { nombre: {nombreUbicacion2} }) " +
							"MERGE (u1)-[:Camino { tipo: {tipoCamino}, costo: {costoCamino} }]->(u2)";
			
			session.run(query, Values.parameters(	"nombreUbicacion1", nombreUbicacion1,
													"nombreUbicacion2", nombreUbicacion2,
													"tipoCamino", tipoCamino,
													"costoCamino", CaminoCosto.valueOf(tipoCamino).getValue()));

		} finally {
			session.close();
		}
	}
	
	/**
	 * Dado el nombre de una ubicación se espera devolver todas aquellas ubicaciones
	 * conectadas directamente con la ubicación de nombre pasado por parámetro 
	 * @param nombreUbicacion - un string
	 * @return - una lista de {@link Ubicacion}
	 */
	public List<String> conectados(String nombreUbicacion) {
		Session session = this.driver.session();

		try {
			String query = 	"MATCH (u:Ubicacion { nombre: {nombreUbicacion}})" +
							"MATCH (n:Ubicacion)" +
							"MATCH (u)-[]->(n)" +
							"RETURN n";
			StatementResult result = session.run(query, Values.parameters("nombreUbicacion", nombreUbicacion));
			return result.list(record -> {
				Value ubic = record.get(0);
				String nuevaUbicacion = ubic.get("nombre").asString(); 
				return nuevaUbicacion;
			});
			
		} finally {
			session.close();
		}
	}
	
	/**
	 * Dado el nombre de una ubicación y un tipo de camino se espera devolver todas aquellas ubicaciones
	 * conectadas directamente con la ubicación de nombre pasado por parámetro a través del camino
	 * especificado. 
	 * @param nombreUbicacion - un string
	 * @param tipoCamino - un string
	 * @return - una lista de {@link Ubicacion}
	 */
	public List<String> conectados(String nombreUbicacion, String tipoCamino) {
		Session session = this.driver.session();

		try {
			String query = "MATCH (u:Ubicacion { nombre: {nombreUbicacion}})" +
					"MATCH (n:Ubicacion)" +
					"MATCH (u)-[c:Camino {tipo: {tipoCamino} } ]->(n)" +
					"RETURN n";
			StatementResult result = session.run(query, Values.parameters(	"tipoCamino", 		tipoCamino,
																			"nombreUbicacion", 	nombreUbicacion));
			return result.list(record -> {
				Value ubic = record.get(0);
				String nuevaUbicacion = ubic.get("nombre").asString(); 
				return nuevaUbicacion;
			});
			
		} finally {
			session.close();
		}

	}
	
	/**
	 * Dados dos nombres de {@link Ubicacion} que son lindantes, se obtiene el costo del camino que las conecta
	 * Se espera levantar una exepción en caso de que la ubicación de destino no sea lindante a la ubicación
	 * de origen.
	 * @param ubicOrigen - un string
	 * @param ubicDestino - un string
	 * @return Integer representando el costo
	 */
	public Integer getCostoLindantes(String ubicOrigen, String ubicDestino) {
		Session session = this.driver.session();
		try {
			String query = 	"MATCH (o:Ubicacion { nombre: {nombreOrigen} })" +
						 	"MATCH (d:Ubicacion { nombre: {nombreDestino} })" +
						 	"MATCH (o)-[r:Camino]->(d)" +
						 	"RETURN r.costo as costo";
			
			StatementResult result = session.run(query, Values.parameters("nombreOrigen",  ubicOrigen,
																		  "nombreDestino", ubicDestino));
			if(!result.hasNext()) {
				throw new UbicacionMuyLejana(ubicDestino);
			}
			else {
				return result.single().get("costo").asInt();
			}
		}
		finally {
			session.close();
		}
	}
	
	/**
	 * Dados dos nombres de {@link Ubicacion} se obtiene la suma del costo de todos los caminos que existen
	 * entre las dos ubicaciones.
	 * @param ubicOrigen - un string
	 * @param ubicDestino - un string
	 * @return Integer representando el costo total
	 */
	public Integer getCostoEntreUbicaciones(String ubicOrigen, String ubicDestino) {
		Session session = this.driver.session();
		try {
			String query = "MATCH (o:Ubicacion { nombre: {nombreOrigen} }) " +
						   "MATCH (d:Ubicacion { nombre: {nombreDestino} }) " +
						   "MATCH p = shortestPath((o)-[Camino*]->(d)) " +
						   "WITH REDUCE(costo = 0, camino in rels(p) | costo + toInt(camino.costo)) " +
						   "AS sumaCosto " +
						   "RETURN sumaCosto";
			
			StatementResult result = session.run(query, Values.parameters("nombreOrigen",  ubicOrigen,
																		   "nombreDestino", ubicDestino));
			return result.peek().get("sumaCosto").asInt();
		}
		finally {
			session.close();
		}
	}
	
	public void eliminarUbicaciones() {
		Session session = this.driver.session();
		try {
			String query = "MATCH (n:Ubicacion) DETACH DELETE n";
			
			session.run(query);
		}
		finally {
			session.close();
		}
	}
	
}
