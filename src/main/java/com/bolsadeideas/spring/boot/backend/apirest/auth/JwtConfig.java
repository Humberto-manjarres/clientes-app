package com.bolsadeideas.spring.boot.backend.apirest.auth;


public class JwtConfig {
	public static final String LLAVE_SECRETA = "alguna.clave.secreta.12345678";
	
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEpAIBAAKCAQEAx5jdTaeEDid87ItI1ztle2nWa1RW08JzCqgJ1aTFKDxcRqGt\r\n"
			+ "GgMODLB5R2EWKfScdTsKhRPkUbkxoEs+K2u1kcdk3MOxvwZNrqHwwVPbgOmOfYzS\r\n"
			+ "/6kZlqOTgb3vO0wiwSydAUBVNrIesIn5GkmZoW0RFZEEnXdu7X5e5PqjerEcUf2e\r\n"
			+ "mxb0OvJeD2t2jdnDcCSpAiBSysflCSxzexhw4sBGOxtTReFMcjlqfLCnTS4L8HDB\r\n"
			+ "zgyAv4VcNCwYgbci8x038Gh0UYV3LPpCHz3tFq5JW36QNPmQi+oLbc6NfIRpBliN\r\n"
			+ "iU4EJ2OhhOngH51CnxkSflskjHwGQSbRKIGdQQIDAQABAoIBAQCbeRZBC9BQWi/z\r\n"
			+ "7VbS2KBm7n0n420yy89iqJvof81XVxUlq4Azt9d0yO3fTN+/1Jsesduy51i80721\r\n"
			+ "N4b4uZCYOjH1yLWmr5lJAzmOzew4UTU91LjyjlyxOz+GG1XC4b5crIyr0dnOnhZL\r\n"
			+ "u11/PX9e/58EJo7qvYJ5mir+v8Ehqny7+IaKfY09XiSxhtm7jo359estd7qB+DJA\r\n"
			+ "6KaN9Xfv7orWZAIrh4CsHFHNoITDE2wBZ4wtHmoUJCUVoIregNqBaV6bm5LR/vh4\r\n"
			+ "gQa8EyoYbs3bkOs7FkwNVGtFArrfC4TrQN6EJi/YJ5qTSK5xctHmxohaCOpz4sem\r\n"
			+ "10NWB5klAoGBAPSmUxLykk0H2onKbWKlWGdZvelxW034P4sMobcsuZdaqunI3PGA\r\n"
			+ "RVd41iT0BoOZ1fuUHQq2a0od5rffWMi0N4TkLJWXHF9A9atsd6fURHYlGrPAq3/2\r\n"
			+ "DKQzj5h1t2PSO2hoRSscn86d8/KZSZisyJMPRRTwVyR0fJ1kUosy8lgvAoGBANDb\r\n"
			+ "dLG0lVLZ6cSdb4QaIVa/ypy0rQxbKsT3atRDhLT3fA4dlvxFFji0s0NRTLodvzPi\r\n"
			+ "5B+JePf+atFlOHzV1ujprPrDwqE4XsJKrh/3ctoNjz15F1Ieu59DwVWj9RU0ufxd\r\n"
			+ "t4t14Q3aZdXpNSogXcSw8EXyBjRVc+kXunOEYZWPAoGAXoA8yK3ga1bkSKNju14J\r\n"
			+ "FuroW9sInMaOrQR1Fw90pQ1FxcZr6fFVV6N6fI2yUnVC3cnpqfCq4tdH/rCMWAKd\r\n"
			+ "C30j56iWMgkinFPqqG5q/4P3m4WJ+YSuf2LXTdnyu+FCR2M3jJ6i8Cm9SHGDpzDe\r\n"
			+ "zUdxtNPdpVxFonpVFCXazMsCgYEAvzYrwAX0L8YRn1ADqI3DuAgfFxjS1V0h6vIZ\r\n"
			+ "e/vUo1lw2ft9H7igf4nwFNF5zWsugfJXqAfUGDd01AA3lxKvjs1g8LQInFFfb8Gv\r\n"
			+ "aNTC8Z84ZK9YCnbYjxcZCa7x0lhTbhje0W86e9RXJ2A6rDa1oj3bvC24QHF7jQuM\r\n"
			+ "R/YK4UECgYB+DBolB427YeL8uFNNSs0MXx6pjVk8lK6e+JLJbiWx6lX/Qjs5BMRd\r\n"
			+ "D7rmv8mw6hqOHgNvRWHQj8wlM1WOb4F1us9mrs3Pam4zC8x2c61Mhd1lMlu+D9Ys\r\n"
			+ "XUFI5sNAt+bcnRI1nrE52bPWohah0q6OGS729ZVppyOtAVrP05MlyQ==\r\n"
			+ "-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx5jdTaeEDid87ItI1ztl\r\n"
			+ "e2nWa1RW08JzCqgJ1aTFKDxcRqGtGgMODLB5R2EWKfScdTsKhRPkUbkxoEs+K2u1\r\n"
			+ "kcdk3MOxvwZNrqHwwVPbgOmOfYzS/6kZlqOTgb3vO0wiwSydAUBVNrIesIn5GkmZ\r\n"
			+ "oW0RFZEEnXdu7X5e5PqjerEcUf2emxb0OvJeD2t2jdnDcCSpAiBSysflCSxzexhw\r\n"
			+ "4sBGOxtTReFMcjlqfLCnTS4L8HDBzgyAv4VcNCwYgbci8x038Gh0UYV3LPpCHz3t\r\n"
			+ "Fq5JW36QNPmQi+oLbc6NfIRpBliNiU4EJ2OhhOngH51CnxkSflskjHwGQSbRKIGd\r\n"
			+ "QQIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";
}
