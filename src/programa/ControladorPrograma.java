package programa;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import objectes.Parking;

public class ControladorPrograma implements Initializable{

    @FXML
    private Pane pContenidorEntrada, pContenidorParking;

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView ivCotxeParking;

    private Parking parking=new Parking();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {


        moureCotxe(3);
    }

    @FXML
    private void moureCotxe(int nPlaca){
        int b=0;
        if(nPlaca>parking.N_PLACES/2)b=1;
        int f=nPlaca-(4*b);
        int[] files={193,296,402,505};
        int[][] bloc={{500,300},{700,900}};
        // Create the Path
        ivCotxeParking.setLayoutX(-200);
        Path path = new Path();
        path.getElements().add(new MoveTo(0, 0));
        path.getElements().add(new LineTo(bloc[b][0], 0));
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

        // Play the animation
        pathTransition.play();
    }


    private String generarMatricula(){
        return "1234BBB";
    }
}
