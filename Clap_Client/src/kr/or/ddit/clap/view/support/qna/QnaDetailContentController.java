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

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.qna.IQnaService;
import kr.or.ddit.clap.vo.support.QnaVO;

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
	@FXML 
	JFXButton btn_update;
	@FXML
	Text Text_Id;
	
	public QnaVO qVO = null;
	LoginSession ls = new LoginSession();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		// 관리자 모드 - 삭제창
		if(LoginSession.session.getMem_auth().equals("t")){
			btn_delete.setVisible(true);
			btn_update.setVisible(false);
		} else {
			btn_delete.setVisible(false);
		}
		if(LoginSession.session.getMem_auth().equals("f")) {
			//수정, 삭제 버튼 구분하기
			if(ls.session.getMem_id().equals(qVO.getMem_id())) {
				//사용자일 때, 작성자인지 아닌지 구분해서 삭제, 수정 버튼 구분하기
			}
		}
		/*//LoginSession.session.getMem_auth().equals("t")
		if(LoginSession.session.getMem_auth().equals("f")) {
			if(!(LoginSession.session.getMem_auth().equals(Text_Id))) {
				
			btn_delete.setVisible(false);
			btn_update.setVisible(false);
			}
		}else {
			
		}*/
		// 사용자 모드 - 수정창 만들기
		
		
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
		Text_Id.setText(qVO.getMem_id());
		
		
		
		
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
		
		
		btn_update.setOnMouseClicked(e -> {
			try {
				
				System.out.println("수 정");
				// 바뀔 화면(FXML)을 가져옴
				QnaContentUpdateController.ContentNo = ContentNo; //번호를 변수로 넘겨줌
				FXMLLoader loader = new FXMLLoader(getClass().getResource("QnaContentUpdate.fxml"));
				Parent UpdateQna = loader.load();
				QnaContentUpdateController controller = loader.getController();
				controller.initData(qVO);
				main.getChildren().removeAll();
				main.getChildren().setAll(UpdateQna);
				
			} catch(IOException ee) {
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
