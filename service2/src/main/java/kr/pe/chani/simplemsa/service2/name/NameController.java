package kr.pe.chani.simplemsa.service2.name;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/name")
public class NameController {

    @Autowired
    NameService nameService;

    //서비스2에서는 고객의 id를  받으며 해당하는 이름을 반환하는 기능 구현##
    @GetMapping(value = "/{id}")
    public String name(@PathVariable("id") String id) {
        return nameService.getName(id);
    }
}
