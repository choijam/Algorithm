import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Hw4 {

	public static void main(String[] args) {
		
		int M= Integer.parseInt(args[0]);
		int hashVal=0;
		double total,avg,sq;
		int arr[]= new int[M];
		String word,data;
		HashSet<String> hs=new HashSet<String>();
	
		total=0.0;
		sq=0.0;
		final JFileChooser fc = new JFileChooser();		
		File file;
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				 file = fc.getSelectedFile();
		else  {
				JOptionPane.showMessageDialog(null, "파일을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
				return;
		}

		Scanner sc = null;
	
		
		try {
			BufferedWriter pw = new BufferedWriter(new FileWriter("out.txt",false));
			sc = new Scanner(file);

			while (sc.hasNext()) {
				word = sc.next();
				if(hs.add(word)){
					hashVal=(word.hashCode()&0x7fffffff)%M;
					arr[hashVal]++;
					total++;
				}
			}
			
			avg=total/M;
			
			for(int i=0;i<M;i++){
				data = i+","+arr[i];
	            pw.write(data);
	            pw.newLine();
	            sq=sq+(arr[i]-avg)*(arr[i]-avg);
			}
			
			pw.close();

			
			System.out.println("전체 단어 수  = "+total);
			System.out.println("해쉬 테이블의 크기 = "+M);
			System.out.println("평균 단어 수 = "+Math.round(avg*100d)/100d);
			System.out.println("표준 편차 = "+Math.round((Math.sqrt(sq/(M)))*100d)/100d);
	
			
		} catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) {
			e.printStackTrace();
		}
		if (sc != null)
			sc.close();
	}

}
