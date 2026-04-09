package com.example.moattravel.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.moattravel.service.ReservationBatchService;

@Component
public class ReservationBatchScheduler {

    @Autowired
    private ReservationBatchService batchService;

    
    // 本番を想定した毎月1日の午前3時に実行するプログラム 
    //※研修時は動作確認の為一旦コメントアウト
    
    /*@Scheduled
     * 今回のバッチ処理の花形！！
     * メソッドを定期実行出来るアノテーション
     * 
     * Cronで日時指定
     * 下の桁から時間は表記されている
     * 秒＝0,分＝0,
     * 時＝3　:　午前3時
     * 日＝1　: 1日
     * 月＝*　：　毎月
     * 曜日　＝?　：　指定しない
     * で 0031*?　表記をしている
     * 毎月1日の午前3時に1回だけ実行する、を意味している。
     * 
     * 
     * */
   /*
    @Scheduled(cron = "0 0 3 1 * ?")
    public void runMonthlyArchive() {
        batchService.archiveOldReservations();
    }*/
    
 // テスト用　アプリ起動してから3分後に実行するプログラム、3分ごとに繰り返し実行
    //本番を想定する場合、こちらはコメントアウトするのを忘れない
    
    
    /*initialDelay→アプリ起動後、処理を実行するまでの待ち時間
     * ※３分以内に予約テーブルに100件残っているか確認する
     * 3分後に事項する
     * 
     * ミリ秒表記なので今回は
     * 180000 ミリ秒 = 3 分　で表されている
     * 
     * fixedRateで3分おきに繰り返し
     * */
    @Scheduled(initialDelay = 180000, fixedRate = 180000)
    public void testScheduler() {
    	batchService.archiveOldReservations();
    } 
    
}