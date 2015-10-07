package nopattern;

import javax.swing.JEditorPane;

@SuppressWarnings("serial")
class UndoableEditorPane extends JEditorPane {

	private String title;
	private String body;

	public UndoableEditorPane() {
		super("text/html", "");
		setEditable(false);
		setText("");
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