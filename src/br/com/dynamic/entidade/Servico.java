package br.com.dynamic.entidade;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "servico", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
public class Servico implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Categoria categoria;
	private String nome;
	private float valor;
	private Date tempo;
	private Set<Registro> registros = new HashSet<Registro>(0);

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idServico", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Categoria_idCategoria", nullable = false)
	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Column(name = "nome", unique = true, nullable = false, length = 80)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "valor", nullable = false, precision = 12, scale = 0)
	public float getValor() {
		return this.valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "tempo", length = 8)
	public Date getTempo() {
		return this.tempo;
	}

	public void setTempo(Date tempo) {
		this.tempo = tempo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "servico")
	public Set<Registro> getRegistros() {
		return this.registros;
	}

	public void setRegistros(Set<Registro> registros) {
		this.registros = registros;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;
        
        Servico other = (Servico) obj;
        if (id == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome)) {
            return false;
        }
        
        return true;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(nome);
        return hcb.toHashCode();
	}

}