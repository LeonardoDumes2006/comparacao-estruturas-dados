package estruturas.arvores;

import estruturas.EstruturaDeDados;

public class ArvoreRubroNegra implements EstruturaDeDados {
	private enum Cor {
		VERMELHO, PRETO
	}

	private class No {
		private int valor;
		private Cor cor;
		private No esq, dir, pai;

		public No(int valor) {
			this.valor = valor;
			cor = Cor.VERMELHO;
			esq = dir = pai = null;
		}
	}

	private No raiz;

	public ArvoreRubroNegra() {
		raiz = null;
	}

	@Override
	public void inserir(int valor) {
		if (buscar(valor)) {
			// System.out.println("Já tá na árvore"); -> Comentado apenas para não atrasar o cronómetro
			return;
		}
		No novoNo = new No(valor);
		raiz = inserirNo(raiz, novoNo);
		corrigirInsercao(novoNo);
	}

	private No inserirNo(No atual, No novoNo) {
		if (atual == null) {
			return novoNo;
		}

		if (novoNo.valor < atual.valor) {
			atual.esq = inserirNo(atual.esq, novoNo);
			atual.esq.pai = atual;
		} else if (novoNo.valor > atual.valor) {
			atual.dir = inserirNo(atual.dir, novoNo);
			atual.dir.pai = atual;
		}
		return atual;
	}

	private void corrigirInsercao(No no) {
		while (no != raiz && cor(no.pai) == Cor.VERMELHO) {
			No pai = no.pai;
			No avo = pai.pai;

			if (pai == avo.esq) {
				No tio = avo.dir;
				if (cor(tio) == Cor.VERMELHO) {
					pai.cor = Cor.PRETO;
					tio.cor = Cor.PRETO;
					avo.cor = Cor.VERMELHO;
					no = avo;
				} else {
					// TRIÂNGULO
					if(no == pai.dir) {
						no = pai;
						rotacaoEsquerda(no);
						
						pai = no.pai;
						avo = pai.pai;
					}
					// caso3 linha
					pai.cor = Cor.PRETO;
					avo.cor = Cor.VERMELHO;
					rotacaoDireita(avo);
				}
			} else {
				// caso simétrico: pai é filho direito do avo
				No tio = avo.esq;
				// Caso 1: tio vermelho
				if(cor(tio) == Cor.VERMELHO) {
					pai.cor = Cor.PRETO;
					tio.cor = Cor.PRETO;
					avo.cor = Cor.VERMELHO;
					no = avo;
				} else {
					// caso 2: triângulo. Transforma em uma linha.
					if(no == pai.esq) {
						no = pai;
						rotacaoDireita(no);
						
						pai = no.pai;
						avo = pai.pai;
					}
					
					// Caso 3: linha 
					pai.cor = Cor.PRETO;
					avo.cor = Cor.VERMELHO;
					rotacaoEsquerda(avo);
				}
			}
		}
		raiz.cor = Cor.PRETO;
	}

	private void rotacaoEsquerda(No no) {
		// Trava de segurança: impede rotação se o nó ou o filho não existirem
		if (no == null || no.dir == null) return;
		
		No novaRaiz = no.dir;
		no.dir = novaRaiz.esq;
		
		if(novaRaiz.esq != null) {
			novaRaiz.esq.pai = no;
		}
		
		novaRaiz.pai = no.pai;
		if(no.pai == null) {
			raiz = novaRaiz;
		} else if(no == no.pai.esq) {
			no.pai.esq = novaRaiz;
		} else {
			no.pai.dir = novaRaiz;
		}
		
		novaRaiz.esq = no;
		no.pai = novaRaiz;
	}
	
	private void rotacaoDireita(No no) {
		// Trava de segurança: impede rotação se o nó ou o filho não existirem
		if (no == null || no.esq == null) return;
		
		No novaRaiz = no.esq;
		
		no.esq = novaRaiz.dir;
		if(novaRaiz.dir != null) {
			novaRaiz.dir.pai = no;
		}
		
		novaRaiz.pai = no.pai;
		if(no.pai == null) {
			raiz = novaRaiz;
		} else if(no == no.pai.dir) {
			no.pai.dir = novaRaiz;
		} else {
			no.pai.esq = novaRaiz;
		}
		
		novaRaiz.dir = no;
		no.pai = novaRaiz;
	}	

	@Override
	public boolean buscar(int valor) {
		return buscarNo(raiz, valor) != null;
	}

	private No buscarNo(No atual, int valor) {
		if(atual == null || atual.valor == valor) {
			return atual;
		}
		if(valor < atual.valor) {
			return buscarNo(atual.esq, valor);
		} else {
			return buscarNo(atual.dir, valor);
		}
	}
	
	private Cor cor(No no) {
		return no == null ? Cor.PRETO : no.cor;
	}

	// -------------------------------------------------------------------------
	// MÉTODOS NOVOS ADICIONADOS COM NOMES CLAROS (REMOÇÃO E INTERVALO)
	// -------------------------------------------------------------------------

	@Override
	public int consultarIntervalo(int minimo, int maximo) {
		return contarIntervaloRecursivo(raiz, minimo, maximo);
	}

