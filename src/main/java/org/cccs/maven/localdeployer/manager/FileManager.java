package org.cccs.maven.localdeployer.manager;

import java.io.*;

/**
 * User: BoyCook
 * Date: Nov 7, 2008
 * Time: 3:43:10 PM
 */
public class FileManager {

    public boolean delete(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = delete(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public boolean delete(File dir, String ext) {
        ExtensionFilter filter = new ExtensionFilter(ext);
        File file;
        boolean success = false;

        if (dir.isDirectory()) {
            String[] children = dir.list(filter);
            for (int i = 0; i < children.length; i++) {
                file = new File(dir, children[i]);
                success = file.delete();
            }
        }

        return success;
    }

    public boolean copy(File source, File dest) {
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }

            InputStream in = null;
            OutputStream out = null;

            try {
                in = new FileInputStream(source);
                out = new FileOutputStream(dest);

                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (Exception e) {
            System.out.println("File copy error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean copyDirectory(File srcPath, File dstPath, String ext) {

        try {
            ExtensionFilter filter = new ExtensionFilter(ext);
            if (srcPath.isDirectory()) {

                if (!dstPath.exists()) {
                    dstPath.mkdir();
                }

                String files[] = srcPath.list(filter);

                for (int i = 0; i < files.length; i++) {
                    InputStream in = new FileInputStream(new File(srcPath, files[i]));
                    OutputStream out = new FileOutputStream(new File(dstPath, files[i]));

                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];

                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    in.close();
                    out.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Directory copy error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean copyDirectory(File srcPath, File dstPath) {

        try {
        if (srcPath.isDirectory()) {

            if (!dstPath.exists()) {
                dstPath.mkdir();
            }

            String files[] = srcPath.list();

            for (int i = 0; i < files.length; i++) {
                copyDirectory(new File(srcPath, files[i]),
                        new File(dstPath, files[i]));
            }

        } else {

            if (!srcPath.exists()) {
                System.out.println("File or directory does not exist.");
                System.exit(0);

            } else {
                InputStream in = new FileInputStream(srcPath);
                OutputStream out = new FileOutputStream(dstPath);
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];

                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();
            }
        }
        } catch (Exception e) {
            System.out.println("Directory copy error: " + e.getMessage());
            return false;
        }
        return true;
    }
}

class ExtensionFilter implements FilenameFilter {
    private String extension;
    public ExtensionFilter( String extension ) {
        this.extension = extension;
    }

    public boolean accept(File dir, String name) {
        return (name.endsWith(extension));
    }
}