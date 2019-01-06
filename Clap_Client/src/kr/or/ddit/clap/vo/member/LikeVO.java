package kr.or.ddit.clap.vo.member;

import java.io.Serializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LikeVO  extends RecursiveTreeObject<LikeVO> implements Serializable{
	private String mus_no;
	private String mus_like_date;
	private String mus_title;
	private String sing_name;
	private String alb_name;
	private String sing_image;
	private String mem_id;
	private ImageView imgView; 
	private JFXCheckBox chBox; 
	private JFXButton btnLike; 
	
	
	
	public String getMus_no() {
		return mus_no;
	}
	public void setMus_no(String mus_no) {
		this.mus_no = mus_no;
	}
	public String getMus_like_date() {
		return mus_like_date;
	}
	public void setMus_like_date(String mus_like_date) {
		this.mus_like_date = mus_like_date;
	}
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
	public String getAlb_name() {
		return alb_name;
	}
	public void setAlb_name(String alb_name) {
		this.alb_name = alb_name;
	}
	public String getSing_image() {
		return sing_image;
	}
	public void setSing_image(String sing_image) {
		this.sing_image = sing_image;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public ImageView getImgView() {
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
	public JFXCheckBox getChBox() {
		this.chBox = new JFXCheckBox();
		chBox.setGraphic(new JFXCheckBox());
		return chBox;
		
	}
	public void setChBox(JFXCheckBox chBox) {
		this.chBox = chBox;
	}
	public JFXButton getBtnLike() {
		this.btnLike = new JFXButton();
		btnLike.setGraphic(new Button());
		return btnLike;
	}
	public void setBtnLike(JFXButton btnLike) {
		this.btnLike = btnLike;
	}
	
	
}