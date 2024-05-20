package objectes;

import java.time.LocalTime;

public class Parking {
    public static final int N_PLACES=8;

    private Placa[] places=new Placa[N_PLACES];

    public Parking(){
        for (int i = 0; i < N_PLACES; i++) {
            places[i]=new Placa(i);
        }
    }

    public void afegirCotxe(Cotxe cotxeP, int nPlacaP){
        if(!placaOcupada(nPlacaP)){
            places[nPlacaP].setCotxe(cotxeP);
            places[nPlacaP].setHoraEntrada(LocalTime.now());
        }
    }

    public void treureCotxe(int nPlacaP){
        if(placaOcupada(nPlacaP)){
            places[nPlacaP].setCotxe(null);
        }
    }

    //==================================

    public int getPlacaLliure(){
        boolean placaTrobada=false;
        int aux=0;
        while(!placaTrobada&&aux<N_PLACES){
            if(places[aux].getCotxe()==null)placaTrobada=true;
            else aux++;
        }
        if(placaTrobada)return aux;
        else return -1;
    }

    public Placa getPlaca(int nPlacaP){
        return places[nPlacaP];
    }

    //==================================

    public boolean placaOcupada(int nPlaca){
        return places[nPlaca].getCotxe()!=null;
    }

    public boolean parkingPle(){
        boolean ple=true;
        int aux=0;
        while(ple&&aux<N_PLACES){
            if(places[aux].getCotxe()==null)ple=false;
            else aux++;
        }
        return ple;
    }
}
