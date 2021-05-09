package classLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassloader extends ClassLoader{

    public static void main(String[] args) {
        try {
           Object hello = new MyClassloader().findClass("src/classLoader/Hello.xlass").newInstance();
           Class<?> clazz = hello.getClass();
            Method method = clazz.getMethod("hello");
            method.invoke(hello);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(name);
        byte[] bytes = getByteArr(file);
        return defineClass("Hello",bytes,0,bytes.length);
    }


    public byte[] getByteArr(File file){
        long fileSize = file.length();
        FileInputStream fi =null;

        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        try{
            fi = new FileInputStream(file);
            while (offset < buffer.length
                    && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
        }catch (Exception e){
            System.out.println(e);
        }finally {
            try {
                fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<buffer.length;i++){
            buffer[i] =(byte) ~buffer[i];
        }
      return buffer;
    }


}
