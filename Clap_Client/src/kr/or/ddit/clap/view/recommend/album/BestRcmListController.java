package kr.or.ddit.clap.view.recommend.album;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import kr.or.ddit.clap.service.recommend.IRecommendService;
import kr.or.ddit.clap.vo.recommend.RecommendAlbumVO;

public class BestRcmListController implements Initializable {

	private Registry reg;
	private IRecommendService irs;
	private ObservableList<RecommendAlbumVO> recommendList;
	@FXML
	AnchorPane main;
	@FXML
	AnchorPane contents;
	@FXML
	VBox main_vbox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			irs = (IRecommendService) reg.lookup("recommend");
			recommendList = FXCollections.observableArrayList(irs.selectBestRecommendAlbum());
			System.out.println(recommendList.size());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		// 높이 조절

		int contentsHeight = 800;

		if (recommendList.size() <= 4) {
			contents.setPrefHeight(contentsHeight);
		}

		else if (recommendList.size() > 4) {
			System.out.println("리스트 갯수 증가");
			int temp_h = ((recommendList.size() - 4) / 2 + (recommendList.size() - 4) % 2);

			System.out.println(temp_h);
			contentsHeight = contentsHeight + (500 * temp_h);
			System.out.println("높이" + contentsHeight);
			contents.setPrefHeight(contentsHeight);

			HBox hbox = null;

			for (int i = 0; i < recommendList.size(); i++) {
				int likeCnt = 0;
				int listCnt = 0;

				System.out.println("사이즈:" + recommendList.size());
				// PK
				String RcmAlbNo = recommendList.get(i).getRcm_alb_no();
				try {
					// 좋아요 수 구하는 쿼리
					likeCnt = irs.selectAlbumLikeCnt(RcmAlbNo);
					// 리스트의 개수구하는 쿼리
					listCnt = irs.selectAlbumListCnt(RcmAlbNo);
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				System.out.println("객체생성시작");
				if (i % 2 == 0) {
					// 큰 HBOX 생성
					hbox = new HBox();
					hbox.setPrefWidth(716);
					hbox.setPrefHeight(500);
					main_vbox.setMargin(hbox, new Insets(10, 0, 0, 0));
				}

				VBox vbox = new VBox();
				vbox.setPrefWidth(358);
				vbox.setPrefHeight(400);
				vbox.setStyle("-fx-border-color:#090948;");
				hbox.setMargin(vbox, new Insets(0, 20, 0, 0));

				// 앨범이미지를 표시하는 ImageView
				ImageView iv_Album = new ImageView();
				Image img_Path = new Image(recommendList.get(i).getRcm_alb_image());
				iv_Album.setImage(img_Path);
				iv_Album.setFitWidth(358);
				iv_Album.setFitHeight(250);
				iv_Album.setOpacity(0.6);

				// Title 라벨
				Label title = new Label();
				title.setFont(Font.font("-윤고딕350", 18));
				title.setTextFill(Color.valueOf("#000"));
				title.setPrefWidth(308);
				title.setPrefHeight(40);
				title.setWrapText(true);
				title.setPadding(new Insets(30, 0, 30, 15));
				title.setText(recommendList.get(i).getRcm_alb_name());

				title.setOnMouseClicked(e -> {
					// 화면전환 코드 작성

					System.out.println("더블클릭");
					if (e.getClickCount() > 1) {
						Label obj_label = (Label) e.getSource();

						for (int j = 0; j < recommendList.size(); j++) {

							// 앨범 이름이 같을 경우 잘못된 값이 들어갈 가능성이 있음 -> 앨범이름도 유효성을 걸어줘?
							if (obj_label.getText().equals(recommendList.get(j).getRcm_alb_name())) {
								String rcmAlbNo = recommendList.get(j).getRcm_alb_no();
								System.out.println("곡번호 :" + rcmAlbNo);
								System.out.println("이동");

								// 화면전환
								RecommendAlbumDetailController.rcmAlbNo = rcmAlbNo;// 곡 번호를 변수로 넘겨줌

								FXMLLoader loader = new FXMLLoader(getClass().getResource("RecommendAlbumDetail.fxml"));// init실행됨
								Parent recommendAlbumDetail;

								try {
									recommendAlbumDetail = loader.load();

									RecommendAlbumDetailController cotroller = loader.getController();
									cotroller.givePane(contents);

									main.getChildren().removeAll();
									main.getChildren().setAll(recommendAlbumDetail);
								} catch (IOException e1) {
									e1.printStackTrace();
								}

							}
						}

					}
				});
				//인기순위를 나타낼 Label
				Label rank = new Label();
				title.setFont(Font.font("-윤고딕350", 16));
				title.setTextFill(Color.valueOf("#9c0000"));
				title.setPrefWidth(308);
				title.setPrefHeight(20);
				title.setWrapText(true);
				title.setPadding(new Insets(30, 0, 30, 15));
				title.setText("인기 Top"+(i+1));
				

				// title과 like를 담을 hbox
				HBox temp_hbox = new HBox();
				temp_hbox.setPrefWidth(308);
				temp_hbox.setPrefHeight(20);
				temp_hbox.setPadding(new Insets(5, 5, 5, 15));
				temp_hbox.setStyle("-fx-bordertop-color:#090948;");
				temp_hbox.setStyle("-fx-border-style : solid hidden hidden hidden;");
				vbox.setMargin(temp_hbox, new Insets(50, 0, 0, 0));

				// 좋아요 라벨
				Label like = new Label();
				like.setFont(Font.font("-윤고딕350", 14));
				like.setTextFill(Color.valueOf("#000"));
				like.setPrefWidth(308);
				like.setPrefHeight(40);
				// like.setPadding(new Insets(20,0,0,30));
				String strLikeCnt = likeCnt + "";
				like.setText(strLikeCnt);

				// 좋아요 아이콘
				FontAwesomeIcon icon_Like = new FontAwesomeIcon();
				icon_Like.setIconName("HEART");
				icon_Like.setFill(Color.valueOf("#9c0000"));
				icon_Like.setSize("20");
				like.setGraphic(icon_Like);

				// Title 곡 라벨 갯수
				Label cntMusic = new Label();
				cntMusic.setFont(Font.font("-윤고딕350", 14));
				cntMusic.setTextFill(Color.valueOf("#000"));
				cntMusic.setPrefWidth(308);
				cntMusic.setPrefHeight(40);
				// cntMusic.setPadding(new Insets(20,0,0,30));
				cntMusic.setText(listCnt + "곡");

				// Title 좋아요 아이콘
				FontAwesomeIcon icon_cntMusic = new FontAwesomeIcon();
				icon_cntMusic.setIconName("PLAY_CIRCLE");
				icon_cntMusic.setFill(Color.valueOf("#9c0000"));
				icon_cntMusic.setSize("20");
				cntMusic.setGraphic(icon_cntMusic);

				temp_hbox.getChildren().addAll(like, cntMusic);
				vbox.getChildren().addAll(rank, iv_Album, title, temp_hbox);
				hbox.getChildren().add(vbox);

				if (i % 2 == 0) {
					main_vbox.getChildren().add(hbox);

				} else if (i == recommendList.size()) { // -------------
					System.out.println("마지막");
					main_vbox.getChildren().add(hbox);
				}

			}

		}
	}

	@FXML
	public void InsertRecommendAlbum() {
		System.out.println("버튼클릭");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("InsertRecommendAlbum.fxml"));// init실행됨
		Parent insertRecommend;
		try {
			insertRecommend = loader.load();

			InsertRecommendAlbumController cotroller = loader.getController();
			cotroller.givePane(contents);

			main.getChildren().removeAll();
			main.getChildren().setAll(insertRecommend);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
