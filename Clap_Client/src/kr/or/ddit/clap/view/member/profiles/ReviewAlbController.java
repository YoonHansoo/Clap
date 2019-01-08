package kr.or.ddit.clap.view.member.profiles;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import kr.or.ddit.clap.service.album.IAlbumReviewService;
import kr.or.ddit.clap.service.musicreview.IMusicReviewService;
import kr.or.ddit.clap.vo.album.AlbumReviewVO;
import kr.or.ddit.clap.vo.music.MusicReviewVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.control.Pagination;

public class ReviewAlbController  implements Initializable{
	private Registry reg;
	private IAlbumReviewService iars;

	@FXML AnchorPane Head;
	@FXML JFXTreeTableView<AlbumReviewVO> tbl_Review;
	@FXML TreeTableColumn<AlbumReviewVO,ImageView> col_imge;
	@FXML TreeTableColumn<AlbumReviewVO,String> col_title;
	@FXML TreeTableColumn<AlbumReviewVO,String>col_Its;
	@FXML TreeTableColumn<AlbumReviewVO,String> col_Reviewcon;
	@FXML TreeTableColumn<AlbumReviewVO,String> col_ReviwIndate;
	@FXML TreeTableColumn<AlbumReviewVO,JFXButton> col_del;
	@FXML Pagination p_Paging;

	private ObservableList<MusicReviewVO> reviewList, currentsingerList;
	private int from, to, itemsForPage, totalPageCnt;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			iars = (IAlbumReviewService) reg.lookup("albreview");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}

}
