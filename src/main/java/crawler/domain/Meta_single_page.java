package crawler.domain;

public class Meta_single_page {
    private String original_image_url;

    public String getOriginal_image_url() {
        return original_image_url;
    }

    public void setOriginal_image_url(String original_image_url) {
        this.original_image_url = original_image_url;
    }

    @Override
    public String toString() {
        return "Meta_single_page{" +
                "original_image_url='" + original_image_url + '\'' +
                '}';
    }
}
