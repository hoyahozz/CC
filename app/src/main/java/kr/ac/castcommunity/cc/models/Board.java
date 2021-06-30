package kr.ac.castcommunity.cc.models;

public class Board {

    private String id;
    private String title;
    private String writer;
    private String contents;
    private String am;
    private String img;

    public Board() {

    }

    public Board(String id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Board(String id, String title,String writer, String contents, String am, String img) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.am = am;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", contents='" + contents + '\'' +
                ", am='" + am + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
