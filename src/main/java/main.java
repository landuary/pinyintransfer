import com.alibaba.fastjson.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

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
public class main {

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
            String b = getAllPinyin(line);
            String[] arrayB = b.split(" ");
            int length = Constants.YANYINS.length;
            StringBuffer sbYuanyin = new StringBuffer();

            for (int i = 0; i < arrayB.length; i++) {
                for (int j = length - 1; j >= 0; j--) {
                    String yin = Constants.YANYINS[j];
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


    public static String getAllPinyin(String hanzi) {
        //输出格式设置
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        /**
         * 输出大小写设置
         *
         * LOWERCASE:输出小写
         * UPPERCASE:输出大写
         */
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

        /**
         * 输出音标设置
         *
         * WITH_TONE_MARK:直接用音标符（必须设置WITH_U_UNICODE，否则会抛出异常）
         * WITH_TONE_NUMBER：1-4数字表示音标
         * WITHOUT_TONE：没有音标
         */
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);

        /**
         * 特殊音标ü设置
         *
         * WITH_V：用v表示ü
         * WITH_U_AND_COLON：用"u:"表示ü
         * WITH_U_UNICODE：直接用ü
         */
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

        char[] hanYuArr = hanzi.trim().toCharArray();
        StringBuilder pinYin = new StringBuilder();

        try {
            for (int i = 0, len = hanYuArr.length; i < len; i++) {
                //匹配是否是汉字
                if (Character.toString(hanYuArr[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    //如果是多音字，返回多个拼音，这里只取第一个
                    String[] pys = PinyinHelper.toHanyuPinyinStringArray(hanYuArr[i], format);
                    if (pys !=null && pys.length > 0) {
                        pinYin.append(pys[0]).append(" ");
                    }
                } else {
                    pinYin.append(hanYuArr[i]).append(" ");
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return pinYin.toString();

    }
}
