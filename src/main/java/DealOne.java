import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * PACKAGE_NAME
 *
 * @author duansong.ds
 * @version 2020/10/26
 */
public class DealOne {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/duansong/git/tools/src/main/resources/sort.txt");
        readFile(file);
    }

    private static void readFile(File fin) throws IOException {
        FileInputStream fis = new FileInputStream(fin);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        List<String> chineseEntities = new ArrayList<>();
        List<String> singleChineseEntities = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String b = PinyinUtils.getAllPinyin(line);
            String[] arrayB = b.split(" ");
            int length = PinyinUtils.YANYINS.length;
            StringBuffer sbYuanyin = new StringBuffer();

            for (int i = 0; i < arrayB.length; i++) {
                for (int j = length - 1; j >= 0; j--) {
                    String yin = PinyinUtils.YANYINS[j];
                    if (arrayB[i].endsWith(yin)) {
                        sbYuanyin.append(yin).append(" ");
                        break;
                    }
                }
            }

            if (line != null) {
                String temp = sbYuanyin.toString().trim().concat(",").concat(line.trim());
                chineseEntities.add(temp);
                if (line.length() == 1) {
                    singleChineseEntities.add(temp);
                }
            }
        }


        writeFile("/Users/duansong/git/tools/src/main/resources/all.csv", chineseEntities);
        writeFile("/Users/duansong/git/tools/src/main/resources/single.csv", singleChineseEntities);

        br.close();
    }

    private static void writeFile(String path, List<String> chineseEntities) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);//创建文本文件
            fileWriter.write("pinyin,hanzi");
            fileWriter.write("\n");

            for (int i = 0; i < chineseEntities.size(); i++) {
                fileWriter.write(chineseEntities.get(i));
                fileWriter.write("\n");
            }

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
