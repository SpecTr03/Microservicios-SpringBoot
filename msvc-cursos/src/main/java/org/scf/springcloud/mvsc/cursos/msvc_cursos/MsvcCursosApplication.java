package org.scf.springcloud.mvsc.cursos.msvc_cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//Habilitar el uso de Feign para la comunicación entre microservicios
@EnableFeignClients
@SpringBootApplication
public class MsvcCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcCursosApplication.class, args);
	}

}
