package kr.or.ddit.clap.vo.singer;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.musicreview.IMusicReviewService;
import kr.or.ddit.clap.service.singer.ISingerReviewService;
import kr.or.ddit.clap.vo.album.AlbumReviewVO;
import kr.or.ddit.clap.vo.music.MusicReviewVO;

public class SingerReviewVO extends RecursiveTreeObject<SingerReviewVO>  implements Serializable{
	private Registry reg;
	private ISingerReviewService isrs;
	
	private String sing_re_no;
	private String sing_re_content;
	private String sing_re_indate;
	private String mem_id;
	private String sing_no;
	
	/**
	 * 현지 마이페이지 리뷰보기 위해 추가
	 * @return
	 */
	private String sing_debut_era;
	private String sing_image;
	private String sing_name;
	private ImageView imgView;
	private JFXButton btnDel;

	
	
	public String getSing_debut_era() {
		return sing_debut_era;
	}
	public void setSing_debut_era(String sing_debut_era) {
		this.sing_debut_era = sing_debut_era;
	}
	public String getSing_image() {
		return sing_image;
	}
	public void setSing_image(String sing_image) {
		this.sing_image = sing_image;
	}
	public String getSing_name() {
		return sing_name;
	}
	public void setSing_name(String sing_name) {
		this.sing_name = sing_name;
	}
	public ImageView getImgView() {
		this.imgView = new ImageView();
		//Image img = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\noImg.png");
		Image img = new Image(sing_image);
		imgView.setImage(img);
		imgView.setFitWidth(100);
		imgView.setFitHeight(70);
		System.out.println("imgView생성");
	
		return imgView;
	}
	public void setImgView(ImageView imgView) {
		this.imgView = imgView;
	}
	public JFXButton getBtnDel() {
		this.btnDel= new JFXButton();
		btnDel.setId(sing_re_no);
		btnDel.setText("x");
		btnDel.setPrefSize(30, 50);
		btnDel.setOnAction(ee->{ 
			
			try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			isrs = (ISingerReviewService) reg.lookup("singreview");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		String user_id = LoginSession.session.getMem_id();
		SingerReviewVO vo1 = new SingerReviewVO();
		vo1.setMem_id(user_id);
		vo1.setSing_re_no(btnDel.getId());
		try {
			isrs.deleteItsReview(vo1);
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}
			
		});
		return this.btnDel;
	}
	public void setBtnDel(JFXButton btnDel) {
		this.btnDel = btnDel;
	}
	public String getSing_re_no() {
		return sing_re_no;
	}
	public void setSing_re_no(String sing_re_no) {
		this.sing_re_no = sing_re_no;
	}
	public String getsing_re_content() {
		return sing_re_content;
	}
	public void setsing_re_content(String sing_re_content) {
		this.sing_re_content = sing_re_content;
	}
	public String getSing_re_indate() {
		return sing_re_indate;
	}
	public void setSing_re_indate(String sing_re_indate) {
		this.sing_re_indate = sing_re_indate;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getSing_no() {
		return sing_no;
	}
	public void setSing_no(String sing_no) {
		this.sing_no = sing_no;
	}

}
