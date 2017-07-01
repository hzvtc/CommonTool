package com.as.commontool.util.account;

import android.accounts.Account;
import android.content.Context;

import com.orhanobut.logger.Logger;

/**
 * Created by FJQ on 2017/2/4.
 */
public class AccountDataManager {
    private static final String TAG="AccountManager";
    private static AccountDataManager mInstance;
    private Context context;

    private AccountDataManager(Context context) {
        this.context = context;
    }

    public static AccountDataManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AccountDataManager(context.getApplicationContext());
        }
        return mInstance;
    }
    /**
     * 获取用户账号信息
     * <p>
     * 所需权限：android.permission.GET_ACCOUNTS
     *
     * @param context Context
     */
    public  void getAccountInfo() {
        android.accounts.AccountManager accountManager = android.accounts.AccountManager.get(context);
        Account[] accounts = accountManager.getAccounts();
        for (Account account : accounts) {
            // 账号名字
            String accountName = account.name;
            // 账号类型
            String accountType = account.type;

            Logger.i("appName = " + accountName);
            Logger.i("packageName = " + accountType);
        }
    }
}
