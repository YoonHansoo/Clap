package kr.or.ddit.clap.view.member.mypage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.musichistory.IMusicHistoryService;
import kr.or.ddit.clap.service.musicreview.IMusicReviewService;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.music.MusicHistoryVO;
import kr.or.ddit.clap.vo.music.MusicReviewVO;
import javafx.scene.image.ImageView;;

public class MypageController implements Initializable {
	int no1=0;
	int no2=0;
	int no3=0;
	static Stage mypageDialog = new Stage(StageStyle.DECORATED);
	static Stage pwok = new Stage(StageStyle.DECORATED);
	
	private Registry reg;
	private IMypageService ims;
	private IMusicReviewService imrs;
	private IMusicHistoryService imhs;
	private ObservableList<MusicReviewVO> revList, currentrevList;
	private ObservableList<MusicHistoryVO> singList;	//최근음악 담기
	private ObservableList<MusicHistoryVO> newList;
	
	private String temp_img_path="";
	
	@FXML Label label_Id;
	@FXML Image img_User;
	@FXML AnchorPane contents;
	@FXML Text text_UserInfo;
	@FXML AnchorPane InfoContents;
	@FXML AnchorPane Head;
	
	@FXML JFXTreeTableView<MusicReviewVO> tbl_Review; 	//리뷰
	@FXML TreeTableColumn<MusicReviewVO, String> col_ReviewCont;
	@FXML TreeTableColumn<MusicReviewVO, String> col_ReviewDate;

	@FXML JFXTreeTableView<MusicHistoryVO> tbl_ManySigner;	//많이들은 아티스트
	@FXML TreeTableColumn col_MSno;
	@FXML TreeTableColumn<MusicHistoryVO, String> col_MSits;
	
	@FXML JFXTreeTableView<MusicHistoryVO> tbl_ManyMusic;	//많이들은 곡
	@FXML TreeTableColumn col_MMno;
	@FXML TreeTableColumn<MusicHistoryVO, String> col_MMits;
	@FXML TreeTableColumn<MusicHistoryVO, String> col_MMtitle;
	
