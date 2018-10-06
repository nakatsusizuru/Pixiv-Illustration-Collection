package crawler.domain;

public class Image_urls {
    private String original;
    private String large;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    @Override
    public String toString() {
        return "Image_urls{" +
                "original='" + original + '\'' +
                ", large='" + large + '\'' +
                '}';
    }
}
