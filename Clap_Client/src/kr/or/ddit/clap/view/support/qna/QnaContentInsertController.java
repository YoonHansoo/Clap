package kr.or.ddit.clap.view.support.qna;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import kr.or.ddit.clap.service.qna.IQnaService;
import kr.or.ddit.clap.vo.support.QnaVO;

public class QnaContentInsertController implements Initializable {
	
	private Registry reg;
	private IQnaService iqs;
	public QnaVO qVO;
	
	@FXML
	ComboBox Com_Type;
	@FXML
	JFXTextField Text_QnaMemTel;
	@FXML
	JFXTextField Text_QnaMemEmail;
	@FXML
	JFXTextField Text_QnaTitle;
	@FXML
	JFXTextField Text_QnaContent;
	@FXML
	JFXButton btn_File;
	@FXML
	Label Lb_MemId;
	@FXML
	JFXButton btn_Add;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			iqs = (IQnaService) reg.lookup("qna");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		Com_Type.getItems().addAll("이용방법","벨/링","선물하기","이벤트","결제/요금제","음원오류","14세 미만 가입/승인","인증 문의","회원/로그인","다운로드","오류문의","개선사항","기타");
		Com_Type.setValue("선택하세요");
		Com_Type.setOnAction(e -> {
			qVO.setQna_type(Com_Type.getValue().toString());
		});
		
		
		
			
		
	}

}
