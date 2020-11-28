package com.wolf.common.test.vipcard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * hong
 */
@RestController
@RequestMapping("/vipcard")
public class VipCardController {

   @Autowired
    private VipCardService importService;

    @RequestMapping(value = "/importCustomer", method = RequestMethod.POST)
    public void validateSmsVerifyCode() {
        try {
            importService.importVipData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
