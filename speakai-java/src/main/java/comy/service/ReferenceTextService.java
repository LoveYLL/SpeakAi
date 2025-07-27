package comy.service;
import comy.dao.ReferenceText;
import org.springframework.stereotype.Service;
@Service
public interface ReferenceTextService {
    ReferenceText getText();
}