package web.listener;

import service.impl.OAuthServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import java.util.Timer;

@WebListener()
public class InitializationListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    public void contextInitialized(ServletContextEvent sce) {

        Timer timer = new Timer();
        long delay = 1000 * 60 * 30;
        long interval = 1000 * 60 * 30;
        timer.schedule(new OAuthServiceImpl(), delay, interval);
    }

}
