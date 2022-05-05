package com.example.musicplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.util.Timer;
import java.util.TimerTask;

public class HelloController {

    private Timer timer;
    private TimerTask task;
    private boolean running;



    private MediaPlayer mediaPlayer;
    @FXML
    private MediaView mediaView;

    @FXML
    private ProgressBar songProgressBar;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select mp3 or mp4 file", "*.mp4", "*.mp3");
        fileChooser.getExtensionFilters().add(filter);

        String filePath = fileChooser.showOpenDialog(null).toURI().toString();

        if (filePath != null){
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
            mediaView.setFitWidth(1280);
            mediaView.setFitHeight(600);
            beginTimer();
        }


        Media media = new Media(filePath);


    }

    @FXML
    private void playMedia(ActionEvent event) {
        mediaPlayer.play();
        beginTimer();
    }

    @FXML
    private void stopMedia(ActionEvent event) {
        mediaPlayer.stop();
        songProgressBar.setProgress(0);
    }

    @FXML
    private void pauseMedia(ActionEvent event) {
        mediaPlayer.pause();
        cancelTimer();
    }
    
    public void beginTimer(){
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = mediaPlayer.getTotalDuration().toSeconds();
                songProgressBar.setProgress(current/end);

                if (current/end == 1) {
                    cancelTimer();
                }
            }

        };
        timer.scheduleAtFixedRate(task, 1000, 1000);

    }
    
    public void cancelTimer(){
        running = false;
        timer.cancel();
    }
}