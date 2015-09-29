package net.porillo.casino.storage;

import net.porillo.casino.Casino;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializer {

    public static File dir = new File("plugins" + File.separator + "Casino" + File.separator
            + "casinos");

    public static void save(List<Casino> casinos) {
        if (!dir.exists()) {
            dir.mkdir();
        }
        for (Casino c : casinos) {
            File f = new File(dir, c.getName() + ".casino");
            try {
                if (f.exists()) {
                    f.delete();
                    f.createNewFile();
                }
                f.createNewFile();
                FileOutputStream fileOut = new FileOutputStream(f);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(c);
                out.close();
                fileOut.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    public static void delete(Casino c) {
        for (File f : dir.listFiles()) {
            if (f.getName().startsWith(c.getName())) {
                f.delete();
            }
        }
    }

    public static void _deleteAll() {
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith(".casino")) {
                f.delete();
            }
        }
    }

    public static List<Casino> load() {
        List<Casino> local = new ArrayList<Casino>();
        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
            for (File f : dir.listFiles()) {
                if (f.getName().endsWith(".casino")) {
                    Casino c = null;
                    FileInputStream fileIn = new FileInputStream(f);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    c = (Casino) in.readObject();
                    in.close();
                    fileIn.close();
                    local.add(c);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return local;
    }

    public static void setWorkingDir(File dir) {
        Serializer.dir = dir;
    }
}
