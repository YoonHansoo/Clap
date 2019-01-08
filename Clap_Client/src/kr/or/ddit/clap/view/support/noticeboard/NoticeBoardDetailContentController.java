/**
 *  문의한 내용의 상세 내용을 나타내는 페이지를 출력하는 화면 controller
 *  
 *  @author hanhwa
 */
package kr.or.ddit.clap.view.support.noticeboard;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import kr.or.ddit.clap.service.noticeboard.INoticeBoardService;
import kr.or.ddit.clap.vo.support.NoticeBoardVO;

public class NoticeBoardDetailContentController implements Initializable {
	
	public static String NoticeNo;
	private Registry reg;
	private INoticeBoardService ins;
	
	@FXML
	Text Text_NtcNo;
	@FXML
	Text Text_NtcTitle;
	@FXML
	Text Text_NtcDate;
	@FXML
	Text Text_Content;
	@FXML
	AnchorPane main;
	
	public NoticeBoardVO nVO = null;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			System.out.println("noticeNO??" + NoticeNo);
			//reg로 IQnaService객체를 받아옴
			reg = LocateRegistry.getRegistry("localhost", 8888);  
			ins = (INoticeBoardService) reg.lookup("notice");
			nVO = ins.NoticeBoardDetailContent(NoticeNo);
			System.out.println("noticeNO??>>" + nVO.getNotice_no());
			//파라미터로 받은 정보를 PK로 상세정보를 가져옴
		} catch (RemoteException e) {
			System.out.println("Detail 1");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println(2);
			e.printStackTrace();
		}
		
		Text_NtcNo.setText(nVO.getNotice_no());
		Text_NtcTitle.setText(nVO.getNotice_title());
		Text_NtcDate.setText(nVO.getNotice_indate());
		Text_Content.setText(nVO.getNotice_content());
		
		
		
		
	}

}
