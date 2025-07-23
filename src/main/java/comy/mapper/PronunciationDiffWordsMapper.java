package comy.mapper;

import comy.dao.PronunciationDiffWords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author myh
* @description 针对表【pronunciation_diff_words(错词表)】的数据库操作Mapper
* @createDate 2025-07-23 00:02:21
* @Entity comy.dao.PronunciationDiffWords
*/
@Mapper
public interface PronunciationDiffWordsMapper extends BaseMapper<PronunciationDiffWords> {

}




