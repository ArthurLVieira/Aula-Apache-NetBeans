package Funcoes;

import Interface.PermiteAcesso;

public class FuncaoAutenticacao {
    	private final PermiteAcesso permitirAcesso;

	public boolean autenticar() {
		return permitirAcesso.autenticar();
	}
	
	public FuncaoAutenticacao(PermiteAcesso acesso) {
		this.permitirAcesso = acesso;
	}

}
