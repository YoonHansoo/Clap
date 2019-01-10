package kr.or.ddit.clap.vo.myalbum;

import java.io.Serializable;

import com.jfoenix.controls.JFXCheckBox;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyAlbumListVO implements Serializable{
	
	private String myalb_list_no;
	private String myalb_no;
	private String mus_no;
	private String myalb_list_indate;
	private String mus_title;
	private String alb_image;
	private String sing_name;
	private String myalb_name;
	private JFXCheckBox chBox;
	private ImageView imgView;


	
	public JFXCheckBox getChBox() {
		this.chBox = new JFXCheckBox();
		return chBox;
	}
	public JFXCheckBox getChBox1() {
		return chBox;
	}
	public void setChBox(JFXCheckBox chBox) {
		this.chBox = chBox;
	}
	public String getMyalb_name() {
		return myalb_name;
	}
	public void setMyalb_name(String myalb_name) {
		this.myalb_name = myalb_name;
	}
	public String getMus_title() {
		return mus_title;
	}
	public void setMus_title(String mus_title) {
		this.mus_title = mus_title;
	}
	public String getAlb_image() {
		return alb_image;
	}
	public void setAlb_image(String alb_image) {
		this.alb_image = alb_image;
	}
	public String getSing_name() {
		return sing_name;
	}
	public void setSing_name(String sing_name) {
		this.sing_name = sing_name;
	}
	public ImageView getImgView() {
		this.imgView = new ImageView();
		Image img = new Image(alb_image);
		imgView.setImage(img);
		imgView.setFitWidth(120);
		imgView.setFitHeight(50);
		return imgView;
	}
	public void setImgView(ImageView imgView) {
		this.imgView = imgView;
	}
	public String getMyalb_list_no() {
		return myalb_list_no;
	}
	public void setMyalb_list_no(String myalb_list_no) {
		this.myalb_list_no = myalb_list_no;
	}
	public String getMyalb_no() {
		return myalb_no;
	}
	public void setMyalb_no(String myalb_no) {
		this.myalb_no = myalb_no;
	}
	public String getMus_no() {
		return mus_no;
	}
	public void setMus_no(String mus_no) {
		this.mus_no = mus_no;
	}
	public String getMyalb_list_indate() {
		return myalb_list_indate;
	}
	public void setMyalb_list_indate(String myalb_list_indate) {
		this.myalb_list_indate = myalb_list_indate;
	}

}
