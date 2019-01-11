package kr.or.ddit.clap.view.support.eventboard;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import com.jfoenix.controls.JFXButton;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

public class EventContentDetailController implements Initializable  {

	@FXML AnchorPane e_main;
	@FXML Text Text_EventNo;
	@FXML Text Text_EventTitle;
	@FXML Text Text_EventSDate;
	@FXML JFXButton btn_del;
	@FXML JFXButton btn_upd;
	@FXML Text Text_EventEDate;
	@FXML ImageView ImageView;
	@FXML Label lb_Content;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
