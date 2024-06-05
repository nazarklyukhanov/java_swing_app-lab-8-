package org.example.managers;

import org.example.recources.MusicBand;

import java.time.LocalDate;
import java.util.LinkedHashMap;

public class CollectionManager {
    private static LinkedHashMap<String, MusicBand> musicBandLinkedHashMap = new LinkedHashMap<>();
    private static LocalDate TimeOfCreate = LocalDate.now();
    public static void add(String key, MusicBand musicBand){
        musicBandLinkedHashMap.put(key, musicBand);
    }
    public static LinkedHashMap getLinkedHashMap(){
        return musicBandLinkedHashMap;
    }

    public static void setMusicBandLinkedHashMap(LinkedHashMap<String, MusicBand> musicBandLinkedHashMap) {
        CollectionManager.musicBandLinkedHashMap = musicBandLinkedHashMap;
    }

    public static LocalDate getTimeOfCreate() {
        return TimeOfCreate;
    }
    public static void remove(String key){
        // MusicBand.removeId(musicBandLinkedHashMap.get(key).getId());
        musicBandLinkedHashMap.remove(key);
    }
    public static void removeById(int id){
        for (String key: musicBandLinkedHashMap.keySet()){
            if (musicBandLinkedHashMap.get(key).getId() == id){
                // MusicBand.removeId(musicBandLinkedHashMap.get(key).getId());
                musicBandLinkedHashMap.remove(key);
                return;
            }
        }
        System.out.println("Элемент не был найден");
    }
}
