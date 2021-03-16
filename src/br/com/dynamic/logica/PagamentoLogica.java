package br.com.dynamic.logica;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dynamic.entidade.Plano;
import br.com.dynamic.repo.interfaces.IPlanoRepo;

@Service
public class PagamentoLogica implements IPagamentoLogica {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IPlanoRepo planoRepo;
	
	public String pagamento(int numeroTransacoes){
		Plano plano = planoRepo.buscarPlano();
		float valorTotal = 0;
		float valorTransacao = plano.getValor();
		float fatorMutipicativo = 0;
		DecimalFormat duasCasasDecimais = new DecimalFormat("#.00"); 
		String valorFinal = "10,00";

		try {
			valorTotal=((float)numeroTransacoes/(float)plano.getFormula());
		} catch (Exception e) {
			valorTotal=1;
		}

		if(valorTotal<25)
		{
			fatorMutipicativo = Float.parseFloat("0.55");
		}
		else if(valorTotal<50)
		{
			fatorMutipicativo = Float.parseFloat("0.4");
		}
		else if(valorTotal<75)
		{
			fatorMutipicativo = Float.parseFloat("0.25");
		}
		else if(valorTotal<100)
		{
			fatorMutipicativo = Float.parseFloat("0.15");
		}
		else if(valorTotal>100)
		{
			fatorMutipicativo = Float.parseFloat("0.1");
		}
		valorTotal=valorTotal*(valorTransacao - valorTransacao*(fatorMutipicativo*valorTotal/100)); 
		try {
			valorFinal = duasCasasDecimais.format(valorTotal);
		} catch (Exception e) {
			
		}
		
		return valorFinal;
	}

}