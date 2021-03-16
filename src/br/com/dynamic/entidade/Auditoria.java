package br.com.dynamic.entidade;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "auditoria")
public class Auditoria implements Serializable {
	
	public static final String ADICAO = "Adição";
	public static final String EDICAO = "Edição";
	public static final String REMOCAO = "Remoção";

	private Integer id;
	private String nomeUsuario;
	private Date data;
	private Date hora;
	private String tipoTransacao;
	private String tabela;
	private Integer idObjeto;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idAuditoria", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nomeUsuario", nullable = false, length = 80)
	public String getNomeUsuario() {
		return this.nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
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

	@Column(name = "tipoTransacao", nullable = false, length = 20)
	public String getTipoTransacao() {
		return this.tipoTransacao;
	}

	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	@Column(name = "tabela", nullable = false, length = 80)
	public String getTabela() {
		return this.tabela;
	}

	public void setTabela(String tabela) {
		this.tabela = tabela;
	}

	@Column(name = "idObjeto", nullable = false)
	public Integer getIdObjeto() {
		return this.idObjeto;
	}

	public void setIdObjeto(Integer idObjeto) {
		this.idObjeto = idObjeto;
	}
	
	public void preencherAuditoria(String tipoTransacao, String tabela, Integer idObjeto) {
		this.tipoTransacao = tipoTransacao;
		this.tabela = tabela;
		this.idObjeto = idObjeto;
	}

}