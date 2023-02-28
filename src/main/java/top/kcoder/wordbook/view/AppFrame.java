package top.kcoder.wordbook.view;

import org.springframework.stereotype.Component;
import top.kcoder.wordbook.view.component.AppPanel;

import javax.swing.*;

import static top.kcoder.wordbook.view.AppFrameStyle.*;

/**
 * AppFrame
 *
 * @author xiejinjie
 * @date 2023/2/28
 */
@Component
public class AppFrame extends JFrame{
    private AppPanel appPanel;

    public void openFrame() {
        this.setTitle("单词本");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setBackground(FRAME_BACKGROUND_COLOR);
        this.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        appPanel = new AppPanel();
        this.add(appPanel);
        this.setVisible(true);
    }
}
