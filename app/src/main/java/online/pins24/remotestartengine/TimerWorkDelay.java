package online.pins24.remotestartengine;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

//Вторая реализация таймера и события что делать по его окончанию
public class TimerWorkDelay {

    private TimerWorkDelayCallBack timerWorkDelayCallBack;
    private Handler handler = new Handler();
    private int counter = 0;
    private static long ONE_MIN = TimeUnit.MINUTES.toMillis(1);

    public void setTimerWorkDelayCallBack(TimerWorkDelayCallBack timerWorkDelayCallBack) {
        this.timerWorkDelayCallBack = timerWorkDelayCallBack;
    }

    public int getCounter() {
        return counter;
    }

    Runnable tickTimer = new Runnable() {
        @Override
        public void run() {
            tickTimer();
        }
    };

    public void startTimer() {
        if (counter > 0) {
            throw new IllegalStateException("Таймер уже запущен");
        }
        counter = 15;
        tickTimer();
    }

    public void stopTimerManual() {
        // Удаляем Runnable-объект для прекращения задачи
        handler.removeCallbacks(tickTimer);
        counter = 0;
    }

    public void tickTimer() {
        counter--;
        if (counter > 0) {
            timerWorkDelayCallBack.onTimerChanged();
            scheduleNextMinute();
        }
        else {
            doSomething();
        }
    }

    //Таймер на минуту
    public void scheduleNextMinute() {
        handler.postDelayed(tickTimer, ONE_MIN);
    }

    private void doSomething() {
        if (timerWorkDelayCallBack == null) {
            return;
        }
        else {
            timerWorkDelayCallBack.onTimerStop();
        }
    }

    public interface TimerWorkDelayCallBack {
        void onTimerStop();
        void onTimerChanged();
    }
}


