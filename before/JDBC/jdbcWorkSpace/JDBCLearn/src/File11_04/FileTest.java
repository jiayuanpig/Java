package File11_04;

import java.io.File;
import java.io.IOException;

public class FileTest {

	
//	f.getName()�������ļ��� temp.dat
//	f.getParent()�������ļ�����Ŀ¼�� data
//	f.getPath()�������ļ�·�� data\temp.dat
//	f.getAbsolutePath()�����ؾ���·�� ��\data\temp.dat
//	f.exists()���ļ��Ƿ����
//	f.canWrite(), f.canRead()���ļ��Ƿ��д����
//	f.isFile(), f.isDirectory()���Ƿ�Ϊ�ļ���Ŀ¼
//	f.lastModified(), f.length(), f.delete()���ļ�������޸����ڡ����ȣ�ɾ���ļ�
//	f.mkdir(), f.list()������һ��Ŀ¼���г�Ŀ¼�����е��ļ�

	
	public static void main(String[] args) {
		// TODO ��ѯ�����ļ�
		File f = new File("C:\\Users\\ADMIN-JY\\Desktop\\test.txt");
		if(!f.exists()) {
			System.out.println("�ļ������ڣ�");
			//�ļ������ھʹ������ļ�
			try {
				f.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		// TODO ��ѯ����Ŀ¼
		File f2 = new File("C:\\Users\\ADMIN-JY\\Desktop\\newFiles");
		if(f2.mkdirs()) {//����Ŀ¼
			System.out.println("YES");
		}else {
			System.out.println("NO");
		}

	}

}
