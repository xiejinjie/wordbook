package top.kcoder.wordbook.domain;

/**
 * Word
 *
 * @author xiejinjie
 * @date 2023/2/28
 */
public class Word {
    private String word;

    private Integer times;

    public Word() {
    }

    public Word(String word, Integer times) {
        this.word = word;
        this.times = times;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
