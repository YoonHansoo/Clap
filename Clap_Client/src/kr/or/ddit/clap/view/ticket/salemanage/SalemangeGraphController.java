package kr.or.ddit.clap.view.ticket.salemanage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import kr.or.ddit.clap.view.album.album.InsertAlbumController;

public class SalemangeGraphController implements Initializable{
	


	public static String singNo;
	@FXML private LineChart<String, Integer> lineChart;
	    XYChart.Series<String, Integer>  series = null;
	
	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	    	
	    	System.out.println(singNo);
	    	
	        //drawChart();
	    }
	    
	    public void drawChart() {
	        series = new XYChart.Series<String, Integer>();
	        series.getData().add(new XYChart.Data<String, Integer>("1차", 30));
	        series.getData().add(new XYChart.Data<String, Integer>("2차", 40));
	        series.getData().add(new XYChart.Data<String, Integer>("3차", 50));
	        series.getData().add(new XYChart.Data<String, Integer>("4차", 30));
	        
	        series.setName("Month Pay");
	        // 라인차트에 시리즈 추가
	        lineChart.getData().add(series);
	    }
	    
	    // 여러 개 라인 그리기 (1개 라인 = 1개 시리즈)
	    public void multichart() {//버튼 클릭시 
	        lineChart.getData().clear(); // 기존 라인 제거
	        series = null;
	        for(int i = 0; i <4; i++) {  // 만들고 싶은 만큼 반복
	            // 새로운 시리즈 만들기 (4개)
	            series = new XYChart.Series<String, Integer>();
	            // 새로운 데이터 추가 (4개)
	            for( int j = 1; j < 5; j ++) {
	                series.getData().add(new XYChart.Data<String, Integer>(j + "차", random()));
	            }
	            series.setName("Pay" + i);
	            // 라인차트에 추가 
	            lineChart.getData().add(series);
	    
	        }
	        
	    }
	    
	    // 임의의 값 추출
	    public int random() {
	        Random rand = new Random();
	        return rand.nextInt(80); // 0 ~ 80 사이의 랜덤수
	    }
	    

	 
}
