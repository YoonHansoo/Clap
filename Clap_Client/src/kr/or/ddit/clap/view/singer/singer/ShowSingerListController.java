package kr.or.ddit.clap.view.singer.singer;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import kr.or.ddit.clap.service.singer.ISingerService;
import kr.or.ddit.clap.vo.singer.SingerVO;

public class ShowSingerListController implements Initializable {

	@FXML
	Pagination p_paging;
	@FXML
	JFXTreeTableView<SingerVO> tbl_singer;
	@FXML
	TreeTableColumn<SingerVO, ImageView> col_singerImg;
	@FXML
	TreeTableColumn<SingerVO, String> col_singerName;
	@FXML
	TreeTableColumn<SingerVO, String> col_singerActType;
	@FXML
	TreeTableColumn<SingerVO, String> col_singerEra;
	@FXML
	TreeTableColumn<SingerVO, String> col_singerDebutEra;
	@FXML
	TreeTableColumn<SingerVO, String> col_singerDebutMus;
	@FXML TreeTableColumn<SingerVO, String> col_singerNo;
	private Registry reg;
	private ISingerService iss;
	private ObservableList<SingerVO> singerList;
	@FXML JFXComboBox combo_search;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry("localhost", 8888);
			iss = (ISingerService) reg.lookup("singer");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		


		col_singerImg
				.setCellValueFactory(param -> new SimpleObjectProperty<ImageView>(param.getValue().getValue().getImgView()));

		col_singerName
				.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getSing_name()));

		col_singerActType
				.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getSing_act_type()));

		col_singerEra
				.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getSing_act_era()));

		col_singerDebutEra.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getValue().getSing_debut_era()));

		col_singerDebutMus.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getValue().getSing_debut_mus()));

		col_singerNo.setCellValueFactory(
				param -> new SimpleStringProperty(param.getValue().getValue().getSing_no()));

		
		
		try {
			singerList = FXCollections.observableArrayList(iss.selectListAll());
		} catch (RemoteException e) {
			System.out.println("에러");
			e.printStackTrace();
		}

		TreeItem<SingerVO> root = new RecursiveTreeItem<>(singerList, RecursiveTreeObject::getChildren);
		tbl_singer.setRoot(root);
		tbl_singer.setShowRoot(false);
		System.out.println("ok...");
	}

}
