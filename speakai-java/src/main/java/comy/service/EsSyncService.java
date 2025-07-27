package comy.service;
import comy.dao.DiffWord;

import java.io.IOException;
import java.util.List;
public interface EsSyncService {
    void syncAllWordsToES() throws IOException;
    List<DiffWord> searchWrongWords(String keyword, int page, int size) throws IOException;
}
