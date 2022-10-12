package com.revature.Util;

import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.server.session.DatabaseAdaptor;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.JDBCSessionDataStoreFactory;
import org.eclipse.jetty.server.session.SessionCache;
import java.util.function.Supplier;

import db.Database;

public class SessionUtil {

    public static Supplier<SessionHandler> supplier = () -> sqlSessionHandler("org.postgresql.ds.PGSimpleDataSource", "postgresql://localhost:5432/project1");

    public static SessionHandler sqlSessionHandler(String driver, String url) {
        SessionHandler sessionHandler = new SessionHandler();
        SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
        sessionCache.setSessionDataStore(
            jdbcDataStoreFactory(driver, url).getSessionDataStore(sessionHandler)
        );
        sessionHandler.setSessionCache(sessionCache);
        sessionHandler.setHttpOnly(true);
        sessionHandler.setSameSite(HttpCookie.SameSite.STRICT);
        sessionHandler.setSecureRequestOnly(true);
        sessionHandler.setMaxInactiveInterval(1800);
        return sessionHandler;
    }

    private static JDBCSessionDataStoreFactory jdbcDataStoreFactory(String driver, String url) {
        DatabaseAdaptor databaseAdaptor = new DatabaseAdaptor();
        databaseAdaptor.setDriverInfo(driver, url);
        databaseAdaptor.setDatasource(Database.getDataSource());
        JDBCSessionDataStoreFactory jdbcSessionDataStoreFactory = new JDBCSessionDataStoreFactory();
        jdbcSessionDataStoreFactory.setDatabaseAdaptor(databaseAdaptor);
        return jdbcSessionDataStoreFactory;
    }
}
