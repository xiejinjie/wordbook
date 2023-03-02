package top.kcoder.wordbook.view.component;

import javax.swing.*;
import java.awt.*;

import static top.kcoder.wordbook.view.AppFrameStyle.GLOBAL_FONT;
import static top.kcoder.wordbook.view.component.ConfirmDialogStyle.*;

/**
 * ConfirmDialog
 *
 * @author xiejinjie
 * @date 2023/3/2
 */
public class ConfirmDialog extends JDialog {

    private boolean result;

    public ConfirmDialog(Frame owner) {
        super(owner,"提示", true);
        addBtn();
        this.setBounds(DIALOG_BOUNDS);
        this.setVisible(true);
    }

    private void addBtn() {
        Container container = this.getContentPane();
        container.setLayout(null);
        JButton confirmBtn = new JButton("确认");
        confirmBtn.setBounds(BTN_CONFIRM_BOUNDS);
        confirmBtn.setFont(GLOBAL_FONT);
        container.add(confirmBtn);

        JButton cancelBtn = new JButton("取消");
        cancelBtn.setBounds(BTN_CANCEL_BOUNDS);
        cancelBtn.setFont(GLOBAL_FONT);
        container.add(cancelBtn);

        confirmBtn.addActionListener(e -> {
            this.result = true;
            this.dispose();
        });

        cancelBtn.addActionListener(e -> {
            this.result = false;
            this.dispose();
        });
    }

    public boolean getResult() {
        return result;
    }
}
