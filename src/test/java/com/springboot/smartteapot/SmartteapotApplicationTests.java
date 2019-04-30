package com.springboot.smartteapot;

import com.springboot.smartteapot.hardware.openapi.GizwitsOpenApi;
import com.springboot.smartteapot.hardware.properties.GizwitsOpenApiProperties;
import com.springboot.smartteapot.hardware.websocket.GizwitsWebsocket;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration

public class SmartteapotApplicationTests {

//    @Autowired
//    private WebApplicationContext context;

    @Autowired
    private GizwitsOpenApi gizwitsOpenApi;

    @Autowired
    private GizwitsOpenApiProperties gizwitsOpenApiProperties;

    @Autowired
    private GizwitsWebsocket gizwitsWebsocket;

//    private MockMvc mockMvc;
//
//    @Before
//    public void setup(){
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }

    //@Ignore
    @Test
    public void contextLoads() {
    }

//    @Test
//    public void whenUploadSuccess() throws Exception {
//        mockMvc.perform(fileUpload("/file")
//                .file(new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello upload".getBytes("UTF-8"))))
//                .andExpect(status().isOk());
//    }

    @Test
    public void jsontest() throws JSONException {

//        JSONObject attrs = new JSONObject();
//        JSONObject body = new JSONObject();
//
//        body.put("Heatint_Switch","true");
//        attrs.put("attrs",body);

//
//        System.out.println(attrs.toString());

//        String jsondata = "{\"meta\": {\"sort\": \"desc\", \"limit\": 20, \"end_time\": 1555171200, \"did\": \"Ms2583DSKaBPaN5MQZkpkR\", \"skip\": 0, \"start_time\": 1555084800, \"total\": 3, \"type\": \"online\"}, \"objects\": [{\"ip\": \"113.105.128.67\", \"m2m_id\": \"a90432235cf549ca84b914bef811eec8\", \"payload\": {\"duration\": 11871, \"heartbeat\": {\"count\": 235, \"max\": 51, \"avg\": 50, \"last\": 130, \"min\": 49}, \"reason\": \"no_heartbeat\"}, \"timestamp\": 1555167994.098, \"type\": \"dev_offline\"}, {\"ip\": \"113.105.128.67\", \"m2m_id\": \"a90432235cf549ca84b914bef811eec8\", \"payload\": {\"keep_alive\": 130}, \"timestamp\": 1555156123.774, \"type\": \"dev_online\"}, {\"ip\": \"113.105.128.67\", \"m2m_id\": \"a90432235cf549ca84b914bef811eec8\", \"payload\": {\"duration\": 40208, \"heartbeat\": {\"count\": 801, \"max\": 50, \"avg\": 50, \"last\": 167, \"min\": 50}, \"reason\": \"no_heartbeat\"}, \"timestamp\": 1555097071.625, \"type\": \"dev_offline\"}]}";
//
//        JSONObject jsonObject = new JSONObject(jsondata);
//        System.out.println(jsonObject);
//        Gson gson = new Gson();

        //System.out.println(gizwitsOpenApi.getDeviceOnlineStatus(gizwitsOpenApiProperties.getProductId()));
        //gson.fromJson(jsondata);

        //System.out.println(gizwitsOpenApi.executeRequest(false,TasteEnum.THICK.getValue(),ConstantTimeEnum.MIN3.getValue(),(long)90));

        System.out.println(gizwitsOpenApi.getLatestStatus().toString());
    }

}
