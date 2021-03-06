package ar.edu.unq.epers.bichomon.backend.model.experiencia;

import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Experiencia es una clase que representa una estructura compuesta por una base de experiencia
 * que es un número, y una lista de nieveles, sobre la cual obtendran acceso otros componentes.
 * Será utilizada por una instancia de {@link ExpHandler}.
 * 
 * @author santiago
 */
@Entity
public class Experiencia {

	@Id
	private String version; 
	private Double baseExp;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Level> levelList;
	
	public Experiencia() {}

	public Experiencia(Double baseExp, List<Level> levelList) {
		
		this.setBaseExp(baseExp);
		this.levelList = levelList;
		this.listToMap();
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public Double getBaseExp() {
		return baseExp;
	}

	public void setBaseExp(Double baseExp) {
		this.baseExp = baseExp;
	}

	public void setLevelList(List<Level> levelList) {
		this.levelList = levelList;
	}
	
	/**
	 * Dado un número que denota un nivel se espera obtener un valor de un HashMap multiplicado
	 * por un coeficiente que representa la experiencia base, que no varía mientras la 
	 * instancia {@link Experiencia} viva. 
	 * @param lvl - un Integer
	 * @return el valor de un level multiplicado por la experiencia base
	 */
	public Double getExpByLvl(Integer lvl) {
		return this.listToMap().get(lvl) * this.baseExp;
	}
	
	public Level getLevelByNumber(Integer n) {
		return this.levelList.get(n-1);
	}
	
	/**
	 * Convierte la los valores del las instancias de {@link Level} de una lista en un HashMap, donde
	 * el nivel de un {@link Level} es clave y el coeficiente un valor. 
	 */
	public HashMap<Integer,Double> listToMap() {
		HashMap<Integer,Double> levels = new HashMap<>();
		for(Level level:levelList) {
			levels.put(level.getNivel(), level.getCoeficienteExp());
		}
		return levels;
	}
	
}
