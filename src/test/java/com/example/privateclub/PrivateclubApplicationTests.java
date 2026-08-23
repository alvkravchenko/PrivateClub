package com.example.privateclub;

import com.example.privateclub.controller.ParticipantController;
import com.example.privateclub.controller.QrCodeController;
import com.example.privateclub.repository.ParticipantRepository;
import com.example.privateclub.repository.QrCodeRepository;
import com.example.privateclub.service.ParticipantService;
import com.example.privateclub.service.QrCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PrivateclubApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();

        assertThat(applicationContext.getBean(ParticipantController.class)).isNotNull();
        assertThat(applicationContext.getBean(QrCodeController.class)).isNotNull();

        assertThat(applicationContext.getBean(QrCodeService.class)).isNotNull();
        assertThat(applicationContext.getBean(ParticipantService.class)).isNotNull();

        assertThat(applicationContext.getBean(QrCodeRepository.class)).isNotNull();
        assertThat(applicationContext.getBean(ParticipantRepository.class)).isNotNull();
    }
}
