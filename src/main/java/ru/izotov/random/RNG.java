package ru.izotov.random;

import cern.jet.random.engine.MersenneTwister;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RNG {
    private static final int DEFAULT_LOOP_LEN = 10;
    private static final MersenneTwister twister;
    private static final SecureRandom randomLoop;

    public RNG() {
    }

    public static int nextInt() {
        randomLoop();
        synchronized(twister) {
            return twister.nextInt();
        }
    }

    public static boolean nextBoolean() {
        randomLoop();
        return nextInt(2) == 1;
    }

    public static long nextLong() {
        randomLoop();
        synchronized(twister) {
            long rnd = twister.nextLong();
            return rnd;
        }
    }

    public static int nextInt(int intervalEnd) {
        if (intervalEnd < 2) {
            return 0;
        } else {
            randomLoop();
            synchronized(twister) {
                int rnd = (int)(twister.raw() * (double)intervalEnd);
                return rnd;
            }
        }
    }

    public static int nextInt(int intervalEnd, boolean rndLoop) {
        randomLoop();
        int rnd = nextInt(intervalEnd);
        return rnd;
    }

    private static void randomLoop() {
        int loopLen = randomLoop.nextInt(10);
        synchronized(twister) {
            for(int i = 0; i < loopLen; ++i) {
                twister.nextInt();
            }

        }
    }

    public static int nextInt(int intervalBegin, int intervalEnd) {
        if (intervalEnd <= intervalBegin) {
            return intervalBegin;
        } else {
            randomLoop();
            synchronized(twister) {
                int rnd = (int)(twister.raw() * (double)(intervalEnd - intervalBegin) + (double)intervalBegin);
                return rnd;
            }
        }
    }

    public static double rand() {
        randomLoop();
        synchronized(twister) {
            double rnd = twister.raw();
            return rnd;
        }
    }

    public static double rand(boolean rndLoop) {
        randomLoop();
        synchronized(twister) {
            double rnd = twister.raw();
            return rnd;
        }
    }

    public static int getRandom(List<Integer> probabilities) {
        if (probabilities != null && !probabilities.isEmpty()) {
            int probabilitySum = 0;

            Integer probability;
            for(Iterator i$ = probabilities.iterator(); i$.hasNext(); probabilitySum += probability) {
                probability = (Integer)i$.next();
            }

            int rnd = nextInt(probabilitySum);
            int start = 0;
            int i = 0;

            for(Iterator i$ = probabilities.iterator(); i$.hasNext(); ++i) {
                /*Integer*/ probability = (Integer)i$.next();
                if (rnd >= start && rnd < start + probability) {
                    return i;
                }

                start += probability;
            }

            return i < probabilities.size() ? i : -1;
        } else {
            throw new RuntimeException("empty probabilities list");
        }
    }

    static {
        long seed = System.nanoTime();
        int addSeed = 0;
        byte[] fileData = new byte[4];
        byte[] fileDataLoopSeed = new byte[20];
        boolean initComplete = false;
        DataInputStream dis = null;

        try {
            File file = new File("/dev/random");
            dis = new DataInputStream(new FileInputStream(file));
            dis.readFully(fileData);
            ByteBuffer byteBuffer = ByteBuffer.wrap(fileData);
            IntBuffer ib = ((ByteBuffer)byteBuffer.rewind()).asIntBuffer();
            long currenTime = System.currentTimeMillis();
            addSeed = ib.get(0);
            dis.readFully(fileDataLoopSeed);
            initComplete = true;
        } catch (Exception var22) {
            var22.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
            } catch (IOException var20) {
                var20.printStackTrace();
            }

        }

        SecureRandom randomLoop_;
        try {
            randomLoop_ = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException var21) {
            var21.printStackTrace();
            randomLoop_ = new SecureRandom();
        }

        randomLoop = randomLoop_;
        if (initComplete) {
            randomLoop.setSeed(fileDataLoopSeed);
        }

        twister = new MersenneTwister(new Date(seed + (long)addSeed));
    }
}
