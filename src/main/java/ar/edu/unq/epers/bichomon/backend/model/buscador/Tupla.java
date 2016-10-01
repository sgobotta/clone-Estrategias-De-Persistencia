package ar.edu.unq.epers.bichomon.backend.model.buscador;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

public class Tupla{
	private Especie key;
	private float value;
	private List<Integer> lsValue;

	public Tupla(Especie especie, float value){
		this.key 	= especie;
		this.value 	= value;
	};
	
	public Tupla(Especie especie, List<Integer> value){
		this.key	 	= especie;
		this.lsValue	= value;
	};

	public Especie getKey(){
		return this.key;
	}
	
	public float getValue(){
		return this.value;
	}
	
	public List<Integer> getLsValue(){
		return this.lsValue;
	}
	
	//@Override
	//public boolean equals(Tupla anotherTupla){
		//anotherTupla.getKey() == this.getKey() 
	//}
}
