package kr.or.ddit.clap.view.support.eventboard;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class EventContentUpdateController implements Initializable  {

	@FXML AnchorPane main;
	@FXML ImageView imgview_eventImg;
	@FXML JFXTextField text_Title;
	@FXML JFXTextField text_SDate;
	@FXML JFXTextField text_EDate;
	@FXML Label label_Id;
	@FXML JFXButton btn_updateImg;
	@FXML JFXButton btn_update;
	@FXML JFXButton btn_cancel;
	@FXML JFXTextArea text_Content;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
