package kr.or.ddit.clap.view.message;

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
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.message.IMessageService;
import kr.or.ddit.clap.view.member.manage.MemberDetailController;
import kr.or.ddit.clap.vo.support.MessageVO;

public class ShowMessageController implements Initializable{

	private Registry reg;
	private IMessageService imsgs;
	private String  user_id =LoginSession.session.getMem_id();
	
	private ObservableList<MessageVO> msgList, currentMsgList;
	private int from, to, itemsForPage, totalPageCnt;
	@FXML JFXTreeTableView<MessageVO> tbl_Message;
	@FXML TreeTableColumn<MessageVO,ImageView> col_imgeview;
	@FXML TreeTableColumn<MessageVO ,String> col_title;
	@FXML TreeTableColumn<MessageVO ,String> col_SendId;
	@FXML TreeTableColumn<MessageVO ,String> col_SendDate;
	@FXML TreeTableColumn<MessageVO ,String> col_ReadDate;
	@FXML Pagination p_paging;
	@FXML AnchorPane contents; 
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			imsgs = (IMessageService) reg.lookup("message");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		MessageVO vo  =new MessageVO();
		vo.setMem_get_id(user_id);
		try {
			msgList=FXCollections.observableArrayList(imsgs.selectMessage(vo));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < msgList.size(); i++) {
			if(msgList.get(i).getMsg_read_tf().equals("f")) {
				//이미지 넣어주기
//				 ImageView img = new ImageView("https://image.shutterstock.com/image-vector/speech-bubble-message-icon-260nw-527527990.jpg");
//				 msgList.get(i).setImageview(img);
//				 msgList.get(i).getImageview().setFitWidth(80);
//				 msgList.get(i).getImageview().setFitHeight(100);
			}else {
				
			}
		}
		col_imgeview.setCellValueFactory(param -> new SimpleObjectProperty<ImageView>(param.getValue().getValue().getImageview()));

		col_title.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMsg_title()));

		col_SendId
				.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMem_send_id()));

		col_SendDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMsg_send_date().substring(0, 10)));

		col_ReadDate.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getValue().getMsg_read_date()));

		TreeItem<MessageVO> root = new RecursiveTreeItem<>(msgList, RecursiveTreeObject::getChildren);
		tbl_Message.setRoot(root);
		tbl_Message.setShowRoot(false);

		itemsForPage = 10; // 한페이지 보여줄 항목 수 설정

		paging();
		
		
		//두번클릭시
		tbl_Message.setOnMouseClicked(e ->{
			if (e.getClickCount()  > 1) {
				//int index = tbl_Message.getSelectionModel().getSelectedIndex();
				String msgno = tbl_Message.getSelectionModel().getSelectedItem().getValue().getMsg_no();
				
				try {
					//바뀔 화면(FXML)을 가져옴
					MessageTextController.msgno = msgno;//가수번호를 변수로 넘겨줌
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("mestext.fxml"));// init실행됨
					Parent messageText= loader.load(); 
					
					MessageTextController cotroller = loader.getController();
					cotroller.givePane(contents); 
					
					contents.getChildren().removeAll();
					contents.getChildren().setAll(messageText);
					
					
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
		});
	}

	private void paging() {
		totalPageCnt = msgList.size() % itemsForPage == 0 ? msgList.size() / itemsForPage
				: msgList.size() / itemsForPage + 1;
		
		p_paging.setPageCount(totalPageCnt); // 전체 페이지 수 설정
		
		p_paging.setPageFactory((Integer pageIndex) -> {
			
			from = pageIndex * itemsForPage;
			to = from + itemsForPage - 1;
			
			
			TreeItem<MessageVO> root = new RecursiveTreeItem<>(getTableViewData(from, to), RecursiveTreeObject::getChildren);
			tbl_Message.setRoot(root);
			tbl_Message.setShowRoot(false);
			return tbl_Message;
		});
	}
	
	//페이징에 맞는 데이터를 가져옴
private ObservableList<MessageVO> getTableViewData(int from, int to) {
		
	currentMsgList = FXCollections.observableArrayList(); //
		int totSize = msgList.size();
		for (int i = from; i <= to && i < totSize; i++) {
			
			currentMsgList.add(msgList.get(i));
		}
		
		return currentMsgList;
	
	}

}
