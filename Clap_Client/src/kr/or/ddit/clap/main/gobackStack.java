package kr.or.ddit.clap.main;

import java.util.Stack;

public class gobackStack {
	public static Stack back  = new Stack();
	
	public static void goURL(String url) {
		back.push(url);
	}
	
	public static String goBack() {
		if(!back.empty()) {
		return (String) back.pop();
		}
		return null;
	}
}
