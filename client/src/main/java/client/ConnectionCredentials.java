package client;
/**
 * A classe ConnectionCredentials contém as credenciais de conexão para o servidor.
 * <p>
 * Esta classe armazena o endereço do servidor e a porta utilizada para a conexão.
 * </p>
 *
 * @version 1.0
 * @since 2024-06-25
 */
public class ConnectionCredentials {
    /**
     * O endereço do servidor. Valor padrão é "localhost".
     */
    public String address = "192.168.56.1";
    /**
     * A porta do servidor. Valor padrão é 8080.
     */
    public int port = 8080;
}
