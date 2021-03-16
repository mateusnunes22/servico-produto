package br.com.dynamic.infra.excecoes;

public class SenhaInvalidaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SenhaInvalidaException() {
		super("usuario.erro.senha");
	}

}
