package kr.or.ddit.clap.view.chartmenu.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXTabPane;

import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;

public class ChartMenuController implements Initializable{
	
	public static int menuCount = 0;
	
	@FXML JFXTabPane tabPane_main;
	@FXML Tab tab_top50;
	@FXML Tab tab_genre;
	@FXML Tab tab_period;
	@FXML Tab tab_musicvideo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		SingleSelectionModel<Tab> selectionModel = tabPane_main.getSelectionModel();
		selectionModel.select(menuCount);
		
		
		
	}
	
}
