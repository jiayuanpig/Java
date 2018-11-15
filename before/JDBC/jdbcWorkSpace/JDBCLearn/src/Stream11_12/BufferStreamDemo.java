package Stream11_12;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferStreamDemo {//缓冲输入输出�?
	
	public static void main(String[] args){
		try {
			File infile = new File("BufferStreamDemo.java");
			File outfile = new File("BufferStreamDemo.txt");
			byte[] data = new byte[1];
			FileInputStream inStream = new FileInputStream(infile);
			FileOutputStream outStream = new FileOutputStream(outfile);
			BufferedInputStream bufferInputStream = new BufferedInputStream(inStream);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outStream);
			//等价�?
//			BufferedInputStream bufferInputStream2 = new BufferedInputStream(new FileInputStream(infile));
			System.out.println("复制文件 " + infile.length() + " 字节�?");
			while(bufferInputStream.read(data) != -1){
				bufferedOutputStream.write(data);
			}
			//将缓冲数据流全部写出
			bufferedOutputStream.flush();
			System.out.println("复制完成�?");
			
//			//显示 BufferStreamDemo.txt 文件的内�?
//			BufferedInputStream bufferInputStream2 = new BufferedInputStream(new FileInputStream(outfile));
//			while(bufferInputStream2.read(data) != -1){
//				String str = new String(data); 
//				System.out.println(str);
//			}
			
			//关闭输入输出�?
			bufferInputStream.close();
//			bufferInputStream2.close();
			bufferedOutputStream.close();
			
			
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("123");
			e.printStackTrace();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		
		
	}

}
