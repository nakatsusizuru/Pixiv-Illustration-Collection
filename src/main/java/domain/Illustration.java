package domain;

public class Illustration {
    private String date;
    private String id;
    private String artistname;
    private String artistid;
    private String url;
    private String title;
    private Integer rank;
    private Integer height;
    private Integer width;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getArtistid() {
        return artistid;
    }

    public void setArtistid(String artistid) {
        this.artistid = artistid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Illustration{" +
                "date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", artistname='" + artistname + '\'' +
                ", artistid='" + artistid + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", rank=" + rank +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
