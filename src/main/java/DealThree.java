import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PACKAGE_NAME
 *
 * @author duansong.ds
 * @version 2020/10/26
 */
public class DealThree {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/duansong/git/tools/src/main/resources/sort.txt");
        readFile(file);
    }

    private static void readFile(File fin) throws IOException {
        FileInputStream fis = new FileInputStream(fin);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        List<ChineseEntity> chineseEntities = new ArrayList<>();
        List<ChineseEntity> singleChineseEntities = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String b = PinyinUtils.getAllPinyin(line).trim();

            String yuanyinByPinyin = PinyinUtils.getYuanyinByPinyin(b);

            if (line != null) {
                ChineseEntity chineseEntity = new ChineseEntity();
                chineseEntity.setPinyin(Arrays.asList(yuanyinByPinyin.split(" ")));
                chineseEntity.setHanzi(line.trim());
                chineseEntities.add(chineseEntity);
                if (line.length() == 1) {
                    singleChineseEntities.add(chineseEntity);
                }
            }
        }


        writeFile("/Users/duansong/git/tools/src/main/resources/all_fuyin.json", chineseEntities);
        writeFile("/Users/duansong/git/tools/src/main/resources/single_fuyin.json", singleChineseEntities);

        br.close();
    }

    private static void writeFile(String path, List<ChineseEntity> chineseEntities) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);//创建文本文件

            for (int i = 0; i < chineseEntities.size(); i++) {
                fileWriter.write(JSONObject.toJSONString(chineseEntities.get(i)));
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
