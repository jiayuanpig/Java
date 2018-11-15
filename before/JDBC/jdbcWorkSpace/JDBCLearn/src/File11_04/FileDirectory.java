package File11_04;

import java.io.File;

public class FileDirectory {

	public static void main(String[] args) {
		// TODO 浏览当前文件夹,获取里面的文件夹名和文件名
		try {
			File f = new File("");//默认当前文件夹
			String str = f.getAbsolutePath();
			System.out.println("str:" + str);//输出绝对路径
			File file = new File(str);//利用绝对路径寻找文件
			if(file.isDirectory()) {//是否为目录
				//获取目录下所有文件列表
				File[] files = file.listFiles();
				for(int i = 0;i<files.length;i++) {
					if(files[i].isDirectory()) {
						System.out.println("<dir>"+files[i].getName());
					}else {
						System.out.println(files[i].getName());
					}
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
