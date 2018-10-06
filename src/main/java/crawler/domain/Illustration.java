package crawler.domain;

import java.util.List;

public class Illustration {
    private String title;
    private String url;
    private User user;
    private String id;
    private List<Meta_page> meta_pages;
    private Meta_single_page meta_single_page;
    private Integer rank;
    private Image_urls image_urls;
    private Integer height;
    private Integer width;
    private ImageInfo imageInfo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Meta_page> getMeta_pages() {
        return meta_pages;
    }

    public void setMeta_pages(List<Meta_page> meta_pages) {
        this.meta_pages = meta_pages;
    }

    public Meta_single_page getMeta_single_page() {
        return meta_single_page;
    }

    public void setMeta_single_page(Meta_single_page meta_single_page) {
        this.meta_single_page = meta_single_page;
    }

    public String getRank() {
        return String.valueOf(rank);
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Image_urls getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(Image_urls image_urls) {
        this.image_urls = image_urls;
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

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }
}
