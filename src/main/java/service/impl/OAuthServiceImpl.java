package service.impl;

import utils.OAuthUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.TimerTask;

public class OAuthServiceImpl extends TimerTask {
    @Override
    public void run() {
        try {
            OAuthUtil.refreshAuthorization();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
