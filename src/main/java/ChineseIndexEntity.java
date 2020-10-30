import java.util.List;

/**
 * PACKAGE_NAME
 *
 * @author duansong.ds
 * @version 2020/10/26
 */
public class ChineseIndexEntity {

    private List<List<String>> hanzi;
    private String pinyin;

    public List<List<String>> getHanzi() {
        return hanzi;
    }

    public void setHanzi(List<List<String>> hanzi) {
        this.hanzi = hanzi;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
