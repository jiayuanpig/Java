package File11_04;

import java.io.File;

public class FileDirectory2 {

	
	static int num = 0;//��¼�ļ�����
	static int num_file = -1;//��¼����һ���ļ�
	
	public static void main(String[] args) {
		// TODO ����ļ���,��ȡ������ļ��������ļ������������ļ��м����ļ���
		try {
			String basePath  = "C:\\Users\\ADMIN-JY\\Desktop\\newFiles";//Ĭ�ϵ�ǰ�ļ���
			getFile(basePath);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static void getFile(String path){
		File f = new File(path);
		if(f.isDirectory()) {//�Ƿ�ΪĿ¼
			System.out.println("����Ŀ¼" + num + ":" + f.getName());
			num++;
			num_file++;
			//��ȡĿ¼�������ļ��б�
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
