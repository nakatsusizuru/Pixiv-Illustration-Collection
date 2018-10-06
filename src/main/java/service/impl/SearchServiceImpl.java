package service.impl;

import service.SearchService;
import utils.SearchUtil;

public class SearchServiceImpl implements SearchService {

    @Override
    public String orderByPop(String keyword, int page)  throws Exception {
        return SearchUtil.orderByPop(keyword,page);
    }

    @Override
    public String orderByDate(String keyword, int page)  throws Exception {
        return null;
    }
}
