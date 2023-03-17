import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(10, 2, 99, 33.2);
        GameProgress gameProgress2 = new GameProgress(12, 3, 105, 45.2);
        GameProgress gameProgress3 = new GameProgress(13, 4, 106, 50.2);

        String way1 = "C:\\Users\\in_sd\\IdeaProjects\\Installation\\Games\\savegames\\save1.dat";
        String way2 = "C:\\Users\\in_sd\\IdeaProjects\\Installation\\Games\\savegames\\save2.dat";
        String way3 = "C:\\Users\\in_sd\\IdeaProjects\\Installation\\Games\\savegames\\save3.dat";

        saveGame(way1, gameProgress1);
        saveGame(way2, gameProgress2);
        saveGame(way3, gameProgress3);

        List<String> listForZip = new ArrayList<>();
        listForZip.add(way1);
        listForZip.add(way2);
        listForZip.add(way3);

        zipFiles("C:\\Users\\in_sd\\IdeaProjects\\Installation\\Games\\savegames\\zip.zip", listForZip);

        fileDelete(listForZip);


    }

    public static void saveGame(String nameWay, GameProgress gameProgress) {
        try (FileOutputStream fos =
                     new FileOutputStream(nameWay);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String nameWayZip, List<String> list) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(nameWayZip))) {
            for (String lists : list) {
                try (FileInputStream fis = new FileInputStream(lists)) { // файл, который архивируем

                    ZipEntry entry = new ZipEntry(getNameFile(lists)); // название файла в архиве
                    zout.putNextEntry(entry); // записываем файл в архив
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zout.write(buffer);
                    // закрываем текущую запись для новой записи
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static String getNameFile(String input) {

        return input.substring(input.lastIndexOf("\\") + 1);
    }

    public static void fileDelete(List<String> list) {
        for (String lists : list) {
            File file = new File(lists);
            if (file.delete()) {
                System.out.println("Файл удален: " + getNameFile(lists) + " по пути: " + lists);
            } else System.out.println("Файла  не обнаружено: " + getNameFile(lists));
        }

    }

}


