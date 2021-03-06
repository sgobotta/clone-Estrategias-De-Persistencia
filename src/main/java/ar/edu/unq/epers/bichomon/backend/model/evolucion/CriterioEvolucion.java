package ar.edu.unq.epers.bichomon.backend.model.evolucion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

/**
 * {@link CriterioEvolucion} es una clase que modela un criterio que se debe
 * cumplir para que una {@link Especie} pueda evolucionar.
 * Existen distintos tipos de criterios que se resuelven con un valor dado, de tipo Integer que 
 * son representados por subclasificación de ésta clase.
 * @author santiago
 *
 */
@Entity
public abstract class CriterioEvolucion {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String tipo;
	private Integer valor;
	
	public CriterioEvolucion(Especie especie, Integer valor) {
		this.setValor(valor);
	}
	
	public CriterioEvolucion(Integer valor) {
		this.setValor(valor);
	}
	
	public CriterioEvolucion() {}
	

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public abstract boolean seCumple(Bicho bicho, Entrenador entrenador);
}
