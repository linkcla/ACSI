import java.io.*;

public class Main {
    public static void main(String[] args) {
        Main.paralelInfo();
    }

    public static void cpuInfo() {
        // FORMATO:
        // top - 18:47:58 up 50 min,  2 users,  load average: 0.03, 0.02, 0.01#%Cpu(s):  0.7 us,  0.7 sy,  0.3 ni, 98.0 id,  0.3 wa,  0.0 hi,  0.1 si,  0.0 st#
        try {
            FileWriter fileWriter = new FileWriter("data/data.csv");
            BufferedWriter writer = new BufferedWriter(fileWriter);
            FileReader fileReader = new FileReader("data/raw-data.txt");
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            // Escribir encabezado
            writer.write("Time,%CPU(global),%CPU(user),%CPU(system)\n");

            line = reader.readLine();
            String[] parts = line.split("#");

            String time;
            String cpuGlobal;
            String cpuUser;
            String cpuSystem;
            for (int i = 0; i < parts.length; i++) {

                if (i%2 == 0) { // linea de top
                    String[] top = parts[i].split(" ");
                    time = top[2];
                    writer.write(time + ",");
                } else { //linea de cpu
                    String[] cpu = parts[i].split(" ");
                    cpuGlobal = String.valueOf(100.0 - Double.parseDouble(cpu[10]));
                    cpuUser = cpu[2];
                    cpuSystem = cpu[5];
                    writer.write(cpuGlobal + "," + cpuUser + "," + cpuSystem + "\n");
                }
            }

            writer.close();
            fileWriter.close();
            writer.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void memoryInfo() {
        // FORMATO:
        // procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu----- -----timestamp-----#
        // r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st                 CET#
        // 1  0      0 2650872  31648 496844    0    0    47     1   35   65  0  1 98  0  0 2024-02-26 17:41:39
        // all in one line

        int totalMedmoria = 4038176;

        try {
            FileWriter fileWriter = new FileWriter("data/memory-data.csv");
            BufferedWriter writer = new BufferedWriter(fileWriter);
            FileReader fileReader = new FileReader("data/raw-memory-data.txt");
            BufferedReader reader = new BufferedReader(fileReader);

            writer.write("Timestamp,Cpacidad disponible, Capacidad utilizada,% Memoria utilizada \n");
            String line = reader.readLine();
            String[] aux1 = line.trim().split("\\s+");
            String aux2 = String.join(" ", aux1);
            String[] parts = aux2.split("#");

            //time --> 19
            //capacidad disponible --> 4
            //capacidad utilizada --> memoria total - capacidad disponible
            //%memoria utilizada --> (capacidad utilizada / memoria total) * 100

            for (int i = 0; i < parts.length; i++) {
                String[] lineParts = parts[i].split(" ");
                if (lineParts.length == 1) continue;

                writer.write(lineParts[19] + "," + lineParts[4] + "," + (totalMedmoria - Integer.parseInt(lineParts[4])) + "," + ((totalMedmoria - Float.parseFloat(lineParts[4])) / totalMedmoria) * 100 + "\n");
            }

            writer.close();
            fileWriter.close();
            writer.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void paralelInfo() {
        // FORMATO:
        // top - 18:14:57 up 4 min,  1 user,  load average: 0.42, 0.42, 0.20#
        // %Cpu(s):  0.8 us,  3.3 sy,  0.0 ni, 93.7 id,  1.5 wa,  0.0 hi,  0.7 si,  0.0 st#
        // KiB Mem :  4038176 total,  2658620 free,   793560 used,   585996 buff/cache#

        int totalMemoria = 4038176;
        try {
            FileWriter fileWriter = new FileWriter("data/paralel-data.csv");
            BufferedWriter writer = new BufferedWriter(fileWriter);
            FileReader fileReader = new FileReader("data/raw-paralel-data.txt");
            BufferedReader reader = new BufferedReader(fileReader);

            writer.write("Timestamp,% global CPU,Cpacidad de memoria utilizada,% Memoria utilizada \n");
            String line = reader.readLine();
            String[] aux1 = line.trim().split("\\s+");
            String aux2 = String.join(" ", aux1);
            String[] parts = aux2.split("#");

            String cpuGlobal;
            String memoriaUtilizada;
            String porcentajeMemoriaUtilizada;
            for (int i = 0; i < parts.length; i++) {

                if(i%3 == 0) { //top
                    String[] top = parts[i].split(" ");
                    writer.write(top[2] + ",");
                } else if (i%3 == 1) { //cpu
                    String[] cpu = parts[i].split(" ");
                    cpuGlobal = String.format("%.1f",100.0 - Float.parseFloat(cpu[7]));
                    cpuGlobal = cpuGlobal.replace(",",".");
                    writer.write(cpuGlobal + ",");
                } else { //memoria
                    String[] memoria = parts[i].split(" ");
                    memoriaUtilizada = String.valueOf(totalMemoria - Integer.parseInt(memoria[5]));
                    porcentajeMemoriaUtilizada = String.format("%.4f",(Float.parseFloat(memoriaUtilizada) / totalMemoria) * 100);
                    porcentajeMemoriaUtilizada = porcentajeMemoriaUtilizada.replace(",",".");
                    writer.write(memoriaUtilizada + "," + porcentajeMemoriaUtilizada + "\n");
                }
            }

            writer.close();
            fileWriter.close();
            writer.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



