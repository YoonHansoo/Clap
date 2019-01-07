package kr.or.ddit.clap.vo.member;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.ddit.clap.main.LoginSession;
import kr.or.ddit.clap.service.like.ILikeService;

public class LikeVO  extends RecursiveTreeObject<LikeVO> implements Serializable{
	private Registry reg;
	private ILikeService ilks;
	
	private String mus_no;
	private String alb_no;
	private String sing_no;
	private String rcm_alb_no;
	
	private String like_date;
	private String mus_title;
	private String sing_name;
	private String alb_name;
	private String sing_image;
	private String mem_id;
	private ImageView imgView; 
	private JFXCheckBox chBox;
	private JFXButton mubtnLike;
	private JFXButton albbtnLike;
	private JFXButton itsbtnLike;
	private JFXButton rcmbtnLike;
	
	
	
	public String getSing_no() {
		return sing_no;
	}
	public void setSing_no(String sing_no) {
		this.sing_no = sing_no;
	}
	public JFXButton getRcmbtnLike() {
		this.rcmbtnLike= new JFXButton();
		rcmbtnLike.setId(rcm_alb_no);
		rcmbtnLike.setText("추천♥");
		rcmbtnLike.setPrefSize(90, 70);
		rcmbtnLike.setOnAction(e2->{ 
			
			try {
				reg = LocateRegistry.getRegistry("localhost", 8888);
				ilks = (ILikeService) reg.lookup("like");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			String user_id1 = LoginSession.session.getMem_id();
			LikeVO vo1 = new LikeVO();
			vo1.setMem_id(user_id1);
			vo1.setRcm_alb_no(rcmbtnLike.getId());
			try {
			List<Integer> liset = FXCollections.observableArrayList(ilks.deleteRcmLike(vo1));
			} catch (RemoteException e) {
				System.out.println("에러");
				e.printStackTrace();
			}
		
		});
		return this.rcmbtnLike;
		}
	public void setRcmbtnLike(JFXButton rcmbtnLike) {
		this.rcmbtnLike = rcmbtnLike;
	}
	public String getRcm_alb_no() {
		return rcm_alb_no;
	}
	public void setRcm_alb_no(String rcm_alb_no) {
		this.rcm_alb_no = rcm_alb_no;
	}
	public String getAlb_no() {
		return alb_no;
	}
	public void setAlb_no(String alb_no) {
		this.alb_no = alb_no;
	}

	public JFXButton getAlbbtnLike() {
		this.albbtnLike= new JFXButton();
		albbtnLike.setId(alb_no);
		albbtnLike.setText("앨범♥");
		albbtnLike.setPrefSize(90, 70);
		albbtnLike.setOnAction(e2->{ 
			
			try {
				reg = LocateRegistry.getRegistry("localhost", 8888);
				ilks = (ILikeService) reg.lookup("like");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			String user_id1 = LoginSession.session.getMem_id();
			LikeVO vo1 = new LikeVO();
			vo1.setMem_id(user_id1);
			vo1.setAlb_no(albbtnLike.getId());
			try {
			List<Integer> liset = FXCollections.observableArrayList(ilks.deleteAlbLike(vo1));
			} catch (RemoteException e) {
				System.out.println("에러");
				e.printStackTrace();
			}
			
		});
		
		return this.albbtnLike;
	}
	public void setAlbbtnLike(JFXButton albbtnLike) {
		this.albbtnLike = albbtnLike;
	}
	public JFXButton getItsbtnLike() {
		this.itsbtnLike= new JFXButton();
		itsbtnLike.setId(sing_no);
		itsbtnLike.setText("아티스트♥");
		itsbtnLike.setPrefSize(170, 70);
		itsbtnLike.setOnAction(e2->{ 
			
			try {
				reg = LocateRegistry.getRegistry("localhost", 8888);
				ilks = (ILikeService) reg.lookup("like");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			String user_id1 = LoginSession.session.getMem_id();
			LikeVO vo1 = new LikeVO();
			vo1.setMem_id(user_id1);
			vo1.setSing_no(itsbtnLike.getId());
			try {
			List<Integer> liset = FXCollections.observableArrayList(ilks.deleteSingLike(vo1));
			} catch (RemoteException e) {
				System.out.println("에러");
				e.printStackTrace();
			}
		
		});
		return this.itsbtnLike;
	}
	public void setItsbtnLike(JFXButton itsbtnLike) {
		this.itsbtnLike = itsbtnLike;
	}
	public String getMus_no() {
		return mus_no;
	}
	public void setMus_no(String mus_no) {
		this.mus_no = mus_no;
	}
	public String getlike_date() {
		return like_date;
	}
	public void setlike_date(String like_date) {
		this.like_date = like_date;
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
		imgView.setFitWidth(100);
		imgView.setFitHeight(70);
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
		
		return this.chBox;
		
	}
	public JFXCheckBox getchBox1() {
		return this.chBox;
	}
	public void setChBox(JFXCheckBox chBox) {
		this.chBox = chBox;
	}
	public JFXButton getBtnLike() {
		this.mubtnLike = new JFXButton();
		mubtnLike.setId(mus_no);
		
		//FontAwesomeIcon iconName="PENCIL" />
		mubtnLike.setText("곡♥");
		mubtnLike.setPrefSize(90, 70);
		mubtnLike.setOnAction(e2->{ 
			
			try {
				reg = LocateRegistry.getRegistry("localhost", 8888);
				ilks = (ILikeService) reg.lookup("like");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}

			
			String user_id = LoginSession.session.getMem_id();
			LikeVO vo = new LikeVO();
			vo.setMem_id(user_id);
			vo.setMus_no(mubtnLike.getId());
			try {
				ObservableList<Integer> cnt = FXCollections.observableArrayList(ilks.deleteMusLike(vo));
			} catch (RemoteException e) {
				System.out.println("에러");
				e.printStackTrace();
			}
		
		});
		
		return this.mubtnLike;
	}
	public void setBtnLike(JFXButton btnLike) {
		this.mubtnLike = btnLike;
	}
	
	
	
}