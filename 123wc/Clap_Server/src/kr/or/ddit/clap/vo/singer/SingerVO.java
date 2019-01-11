package kr.or.ddit.clap.vo.singer;

import java.io.Serializable;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SingerVO extends RecursiveTreeObject<SingerVO> implements Serializable{
						//JFXTreeTableView를 사용하기 위해 상속받아야함
	private String sing_no;
	private String sing_name;
	private String sing_nation;
	private String sing_indate;
	private String sing_act_type;
	private String sing_debut_mus;
	private String sing_act_era;
	private String sing_image;
	private String sing_debut_era;
	private String sing_intro;
	private ImageView imgView;
	
	public ImageView getImgView() {
		//try {
		this.imgView = new ImageView();
		//Image img = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\noImg.png");
		Image img = new Image(sing_image);
		imgView.setImage(img);
		imgView.setFitWidth(120);
		imgView.setFitHeight(50);
		System.out.println("imgView생성");
		/*}catch (Exception e) {
			//이미지 불러올 때 예외발생시
			Image img = new Image("file:\\\\Sem-pc\\공유폴더\\Clap\\img\\noImg.png");
			imgView.setImage(img);
			imgView.setFitWidth(120);
			imgView.setFitHeight(50);
			System.out.println("임시 생성 imgView생성");
			
		}*/
		return imgView;
	}

	public void setImgView(ImageView imgView) {
		this.imgView = imgView;
	}

	public SingerVO() {
		super();
	}
	
	public String getSing_no() {
		return sing_no;
	}
	public void setSing_no(String sing_no) {
		this.sing_no = sing_no;
	}
	public String getSing_name() {
		return sing_name;
	}
	public void setSing_name(String sing_name) {
		this.sing_name = sing_name;
	}
	public String getSing_nation() {
		return sing_nation;
	}
	public void setSing_nation(String sing_nation) {
		this.sing_nation = sing_nation;
	}
	public String getSing_indate() {
		return sing_indate;
	}
	public void setSing_indate(String sing_indate) {
		this.sing_indate = sing_indate;
	}
	public String getSing_act_type() {
		return sing_act_type;
	}
	public void setSing_act_type(String sing_act_type) {
		this.sing_act_type = sing_act_type;
	}
	public String getSing_debut_mus() {
		return sing_debut_mus;
	}
	public void setSing_debut_mus(String sing_debut_mus) {
		this.sing_debut_mus = sing_debut_mus;
	}
	public String getSing_act_era() {
		return sing_act_era;
	}
	public void setSing_act_era(String sing_act_era) {
		this.sing_act_era = sing_act_era;
	}
	public String getSing_image() {
		
		return sing_image;
	}
	public void setSing_image(String sing_image) {
		
		this.sing_image = sing_image;
	
	}
	public String getSing_debut_era() {
		return sing_debut_era;
	}
	public void setSing_debut_era(String sing_debut_era) {
		this.sing_debut_era = sing_debut_era;
	}
	public String getSing_intro() {
		return sing_intro;
	}
	public void setSing_intro(String sing_intro) {
		this.sing_intro = sing_intro;
	}

}
