package joeuncamp.dabombackend.global.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class URI {
    public static String EMAIL_CERTIFICATION_URI;

    @Value("${cert.email}")
    public void setEmailCertificationUri(String uri){
        EMAIL_CERTIFICATION_URI = uri;
    }
}
