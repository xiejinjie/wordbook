package top.kcoder.wordbook;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import top.kcoder.wordbook.util.BeanUtil;
import top.kcoder.wordbook.view.AppFrame;

/**
 * WordAssistantApp
 *
 * @author xiejinjie
 * @date 2023/2/28
 */
@SpringBootApplication
public class WordbookApp implements ApplicationRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(WordbookApp.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        AppFrame frame = BeanUtil.getBean(AppFrame.class);
        frame.openFrame();
    }
}
