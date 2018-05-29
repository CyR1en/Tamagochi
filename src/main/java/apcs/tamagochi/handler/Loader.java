package apcs.tamagochi.handler;

import apcs.tamagochi.entity.Pet;
import com.cyr1en.cgdl.util.SerializationUtil;

import java.io.File;
import java.io.IOException;

public class Loader {

    public Loader() {
    }

    public boolean isSaveExist() {
        File f = new File("saves/save.dat");
        return f.exists();
    }

    public Pet loadData() {
        Pet object = new Pet(1);
        try {
            object = (Pet) SerializationUtil.deserialize("saves/save.dat");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return object;
    }
}
