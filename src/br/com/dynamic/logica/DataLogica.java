package br.com.dynamic.logica;

import java.util.Calendar;
import java.util.Date;

public class DataLogica {
	
	static public String diaDaSemana(Date data){
		
		int valorDiaDaSemana;
		String diaDaSemana = "";
		Calendar dataCalendar = Calendar.getInstance();
		dataCalendar.setTime(data);
		valorDiaDaSemana = dataCalendar.get(Calendar.DAY_OF_WEEK);
		
		if(valorDiaDaSemana==1)
		{
			diaDaSemana="Domingo";
		}
		else if(valorDiaDaSemana==2)
		{
			diaDaSemana="Segunda-feira";
		}
		else if(valorDiaDaSemana==3)
		{
			diaDaSemana="Terça-feira";
		}
		else if(valorDiaDaSemana==4)
		{
			diaDaSemana="Quarta-feira";
		}
		else if(valorDiaDaSemana==5)
		{
			diaDaSemana="Quinta-feira";
		}
		else if(valorDiaDaSemana==6)
		{
			diaDaSemana="Sexta-feira";
		}
		else if(valorDiaDaSemana==7)
		{
			diaDaSemana="Sábado";
		}
		
		return diaDaSemana;
	}

}
