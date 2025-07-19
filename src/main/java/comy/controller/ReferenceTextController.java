package comy.controller;

import comy.dao.ReferenceText;
import comy.result.Result;
import comy.service.ReferenceTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reference")
    public class ReferenceTextController {

        @Autowired
        private ReferenceTextService referenceTextService;

        @GetMapping("/random")
        public Result<ReferenceText> getRandomReferenceText() {
            ReferenceText text = referenceTextService.getText();
            return Result.success(text);
        }
    }