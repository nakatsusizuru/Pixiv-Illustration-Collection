package crawler.domain;

public class ImageInfo {
    public Integer width;
    public Integer height;

    public ImageInfo() {
        this.height = this.width = -1;

    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
