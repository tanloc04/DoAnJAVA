/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Settings;

//quản lý việc lưu trữ và tải cài đặt của ứng dụng từ tệp tin.
public class SettingsManager {

    private static final String SETTINGS_FILE_NAME = "settings.dat";

    private static SettingsManager instance = null;
    
    //Đối tượng Settings chứa các cài đặt.
     private Settings settings;

//đảm bảo chỉ có một thể hiện của SettingsManager trong suốt vòng đời của ứng dụng.
    public static synchronized SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }

    
//    Ghi đối tượng Settings vào tệp tin.
    public void writeSettingsToFile( Settings settings) {
//        ObjectOutputStream: Được sử dụng để ghi đối tượng vào tệp tin.
//        FileOutputStream: Được sử dụng để tạo luồng ghi vào tệp tin.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SETTINGS_FILE_NAME))) {
//            Ghi đối tượng settings vào tệp tin.
            oos.writeObject(settings);
            
//            catch (Exception ex): Bắt và xử lý ngoại lệ nếu có lỗi xảy ra.
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    Đọc đối tượng Settings từ tệp tin.
    public void readSettingsFromFile() {
//        Kiểm tra xem tệp tin cài đặt có tồn tại hay không.
        if (new File(SETTINGS_FILE_NAME).exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SETTINGS_FILE_NAME))) {
                settings = (Settings) ois.readObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

//    Đọc cài đặt từ tệp tin và trả về đối tượng Settings.
    public Settings getSettings() {
        readSettingsFromFile();
        return settings;
    }
}