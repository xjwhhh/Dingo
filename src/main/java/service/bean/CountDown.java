package service.bean;

public class CountDown implements Runnable{
    public void run() {
        int time=900;
        while(time>0){
            int min=time/60;
            int sec=time%60;
            System.out.println("还剩"+min+"分"+sec+"秒");
            time--;
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args){
        CountDown countDown=new CountDown();
        countDown.run();
        System.out.println("7890");
    }
}
