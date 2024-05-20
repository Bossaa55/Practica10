package objectes;

public class Cotxe {
    private String matricula;
    private String marca;
    private String model;

    Cotxe(String matriculaP, String marcaP, String modelP){
        this.matricula=matriculaP;
        this.marca=marcaP;
        this.model=modelP;
    }

    public String getMatricula(){return this.matricula;}
    public String getMarca(){return this.marca;}
    public String getModel(){return this.model;}
}
