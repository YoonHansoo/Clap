/**
 * 이벤트관리 등록 화면 컨트롤러
 * @author Hanhwa
 * 
 */
package kr.or.ddit.clap.view.support.eventboard;

import java.io.File;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kr.or.ddit.clap.service.eventboard.IEventBoardService;
import kr.or.ddit.clap.service.singer.ISingerService;




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
	private FileChooser fileChooser;
	private File filePath;
	private String img_path;
	private Registry reg;
	private IEventBoardService ies;
	
	//ShowSingerList.fxml는 VBOX를 포함한 전부이기 때문에
	//현재 씬의 VBox까지 모두 제거 후   ShowSingerList를 불러야함.
	public void givePane(AnchorPane contents) {
		this.contents = contents;
		System.out.println("contents 적용완료");
		
	}
	
	// 전 화면에 있는 데이터를 그대로 가져와  세팅해주는 메서드
	public void initData() {
		System.out.println("initData");
		
		img_path = "file:\\\\\\\\Sem-pc\\\\공유폴더\\\\Clap\\\\img\\\\noImg.png";
		Image img = new Image(img_path); 
		imgview_singImg.setImage(img);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ies = (IEventBoardService) reg.lookup("eventboard");
			initData();
		} catch(RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		/*@FXML
		public void btn_insertImg(ActionEvent event) {
			Stage stage =  (Stage) ((Node)event.getSource()).getScene().getWindow();
			 fileChooser = new FileChooser();
			 fileChooser.setTitle("Open image");
		}*/
		
		
		
		
	}

}
