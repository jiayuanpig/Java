package Stream11_12;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class fileStreamDemo {

	public static void main(String[] args) throws IOException {
		// TODO �����д���ļ�
		
		//����:	read 	InputStream	�ļ������ҿɶ�
		//���:	write	OutputStream
		
//		File file = new File("inFile.txt");
//		FileInputStream inStream = new FileInputStream(file);
//		FileInputStream inStream2 = new FileInputStream("inFile.txt");
		
		
		
		//��������������ļ�
		File inFile = new File("I:\\Java\\JDBC\\jdbcWorkSpace\\JDBCLearn\\Doc\\inFile.txt");
		File outFile = new File("I:\\Java\\JDBC\\jdbcWorkSpace\\JDBCLearn\\Doc\\outFile.txt");
		FileInputStream inStream = new FileInputStream(inFile);
		FileOutputStream outStream = new FileOutputStream(outFile);
		//����ʵ���ļ�����
		byte[] inOutb = new byte[inStream.available()];
		inStream.read(inOutb);
		outStream.write(inOutb);
		inStream.close();
		outStream.close();

	}

}
