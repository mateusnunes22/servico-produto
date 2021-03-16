package br.com.dynamic.entidade;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * Mudanças na estrutura da tabela devem ser refletidas em log4j.properties, no JDBC Appender
 */

@Entity
@Table(name = "log")
public class Log {
	
	private Integer id;
	private String classe;
	private String mensagem;
	private String descricao;
	private String level;
	private String nomeUsuario;
	private Date data;
	private Date hora;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idLog")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "classe", nullable = false, length = 30)
	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	@Column(name = "mensagem", nullable = false, length = 65535, columnDefinition="TEXT")
	public String getMensagem() {
		return this.mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Column(name = "descricao", nullable = true, length = 65535, columnDefinition="TEXT")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "level", nullable = false)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(name = "nomeUsuario", nullable = true, length = 80)
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String usuario) {
		this.nomeUsuario = usuario;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data", nullable = false, length = 10)
	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "hora", nullable = false, length = 8)
	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

}
