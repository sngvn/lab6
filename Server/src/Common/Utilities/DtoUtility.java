package Common.Utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class DtoUtility {

    public static <T> T tryGetObject(Class<T> type, byte[] bytes) {
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);

            Object object = objectStream.readObject();

            if (type.isInstance(object)) {
                return type.cast(object);
            } else {
                return null;
            }
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public static byte[] getBytes(Object object) {
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
