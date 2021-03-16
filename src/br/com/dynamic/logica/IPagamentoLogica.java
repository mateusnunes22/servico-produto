package br.com.dynamic.logica;

import java.io.Serializable;

public interface IPagamentoLogica extends Serializable {

	public String pagamento(int numeroTransacoes);

}