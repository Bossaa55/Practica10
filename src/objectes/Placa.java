package objectes;

import java.time.LocalTime;

public class Placa {
    private Cotxe cotxe;
    private LocalTime horaEntrada;
    private LocalTime horaSortida;
    private int nPlaca;

    public Placa(int nPlacaP){
        this.nPlaca=nPlacaP;
    }

    public void setCotxe(Cotxe cotxeP){this.cotxe=cotxeP;}
    public void setHoraEntrada(LocalTime horaEntradaP){this.horaEntrada=horaEntradaP;}
    public void setHoraSortida(LocalTime horaSortidaP){this.horaSortida=horaSortidaP;}

    public Cotxe getCotxe(){return this.cotxe;}
    public LocalTime getHoraEntrada(){return this.horaEntrada;}
    public LocalTime getHoraSortida(){return this.horaSortida;}
    public int getNPlaca(){return this.nPlaca;}
}