package crawler.utils;

import crawler.constant.Constant;
import crawler.domain.Illustration;
import crawler.domain.ImageInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//本地图片尺寸检测
public class ImageSizeUtil {
    public static void setImageSize(Illustration illustration) throws IOException {
        File file = new File("/home/PIC/" + Constant.DATE + "/" + String.valueOf(illustration.getRank()) + ".png");
        InputStream is = new FileInputStream(file);
        try {
            illustration.setImageInfo(processStream(is));
        } finally {
            is.close();
        }
    }

    private static ImageInfo processStream(InputStream is) throws IOException {
        ImageInfo imageInfo = new ImageInfo();
        int c1 = is.read();
        int c2 = is.read();
        int c3 = is.read();
        if (c1 == 'G' && c2 == 'I' && c3 == 'F') { // GIF
            is.skip(3);
            imageInfo.width = readInt(is, 2, false);
            imageInfo.height = readInt(is, 2, false);
        } else if (c1 == 0xFF && c2 == 0xD8) { // JPG
            while (c3 == 255) {
                int marker = is.read();
                int len = readInt(is, 2, true);
                if (marker == 192 || marker == 193 || marker == 194) {
                    is.skip(1);
                    imageInfo.height = readInt(is, 2, true);
                    imageInfo.width = readInt(is, 2, true);
                    break;
                }
                is.skip(len - 2);
                c3 = is.read();
            }
        } else if (c1 == 137 && c2 == 80 && c3 == 78) { // PNG
            is.skip(15);
            imageInfo.width = readInt(is, 2, true);
            is.skip(2);
            imageInfo.height = readInt(is, 2, true);
        } else if (c1 == 66 && c2 == 77) { // BMP
            is.skip(15);
            imageInfo.width = readInt(is, 2, false);
            is.skip(2);
            imageInfo.height = readInt(is, 2, false);
        } else {
            int c4 = is.read();
            if ((c1 == 'M' && c2 == 'M' && c3 == 0 && c4 == 42)
                    || (c1 == 'I' && c2 == 'I' && c3 == 42 && c4 == 0)) { //TIFF
                boolean bigEndian = c1 == 'M';
                int ifd = 0;
                int entries;
                ifd = readInt(is, 4, bigEndian);
                is.skip(ifd - 8);
                entries = readInt(is, 2, bigEndian);
                for (int i = 1; i <= entries; i++) {
                    int tag = readInt(is, 2, bigEndian);
                    int fieldType = readInt(is, 2, bigEndian);
                    long count = readInt(is, 4, bigEndian);
                    int valOffset;
                    if ((fieldType == 3 || fieldType == 8)) {
                        valOffset = readInt(is, 2, bigEndian);
                        is.skip(2);
                    } else {
                        valOffset = readInt(is, 4, bigEndian);
                    }
                    if (tag == 256) {
                        imageInfo.width = valOffset;
                    } else if (tag == 257) {
                        imageInfo.height = valOffset;
                    }
                    if (imageInfo.width != -1 && imageInfo.height != -1) {
                        break;
                    }
                }
            }

        }

        return imageInfo;
    }

    private static int readInt(InputStream is, int noOfBytes, boolean bigEndian) throws IOException {
        int ret = 0;
        int sv = bigEndian ? ((noOfBytes - 1) * 8) : 0;
        int cnt = bigEndian ? -8 : 8;
        for (int i = 0; i < noOfBytes; i++) {
            ret |= is.read() << sv;
            sv += cnt;
        }
        return ret;
    }

}