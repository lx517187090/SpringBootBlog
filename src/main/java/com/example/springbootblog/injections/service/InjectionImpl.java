package com.example.springbootblog.injections.service;

import com.example.springbootblog.injections.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 注解注入
 *
 * @author 周亮
 *         Created by zhoul on 2017/5/22.
 */
@Component
public class InjectionImpl extends AbstractInjection implements InitializingBean {

    @Resource
    private IMultiLangInjectService multiLangServiceImpl;

    @Resource
    private IDictInjectService dictServiceImpl;

    @Resource
    private ICorpInjectService corpServiceImpl;

    @Resource
    private IRegionInjectService regionServiceImpl;

    @Resource
    private ICurrencyInjectService btCurrencyServiceImpl;

    @Resource
    private IBankTypeInjectService btBankTypeServiceImpl;

    @Resource
    private IUserInjectService userServiceImpl;

    @Resource
    private IMenuInjectService menuServiceImpl;

    @Resource
    private IInputBankInfoInjectService inputBankInfoInjectService;

    @Override
    public void afterPropertiesSet() throws Exception {
        /*putMultiInjectService(Corps.class, corpServiceImpl);
        putMultiInjectService(Users.class, userServiceImpl);
        putMultiInjectService(Dicts.class, dictServiceImpl);
        putMultiInjectService(InputBankInfos.class,inputBankInfoInjectService);


        putSingleInjectService(Corp.class, corpServiceImpl);
        putSingleInjectService(Region.class, regionServiceImpl);
        putSingleInjectService(Dict.class, dictServiceImpl);
        putSingleInjectService(Lang.class, multiLangServiceImpl);
        putSingleInjectService(Currency.class, btCurrencyServiceImpl);
        putSingleInjectService(BankType.class, btBankTypeServiceImpl);
        putSingleInjectService(User.class, userServiceImpl);
        putSingleInjectService(Menu.class, menuServiceImpl);
        putSingleInjectService(InputBankInfo.class,inputBankInfoInjectService);*/
    }

}
