package File11_04;

import java.io.*;
import java.net.*;

public class RetrivePage {//ץȡ��ҳ
	public static String downloadPage(String path) throws IOException {
		URL pageURL =  new URL(path);
		InputStreamReader input = new InputStreamReader(pageURL.openStream());
		BufferedReader reader = new BufferedReader(input);
		String line;
		StringBuilder pageBuffer = new StringBuilder();
		while((line = reader.readLine())!= null) {
			pageBuffer.append(line);
		}
		return pageBuffer.toString();
	}
	public static void main(String[] args) throws IOException {
		// TODO ץȡ��ҳ
		System.out.println(RetrivePage.downloadPage("http://www.baidu.com"));
	}

}
