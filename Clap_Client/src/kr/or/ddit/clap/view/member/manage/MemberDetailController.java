package kr.or.ddit.clap.view.member.manage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.view.singer.singer.UpdateSingerController;
import kr.or.ddit.clap.vo.member.MemberVO;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MemberDetailController implements Initializable{
	
	private Registry reg;
	private IMypageService ims;
	
	public static AnchorPane contents;
	public static String memid;
	@FXML AnchorPane main;
	@FXML Label label_MemName1;
	@FXML ImageView imgview_MemImg;
	@FXML Label label_MemName2;
	@FXML Label label_Memid;
	@FXML Label label_MemBir;
	@FXML Label label_MemTel;
	@FXML Label label_MemGrade;
	@FXML Label label_MemEmail;
	@FXML Label label_BlackCnt;
	@FXML Label label_BlackTF;
	@FXML Label txt_intro;
	@FXML Label label_MemGender;
	@FXML Label label_MemAuth;
	@FXML Label label_MeminDate;
	@FXML Label label_DelTF;
	
	
	public void givePane(AnchorPane contents) {
		this.contents = contents;
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(memid);
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ims = (IMypageService) reg.lookup("mypage");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	 MemberVO vo = new MemberVO();
		
		vo.setMem_id(memid);
		 MemberVO mvo = new MemberVO();
		try {
			mvo = ims.select(vo);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		label_MemName1.setText(mvo.getMem_name());
		label_MemName2.setText(mvo.getMem_name());
		label_Memid .setText(memid);
		label_MeminDate.setText(mvo.getMem_indate());
		label_DelTF.setText(mvo.getMem_del_tf());
		
		label_MemBir .setText(mvo.getMem_bir());
		label_MemTel .setText(mvo.getMem_tel());
		label_MemGrade .setText(mvo.getMem_grade());
		label_MemEmail .setText(mvo.getMem_email());
		
		label_BlackCnt  .setText(mvo.getMem_black_cnt());
		label_BlackTF  .setText(mvo.getMem_blacklist_tf());
		txt_intro  .setText(mvo.getMem_intro());
		label_MemGender .setText(mvo.getMem_gender());
		
		label_MemAuth .setText(mvo.getMem_auth());
		
		
		
		
	}
	//이미지 크게 보기
	@FXML public void wideView() {}
	@FXML public void updateMem() {
		try {
			// 바뀔 화면(FXML)을 가져옴
			MemberUpdateController.memid = memid;// 가수번호를 변수로 넘겨줌

			FXMLLoader loader = new FXMLLoader(getClass().getResource("memupdate.fxml"));// initialize실행됨
			Parent UpdateMember = loader.load();
			MemberUpdateController cotroller = loader.getController();
			main.getChildren().removeAll();
			main.getChildren().setAll(UpdateMember);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	

}
