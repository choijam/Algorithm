import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Hw5_2 {
	public static void main(String args[]) {

		TreeMap<String, Integer> tm = new TreeMap<String, Integer>();
		Map<String, Integer> topKmap = new TreeMap<String, Integer>();

		int topK;
		double total;
		double support, approval;

		Scanner input = new Scanner(System.in);

		final JFileChooser fc = new JFileChooser(); // 파일 선택기를 사용
		File file;
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			file = fc.getSelectedFile();
		else {
			JOptionPane.showMessageDialog(null, "파일을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			return;
		}

		System.out.print("Top-K? ");
		topK = input.nextInt();
		System.out.print("신뢰도? ");
		approval = input.nextDouble();
		System.out.print("지지도? ");
		support = input.nextDouble();

		Scanner sc = null;

		ArrayList<Integer> al = new ArrayList<Integer>();
		try {
			sc = new Scanner(file);

			total = sc.nextDouble();
			double arr[] = new double[1001];

			while (sc.hasNext()) {
				int id = sc.nextInt();
				int k = sc.nextInt();

				for (int i = 0; i < k; i++) {
					al.add(sc.nextInt());
				}

				for (int i = 0; i < k; i++) {
					int first = al.get(i);
					arr[first]++;

					for (int j = i + 1; j < k; j++) {
						int second = al.get(j);
						String thing = first + "," + second;
						String thingRever = second + "," + first;

						if (topKmap.containsKey(thing))
							topKmap.put(thing, topKmap.get(thing) + 1);

						if (!topKmap.containsKey(thing))
							topKmap.put(thing, 1);

						if (tm.containsKey(thing))
							tm.put(thing, tm.get(thing) + 1);

						if (!tm.containsKey(thing))
							tm.put(thing, 1);

						if (tm.containsKey(thingRever))
							tm.put(thingRever, tm.get(thingRever) + 1);

						if (!tm.containsKey(thingRever))
							tm.put(thingRever, 1);

					}
				}
				al.clear();
			}

			List<Map.Entry<String, Integer>> topKlist = new ArrayList<Map.Entry<String, Integer>>(topKmap.entrySet());

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
				if (i <= topK)
					System.out.println(i + ": [" + key.getKey() + "] " + key.getValue());
				i++;
			}

			
			Set<Map.Entry<String, Integer>> set = tm.entrySet();

			for (Map.Entry<String, Integer> me : set) {
				StringTokenizer tokens = new StringTokenizer(me.getKey());
				int firstNum = Integer.parseInt(tokens.nextToken(","));
				int secNum = Integer.parseInt(tokens.nextToken(","));

				if (support <= me.getValue() / total) {
					if (approval <= (me.getValue() / arr[firstNum]))
						System.out.println(firstNum + " -> " + secNum + " : 지지도 = " + me.getValue() / total + " 신뢰도 = "
								+ me.getValue() / arr[firstNum]);
				}
			}

		} catch (

		FileNotFoundException e)

		{
			e.printStackTrace();
		}
		if (sc != null)
			sc.close();
		input.close();
	}
}