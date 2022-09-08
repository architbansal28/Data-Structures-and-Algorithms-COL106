package col106.assignment4.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

import col106.assignment4.HashMap.HashMap;
import col106.assignment4.WeakAVLMap.WeakAVLMap;

public class Map<V> {
	
	static PrintStream out;
	public PrintStream fileout() {
		return out;
	}
	
	public Map() {
		// write your code here	
	}

	public void eval(String inputFileName, String outputFileName) {
		// write your code here	
		File file;
        try {
			out = new PrintStream(new FileOutputStream(outputFileName, false), true);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        System.setOut(out);
        URL url = Map.class.getResource(inputFileName);
        file = new File(url.getPath());
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String st = br.readLine();
            int N = Integer.parseInt(st);
            WeakAVLMap<String, String> weakAVLMap = new WeakAVLMap<>();
            HashMap<String> hashMap = new HashMap<>(N);
            int weakAVLTimeI = 0;
            int weakAVLTimeD = 0;
            int hashMapTimeI = 0;
            int hashMapTimeD = 0;
            for (int i = 0; i < N; i++) {
            	st = br.readLine();
                String[] cmd = st.split(" ");
                String key, value;
                switch (cmd[0]) {
                    case "I":
                    	key = cmd[1].substring(0, cmd[1].length()-1);
                        value = cmd[2];
                        long startT1 = System.currentTimeMillis();
                        weakAVLMap.put(key, value);
                        long endT1 = System.currentTimeMillis();
                        weakAVLTimeI += (endT1 - startT1);
                        long startT2 = System.currentTimeMillis();
                        hashMap.put(key, value);
                        long endT2 = System.currentTimeMillis();
                        hashMapTimeI += (endT2 - startT2);
                        break;
                    case "D":
                        key = cmd[1];
                        long startT3 = System.currentTimeMillis();
                        weakAVLMap.remove(key);
                        long endT3 = System.currentTimeMillis();
                        weakAVLTimeD += (endT3 - startT3);
                        long startT4 = System.currentTimeMillis();
                        hashMap.remove(key);
                        long endT4 = System.currentTimeMillis();
                        hashMapTimeD += (endT4 - startT4);
                        break;
                    default:
                        System.err.println("Unknown command: " + st);
                }
            }
            System.out.println("Operations WAVL HashMap");
            System.out.println("Insertions " + weakAVLTimeI + " " + hashMapTimeI);
            System.out.println("Deletions " + weakAVLTimeD + " " + hashMapTimeD);
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + file.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
