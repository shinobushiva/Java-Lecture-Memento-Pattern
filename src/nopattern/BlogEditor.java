package nopattern;

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

public class BlogEditor extends JFrame {

	private JTextField titleField;
	private JTextArea bodyArea;
	private UndoableEditorPane editorPane;

	/**
	 * Undoを保持するスタック
	 */
	private Stack<String> bodyUndoStack;

	/**
	 * Undo（やり直し）情報を保存するを実行する
	 */
	private void saveUndo() {
		bodyUndoStack.push(editorPane.getBody());
	}

	/**
	 * Undo（やり直し）を実行する
	 */
	private void performUndo() {
		if (bodyUndoStack.isEmpty())
			return;

		String body = bodyUndoStack.pop();
		editorPane.setBody(body);
	}

	public BlogEditor() {
		initComponent();
		bodyUndoStack = new Stack<String>();
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
				bodyArea.setText(editorPane.getBody()); // bodyのテキストを取得
				editorPane.updateView(); // HTMLビューを更新
			}
		});

		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(titleField, BorderLayout.NORTH);
		leftPanel.add(new JScrollPane(bodyArea), BorderLayout.CENTER);

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
