package File11_04;

import java.io.File;

public class FileDirectory2 {

	
	static int num = 0;//记录文件层数
	static int num_file = -1;//记录是哪一层文件
	
	public static void main(String[] args) {
		// TODO 浏览文件夹,获取里面的文件夹名和文件名（包括子文件夹及其文件）
		try {
			String basePath  = "C:\\Users\\ADMIN-JY\\Desktop\\newFiles";//默认当前文件夹
			getFile(basePath);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static void getFile(String path){
		File f = new File(path);
		if(f.isDirectory()) {//是否为目录
			System.out.println("这是目录" + num + ":" + f.getName());
			num++;
			num_file++;
			//获取目录下所有文件列表
			File[] files = f.listFiles();
			for(int i = 0;i<files.length;i++) {
				getFile(files[i].getAbsolutePath());
			}
			num_file--;
		}else {
			System.out.println(num_file + ":" + f.getName());
		}
	}
}
