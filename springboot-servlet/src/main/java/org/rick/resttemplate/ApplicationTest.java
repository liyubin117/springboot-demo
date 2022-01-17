package org.rick.resttemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rick.mysqlcrud.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Config config;


    @Test
    public void testREST() {
        String url = config.getUrl();
        UserEntity[] json = restTemplate.getForObject(url, UserEntity[].class);
        for (UserEntity ue : json) {
            System.out.println(ue);
        }

        String postUrl = config.getPostUrl();
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("name","lyb");
        paramMap.add("age",29);
        paramMap.add("address","hangzhou");
        restTemplate.postForObject(postUrl, paramMap,  UserEntity.class);

        restTemplate.delete("http://localhost:8123/user/delete?id="+12);
    }
}
