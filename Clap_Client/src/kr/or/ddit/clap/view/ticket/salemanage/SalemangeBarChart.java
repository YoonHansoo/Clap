package kr.or.ddit.clap.view.ticket.salemanage;

import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

public class SalemangeBarChart implements Initializable{
	private AnchorPane contents;
	 private BarChart<String, Number> chart;
	 private CategoryAxis xAxis; // X축
	 private NumberAxis yAxis; // Y축
	 String[] cates = {"속도", "평점", "주행거리", "안정성"};
	    // 시리즈를 담을 자료구조 
	    XYChart.Series<String, Number> series = null;
	    private ObservableList<String> xLabels = FXCollections.observableArrayList();
	    
	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	        System.out.println("FXML LOAD COMPLETE !");
	        xLabels.addAll(Arrays.asList(cates));
	        xAxis.setCategories(xLabels);
	        style2();
	    }
	    
	    public void style1() { // 바 하나짜리 차트
	        // 바 생성
	        series = new XYChart.Series<String,Number>();
	        chart.getData().clear();
	        series.setName("점수");
	        // 데이터 생성
	        for(int i = 0; i < cates.length; i ++) {
	            series.getData().add(new XYChart.Data<String, Number>(xLabels.get(i), random()));
	        }
	        // 차트에 추가 
	        chart.getData().add(series);
	    }
	    
	    public void style2() { // 여러개의 바 [BMW,Audi,Benz,Mini] 생성
	        
	        chart.getData().clear();
	        
	        String[] names = {"BMW","Audi","Benz","Mini"};
	        for(int i = 0; i < names.length; i++) {
	            series = new XYChart.Series<String,Number>(); // 시리즈를 4개 생성 
	            series.setName(names[i]); // 시리즈의 이름 4개 생성 []
	            for(int j = 0; j < cates.length; j ++) { // 시리즈 별 데이터 생성
	                series.getData().add(new XYChart.Data<String, Number>(xLabels.get(j), random()));
	            }
	            // 차트에 추가
	            chart.getData().add(series);
	        }
	        
	    }
	    
	    public int random() {
	        Random rand = new Random();
	        return rand.nextInt(9)+1; // 1 ~ 10
	    }
	}
