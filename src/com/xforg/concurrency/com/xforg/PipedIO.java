package com.xforg.concurrency.com.xforg;//: concurrency/PipedIO.java
// Using pipes for inter-task I/O
import java.util.concurrent.*;
import java.io.*;
import java.util.*;

import static com.xforg.utils.Print.print;
import static com.xforg.utils.Print.printnb;

class Sender implements Runnable {
  private Random rand = new Random(47);
  private PipedWriter out = new PipedWriter();
  public PipedWriter getPipedWriter() { return out; }
  public void run() {
    try {
      while(true)
        for(char c = 'A'; c <= 'z'; c++) {
          out.write(c);
          TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
        }
    } catch(IOException e) {
      print(e + " Sender write exception");
    } catch(InterruptedException e) {
      print(e + " Sender sleep interrupted");
    }
  }
}

class Receiver implements Runnable {
  private PipedReader in;
  public Receiver(Sender sender) throws IOException {
    in = new PipedReader(sender.getPipedWriter());
  }
  public void run() {
    try {
      while(true) {
        // Blocks until characters are there:
        printnb("Read: " + (char)in.read() + ", ");/*当它调用read（）时，如果没有更多的数据，管道将自动阻塞*/
      }
    } catch(IOException e) {
      print(e + " Receiver read exception");
    }
  }
}

public class PipedIO {
  public static void main(String[] args) throws Exception {
    Sender sender = new Sender();
    Receiver receiver = new Receiver(sender);
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(sender);
    exec.execute(receiver);
    TimeUnit.SECONDS.sleep(4);
    exec.shutdownNow();
  }
} /* Output: (65% match)
Read: A, Read: B, Read: C, Read: D, Read: E, Read: F, Read: G, Read: H, Read: I, Read: J, Read: K, Read: L, Read: M, java.lang.InterruptedException: sleep interrupted Sender sleep interrupted
java.io.InterruptedIOException Receiver read exception
*///:~
