/**
 *이벤트 리스트를 출력하는 화면 controller
 * 
 * 
 * @author Hanhwa
 *
 */
package kr.or.ddit.clap.view.support.eventboard;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import kr.or.ddit.clap.service.eventboard.IEventBoardService;
import kr.or.ddit.clap.vo.support.EventBoardVO;

public class EventShowListController implements Initializable {
	//관리자 페이지

	@FXML
	AnchorPane contents;
	@FXML
	AnchorPane main;
	@FXML
	Pagination e_paging;
	@FXML
	JFXTreeTableView<EventBoardVO> tbl_Event;
	@FXML
	TreeTableColumn<EventBoardVO, String> col_EventNo;
	@FXML
	TreeTableColumn<EventBoardVO, String> col_EventTitle;
	@FXML
	TreeTableColumn<EventBoardVO, String> col_EventSDate;
	@FXML
	TreeTableColumn<EventBoardVO, String> col_EventEDate;
	@FXML
	TreeTableColumn<EventBoardVO, ImageView> col_EventImage;
	@FXML
	JFXComboBox<String> combo_Search;
	@FXML
	JFXButton btn_Add;
	@FXML
	JFXButton btn_search;
	@FXML
	JFXTextField text_Search;
	
	private Registry reg;
	private IEventBoardService ies;
	private ObservableList<EventBoardVO> eventList, currenteventList;
	private int from, to, itemsForPage, totalPageCnt;
	//public static String eventNo;
	//public EventBoardVO eVO = null;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ies = (IEventBoardService) reg.lookup("eventboard");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		col_EventImage.setCellValueFactory(param -> new SimpleObjectProperty<ImageView>(param.getValue().getValue().getImgView()));
		col_EventNo.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getEvent_no()));
		col_EventTitle.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getEvent_title()));
		col_EventSDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getEvent_sdate()));
		col_EventEDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getEvent_edate()));
		
		try {
			
			eventList = FXCollections.observableArrayList(ies.selectListAll());
		} catch(RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}
		
		//데이터 삽입
		TreeItem<EventBoardVO> root = new RecursiveTreeItem<>(eventList,RecursiveTreeObject::getChildren);
		tbl_Event.setRoot(root);
		tbl_Event.setShowRoot(false);
		
		itemsForPage=10; // 한페이지 보여줄 항목 수 설정
		
		paging();
		
		combo_Search.getItems().addAll("제목명");
		combo_Search.setValue(combo_Search.getItems().get(0));
		
		
		//검색버튼 클릭
		btn_search.setOnAction(e ->{
			search();
		});
		
		
		// 더블클릭
		btn_Add.setOnMouseClicked(e -> {
			
			try {
				// 바뀔 화면(FXML)을 가져옴	
				FXMLLoader loader = new FXMLLoader(getClass().getResource("EventContentInsert.fxml"));// init실행됨
				Parent EventInsert = loader.load();
				
				EventContentInsertController cotroller = loader.getController();
				cotroller.givePane(contents); 
				
				main.getChildren().removeAll();
				main.getChildren().setAll(EventInsert);
				
				
			} catch(IOException ee) {
				ee.printStackTrace();
			}
			
			
		});
		
		
		
		
		
		// 더블클릭
		tbl_Event.setOnMouseClicked(e -> {
			if (e.getClickCount()  > 1) {
				int index = tbl_Event.getSelectionModel().getSelectedIndex();
				System.out.println("선택한 인덱스" + index);
				EventBoardVO vo = eventList.get(index);
				System.out.println("번호 : " + vo.getEvent_no());
				String eventNo = vo.getEvent_no();
				
				try {
					System.out.println("업데이트");
					System.out.println("선택한 글 번호 : " + eventNo);
				
					//바뀔 화면(FXML)을 가져옴
					EventContentUpdateController.eventNo = vo.getEvent_no(); //글 번호를 변수로 넘겨줌.
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("EventContentUpdate.fxml"));
					Parent eventUpdate = loader.load();
					EventContentUpdateController controller = loader.getController();
					controller.initData(vo); //eVO
					
					main.getChildren().removeAll();
					main.getChildren().setAll(eventUpdate);
					
					
					main.getChildren().removeAll();
					main.getChildren().setAll(eventUpdate);
					
				} catch(IOException ee) {
					ee.printStackTrace();
				}
			}
			
		});
		
		
		
	}
	
	
	
	
	//페이징  메서드
		private void paging() {
			totalPageCnt = eventList.size() % itemsForPage == 0 ? eventList.size() / itemsForPage
					: eventList.size() / itemsForPage + 1;
			
			e_paging.setPageCount(totalPageCnt); // 전체 페이지 수 설정
			
			e_paging.setPageFactory((Integer pageIndex) -> {
				
				from = pageIndex * itemsForPage;
				to = from + itemsForPage - 1;
				
				
				TreeItem<EventBoardVO> root = new RecursiveTreeItem<>(getTableViewData(from, to), RecursiveTreeObject::getChildren);
				tbl_Event.setRoot(root);
				tbl_Event.setShowRoot(false);
				return tbl_Event;
			});
		}
		
		
	// 페이징에 맞는 데이터를 가져옴
	private ObservableList<EventBoardVO> getTableViewData(int from, int to) {

		currenteventList = FXCollections.observableArrayList(); //
		int totSize = eventList.size();
		for (int i = from; i <= to && i < totSize; i++) {

			currenteventList.add(eventList.get(i));
		}

		return currenteventList;

	}
		
	// 검색 메서드
	private void search() {
		try {
			EventBoardVO vo = new EventBoardVO();
			ObservableList<EventBoardVO> searchlist = FXCollections.observableArrayList();

			switch (combo_Search.getValue()) {

			case "제목명":
				vo.setEvent_title(text_Search.getText());
				searchlist = FXCollections.observableArrayList(ies.searchList(vo));
				break;

			default:
				break;

			}

			eventList = FXCollections.observableArrayList(searchlist); // 검색조건에 맞는 리스트를 저장
			paging();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		

}