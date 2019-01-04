/**
 * 
 * @author Hansoo
 * 
 */
package kr.or.ddit.clap.view.singer.singer;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class UpdateSingerController implements Initializable {

	@FXML
	ImageView imgview_singImg;
	@FXML
	JFXTextField txt_name;
	@FXML
	JFXComboBox<String> combo_actType;
	@FXML
	JFXComboBox<String> combo_actEra;
	@FXML
	JFXComboBox<String> combo_DebutEra;
	@FXML
	JFXTextField txt_debutMus;
	@FXML
	JFXTextField txt_nation;
	@FXML
	Label label_LikeCnt;
	@FXML
	JFXButton btn_chageImg;
	@FXML
	JFXTextArea txt_intro;
	

	public static String singerNo; // PK값 받기

	// 전 화면에 있는 데이터를 그대로 가져와  세팅해주는 메서드
	public void initData(SingerVO sVO, String str_like_cnt) {
		System.out.println("initData");
		
		Image img = new Image(sVO.getSing_image()); //이미지 객체등록
		imgview_singImg.setImage(img);
		txt_name.setText(sVO.getSing_name());
		combo_actType.setPromptText(sVO.getSing_act_type());
		combo_actEra.setPromptText(sVO.getSing_act_era());
		
		combo_DebutEra.setPromptText(sVO.getSing_debut_era());
		txt_debutMus.setText(sVO.getSing_debut_mus());
		txt_nation.setText(sVO.getSing_nation());
		label_LikeCnt.setText(str_like_cnt);
		txt_intro.setText(sVO.getSing_intro());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("가수번호:" + singerNo);
		combo_actType.getItems().add("여성/솔로");
		combo_actType.getItems().add("남성/솔로");
		combo_actType.getItems().add("남성/솔로");
		combo_actType.getItems().add("남성/그룹");
		combo_actType.getItems().add("여성/그룹");
		combo_actType.getItems().add("혼성/그룹");
		combo_actEra.getItems().add("1960년대");
		combo_actEra.getItems().add("1970년대");
		combo_actEra.getItems().add("1980년대");
		combo_actEra.getItems().add("1990년대");
		combo_actEra.getItems().add("2000년대");
		combo_actEra.getItems().add("2010년대");

		for (int i = 1960; i < 2020; i++) {
			String year = i + "년";
			combo_DebutEra.getItems().add(year);
		}
	}

	@FXML
	public void btn_chageImg() {

	}

	@FXML
	public void updateSinger() {

	}

	@FXML
	public void deleteSinger() {

	}

}