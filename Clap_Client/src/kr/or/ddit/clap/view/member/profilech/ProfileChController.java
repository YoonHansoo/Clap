package kr.or.ddit.clap.view.member.profilech;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.like.ILikeService;
import kr.or.ddit.clap.service.mypage.IMypageService;
import kr.or.ddit.clap.vo.member.MemberVO;
import kr.or.ddit.clap.vo.singer.SingerVO;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import com.jfoenix.controls.JFXCheckBox;

public class ProfileChController implements Initializable {
	private Registry reg;
	private IMypageService ims;

	public Stage primaryStage;
	@FXML
	Button btn_sh;
	@FXML
	ImageView img;
	@FXML
	TextField textF_Info;
	@FXML
	ImageView imgview_UserImg;
	@FXML
	Button btn_Image;

	private FileChooser fileChooser;
	private String img_path;
	private File filePath;
	@FXML
	JFXCheckBox chBox_del;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			ims = (IMypageService) reg.lookup("mypage");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		String user_id = LoginSession.session.getMem_id();
		MemberVO vo = new MemberVO();
		vo.setMem_id(user_id);
		MemberVO memvo = new MemberVO();
		try {
			memvo = ims.select(vo);
			textF_Info.setText(memvo.getMem_intro());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Image img = new Image(memvo.getMem_image());
		img_path = memvo.getMem_image(); // sVO.getSing_image()를 전역으로 쓰기위해
		imgview_UserImg.setImage(img);

	}

	@FXML
	public void btn_Img(ActionEvent event) { // 찾아보기 클릭시

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		fileChooser = new FileChooser();
		fileChooser.setTitle("Open image");

		// 사용자의 디렉토리 보여줌
		// String userDirectoryString = System.getProperty("user.home") + "\\Pictures";
		// 기본위치
		String userDirectoryString = "\\\\Sem-pc\\공유폴더\\Clap\\img\\singer";

		System.out.println("userDirectoryString:" + userDirectoryString);
		File userDirectory = new File(userDirectoryString);

		if (!userDirectory.canRead()) {
			userDirectory = new File("c:/");
		}

		fileChooser.setInitialDirectory(userDirectory);
		;

		this.filePath = fileChooser.showOpenDialog(stage);

		// 이미지를 새로운 이미지로 바꿈
		try {
			BufferedImage bufferedImage = ImageIO.read(filePath);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			imgview_UserImg.setImage(image);
			System.out.println("파일경로:" + filePath);
			String str_filePath = "file:" + filePath;
			// userDirectoryString = "file:\\\\Sem-pc\\공유폴더\\Clap\\img\\singer"; //화면 출력 시
			// 절대경로로 이미지를 읽기위해서
			img_path = str_filePath;

		} catch (Exception e) {
			// System.out.println(e.getMessage());
			// e.printStackTrace();
			System.out.println("이미지를 선택하지 않았습니다.");
		}

	}

	@FXML
	public void btn_Del() { // x표시 클릭시
		textF_Info.setText("");
	}

	@FXML
	public void updateUser() { //설정 클릭시

		if (chBox_del.isSelected()) {
			String user_id = LoginSession.session.getMem_id();
			MemberVO vo = new MemberVO();
			vo.setMem_intro(textF_Info.getText());
			vo.setMem_image("http://image.genie.co.kr/imageg/web/common/blank_man.png");
			vo.setMem_id(user_id);
			try {
				ims.updateImage(vo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			String user_id = LoginSession.session.getMem_id();
			MemberVO vo = new MemberVO();
			vo.setMem_intro(textF_Info.getText());
			vo.setMem_image(img_path);
			vo.setMem_id(user_id);
			try {
				ims.updateImage(vo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML public void btn_cl(ActionEvent event) {
		//화면 종료
	
	}
}
