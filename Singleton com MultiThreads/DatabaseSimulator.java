import java.util.Random;

public class DatabaseSimulator {
    // Simula dados fictícios no banco
    private static String[] usuarios = {
        "João Silva", "Maria Santos", "Pedro Oliveira", "Ana Souza", 
        "Carlos Ferreira", "Juliana Lima", "Roberto Alves", "Fernanda Costa"
    };
    
    private static String[] produtos = {
        "Notebook Dell", "Mouse Logitech", "Teclado Mecânico", "Monitor LG",
        "iPhone 15", "Samsung Galaxy", "iPad Pro", "Smartwatch Apple"
    };
    
    private static Random random = new Random();
    
    // Gera queries aleatórias simulando operações reais
    public static String generateRandomQuery() {
        int operationType = random.nextInt(3); // 0=SELECT, 1=INSERT, 2=UPDATE
        
        switch (operationType) {
            case 0: // SELECT
                return "SELECT * FROM usuarios WHERE id = " + (random.nextInt(100) + 1);
                
            case 1: // INSERT
                String nome = usuarios[random.nextInt(usuarios.length)];
                return "INSERT INTO usuarios (nome, email, data_cadastro) VALUES ('" 
                       + nome + "', '" + nome.toLowerCase().replace(" ", ".") 
                       + "@email.com', NOW())";
                       
            case 2: // UPDATE
                return "UPDATE produtos SET estoque = " + (random.nextInt(100) + 1) 
                       + " WHERE id = " + (random.nextInt(50) + 1);
                       
            default:
                return "SELECT 1";
        }
    }
    
    // Gera queries de consulta específicas
    public static String generateSpecificQuery(int threadId) {
        String[] queries = {
            "SELECT * FROM clientes WHERE status = 'ATIVO'",
            "SELECT COUNT(*) FROM pedidos WHERE data > '2024-01-01'",
            "INSERT INTO logs (thread_id, data_acesso) VALUES (" + threadId + ", NOW())",
            "UPDATE vendas SET status = 'PROCESSADO' WHERE thread_id = " + threadId,
            "SELECT nome, email FROM usuarios WHERE ultimo_acesso > NOW() - INTERVAL 7 DAY"
        };
        
        return queries[threadId % queries.length];
    }
}