package objectes;

import java.time.LocalTime;
import java.util.ArrayList;

public class Parking {
    public static final int N_PLACES=8;

    private Placa[] places=new Placa[N_PLACES];

    public Parking(){
        for (int i = 0; i < N_PLACES; i++) {
            places[i]=new Placa(i);
        }
    }

    public void entrarCotxe(Cotxe cotxeP, int nPlacaP){
        if(!placaOcupada(nPlacaP)){
            places[nPlacaP].setCotxe(cotxeP);
            places[nPlacaP].setHoraEntrada(LocalTime.now());
        }
    }
    
    public void sortidaCotxe(int nPlaca){
        places[nPlaca].setHoraSortida(LocalTime.now());
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
    
    public int getNPlacesOcupades() {
    	return 1;
    }

    public ArrayList<Integer> getPlacesOcupades(){
        ArrayList<Integer> p=new ArrayList<Integer>();
        for (int i = 0; i < places.length; i++) {
            if(places[i].getCotxe()!=null)p.add(i);
        }
        return p;
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
    
    public boolean matriculaRepetida(String matricula) {
    	boolean fi=false;
    	int aux=0;
    	while(!fi&&aux<N_PLACES) {
    		if(places[aux].getCotxe()!=null) {
        		if(places[aux].getCotxe().getMatricula().equals(matricula))fi=true;
    		}
        	aux++;
    	}
    	return fi;
    }
}
