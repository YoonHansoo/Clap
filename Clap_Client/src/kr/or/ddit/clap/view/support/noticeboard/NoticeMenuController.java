package kr.or.ddit.clap.view.support.noticeboard;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import kr.or.ddit.clap.service.noticeboard.INoticeBoardService;
import kr.or.ddit.clap.vo.support.NoticeBoardVO;
import com.jfoenix.controls.JFXButton;

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
	AnchorPane d_main;
	@FXML
	Pagination n_paging;
	@FXML
	JFXButton btn_add;
	
	private Registry reg;
	private INoticeBoardService ins;
	//public NoticeBoardVO nVO = new NoticeBoardVO();
	private ObservableList<NoticeBoardVO> ntcList, currentntcList;
	private int from, to, itemsForPage, totalPageCnt;
	@FXML AnchorPane main;
	
	
	
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
		
		col_noticeNo.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getNotice_no()));
		col_noticeTitle.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getNotice_title()));
		col_noticeDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getNotice_indate()));
		col_noticeCnt.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getNotice_view_cnt()));
		
		
		
		
		try {
			ntcList = FXCollections.observableArrayList(ins.selectListAll());
			
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}
		
		// 데이터 삽입
				TreeItem<NoticeBoardVO> root = new RecursiveTreeItem<>(ntcList, RecursiveTreeObject::getChildren);
				tbl_notice.setRoot(root);
				tbl_notice.setShowRoot(false);
				
				itemsForPage = 10; // 한페이지 보여줄 항목 수 설정
				
				paging();
				
				
				//더블클릭
				tbl_notice.setOnMouseClicked(e -> {
					if (e.getClickCount() > 1) {
						int index = tbl_notice.getSelectionModel().getSelectedIndex();
						System.out.println("선택한 인덱스 : " + index);
						NoticeBoardVO vo = ntcList.get(index);
						System.out.println("번호:" + vo.getNotice_no());
						String NoticeNo = vo.getNotice_no();
						
						try {
							// 바뀔 화면(FXML)을 가져옴
							NoticeBoardDetailContentController.NoticeNo = vo.getNotice_no();// 번호을 변수로 넘겨줌
							System.out.println(vo.getNotice_no());

							FXMLLoader loader = new FXMLLoader(getClass().getResource("NoticeBoardDetailContent.fxml"));// init실행됨
							Parent noticeDetail = loader.load();
							d_main.getChildren().removeAll();
							d_main.getChildren().setAll(noticeDetail);

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				
				
				btn_add.setOnAction(e -> {
					
					try {
						// 바뀔 화면(FXML)을 가져옴	

						FXMLLoader loader = new FXMLLoader(getClass().getResource("NoticeBoardInsert.fxml"));// init실행됨
						Parent NoticeInsert = loader.load();
						main.getChildren().removeAll();
						main.getChildren().setAll(NoticeInsert);

					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					
				});
				
				
				
		
		
	}
	
	
	// 페이징 메서드
		private void paging() {
			totalPageCnt = ntcList.size() % itemsForPage == 0 ? ntcList.size() / itemsForPage
					: ntcList.size() / itemsForPage + 1;

			n_paging.setPageCount(totalPageCnt); // 전체 페이지 수 설정

			n_paging.setPageFactory((Integer pageIndex) -> {

				from = pageIndex * itemsForPage;
				to = from + itemsForPage - 1;

				TreeItem<NoticeBoardVO> root = new RecursiveTreeItem<>(getTableViewData(from, to),
						RecursiveTreeObject::getChildren);
				tbl_notice.setRoot(root);
				tbl_notice.setShowRoot(false);
				return tbl_notice;
			});
		}
		
		// 페이징에 맞는 데이터를 가져옴
		private ObservableList<NoticeBoardVO> getTableViewData(int from, int to) {

			currentntcList = FXCollections.observableArrayList(); //
			int totSize = ntcList.size();
			for (int i = from; i <= to && i < totSize; i++) {

				currentntcList.add(ntcList.get(i));
			}

			return currentntcList;
		}
	
	
	
	
	
	
	
	
	
	

}
