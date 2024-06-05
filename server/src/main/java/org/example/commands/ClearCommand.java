package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.recources.MusicBand;
import org.example.recources.Request;

import java.util.LinkedHashMap;

public class ClearCommand implements BaseCommand{

    @Override
    public String execute(Request request) {
        CollectionManager.setMusicBandLinkedHashMap(new LinkedHashMap<String, MusicBand>());
        return "Ваша коллекция очищена";
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
