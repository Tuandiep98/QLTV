/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.tien_ich;

import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import QLTV.Model.NhanVien;

/**
 *
 * @author ASUS
 */
public class sharehelper {
    public static final Image APP_ICON;

    // thieesu jdbchelp voi datehelp
    static {

        String file = "/icon/fpt.png";
        APP_ICON = new ImageIcon(sharehelper.class.getResource(file)).getImage();
    }

    public static boolean saveLogo(File file) {
        File dir = new File("logos");

        if (!dir.exists()) {
            dir.mkdirs();
        }
        File newFile = new File(dir, file.getName());
        try {

            Path source = Paths.get(file.getAbsolutePath());
            Path destination = Paths.get(newFile.getAbsolutePath());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static ImageIcon readLogo(String fileName) {
        File path = new File("logos", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }

    public static NhanVien  USER = null;

    public static void logoff() {
        sharehelper.USER = null;
    }

    public static boolean authenticated() {
        return sharehelper.USER != null;
    }
}
    

