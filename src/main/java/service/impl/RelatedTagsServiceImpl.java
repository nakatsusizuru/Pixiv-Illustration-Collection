package service.impl;

import service.RelatedTagsService;
import utils.RelatedTagsUtil;

import java.io.IOException;

public class RelatedTagsServiceImpl implements RelatedTagsService {
    @Override
    public String getRelatedTagsString(String keyword) throws IOException {
        return RelatedTagsUtil.getRelatedTags(keyword);
    }
}
