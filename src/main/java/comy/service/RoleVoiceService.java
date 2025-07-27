package comy.service;
import comy.dao.RoleVoice;
import com.baomidou.mybatisplus.extension.service.IService;
/**
* @author myh
* @description 针对表【role_voice(角色语音配置表)】的数据库操作Service
* @createDate 2025-07-22 15:06:08
*/
public interface RoleVoiceService extends IService<RoleVoice> {
    String getVoiceByRoleCode(String roleCode);
}