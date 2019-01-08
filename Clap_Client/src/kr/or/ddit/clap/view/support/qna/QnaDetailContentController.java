/**
 *  문의한 내용의 상세 내용을 나타내는 페이지를 출력하는 화면 controller
 *  
 *  @author hanhwa
 */
package kr.or.ddit.clap.view.support.qna;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import kr.or.ddit.clap.service.qna.IQnaService;
import kr.or.ddit.clap.vo.support.QnaVO;
import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.AnchorPane;

public class QnaDetailContentController implements Initializable {
	
	public static String ContentNo;
	private Registry reg;
	private IQnaService iqs;
	
	@FXML
	Text Text_QnaType;
	@FXML
	Text Text_QnaTitle;
	@FXML
	Text Text_Date;
	@FXML
	Text Text_Content;
	@FXML
	JFXButton btn_delete;
	@FXML
	AnchorPane main;
	
	public QnaVO qVO = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			System.out.println(ContentNo);
			//reg로 IQnaService객체를 받아옴
			reg = LocateRegistry.getRegistry("localhost", 8888);  
			iqs = (IQnaService) reg.lookup("qna");
			qVO = iqs.qnaDetailContent(ContentNo);
			System.out.println(qVO.getQna_no());
			//파라미터로 받은 정보를 PK로 상세정보를 가져옴
		} catch (RemoteException e) {
			System.out.println(1);
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println(2);
			e.printStackTrace();
		}
		
		Text_QnaType.setText(qVO.getQna_type());
		Text_QnaTitle.setText(qVO.getQna_title());
		Text_Date.setText(qVO.getQna_indate());
		Text_Content.setText(qVO.getQna_content());
		
		
		
		
		btn_delete.setOnMouseClicked(e -> {
			
			//Alert창을 출력해 정말 삭제할 지 물어봄
			try {
				if(0>alertConfrimDelete()) {
					return;
				}
				
				int cnt = iqs.deleteQnaContent(ContentNo);
				
				
				
			} catch(RemoteException ee) {
				ee.printStackTrace();
			}
			
		});
		
	}
		
		//사용자가 확인을 누르면 1을 리턴 이외는 -1
		public int alertConfrimDelete() {
			Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
		      
		      alertConfirm.setTitle("CONFIRMATION");
		      alertConfirm.setContentText("삭제하시면 복구가 불가능합니다.");
		      
		      // Alert창을 보여주고 사용자가 누른 버튼 값 읽어오기
		      ButtonType confirmResult = alertConfirm.showAndWait().get();
		      
		      if (confirmResult == ButtonType.OK) {
		         System.out.println("OK 버튼을 눌렀습니다.");
		         
		         Parent root1;
					try {
						root1 = FXMLLoader.load(getClass().getResource("QnaMenuList.fxml"));
						main.getChildren().removeAll();
						main.getChildren().setAll(root1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		         
		         return 1;
		      } else if (confirmResult == ButtonType.CANCEL) {
		         System.out.println("취소 버튼을 눌렀습니다.");
		         return -1;
		      }
		      return -1;
		}

}
