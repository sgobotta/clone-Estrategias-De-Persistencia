package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

public class NoSePuedeBuscarSinCampeonException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoSePuedeBuscarSinCampeonException(String e){
		super(e);
	};
}