package kr.or.ddit.clap.view.support.eventboard;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.Pagination;

public class EventClientShowListController implements Initializable  {

	@FXML
	AnchorPane e_main;
	@FXML
	JFXTreeTableView tbl_Event;
	@FXML
	TreeTableColumn col_EventNo;
	@FXML
	TreeTableColumn col_EventImg;
	@FXML
	TreeTableColumn col_EventTitle;
	@FXML
	TreeTableColumn col_EventSDate;
	@FXML
	TreeTableColumn col_EventEDate;
	@FXML
	TreeTableColumn col_EventCnt;
	@FXML
	Pagination e_paging;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
