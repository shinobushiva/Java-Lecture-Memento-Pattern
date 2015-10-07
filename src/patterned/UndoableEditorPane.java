package patterned;

import javax.swing.JEditorPane;

@SuppressWarnings("serial")
class UndoableEditorPane extends JEditorPane {

	/**
	 * 自身の状態を保存するためのメメント(思い出）クラス
	 */
	static class Memento { // クラスのアクセス制御はパッケージアクセス
		private String title;
		private String body;

		public Memento(String title, String body) {
			this.title = title;
			this.body = body;
		}

		public String getTitle() {
			return title;
		}

		public String getBody() {
			return body;
		}
	}

	private String title;
	private String body;

	public UndoableEditorPane() {
		super("text/html", "");
		setEditable(false);
		setText("");
	}

	public Memento createMemento() {
		Memento mem = new Memento(title, body);
		return mem;
	}

	public void restoreMemento(Memento mem) {
		this.title = mem.title;
		this.body = mem.body;
	}

	public void set(String title, String body) {
		this.title = title;
		this.body = body;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void updateView() {
		StringBuilder buf = new StringBuilder();

		if (!title.isEmpty()) {
			buf.append("<h1>").append(title).append("</h1>");
		}

		buf.append("<div>");
		buf.append(body);
		buf.append("</div>");

		setText(buf.toString());
	}

}