package objectes;

public enum ColorCotxe {
	VERMELL("vermell"),
	BLAU("blau"),
	CEL("cel");
	
	String nom;
	
	private ColorCotxe(String nomP) {
		this.nom=nomP;
	}
	
	public String getNom() {return this.nom;}
}
