package br.com.dynamic.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "registro")
public class Registro implements Serializable {

	private static final long serialVersionUID = 1L;

	private RegistroId id;
	private Produto produto;
	private Funcionario funcionario;
	private Cliente cliente;
	private Usuario usuario;
	private Fornecedor fornecedor;
	private Servico servico;
	private Date data;
	private String diaSemana;
	private Date hora;
	private Date tempo;
	private Float valor;
	private boolean venda;
	private boolean agendamento;
	private String formaPagamento;
	private Integer numeroParcela;
	private String observacao;

	public Registro() {
		this.numeroParcela = 1;
	}
	
	public Registro(Registro registro) {
		this.id = registro.getId();
		this.fornecedor = registro.getFornecedor();
		this.cliente = registro.getCliente();
		this.funcionario = registro.getFuncionario();
		this.usuario = registro.getUsuario();
		this.servico = registro.getServico();
		this.produto = registro.getProduto();
		this.data = registro.getData();
		this.diaSemana = registro.getDiaSemana();
		this.hora = registro.getHora();
		this.tempo = registro.getTempo();
		this.valor = registro.getValor();
		this.venda = registro.isVenda();
		this.agendamento = registro.isAgendamento();
		this.formaPagamento = registro.getFormaPagamento();
		this.numeroParcela = registro.getNumeroParcela();
		this.observacao = registro.getObservacao();
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idVenda", column = @Column(name = "idVenda", nullable = false)),
			@AttributeOverride(name = "usuarioIdUsuario", column = @Column(name = "Usuario_idUsuario", nullable = false)) })
	public RegistroId getId() {
		return this.id;
	}

	public void setId(RegistroId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Produto_idProduto", referencedColumnName = "idProduto", nullable = true)
	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Funcionario_idFuncionario", nullable = true)
	public Funcionario getFuncionario() {
		return this.funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Cliente_idCliente", nullable = true)
	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Usuario_idUsuario", nullable = false, insertable = false, updatable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Fornecedor_idFornecedor", nullable = true)
	public Fornecedor getFornecedor() {
		return this.fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Servico_idServico", referencedColumnName = "idServico", nullable = true)
	public Servico getServico() {
		return this.servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data", nullable = false, length = 10)
	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	@Column(name = "diaSemana", length = 18)
	public String getDiaSemana() {
		return this.diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "hora", nullable = false, length = 8)
	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "tempo", length = 8)
	public Date getTempo() {
		return this.tempo;
	}

	public void setTempo(Date tempo) {
		this.tempo = tempo;
	}

	@Column(name = "valor", nullable = false, precision = 12, scale = 0)
	public Float getValor() {
		return this.valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	@Column(name = "venda", nullable = false)
	public boolean isVenda() {
		return this.venda;
	}

	public void setVenda(boolean venda) {
		this.venda = venda;
	}

	@Column(name = "agendamento", nullable = false)
	public boolean isAgendamento() {
		return this.agendamento;
	}

	public void setAgendamento(boolean agendamento) {
		this.agendamento = agendamento;
	}

	@Column(name = "formaPagamento", nullable = false, length = 12)
	public String getFormaPagamento() {
		return this.formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	@Column(name = "numeroParcela")
	public Integer getNumeroParcela() {
		return this.numeroParcela;
	}

	public void setNumeroParcela(Integer numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	@Column(name = "observacao", length = 80)
	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