	@FXML JFXTreeTableView tbl_NewMusic;					//최근들은곡
	@FXML TreeTableColumn col_NMno;
	@FXML TreeTableColumn<MusicHistoryVO, String> col_NMits;
	@FXML TreeTableColumn<MusicHistoryVO, String> col_NMtitle;
	@FXML TreeTableColumn<MusicHistoryVO, String> col_NMdate;
	@FXML ImageView img_UserImg;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ims = (IMypageService) reg.lookup("mypage");
			imrs = (IMusicReviewService) reg.lookup("musicreview");
			imhs = (IMusicHistoryService) reg.lookup("history");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		String user_id = LoginSession.session.getMem_id();
		label_Id.setText(user_id); // 현재 로그인한 사용자 아이디 가져오기
		MemberVO vo = new MemberVO();
		vo.setMem_id(user_id);
		MemberVO memvo =new MemberVO();
		try {
			memvo = ims.select(vo);
			text_UserInfo.setText(memvo.getMem_intro());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//이미지 설정
		Image img = new Image(memvo.getMem_image());
		temp_img_path = memvo.getMem_image();   //sVO.getSing_image()를 전역으로 쓰기위해
		img_UserImg.setImage(img);
		
		
		//최근댓글테이블
		col_ReviewCont.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMus_re_content()));
		col_ReviewDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getIndate()));
	
		//최근많이 들은 아티스트이름넣기 
		col_MSno.setCellValueFactory(param -> new SimpleStringProperty(""+(no1++)));
		col_MSits.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getName()));

		//최근많이 들은 곡
		col_MMno.setCellValueFactory(param -> new SimpleStringProperty(""+(no2++)));
		col_MMits.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getName()));
		col_MMtitle.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getTitle()));
	
		//최근 감상곡
		col_NMno.setCellValueFactory(param -> new SimpleStringProperty(""+(no3++)));
		col_NMits.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getName()));
		col_NMtitle.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getTitle()));
		col_NMdate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getHisto_indate().substring(0,10)));
		
		
		
		
		// 데이터 삽입
		MusicReviewVO muvo =new MusicReviewVO();
		muvo.setMem_id(user_id);
		MusicHistoryVO muh =new MusicHistoryVO();
		muh.setMem_id(user_id);
		try {
			revList = FXCollections.observableArrayList(imrs.selectReview(muvo));
			singList = FXCollections.observableArrayList(imhs.selectMayIts(muh));
			newList = FXCollections.observableArrayList(imhs.selectMayIndate(muh));
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}	
		
		TreeItem<MusicReviewVO> root = new RecursiveTreeItem<>(revList, RecursiveTreeObject::getChildren);
		tbl_Review.setRoot(root);
		tbl_Review.setShowRoot(false);
		
		TreeItem<MusicHistoryVO> root1 = new RecursiveTreeItem<>(singList, RecursiveTreeObject::getChildren);
		tbl_ManySigner.setRoot(root1);
		tbl_ManySigner.setShowRoot(false);
		tbl_ManyMusic.setRoot(root1);
		tbl_ManyMusic.setShowRoot(false);
		
		TreeItem<MusicHistoryVO> root2 = new RecursiveTreeItem<>(newList, RecursiveTreeObject::getChildren);
		tbl_NewMusic.setRoot(root2);
		tbl_NewMusic.setShowRoot(false);
		
		

	}

	@FXML
	public void btn_profch() throws IOException { // 프로필 수정 클릭시
		Parent root = FXMLLoader.load(getClass().getResource("../profilech/profile.fxml"));
		Scene scene = new Scene(root);
		mypageDialog.setTitle("모여서 각잡고 코딩 - clap");

		mypageDialog.setScene(scene);
		mypageDialog.show();
	}

	@FXML
	public void btn_my() throws IOException { // 내정보 클릭시
		Parent root = FXMLLoader.load(getClass().getResource("pwcheck.fxml"));
		Scene scene = new Scene(root);
		pwok.setTitle("모여서 각잡고 코딩 - clap");

		pwok.setScene(scene);
		pwok.show();

		Button btn = (Button) root.lookup("#btn_Ok");

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				String user_id = LoginSession.session.getMem_id();
				label_Id.setText(user_id); // 현재 로그인한 사용자 아이디 가져오기

				MemberVO vo = new MemberVO();
				vo.setMem_id(user_id);
				MemberVO vo2 = new MemberVO();

				try {
					vo2 = ims.select(vo);
				} catch (RemoteException e) {
					System.out.println("에러입니다");
					e.printStackTrace();
				}

				JFXPasswordField fild = (JFXPasswordField) root.lookup("#fild_ok");

				if (fild.getText().equals(vo2.getMem_pw())) {
					pwok.close();

					Parent root1 = null;
					try {
						root1 = FXMLLoader.load(getClass().getResource("../mypageCh/mypageCh_Info.fxml"));
						contents.getChildren().removeAll();
						contents.getChildren().setAll(root1);
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					Text test_set = (Text) root.lookup("#text");
					test_set.setText("\t   잘못된 비밀번호를 입력하였습니다.");
					fild.clear();
				}
			}
		});// btn_Ok

		Button btn_no = (Button) root.lookup("#btn_Cl");
		btn_no.setOnMouseClicked(e -> {
			pwok.close();
		});

	}
	@FXML
	public void btn_Info() throws IOException {  //열필모양 (사용자 개인소개)수정
		
		
		
		Parent root=null;
		try {
			 root = FXMLLoader.load(getClass().getResource("mypageSub.fxml"));
			InfoContents.getChildren().removeAll();
			InfoContents.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		String user_id = LoginSession.session.getMem_id();
		MemberVO vo = new MemberVO();
		vo.setMem_id(user_id);
		MemberVO memvo =new MemberVO();
		try {
			memvo = ims.select(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		JFXButton btn_SubOk =(JFXButton)root.lookup("#btn_SubOk");
		JFXButton btn_SubCl =(JFXButton)root.lookup("#btn_SubCl");
		JFXTextField textF_Sub=  (JFXTextField)root.lookup("#textF_Sub");
		textF_Sub.setText(memvo.getMem_intro());
		
		
		btn_SubOk.setOnAction(e1->{
			vo.setMem_intro(textF_Sub.getText());
			try {
				 ims.updateInfo(vo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			System.out.println("완료");
			
			try {
				Parent root2 = FXMLLoader.load(getClass().getResource("Mypage.fxml"));
				Head.getChildren().removeAll();
				InfoContents.getChildren().removeAll();
				contents.getChildren().removeAll();
				Head.getChildren().setAll(root2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		btn_SubCl.setOnAction(e1->{try {
			Parent root2 = FXMLLoader.load(getClass().getResource("Mypage.fxml"));
			Head.getChildren().removeAll();
			InfoContents.getChildren().removeAll();
			contents.getChildren().removeAll();
			Head.getChildren().setAll(root2);
		} catch (IOException e) {
			e.printStackTrace();
		}});
	}
	
	
	
	@FXML
	public void btn_like() throws IOException {   //좋아요클릭시
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../profiles/like.fxml"));
			contents.getChildren().removeAll();
			contents.getChildren().setAll(root);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
