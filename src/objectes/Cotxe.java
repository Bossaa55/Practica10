package objectes;

public class Cotxe {
    private String matricula;
    private ColorCotxe color;
    private String model;

    public Cotxe(String matriculaP, ColorCotxe colorP){
        this.matricula=matriculaP;
        this.color=colorP;
    }

    public String getMatricula(){return this.matricula;}
    public ColorCotxe getColor(){return this.color;}
    public String getModel(){return this.model;}
}
