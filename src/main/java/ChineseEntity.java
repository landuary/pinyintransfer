import java.util.List;

/**
 * PACKAGE_NAME
 *
 * @author duansong.ds
 * @version 2020/10/26
 */
public class ChineseEntity {

    private List<String> pinyin;
    private String hanzi;


    public List<String> getPinyin() {
        return pinyin;
    }

    public void setPinyin(List<String> pinyin) {
        this.pinyin = pinyin;
    }

    public String getHanzi() {
        return hanzi;
    }

    public void setHanzi(String hanzi) {
        this.hanzi = hanzi;
    }
}
