package Stream11_12;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class fileStreamDemo {

	public static void main(String[] args) throws IOException {
		// TODO 读入和写出文件
		
		//输入:	read 	InputStream	文件存在且可读
		//输出:	write	OutputStream
		
//		File file = new File("inFile.txt");
//		FileInputStream inStream = new FileInputStream(file);
//		FileInputStream inStream2 = new FileInputStream("inFile.txt");
		
		
		
		//定义输入输出流文件
		File inFile = new File("I:\\Java\\JDBC\\jdbcWorkSpace\\JDBCLearn\\Doc\\inFile.txt");
		File outFile = new File("I:\\Java\\JDBC\\jdbcWorkSpace\\JDBCLearn\\Doc\\outFile.txt");
		FileInputStream inStream = new FileInputStream(inFile);
		FileOutputStream outStream = new FileOutputStream(outFile);
		//真正实现文件复制
		byte[] inOutb = new byte[inStream.available()];
		inStream.read(inOutb);
		outStream.write(inOutb);
		inStream.close();
		outStream.close();

	}

}
