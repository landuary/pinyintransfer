import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PACKAGE_NAME
 *
 * @author duansong.ds
 * @version 2020/10/26
 */
public class DealTwo {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/duansong/git/tools/src/main/resources/sort.txt");
        readFile(file);
    }

    private static void readFile(File fin) throws IOException {
        FileInputStream fis = new FileInputStream(fin);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        HashMap<String, ChineseIndexEntity> chineseEntities = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String b = PinyinUtils.getAllPinyin(line).trim();
            String[] arrayB = b.split(" ");
            int length = PinyinUtils.YANYINS.length;
            List<String> arrayYuanyin = new ArrayList<>();

            for (int j = length - 1; j >= 0; j--) {
                String yin = PinyinUtils.YANYINS[j];
                if (!chineseEntities.containsKey(yin)) {
                    ChineseIndexEntity chineseIndexEntity = new ChineseIndexEntity();
                    chineseEntities.put(yin, chineseIndexEntity);
                }
                ChineseIndexEntity chineseIndexEntity = chineseEntities.get(yin);
                chineseIndexEntity.setPinyin(yin);
                List<List<String>> hanzi = chineseIndexEntity.getHanzi();
                if (hanzi == null) {
                    hanzi = new ArrayList<>();
                }

                if (arrayB[arrayB.length - 1].endsWith(yin)) {
                    List<String> stringList = new ArrayList<>();
                    stringList.add(line.trim());
                    stringList.add(PinyinUtils.getYuanyinByPinyin(b));
                    hanzi.add(stringList);
                    chineseIndexEntity.setHanzi(hanzi);
                    break;
                }
            }
        }


        writeFile("/Users/duansong/git/tools/src/main/resources/all_fuyin_index.json", chineseEntities);

        br.close();
    }

    private static void writeFile(String path, HashMap<String, ChineseIndexEntity> chineseEntities) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);//创建文本文件

            for (Map.Entry<String, ChineseIndexEntity> stringChineseIndexEntityEntry : chineseEntities.entrySet()) {
                fileWriter.write(JSONObject.toJSONString(stringChineseIndexEntityEntry.getValue()));
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
