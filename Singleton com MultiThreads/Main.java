import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║     SIMULADOR DE ACESSO A BANCO DE DADOS COM MULTITHREAD     ║");
        System.out.println("║              (Singleton Pattern - Database Connection)       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");
        
        // Número de threads que serão criadas
        int numberOfThreads = 5;
        
        System.out.println("🎯 Configuração inicial:");
        System.out.println("   • Total de threads: " + numberOfThreads);
        System.out.println("   • Padrão: Singleton para gerenciador de conexão");
        System.out.println("   • Objetivo: Garantir apenas uma instância do DatabaseConnectionManager\n");
        
        System.out.println("════════════════════════════════════════════════════════════════\n");
        
        // Lista para armazenar as threads
        List<DatabaseAccessThread> threads = new ArrayList<>();
        
        // Criação das threads com queries diferentes
        for (int i = 1; i <= numberOfThreads; i++) {
            // Gera uma query diferente para cada thread
            String query;
            if (i == 1) {
                query = "SELECT * FROM produtos WHERE preco > 100.00";
            } else if (i == 2) {
                query = "INSERT INTO logs (mensagem, data) VALUES ('Acesso via thread " + i + "', NOW())";
            } else if (i == 3) {
                query = "UPDATE usuarios SET ultimo_acesso = NOW() WHERE id = " + i;
            } else if (i == 4) {
                query = "SELECT nome, email, telefone FROM clientes ORDER BY nome";
            } else {
                query = DatabaseSimulator.generateRandomQuery();
            }
            
            DatabaseAccessThread thread = new DatabaseAccessThread(i, query);
            threads.add(thread);
        }
        
        System.out.println("🚀 Iniciando " + numberOfThreads + " threads de acesso ao banco de dados...\n");
        System.out.println("════════════════════════════════════════════════════════════════\n");
        
        // Inicia todas as threads
        for (DatabaseAccessThread thread : threads) {
            thread.start();
            
            // Pequeno delay entre as inicializações para simular chegada em momentos diferentes
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Aguarda todas as threads terminarem
        for (DatabaseAccessThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("\n📊 RELATÓRIO FINAL:");
        System.out.println("════════════════════════════════════════════════════════════════");
        
        // Verifica quantas instâncias foram criadas
        int instanceCount = DatabaseConnectionManager.getInstanceCount();
        DatabaseConnectionManager finalManager = DatabaseConnectionManager.getInstance();
        
        System.out.println("\n✅ RESULTADO DA SIMULAÇÃO:");
        System.out.println("   • Total de threads executadas: " + numberOfThreads);
        System.out.println("   • Instâncias do DatabaseConnectionManager criadas: " + instanceCount);
        System.out.println("   • Total de acessos ao banco: " + finalManager.getAccessCount());
        System.out.println("   • Conexão utilizada: " + finalManager.getConnectionInfo());
        
        if (instanceCount == 1) {
            System.out.println("\n🎉 SUCESSO! Apenas uma instância foi criada e compartilhada por todas as threads!");
            System.out.println("   ✅ Singleton Pattern funcionou corretamente.");
            System.out.println("   ✅ Todas as threads utilizaram a MESMA conexão com o banco.");
            System.out.println("   ✅ Problemas de concorrência foram evitados.");
        } else {
            System.out.println("\n⚠️  ALERTA! Múltiplas instâncias foram criadas!");
            System.out.println("   ❌ O Singleton Pattern não funcionou como esperado.");
        }
        
        // Fecha a conexão no final
        System.out.println("\n🔒 Encerrando aplicação...");
        finalManager.disconnect();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    SIMULAÇÃO CONCLUÍDA                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");
    }
}