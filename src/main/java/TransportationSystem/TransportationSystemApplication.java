package TransportationSystem;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import common.CommonSection;
import user.Admin;

@SpringBootApplication
public class TransportationSystemApplication {

	public static void main(String[] args) {
		try{
            Date adminBirthdate = new SimpleDateFormat("dd/MM/yyyy").parse("7/6/2001");

            Admin systemAdmin = new Admin(
                "fciAdmin",
                "12345678910",
                null,
                "12345678",
                adminBirthdate
            );

            CommonSection.sysDatabase.setSystemAdmin(systemAdmin);

			SpringApplication.run(TransportationSystemApplication.class, args);
        }catch(Exception ex){}

	}

}
