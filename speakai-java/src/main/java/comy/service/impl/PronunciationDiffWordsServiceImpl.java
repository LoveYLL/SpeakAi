package comy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import comy.dao.PronunciationDiffWords;
import comy.service.PronunciationDiffWordsService;
import comy.mapper.PronunciationDiffWordsMapper;
import org.springframework.stereotype.Service;

/**
* @author myh
* @description 针对表【pronunciation_diff_words(错词表)】的数据库操作Service实现
* @createDate 2025-07-23 00:02:20
*/
@Service
public class PronunciationDiffWordsServiceImpl extends ServiceImpl<PronunciationDiffWordsMapper, PronunciationDiffWords>
    implements PronunciationDiffWordsService{
}




