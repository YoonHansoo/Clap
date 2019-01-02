package kr.or.ddit.clap.view.chartmenu.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * @author 진민규
 *
 */

public class ChartMenuController implements Initializable{
	
	public static int menuCount = 0;
	
	@FXML JFXTabPane tabPane_main;
	@FXML Tab tab_top50;
	@FXML Tab tab_genre;
	@FXML Tab tab_period;
	@FXML Tab tab_musicvideo;
	@FXML AnchorPane anchorPane_top50;

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tabPane_main.getSelectionModel().select(menuCount);
		
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("../top50/Top50RealTime.fxml"));
			anchorPane_top50.getChildren().removeAll();
			anchorPane_top50.getChildren().setAll(pane);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
}
