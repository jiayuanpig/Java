package File11_04;

import java.io.File;

public class FileDirectory {

	public static void main(String[] args) {
		// TODO �����ǰ�ļ���,��ȡ������ļ��������ļ���
		try {
			File f = new File("");//Ĭ�ϵ�ǰ�ļ���
			String str = f.getAbsolutePath();
			System.out.println("str:" + str);//�������·��
			File file = new File(str);//���þ���·��Ѱ���ļ�
			if(file.isDirectory()) {//�Ƿ�ΪĿ¼
				//��ȡĿ¼�������ļ��б�
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
