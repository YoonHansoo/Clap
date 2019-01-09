/**
 *이벤트 리스트를 출력하는 화면 controller
 * 
 * 
 * @author Hanhwa
 *
 */
package kr.or.ddit.clap.view.support.eventboard;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import kr.or.ddit.clap.service.eventboard.IEventBoardService;
import kr.or.ddit.clap.vo.support.EventBoardVO;

public class EventShowListController implements Initializable {

	@FXML
	AnchorPane contents;
	@FXML
	AnchorPane main;
	@FXML
	Pagination paging;
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
	TreeTableColumn<EventBoardVO, String> col_EventContent;
	@FXML
	TreeTableColumn<EventBoardVO, ImageView> col_EventImage;
	@FXML
	JFXComboBox<String> combo_Search;
	@FXML
	JFXButton btn_Add;
	
	private Registry reg;
	private IEventBoardService ies;
	private ObservableList<EventBoardVO> eventList, currenteventList;
	private int from, to, itemsForPage, totalPageCnt;
	
	
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
		
		//col_EventImage.setCellValueFactory(param -> new SimpleObjectProperty<ImageView>(param.getValue().getValue().getEvent_image()));
		// vo에 imageview 물어보고 고치기
		
		
		
		
		
	}

}
