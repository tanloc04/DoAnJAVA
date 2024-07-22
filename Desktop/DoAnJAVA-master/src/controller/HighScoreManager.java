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
import java.util.ArrayList;
import java.util.List;

import model.HighScore;

public class HighScoreManager {
//HIGH_SCORE_FILE_NAME: Tên file lưu trữ điểm cao là highscores.dat.
    private static final String HIGH_SCORE_FILE_NAME = "highscores.dat";

    private static HighScoreManager instance = null;

    private List<HighScore> highScores = new ArrayList<>();

    public static synchronized HighScoreManager getInstance() {
        if (instance == null) {
            instance = new HighScoreManager();
        }
        return instance;
    }
    
    
//writeScoreToFile: Ghi điểm cao mới vào file.
    public void writeScoreToFile( HighScore highScore) {
//        ObjectOutputStream: Dùng để ghi đối tượng vào file.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGH_SCORE_FILE_NAME))) {
//            highScores.add(highScore): Thêm điểm cao mới vào danh sách.
            highScores.add(highScore);
//            oos.writeObject(highScores): Ghi danh sách điểm cao vào file.
            oos.writeObject(highScores);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
//Đọc danh sách điểm cao từ file.
    public void readScoresFromFile() {
//        File.exists(): Kiểm tra xem file có tồn tại hay không.
        if (new File(HIGH_SCORE_FILE_NAME).exists()) {
//            ObjectInputStream: Dùng để đọc đối tượng từ file.
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HIGH_SCORE_FILE_NAME))) {
                highScores.clear();
//                highScores.addAll(...): Thêm tất cả điểm cao từ file vào danh sách.
                highScores.addAll((List<HighScore>) ois.readObject());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
//getHighScores: Trả về danh sách các điểm cao.
    public List<HighScore> getHighScores() {
//        readScoresFromFile: Đảm bảo danh sách điểm cao được cập nhật từ file trước khi trả về.
        readScoresFromFile();
        return highScores;
    }

}