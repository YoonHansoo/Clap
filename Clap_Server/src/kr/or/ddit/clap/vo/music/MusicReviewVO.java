package kr.or.ddit.clap.vo.music;

import java.io.Serializable;
import java.rmi.registry.Registry;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.ddit.clap.service.musicreview.IMusicReviewService;

public class MusicReviewVO  extends RecursiveTreeObject<MusicReviewVO>  implements Serializable{
	
	private Registry reg;
	private IMusicReviewService imrs;
	
	
	private String mus_re_no;
	private String mus_re_content;
	private String mus_re_indate;
	private String mem_id;
	private String mus_no;
	
	/**
	 * 현지 리뷰테이블을 위해 추가
	 */
	private String mus_title;
	private String indate;
	private String alb_image;
	private String sing_name;
	private ImageView imgView;
	private JFXButton btnDel;
	
	
	
	public String getMus_title() {
		return mus_title;
	}
	public void setMus_title(String mus_title) {
		this.mus_title = mus_title;
	}
	public String getSing_name() {
		return sing_name;
	}
	public void setSing_name(String sing_name) {
		this.sing_name = sing_name;
	}
	public String getAlb_image() {
		return alb_image;
	}
	public void setAlb_image(String alb_image) {
		this.alb_image = alb_image;
	}
	public ImageView getImgView() {
		this.imgView = new ImageView();
		//Image img = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\noImg.png");
		Image img = new Image(alb_image);
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
		this.btnDel= new JFXButton();/*
		btnDel.setId(mus_re_no);
		btnDel.setText("x");
		btnDel.setPrefSize(30, 50);
		btnDel.setOnAction(ee->{ 
			
			try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			imrs = (IMusicReviewService) reg.lookup("musicreview");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		String user_id = LoginSession.session.getMem_id();
		MusicReviewVO vo1 = new MusicReviewVO();
		vo1.setMem_id(user_id);
		vo1.setMus_re_no(btnDel.getId());
		try {
		imrs.deleteMusReview(vo1);
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}
			
		});*/
		return this.btnDel;
	}
	public void setBtnDel(JFXButton btnDel) {
		this.btnDel = btnDel;
	}
	public String getMus_re_no() {
		return mus_re_no;
	}
	public void setMus_re_no(String mus_re_no) {
		this.mus_re_no = mus_re_no;
	}
	public String getMus_re_content() {
		return mus_re_content;
	}
	public void setMus_re_content(String mus_re_content) {
		this.mus_re_content = mus_re_content;
	}
	public String getMus_re_indate() {
		return mus_re_indate;
	}
	public void setMus_re_indate(String mus_re_indate) {
		this.mus_re_indate = mus_re_indate;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMus_no() {
		return mus_no;
	}
	public void setMus_no(String mus_no) {
		this.mus_no = mus_no;
	}
	public String getIndate() {
		return indate;
	}
	public void setIndate(String indate) {
		this.indate = indate;
	}
	
	
}
