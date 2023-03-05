package top.kcoder.wordbook.view.component;

import top.kcoder.wordbook.domain.Word;
import top.kcoder.wordbook.service.ICmdService;
import top.kcoder.wordbook.service.IWordbookService;
import top.kcoder.wordbook.util.BeanUtil;
import top.kcoder.wordbook.util.CommonUtil;
import top.kcoder.wordbook.view.AppFrame;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import java.util.List;
import java.util.Locale;

import static top.kcoder.wordbook.constant.WordbookConstant.DICT_URL;
import static top.kcoder.wordbook.view.AppFrameStyle.GLOBAL_FONT;
import static top.kcoder.wordbook.view.component.AppPanelStyle.*;

/**
 * PackagePanel
 *
 * @author xiejinjie
 * @date 2023/2/17
 */
public class AppPanel extends JPanel {
    private JTextField wordField;

    private JButton searchBtn;

    private JButton paddingBtn;

    private JButton deleteBtn;

    private JTable wordbook;

    private IWordbookService wordbookService = BeanUtil.getBean(IWordbookService.class);

    private ICmdService cmdService = BeanUtil.getBean(ICmdService.class);

    public AppPanel() {
        this.setLayout(null);
        this.setFont(GLOBAL_FONT);
        addOperation();
        addWordBook();
    }

    private void addOperation() {
        wordField = new JTextField(10);
        wordField.setBounds(FIELD_WORD_BOUNDS);
        wordField.setFont(GLOBAL_FONT);
        this.add(wordField);

        searchBtn = new JButton("搜索");
        searchBtn.setBounds(BTN_SEARCH_BOUNDS);
        searchBtn.setFont(GLOBAL_FONT);
        this.add(searchBtn);

        paddingBtn = new JButton("填入");
        paddingBtn.setBounds(BTN_PADDING_BOUNDS);
        paddingBtn.setFont(GLOBAL_FONT);
        this.add(paddingBtn);

        deleteBtn = new JButton("删除");
        deleteBtn.setBounds(BTN_DELETE_BOUNDS);
        deleteBtn.setFont(GLOBAL_FONT);
        this.add(deleteBtn);

        searchBtn.addActionListener(e -> {
            String word = wordField.getText().trim().toLowerCase(Locale.ROOT);
            if (CommonUtil.isEmpty(word)) {
                return;
            }
            cmdService.openBrowser(DICT_URL + word);
            wordbookService.recordWord(word);
            wordbook.updateUI();
        });

        paddingBtn.addActionListener(e -> {
            int row = wordbook.getSelectedRow();
            if (row != -1) {
                String word = (String) wordbook.getValueAt(row, 1);
                wordField.setText(word);
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = wordbook.getSelectedRow();
            if (row != -1) {
                ConfirmDialog confirmDialog = new ConfirmDialog(BeanUtil.getBean(AppFrame.class));
                if (confirmDialog.getResult()) {
                    String word = (String) wordbook.getValueAt(row, 1);
                    wordbookService.removeWord(word);
                    wordbook.updateUI();
                }
            }
        });
    }

    private void addWordBook() {
        List<Word> wordList = wordbookService.getWordbook();
        wordbook = new JTable(new WordbookTableModel(wordList));
        wordbook.setFont(GLOBAL_FONT);
        wordbook.setFillsViewportHeight(true);
        wordbook.getColumnModel().getColumn(0).setPreferredWidth(100);
        wordbook.getColumnModel().getColumn(0).setMaxWidth(200);
        wordbook.getColumnModel().getColumn(0).setMinWidth(50);
        wordbook.setRowHeight(20);

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) wordbook.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        wordbook.setDefaultRenderer(Object.class,r);
        wordbook.setRowSelectionAllowed(true);
        wordbook.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane=new JScrollPane();
        scrollPane.setBounds(TABLE_WORDBOOK_BOUNDS);
        scrollPane.setViewportView(wordbook);
        this.add(scrollPane);
    }


    class WordbookTableModel extends AbstractTableModel {
        private List<Word> wordList;

        private String[] columns = {"次数", "单词"};

        public WordbookTableModel(List<Word> wordList) {
            this.wordList = wordList;
        }

        @Override
        public int getRowCount() {
            return wordList.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Word word = wordList.get(rowIndex);
            if (columnIndex == 0) {
                return word.getTimes();
            } else {
                return word.getWord();
            }
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }
    }
}
