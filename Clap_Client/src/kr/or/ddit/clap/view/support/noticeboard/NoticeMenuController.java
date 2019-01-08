package kr.or.ddit.clap.view.support.noticeboard;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import kr.or.ddit.clap.service.noticeboard.INoticeBoardService;
import kr.or.ddit.clap.vo.support.NoticeBoardVO;

public class NoticeMenuController implements Initializable {
	
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
	
	private Registry reg;
	private INoticeBoardService ins;
	//public NoticeBoardVO nVO = new NoticeBoardVO();
	private ObservableList<NoticeBoardVO> ntcList, currentntcList;
	private int from, to, itemsForPage, totalPageCnt;
	
	
	
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
		
		
		try {
			ntcList = FXCollections.observableArrayList(ins.selectListAll());
			
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}
		
		
	}

}
