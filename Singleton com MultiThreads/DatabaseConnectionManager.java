import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseConnectionManager {
    // Única instância da classe (Singleton)
    private static DatabaseConnectionManager instance;
    
    // Contador para mostrar quantas conexões foram criadas
    private static AtomicInteger instanceCount = new AtomicInteger(0);
    
    // Simula informações da conexão
    private String connectionUrl;
    private String username;
    private boolean isConnected;
    private AtomicInteger accessCount;
    
    // Construtor privado - impede criação externa
    private DatabaseConnectionManager() {
        this.connectionUrl = "jdbc:mysql://localhost:3306/meubanco";
        this.username = "usuario_db";
        this.isConnected = false;
        this.accessCount = new AtomicInteger(0);
        
        // Incrementa contador de instâncias criadas
        int instanceNumber = instanceCount.incrementAndGet();
        
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║  🗄️  NOVA INSTÂNCIA DO DATABASE CONNECTION MANAGER  ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("🔢 Número da instância: " + instanceNumber);
        System.out.println("🔗 Connection URL: " + connectionUrl);
        System.out.println("👤 Username: " + username);
        System.out.println("📊 Status: Instância criada (ainda não conectada)");
        System.out.println("────────────────────────────────────────────────────\n");
    }
    
    // Método estático para obter a única instância (thread-safe)
    public static synchronized DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }
    
    // Simula abertura de conexão com o banco
    public synchronized void connect() {
        if (!isConnected) {
            System.out.println("🔌 [" + Thread.currentThread().getName() + "] Conectando ao banco de dados...");
            
            // Simula tempo de conexão
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            isConnected = true;
            System.out.println("✅ [" + Thread.currentThread().getName() + "] Conexão estabelecida com sucesso!");
        } else {
            System.out.println("♻️  [" + Thread.currentThread().getName() + "] Reutilizando conexão existente...");
        }
    }
    
    // Simula execução de query no banco
    public synchronized void executeQuery(String query) {
        if (!isConnected) {
            System.out.println("❌ [" + Thread.currentThread().getName() + "] Sem conexão ativa! Conectando primeiro...");
            connect();
        }
        
        int currentAccess = accessCount.incrementAndGet();
        
        System.out.println("\n📊 [" + Thread.currentThread().getName() + "] Executando query:");
        System.out.println("   SQL: " + query);
        
        // Simula processamento da query
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("   ✅ Query executada com sucesso!");
        System.out.println("   📈 Total de acessos via esta instância: " + currentAccess);
        System.out.println("   🔗 Conexão utilizada: " + this.hashCode());
    }
    
    // Simula fechamento da conexão
    public synchronized void disconnect() {
        if (isConnected) {
            System.out.println("\n🔌 [" + Thread.currentThread().getName() + "] Fechando conexão com o banco...");
            isConnected = false;
            System.out.println("✅ Conexão fechada com sucesso!");
        } else {
            System.out.println("⚠️  [" + Thread.currentThread().getName() + "] Conexão já estava fechada.");
        }
    }
    
    // Métodos getters para informações
    public boolean isConnected() {
        return isConnected;
    }
    
    public int getAccessCount() {
        return accessCount.get();
    }
    
    public static int getInstanceCount() {
        return instanceCount.get();
    }
    
    public String getConnectionInfo() {
        return "URL: " + connectionUrl + " | Usuário: " + username + " | Hash: " + this.hashCode();
    }
}