	private int contarIntervaloRecursivo(No no, int minimo, int maximo) {
		if (no == null) return 0;
		
		int contagem = 0;
		if (no.valor >= minimo && no.valor <= maximo) {
			contagem = 1;
		}
		
		if (no.valor > minimo) {
			contagem += contarIntervaloRecursivo(no.esq, minimo, maximo);
		}
		if (no.valor < maximo) {
			contagem += contarIntervaloRecursivo(no.dir, minimo, maximo);
		}
		return contagem;
	}

	@Override
	public void remover(int valor) {
		No alvo = buscarNo(raiz, valor);
		if (alvo == null) return; // Se não achar o valor, não faz nada
		
		No filhoSubstituto;
		No substituto = alvo;
		Cor corOriginalSubstituto = substituto.cor;
		
		// Caso 1 e 2: O alvo tem no máximo um filho
		if (alvo.esq == null) {
			filhoSubstituto = alvo.dir;
			transplantar(alvo, alvo.dir);
		} else if (alvo.dir == null) {
			filhoSubstituto = alvo.esq;
			transplantar(alvo, alvo.esq);
		} else {
			// Caso 3: O alvo tem dois filhos, o substituto será o menor dos maiores
			substituto = encontrarMenor(alvo.dir);
			corOriginalSubstituto = substituto.cor;
			filhoSubstituto = substituto.dir;
			
			if (substituto.pai == alvo) {
				if (filhoSubstituto != null) filhoSubstituto.pai = substituto;
			} else {
				transplantar(substituto, substituto.dir);
				substituto.dir = alvo.dir;
				substituto.dir.pai = substituto;
			}
			transplantar(alvo, substituto);
			substituto.esq = alvo.esq;
			substituto.esq.pai = substituto;
			substituto.cor = alvo.cor;
		}
		
		// Se apagamos um nó preto, a árvore perdeu equilíbrio nas cores e precisa de ser corrigida
		if (corOriginalSubstituto == Cor.PRETO) {
			No paiDoFilho = (substituto.pai == null) ? substituto : (filhoSubstituto != null ? filhoSubstituto.pai : (substituto.pai != alvo ? substituto.pai : substituto));
			corrigirRemocao(filhoSubstituto, paiDoFilho);
		}
	}

	private void transplantar(No antigo, No novoNo) {
		if (antigo.pai == null) {
			raiz = novoNo;
		} else if (antigo == antigo.pai.esq) {
			antigo.pai.esq = novoNo;
		} else {
			antigo.pai.dir = novoNo;
		}
		if (novoNo != null) {
			novoNo.pai = antigo.pai;
		}
	}

	private No encontrarMenor(No no) {
		while (no.esq != null) {
			no = no.esq;
		}
		return no;
	}

	private void corrigirRemocao(No atual, No paiDoAtual) {
		while (atual != raiz && cor(atual) == Cor.PRETO) {
			if (atual == (paiDoAtual != null ? paiDoAtual.esq : null)) {
				No irmao = paiDoAtual.dir;
				if (cor(irmao) == Cor.VERMELHO) {
					irmao.cor = Cor.PRETO;
					paiDoAtual.cor = Cor.VERMELHO;
					rotacaoEsquerda(paiDoAtual);
					irmao = paiDoAtual.dir;
				}
				if (irmao != null && cor(irmao.esq) == Cor.PRETO && cor(irmao.dir) == Cor.PRETO) {
					irmao.cor = Cor.VERMELHO;
					atual = paiDoAtual;
					paiDoAtual = atual.pai;
				} else {
					if (irmao != null && cor(irmao.dir) == Cor.PRETO) {
						if (irmao.esq != null) irmao.esq.cor = Cor.PRETO;
						irmao.cor = Cor.VERMELHO;
						rotacaoDireita(irmao);
						irmao = paiDoAtual.dir;
					}
					if (irmao != null) {
						irmao.cor = cor(paiDoAtual);
						if (irmao.dir != null) irmao.dir.cor = Cor.PRETO;
					}
					paiDoAtual.cor = Cor.PRETO;
					rotacaoEsquerda(paiDoAtual);
					atual = raiz;
				}
			} else {
				No irmao = (paiDoAtual != null ? paiDoAtual.esq : null);
				if (cor(irmao) == Cor.VERMELHO) {
					irmao.cor = Cor.PRETO;
					paiDoAtual.cor = Cor.VERMELHO;
					rotacaoDireita(paiDoAtual);
					irmao = paiDoAtual.esq;
				}
				if (irmao != null && cor(irmao.dir) == Cor.PRETO && cor(irmao.esq) == Cor.PRETO) {
					irmao.cor = Cor.VERMELHO;
					atual = paiDoAtual;
					paiDoAtual = atual.pai;
				} else {
					if (irmao != null && cor(irmao.esq) == Cor.PRETO) {
						if (irmao.dir != null) irmao.dir.cor = Cor.PRETO;
						irmao.cor = Cor.VERMELHO;
						rotacaoEsquerda(irmao);
						irmao = paiDoAtual.esq;
					}
					if (irmao != null) {
						irmao.cor = cor(paiDoAtual);
						if (irmao.esq != null) irmao.esq.cor = Cor.PRETO;
					}
					paiDoAtual.cor = Cor.PRETO;
					rotacaoDireita(paiDoAtual);
					atual = raiz;
				}
			}
		}
		if (atual != null) atual.cor = Cor.PRETO;
	}
}