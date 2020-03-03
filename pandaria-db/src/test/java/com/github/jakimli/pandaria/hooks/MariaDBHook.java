package com.github.jakimli.pandaria.hooks;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.MariaDB4jService;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.commons.lang3.SystemUtils;

public class MariaDBHook {

    private MariaDB4jService foo;
    private MariaDB4jService bar;

    @Before("@multi_db")
    public void start() throws ManagedProcessException {
        foo = foo();
        bar = bar();

        foo.start();
        bar.start();

        foo.getDB().createDB("pandaria");
        bar.getDB().createDB("pandaria");
    }

    @After("@multi_db")
    public void stop() throws ManagedProcessException {
        foo.stop();
        bar.stop();
    }

    private MariaDB4jService foo() throws ManagedProcessException {
        return mariaDb(3317, "foo");
    }

    private MariaDB4jService bar() throws ManagedProcessException {
        return mariaDb(3318, "bar");
    }

    private MariaDB4jService mariaDb(int port, String name) throws ManagedProcessException {
        MariaDB4jService db = new MariaDB4jService();
        db.getConfiguration().setPort(port);
        db.getConfiguration().setDataDir(String.format("%s/%s/data", SystemUtils.JAVA_IO_TMPDIR, name));
        db.getConfiguration().setBaseDir(String.format("%s/%s/base", SystemUtils.JAVA_IO_TMPDIR, name));
        db.getConfiguration().addArg("--user=root");
        return db;
    }
}
