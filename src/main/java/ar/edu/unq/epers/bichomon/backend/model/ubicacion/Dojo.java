package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Historial;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

@Entity
public class Dojo extends Ubicacion {
	
	@OneToOne
	private Bicho campeon;
	
	@Transient
	private Historial historial;
		
	public Dojo(String nombreDojo,Random random) {
		super(nombreDojo,random);
	}
	
	public Dojo(String nombreDojo) {
		super(nombreDojo);
	}

	public Dojo() {}
	
	public Bicho getCampeon() {
		return campeon;
	}

	public void setCampeon(Bicho campeon) {
		this.campeon = campeon;
	}
	
	public Historial getHistorial() {
		return historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}

	public List<Bicho> mismaEspecieQueElCampeon(){
		Especie especieCampeon= this.campeon.getEspecie().getRaiz();
		List<Bicho> bichosDeIgualEspecie= new ArrayList<>();
		for (Bicho bicho: this.getBichos()){
			if(bicho.getEspecie()== especieCampeon){
				bichosDeIgualEspecie.add(bicho);
			}
		}
		return  bichosDeIgualEspecie;
	}
	
	public Integer cantidadDeBichosEnDojo(){
		return this.getBichos().size();
	}
	
	public Bicho asignarBicho(){
		Integer index = this.numeroRandom();
		
		return this.getBichos().get(index);
		
	}
	public Integer numeroRandom(){
		Integer valor = random.nextInt(this.cantidadDeBichosEnDojo());
		return valor;
	}
	
	@Override
	public Bicho buscar(Entrenador entrenador) {
		if (entrenador.puedeBuscar()){

			return new Bicho(this.campeon.getEspecie().getRaiz()); 
		}

		return null;
	}

	@Override
	public void abandonar(Entrenador entrenador, Bicho bicho) {
		throw new NoSePuedeAbandonarEnUbicacionException();
		
	}

	@Override
	public ResultadoCombate duelo(Entrenador entrenador, Bicho bicho) {
		ResultadoCombate duelo = new Duelo(bicho, campeon).iniciarDuelo();
		campeon = duelo.getBichoGanador();
		return duelo;
	}
	
	private void agregarAlHistorial(Bicho bichoCampeon, LocalDateTime fecha, Bicho derrocado){
		new Campeon(bichoCampeon, fecha, bichoCampeon.getOwner(), this);
	}

}