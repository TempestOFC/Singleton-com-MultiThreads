public class DatabaseAccessThread extends Thread {
    private String queryToExecute;
    private int threadId;
    
    public DatabaseAccessThread(int threadId, String queryToExecute) {
        super("Thread-" + threadId);
        this.threadId = threadId;
        this.queryToExecute = queryToExecute;
    }
    
    @Override
    public void run() {
        System.out.println("\n🚀 [" + getName() + "] INICIANDO ACESSO AO BANCO DE DADOS");
        System.out.println("   Query: " + queryToExecute);
        
        // Obtém a instância única do gerenciador de conexão
        DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
        
        // Mostra informações da conexão obtida
        System.out.println("📦 [" + getName() + "] Conexão obtida: " + connectionManager.getConnectionInfo());
        
        // Simula o acesso ao banco
        connectionManager.connect();
        connectionManager.executeQuery(queryToExecute);
        
        // Simula um pequeno processamento
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✨ [" + getName() + "] ACESSO CONCLUÍDO COM SUCESSO\n");
        System.out.println("────────────────────────────────────────────────────");
    }
}