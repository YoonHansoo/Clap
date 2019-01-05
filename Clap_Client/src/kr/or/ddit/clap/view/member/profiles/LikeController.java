package kr.or.ddit.clap.view.member.profiles;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.ImageView;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.like.ILikeService;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicLikeVO;
import kr.or.ddit.clap.vo.singer.SingerVO;
import javafx.scene.control.TableColumn;

public class LikeController implements Initializable{
	private Registry reg;
	private IMypageService ims;
	@FXML TableView<Map> tbl_Like;
	@FXML TableColumn<Map,CheckBox> col_Checks;
	@FXML TableColumn col_No;
	@FXML TableColumn<Map,ImageView> col_Img;
	@FXML TableColumn<Map,String> col_MusInfo;
	@FXML TableColumn<Map,String> col_Its;
	@FXML TableColumn<Map,String> col_Alb;
	@FXML TableColumn<Map,String>  col_LikeIndate;
	@FXML TableColumn<Map,String>  col_Like;
	private ObservableList<Map> likeList;
	

	int i;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ims = (IMypageService) reg.lookup("mypage");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		/*col_Img
		.setCellValueFactory(param -> new SimpleObjectProperty<ImageView>(likeList.get(0).get("SING_IMAGE").toString()));
*/
		String user_id=LoginSession.session.getMem_id();
		MemberVO vo = new MemberVO();
		vo.setMem_id(user_id);
		try {
			likeList = FXCollections.observableArrayList((ims.selectMusLike(vo)));
			System.out.println(		likeList.get(0).get("SING_NAME").toString());
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}    
	
		for ( i = 1; i <= likeList.size()-1; i++) {
		col_Its.setCellValueFactory(
				param -> new SimpleStringProperty(likeList.get(i-1).get("SING_NAME").toString()));
		
		col_LikeIndate.setCellValueFactory(
				param -> new SimpleStringProperty(likeList.get(i-1).get("MUS_LIKE_DATE").toString()));

		}
		tbl_Like.setItems(likeList);
		
	/*	itemsForPage=10; // 한페이지 보여줄 항목 수 설정
		*/
		//paging();
	
		
		
	}
}
