package br.com.dynamic.services;

import java.io.Serializable;

import javassist.NotFoundException;
import br.com.dynamic.entidade.Usuario;

public interface ILoginService extends Serializable {

	public abstract Usuario authorize(String username, String password)
			throws NotFoundException;

}