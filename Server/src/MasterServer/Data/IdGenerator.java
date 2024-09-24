package MasterServer.Data;

import java.util.Random;

/**
 * Класс для генерации рандомного id
 */
public class IdGenerator {
    /**
     * Экземпляр класса Random
     */
    private static Random randomId = new Random();

    /**
     * Генерирует рандмоное число типа int в заданном диапазоне
     * @param range диапазон int
     * @return рандомное int число из диапазона
     */
    public static int generateId(int range){
        return  randomId.nextInt(range);
    }
}
