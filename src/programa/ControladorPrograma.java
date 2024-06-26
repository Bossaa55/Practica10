package programa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    private Pane pContenidorEntrada, pContenidorParking, pContenidorSortida, pContenidorCaixer, pContenidorSortidaFinal, pContenidorNoSortida;

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

    //==============SORTIDA==============

    @FXML
    private Label etiSortidaCotxeMatricula, etiSortidaTiquetEntradaMatricula, etiSortidaTiquetEntradaPlaca, etiSortidaTiquetEntradaHora,
                    etiSortidaTiquetSortidaMatricula, etiSortidaTiquetSortidaPlaca, etiSortidaTiquetSortidaHoraEntrada, etiSortidaTiquetSortidaHoraSortida,
                    etiSortidaTiquetSortidaTotalPagar;

    @FXML
    private Pane pTiquetSortida, pSortidaTiquetEntrada;

    @FXML
    private ImageView ivSortidaCotxe;

    //==============CAIXER==============

    @FXML
    private Label etiCaixerTotalPagar, etiCaixerPagat, etiCaixerCanvi;
    
    @FXML
    private Button btnCaixerPagar;

    @FXML
    private ImageView ivCaixer200, ivCaixer100, ivCaixer50, ivCaixer20, ivCaixer10, ivCaixer5, ivCaixer2, ivCaixer1;

    @FXML
    private ScrollPane spCaixerCanvi;

    @FXML
    private VBox vBoxContenidorCanvi;

    private ImageView[] ivMonedes;

    //==============SORTIDA FINAL==============
    
    @FXML
    private Label etiSortidaFinalCotxeMatricula, etiSortidaFinalTiquetSortidaMatricula, etiSortidaFinalTiquetSortidaPlaca, 
    				etiSortidaFinalTiquetSortidaHoraEntrada, etiSortidaFinalTiquetSortidaHoraSortida,
    				etiSortidaFinalTiquetSortidaTotalPagar; 
    
    @FXML
    private ImageView ivSortidaFinalCotxe;

    //==============NO SORTIDA==============

    @FXML
    private Label etiNoSortidaTiquetMatricula, etiNoSortidaTiquetPlaca, etiNoSortidaTiquetHoraEntrada, etiNoSortidaMatricula;
    
    @FXML
    private ImageView ivNoSortidaCotxe;

    private Parking parking=new Parking();
    private String[] matricules;

    private String matriculaActual;
    private int placaActual;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ImageView[] a={ivCaixer200,ivCaixer100,ivCaixer50,ivCaixer20,ivCaixer10,ivCaixer5,ivCaixer2,ivCaixer1};
        ivMonedes=a;
    	obtenirMonedesCanvi(185);
    	try {
			ivCotxeParking.setImage(new Image(new FileInputStream("imatges/top-view/cotxe-cel.png")));
		} catch (Exception e) {
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
        pContenidorSortida.setVisible(false);
        pContenidorCaixer.setVisible(false);
        pContenidorSortidaFinal.setVisible(false);
        pContenidorNoSortida.setVisible(false);
    	entrada();
    }
    
    //==============ENTRADA==============

    @FXML
    void entrada() {
    	pContenidorEntrada.setVisible(true);
    	pContenidorParking.setVisible(false);
        pContenidorSortida.setVisible(false);
        pContenidorCaixer.setVisible(false);
        pContenidorSortidaFinal.setVisible(false);
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
    	etiEntradaTiquetHora.setText(formatarHora(parking.getPlaca(nPlaca).getHoraEntrada()));

        matriculaActual=matricula;
        placaActual=nPlaca;
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
        canviarPantallaParking(true);
    	moureCotxe(Integer.parseInt(etiEntradaTiquetPlaca.getText())-1,etiEntradaCotxeMatricula.getText(),etiEntradaTiquetHora.getText());
    }
    
    //==============PARKING==============

    @FXML
    private void canviarPantallaParking(boolean entrarCotxe) {
    	Random aleatori = new Random();
		int a=aleatori.nextInt(100);
		if((a<65||parking.getPlacesOcupades().size()==1)&&!parking.parkingPle()) {
			btnParkingEntrar.setVisible(true);
			btnParkingSortir.setVisible(false);
		}else {
			btnParkingEntrar.setVisible(false);
			btnParkingSortir.setVisible(true);
		}
		

    	pContenidorEntrada.setVisible(false);
    	pContenidorParking.setVisible(true);
        pContenidorSortida.setVisible(false);
        pContenidorCaixer.setVisible(false);
        pContenidorSortidaFinal.setVisible(false);
        pContenidorNoSortida.setVisible(false);
    	
    	for (int i = 0; i < ivsParkingCotxes.length; i++) {
    		if(i!=placaActual||!entrarCotxe) {
    			if(parking.getPlaca(i).getCotxe()!=null) {
    				try {
    					ivsParkingCotxes[i].setImage(new Image(new FileInputStream("imatges/top-view/"+parking.getPlaca(i).getCotxe().getColor().getNom()+".png")));
    				} catch (Exception e) {
    				}
    				ivsParkingCotxes[i].setVisible(true);
    				etisParkingInfoCotxe[i][0].setOpacity(1);
    				etisParkingInfoCotxe[i][1].setOpacity(1);
    			}else {
    				ivsParkingCotxes[i].setVisible(false);
    				etisParkingInfoCotxe[i][0].setOpacity(0);
    				etisParkingInfoCotxe[i][1].setOpacity(0);
    			}
    		}
		}
    	ivCotxeParking.setVisible(false);
    }
    
    @FXML
    private void moureCotxe(int nPlaca, String matricula, String hora){
    	ivCotxeParking.setVisible(true);
        int b=0;
        if(nPlaca>=parking.N_PLACES/2)b=1;
        int f=nPlaca-(4*b);
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
    //==============SORTIDA==============

    @FXML
    void sortida(){
        Random aleatori=new Random();
        int placa=parking.getPlacesOcupades().get(aleatori.nextInt(0,parking.getPlacesOcupades().size()));

        pContenidorEntrada.setVisible(false);
        pContenidorParking.setVisible(false);
        pContenidorSortida.setVisible(true);
        pContenidorCaixer.setVisible(false);
        pContenidorNoSortida.setVisible(false);
        parking.sortidaCotxe(placa);

    	pTiquetSortida.setTranslateY(250);

        etiSortidaCotxeMatricula.setText(parking.getPlaca(placa).getCotxe().getMatricula());
        etiSortidaTiquetEntradaMatricula.setText(parking.getPlaca(placa).getCotxe().getMatricula());
        etiSortidaTiquetSortidaMatricula.setText(parking.getPlaca(placa).getCotxe().getMatricula());
        etiSortidaTiquetEntradaPlaca.setText(String.format("%02d", placa+1));
        etiSortidaTiquetSortidaPlaca.setText(String.format("%02d", placa+1));
    	etiSortidaTiquetEntradaHora.setText(formatarHora(parking.getPlaca(placa).getHoraEntrada()));
        etiSortidaTiquetSortidaHoraEntrada.setText(formatarHora(parking.getPlaca(placa).getHoraEntrada()));
        etiSortidaTiquetSortidaHoraSortida.setText(formatarHora(parking.getPlaca(placa).getHoraSortida()));
        etiSortidaTiquetSortidaTotalPagar.setText(formatarPagament(parking.getPlaca(placa).getTotalPagar())+"€");

        try {
			ivSortidaCotxe.setImage(new Image(new FileInputStream("imatges/front-view/"+parking.getPlaca(placa).getCotxe().getColor().getNom()+".png")));
		} catch (Exception e) {
		}

        placaActual=placa;
    }

    @FXML
    void sortidaTreureTiquet() {    	
    	TranslateTransition transTransition = new TranslateTransition(Duration.seconds(2), pTiquetSortida);
    	transTransition.setByY(-250);
    	transTransition.setCycleCount(1);
    	transTransition.setAutoReverse(true);
    	transTransition.play();
    }

    //==============CAIXER==============

    @FXML
    void caixer(){
        pContenidorEntrada.setVisible(false);
        pContenidorParking.setVisible(false);
        pContenidorSortida.setVisible(false);
        pContenidorCaixer.setVisible(true);
        pContenidorSortidaFinal.setVisible(false);
        pContenidorNoSortida.setVisible(false);

        etiCaixerTotalPagar.setText(formatarPagament(parking.getPlaca(placaActual).getTotalPagar())+"€");
        etiCaixerPagat.setText("0,00€");
        totalPagat=0;
        btnCaixerPagar.setVisible(true);
        btnCaixerPagar.setDisable(true);
        pPantallaPagamentContenidorMonedes.setVisible(true);
        spCaixerCanvi.setVisible(false);
    }
    
    @FXML
    void pagar() {
    	int canvi=totalPagat-parking.getPlaca(placaActual).getTotalPagar();
    	int[] monedes=obtenirMonedesCanvi(canvi);
    	btnCaixerPagar.setVisible(false);
        pPantallaPagamentContenidorMonedes.setVisible(false);
        spCaixerCanvi.setVisible(true);
        donarCanvi(monedes);
    }

    @FXML
    private void actualitzarPagat(){
        etiCaixerPagat.setText(formatarPagament(totalPagat)+"€");
        if(totalPagat>=parking.getPlaca(placaActual).getTotalPagar()){
        	btnCaixerPagar.setDisable(false);
            etiCaixerCanvi.setText(formatarPagament(totalPagat-parking.getPlaca(placaActual).getTotalPagar())+"€");
        }
    }

    private double initialX;
    private double initialY;
    private int valorMonedaMoguent;
    private ArrayList<ImageView> monedesVolant=new ArrayList<ImageView>();
    private ArrayList<Integer> valorMonedesVolant=new ArrayList<Integer>();
    private int totalPagat=0;

    @FXML
    private Pane pPantallaPagamentContenidorMonedes;

    @FXML
    void monedaMousePressed200(MouseEvent event) {
    	valorMonedaMoguent=200;
        monedaMousePressed(event, 200);
    }
    @FXML
    void monedaMousePressed100(MouseEvent event) {
    	valorMonedaMoguent=100;
        monedaMousePressed(event, 100);
    }
    @FXML
    void monedaMousePressed50(MouseEvent event) {
    	valorMonedaMoguent=50;
        monedaMousePressed(event, 50);
    }
    @FXML
    void monedaMousePressed20(MouseEvent event) {
    	valorMonedaMoguent=20;
        monedaMousePressed(event, 20);
    }
    @FXML
    void monedaMousePressed10(MouseEvent event) {
    	valorMonedaMoguent=10;
        monedaMousePressed(event, 10);
    }
    @FXML
    void monedaMousePressed5(MouseEvent event) {
    	valorMonedaMoguent=5;
        monedaMousePressed(event, 5);
    }
    @FXML
    void monedaMousePressed2(MouseEvent event) {
    	valorMonedaMoguent=2;
        monedaMousePressed(event, 2);
    }
    @FXML
    void monedaMousePressed1(MouseEvent event) {
    	valorMonedaMoguent=1;
        monedaMousePressed(event, 1);
    }

    @FXML
    void monedaMouseDragged(MouseEvent event) {
        ImageView source=(ImageView) event.getSource();
        source.setTranslateX(event.getSceneX()-initialX);
        source.setTranslateY(event.getSceneY()-initialY);
    }

    @FXML
    void monedaMousePressed(MouseEvent event, int valor) {
        ImageView source=(ImageView) event.getSource();
        ImageView newIv=new ImageView(source.getImage());
        newIv.setLayoutX(source.getLayoutX());
        newIv.setLayoutY(source.getLayoutY());
        newIv.setFitHeight(source.getFitHeight());
        newIv.setFitWidth(source.getFitWidth());
        switch (valor) {
            case 200: newIv.setOnMousePressed(e ->{monedaMousePressed200(e);});
                break;
            case 100: newIv.setOnMousePressed(e ->{monedaMousePressed100(e);});
                break;
            case 50: newIv.setOnMousePressed(e ->{monedaMousePressed50(e);});
                break;
            case 20: newIv.setOnMousePressed(e ->{monedaMousePressed20(e);});
                break;
            case 10: newIv.setOnMousePressed(e ->{monedaMousePressed10(e);});
                break;
            case 5: newIv.setOnMousePressed(e ->{monedaMousePressed5(e);});
                break;
            case 2: newIv.setOnMousePressed(e ->{monedaMousePressed2(e);});
                break;
            case 1: newIv.setOnMousePressed(e ->{monedaMousePressed1(e);});
                break;
            default:
                break;
        }
        newIv.setOnMouseDragged(e ->{monedaMouseDragged(e);});
        newIv.setOnMouseReleased(e -> {monedaMouseReleased(e);});
        pPantallaPagamentContenidorMonedes.getChildren().add(newIv);

        initialX=event.getSceneX();
        initialY=event.getSceneY();
    }

    @FXML
    void monedaMouseReleased(MouseEvent event) {
        ImageView source=(ImageView) event.getSource();
        source.setLayoutX(source.getLayoutX()+event.getSceneX()-initialX);
        source.setTranslateX(0);
        source.setLayoutY(source.getLayoutY()+event.getSceneY()-initialY);
        source.setTranslateY(0);
        if(source.getLayoutX()>=482&&source.getLayoutX()<=786&&source.getLayoutY()<=100){
            monedesVolant.add(source);
            valorMonedesVolant.add(valorMonedaMoguent);

            Timeline timeline=new Timeline();

            KeyFrame kayFrame = new KeyFrame(Duration.millis(5), ev ->{
                if(source.getTranslateZ()!=1){
                    source.setTranslateY(source.getTranslateY()-(75+source.getTranslateY())/30);
                    if(source.getTranslateY()<-73){
                        source.setTranslateZ(1);
                    }
                }else{
                    source.setTranslateY(source.getTranslateY()+(75+source.getTranslateY())/30);
                    if(source.getLayoutY()+source.getTranslateY()>300){
                        pPantallaPagamentContenidorMonedes.getChildren().remove(source);
                        totalPagat+=valorMonedesVolant.get(0);
                        actualitzarPagat();
                        monedesVolant.remove(0);
                        valorMonedesVolant.remove(0);
                        timeline.stop();
                    }
                }
            });
            timeline.getKeyFrames().add(kayFrame);
            
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }else{
            pPantallaPagamentContenidorMonedes.getChildren().remove(source);
        } 
    }
    
    @FXML
    private void donarCanvi(int[] monedes) {
        vBoxContenidorCanvi.getChildren().clear();
    	for (int i = 0; i < monedes.length; i++) {
            for (int j = 0; j < monedes[i]; j++) {
                ImageView newIv=new ImageView(ivMonedes[i].getImage());
                newIv.setLayoutX(ivMonedes[i].getLayoutX());
                newIv.setLayoutY(ivMonedes[i].getLayoutY());
                newIv.setFitHeight(ivMonedes[i].getFitHeight());
                newIv.setFitWidth(ivMonedes[i].getFitWidth());
                vBoxContenidorCanvi.getChildren().addAll(newIv);
            }
        }
    }
    
    @FXML
    void sortirCaixer() {
    	if(!btnCaixerPagar.isVisible()) {
    		sortidaFinal();
    	}else{
            noSortida();
        }
    }
    
    //==============SORTIDA FINAL==============
    
    @FXML
    void sortidaFinalContinuar() {
    	parking.treureCotxe(placaActual);
    	canviarPantallaParking(false);
    }
    
    @FXML
    private void sortidaFinal() {
        pContenidorEntrada.setVisible(false);
        pContenidorParking.setVisible(false);
        pContenidorSortida.setVisible(false);
        pContenidorCaixer.setVisible(false);
        pContenidorSortidaFinal.setVisible(true);
        pContenidorNoSortida.setVisible(false);
        
        etiSortidaFinalCotxeMatricula.setText(parking.getPlaca(placaActual).getCotxe().getMatricula());
        etiSortidaFinalTiquetSortidaMatricula.setText(parking.getPlaca(placaActual).getCotxe().getMatricula());
        etiSortidaFinalTiquetSortidaPlaca.setText(String.format("%02d", placaActual+1));
        etiSortidaFinalTiquetSortidaHoraEntrada.setText(formatarHora(parking.getPlaca(placaActual).getHoraEntrada()));
        etiSortidaFinalTiquetSortidaHoraSortida.setText(formatarHora(parking.getPlaca(placaActual).getHoraSortida()));
        etiSortidaFinalTiquetSortidaTotalPagar.setText(formatarPagament(parking.getPlaca(placaActual).getTotalPagar()));

        try {
			ivSortidaFinalCotxe.setImage(ivSortidaCotxe.getImage());
		} catch (Exception e) {
		}
    }

    //==============NO SORTIDA==============

    @FXML
    private void noSortida(){
        pContenidorEntrada.setVisible(false);
        pContenidorParking.setVisible(false);
        pContenidorSortida.setVisible(false);
        pContenidorCaixer.setVisible(false);
        pContenidorSortidaFinal.setVisible(false);
        pContenidorNoSortida.setVisible(true);

        etiNoSortidaMatricula.setText(parking.getPlaca(placaActual).getCotxe().getMatricula());
        etiNoSortidaTiquetMatricula.setText(parking.getPlaca(placaActual).getCotxe().getMatricula());
        etiNoSortidaTiquetPlaca.setText(String.format("%02d", placaActual+1));
        etiNoSortidaTiquetHoraEntrada.setText(formatarHora(parking.getPlaca(placaActual).getHoraEntrada()));

        try {
			ivNoSortidaCotxe.setImage(ivSortidaCotxe.getImage());
		} catch (Exception e) {
		}
    }

    @FXML
    void noSortidaContinuar(){
        canviarPantallaParking(false);
    }
    
    private int[] obtenirMonedesCanvi(int canvi) {
    	int[] valorMonedes= {200,100,50,20,10,5,2,1};
    	int[] monedes=new int[8];
    	for (int i = 0; i < monedes.length; i++) {
    		int resta=canvi%valorMonedes[i];
			monedes[i]=(canvi-resta)/valorMonedes[i];
			canvi=resta;
		}
    	return monedes;
    }

    private String formatarPagament(int pagament){
        String total=String.valueOf(pagament);
        String totalFormatat;
        if(total.length()<2)totalFormatat="0,0"+total;
        else{
            totalFormatat=total.substring(0,total.length()-2);
            if(totalFormatat.length()==0)totalFormatat="0";
            totalFormatat+=","+total.substring(total.length()-2);
        }
        return totalFormatat;
    }

    private String formatarHora(LocalTime time){
        return String.format("%02d:%02d",time.getHour(), time.getMinute());
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
