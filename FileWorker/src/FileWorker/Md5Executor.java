package FileWorker;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static Utils.Constants.HASH_FILENAME;

public class Md5Executor implements IExecutable {
        private final String hashFile = HASH_FILENAME;

        @Override
        public void process(File file) {
            String hash = file.isDirectory()? getHashFromDirectory(file): getHashFromFile(file);
            WriteToHashFile(hash);
        }

        public static String getHashFromDirectory(File dir)
        {
            File[] files =  dir.listFiles();
            StringBuilder hashes = new StringBuilder();
            if (files == null) return "";
            for (File file: files){
                hashes.append(getHashFromFile(file));
            }
            return hashes.toString();
        }

        public static String getHashFromFile(File file){
            MessageDigest md = getMD5();
            try(FileInputStream input = new FileInputStream(file))
            {
                int i = 0;
                do {
                    byte[] buf = new byte[8*1024];
                    i = input.read(buf);
                    if(i > 0)
                        md.update(buf, 0, i);
                } while (i != -1);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return Md5Executor.getHex(md.digest());
        }

        private static String getHex(byte[] hash){
            StringBuilder builder = new StringBuilder();
            for (byte b: hash)
                builder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            return builder.toString();
        }
        private static MessageDigest getMD5 (){
            try {
                return MessageDigest.getInstance("MD5");
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        private void WriteToHashFile(String hash){
            try (FileOutputStream out = new FileOutputStream(hashFile)){
                byte[] bytes = hash.getBytes();
                out.write(bytes);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }


    }
