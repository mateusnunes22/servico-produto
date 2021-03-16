package br.com.dynamic.login;

public class Perfil {

	private String perf;
	private String paginasPerfil[];
	private Pagina paginas = new Pagina();

	public Perfil(String perfil) {

		this.setPerf(perfil.substring(0, 3));

		if (this.getPerf().equalsIgnoreCase("adm")) {
			this.paginasPerfil = paginas.getAdmistrador();
		} else if (this.getPerf().equalsIgnoreCase("fun")) {
			this.paginasPerfil = paginas.getFuncionario();
		} else {
			this.paginasPerfil = paginas.getSemAcesso();
		}

	}

	public boolean temAcesso(String pagina) {
		Boolean temAcesso = false;
		String p[] = this.paginasPerfil;

		for (int i = 0; i < p.length; i++) {
			if (pagina.endsWith(p[i])) {
				temAcesso = true;
			}
		}

		return temAcesso;
	}

	public String getPerf() {
		return perf;
	}

	public void setPerf(String perf) {
		this.perf = perf;
	}

}
