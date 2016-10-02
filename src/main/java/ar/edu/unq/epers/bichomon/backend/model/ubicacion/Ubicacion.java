package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;

@Entity
public abstract class Ubicacion {

	@Id
	protected String nombre;
	
	@OneToMany(cascade = {CascadeType.ALL})
	protected List<Bicho> bichos;
	
	@OneToMany(cascade = {CascadeType.ALL})
	protected List<Entrenador> entrenadores;
	
	@Transient
	protected Random random;
	
	public Ubicacion (String nombre, Random random){
		this.setNombre(nombre);
		this.setBichos(new ArrayList <>());
		this.random= random;
	}
	
	public Ubicacion(String nombre) {
		this.nombre = nombre;
	}
	
	public Ubicacion() {}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Bicho> getBichos() {
		return bichos;
	}
	
	public void setBichos(List<Bicho> bichos) {
		this.bichos = bichos;
	}
	
	public abstract Bicho buscar(Entrenador entrenador);
	
	public abstract void abandonar(Entrenador entrenador, Bicho bicho);
	
	public abstract ResultadoCombate duelo(Entrenador entrenador, Bicho bicho);
	
}
