/**
 *  문의한 내용의 상세 내용을 나타내는 페이지를 출력하는 화면 controller
 *  
 *  @author hanhwa
 */
package kr.or.ddit.clap.view.support.qna;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
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
		
		
	}

}
