import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Hw5_1 {

	public static void main(String[] args) {

		HashMap<String, Integer> hm = new HashMap<String, Integer>();

		String word;
		int minlen, k;

		final JFileChooser fc = new JFileChooser();
		File file;
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			file = fc.getSelectedFile();
		else {
			JOptionPane.showMessageDialog(null, "파일을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Scanner sc = new Scanner(System.in);
		System.out.print("minlen ? ");
		minlen = sc.nextInt();
		System.out.print("top-k ? ");
		k = sc.nextInt();

		sc = null;

		try {
			sc = new Scanner(file);

			while (sc.hasNext()) {
				word = sc.next();

				if (word.length() < minlen)
					continue;

				if (hm.get(word) != null) {
					hm.put(word, hm.get(word) + 1);
				}

				else
					hm.put(word, 1);
			}

			List<Map.Entry<String, Integer>> topKlist = new ArrayList<Map.Entry<String, Integer>>(hm.entrySet());

			Collections.sort(topKlist, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					if (o1.getValue() == o2.getValue())
						return o1.getKey().compareTo(o2.getKey());
					else
						return o2.getValue().compareTo(o1.getValue());
				}
			});

			int i = 1;

			for (Map.Entry<String, Integer> key : topKlist) {
				if (i <= k)
					System.out.println(i + ": [" + key.getKey() + "] " + key.getValue());
				i++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (sc != null)
			sc.close();
	}

}