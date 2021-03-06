package ar.edu.unq.epers.bichomon.backend.service.data;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

/**
 * Este servicio solo será utilizado para pruebas.
 * 
 * @author Steve Frontend
 */
public interface DataService {

	/**
	 *  Se espera que tras ejecutarse esto se elimine toda la información persistida
	 *  en la base de datos, de manera de poder comenzar el siguiente tests desde cero.
	 */
	void eliminarDatos();
	
	/**
	 * Crea un set de datos iniciales (de momento solo objetos {@link Especie}) para
	 * facilitar las pruebas de frontend.
	 */
	void crearSetDatosIniciales();

	/**
	 * Eliminar todas las tablas de una base de datos SQL
	 */
	void eliminarTablas();
	
	/**
	 * Crea un set de datos iniciales para test en Neo4j
	 */
	public void crearTestSetDeUbicacionesNeo4j();
	
	/**
	 * Crea un set de datos iniciales con datos de Hibernate
	 */
	public void crearSetDeUbicaciones();
	
	/**
	 * Elimina las ubicaciones de una base de datos en Neo4J
	 */
	public void eliminarUbicaciones();
}
