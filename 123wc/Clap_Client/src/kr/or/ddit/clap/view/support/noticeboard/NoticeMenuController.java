package kr.or.ddit.clap.view.support.noticeboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import kr.or.ddit.clap.service.qna.IQnaService;
import kr.or.ddit.clap.vo.support.NoticeBoardVO;
import kr.or.ddit.clap.vo.support.QnaVO;
import javafx.scene.control.Pagination;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableView;

public class NoticeMenuController implements Initializable {
	
	private Registry reg;
	private INoticeBoardService ins;
	public NoticeBoardVO nVO = new NoticeBoardVO();

	@FXML
	JFXTreeTableView<NoticeBoardVO> tbl_notice;
	@FXML
	TreeTableColumn<NoticeBoardVO, String> col_noticeNo;
	@FXML
	TreeTableColumn<NoticeBoardVO, String> col_noticeTitle;
	@FXML
	TreeTableColumn<NoticeBoardVO, String> col_noticeDate;
	@FXML
	TreeTableColumn<NoticeBoardVO, String> col_noticeCnt;
	@FXML
	AnchorPane main;
	@FXML
	Pagination n_paging;
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ins = (INoticeBoardService) reg.lookup("notice");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		
	}

}
