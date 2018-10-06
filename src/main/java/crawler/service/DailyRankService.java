package crawler.service;

import crawler.domain.Illustration;
import crawler.utils.GetIllustrationsUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class DailyRankService {

    public Illustration[] getDailyRankArray() throws IOException, NoSuchAlgorithmException {
        Illustration[] illustrations = GetIllustrationsUtil.getIllustrations();
        return illustrations;
    }
}
