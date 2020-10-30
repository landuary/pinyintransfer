import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * PACKAGE_NAME
 *
 * @author duansong.ds
 * @version 2020/10/29
 */
public class PinyinUtils {
    public static String[] YANYINS = new String[]{
            "a",
            "ā",
            "á",
            "ă",
            "à",
            "e",
            "ē",
            "é",
            "ĕ",
            "è",
            "i",
            "ī",
            "í",
            "ĭ",
            "ì",
            "o",
            "ō",
            "ó",
            "ŏ",
            "ò",
            "u",
            "ū",
            "ú",
            "ŭ",
            "ù",
            "ü",
            "ǘ",
            "ǚ",
            "ǜ",
            "an",
            "ān",
            "án",
            "ăn",
            "àn",
            "au",
            "āu",
            "áu",
            "ău",
            "àu",
            "ai",
            "āi",
            "ái",
            "ăi",
            "ài",
            "ao",
            "āo",
            "áo",
            "ăo",
            "ào",
            "ia",
            "iā",
            "iá",
            "iă",
            "ià",
            "ua",
            "uā",
            "uá",
            "uă",
            "uà",
            "en",
            "ēn",
            "én",
            "ĕn",
            "èn",
            "ei",
            "ēi",
            "éi",
            "ĕi",
            "èi",
            "er",
            "ēr",
            "ér",
            "ĕr",
            "èr",
            "ie",
            "iē",
            "ié",
            "iĕ",
            "iè",
            "ue",
            "uē",
            "ué",
            "uĕ",
            "uè",
            "in",
            "īn",
            "ín",
            "ĭn",
            "ìn",
            "iu",
            "iū",
            "iú",
            "iŭ",
            "iù",
            "on",
            "ōn",
            "ón",
            "ŏn",
            "òn",
            "ou",
            "ōu",
            "óu",
            "ŏu",
            "òu",
            "uo",
            "uō",
            "uó",
            "uŏ",
            "uò",
            "ui",
            "uī",
            "uí",
            "uĭ",
            "uì",
            "un",
            "ūn",
            "ún",
            "ŭn",
            "ùn",
            "ün",
            "ǘn",
            "ǚn",
            "ǜn",
            "üe",
            "ǘe",
            "ǚe",
            "ǜe",
            "ǚan",
            "ǚān",
            "ǚán",
            "ǚăn",
            "ǚàn",
            "iao",
            "iāo",
            "iáo",
            "iăo",
            "iào",
            "ian",
            "iān",
            "ián",
            "iăn",
            "iàn",
            "uan",
            "uān",
            "uán",
            "uăn",
            "uàn",
            "uai",
            "uāi",
            "uái",
            "uăi",
            "uài",
            "ang",
            "āng",
            "áng",
            "ăng",
            "àng",
            "eng",
            "ēng",
            "éng",
            "ĕng",
            "èng",
            "ing",
            "īng",
            "íng",
            "ĭng",
            "ìng",
            "ong",
            "ōng",
            "óng",
            "ŏng",
            "òng",
            "iang",
            "iāng",
            "iáng",
            "iăng",
            "iàng",
            "iong",
            "iōng",
            "ióng",
            "iŏng",
            "iòng",
            "uang",
            "uāng",
            "uáng",
            "uăng",
            "uàng",
    };

    public static String getYuanyinByPinyin(String pinyins) {
        int length = PinyinUtils.YANYINS.length;
        String[] arrPinyin = pinyins.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arrPinyin.length; i++) {
            for (int j = length - 1; j >= 0; j--) {
                String yin = PinyinUtils.YANYINS[j];
                if (arrPinyin[i].endsWith(yin)) {
                    sb.append(yin).append(" ");
                    break;
                }
            }
        }
        return sb.toString().trim();
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
