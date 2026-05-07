import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Main {

    // Segredo compartilhado para HMAC (apenas exemplo didático)
    private static final String SECRET = "MINHA_CHAVE_SECRETA_123";

    public static void main(String[] args) throws Exception {
        // 1) Gera o token
        String token = gerarToken();
        System.out.println("Token: " + token);

        // 2) Decodifica o payload (só para visualizar o JSON)
        System.out.println("\nDecodificando payload:");
        decodificarPayload(token);

        // 3) Valida o token (recalcula a assinatura)
        System.out.println("\nValidando token:");
        boolean valido = validarToken(token);
        System.out.println("Token valido? " + valido);
    }

    // Gera um JWT bem simples com HS256
    private static String gerarToken() throws Exception {
        // Header padrao para HS256
        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";

        // Payload didatico (sem exp, iss, etc. para simplificar)
        String payloadJson = "{\"sub\":\"usuario123\",\"name\":\"Joao da Silva\",\"admin\":true}";

        // Base64URL do header e do payload
        String headerBase64 = base64UrlEncode(headerJson.getBytes(StandardCharsets.UTF_8));
        String payloadBase64 = base64UrlEncode(payloadJson.getBytes(StandardCharsets.UTF_8));

        // Parte sem assinatura
        String headerPayload = headerBase64 + "." + payloadBase64;

        // Assinatura HMAC-SHA256 em cima de header.payload
        String assinatura = assinar(headerPayload);

        // Token completo
        return headerPayload + "." + assinatura;
    }

    // Valida o token recalculando a assinatura
    private static boolean validarToken(String token) throws Exception {
        String[] partes = token.split("\\.");
        if (partes.length != 3) {
            System.out.println("Formato invalido de token");
            return false;
        }

        String headerPayload = partes[0] + "." + partes[1];
        String assinaturaRecebida = partes[2];

        String assinaturaEsperada = assinar(headerPayload);

        if (assinaturaEsperada.equals(assinaturaRecebida)) {
            System.out.println("Assinatura correta");
            return true;
        } else {
            System.out.println("Assinatura incorreta");
            return false;
        }
    }

    // Apenas imprime o JSON do payload
    private static void decodificarPayload(String token) {
        String[] partes = token.split("\\.");
        if (partes.length < 2) {
            System.out.println("Token invalido");
            return;
        }

        byte[] payloadBytes = Base64.getUrlDecoder().decode(partes[1]);
        String payloadJson = new String(payloadBytes, StandardCharsets.UTF_8);
        System.out.println(payloadJson);
    }

    // Calcula HMAC-SHA256(header.payload) com a SECRET
    private static String assinar(String dados) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec chave = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(chave);
        byte[] assinaturaBytes = mac.doFinal(dados.getBytes(StandardCharsets.UTF_8));
        return base64UrlEncode(assinaturaBytes);
    }

    // Base64 URL-safe sem padding
    private static String base64UrlEncode(byte[] valor) {
        return Base64.getUrlEncoder()
                     .withoutPadding()
                     .encodeToString(valor);
    }
}
