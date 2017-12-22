package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;
import java.util.HashMap;


@Controller
public class SearchController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {

        Map<String, Object> map = new HashMap<>();
        String clave;

        if (q.contains("max:")) {
            String[] q2 = q.split("max:");
            String maximo = q2[1];
            clave = q2[0];
            map.put("CamelTwitterCount", maximo);
        } else {
            clave = q;
        }

        map.put("CamelTwitterKeywords", clave);

        return producerTemplate.requestBodyAndHeader("direct:search", "", "CamelTwitterKeywords", q);
    }
}