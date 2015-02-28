import com.jetty.one.WebSocketServer;
import org.eclipse.jetty.server.Server;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zuoyan on 2015/2/12.
 */
public class wstTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Server server;

    //private FlashPolicyServer fpServer;

    private int port;

    @Test
    public void test() {
        WebSocketServer server = new WebSocketServer(8085);
        server.start();
    }
}
