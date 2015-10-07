package lesson;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import lesson.UndoableEditorPane.Memento;

/**
 * Mementoパターンを使ってUndoを実装したエディターです。
 * 
 * [課題] このエディタにRedo機能を実装してください。（Redo: Undoで戻した動作を再度行う）
 * Redoの実行のさせ方はRedoボタンをつけてもキーボードショートカットなどに割り当ててもどのような方法でも大丈夫です。
 * 
 * @author shiva
 *
 */
public class BlogEditor extends JFrame {

	private JTextField titleField;
	private JTextArea bodyArea;
	private UndoableEditorPane editorPane;

	/**
	 * Undoを保持するスタック
	 */
	private Stack<Memento> undoStack;

	/**
	 * Undo（やり直し）情報を保存するを実行する
	 */
	private void saveUndo() {
		undoStack.push(editorPane.createMemento());
	}

	/**
	 * Undo（やり直し）を実行する
	 */
	private void performUndo() {
		if (undoStack.isEmpty())
			return;

		Memento mem = undoStack.pop();
		editorPane.restoreMemento(mem);
	}

	public BlogEditor() {

		initComponent();
		undoStack = new Stack<Memento>();
	}

	private void initComponent() {

		JPanel leftPanel = new JPanel();

		titleField = new JTextField("HTML Editor");
		bodyArea = new JTextArea();
		bodyArea.setText("<b>Hello</b> <i>HTML!</i>");

		JButton saveButton = new JButton("Update");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveUndo(); // undo情報を保存
				editorPane.set(titleField.getText(), bodyArea.getText()); // テキストをセット
				editorPane.updateView(); // HTMLビューを更新
			}
		});

		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				performUndo(); // Undoを実行
				titleField.setText(editorPane.getTitle()); // titleのテキストを取得
				bodyArea.setText(editorPane.getBody()); // bodyのテキストを取得
				editorPane.updateView(); // HTMLビューを更新
			}
		});

		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(titleField, BorderLayout.NORTH);
		leftPanel.add(new JScrollPane(bodyArea), BorderLayout.CENTER);

		// ボタンを追加
		JPanel buttons = new JPanel();
		buttons.add(undoButton);
		buttons.add(saveButton);

		leftPanel.add(buttons, BorderLayout.SOUTH);

		editorPane = new UndoableEditorPane();
		editorPane.set(titleField.getText(), bodyArea.getText());

		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jsp.setResizeWeight(0.5);
		jsp.setLeftComponent(leftPanel);
		jsp.setRightComponent(new JScrollPane(editorPane));

		add(jsp);
		editorPane.set(titleField.getText(), bodyArea.getText()); // テキストをセット
		editorPane.updateView(); // HTMLビューを更新

	}

	public static void main(String[] args) {
		BlogEditor be = new BlogEditor();
		be.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		be.setBounds(100, 100, 960, 480);
		be.setVisible(true);
	}

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1508601814645429086L;

}
