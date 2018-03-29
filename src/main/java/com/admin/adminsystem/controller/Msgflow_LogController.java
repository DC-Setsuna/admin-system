package com.admin.adminsystem.controller;

import com.admin.adminsystem.entity.Msgflow_LogEntity;
import com.admin.adminsystem.jpa.Msgflow_LogJPA;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.*;

@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@RestController
public class Msgflow_LogController {

    @Autowired
    Msgflow_LogJPA msgflow;

    @RequestMapping(value = "/api/msgflow/selectmsg", method = RequestMethod.POST)
    public JSONObject selectByCondition (@RequestBody String info) {

        JSONObject list=new JSONObject();
        List<Msgflow_LogEntity> resultList;
        System.out.println(info);
        //String info = "{\"mindate\":\"Sat Mar 15 2018 00:00:00 GMT+0800 (CST)\",\"maxdate\":\"Sat Mar 31 2018 00:00:00 GMT+0800 (CST)\",\"sender_org\":\"ADXP\",\"sender\":\"\",\"receiver_org\":\"\",\"receiver\":\"\",\"type\":\"true\"}";

        Specification querySpecifi = new Specification<Msgflow_LogEntity>() {
            @Override
            public Predicate toPredicate(Root<Msgflow_LogEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(null != JSON.parseObject(info).get("mindate").toString() && !JSON.parseObject(info).get("mindate").toString().equals("")){

                    String min = JSON.parseObject(info).get("mindate").toString();
                    predicates.add(criteriaBuilder.greaterThan(root.get("LOG_TIMESTAMP"), Timestamp.valueOf(min)));
                }
                if(null != JSON.parseObject(info).get("maxdate").toString() && !JSON.parseObject(info).get("maxdate").toString().equals("")){

                    String max = JSON.parseObject(info).get("maxdate").toString();
                    predicates.add(criteriaBuilder.lessThan(root.get("LOG_TIMESTAMP"), Timestamp.valueOf(max)));
                }
                if(null != JSON.parseObject(info).get("sender_org").toString() && !JSON.parseObject(info).get("sender_org").toString().equals("")){
                    predicates.add(criteriaBuilder.equal(root.get("SENDER_ORG"), JSON.parseObject(info).get("sender_org").toString()));
                }
                if(null != JSON.parseObject(info).get("sender").toString() && !JSON.parseObject(info).get("sender").toString().equals("")){
                    predicates.add(criteriaBuilder.equal(root.get("SENDER"), JSON.parseObject(info).get("sender").toString()));
                }
                if(null != JSON.parseObject(info).get("receiver").toString() && !JSON.parseObject(info).get("receiver").toString().equals("")){
                    predicates.add(criteriaBuilder.equal(root.get("RECEIVER"), JSON.parseObject(info).get("receiver").toString()));
                }
                if(null != JSON.parseObject(info).get("receiver_org").toString() && !JSON.parseObject(info).get("receiver_org").toString().equals("")){
                    predicates.add(criteriaBuilder.equal(root.get("RECEIVER_ORG"), JSON.parseObject(info).get("receiver_org").toString()));
                }
                if(null != JSON.parseObject(info).get("type").toString() && !JSON.parseObject(info).get("type").toString().equals("false")) {
                    predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("MSG_TYPE"), "1"),criteriaBuilder.equal(root.get("MSG_TYPE"), "3")));
                }
                if(null != JSON.parseObject(info).get("type").toString() && !JSON.parseObject(info).get("type").toString().equals("true")) {
                    predicates.add(criteriaBuilder.equal(root.get("MSG_TYPE"), "1"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        try {
            resultList = msgflow.findAll(querySpecifi);
            Collections.sort(resultList, resultList.get(0));
        } catch(Exception e){
            list.put("code","400");
            list.put("message",e.toString());
            list.put("data","");//.getContent()
            return list;
        }
        for (int i = 0 ;i<resultList.size();i++){
            resultList.get(i).setLOG_TIMESTAMP(resultList.get(i).getLOG_TIMESTAMP().substring(0,19));
        }

        list.put("code","200");
        list.put("message","ok");
        list.put("data",resultList);//.getContent()

        return list;
    }

    @RequestMapping(value = "/api/msgflow/selectmsgbynumber", method = RequestMethod.POST)
    public JSONObject selectBySerialNumber (@RequestBody String info) {//

        //String info = "{\"serial_number\":\"8ae90800-435e-46ff-9939-56506be4e4ev\"}";
        JSONObject list=new JSONObject();
        List<Msgflow_LogEntity> resultList;
        String serial_number = JSON.parseObject(info).get("serial_number").toString();

        Specification querySpecifi = new Specification<Msgflow_LogEntity>() {
            @Override
            public Predicate toPredicate(Root<Msgflow_LogEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(null != serial_number && !serial_number.equals("")){
                    predicates.add(criteriaBuilder.equal(root.get("SERIAL_NUMBER"), serial_number));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        try{
            resultList = msgflow.findAll(querySpecifi);
        } catch(Exception e){
            list.put("code",400);
            list.put("message",e.toString());
            list.put("data","");
            return list;
        }
        list.put("code",200);
        list.put("message","ok");
        list.put("data",resultList);
        return list;
    }
}
