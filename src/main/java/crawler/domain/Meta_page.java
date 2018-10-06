package crawler.domain;

public class Meta_page {
    private Image_urls image_urls;

    public Image_urls getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(Image_urls image_urls) {
        this.image_urls = image_urls;
    }

    @Override
    public String toString() {
        return "Meta_page{" +
                "image_urls=" + image_urls +
                '}';
    }
}
