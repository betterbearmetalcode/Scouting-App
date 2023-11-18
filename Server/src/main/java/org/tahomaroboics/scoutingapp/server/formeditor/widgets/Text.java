package org.tahomaroboics.scoutingapp.server.formeditor.widgets;

public class Text extends Widget {

    private final int DEFAULT_FONT_SIZE = 12;//idk what the default should be
    private final String DEFAULT_TEXT  = "";
    private String text;

    private int fontSize;

    public Text(String id, String val) {
        super(id);
        this.text = val;
        this.fontSize = DEFAULT_FONT_SIZE;
    }

    public Text(String id, String text, int fontSize) {
        super(id);
        this.text = text;
        this.fontSize = fontSize;
    }

    public Text(String id){
        super(id);
        this.text = DEFAULT_TEXT;
        this.fontSize = DEFAULT_FONT_SIZE;

    }

    public Text(String id, int fontSize) {
        super(id);
        this.fontSize = fontSize;
        this.text = DEFAULT_TEXT;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
