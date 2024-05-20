package programa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import objectes.ColorCotxe;
import objectes.Cotxe;
import objectes.Parking;
import objectes.Placa;

public class ControladorPrograma implements Initializable{
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private Pane pContenidorEntrada, pContenidorParking;

    //==============ENTRADA==============
    
    @FXML
    private Pane pTiquetEntrada;
    
    @FXML
    private Label etiEntradaTiquetMatricula, etiEntradaTiquetPlaca, etiEntradaTiquetHora, etiEntradaCotxeMatricula;
    
    @FXML
    private ImageView ivEntradaCotxe;

    //==============PARKING==============
    
    @FXML
    private ImageView ivCotxeParking;
    
    @FXML
    private Pane pContenidorInfoCotxe, pParkingContenidorCotxes;
    
    @FXML
    private Button btnParkingSortir, btnParkingEntrar;

    private Label[][] etisParkingInfoCotxe=new Label[8][2];
    private ImageView[] ivsParkingCotxes=new ImageView[8];
    private Parking parking=new Parking();
    private String[] matricules;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.printf("%5d%n",25);
    	System.out.printf("%5d%n",132);
    	System.out.printf("%5d%n",9);
    	try {
			ivCotxeParking.setImage(new Image(new FileInputStream("imatges/top-view/cotxe-cel.png")));
		} catch (Exception e) {
			// TODO: handle exception
		}
    	carregarMatricules();
    	for (int i = 0; i < pContenidorInfoCotxe.getChildren().size(); i+=2) {
			etisParkingInfoCotxe[i/2][0]=(Label) pContenidorInfoCotxe.getChildren().get(i);
			etisParkingInfoCotxe[i/2][1]=(Label) pContenidorInfoCotxe.getChildren().get(i+1);
			etisParkingInfoCotxe[i/2][0].setOpacity(0);
			etisParkingInfoCotxe[i/2][1].setOpacity(0);
		}
    	for (int i = 0; i < ivsParkingCotxes.length; i++) {
			ivsParkingCotxes[i]=(ImageView) pParkingContenidorCotxes.getChildren().get(i);
			ivsParkingCotxes[i].setVisible(false);
		}
    	pContenidorEntrada.setVisible(true);
    	pContenidorParking.setVisible(false);
    	entrada();
    }
    
    @FXML
    void entrada() {
    	pContenidorEntrada.setVisible(true);
    	pContenidorParking.setVisible(false);
    	pTiquetEntrada.setTranslateY(250);
    	
    	Random aleatori = new Random();
    	
    	String matricula=matricules[aleatori.nextInt(matricules.length)];
    	while(parking.matriculaRepetida(matricula)) {
    		matricula=matricules[aleatori.nextInt(matricules.length)];
    	}
    	int nPlaca=parking.getPlacaLliure();
    	LocalTime hora=LocalTime.now();
    	
    	ColorCotxe color=colorAleatori();
    	
    	parking.entrarCotxe(new Cotxe(matricula, color), nPlaca);
    	
    	try {
			ivEntradaCotxe.setImage(new Image(new FileInputStream("imatges/front-view/"+color.getNom()+".png")));
		} catch (Exception e) {
		}
    	etiEntradaCotxeMatricula.setText(matricula);
    	etiEntradaTiquetMatricula.setText(matricula);
    	etiEntradaTiquetPlaca.setText(String.format("%02d", nPlaca+1));
    	etiEntradaTiquetHora.setText(String.format("%02d:%02d",hora.getHour(), hora.getMinute()));
    }

    @FXML
    void entradaTreureTiquet() {    	
    	TranslateTransition transTransition = new TranslateTransition(Duration.seconds(2), pTiquetEntrada);
    	transTransition.setByY(-250);
    	transTransition.setCycleCount(1);
    	transTransition.setAutoReverse(true);
    	transTransition.play();
    }
    
    @FXML 
    void entrarCotxe(){
    	Random aleatori = new Random();
		int a=aleatori.nextInt(100);
		if((a<65&&!parking.parkingPle())||parking.getNPlacesOcupades()==1) {
			btnParkingEntrar.setVisible(true);
			btnParkingSortir.setVisible(false);
		}else {
			btnParkingEntrar.setVisible(false);
			btnParkingSortir.setVisible(true);
		}
		
    	pContenidorEntrada.setVisible(false);
    	pContenidorParking.setVisible(true);
    	
    	for (int i = 0; i < ivsParkingCotxes.length; i++) {
    		if(i!=Integer.valueOf(etiEntradaTiquetPlaca.getText())-1) {
    			if(parking.getPlaca(i).getCotxe()!=null) {
    				try {
    					ivsParkingCotxes[i].setImage(new Image(new FileInputStream("imatges/top-view/"+parking.getPlaca(i).getCotxe().getColor().getNom()+".png")));
    				} catch (Exception e) {
    				}
    				ivsParkingCotxes[i].setVisible(true);
    			}
    		}
		}
    	
    	moureCotxe(Integer.parseInt(etiEntradaTiquetPlaca.getText())-1,etiEntradaCotxeMatricula.getText(),etiEntradaTiquetHora.getText());
    }
    
    @FXML
    private void moureCotxe(int nPlaca, String matricula, String hora){
        int b=0;
        if(nPlaca>=parking.N_PLACES/2)b=1;
        int f=nPlaca-(4*b);
        System.out.println(b+" "+f);
        int[] files={193,296,402,505};
        int[][] bloc={{500,300},{700,900}};
        // Create the Path
        ivCotxeParking.setLayoutX(-200);
        try {
			ivCotxeParking.setImage(new Image(new FileInputStream("imatges/top-view/"+parking.getPlaca(nPlaca).getCotxe().getColor().getNom()+".png")));
		} catch (Exception e) {
			// TODO: handle exception
		}
        Path path = new Path();
        path.getElements().add(new MoveTo(0, 10));
        path.getElements().add(new LineTo(bloc[b][0], 10));
        path.getElements().add(new LineTo(bloc[b][0], files[f]));
        path.getElements().add(new LineTo(bloc[b][1], files[f]));

        // Create the PathTransition
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(5));
        pathTransition.setPath(path);
        pathTransition.setNode(ivCotxeParking);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.setOnFinished(event -> {
            etisParkingInfoCotxe[nPlaca][0].setText(matricula);
            etisParkingInfoCotxe[nPlaca][1].setText(hora);
            
            FadeTransition fadeIn1 = new FadeTransition(Duration.seconds(1), etisParkingInfoCotxe[nPlaca][0]);
            fadeIn1.setFromValue(0);
            fadeIn1.setToValue(1);
            fadeIn1.setInterpolator(Interpolator.EASE_IN);
            fadeIn1.setCycleCount(1);
            fadeIn1.setAutoReverse(false);

            FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(1), etisParkingInfoCotxe[nPlaca][1]);
            fadeIn2.setFromValue(0);
            fadeIn2.setToValue(1);
            fadeIn1.setInterpolator(Interpolator.EASE_IN);
            fadeIn2.setCycleCount(1);
            fadeIn2.setAutoReverse(false);
            
            TranslateTransition transTransition1 = new TranslateTransition(Duration.seconds(0.5), etisParkingInfoCotxe[nPlaca][0]);
        	transTransition1.setByX(50);
        	transTransition1.setInterpolator(Interpolator.EASE_OUT);
        	transTransition1.setCycleCount(1);
        	transTransition1.setAutoReverse(true);
        	
        	TranslateTransition transTransition2 = new TranslateTransition(Duration.seconds(0.5), etisParkingInfoCotxe[nPlaca][1]);
        	transTransition2.setByX(50);
        	transTransition2.setInterpolator(Interpolator.EASE_OUT);
        	transTransition2.setCycleCount(1);
        	transTransition2.setAutoReverse(true);

        	etisParkingInfoCotxe[nPlaca][0].setTranslateX(-50);
        	etisParkingInfoCotxe[nPlaca][1].setTranslateX(-50);
        	transTransition1.play();
        	transTransition2.play();
            fadeIn1.play();
            fadeIn2.play();
        });
        
        
        // Play the animation
        pathTransition.play();
    }


    private void carregarMatricules(){
    	try {
			File f=new File("fitxers/matricules.txt");
			Scanner lector=new Scanner(f);
			matricules=lector.nextLine().split(",");
			for (int i = 0; i < matricules.length; i++) {
				matricules[i]=matricules[i].trim();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    private ColorCotxe colorAleatori() {
    	Random aleatori = new Random();
		return ColorCotxe.values()[aleatori.nextInt(ColorCotxe.values().length)];
    }
}
