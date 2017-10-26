package ch.loewenfels.robot.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;

@Path("motor/native")
public class NativeMotorService {

    @Path("init")
    @GET
    @Produces("text/plain")
    public String init() throws IOException {
        exportGpioOut(0);
        exportGpioOut(2);
        exportGpioOut(3);
        exportGpioOut(198);
        exportGpioOut(199);
        exportGpioOut(200);
        System.out.println("init");
        return "init\n";
    }

    private void exportGpioOut(int pin) throws IOException {
        exec(String.format("echo %s > /sys/class/gpio/export", pin));
        exec(String.format("echo out > /sys/class/gpio/gpio%s/direction", pin));
    }

    private void exec(String cmd) throws IOException {
        Runtime.getRuntime().exec(new String[] {"bash", "-c", cmd});
    }

    @Path("off")
    @GET
    @Produces("text/plain")
    public String turnOff() throws IOException {
        final String off1 = turnOff1();
        final String off2 = turnOff2();
        return String.format("%s,%s", off1, off2);
    }

    @Path("off/1")
    @GET
    @Produces("text/plain")
    public String turnOff1() throws IOException {
        valueToPin(0, 3);
        valueToPin(0, 2);
        valueToPin(0, 0);
        System.out.println("off1");
        return "off1\n";
    }

    @Path("off/2")
    @GET
    @Produces("text/plain")
    public String turnOff2() throws IOException {
        valueToPin(0, 200);
        valueToPin(0, 199);
        valueToPin(0, 198);
        System.out.println("off2");
        return "off2\n";
    }

    private void valueToPin(int value, int pin) throws IOException {
        exec(String.format("echo %s > /sys/class/gpio/gpio%s/value", value, pin));
    }

    @Path("fwd")
    @GET
    @Produces("text/plain")
    public String turnFwd() throws IOException {
        final String fwd1 = turnFwd1();
        final String fwd2 = turnFwd2();
        return String.format("%s,%s", fwd1, fwd2);
    }

    @Path("fwd/1")
    @GET
    @Produces("text/plain")
    public String turnFwd1() throws IOException {
        valueToPin(0, 3);
        valueToPin(1, 2);
        valueToPin(1, 0);
        System.out.println("fwd1");
        return "fwd1\n";
    }

    @Path("bwd/1")
    @GET
    @Produces("text/plain")
    public String turnBwd1() throws IOException {
        valueToPin(1, 3);
        valueToPin(1, 2);
        valueToPin(0, 0);
        System.out.println("bwd1");
        return "bwd1\n";
    }

    @Path("bwd/2")
    @GET
    @Produces("text/plain")
    public String turnBwd2() throws IOException {
        valueToPin(0, 200);
        valueToPin(1, 199);
        valueToPin(1, 198);
        System.out.println("fwd2");
        return "fwd2\n";
    }

    @Path("fwd/2")
    @GET
    @Produces("text/plain")
    public String turnFwd2() throws IOException {
        valueToPin(1, 200);
        valueToPin(1, 199);
        valueToPin(0, 198);
        System.out.println("bwd2");
        return "bwd2\n";
    }

    @Path("bwd")
    @GET
    @Produces("text/plain")
    public String turnBwd() throws IOException {
        final String bwd1 = turnBwd1();
        final String bwd2 = turnBwd2();
        return String.format("%s,%s", bwd1, bwd2);
    }

    @Path("turn/left/{time}")
    @GET
    @Produces("text/plain")
    public String left(@PathParam("time")String duration) throws IOException {
        turnFwd1();
//        turnBwd2();
        wait(duration);
        turnOff();
        return String.format("turnleft:%s\n", duration);
    }

    @Path("turn/right/{time}")
    @GET
    @Produces("text/plain")
    public String right(@PathParam("time")String duration) throws IOException {
        turnFwd2();
//        turnBwd1();
        wait(duration);
        turnOff();
        return String.format("turnleft:%s\n", duration);
    }

    private void wait(String duration) throws IOException {
        try {
            Thread.sleep(Integer.parseInt(duration));
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }
}