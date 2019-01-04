/**
 *  문의사항 페이지를 출력하는 화면 controller
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
import kr.or.ddit.clap.service.qna.IQnaService;
import kr.or.ddit.clap.view.singer.singer.ShowSingerDetailController;
import kr.or.ddit.clap.vo.singer.SingerVO;
import kr.or.ddit.clap.vo.support.QnaVO;

public class QnaMenuController implements Initializable {

	@FXML
	Pagination p_paging;
	@FXML
	JFXTreeTableView<QnaVO> tbl_qna;
	@FXML
	TreeTableColumn<QnaVO, String> col_qnaNumber;
	@FXML
	TreeTableColumn<QnaVO, String> col_qnaTitle;
	@FXML
	TreeTableColumn<QnaVO, String> col_qnaDate;
	@FXML
	TreeTableColumn<QnaVO, String> col_qnaViewCnt;
	@FXML
	AnchorPane main;

	private Registry reg;
	private IQnaService iqs;
	private ObservableList<QnaVO> qnaList, currentqnaList;
	private int from, to, itemsForPage, totalPageCnt;

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
		
		col_qnaDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getQna_indate()));
		col_qnaNumber.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getQna_no()));
		col_qnaTitle.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getQna_title()));
		col_qnaViewCnt.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getQna_view_cnt()));

		try {
			qnaList = FXCollections.observableArrayList(iqs.selectListAll());
			
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}

		// 데이터 삽입
		TreeItem<QnaVO> root = new RecursiveTreeItem<>(qnaList, RecursiveTreeObject::getChildren);
		tbl_qna.setRoot(root);
		tbl_qna.setShowRoot(false);

		itemsForPage = 10; // 한페이지 보여줄 항목 수 설정

		paging();
		
		
		// 더블클릭
		tbl_qna.setOnMouseClicked(e -> {
			if (e.getClickCount() > 1) {
				int index = tbl_qna.getSelectionModel().getSelectedIndex();
				System.out.println("선택한 인덱스 : " + index);
				QnaVO vo = qnaList.get(index);
				System.out.println("번호:" + vo.getQna_no());
				String ContentNo = vo.getQna_no();

				try {
					// 바뀔 화면(FXML)을 가져옴
					QnaDetailContentController.ContentNo = vo.getQna_no();// 번호을 변수로 넘겨줌
					System.out.println(vo.getQna_no());

					FXMLLoader loader = new FXMLLoader(getClass().getResource("QnaDetailContent.fxml"));// init실행됨
					Parent singerDetail = loader.load();
					main.getChildren().removeAll();
					main.getChildren().setAll(singerDetail);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		

	}

	// 페이징 메서드
	private void paging() {
		totalPageCnt = qnaList.size() % itemsForPage == 0 ? qnaList.size() / itemsForPage
				: qnaList.size() / itemsForPage + 1;

		p_paging.setPageCount(totalPageCnt); // 전체 페이지 수 설정

		p_paging.setPageFactory((Integer pageIndex) -> {

			from = pageIndex * itemsForPage;
			to = from + itemsForPage - 1;

			TreeItem<QnaVO> root = new RecursiveTreeItem<>(getTableViewData(from, to),
					RecursiveTreeObject::getChildren);
			tbl_qna.setRoot(root);
			tbl_qna.setShowRoot(false);
			return tbl_qna;
		});
	}

	// 페이징에 맞는 데이터를 가져옴
	private ObservableList<QnaVO> getTableViewData(int from, int to) {

		currentqnaList = FXCollections.observableArrayList(); //
		int totSize = qnaList.size();
		for (int i = from; i <= to && i < totSize; i++) {

			currentqnaList.add(qnaList.get(i));
		}

		return currentqnaList;
	}

}
