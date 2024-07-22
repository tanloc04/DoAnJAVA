/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.Bubble;
import model.Direction;
import model.GameObject;
import model.PlayerFish;
import view.GamePanel;

//chịu trách nhiệm quản lý vòng lặp chính của trò chơi, xử lý các trạng thái của trò chơi
public class GameManager {
    
//    1.Các Biến
    //chất lượng card màn hình hiện bao nhiêu hình ảnh trong 1s để hiển thị trên màn hinh
    private static final long REFRESH_INTERVAL_MS = 10;
    private long startTime;//thời gian bắt đầu
    private boolean isGameRunning = false;//trò chơi đang chạy
    private boolean isGamePaused = false;//trò chơi bị tạm dừng
    /*
     * Nếu trò chơi kết thúc
     */
    private boolean isGameOver = false;
    /*
     * Nếu trò chơi đang trong quá trình chuyển đổi cấp độ
     */
    private boolean isInLevelTransition = false;

    /*
     * If game should add a special fish
     */
    private boolean readyForSpecialFish = false;

    /**
     * Nếu thêm hiệu ứng bong bóng cho game
     */
    private boolean shouldAddBubble = false;

    /**
     * Thành phần Game View, sẽ được sử dụng để vẽ các đối tượng trò chơi lên màn hình đc truy xuất từ lớp GamePanel
     */
    private GamePanel gamePanel;
    /**
     * Trình quản lý bản đồ trò chơi, sẽ quyết định vị trí và số lượng trò chơi
     * các đối tượng
     */
    private GameMapManager gameMapManager;
    /**
     * Luồng chính chạy vòng lặp trò chơi.
     */
    private Thread gameThread;
    /**
     * Danh sách đối tượng trò chơi
     */
    private List<GameObject> gameObjects = new ArrayList<>();
    /**
     * Nhân vật chính do người chơi điều khiển
    * danh sách đối tượng.
     */
    private PlayerFish playerFish;

