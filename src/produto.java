

public class produto {
	private Integer id;
	private String nome;
	private String descricao;
	
	public produto(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public int setId(int id) {
		return this.id = id;
	}
	
	public String toString() {
		return String.format("O produto criado foi: %d, %s, %s",
				this.id, this.nome, this.descricao);
	}
}
