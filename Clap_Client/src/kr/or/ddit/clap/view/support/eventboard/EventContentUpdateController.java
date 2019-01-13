package kr.or.ddit.clap.view.support.eventboard;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kr.or.ddit.clap.service.eventboard.IEventBoardService;
import kr.or.ddit.clap.vo.support.EventBoardVO;

public class EventContentUpdateController implements Initializable  {

	@FXML
	AnchorPane main;
	@FXML
	ImageView imgview_eventImg;
	@FXML
	JFXTextField text_Title;
	@FXML
	JFXTextField text_SDate;
	@FXML
	JFXTextField text_EDate;
	@FXML
	Label label_Id;
	@FXML
	JFXButton btn_updateImg;
	@FXML
	JFXButton btn_update;
	@FXML
	JFXButton btn_cancel;
	@FXML
	JFXTextArea text_Content;
	
	private FileChooser fileChooser;
	private File filePath;
	private String img_path;
	private Registry reg;
	private IEventBoardService ies;
	public static String eventNo; // 파라미터로 받은 선택한 글 번호
	//public static AnchorPane contents;
	
	/*public void givePane(AnchorPane contents) {
		this.contents = contents;
		System.out.println("contents 적용 완료");
	}*/
	
	
	public void initData(EventBoardVO eVO) {
		System.out.println("initData");
		
		img_path = eVO.getEvent_image(); //이미지경로를 전역에 저장
		Image img = new Image(img_path); //이미지 객체등록
		imgview_eventImg.setImage(img);
		
		text_Title.setText(eVO.getEvent_title());
		text_SDate.setText(eVO.getEvent_sdate());
		text_EDate.setText(eVO.getEvent_edate());
		label_Id.setText(eVO.getMem_id());
		text_Content.setText(eVO.getEvent_content());
		
		
	}
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//System.out.println("글 번호 : " + eventNo);
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ies = (IEventBoardService) reg.lookup("eventboard");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		
		btn_updateImg.setOnAction(e -> {
			
			//에러 해결하기
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			fileChooser = new FileChooser();
			fileChooser.setTitle("Open image");
			
			//사용자에 화면에 해당 디렉토리가 기본값으로 보여짐 
			String userDirectoryString = "\\\\Sem-pc\\공유폴더\\Clap\\img\\event";
			
			System.out.println("userDirectoryString:" + userDirectoryString);
			File userDirectory = new File(userDirectoryString); 
			
			if(!userDirectory.canRead()) { //예외시?
				 userDirectory = new File("c:/");
			 }
			
			fileChooser.setInitialDirectory(userDirectory);
			this.filePath = fileChooser.showOpenDialog(stage);
			
			//이미지를 새로운 이미지로 바꿈
			 try {
				 BufferedImage bufferedImage = ImageIO.read(filePath);
				 Image image =  SwingFXUtils.toFXImage(bufferedImage, null);
				 imgview_eventImg.setImage(image);
				 System.out.println("파일경로 : " + filePath);
				 String str_filePath = "file:"+filePath;				 
				 img_path = str_filePath;
				 System.out.println(img_path);
			 } catch(Exception e1) {
				 System.out.println("이미지를 선택하지 않았습니다.");
			 }
			
		});
		
		
		
		btn_update.setOnAction(e -> {
			
			if(text_Title.getText().isEmpty() || text_SDate.getText().isEmpty() ||
			   text_EDate.getText().isEmpty() || text_Content.getText().isEmpty()) {
				
				errMsg("작업오류", "빈 항목이 있습니다.");
				return;
			}
			
			EventBoardVO eVO = new EventBoardVO();
			eVO.setEvent_no(eventNo);
			eVO.setEvent_image(img_path);
			eVO.setEvent_title(text_Title.getText());
			eVO.setEvent_sdate(text_SDate.getText());
			eVO.setEvent_edate(text_EDate.getText());
			eVO.setEvent_content(text_Content.getText());
			eVO.setMem_id(label_Id.getText());
			
			try {
				//이 부분 업데이트 안됨.
				//Cause: java.sql.SQLDataException: ORA-01861: literal does not match format string
				// Date쪽 부분 문제
				ies.updateEvent(eVO);
				System.out.println("update 완료");
				//업데이트 왜 안돼..
			} catch(RemoteException ee) {
				ee.printStackTrace();
			}
			
			infoMsg("update 완료", "이벤트 - update가 되었습니다.");
			
			Parent root1;
			try {
				root1 = FXMLLoader.load(getClass().getResource("EventShowList.fxml"));
				main.getChildren().removeAll();
				main.getChildren().setAll(root1);
				
			} catch(IOException eee) {
				eee.printStackTrace();
			}
			
		});
		
	}
	
	
	public void errMsg(String headerText, String msg) {
		Alert errAlert = new Alert(AlertType.ERROR);
		errAlert.setTitle("오류");
		errAlert.setHeaderText(headerText);
		errAlert.setContentText(msg);
		errAlert.showAndWait();
	}
	
	public void infoMsg(String headerText, String msg) {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setTitle("정보 확인");
		infoAlert.setHeaderText(headerText);
		infoAlert.setContentText(msg);
		infoAlert.showAndWait();
	}
	

}