    /**
     * 2. Constructor
     * 
     * Khởi tạo với gamePanel và gameMapManager.
     */
    public GameManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gameMapManager = new GameMapManager();
    }

    /**
     * 3.Phương Thức
     * a.initialize(): Khởi tạo trạng thái trò chơi, xóa các đối tượng trò chơi hiện có, và thiết lập cá người chơi 
     */
    public void initialize() {

        isGameRunning = true;
        isGamePaused = false;
        isGameOver = false;
        
       //Xóa tất cả các đối tượng hiện có trong danh sách đối tượng trò chơi.
//        gameObjects.clear();
       //Thêm tất cả các đối tượng trò chơi từ trình quản lý bản đồ (gameMapManager) vào danh sách đối tượng trò chơi.
        gameObjects.addAll(gameMapManager.getMapObjects());

//        Nếu cá người chơi chưa được khởi tạo (lần đầu tiên chơi  tạo mới hoàn toàn)
        if (playerFish == null) {
            playerFish = new PlayerFish();
//            Nếu đang trong giai đoạn chuyển cấp độ->Đặt lại cá người chơi cho cấp độ mới+Đặt lại cờ để biểu thị đã hoàn tất chuyển cấp độ.
        } else if (isInLevelTransition) {
            playerFish.resetForNewLevel();
            isInLevelTransition = false;
        } else {
            playerFish.reset();
        }
//Cập nhật giao diện người dùng (gamePanel) với thông tin về cá người chơi, các đối tượng trò chơi, và cấp độ hiện tại.
        this.gamePanel.setLevel(playerFish, gameObjects, gameMapManager.getLevel());
    }

    /**
     * b.Bắt đầu trò chơi mới hoặc tiếp tục trò chơi hiện tại
     */
    public void startGame( boolean isNewGame) {
        if (isNewGame) {
            playerFish = new PlayerFish();
//            Đặt cấp độ trò chơi về cấp độ 0, tức là bắt đầu từ cấp độ đầu tiên.
            gameMapManager.setLevel(0);
        }
//        khởi tạo lại trạng thái của trò chơi
        initialize();
//        Nếu chủ đề trò chơi (gameThread) không phải là null và đang chạy (isAlive()):
        if (gameThread != null && gameThread.isAlive()) {
//            Dừng chủ đề trò chơi cũ bằng cách gửi tín hiệu ngắt (interrupt).
            gameThread.interrupt();
        }
//        Tạo Và Bắt Đầu Chủ Đề Trò Chơi Mới
        gameThread = new Thread(new GameLoop());
        gameThread.start();
//        Đặt Trạng Thái Không Hoạt Động Cho Cá Người Chơi
        playerFish.setCurrentlyActive(false);
    }

    /**
     * Dừng trò chơi.
     */
    public void stopGame() {
        isGamePaused = false;
        isGameRunning = false;
    }

    /**
     * Tạm dừng trò chơi.
     */
    public void pauseGame() {
        isGamePaused = true;
    }

    /**
     * Tiếp tục trò chơi.
     */
    public void resumeGame() {
        isGamePaused = false;
    }

    /**
     * Vòng lặp trò chơi
     */
    private class GameLoop implements Runnable {

        @Override
        public void run() {
        // Khởi Tạo Thời Gian Bắt Đầu:Lấy thời gian hiện tại khi trò chơi bắt đầu.
            startTime = System.currentTimeMillis();

             // Vòng lặp chính của trò chơi chạy liên tục khi trò chơi đang chạy
            while (isGameRunning) {
                
                // Nếu trò chơi đang tạm dừng, dừng lại trong khoảng thời gian ngắn:100s
                while (isGamePaused) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Kiểm tra xem trò chơi có kết thúc hoặc chuyển sang cấp độ mới không. Nếu có, bỏ qua các bước tiếp theo và tiếp tục vòng lặp.
                if (checkGameState()) {
                    continue;
                }

//                Lấy thời gian hiện tại để tính toán thời gian cho một tick của trò chơi.
                long oneTickOfGame = System.currentTimeMillis();
                // Xử lý đầu vào
                handleInput();
                 // Xử lý logic nếu người chơi có thể chơi
                if (playerFish.isCurrentlyActive()) {
                    handleLogic();
                }
                 // Xử lý vẽ
                handleDrawing();

                 // Đảm bảo FPS không giảm quá nhiều
                if (REFRESH_INTERVAL_MS - (System.currentTimeMillis() - oneTickOfGame) > 0) {
                    try {
                        Thread.sleep(REFRESH_INTERVAL_MS - (System.currentTimeMillis() - oneTickOfGame));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("FPS is decreasing!");
                }
            }
             // Nếu người chơi hết mạng sống, hiển thị màn hình kết thúc trò chơi
            if (playerFish.getLives() == 0) {
                gamePanel.showGameOverScreen();
            }
        }
    }

    /**
     *  kiểm tra trạng thái hiện tại của trò chơi để xác định xem trò chơi đã kết thúc hay người chơi đã đạt đến cấp độ tiếp theo
     *
     * @return true, nếu trò chơi kết thúc hoặc cấp độ tiếp theo
     */
    private synchronized boolean checkGameState() {
        
//        Kiểm tra xem người chơi còn mạng sống nào không.
//Nếu playerFish.getLives() trả về 0, điều đó có nghĩa là người chơi đã hết mạng sống
        if (playerFish.getLives() == 0) {
//            đảm bảo trò chơi không bị tạm dừng.
            isGamePaused = false;
//            để ngừng vòng lặp trò chơi.
            isGameRunning = false;
//            để đánh dấu trò chơi đã kết thúc.
            isGameOver = true;
            return true;
            
//            Kiểm Tra Nếu Người Chơi Đạt Đến Cấp Độ Tiếp Theo
//Nếu playerFish.getSize() trả về 8, điều đó có nghĩa là người chơi đã đạt đến kích thước tối đa cần thiết để hoàn thành cấp độ.
        } else if (playerFish.getSize() == 8) {
            isGamePaused = false;
//            tiếp tục vòng lặp trò chơi.
            isGameRunning = true;
            isGameOver = false;
//            bắt đầu quá trình chuyển cấp độ.
            isInLevelTransition = true;
            gamePanel.setInLevelTransition(true);
//            vẽ lại màn hình ngay lập tức, cập nhật giao diện người dùng.
            gamePanel.paintImmediately(0, 0, GamePanel.RESOLUTION.width, GamePanel.RESOLUTION.height);
            try {
//                Tạm dừng luồng trong 2 giây để tạo hiệu ứng chuyển tiếp cấp độ
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gamePanel.setInLevelTransition(false);
            initialize();
            return true;
        }
        return false;
    }

    /**
     * Xử lý đầu vào
     */
    private synchronized void handleInput() {
//        Cập nhật hướng và vị trí của playerFish (nhân vật chính do người chơi điều khiển).
        playerFish.setDirection((Direction) InputManager.getInstance().getChangeDirection());
        playerFish.setPosition(InputManager.getInstance().getMousePoint());

        for (GameObject gameObject : gameObjects) {
            if (gameObject.isControlledByMouse()) {
                gameObject.setDirection((Direction) InputManager.getInstance().getChangeDirection());
                gameObject.setPosition(InputManager.getInstance().getMousePoint());
            } else if (gameObject.isControlledByAi()) {
                gameObject.move();
            }
        }
    }

    /**
     * Xử lý logic.
     */
    private synchronized void handleLogic() {
        boolean shouldAddNewEnemyFish = false;

        // Xóa Các Đối Tượng Trò Chơi Cần Được Xóa
        Iterator<GameObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()) {
            GameObject gameObject = iterator.next();
            if (gameObject.isMarkedForDestroying()) {
                iterator.remove();
                shouldAddNewEnemyFish = true;
            }
        }

        // Cập Nhật Trạng Thái Của Các Đối Tượng Trò Chơi
        for (GameObject gameObject : gameObjects) {
            if (gameObject.intersects(playerFish.getBoundingBox())) {
                gameObject.updateState(this, gameMapManager, playerFish);
            }
        }

        // Thêm Đối Thủ Mới
        if (shouldAddNewEnemyFish) {
            gameObjects.add(gameMapManager.getNewEnemyFish(gameObjects, playerFish.getSize()));
        }

        // Thêm Đối Thủ Đặc Biệt
        if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime) % 10 == 0 && !readyForSpecialFish) {
            GameObject specialFish = gameMapManager.getSpecialFish();
            if (specialFish != null) {
                gameObjects.add(specialFish);
                readyForSpecialFish = true;
            }
        } else if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime) % 10 != 0) {
            readyForSpecialFish = false;
        }

        // Thêm Bong Bóng
        if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime) % 5 == 0
                && gameObjects.stream().filter(gameObject -> gameObject instanceof Bubble).count() < 20
                && !shouldAddBubble) {
            gameObjects.addAll(gameMapManager.addBubbles());
            shouldAddBubble = true;
        } else if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime) % 5 != 0) {
            shouldAddBubble = false;
        }
    }

    /**
     * Xử lý bản vẽ:Vẽ lại trạng thái mới nhất của trò chơi lên màn hình.
     */
    private synchronized void handleDrawing() {
//        Tính thời gian đã trôi qua kể từ khi bắt đầu trò chơi.
        long currentTime = System.currentTimeMillis() - startTime;
//        Cập nhật số phút và giây cho gamePanel dựa trên thời gian đã trôi qua.
        gamePanel.setMinutes(TimeUnit.MILLISECONDS.toMinutes(currentTime));
        gamePanel.setSeconds(TimeUnit.MILLISECONDS.toSeconds(currentTime));
//        Sử dụng paintImmediately() để vẽ lại màn hình ngay lập tức với các thay đổi mới nhất. 
        gamePanel.paintImmediately(0, 0, GamePanel.RESOLUTION.width, GamePanel.RESOLUTION.height);
   
    }

    /**
     * Thêm một đối tượng mới vào danh sách đối tượng trò chơi.
     */
    public synchronized void addNewObject( GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    /**
     * Trả về điểm số hiện tại của người chơi.
     * Trả về: Điểm số hiện tại của người chơi (playerFish.getScore()).
     */
    public int getScore() {
        return playerFish.getScore();
    }

    /**
     * Kiểm tra xem trò chơi đã kết thúc chưa.
     * Trả về: true nếu trò chơi đã kết thúc, ngược lại trả về false.
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Kiểm tra xem trò chơi có đang tạm dừng không.
     * Trả về: true nếu trò chơi đang tạm dừng, ngược lại trả về false.
     */
    public boolean isGamePaused() {
        return isGamePaused;
    }

    /**
     * Kiểm tra xem trò chơi đang chạy không.
     *
     Trả về: true nếu trò chơi đang chạy, ngược lại trả về false.
     */
    public boolean isGameRunning() {
        return isGameRunning;
    }

}