package com.palomino.confecontrol.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class ConstantesSeguridad {
    public static final long JWT_EXPIRATION_TOKEN = 1000*60*60 * 12; //12 horas
    //public static final SecretKey JWT_FIRMA = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public static final String JWT_FIRMA = "ASFFGADFSDFGSDFSDDFADADFFSDFASXDFSDCSDFSDFSDFSDFSDFASDDAXFSDFSDFSXFSDFSDFSDFSDFSDFDFASDFSDFSDFSD";

}
