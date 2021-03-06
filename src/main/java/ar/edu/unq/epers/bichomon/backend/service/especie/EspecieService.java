package ar.edu.unq.epers.bichomon.backend.service.especie;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

/**
 * EspecieService es una interfaz provista por los encargados frontend
 * para obtener servicios sobre las distintas especies del juego
 * 
 * @author santiago
 */
public interface EspecieService {
	
	/**
	 * Este método será utilizado por una interfaz de administración para crear nuevas
	 * especies de bichos. Recibe por parametro un objeto {@link Especie} previamente
	 * construido y se encarga de persistirlo en la base de datos.  Tener en cuenta que
	 * el nombre de cada especie debe ser único para toda la aplicación.
	 * 
	 * @param especie - un objeto Especie previamente construido por la gente de frontend
	 */
	void crearEspecie(Especie especie);
	
	/**
	 * Este método devolverá la {@link Especie} cuyo nombre sea igual al provisto por
	 * parámetro.
	 * 
	 * Se espera que este método devuelva, a lo sumo, un solo resultado.
	 * 
	 * @param nombreEspecie - el nombre de la especie que se busca
	 * @return la especie encontrada
	 * @throws la excepción {@link EspecieNoExistente} (no chequeada)
	 */
	Especie getEspecie(String nombreEspecie);

	/**
	 * @return una lista de todas los objetos {@link Especie} existentes ordenados
	 * alfabéticamente por su nombre en forma ascendente
	 */
	List<Especie> getAllEspecies();

	/**
	 * Crea un nuevo Bicho perteneciente a la especie especificada. El nuevo objeto Bicho no es
	 * persistido (de momento), solo devuelto.
	 * 
	 * Para llevar una mejor estadística de los bichos que han sido creados cada objeto
	 * {@link Especie} cuenta con un contador cantidadBichos. El mismo deberá ser incrementado
	 * en 1.
	 * 
	 * @param nombreEspecie - el nombre de la especie del bicho a crear
	 * @param nombreBicho - el nombre del bicho a ser creado
	 * @return un objeto {@link Bicho} instanciado
	 */
	Bicho crearBicho(String nombreEspecie, String nombreBicho);

	/** 
	 * 
	 * @return retorna la las 10 especie mas populares de bichomon go.
	 */
	public List<Especie> populares();
//	
//	/**
//	 * TODO
//	 * @return
//	 */
//	public List<Especie> impopulares();
}
