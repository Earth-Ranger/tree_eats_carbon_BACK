package smu.earthranger.jwt;

public class JwtProperties {

    //암호화 키
    public static final String SECRET_KEY = "webfirewood";

    //토큰 유효시간(30분 설정)
    public static final Long EXPIRATION_TIME = 30*60*1000L;
}

