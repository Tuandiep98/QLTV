/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.tien_ich;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class datahelper {

    public static char[] syb = {'.', ',', '_', '=', '+', '(', ')', '<', '>', '?', '/', '"', '\\', '\'', '!',
            '@', '#', '$', '%', '^', '*', '{', '}', '[', ']', ':', ';'};
        public static char[] num = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    
    public static boolean checkNumDouble(String text, int dodai, int min, int max,String note) {
        boolean b = true;
        char[] a = text.toCharArray();

        if (text.length() > 0) {
            if (text.length() <= dodai) {
                try {
                        double n = Double.parseDouble(text);
                        if (!(n >= min && n <= max)) {
                            b = false;
                            dialoghelp.alert(null, note+" Phải lớn hơn hoặc bằng "
                                    + min + " và " + "nhỏ hơn hoặc bằng " + max);
                        }
                    } catch (Exception e) {
                        b = false;
                        dialoghelp.alert(null, "Sai định dạng "+note);
                    }
            } else {
                b = false;
                dialoghelp.alert(null, note+" Không được quá " + dodai + " ký tự");
            }
        } else {
            b = false;
            dialoghelp.alert(null, "Không được để trống "+note);
        }
        return b;
    }
   
    

    public static boolean checkNum(String text, int dodai, int min, int max,String note) {
        boolean b = true;
        char[] a = text.toCharArray();

        if (text.length() > 0) {
            if (text.length() <= dodai) {
                try {
                        int n = Integer.parseInt(text);
                        if (!(n >= min && n <= max)) {
                            b = false;
                            dialoghelp.alert(null, note+" Phải lớn hơn hoặc bằng "
                                    + min + " và " + "nhỏ hơn hoặc bằng " + max);
                        }
                    } catch (Exception e) {
                        b = false;
                        dialoghelp.alert(null,"Sai định dạng " + note);
                    }
            } else {
                b = false;
                dialoghelp.alert(null, note+" Không được quá " + dodai + " ký tự");
            }
        } else {
            b = false;
            dialoghelp.alert(null, "Không được để trống "+note);
        }
        return b;
    }

    public static boolean checkChar(String text, int dodai,String note) {
        boolean b = true;
        char[] a = text.toCharArray();

        if (text.length() > 0) {
            if (text.length() <= dodai) {
                chk:
                for (int i = 0; i < a.length; i++) {
                    for (int j = 0; j < syb.length; j++) {
                        if (a[i] == syb[j]) {
                            b = false;
                            dialoghelp.alert(null,"Lỗi "+ note+" sai định dạng");
                            break chk;
                        }
                    }
                    if (note.equals("tác giả")|| note.equals("tên danh mục")||note.equals("tên nhà xuất bản")) {
                        for (int j = 0; j < num.length; j++) {
                        if (a[i] == num[j]) {
                            b = false;
                            dialoghelp.alert(null,"lỗi "+ note+" không được nhập số");
                            break chk;
                        }
                    }
                    }
                    
                    
                }
            } else {
                b = false;
                dialoghelp.alert(null, note+" không được quá " + dodai + " ký tự");
            }
        } else {
            b = false;
            dialoghelp.alert(null, "Không được để trống "+note);
        }
        return b;
    }



}
