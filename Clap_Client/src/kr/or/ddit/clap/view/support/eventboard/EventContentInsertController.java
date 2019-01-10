/**
 * 이벤트관리 emdfhr화면 컨트롤러
 * @author Hanhwa
 * 
 */
package kr.or.ddit.clap.view.support.eventboard;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;




public class EventContentInsertController implements Initializable {

	@FXML
	AnchorPane main;
	@FXML
	ImageView imgview_singImg;
	@FXML
	JFXTextField text_Title;
	@FXML
	JFXTextField text_Sdate;
	@FXML
	JFXTextField text_Edate;
	@FXML
	Label label_Id;
	@FXML
	JFXButton btn_insertImg;
	@FXML
	JFXButton btn_Insert;
	@FXML
	JFXButton btn_cancel;
	@FXML
	JFXTextArea text_Content;
	
	public static AnchorPane contents;
	
	//ShowSingerList.fxml는 VBOX를 포함한 전부이기 때문에
	//현재 씬의 VBox까지 모두 제거 후   ShowSingerList를 불러야함.
	public void givePane(AnchorPane contents) {
		this.contents = contents;
		System.out.println("contents 적용완료");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
