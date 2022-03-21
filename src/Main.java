import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static int[] intArray(int count) {
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = (int) ((Math.random() * 1000) + 1);
        }
        return result;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Map<Integer, Integer> synchronizedHashMap = Collections.synchronizedMap(new HashMap<>());
        int intArrayCount = 9_999_999;
        int[] intArray = intArray(intArrayCount);


        Thread writeThread = new Thread(() -> {
            for (int j = 0; j < intArray.length; j++) {
                concurrentHashMap.putIfAbsent(j, intArray[j]);
            }
            System.out.println("Thread Write: " + Thread.currentThread() + ", end");
        });

        Thread writeThread2 = new Thread(() -> {
            for (int j = 0; j < intArray.length; j++) {
                concurrentHashMap.putIfAbsent(j, intArray[j]);
            }
            System.out.println("Thread Write: " + Thread.currentThread() + ", end");
        });

        Thread readThread = new Thread(() -> {
            for (int j = 0; j < intArrayCount; j++) {
                while (true) {
                    if (concurrentHashMap.get(j) != null) {
                        int d = concurrentHashMap.get(j);
                        break;
                    }
                }
            }
            System.out.println("Thread Read: " + Thread.currentThread() + ", end");
        });

        Thread readThread2 = new Thread(() -> {
            for (int j = 0; j < intArrayCount; j++) {
                while (true) {
                    if (concurrentHashMap.get(j) != null) {
                        int d = concurrentHashMap.get(j);
                        break;
                    }
                }
            }
            System.out.println("Thread Read: " + Thread.currentThread() + ", end");
        });

        MyTimer.start();
        writeThread.start();
        writeThread2.start();
        readThread.start();
        readThread2.start();
        try {
            writeThread.join();
            writeThread2.join();
            readThread.join();
            readThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MyTimer.end();

        Thread writeThread3 = new Thread(() -> {
            for (int j = 0; j < intArray.length; j++) {
                synchronizedHashMap.putIfAbsent(j, intArray[j]);
            }
            System.out.println("Thread Write: " + Thread.currentThread() + ", end");
        });

        Thread writeThread4 = new Thread(() -> {
            for (int j = 0; j < intArray.length; j++) {
                synchronizedHashMap.putIfAbsent(j, intArray[j]);
            }
            System.out.println("Thread Write: " + Thread.currentThread() + ", end");
        });

        Thread readThread3 = new Thread(() -> {
            for (int j = 0; j < intArrayCount; j++) {
                while (true) {
                    if (synchronizedHashMap.get(j) != null) {
                        int d = synchronizedHashMap.get(j);
                        break;
                    }
                }
            }
            System.out.println("Thread Read: " + Thread.currentThread() + ", end");
        });

        Thread readThread4 = new Thread(() -> {
            for (int j = 0; j < intArrayCount; j++) {
                while (true) {
                    if (synchronizedHashMap.get(j) != null) {
                        int d = synchronizedHashMap.get(j);
                        break;
                    }
                }
            }
            System.out.println("Thread Read: " + Thread.currentThread() + ", end");
        });

        MyTimer.start();
        writeThread3.start();
        writeThread4.start();
        readThread3.start();
        readThread4.start();
        try {
            writeThread3.join();
            writeThread4.join();
            readThread3.join();
            readThread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MyTimer.end();
    }
}
