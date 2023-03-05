package top.kcoder.wordbook.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.kcoder.wordbook.domain.Word;
import top.kcoder.wordbook.service.IWordbookService;
import top.kcoder.wordbook.util.CommonUtil;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

import static top.kcoder.wordbook.constant.WordbookConstant.SAVE_FILENAME;

/**
 * WordbookServiceImpl
 *
 * @author xiejinjie
 * @date 2023/2/28
 */
@Service
public class WordbookServiceImpl implements IWordbookService {
    private static final Logger logger = LoggerFactory.getLogger(WordbookServiceImpl.class);

    private List<Word> wordbook = new LinkedList<>();

    @PostConstruct
    public void loadWordbook() {
        File saveFile = new File(SAVE_FILENAME);
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                logger.error("创建文件失败");
                throw new RuntimeException(e);
            }
            return;
        }
        try (FileReader fr = new FileReader(SAVE_FILENAME);
             BufferedReader br = new BufferedReader(fr)){
            logger.info("读取文件...");
            String line = null;
            while ((line =br.readLine()) != null) {
                if (CommonUtil.isEmpty(line)) {
                    continue;
                }
                String[] ss = line.split("\\|");
                wordbook.add(new Word(ss[0], Integer.valueOf(ss[1])));
            }
            wordbook.sort(Comparator.comparing(Word::getTimes).reversed());
        } catch (IOException e) {
            logger.error("读取文件失败");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Word> getWordbook() {
        return Collections.unmodifiableList(wordbook);
    }

    @Override
    public void recordWord(String wd) {
        wordbook.sort(Comparator.comparing(Word::getTimes).reversed());
        Word word = wordbook.stream().filter(w -> w.getWord().equals(wd)).findFirst().orElse(null);
        if (word != null) {
            word.setTimes(word.getTimes() + 1);
            wordbook.remove(word);
            wordbook.add(0, word);
        } else {
            wordbook.add(0, new Word(wd, 1));
        }
        saveWordbook();
    }

    @Override
    public void removeWord(String word) {
        if (CommonUtil.isEmpty(word)) {
            return;
        }
        Iterator<Word> iterator = wordbook.iterator();
        while (iterator.hasNext()) {
            Word next = iterator.next();
            if (word.equals(next.getWord())) {
                iterator.remove();
                saveWordbook();
                break;
            }
        }
    }

    private synchronized void saveWordbook() {
        try(FileWriter fw = new FileWriter(SAVE_FILENAME);
            BufferedWriter bw = new BufferedWriter(fw)) {
            for (Word word : wordbook) {
                bw.write(word.getWord() + "|" + word.getTimes());
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
