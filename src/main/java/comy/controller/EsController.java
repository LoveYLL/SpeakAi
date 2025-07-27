package comy.controller;
import comy.dao.DiffWord;
import comy.result.Result;
import comy.service.EsSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/es")
public class EsController {
    @Autowired
    private EsSyncService esSyncService;
    @GetMapping("/search")
    public Result<List<DiffWord>> search( @RequestParam(required = false) String keyword,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "9") int size) throws IOException {
            List<DiffWord> list = esSyncService.searchWrongWords(keyword,page,size);
            return Result.success(list);
    }
}
