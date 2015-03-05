/**
 * Created by zuoyan on 2015/3/4.
 */
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

//import org.eclipse.jetty.*;

/**
 * Basic Echo Client Socket
 */
public class SimpleEchoSocket2 {
//    private final CountDownLatch closeLatch;
//    @SuppressWarnings("unused")
//    private Session session;
//
//    public SimpleEchoSocket2() {
//        this.closeLatch = new CountDownLatch(1);
//    }
//
//    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
//        return this.closeLatch.await(duration, unit);
//    }
//
//    public void onClose(int statusCode, String reason) {
//        System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
//        this.session = null;
//        this.closeLatch.countDown();
//    }
//
//    public void onConnect(Session session) {
//        System.out.printf("Got connect: %s%n", session);
//        this.session = session;
//        try {
//            Future<Void> fut;
//            fut = session.getRemote().sendStringByFuture("Hello");
//            fut.get(2, TimeUnit.SECONDS);
//            fut = session.getRemote().sendStringByFuture("Thanks for the conversation.");
//            fut.get(2, TimeUnit.SECONDS);
//            session.close(StatusCode.NORMAL, "I'm done");
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//    }

    public void onMessage(String msg) {
        System.out.printf("Got msg: %s%n", msg);
    }
}
