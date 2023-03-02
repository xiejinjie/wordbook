package top.kcoder.wordbook.service;

import top.kcoder.wordbook.domain.Word;

import java.util.List;

/**
 * IWordbookService
 *
 * @author xiejinjie
 * @date 2023/2/28
 */
public interface IWordbookService {
    List<Word> getWordbook();

    void recordWord(String word);

    void removeWord(String work);
}
