package org.moviles;

import java.io.File;

public class Util {

    public static boolean deleteFileOnPath(File root,String file){
        File dir = new File(root,file);
        if (dir.isDirectory()){
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }

        return dir.delete();
    }
}
