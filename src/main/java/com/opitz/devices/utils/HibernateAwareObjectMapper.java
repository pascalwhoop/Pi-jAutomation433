package com.opitz.devices.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * User: Pascal
 * Date: 05.06.13
 * Time: 17:35
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper(){
        registerModule(new Hibernate4Module());
    }


}
