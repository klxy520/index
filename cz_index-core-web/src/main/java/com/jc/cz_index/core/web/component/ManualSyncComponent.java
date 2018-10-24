package com.jc.cz_index.core.web.component;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.service.IFrontEndMachineService;
import com.jc.cz_index.core.web.webservice.FemServiceClient;
import com.jc.cz_index.model.Address;
import com.jc.cz_index.model.Card;
import com.jc.cz_index.model.Certificate;
import com.jc.cz_index.model.Contact;
import com.jc.cz_index.model.ContactWay;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Person;

@Component
public class ManualSyncComponent {
    @Autowired
    private FemServiceClient femServiceClient;
    @Autowired
    private IFrontEndMachineService frontEndMachineService;
    
    /**
     * 
     * 描述：后台手动同步组件
     * @param list
     * @param type
     * @author fengshengliang 2018年1月5日 下午4:53:22 
     * @version 1.0
     */
    public List<Long> manualSync(List<Map<String,Object>> list,String type){
        List<Long> results = new ArrayList<Long>();
        if(CollectionsUtils.isNull(list)){
            return results;
        }
        switch (type) {
        case "doSendPersons":
                for(Map<String,Object> mp : list){
                    try {
                        List<Person> persons = (List<Person>)mp.get("syncData");
                        Long logId = (Long) mp.get("logId");
                        FrontEndMachine fem = (FrontEndMachine) mp.get("frontEndMachine");
                        // 检测前置机是否可以达到
                        if (WebUtils.urlIsReach(fem.getFrontEndMachineaddress())){
                            int n = femServiceClient.doSendPersons(fem, persons);
                            if(n>0){
                                results.add(logId);
                            }
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } 
                }
            break;
        case "doSendCard":
            for(Map<String,Object> mp : list){
                try {
                    List<Card> cards = (List<Card>)mp.get("syncData");
                    Long logId = (Long) mp.get("logId");
                    FrontEndMachine fem = (FrontEndMachine) mp.get("frontEndMachine");
                    // 检测前置机是否可以达到
                    if (WebUtils.urlIsReach(fem.getFrontEndMachineaddress())){
                        int n = femServiceClient.doSendCard(fem, cards);
                        if(n>0){
                            results.add(logId);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } 
            }
            break;
        case "doSendContact":
            for(Map<String,Object> mp : list){
                try {
                    List<Contact> contacts = (List<Contact>)mp.get("syncData");
                    Long logId = (Long) mp.get("logId");
                    FrontEndMachine fem = (FrontEndMachine) mp.get("frontEndMachine");
                    // 检测前置机是否可以达到
                    if (WebUtils.urlIsReach(fem.getFrontEndMachineaddress())){
                        int n = femServiceClient.doSendContact((FrontEndMachine) mp.get("frontEndMachine"), contacts);
                        if (n > 0) {
                            results.add(logId);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } 
            }
            break;
        case "doSendContactWay":
            for(Map<String,Object> mp : list){
                try {
                    List<ContactWay> contactWays = (List<ContactWay>)mp.get("syncData");
                    Long logId = (Long) mp.get("logId");
                    FrontEndMachine fem = (FrontEndMachine) mp.get("frontEndMachine");
                    // 检测前置机是否可以达到
                    if (WebUtils.urlIsReach(fem.getFrontEndMachineaddress())){
                        int n = femServiceClient.doSendContactWay((FrontEndMachine) mp.get("frontEndMachine"), contactWays);
                        if(n>0){
                            results.add(logId);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } 
            }
            break;
        case "doSendAddress":
            for(Map<String,Object> mp : list){
                try {
                    List<Address> addresss = (List<Address>)mp.get("syncData");
                    Long logId = (Long) mp.get("logId");
                    FrontEndMachine fem = (FrontEndMachine) mp.get("frontEndMachine");
                    // 检测前置机是否可以达到
                    if (WebUtils.urlIsReach(fem.getFrontEndMachineaddress())){
                        int n = femServiceClient.doSendAddress((FrontEndMachine) mp.get("frontEndMachine"), addresss);
                        if(n>0){
                            results.add(logId);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } 
            }
            break;
        case "doSendCertificate":
            for(Map<String,Object> mp : list){
                try {
                    List<Certificate> certificates = (List<Certificate>)mp.get("syncData");
                    Long logId = (Long) mp.get("logId");
                    FrontEndMachine fem = (FrontEndMachine) mp.get("frontEndMachine");
                    // 检测前置机是否可以达到
                    if (WebUtils.urlIsReach(fem.getFrontEndMachineaddress())){
                        int n = femServiceClient.doSendCertificate((FrontEndMachine) mp.get("frontEndMachine"), certificates);
                        if(n>0){
                            results.add(logId);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } 
            }
            break;
        default:
            break;
        }
        return results;
    }
}
