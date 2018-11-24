package com.regus.pay.db.config;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = DbConfig.NAME, version = DbConfig.VERSION)
public class DbConfig {

    public static final String NAME = "PayAppDB";

    public static final int VERSION = 1;

}
