package ch.loewenfels.robot.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;

@Path("motor")
public class MotorService {
    
    @Path("test")
    @GET
    @Produces("text/plain")
    public String test() throws IOException {
        System.out.println("test");
        return "test";
    }

    @Path("init")
    @GET
    @Produces("text/plain")
    public String init() throws IOException {
        Runtime.getRuntime().exec("gpio mode 4 out");
        Runtime.getRuntime().exec("gpio mode 5 out");
        Runtime.getRuntime().exec("gpio mode 6 out");
        System.out.println("init");
        return "init";
    }
    @Path("on")
    @GET
    @Produces("text/plain")
    public String turnOn() throws IOException {
        Runtime.getRuntime().exec("gpio write 5 1");
        System.out.printf("on\n");
        return "on";
    }

    @Path("off")
    @GET
    @Produces("text/plain")
    public String turnOff() throws IOException {
        Runtime.getRuntime().exec("gpio write 5 0");
        System.out.println("off\n");
        return "off";
    }

    @Path("fwd")
    @GET
    @Produces("text/plain")
    public String turnFwd() throws IOException {
        Runtime.getRuntime().exec("gpio write 4 0");
        Runtime.getRuntime().exec("gpio write 6 1");
        System.out.println("fwd\n");
        return "fwd";
    }

    @Path("bwd")
    @GET
    @Produces("text/plain")
    public String turnBwd() throws IOException {
        Runtime.getRuntime().exec("gpio write 4 1");
        Runtime.getRuntime().exec("gpio write 6 0");
        System.out.println("bwd\n");
        return "bwd";
    }

}
