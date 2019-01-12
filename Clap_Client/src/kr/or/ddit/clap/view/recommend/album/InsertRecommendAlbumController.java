package kr.or.ddit.clap.view.recommend.album;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class InsertRecommendAlbumController implements Initializable {

	@FXML ImageView imgview_img;
	@FXML AnchorPane main;
	@FXML JFXTreeTableView tbl_music;
	@FXML TextArea txt_rcmContents;
	@FXML JFXTextField txt_rcmName;

	private FileChooser fileChooser;
	private File filePath;
	private String img_path;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("인서트");
	}

	//인서트
	public void insertRcmAlbum() {
		
	}
	
	public void cancel() {
		
	}
	
	//추천앨범 목록에 곡을 추가하는 모달창 띄우는 메서드
	public void btn_insertMusic() {
		
	}
	
	//앨범 이미지 선택
	public void btn_insertImg(ActionEvent event) {
		 Stage stage =  (Stage) ((Node)event.getSource()).getScene().getWindow();
		 fileChooser = new FileChooser();
		 fileChooser.setTitle("Open AlbumImage");
		 
		 String userDirectoryString = "\\\\Sem-pc\\공유폴더\\Clap\\img\\album";
		 
		 System.out.println("userDirectoryString:" + userDirectoryString);
		 File userDirectory = new File(userDirectoryString); 
		 
		 if(!userDirectory.canRead()) { //예외시?
			 userDirectory = new File("c:/");
		 }
		 
		 fileChooser.setInitialDirectory(userDirectory); 
		 
		 this.filePath = fileChooser.showOpenDialog(stage);
		 
		
		 //이미지를 새로운 이미지로 바꿈
		 try {
			 BufferedImage bufferedImage = ImageIO.read(filePath);
			 Image image =  SwingFXUtils.toFXImage(bufferedImage, null);
			 imgview_img.setImage(image);
			 System.out.println("파일경로:" + filePath);
			 String str_filePath = "file:"+filePath;
			 img_path = str_filePath;
			 System.out.println(img_path);
			 
		 }catch (Exception e) {
			 System.out.println("이미지를 선택하지 않았습니다.");
		 }
		
		
		
		
	}
}
