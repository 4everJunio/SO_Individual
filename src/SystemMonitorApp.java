import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.management.*;
import java.io.*;

public class SystemMonitorApp extends JFrame {

    private JLabel cpuLabel;
    private JLabel memoryLabel;
    
    public SystemMonitorApp() {
        setTitle("System Monitor");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        
        cpuLabel = new JLabel("CPU Usage: 0%", JLabel.CENTER);
        memoryLabel = new JLabel("Free Memory: 0 GB", JLabel.CENTER);
        
        add(cpuLabel);
        add(memoryLabel);
        
        setVisible(true);
        
        // Update stats every 10 seconds
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new UpdateStatsTask(), 0, 1000);
    }
    
    private class UpdateStatsTask extends TimerTask {
        @Override
        public void run() {
            // Obținem utilizarea CPU
            double cpuUsage = getCpuUsage();
            // Obținem memoria liberă
            double freeMemoryGB = getFreeMemory();
            
            // Actualizăm etichetele
            cpuLabel.setText(String.format("CPU Usage: %.2f%%", cpuUsage));
            memoryLabel.setText(String.format("Free Memory: %.2f GB", freeMemoryGB));
        }
    }
    
    private double getCpuUsage() {
        try {
            // Comandă pentru a obține utilizarea CPU pe macOS
            String command = "top -l 1 | grep 'CPU usage' | awk '{print $3}'";
            Process process = Runtime.getRuntime().exec(new String[] {"/bin/bash", "-c", command});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            return Double.parseDouble(line.replace("%", "").trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    private double getFreeMemory() {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        // Convertim la GB
        return freeMemory / (1024.0 * 1024 * 1024);
    }

    public static void main(String[] args) {
        // Lansați aplicația Swing
        SwingUtilities.invokeLater(() -> new SystemMonitorApp());
    }
}
