/**
 *  문의사항 페이지를 출력하는 화면 controller
 *  
 *  @author hanhwa
 */
package kr.or.ddit.clap.view.support.qna;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kr.or.ddit.clap.vo.support.QnaVO;

public class QnaMenuController implements Initializable {
	
	@FXML
	Pagination p_paging;
	@FXML
	TableView<QnaVO> tbl_qna;
	@FXML
	TableColumn<String , QnaVO> col_qnaNumber;
	@FXML
	TableColumn<String , QnaVO> col_qnaTitle;
	@FXML
	TableColumn<String , QnaVO> col_qnaDate;
	@FXML
	TableColumn<String , QnaVO> col_qnaViewCnt;

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
	}

}
