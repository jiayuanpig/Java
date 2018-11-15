package File11_04;

import java.io.File;
import java.io.IOException;

public class FileTest {

	
//	f.getName()：返回文件名 temp.dat
//	f.getParent()：返回文件所在目录名 data
//	f.getPath()：返回文件路径 data\temp.dat
//	f.getAbsolutePath()：返回绝对路径 …\data\temp.dat
//	f.exists()：文件是否存在
//	f.canWrite(), f.canRead()：文件是否可写、读
//	f.isFile(), f.isDirectory()：是否为文件或目录
//	f.lastModified(), f.length(), f.delete()：文件的最后修改日期、长度；删除文件
//	f.mkdir(), f.list()：创建一个目录；列出目录下所有的文件

	
	public static void main(String[] args) {
		// TODO 查询本地文件
		File f = new File("C:\\Users\\ADMIN-JY\\Desktop\\test.txt");
		if(!f.exists()) {
			System.out.println("文件不存在！");
			//文件不存在就创建新文件
			try {
				f.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		// TODO 查询本地目录
		File f2 = new File("C:\\Users\\ADMIN-JY\\Desktop\\newFiles");
		if(f2.mkdirs()) {//创建目录
			System.out.println("YES");
		}else {
			System.out.println("NO");
		}

	}

}
