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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.qna.IQnaService;
import kr.or.ddit.clap.vo.support.QnaVO;
import kr.or.ddit.clap.vo.support.QnaReviewVO;
import javafx.scene.control.TextArea;

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
	JFXButton btn_delete;
	@FXML
	AnchorPane main;
	@FXML
	AnchorPane r_main; //댓글창 전체
	@FXML 
	JFXButton btn_update;
	@FXML
	Text Text_Id;
	@FXML
	Label Text_Content;
	@FXML
	TextArea text_QnaRe; // 댓글 작성 TextArea
	@FXML
	JFXButton btn_QnaRe; // 댓글 버튼
	@FXML
	Label lb_qnaNo;
	@FXML
	Label lb_Content;
	@FXML
	Label lb_Qna_re_no;
	@FXML
	Label lb_Date;
	@FXML
	Label lb_Memid;
	
	public QnaVO qVO = null;
	public QnaReviewVO qrVO = new QnaReviewVO();
	LoginSession ls = new LoginSession();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println(ls.session.getMem_id());
		
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
		
		//btn_delete, btn_update : 관리자, 작성자 구분
		if(LoginSession.session.getMem_auth().equals("t")){
			btn_delete.setVisible(true);
			btn_update.setVisible(false);
		} else {
			btn_delete.setVisible(false);
			if(ls.session.getMem_id().equals(qVO.getMem_id())) {
				btn_delete.setVisible(true);
				btn_update.setVisible(true);
			} else {
				btn_delete.setVisible(false);
				btn_update.setVisible(false);
				
			}
		}
		// ***댓글 등록 버튼 사용자/ 관리자 구분하기***
		
		
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
		
		// 댓글 입력
		// 시퀀스 만들기
		btn_QnaRe.setOnMouseClicked(e -> {
			qrVO.setQna_re_content(text_QnaRe.getText());
			qrVO.setQna_no(ContentNo);
			qrVO.setMem_id(LoginSession.session.getMem_id());
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
